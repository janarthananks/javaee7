/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jana.showcase.movies.boundary;

import com.jana.showcase.movies.boundary.data.ErrorData;
import com.jana.showcase.movies.boundary.data.MovieJson;
import com.jana.showcase.movies.control.MovieService;
import com.jana.showcase.movies.entity.Movie;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
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
     * @param filter
     * @param keyword
     * @param page
     * @param limit
     * @return 
     */
    @GET
    public Response load(
                        @QueryParam(value = "sort") @DefaultValue(value = "title") String sort,
                        @QueryParam(value = "filter") String filter,
                        @QueryParam(value = "keyword") String keyword,
                        @QueryParam(value="page") @DefaultValue(value="1") int page,
                        @QueryParam(value="limit") @DefaultValue(value="10") int limit
    ) {
        try {
            List<Movie> movies;
            int moviesFound;
            if((Objects.isNull(filter) || filter.trim().isEmpty() || filter.equalsIgnoreCase("null")) &&
                    (Objects.isNull(keyword) || keyword.trim().isEmpty() || keyword.equalsIgnoreCase("null"))
                ) {
                movies = movieService.load(page, limit);
                moviesFound =  movieService.getCount();
            } else {
                movies = movieService.search(filter, keyword, page, limit);
                moviesFound = movieService.search(filter, keyword, 0, 0).size();
            }
            return Response
                    .ok()
                    .entity(
                            new MovieJson(
                                    movies,
                                    moviesFound)).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().build();
        }
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
            response = Response.status(Response.Status.OK)
                    .entity(
                        new MovieJson(
                            movie
                        )
                    ).build();
            System.out.println("Rank of movie "+movie.getId()+" is "+movieService.getRank(movie.getId()));
        } else {
            response = Response.status(Response.Status.NOT_FOUND)
                    .entity(
                        new MovieJson(
                            new ErrorData("Movie not found")
                        ))
                    .build();
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
            response = Response
                    .status(Response.Status.CONFLICT)
                    .build();
        } else {
            try {
                int retval = movieService.create(movie);
                if(retval==0) {
                    response = Response.status(Response.Status.CREATED)
                            .entity(
                                new MovieJson(movie)
                            ).build();
                    System.out.println("Rank of movie "+movie.getId()+" is "+movieService.getRank(movie.getId()));
                } else {
                    response = Response
                            .status(Response.Status.INTERNAL_SERVER_ERROR)
                            .build();
                }
            } catch (Exception ex) {
                System.out.println("Exception: "+ex.getMessage());
                if(ex.getCause() instanceof ConstraintViolationException) {
                    Map<String, String[]> errorMap = new HashMap<>();
                    ((ConstraintViolationException)ex.getCause())
                            .getConstraintViolations()
                            .stream().forEach((violation) -> {
                        //Map - Errors added to HashMap named errorMap. With key as a string
                        //and value as an array to hold a list of message
                        if(errorMap.containsKey(violation.getPropertyPath().toString())) {
                            String[] error = errorMap.get(violation.getPropertyPath().toString());
                            List<String> errorList = Arrays.asList(error);
                            errorList.add(violation.getMessage());
                            errorMap.put(violation.getPropertyPath().toString(), 
                                    (String[])errorList.toArray());
                        } else {
                            errorMap.put(violation.getPropertyPath().toString(), 
                                    new String[]{violation.getMessage()});
                        }
                    });
                    JsonArrayBuilder jsonErrorArraybuilder = Json.createArrayBuilder();
                    errorMap.entrySet().stream().forEach(em->{
                        JsonObjectBuilder jsonMessageObjectBuilder = Json.createObjectBuilder();
                        JsonArrayBuilder jsonMessageArrayBuilder = Json.createArrayBuilder();
                        Arrays.asList(em.getValue()).stream().forEach(message->{
                            jsonMessageArrayBuilder.add(message);
                        });
                        jsonMessageObjectBuilder.add("attribute",em.getKey());
                        jsonMessageObjectBuilder.add("message", jsonMessageArrayBuilder);
                        jsonErrorArraybuilder.add(jsonMessageObjectBuilder);
                    });
                    response = Response.status(422)
                            .entity(
                                Json.createObjectBuilder()
                                .add("errors", 
                                        jsonErrorArraybuilder
                                    )
                                .build()
                            ).build();
                } else {
                    response = Response.serverError()
                            .entity(new MovieJson(new ErrorData(ex.getMessage())))
                            .build();
                }
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
                    .build();
        }
        movie.setId(id);
        movie = movieService.update(movie);
        if(Objects.nonNull(movie)) {
            response = Response.status(Response.Status.OK)
                    .entity(
                        new MovieJson(
                            movie
                        )
                    ).build();
        } else {
            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .build();
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
                    .build();
        }
        return Response.status(Response.Status.NO_CONTENT).build();
    }
    
}
