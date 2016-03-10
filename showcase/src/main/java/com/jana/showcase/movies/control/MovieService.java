/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jana.showcase.movies.control;

import com.jana.showcase.movies.entity.Movie;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.Valid;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author JANARTHANANS
 */
@Stateless
public class MovieService {
    
    private Log logger = LogFactory.getLog(MovieService.class);
    
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
            logger.info("Exception "+e.getMessage());
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
        logger.info("Movie Added");
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

    /**
     * 
     * @param filter
     * @param keyword
     * @param page
     * @param limit
     * @return 
     */
    public List<Movie> search(
            String filter,
            String keyword,
            int page,
            int limit) {
        String nativeQuery = "SELECT * FROM MOVIE ";
//        String searchClause = getSearchClause(title, year, actor, director, rating, condition);
        String searchClause = getSearchClause(filter, keyword);
        nativeQuery = Objects.nonNull(searchClause)?nativeQuery+searchClause:nativeQuery;

        // Create a native query and map the resulting entity (i.e. Movie.class) for each object
        Query query = em.createNativeQuery(nativeQuery, Movie.class);
//        query.setParameter(1, keyword);
        query = setQueryParameters(keyword, query);
//        query = setQueryParameters(title, year, actor, director, rating, query);
        if(page>0 && limit>0) {
            query.setFirstResult((page-1) * limit);
            query.setMaxResults(limit);
        }

        List<Movie> movies = new ArrayList<>();
        try {
            movies = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return movies;
    }

    /**
     * 
     * @param keyword
     * @param filter
     * @return 
     */
    private String getSearchClause(String filter, String keyword) {
        if(!(Objects.isNull(filter) || filter.trim().isEmpty() || filter.equalsIgnoreCase("null")) &&
                    !(Objects.isNull(keyword) || keyword.trim().isEmpty() || keyword.equalsIgnoreCase("null"))) {
            if(filter.equalsIgnoreCase("title") ||
                    filter.equalsIgnoreCase("director") ||
                    filter.equalsIgnoreCase("actor")) {
                return " where lower("+(filter.equalsIgnoreCase("actor")?"stars":filter)+") like ?1";
            } else if(filter.equalsIgnoreCase("year")){
                return " where char(release_year) like ?1";
            } else if(filter.equalsIgnoreCase("rating")){
                return " where "+filter+" like ?1";
            }
        } else if(Objects.isNull(filter)) {
                return " where lower(title) like ?1";
        }
        return null;
    }
    
    /**
     * 
     * @param keyword
     * @param query
     * @return 
     */
    private Query setQueryParameters(String keyword, Query query) {
        if(Objects.nonNull(keyword)) {
            query.setParameter(1, "%"+keyword.toLowerCase()+"%");
        }
        return query;
    }
    
    /**
     * 
     * @param title
     * @param year
     * @param actor
     * @param director
     * @param rating
     * @param condition
     * @return 
     */
    private String getSearchClause(String title, 
            String year, 
            String actor, 
            String director, 
            String rating, 
            String condition) {
        String searchClause = null;
        int position = 1;
        if(Objects.nonNull(title)) {
            searchClause = " lower(title) like ?"+position+" ";
            position++;
        }
        if(Objects.nonNull(year)) {
            searchClause = (Objects.nonNull(searchClause))
                    ? searchClause+condition+" char(release_year) like ?"+position+" "
                    : " char(release_year) like ?"+position+" ";
            position++;
        }
        if(Objects.nonNull(actor)) {
            searchClause = (Objects.nonNull(searchClause))
                    ? searchClause+condition+" lower(stars) like ?"+position+" "
                    : " lower(stars) like ?"+position+" ";
            position++;
        }
        if(Objects.nonNull(director)) {
            searchClause = (Objects.nonNull(searchClause))
                    ? searchClause+condition+" lower(director) like ?"+position+" "
                    : " lower(director) like ?"+position+" ";
            position++;
        }
        if(Objects.nonNull(rating)) {
            searchClause = (Objects.nonNull(searchClause))
                    ? searchClause+condition+" rating = ?"+position+" "
                    : " rating = ?"+position+" ";
            position++;
        }
        searchClause = (Objects.nonNull(searchClause))
                    ?" WHERE "+searchClause:searchClause;
        return searchClause;
    }

    /**
     * 
     * @param title
     * @param year
     * @param actor
     * @param director
     * @param rating
     * @param query
     * @return 
     */
    private Query setQueryParameters(String title, 
            String year, 
            String actor, 
            String director, 
            String rating, 
            Query query) {
        int position = 1;
        if(Objects.nonNull(title)) {
            query.setParameter(position, "%"+title.toLowerCase()+"%");
            position++;
        }
        if(Objects.nonNull(year)) {
            query.setParameter(position, "%"+year.toLowerCase()+"%");
            position++;
        }
        if(Objects.nonNull(actor)) {
            query.setParameter(position, "%"+actor.toLowerCase()+"%");
            position++;
        }
        if(Objects.nonNull(director)) {
            query.setParameter(position, "%"+director.toLowerCase()+"%");
            position++;
        }
        if(Objects.nonNull(rating)) {
            query.setParameter(position, rating);
            position++;
        }
        return query;
    }

}
