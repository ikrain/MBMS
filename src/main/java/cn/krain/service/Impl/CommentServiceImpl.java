package cn.krain.service.Impl;

import cn.krain.dao.CommentDao;
import cn.krain.dao.MovieCommentRelationDao;
import cn.krain.dao.MovieDao;
import cn.krain.entity.Comment;
import cn.krain.entity.Movie;
import cn.krain.entity.MovieCommentRelation;
import cn.krain.service.CommentService;
import cn.krain.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author CC
 * @data 2020/12/20 - 11:59
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private MovieDao movieDao;

    @Autowired
    private MovieCommentRelationDao movieCommentRelationDao;

    /**
     * 通过电影名称查询出电影ID，再通过ID查询影评
     *
     * 首先会判断movieName是否为空，如果为空则直接查询所有影评
     * 如果不为空则根据电影ID查询对应的影评
     *
     * @param movieName
     * @return
     */
    @Override
    public List<Comment> queryCommentByMoviename(String movieName) {
        if (movieName.trim()=="" || movieName.length()==0){
            return commentDao.selectAllComment();
        }else {
            // 根据电影名称查询电影
            Movie movie = movieDao.selectMovieByMovieName(movieName);
            System.out.println(movie.getId());
            // 根据电影ID查询对应的影评
            return commentDao.selectCommentByMovieId(movie.getId());
        }
    }

    /**
     * 首先删除关联数据
     * 之后删除影评
     * @param id
     */
    @Override
    public void delCommentById(String id) {
        // 根据影评ID删除关联数据
        movieCommentRelationDao.deleteMCRByCommentId(id);
        // 删除影评
        commentDao.deleteCommentByCommentId(id);
    }

    @Override
    public void addCommentForMovie(Comment comment, String movieId) {
        // 根据电影和影评ID添加关联数据
        MovieCommentRelation mcr = new MovieCommentRelation();
        mcr.setId(UUIDUtil.getUUID());
        mcr.setMovieId(movieId);
        mcr.setCommentId(comment.getId());
        movieCommentRelationDao.insertMCR(mcr);
        // 添加影评
        commentDao.insertComment(comment);
    }

    /**
     * 修改影评
     * @param comment
     */
    @Override
    public void modifyComment(Comment comment) {
        commentDao.updateComment(comment);
    }


}
