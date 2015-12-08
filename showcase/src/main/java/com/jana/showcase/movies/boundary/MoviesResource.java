/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jana.showcase.movies.boundary;

import com.jana.showcase.movies.boundary.data.MessageJson;
import com.jana.showcase.movies.boundary.data.MovieJson;
import com.jana.showcase.movies.control.MovieService;
import com.jana.showcase.movies.entity.Message;
import com.jana.showcase.movies.entity.Movie;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.inject.Inject;
import javax.validation.ConstraintViolationException;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author JANARTHANANS
 */
@Path("/movies")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class MoviesResource {
    
    @Inject
    private MovieService movieService;
    /**
     *
     * @param sort
     * @return
     */
    @GET
    public Response load(@QueryParam(value = "sort") @DefaultValue(value = "title") String sort) {
        return Response.ok().entity(new MovieJson(movieService.load(sort))).build();
    }
    
    /**
     *
     * @param id
     * @return
     */
    @GET
    @Path(value = "/{id}")
    public Response find(@PathParam(value = "id") @NotNull  String id) {
        final Movie movie = movieService.find(id);
        Response response;
        if(Objects.nonNull(movie)) {
            response = Response.status(Response.Status.OK).entity(new MovieJson(movie)).build();
        } else {
            response = Response.status(Response.Status.NOT_FOUND)
                    .entity(
                            new MessageJson(
                                new Message[] {
                                    new Message(Message.Code.RECORD_NOT_FOUND)
                                }
                            )).build();
        }
        return response;
    }
    
    /**
     * 
     * @param movieJson
     * @return 
     */
    @POST
    @Consumes(value = MediaType.APPLICATION_JSON)
    public Response create(MovieJson movieJson) {
        Response response;
        Movie movie = movieJson.getMovie();
        final Movie oldMovie = movieService.find(movie.getId());
        if(oldMovie!=null) {
            response = Response.status(Response.Status.CONFLICT)
                        .entity(new MessageJson(
                                new Message[]{
                                    new Message(Message.Code.RECORD_DUPLICATE)
                                }
                            )).build();
        } else {
            try {
                int retval = movieService.create(movie);
                if(retval==0) {
                    response = Response.status(Response.Status.CREATED).entity(new MovieJson(movie)).build();
                } else {
                    response = Response.status(Response.Status.NOT_ACCEPTABLE)
                            .entity(new MessageJson(
                                        new Message[]{
                                            new Message(Message.Code.CREATION_FAILED)
                                        }
                                    )).build();
                }
            } catch (Exception e) {
                System.out.println("Exception: "+e.getMessage());
                if(e.getCause() instanceof ConstraintViolationException) {
                    ((ConstraintViolationException)e.getCause()).getConstraintViolations().stream().forEach(System.out::println);
                }
                response = Response.status(Response.Status.NOT_ACCEPTABLE)
                            .entity(new MessageJson(
                                        new Message[]{
                                            new Message(Message.Code.CREATION_FAILED)
                                        }
                                    )).build();
            }
        }
        return response;
    }
    
    /**
     * 
     * @param id
     * @param movieJson
     * @return 
     */
    @PUT
    @Path(value = "/{id}")
    @Consumes(value = MediaType.APPLICATION_JSON)
    public Response update(@PathParam(value = "id") @NotNull String id, MovieJson movieJson) {
        Movie movie = movieJson.getMovie();
        Response response;
        if(Objects.nonNull(movie.getId()) && !Objects.equals(id, movie.getId())){
            return Response.status(Response.Status.CONFLICT)
                            .entity(new MessageJson(
                                        new Message[]{
                                            new Message(Message.Code.DATA_MISMATCH)
                                        }
                                    )).build();
        }
        movie.setId(id);
        movie = movieService.update(movie);
        if(Objects.nonNull(movie)) {
            response = Response.status(Response.Status.OK).entity(new MovieJson(movie)).build();
        } else {
            response = Response.status(Response.Status.NOT_ACCEPTABLE)
                            .entity(new MessageJson(
                                new Message[] {
                                    new Message(Message.Code.RECORD_NOT_FOUND)
                                }
                            )).build();
        }
        return response;
    }

    /**
     * 
     * @param id
     * @return 
     */
    @DELETE
    @Path(value="/{id}")
    public Response delete(@PathParam("id") @NotNull String id) {
        if(movieService.delete(id)!=1){
            return Response.status(Response.Status.NOT_FOUND)
                            .entity(new MessageJson(
                                        new Message[]{
                                            new Message(Message.Code.RECORD_NOT_FOUND)
                                        }
                                    )).build();
        }
        return Response.status(Response.Status.OK)
                            .entity(new MessageJson(
                                        new Message[]{
                                            new Message(Message.Code.DELETION_SUCCESS)
                                        }
                                    )).build();
    }
    
}
