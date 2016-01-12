/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jana.showcase.movies.control;

import com.jana.showcase.movies.entity.Movie;
import java.util.List;
import java.util.Objects;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.Valid;

/**
 *
 * @author JANARTHANANS
 */
@Stateless
public class MovieService {
    
    @PersistenceContext (unitName = "default")
    private EntityManager em;
    
    /**
     * 
     * @return 
     */
    public int getCount() {
        Query query = em.createNamedQuery("Movie.findCount");
        return ((Number)query.getSingleResult()).intValue();
    }
    
    /**
     * 
     * @param id
     * @return 
     */
    public int getRank(String id) {
        Query query = em.createNativeQuery(
                "SELECT MOVIE.RANK FROM (SELECT ROW_NUMBER() OVER () AS RANK, MOVIE.* FROM MOVIE) "
                        + "AS MOVIE WHERE MOVIE.id = '"+ id+"'");
        return ((Number)query.getSingleResult()).intValue();
    }
    
    /**
     * 
     * @param page
     * @param limit
     * @return 
     */
    public List<Movie> load(int page, int limit) {
        Query query = em.createNamedQuery("Movie.findAll");
//        query.setParameter("column", sortColumn);
        query.setFirstResult((page-1)* limit);
        query.setMaxResults(limit);
        return query.getResultList();
    }
    
    /**
     * 
     * @param id
     * @return 
     */
    public Movie find(String id) {
        try {
            return (Movie)em.createNamedQuery("Movie.findById").setParameter("id", id).getSingleResult();
        } catch (NoResultException e) {
            System.out.println("Exception "+e.getMessage());
            return null;
        }
    }

    /**
     * 
     * @param movie
     * @return 
     */
    public int create(Movie movie) {
        em.persist(movie);
        System.out.println("Movie Added");
        return 0;
    }
    
    /**
     * 
     * @param movie
     * @return 
     */
    public Movie update(@Valid Movie movie) {
        if(Objects.isNull(em.find(Movie.class, movie.getId()))) {
            return null;
        } else {
            return em.merge(movie);
        }
    }
    
    /**
     * 
     * @param id
     * @return 
     */
    public int delete(String id) {
        Movie movie = em.find(Movie.class, id);
        if(Objects.isNull(movie)) {
            return -1;
        } else {
            em.remove(movie);
            return 1;
        }
    }
}
