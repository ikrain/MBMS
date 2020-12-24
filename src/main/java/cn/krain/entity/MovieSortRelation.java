package cn.krain.entity;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author CC
 * @data 2020/12/18 - 10:43
 */
@Entity
@Table(name = "movie_sort_relation", schema = "movie", catalog = "")
public class MovieSortRelation {
    private String id;
    private String movieId;
    private String sortId;

    @Id
    @Column(name = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "movieId")
    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    @Basic
    @Column(name = "sortId")
    public String getSortId() {
        return sortId;
    }

    public void setSortId(String sortId) {
        this.sortId = sortId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovieSortRelation that = (MovieSortRelation) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(movieId, that.movieId) &&
                Objects.equals(sortId, that.sortId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, movieId, sortId);
    }
}
