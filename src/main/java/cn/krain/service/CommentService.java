package cn.krain.service;

import cn.krain.entity.Comment;

import java.util.List;

/**
 * @author CC
 * @data 2020/12/20 - 11:53
 */
public interface CommentService {
    List<Comment> queryCommentByMoviename(String movieName);

    void delCommentById(String id);

    void addCommentForMovie(Comment comment, String movieId);

    void modifyComment(Comment comment);
}
