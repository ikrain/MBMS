package cn.krain.dao;

import cn.krain.entity.MovieSortRelation;

import java.util.List;

/**
 * @author CC
 * @data 2020/12/18 - 10:42
 */
public interface MovieSortRelationDao {

    void insertMovieSortRelation(MovieSortRelation movieSortRelation);

    /**
     * 根据movieId删除关联信息
     * @param movieId
     */
    void deleteMovieSortRelation(String movieId);

    /**
     * 根据movieId和sortId删除对应关联关系
     * @param movieSortRelation
     */
    void deleteMSRByMSID(MovieSortRelation movieSortRelation);

    /**
     * 判断当前数据库中是否存在给的movieId和typeId的信息
     * @param movieId
     * @param typeId
     * @return
     */
    List<MovieSortRelation> selectMovieSortRelation(String movieId, String typeId);

    /**
     * 根据movieId查询关联关系
     * @param movieId
     * @return
     */
    List<MovieSortRelation> selectMSRByMovieId(String movieId);
}

