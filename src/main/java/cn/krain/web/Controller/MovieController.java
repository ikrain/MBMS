package cn.krain.web.Controller;

import cn.krain.dao.MovieSortRelationDao;
import cn.krain.entity.Movie;
import cn.krain.entity.MovieSortRelation;
import cn.krain.result.ResultUtil;
import cn.krain.security.TokenUtil;
import cn.krain.service.CommentService;
import cn.krain.service.MovieService;
import cn.krain.service.NewsService;
import cn.krain.util.NonStaticResourceHttpRequestHandler;
import cn.krain.util.UUIDUtil;
import cn.krain.vo.MovieVo;
import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author CC
 * @data 2020/12/17 - 16:31
 */
@Controller
@ResponseBody
@RequestMapping("/movie")
@AllArgsConstructor
public class MovieController {

    @Autowired
    private MovieService movieService;

    @Autowired
    private MovieSortRelationDao movieSortRelationDao;

    @Autowired
    private NewsService newsService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private TokenUtil tokenUtil;

    private final NonStaticResourceHttpRequestHandler nonStaticResourceHttpRequestHandler;

    /**
     * 前端访问服务器视频
     *
     * 该功能借鉴于：https://blog.csdn.net/weixin_38759449/article/details/104496464
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @GetMapping("/video")
    public void videoPreview(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String fileName = request.getParameter("fileName");
        System.out.println("movieName:"+fileName);
        //假如我把视频1.mp4放在了static下的video文件夹里面
        //sourcePath 是获取resources文件夹的绝对地址
        //realPath 即是视频所在的磁盘地址
        String sourcePath = ClassUtils.getDefaultClassLoader().getResource("").getPath().substring(1);
        //String realPath = sourcePath +"static/"+movieName+".mp4";
        // 自定义访问路径
        String realPath = "E:/MBMS_FileServer/"+fileName;
        Path filePath = Paths.get(realPath);
        if (Files.exists(filePath)) {
            String mimeType = Files.probeContentType(filePath);
            if (!StringUtils.isEmpty(mimeType)) {
                response.setContentType(mimeType);
            }
            request.setAttribute(NonStaticResourceHttpRequestHandler.ATTR_FILE, filePath);
            nonStaticResourceHttpRequestHandler.handleRequest(request, response);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        }
    }

    /**
     * 添加电影：
     *      首先添加电影信息
     *      其次向电影与类型关联表中添加关联
     *      之后上传电影文件到指定服务器
     *  需修改：
     *      关联关系表中的sortId需换成sort表中对应的ID
     *
     * @param file
     * @param request
     * @return
     */
    @PostMapping("/upload")
    public Object uploadMovie(@RequestParam("file") MultipartFile file, HttpServletRequest request){
        String token = request.getHeader("token");
        /*try {
            tokenUtil.authToken(token);
        }catch (Exception e){
            return ResultUtil.error("无效的Token");
        }*/
        // 将数据库中电影信息的id传给文件上传方法，从而使用该ID对电影文件进行命名
        String movieName = String.valueOf(System.currentTimeMillis()/1000);
        return movieService.uploadFile(file,movieName);   // 上传电影源文件
    }

