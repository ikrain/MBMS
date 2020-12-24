package cn.krain.dao;

import cn.krain.entity.MovieCommentRelation;
import cn.krain.entity.MovieNewsRelation;

/**
 * @author CC
 * @data 2020/12/18 - 18:50
 */
public interface MovieCommentRelationDao {

    // 根据影评ID删除影评
    void deleteMCRByCommentId(String id);

    // 根据电影ID删除所有影评
    void deleteMCRByMovieId(String id);

    void insertMCR(MovieCommentRelation movieCommentRelation);
}
