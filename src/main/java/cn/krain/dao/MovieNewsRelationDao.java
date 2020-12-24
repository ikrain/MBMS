package cn.krain.dao;

import cn.krain.entity.MovieNewsRelation;

/**
 * @author CC
 * @data 2020/12/18 - 18:50
 */
public interface MovieNewsRelationDao {
    // 通过新闻id删除新闻关系表数据
    void deleteMERByNewsId(String id);

    // 通过电影id删除新闻关系表数据
    void deleteMERByMovieId(String id);

    // 添加新闻关联信息
    void insertMNR(MovieNewsRelation movieNewsRelation);
}