    @PostMapping("/addMovieMess")
    public Object uploadMovieMessage(/*@RequestParam("file") MultipartFile file,
                              String movieName, String director, String screenwriter,
                              String actor, String typeId, String typeName ,
                              String country, String language, String releaseDate,
                              String duration, String poster, String synopsis,
                              String description, String moviePath,*/
                              @RequestBody String json,
                              HttpServletRequest request){
        String token = request.getHeader("token");
        /*try {
            tokenUtil.authToken(token);
        }catch (Exception e){
            return ResultUtil.error("无效的Token");
        }*/
        JSONObject jsonObject = JSONObject.parseObject(json);
        String movieName = jsonObject.getString("movieName");
        String director = jsonObject.getString("director");
        String screenwriter = jsonObject.getString("screenwriter");
        String actor = jsonObject.getString("actor");
        //String typeId = jsonObject.getString("typeId");
        //String typeName = jsonObject.getString("typeName");
        Map<String, String> typeMap = (Map<String, String>) jsonObject.get("typeMap");
        String country = jsonObject.getString("country");
        String language = jsonObject.getString("language");
        String releaseDate = jsonObject.getString("releaseDate");
        String duration = jsonObject.getString("duration");
        String poster = jsonObject.getString("posterServerName");
        String synopsis = jsonObject.getString("synopsis");
        String description = jsonObject.getString("description");
        String movieServerName = jsonObject.getString("movieServerName");
        Movie movie = new Movie();
        String movieId = UUIDUtil.getUUID();
        movie.setId(movieId);
        movie.setMovieName(movieName);
        movie.setDirector(director);
        movie.setScreenwriter(screenwriter);
        movie.setActor(actor);
        //movie.setType(typeName);    // 向数据库movie表中添加类型名称
        movie.setCountry(country);
        movie.setLanguage(language);
        movie.setReleaseDate(releaseDate);
        movie.setDuration(duration);
        movie.setPoster(poster);
        movie.setSynopsis(synopsis);
        movie.setDescription(description);
        // 为视频文件获取新的文件路径，并放入到movie实体类中
        movie.setMovieServerName(movieServerName);
        // 遍历typeMap，拼接字符串并循环向电影与类别关联表中添加关联
        Iterator<Map.Entry<String,String>> iterator = typeMap.entrySet().iterator();
        String typeNameStr = "";
        while (iterator.hasNext()){
            Map.Entry entry = iterator.next();
            typeNameStr += entry.getKey();
            if (iterator.hasNext()){
                typeNameStr += " ";
            }
            MovieSortRelation movieSortRelation = new MovieSortRelation();
            movieSortRelation.setId(UUIDUtil.getUUID());
            movieSortRelation.setSortId((String) entry.getValue());        // 向数据库MovieSortRelation表中添加类型Id
            movieSortRelation.setMovieId(movieId);      // 向数据库MovieSortRelation表中添加电影ID
            movieSortRelationDao.insertMovieSortRelation(movieSortRelation);
        }
        movie.setType(typeNameStr);
        // 添加电影信息
        movieService.addMovieMess(movie);
        return ResultUtil.success("电影上传成功");
    }



    /**
     * 删除电影，删除电影前要先删除与分类、新闻、影评的管理关系
     * @param json
     * @param request
     * @return
     */
    @PostMapping("/deleteMovieFile")
    public Object doDelMovieFile(@RequestBody String json, HttpServletRequest request){
        // 首先验证token
        String token = request.getHeader("token");
        /*try {
            tokenUtil.authToken(token);
        }catch (Exception e){
            return ResultUtil.error("无效的Token");
        }*/
        JSONObject jsonObject = JSONObject.parseObject(json);
        String movieServerName = jsonObject.getString("movieServerName");
        // 删除电影文件，并返回结果
        String moviePath = "E:\\MBMS_FileServer\\"+movieServerName;
        // System.out.println(moviePath);
        return movieService.delMovieFile(moviePath);
    }

    /**
     *
     * @param json
     * @param request
     * @return
     */
    @PostMapping("/deleteMovieMess")
    public Object doDelMovieMess(@RequestBody String json, HttpServletRequest request){
        // 首先验证token
        String token = request.getHeader("token");
        /*try {
            tokenUtil.authToken(token);
        }catch (Exception e){
            return ResultUtil.error("无效的Token");
        }*/
        JSONObject jsonObject = JSONObject.parseObject(json);
        String id = jsonObject.getString("id");
        String posterServerName = jsonObject.getString("posterServerName");
        String movieServerName = jsonObject.getString("movieServerName");
        // 删除电影信息
        movieService.delMovie(id,posterServerName,movieServerName);
        return ResultUtil.success("电影删除成功");
    }

