/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jana.showcase.movies.boundary;

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
public class Movies {

    private List<Movie> movies;
    
    /**
     * 
     */
    public Movies() {
    }
    
    /**
     * 
     * @param movies 
     */
    public Movies(List<Movie> movies) {
        this.movies = movies;
    }
    
}
