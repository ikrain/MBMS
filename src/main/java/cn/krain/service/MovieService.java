package cn.krain.service;

import cn.krain.entity.Movie;
import cn.krain.vo.MovieVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author CC
 * @data 2020/12/17 - 17:51
 */
public interface MovieService {

    Object uploadFile(MultipartFile file,String movieId);

    Object delMovieFile(String moviePath);

    void addMovieMess(Movie movie);

    void delMovie(String id, String posterServerName, String movieServerName);

    void modifyMovieMess(Movie movie);

    List<MovieVo> queryMovieGetMovieVo(Movie movie);
}
