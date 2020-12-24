package cn.krain.web.Controller;

import cn.krain.entity.Role;
import cn.krain.entity.User;
import cn.krain.result.ResultUtil;
import cn.krain.security.RedisUtil;
import cn.krain.security.TokenUtil;
import cn.krain.service.RoleService;
import cn.krain.service.UserService;
import cn.krain.util.MD5Util;
import cn.krain.util.RedisTemplateInit;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


/**
 * @author CC
 * @data 2020/12/15 - 10:50
 */
@Controller
@RequestMapping("/main")
public class MainController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private TokenUtil tokenUtil;

    @Autowired
    private RedisUtil redisUtil;

    @ResponseBody
    @PostMapping(value = "/login")
    public Object userLogin(@RequestBody String json){
        // 解析前端提供的json数据
        JSONObject jsonObject = JSONObject.parseObject(json);
        String logUser = jsonObject.getString("userName");
        String passWord = jsonObject.getString("passWord");
        // 将用户名密码转换为map传参
        Map<String, String> map = new HashMap<>();
        map.put("logUser",logUser);
        map.put("md5Pw", MD5Util.getMD5(passWord));
        try {
            User user = userService.userLogin(map);
            // 将token和对应的user加入redis
            String token = tokenUtil.getToKen();
            System.out.println(token);
            redisUtil.addKey(token,user);
            // 根据roleId查询roleName
            Role role = roleService.queryRoleById(user.getRoleId());
            // 封装json数据，发送给前端
            JSONObject object = new JSONObject();
            object.put("id",user.getId());
            object.put("userName",user.getUsername());
            object.put("roleId",user.getRoleId());
            object.put("roleName",role.getRoleName());
            object.put("token",token);
            return ResultUtil.success("登录成功",object);
        }catch (Exception e){
            e.printStackTrace();
            // 登录失败返回数据
            return ResultUtil.error("用户名或密码错误");
        }
    }

    @ResponseBody
    @PostMapping(value = "/logout")
    public Object userLogout(@RequestBody String json, HttpServletRequest request){
        // 解析前端提供的json数据
        JSONObject jsonObject = JSONObject.parseObject(json);
        String outUser = jsonObject.getString("id");
        String outToken = request.getHeader("token");
        System.out.println(outToken);
        // 用户退出后删除redis中的token
        redisUtil.deleteKey(outToken);
        return ResultUtil.success("用户退出成功");
    }

    /**
     * 测试方法
     * @return
     */
    @ResponseBody
    @GetMapping("/test")
    public Object query(){
        JSONObject object = new JSONObject();
        object.put("id","1");
        object.put("userName","cc");
        object.put("roleId","2");
        return ResultUtil.success("查询成功",object);
    }

}
