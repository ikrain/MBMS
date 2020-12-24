package cn.krain.dao;

import cn.krain.entity.Comment;

import java.util.List;

/**
 * @author CC
 * @data 2020/12/20 - 12:00
 */
public interface CommentDao {
    List<Comment> selectCommentByMovieId(String movieName);

    List<Comment> selectAllComment();

    void deleteCommentByCommentId(String id);

    void insertComment(Comment comment);

    void updateComment(Comment comment);

    /**
     * 根据电影ID删除影评
     * @param id
     */
    void deleteCommentByMovieId(String id);
}
