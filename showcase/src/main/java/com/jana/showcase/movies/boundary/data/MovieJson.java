/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jana.showcase.movies.boundary.data;

import com.jana.showcase.movies.entity.Movie;
import java.util.List;
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
     */
    public MovieJson(List<Movie> movies) {
        this.movies = movies;
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
}
