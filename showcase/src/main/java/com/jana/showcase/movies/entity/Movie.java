/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jana.showcase.movies.entity;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author JANARTHANANS
 */
@Entity
@Table(name = "MOVIE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Movie.findCount", query = "SELECT COUNT(m) FROM Movie m"),
    @NamedQuery(name = "Movie.findAll", query = "SELECT m FROM Movie m order by m.rating desc, m.votes desc"),
    @NamedQuery(name = "Movie.findById", query = "SELECT m FROM Movie m WHERE m.id = :id"),
    @NamedQuery(name = "Movie.findByTitle", query = "SELECT m FROM Movie m WHERE m.title = :title"),
    @NamedQuery(name = "Movie.findByDirector", query = "SELECT m FROM Movie m WHERE m.director = :director"),
    @NamedQuery(name = "Movie.findByCertification", query = "SELECT m FROM Movie m WHERE m.certification = :certification"),
    @NamedQuery(name = "Movie.findByReleaseDate", query = "SELECT m FROM Movie m WHERE m.releaseDate = :releaseDate"),
    @NamedQuery(name = "Movie.findByRunningTime", query = "SELECT m FROM Movie m WHERE m.runningTime = :runningTime"),
    @NamedQuery(name = "Movie.findByVotes", query = "SELECT m FROM Movie m WHERE m.votes = :votes"),
    @NamedQuery(name = "Movie.findByRating", query = "SELECT m FROM Movie m WHERE m.rating = :rating"),
    @NamedQuery(name = "Movie.findByMetascore", query = "SELECT m FROM Movie m WHERE m.metascore = :metascore"),
    @NamedQuery(name = "Movie.findByPoster", query = "SELECT m FROM Movie m WHERE m.poster = :poster"),
    @NamedQuery(name = "Movie.findByReleaseYear", query = "SELECT m FROM Movie m WHERE m.releaseYear = :releaseYear")})
public class Movie implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull (message = "Id cannot be empty")
    @Size(min = 1, max = 20)
    @Column(name = "ID")
    private String id;
    @Basic(optional = false)
    @NotNull (message = "Title cannot be empty")
    @Size(min = 1, max = 255)
    @Column(name = "TITLE")
    private String title;
    @Lob
    @NotNull (message = "Description cannot be empty")
    @Size(min = 1)
    @Column(name = "DESCRIPTION")
    private String description;
    @Lob
    @NotNull (message = "Actors cannot be empty")
    @Size(min = 1)
    @Column(name = "STARS")
    private String stars;
    @Basic(optional = false)
    @NotNull (message = "Director cannot be empty")
    @Size(min = 1, max = 100)
    @Column(name = "DIRECTOR")
    private String director;
    @Size(max = 25)
    @Column(name = "CERTIFICATION")
    private String certification;
    @Column(name = "RELEASE_DATE")
    @Temporal(TemporalType.DATE)
    private LocalDate releaseDate;
    @Size(max = 25)
    @Column(name = "RUNNING_TIME")
    private String runningTime;
    @Column(name = "VOTES")
    private Long votes;
    @Max(value=10)  @Min(value=0)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "RATING")
    private Double rating;
    @Lob
    @Column(name = "STORYLINE")
    private String storyline;
    @Lob
    @Column(name = "GALLERY")
    private String gallery;
    @Lob
    @Column(name = "GENRE")
    private String genre;
    @Column(name = "METASCORE")
    private Double metascore;
    @Size(max = 255)
    @Column(name = "POSTER")
    private String poster;
    @NotNull (message = "Release Year cannot be empty")
    @Size(max=4)
    @Min(value=1900)
    @Column(name = "RELEASE_YEAR")
    private String releaseYear;

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
        return stars;
    }

    public void setStars(String stars) {
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

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getRunningTime() {
        return runningTime;
    }

    public void setRunningTime(String runningTime) {
        this.runningTime = runningTime;
    }

    public Long getVotes() {
        return votes;
    }

    public void setVotes(Long votes) {
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
        return gallery;
    }

    public void setGallery(String gallery) {
        this.gallery = gallery;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
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

    public String getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(String releaseYear) {
        this.releaseYear = releaseYear;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in case the id field is not set
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
