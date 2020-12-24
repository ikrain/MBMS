package cn.krain.dao;

import cn.krain.entity.News;

import java.util.List;

/**
 * @author CC
 * @data 2020/12/18 - 18:43
 */
public interface NewsDao {
    List<News> selectNews(News news);

    void deleteNewsById(String id);

    void insertNews(News news);

    void updateNews(News news);

    List<News> selectAllNews();

    List<News> selectNewsByMovieId(String id);

    /**
     * 根据电影ID删除新闻
     * @param id
     */
    void deleteNewsByMovieId(String id);
}
