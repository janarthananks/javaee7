/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jana.showcase.movies.boundary.data;

import com.jana.showcase.movies.entity.Movie;
import java.io.StringWriter;
import java.util.List;
import javax.json.Json;
import javax.json.JsonObject;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author JANARTHANANS
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class MovieJson {

    private Movie movie;
    
    private List<Movie> movies;
    
    private Meta meta;
    
    private JsonObject errorJson;
    
    private List<ErrorData> errors;
    
    private ErrorData error;
       
    /**
     * 
     */
    public MovieJson() {
    }
    /**
     * 
     * @param movie 
     */
    public MovieJson(Movie movie) {
        this.movie = movie;
    }
    /**
     * 
     * @param movies 
     * @param total 
     */
    public MovieJson(List<Movie> movies, int total) {
        this.movies = movies;
        this.meta = new Meta(total);
    }
    /**
     * 
     * @param errorJson
     */
    public MovieJson(JsonObject errorJson) {
        StringWriter writer = new StringWriter();
        Json.createWriter(writer).writeObject(errorJson);
//        this.errors = writer.toString();
        this.errorJson = errorJson;
    }
    /**
     * 
     * @param errors 
     */
    public MovieJson(List<ErrorData> errors) {
        this.errors = errors;
    }
    /**
     * 
     * @param error 
     */
    public MovieJson(ErrorData error) {
        this.error=error;
    }
    /**
     * 
     * @return 
     */
    public Movie getMovie() {
        return this.movie;
    }
    /**
     * 
     * @return 
     */
    public List<Movie> getMovies() {
        return this.movies;
    }
    /**
     * 
     * @return 
     */
    public Meta getMeta() {
        return this.meta;
    }
    /**
     * 
     * @return 
     */
    public JsonObject getErrors() {
        return this.errorJson;
    }
    /**
     * 
     * @return 
     */
    public ErrorData getError() {
        return this.error;
    }
}
