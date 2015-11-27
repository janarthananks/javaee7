/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jana.showcase.movies.boundary;

import com.jana.showcase.movies.control.MovieService;
import com.jana.showcase.movies.entity.Message;
import com.jana.showcase.movies.entity.Movie;
import java.util.List;
import java.util.Objects;
import javax.inject.Inject;
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
    public List<Movie> load(@QueryParam(value = "sort") @DefaultValue(value = "title") String sort) {
        return movieService.load(sort);
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
            System.out.println(movie);
            response = Response.status(Response.Status.OK).entity(movie).build();
        } else {
            response = Response.status(Response.Status.NOT_FOUND)
                    .entity(new Message(Message.Code.RECORD_NOT_FOUND)).build();
        }
        return response;
    }
    
    /**
     * 
     * @param movie
     * @return 
     */
    @POST
    @Consumes(value = MediaType.APPLICATION_JSON)
    public Response create(Movie movie) {
        Response response;
        final Movie oldMovie = movieService.find(movie.getId());
        if(oldMovie!=null) {
            response = Response.status(Response.Status.CONFLICT)
                    .entity(new Message(Message.Code.RECORD_DUPLICATE)).build();
        } else {
            try {
                int retval = movieService.create(movie);
                if(retval==0) {
                    response = Response.status(Response.Status.CREATED).entity(movie).build();
                } else {
                    response = Response.status(Response.Status.NOT_ACCEPTABLE)
                            .entity(new Message(Message.Code.CREATION_FAILED)).build();
                }
            } catch (Exception e) {
                e.printStackTrace();
                response = Response.status(Response.Status.NOT_ACCEPTABLE)
                            .entity(new Message(Message.Code.CREATION_FAILED)).build();
            }
        }
        return response;
    }
    
    /**
     * 
     * @param id
     * @param movie
     * @return 
     */
    @PUT
    @Path(value = "/{id}")
    @Consumes(value = MediaType.APPLICATION_JSON)
    public Response update(@PathParam(value = "id") @NotNull String id, Movie movie) {
        Response response;
        if(!Objects.equals(id, movie.getId())){
            return Response.status(Response.Status.CONFLICT)
                            .entity(new Message(Message.Code.DATA_MISMATCH)).build();
        }
        movie = movieService.update(movie);
        if(Objects.nonNull(movie)) {
            response = Response.status(Response.Status.OK).entity(movie).build();
        } else {
            response = Response.status(Response.Status.NOT_ACCEPTABLE)
                            .entity(new Message(Message.Code.RECORD_NOT_FOUND)).build();
        }
        return response;
    }

    @DELETE
    @Path(value="/{id}")
    public Response delete(@PathParam("id") @NotNull String id) {
        if(movieService.delete(id)!=1){
            return Response.status(Response.Status.NOT_FOUND)
                            .entity(new Message(Message.Code.RECORD_NOT_FOUND)).build();
        }
        return Response.status(Response.Status.OK)
                            .entity(new Message(Message.Code.DELETION_SUCCESS)).build();
    }
    
}
