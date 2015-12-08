/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jana.showcase.movies.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author JANARTHANANS
 */
@Entity
@Table(name = "MOVIE")
@XmlRootElement(name = "movie")
@NamedQueries({
    @NamedQuery(name = "Movie.findAll", query = "SELECT m FROM Movie m"),
    @NamedQuery(name = "Movie.findById", query = "SELECT m FROM Movie m WHERE m.id = :id"),
    @NamedQuery(name = "Movie.findByTitle", query = "SELECT m FROM Movie m WHERE m.title = :title"),
    @NamedQuery(name = "Movie.findByRanking", query = "SELECT m FROM Movie m WHERE m.ranking = :ranking"),
    @NamedQuery(name = "Movie.findByRating", query = "SELECT m FROM Movie m WHERE m.rating = :rating"),
    @NamedQuery(name = "Movie.findByPosterUrl", query = "SELECT m FROM Movie m WHERE m.posterUrl = :posterUrl"),
    @NamedQuery(name = "Movie.findByRelease", query = "SELECT m FROM Movie m WHERE m.release = :release")})
public class Movie implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "ID_IMDB")
    private String id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "TITLE")
    private String title;
    @Column(name = "RANKING")
    private Integer ranking;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "RATING")
    private Double rating;
    @Size(max = 250)
    @Column(name = "POSTER_URL")
    private String posterUrl;
    @Column(name = "RELEASE")
    private Integer release;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getRanking() {
        return ranking;
    }

    public void setRanking(Integer ranking) {
        this.ranking = ranking;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public Integer getRelease() {
        return release;
    }

    public void setRelease(Integer release) {
        this.release = release;
    }

//    @Override
//    public String toString() {
//        return "{"
//                + "\"movie\":{"
//                + "\"id\":\"" + id + "\","
//                + "\"title\":\""+title+"\","
//                + "\"ranking\":\""+ranking+"\", "
//                + "\"rating\":\""+rating+"\", "
//                + "\"posterUrl\":\""+posterUrl+"\", "
//                + "\"release\":\""+release+"\""
//                + "}"
//                + "}";
//    }
    
}
