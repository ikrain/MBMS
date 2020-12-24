package cn.krain.web.Controller;

import cn.krain.entity.Sort;
import cn.krain.result.ResultUtil;
import cn.krain.security.RedisUtil;
import cn.krain.security.TokenUtil;
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
 *
 */
@Controller
@ResponseBody
@RequestMapping("/sort")
public class SortController {

    @Autowired
    private SortService sortService;

    @Autowired
    private TokenUtil tokenUtil;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 处理查询role请求
     * @param json
     * @param request
     * @return
     */
    @PostMapping("/query")
    public Object doQueryRole(@RequestBody String json, HttpServletRequest request){
        // 首先验证token
        String token = request.getHeader("token");
        /*try {
            tokenUtil.authToken(token);
        }catch (Exception e){
            return ResultUtil.error("无效的Token");
        }*/
        JSONObject jsonObject = JSONObject.parseObject(json);
        String sortName = jsonObject.getString("sortName");
        Sort sort = new Sort();
        sort.setSort(sortName);
        List<Sort> list = sortService.querySort(sort);
        JSONObject object = new JSONObject();
        object.put("sortList",list);
        return ResultUtil.success("电影类型查询成功",object);
    }

    /**
     * 处理删除角色请求
     * @param json
     * @param request
     * @return
     */
    @PostMapping("/delete")
    public Object doDeleteSortById(@RequestBody String json, HttpServletRequest request){
        // 首先验证token
        String token = request.getHeader("token");
        /*try {
            tokenUtil.authToken(token);
        }catch (Exception e){
            return ResultUtil.error("无效的Token");
        }*/
        JSONObject jsonObject = JSONObject.parseObject(json);
        String id = jsonObject.getString("sortId");
        sortService.delSortById(id);
        return ResultUtil.success("电影类型删除成功");
    }

    /**
     * 处理新增角色请求
     * @param json
     * @param request
     * @return
     */
    @PostMapping("/add")
    public Object doAddSort(@RequestBody String json, HttpServletRequest request){
        // 首先验证token
        String token = request.getHeader("token");
        /*try {
            tokenUtil.authToken(token);
        }catch (Exception e){
            // token不通过时返回给前端相应的数据
            return ResultUtil.error("无效的Token");
        }*/
        JSONObject jsonObject = JSONObject.parseObject(json);
        String sortName = jsonObject.getString("sortName");
        Sort sort = new Sort();
        sort.setId(UUIDUtil.getUUID());
        sort.setSort(sortName);
        sortService.addSort(sort);
        return ResultUtil.success("电影类型添加成功");
    }

     /**
     * 处理修改角色请求
     * @param json
     * @param request
     * @return
     */
    @PostMapping("/modify")
    public Object doModifySort(@RequestBody String json, HttpServletRequest request){
        // 首先验证token
        String token = request.getHeader("token");
        /*try {
            tokenUtil.authToken(token);
        }catch (Exception e){
            // token不通过时返回给前端相应的数据
            return ResultUtil.error("无效的Token");
        }*/
        JSONObject jsonObject = JSONObject.parseObject(json);
        String sortName = jsonObject.getString("sortName");
        String id = jsonObject.getString("sortId");
        Sort sort = new Sort();
        sort.setId(id);
        sort.setSort(sortName);
        sortService.modifySort(sort);
        return ResultUtil.success("电影类型修改成功");
    }

}
