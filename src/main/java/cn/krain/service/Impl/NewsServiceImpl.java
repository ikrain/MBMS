package cn.krain.service.Impl;

import cn.krain.dao.MovieDao;
import cn.krain.dao.MovieNewsRelationDao;
import cn.krain.dao.NewsDao;
import cn.krain.entity.Movie;
import cn.krain.entity.MovieNewsRelation;
import cn.krain.entity.News;
import cn.krain.service.NewsService;
import cn.krain.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author CC
 * @data 2020/12/18 - 18:43
 */
@Service
public class NewsServiceImpl implements NewsService {

    @Autowired
    private NewsDao newsDao;

    @Autowired
    private MovieDao movieDao;

    @Autowired
    private MovieNewsRelationDao movieNewsRelationDao;

    @Override
    public List<News> queryNewsByMovieName(String movieName) {
        if (movieName.trim()=="" || movieName.length()==0){
            return newsDao.selectAllNews();
        }else {
            // 根据电影名称查询电影
            Movie movie = movieDao.selectMovieByMovieName(movieName);
            System.out.println(movie.getId());
            // 根据电影ID查询对应的新闻
            return newsDao.selectNewsByMovieId(movie.getId());
        }
    }

    /**
     * @param id 新闻ID
     */
    @Override
    public void delNewsById(String id) {
        // 首先删除电影与新闻关联关系数据
        movieNewsRelationDao.deleteMERByNewsId(id);
        // 之后删除新闻
        newsDao.deleteNewsById(id);
    }

    @Override
    public void addNews(News news, String movieId) {
        // 1.向关联关系表中添加关联信息
        MovieNewsRelation movieNewsRelation = new MovieNewsRelation();
        movieNewsRelation.setId(UUIDUtil.getUUID());
        movieNewsRelation.setMovieId(movieId);
        movieNewsRelation.setNewsId(news.getId());
        movieNewsRelationDao.insertMNR(movieNewsRelation);
        // 2.添加新闻
        newsDao.insertNews(news);
    }

    @Override
    public void modifyNews(News news) {
        newsDao.updateNews(news);
    }
}
