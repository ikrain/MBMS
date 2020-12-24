package cn.krain.web.Controller;

import cn.krain.entity.User;
import cn.krain.result.ResultUtil;
import cn.krain.security.RedisUtil;
import cn.krain.security.TokenUtil;
import cn.krain.service.UserService;
import cn.krain.util.MD5Util;
import cn.krain.util.UUIDUtil;
import com.alibaba.fastjson.JSONObject;
import com.cxytiandi.encrypt.springboot.annotation.Decrypt;
import com.cxytiandi.encrypt.springboot.annotation.Encrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author CC
 * @data 2020/12/16 - 10:29
 */
@Controller
@ResponseBody
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenUtil tokenUtil;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 处理查询user请求
     * @param json
     * @param request
     * @return
     */
    //@Encrypt
    //@Decrypt
    @PostMapping("/query")
    public Object queryUser(@RequestBody String json, HttpServletRequest request){
        // 首先验证token
        String token = request.getHeader("token");
        /*try {
            tokenUtil.authToken(token);
        }catch (Exception e){
            return ResultUtil.error("无效的Token");
        }*/
        JSONObject jsonObject = JSONObject.parseObject(json);
        String userName = jsonObject.getString("userName");
        String roleName = jsonObject.getString("roleName");
        Map<String, String> map = new HashMap<>();
        map.put("username", userName);
        map.put("roleName", roleName);
        List<User> list = userService.queryUser(userName,roleName);
        JSONObject object = new JSONObject();
        object.put("userList",list);
        return ResultUtil.success("查询用户成功",object);
    }

    /**
     * 处理删除请求
     * @param json
     * @param request
     * @return
     */
    @PostMapping("/delete")
    public Object deleteUserById(@RequestBody String json, HttpServletRequest request){
        // 首先验证token
        String token = request.getHeader("token");
        /*try {
            tokenUtil.authToken(token);
        }catch (Exception e){
            return ResultUtil.error("无效的Token");
        }*/
        JSONObject jsonObject = JSONObject.parseObject(json);
        String id = jsonObject.getString("id");
        userService.delUserById(id);
        return ResultUtil.success("用户删除成功");
    }

    /**
     * 处理新增用户请求
     * @param json
     * @param request
     * @return
     */
    @PostMapping("/add")
    public Object addUser(@RequestBody String json, HttpServletRequest request){
        // 首先验证token
        String token = request.getHeader("token");
        /*try {
            tokenUtil.authToken(token);
        }catch (Exception e){
            // token不通过时返回给前端相应的数据
            return ResultUtil.error("无效的Token");
        }*/
        JSONObject jsonObject = JSONObject.parseObject(json);
        String userName = jsonObject.getString("userName");
        String passWord = jsonObject.getString("passWord");
        String roleId = jsonObject.getString("roleId");
        User user = new User();
        user.setId(UUIDUtil.getUUID());
        user.setUsername(userName);
        user.setPassword(MD5Util.getMD5(passWord));
        user.setRoleId(roleId);
        user.setCreateTime(String.valueOf(System.currentTimeMillis()));
        userService.addUser(user);
        return ResultUtil.success("用户添加成功");
    }

    /**
     * 处理修改用户请求
     * @param json
     * @param request
     * @return
     */
    @PostMapping("/modify")
    public Object doModifyUser(@RequestBody String json, HttpServletRequest request){
        // 首先验证token
        String token = request.getHeader("token");
        /*try {
            tokenUtil.authToken(token);
        }catch (Exception e){
            // token不通过时返回给前端相应的数据
            return ResultUtil.error("无效的Token");
        }*/
        JSONObject jsonObject = JSONObject.parseObject(json);
        String userName = jsonObject.getString("userName");
        String id = jsonObject.getString("userId");
        String roleId = jsonObject.getString("roleId");
        User user = new User();
        user.setId(id);
        user.setUsername(userName);
        user.setRoleId(roleId);
        userService.modifyUser(user);
        return ResultUtil.success("用户修改成功");
    }

    @Encrypt
    @GetMapping("/test")
    public Object test(){
        User user = new User();
        user.setId("345");
        user.setUsername("fdg");
        return user;
    }

}
