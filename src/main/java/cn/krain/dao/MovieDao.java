package cn.krain.dao;

import cn.krain.entity.Movie;

import java.util.List;

/**
 * @author CC
 * @data 2020/12/17 - 19:22
 */
public interface MovieDao {

    void insertMovie(Movie movie);

    void deleteMovie(String id);

    void updateMovie(Movie movie);

    List<Movie> selectMovieByMore(Movie movie);

    // 根据电影名称查询电影
    Movie selectMovieByMovieName(String movieName);
}
