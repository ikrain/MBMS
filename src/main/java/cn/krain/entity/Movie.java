package cn.krain.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

/**
 * @author CC
 * @data 2020/12/18 - 11:38
 */
@Entity
public class Movie {
    private String id;
    private String movieName;
    private String director;
    private String screenwriter;
    private String actor;
    private String type;
    private String country;
    private String language;
    private String releaseDate;
    private String duration;
    private String poster;
    private String synopsis;
    private String description;
    private String movieServerName;

    @Id
    @Column(name = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "movieName")
    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    @Basic
    @Column(name = "director")
    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    @Basic
    @Column(name = "screenwriter")
    public String getScreenwriter() {
        return screenwriter;
    }

    public void setScreenwriter(String screenwriter) {
        this.screenwriter = screenwriter;
    }

    @Basic
    @Column(name = "actor")
    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    @Basic
    @Column(name = "type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Basic
    @Column(name = "country")
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Basic
    @Column(name = "language")
    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Basic
    @Column(name = "releaseDate")
    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Basic
    @Column(name = "duration")
    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    @Basic
    @Column(name = "poster")
    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    @Basic
    @Column(name = "Synopsis")
    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "movieServerName")
    public String getMovieServerName() {
        return movieServerName;
    }

    public void setMovieServerName(String movieServerName) {
        this.movieServerName = movieServerName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return Objects.equals(id, movie.id) &&
                Objects.equals(movieName, movie.movieName) &&
                Objects.equals(director, movie.director) &&
                Objects.equals(screenwriter, movie.screenwriter) &&
                Objects.equals(actor, movie.actor) &&
                Objects.equals(type, movie.type) &&
                Objects.equals(country, movie.country) &&
                Objects.equals(language, movie.language) &&
                Objects.equals(releaseDate, movie.releaseDate) &&
                Objects.equals(duration, movie.duration) &&
                Objects.equals(poster, movie.poster) &&
                Objects.equals(synopsis, movie.synopsis) &&
                Objects.equals(description, movie.description) &&
                Objects.equals(movieServerName, movie.movieServerName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, movieName, director, screenwriter, actor, type, country, language, releaseDate, duration, poster, synopsis, description, movieServerName);
    }


}
