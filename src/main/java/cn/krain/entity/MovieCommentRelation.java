package cn.krain.entity;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author CC
 * @data 2020/12/18 - 10:43
 */
@Entity
@Table(name = "movie_comment_relation", schema = "movie", catalog = "")
public class MovieCommentRelation {
    private String id;
    private String movieId;
    private String commentId;

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
    @Column(name = "commentId")
    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovieCommentRelation that = (MovieCommentRelation) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(movieId, that.movieId) &&
                Objects.equals(commentId, that.commentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, movieId, commentId);
    }
}
