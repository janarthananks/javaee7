/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jana.showcase.imdbclient;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author JANARTHANANS
 */
@XmlRootElement
public class Movie implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String title;
    private String description;
    private String[] stars;
    private String director;
    private String certification;
    private String releaseDate;
    private String runningTime;
    private String votes;
    private Double rating;
    private String storyline;
    private String[] gallery;
    private String[] genre;
    private Double metascore;
    private String poster;
    private String year;

    public Movie() {
    }

    public Movie(String id) {
        this.id = id;
    }

    public Movie(String id, String title, String director) {
        this.id = id;
        this.title = title;
        this.director = director;
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStars() {
        return Objects.nonNull(stars)?Arrays.toString(stars):null;
    }

    public void setStars(String[] stars) {
        this.stars = stars;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getCertification() {
        return certification;
    }

    public void setCertification(String certification) {
        this.certification = certification;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getRunningTime() {
        return runningTime;
    }

    public void setRunningTime(String runningTime) {
        this.runningTime = runningTime;
    }

    public String getVotes() {
        return votes;
    }

    public void setVotes(String votes) {
            this.votes = votes;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getStoryline() {
        return storyline;
    }

    public void setStoryline(String storyline) {
        this.storyline = storyline;
    }

    public String getGallery() {
        return Objects.nonNull(gallery)?Arrays.toString(gallery):null;
    }

    public void setGallery(String[] gallery) {
        this.gallery = gallery;
    }

    public String getGenre() {
        return Objects.nonNull(genre)?Arrays.toString(genre):null;
    }

    public void setGenre(String[] genre) {
        this.genre = genre;
    }

    public Double getMetascore() {
        return metascore;
    }

    public void setMetascore(Double metascore) {
        this.metascore = metascore;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Movie)) {
            return false;
        }
        Movie other = (Movie) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.jana.showcase.Movie[ id=" + id + " ]";
    }
    
}
