package cn.krain.entity;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author CC
 * @data 2020/12/18 - 10:43
 */
@Entity
@Table(name = "movie_news_relation", schema = "movie", catalog = "")
public class MovieNewsRelation {
    private String id;
    private String movieId;
    private String newsId;

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
    @Column(name = "newsId")
    public String getNewsId() {
        return newsId;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovieNewsRelation that = (MovieNewsRelation) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(movieId, that.movieId) &&
                Objects.equals(newsId, that.newsId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, movieId, newsId);
    }
}
