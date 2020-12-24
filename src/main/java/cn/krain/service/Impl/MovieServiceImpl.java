package cn.krain.service.Impl;

import cn.krain.dao.*;
import cn.krain.entity.Movie;
import cn.krain.entity.Sort;
import cn.krain.result.ResultUtil;
import cn.krain.service.MovieService;
import cn.krain.vo.MovieVo;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author CC
 * @data 2020/12/17 - 17:52
 */
@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieDao movieDao;

    @Autowired
    private MovieSortRelationDao movieSortRelationDao;

    @Autowired
    private SortDao sortDao;

    @Autowired
    private MovieNewsRelationDao movieNewsRelationDao;

    @Autowired
    private NewsDao newsDao;

    @Autowired
    private MovieCommentRelationDao movieCommentRelationDao;

    @Autowired
    private CommentDao commentDao;

    private String SERVER_PATH = "E:\\MBMS_FileServer\\";

    /**
     * 上传电影文件
     * @param file
     * @return
     */
    @Override
    public Object uploadFile(MultipartFile file,String movieName) {
        if (file.isEmpty()){
            return ResultUtil.fileError("所选电影文件为空");
        }
        // 获取原始名称
        String originalFileName = file.getOriginalFilename();
        // 设置新的文件名称
        String fileName = movieName+"."+originalFileName.substring(originalFileName.lastIndexOf(".")+1);
        String filePath = "E:\\MBMS_FileServer\\";  // 设置本地文件服务器路径
        File localFile = new File(filePath+fileName);
        if (!localFile.getParentFile().exists()){
            localFile.mkdirs();
        }
        try {
            file.transferTo(localFile);
        } catch (IOException e) {
            e.printStackTrace();
            return ResultUtil.fileError("电影文件上传失败");
        }
        JSONObject object = new JSONObject();
        object.put("movieServerName",fileName);
        return ResultUtil.success("电影文件上传成功",object);
    }

    /**
     * 添加电影信息
     * @param movie
     */
    @Override
    public void addMovieMess(Movie movie){
        movieDao.insertMovie(movie);
    }

    /**
     * 删除电影文件
     * @param moviePath
     * @return
     */
    @Override
    public Object delMovieFile(String moviePath) {
        File file = new File(moviePath);
        if (!file.exists()){
            return ResultUtil.fileError("电影文件不存在");
        }
        file.delete();
        return ResultUtil.success("电影删除成功");
    }

    /**
     * 删除电影信息
     * @param id
     */
    @Override
    public void delMovie(String id, String posterServerName, String movieServerName) {
        // 删除类型关联，根据电影ID删除关联关系中的信息，即使有多个关联关系也可以删除
        // 删除电影时，不需要删除电影类型，只需要删除类型关联数据即可
        // 将该电影的所有类型关联都删除
        movieSortRelationDao.deleteMovieSortRelation(id);
        // 删除新闻
        newsDao.deleteNewsByMovieId(id);
        // 删除新闻关联
        movieNewsRelationDao.deleteMERByMovieId(id);
        // 删除影评
        commentDao.deleteCommentByMovieId(id);
        // 删除影评关联
        movieCommentRelationDao.deleteMCRByMovieId(id);
        // 删除海报
        String posterPath = SERVER_PATH + posterServerName;
        delMovieFile(posterPath);
        // 删除视频文件
        String moviePath = SERVER_PATH + movieServerName;
        delMovieFile(moviePath);
        // 删除电影
        movieDao.deleteMovie(id);
    }

    /**
     * 修改电影信息
     * @param movie
     *
     * 其实一个电影属于多个类型，每次修改、添加电影都需要前端给后端传输一个包含类型ID的数组和一个包含类型名称的数组
     *
     */
    @Override
    public void modifyMovieMess(Movie movie) {
        // 修改电影信息前先判断电影类型是否需要修改
        /*if (movieSortRelationDao.selectMovieSortRelation(movie.getId(),typeId)==null){
            // 如果根据电影ID未查询到关联信息，则表示类型发生改变
            // 1.删除当前关联信息
            //movieSortRelationDao.deleteMovieSortRelation();
            // 2.存入信息的关联信息
            MovieSortRelation movieSortRelation = new MovieSortRelation();
            movieSortRelation.setId(UUIDUtil.getUUID());
            movieSortRelation.setMovieId(movie.getId());
            movieSortRelation.setSortId(typeId);
            movieSortRelationDao.insertMovieSortRelation(movieSortRelation);
        }*/
        // 如果查询到关联信息，则不对关联表操作
        // 更新电影表中的信息
        movieDao.updateMovie(movie);
    }

    /**
     * 根据指定字段查询所有电影信息，字段可为空
     * @param movie
     * @return
     */
    @Override
    public List<MovieVo> queryMovieGetMovieVo(Movie movie) {
        String[] typeName = {};
        String typeStr = "";
        //MovieVo movieVo = new MovieVo();
        List<MovieVo> movieVoList = new ArrayList<>();
        // 1.首先根据字段查询电影信息
        List<Movie> movieList = movieDao.selectMovieByMore(movie);
        // 2.之后对查询出的电影type字段进行分割，存入到数组中
        for (int i = 0; i < movieList.size(); i++) {
            MovieVo movieVo = new MovieVo();                // 查询电影时会出现全部数据都相同，解决方法：每次封装MovieVo都需要重新实例化
            Map<String,String> typeMap = new HashMap<>();   // 查询电影时会出现typeMap的数据全部都相同，解决方法：每次封装typeMap都需要重新实例化
            typeStr = movieList.get(i).getType();
            // 使用空格分割movie表中的type
            typeName = typeStr.split(" ");
            // 根据movieId查询对应的所有类型
            /*List<MovieSortRelation> movieSortRelationList =
                    movieSortRelationDao.selectMSRByMovieId(movieList.get(i).getId());
            for (int j = 0; j < movieSortRelationList.size(); j++) {
                typeNameList.add(j,movieSortRelationList.get(j).getSortId());
            }*/
            // 根据分割的类型名称，查询对应的ID，放入到map中
            Sort sort;
            for (int j = 0; j < typeName.length; j++) {
                sort = sortDao.selectSortByStrName(typeName[j]);
                typeMap.put(typeName[j], sort.getId());
            }
            // 封装MovieVo实体类
            movieVo.setId(movieList.get(i).getId());
            movieVo.setMovieName(movieList.get(i).getMovieName());
            movieVo.setDirector(movieList.get(i).getDirector());
            movieVo.setScreenwriter(movieList.get(i).getScreenwriter());
            movieVo.setActor(movieList.get(i).getActor());
            //movieVo.setTypeId(typeId);
            //movieVo.setTypeName(typeName);
            movieVo.setTypeMap(typeMap);
            movieVo.setCountry(movieList.get(i).getCountry());
            movieVo.setLanguage(movieList.get(i).getLanguage());
            movieVo.setReleaseDate(movieList.get(i).getReleaseDate());
            movieVo.setDuration(movieList.get(i).getDuration());
            movieVo.setPosterServerName(movieList.get(i).getPoster());
            movieVo.setSynopsis(movieList.get(i).getSynopsis());
            movieVo.setDescription(movieList.get(i).getDescription());
            movieVo.setMovieServerName(movieList.get(i).getMovieServerName());
            // 将封装好的vo放到list当中，为前端传输数据
            movieVoList.add(i,movieVo);
        }
        return movieVoList;
    }
}
