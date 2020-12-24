package cn.krain.web.Controller;

import cn.krain.entity.Comment;
import cn.krain.result.ResultUtil;
import cn.krain.security.RedisUtil;
import cn.krain.security.TokenUtil;
import cn.krain.service.CommentService;
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
 */
@Controller
@ResponseBody
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private TokenUtil tokenUtil;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 处理查询comment请求
     * @param json
     * @param request
     * @return
     */
    @PostMapping("/query")
    public Object doQueryComment(@RequestBody String json, HttpServletRequest request){
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
        List<Comment> commentList = commentService.queryCommentByMoviename(movieName);
        object.put("commentList",commentList);
        return ResultUtil.success("影评查询成功",object);
    }

    /**
     * 处理删除影评请求
     * @param json
     * @param request
     * @return
     */
    @PostMapping("/delete")
    public Object doDeleteCommentById(@RequestBody String json, HttpServletRequest request){
        // 首先验证token
        String token = request.getHeader("token");
        /*try {
            tokenUtil.authToken(token);
        }catch (Exception e){
            return ResultUtil.error("无效的Token");
        }*/
        JSONObject jsonObject = JSONObject.parseObject(json);
        String id = jsonObject.getString("id");
        // 执行删除影评的业务方法，在业务方法内首先删除关联关系、然后再删除影评
        commentService.delCommentById(id);
        return ResultUtil.success("影评删除成功");
    }

    /**
     * 处理新增新闻请求
     * @param json
     * @param request
     * @return
     */
    @PostMapping("/add")
    public Object doAddComment(@RequestBody String json, HttpServletRequest request){
        // 首先验证token
        String token = request.getHeader("token");
        /*try {
            tokenUtil.authToken(token);
        }catch (Exception e){
            // token不通过时返回给前端相应的数据
            return ResultUtil.error("无效的Token");
        }*/
        JSONObject jsonObject = JSONObject.parseObject(json);
        String author = jsonObject.getString("author");
        String content = jsonObject.getString("content");
        String time = jsonObject.getString("time");
        String movieId = jsonObject.getString("movieId");
        Comment comment = new Comment();
        String commentId = UUIDUtil.getUUID();
        comment.setId(commentId);
        comment.setAuthor(author);
        comment.setContent(content);
        comment.setTime(time);
        // 为指定的movieId添加影评，添加影评前添加关联数据
        commentService.addCommentForMovie(comment,movieId);
        return ResultUtil.success("影评添加成功");
    }

     /**
     * 处理修改影评请求
     * @param json
     * @param request
     * @return
     */
    @PostMapping("/modify")
    public Object doModifyComment(@RequestBody String json, HttpServletRequest request){
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
        String author = jsonObject.getString("author");
        String content = jsonObject.getString("content");
        String time = jsonObject.getString("time");
        Comment comment = new Comment();
        comment.setId(id);
        comment.setAuthor(author);
        comment.setContent(content);
        comment.setTime(time);
        commentService.modifyComment(comment);
        return ResultUtil.success("电影新闻修改成功");
    }
}
