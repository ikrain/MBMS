package cn.krain.web.Controller;

import cn.krain.entity.News;
import cn.krain.result.ResultUtil;
import cn.krain.security.RedisUtil;
import cn.krain.security.TokenUtil;
import cn.krain.service.NewsService;
import cn.krain.service.SortService;
import cn.krain.util.UUIDUtil;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * @author CC
 * @data 2020/12/16 - 10:29
 *
 * 后期需要加入对movie_sort_relation的相关操作
 */
@Controller
@ResponseBody
@RequestMapping("/news")
public class NewsController {

    @Autowired
    private NewsService newsService;

    @Autowired
    private TokenUtil tokenUtil;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 处理查询news请求
     * @param json
     * @param request
     * @return
     */
    @PostMapping("/query")
    public Object doQueryNews(@RequestBody String json, HttpServletRequest request){
        // 首先验证token
        String token = request.getHeader("token");
        /*try {
            tokenUtil.authToken(token);
        }catch (Exception e){
            return ResultUtil.error("无效的Token");
        }*/
        JSONObject jsonObject = JSONObject.parseObject(json);
        String movieName = jsonObject.getString("movieName");
        JSONObject object = new JSONObject();
        List<News> newsList = newsService.queryNewsByMovieName(movieName);
        object.put("newsList",newsList);
        return ResultUtil.success("电影新闻查询成功",object);
    }

    /**
     * 处理删除新闻请求
     * @param json
     * @param request
     * @return
     */
    @PostMapping("/delete")
    public Object doDeleteNewsById(@RequestBody String json, HttpServletRequest request){
        // 首先验证token
        String token = request.getHeader("token");
        /*try {
            tokenUtil.authToken(token);
        }catch (Exception e){
            return ResultUtil.error("无效的Token");
        }*/
        JSONObject jsonObject = JSONObject.parseObject(json);
        String id = jsonObject.getString("id");
        // 执行删除新闻的业务方法，在业务方法内首先删除关联关系、然后再删除新闻
        newsService.delNewsById(id);
        return ResultUtil.success("电影新闻删除成功");
    }

    /**
     * 处理新增新闻请求
     * @param json
     * @param request
     * @return
     */
    @PostMapping("/add")
    public Object doAddNews(@RequestBody String json, HttpServletRequest request){
        // 首先验证token
        String token = request.getHeader("token");
        /*try {
            tokenUtil.authToken(token);
        }catch (Exception e){
            // token不通过时返回给前端相应的数据
            return ResultUtil.error("无效的Token");
        }*/
        JSONObject jsonObject = JSONObject.parseObject(json);
        String movieId = jsonObject.getString("movieId");
        String name = jsonObject.getString("name");
        String content = jsonObject.getString("content");
        String type = jsonObject.getString("type");
        String createTime = jsonObject.getString("createTime");
        News news = new News();
        String newsId = UUIDUtil.getUUID();
        news.setId(newsId);
        news.setName(name);
        news.setContent(content);
        news.setType(type);
        news.setCreateTime(createTime);
        // 为指定的movieId添加新闻，添加新闻前添加关联数据
        newsService.addNews(news,movieId);
        return ResultUtil.success("电影新闻添加成功");
    }

     /**
     * 处理修改新闻请求
     * @param json
     * @param request
     * @return
     */
    @PostMapping("/modify")
    public Object doModifyNews(@RequestBody String json, HttpServletRequest request){
        // 首先验证token
        String token = request.getHeader("token");
        /*try {
            tokenUtil.authToken(token);
        }catch (Exception e){
            // token不通过时返回给前端相应的数据
            return ResultUtil.error("无效的Token");
        }*/
        JSONObject jsonObject = JSONObject.parseObject(json);
        String id = jsonObject.getString("id");
        String name = jsonObject.getString("name");
        String content = jsonObject.getString("content");
        String type = jsonObject.getString("type");
        String createTime = jsonObject.getString("createTime");
        News news = new News();
        news.setId(id);
        news.setName(name);
        news.setContent(content);
        news.setType(type);
        news.setCreateTime(createTime);
        newsService.modifyNews(news);
        return ResultUtil.success("电影新闻修改成功");
    }

}
