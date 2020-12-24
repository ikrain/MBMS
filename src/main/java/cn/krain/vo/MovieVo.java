package cn.krain.vo;

import java.util.Map;

/**
 * @author CC
 * @data 2020/12/18 - 15:29
 */
public class MovieVo {

    private String id;
    private String movieName;
    private String director;
    private String screenwriter;
    private String actor;
    Map<String, String> typeMap;
    private String country;
    private String language;
    private String releaseDate;
    private String duration;
    private String posterServerName;
    private String Synopsis;
    private String description;
    private String movieServerName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getScreenwriter() {
        return screenwriter;
    }

    public void setScreenwriter(String screenwriter) {
        this.screenwriter = screenwriter;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public Map<String, String> getTypeMap() {
        return typeMap;
    }

    public void setTypeMap(Map<String, String> typeMap) {
        this.typeMap = typeMap;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getPosterServerName() {
        return posterServerName;
    }

    public void setPosterServerName(String posterServerName) {
        this.posterServerName = posterServerName;
    }

    public String getSynopsis() {
        return Synopsis;
    }

    public void setSynopsis(String synopsis) {
        Synopsis = synopsis;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMovieServerName() {
        return movieServerName;
    }

    public void setMovieServerName(String movieServerName) {
        this.movieServerName = movieServerName;
    }
}
