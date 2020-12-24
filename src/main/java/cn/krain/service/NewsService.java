package cn.krain.service;

import cn.krain.entity.News;

import java.util.List;

/**
 * @author CC
 * @data 2020/12/18 - 18:38
 */
public interface NewsService {
    List<News> queryNewsByMovieName(String movieName);

    void delNewsById(String id);

    void addNews(News news, String movieId);

    void modifyNews(News news);
}