    /**
     * 处理修改电影请求，此处需要修改，根据前端传回的Map进行类型的修改
     * @param json
     * @param request
     * @return
     */
    @PostMapping("/modify")
    public Object doModifyMovie(@RequestBody String json, HttpServletRequest request){
        // 首先验证token
        String token = request.getHeader("token");
        /*try {
            tokenUtil.authToken(token);
        }catch (Exception e){
            // token不通过时返回给前端相应的数据
            return ResultUtil.error("无效的Token");
        }*/
        JSONObject jsonObject = JSONObject.parseObject(json);
        String movieId = jsonObject.getString("id");
        String movieName = jsonObject.getString("movieName");
        String director = jsonObject.getString("director");
        String screenwriter = jsonObject.getString("screenwriter");
        String actor = jsonObject.getString("actor");
        //String typeId = jsonObject.getString("typeId");
        //String typeName = jsonObject.getString("typeName");
        Map<String,String> typeMap = (Map<String, String>) jsonObject.get("typeMap");
        String country = jsonObject.getString("country");
        String language = jsonObject.getString("language");
        String releaseDate = jsonObject.getString("releaseDate");
        String duration = jsonObject.getString("duration");
        String poster = jsonObject.getString("posterServerName");
        String synopsis = jsonObject.getString("synopsis");
        String description = jsonObject.getString("description");
        String movieServerName = jsonObject.getString("movieServerName");
        Movie movie = new Movie();
        movie.setId(movieId);
        movie.setMovieName(movieName);
        movie.setDirector(director);
        movie.setScreenwriter(screenwriter);
        movie.setActor(actor);
        //movie.setType(typeName);
        movie.setCountry(country);
        movie.setLanguage(language);
        movie.setReleaseDate(releaseDate);
        movie.setDuration(duration);
        movie.setPoster(poster);
        movie.setSynopsis(synopsis);
        movie.setDescription(description);
        movie.setMovieServerName(movieServerName);
        // 修改电影信息时，因为未判断用户对哪条信息进行了修改，因此需要将类别关联表中的信息进行删除，然后再添加
        movieSortRelationDao.deleteMovieSortRelation(movieId);
        // 遍历typeMap，拼接字符串并循环向电影与类别关联表中添加关联
        Iterator<Map.Entry<String,String>> iterator = typeMap.entrySet().iterator();
        String typeNameStr = "";
        while (iterator.hasNext()){
            Map.Entry entry = iterator.next();
            typeNameStr += entry.getKey();
            if (iterator.hasNext()){
                typeNameStr += " ";
            }
            MovieSortRelation movieSortRelation = new MovieSortRelation();
            movieSortRelation.setId(UUIDUtil.getUUID());
            movieSortRelation.setSortId((String) entry.getValue());        // 向数据库MovieSortRelation表中添加类型Id
            movieSortRelation.setMovieId(movieId);      // 向数据库MovieSortRelation表中添加电影ID
            movieSortRelationDao.insertMovieSortRelation(movieSortRelation);
        }
        movie.setType(typeNameStr);
        // 修改电影信息
        movieService.modifyMovieMess(movie);
        return ResultUtil.success("修改电影信息成功");
    }

    /**
     * 处理查询电影请求
     * @param json
     * @param request
     * @return
     */
    @PostMapping("/query")
    public Object doQueryMovie(@RequestBody String json, HttpServletRequest request){
        // 首先验证token
        String token = request.getHeader("token");
        /*try {
            tokenUtil.authToken(token);
        }catch (Exception e){
            // token不通过时返回给前端相应的数据
            return ResultUtil.error("无效的Token");
        }*/
        JSONObject jsonObject = JSONObject.parseObject(json);
        String movieName = jsonObject.getString("movieName");
        String director = jsonObject.getString("director");
        String screenwriter = jsonObject.getString("screenwriter");
        String typeName = jsonObject.getString("typeName");
        String country = jsonObject.getString("country");
        Movie movie = new Movie();
        movie.setMovieName(movieName);              // 电影名称
        movie.setDirector(director);                // 导演
        movie.setScreenwriter(screenwriter);        // 编剧
        movie.setType(typeName);                    // 类型名称
        movie.setCountry(country);                  // 国家/地区
        List<MovieVo> list = movieService.queryMovieGetMovieVo(movie);
        JSONObject object = new JSONObject();
        object.put("movieList",list);
        return ResultUtil.success("电影查询成功",object);
    }

    @PostMapping("/test")
    public void test(Map<String, String> map){
        System.out.println(map.get("动作"));
        System.out.println(map.get("犯罪"));
        System.out.println(map);
    }
}
