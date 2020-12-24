package cn.krain.web.Controller;

import cn.krain.entity.Role;
import cn.krain.result.ResultUtil;
import cn.krain.security.RedisUtil;
import cn.krain.security.TokenUtil;
import cn.krain.service.RoleService;
import cn.krain.util.UUIDUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * @author CC
 * @data 2020/12/16 - 10:29
 */
@Controller
@ResponseBody
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

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
        String menuPath = jsonObject.getString("menu");
        String roleName = jsonObject.getString("roleName");
        Role role = new Role();
        role.setRoleName(roleName);
        role.setMenu(menuPath);
        List<Role> list = roleService.queryRole(role);
        // 将list中所有role的menu值，转化为一个数组，再重新加入到list中
        String[] menus = {};
        // 遍历list
        for (int i = 0; i < list.size(); i++) {
            // list.get(i)获取每个role的menu
            String menuString = list.get(i).getMenu();
            // 以字符" "（空格）分割字符串menu，放入到menus中
            menus = menuString.split(" ");
            // 将新的menus放入到对应的role当中
            list.get(i).setMenus(menus);
        }
        JSONObject object = new JSONObject();
        object.put("roleList",list);
        return ResultUtil.success("查询角色成功",object);
    }

    /**
     * 处理删除角色请求
     * @param json
     * @param request
     * @return
     */
    @PostMapping("/delete")
    public Object doDeleteRoleById(@RequestBody String json, HttpServletRequest request){
        // 首先验证token
        String token = request.getHeader("token");
        /*try {
            tokenUtil.authToken(token);
        }catch (Exception e){
            return ResultUtil.error("无效的Token");
        }*/
        JSONObject jsonObject = JSONObject.parseObject(json);
        String id = jsonObject.getString("roleId");
        roleService.delRoleById(id);
        return ResultUtil.success("角色删除成功");
    }

    /**
     * 处理新增角色请求
     * @param json
     * @param request
     * @return
     */
    @PostMapping("/add")
    public Object doAddRole(@RequestBody String json, HttpServletRequest request){
        // 首先验证token
        String token = request.getHeader("token");
        /*try {
            tokenUtil.authToken(token);
        }catch (Exception e){
            // token不通过时返回给前端相应的数据
            return ResultUtil.error("无效的Token");
        }*/
        JSONObject jsonObject = JSONObject.parseObject(json);
        String roleName = jsonObject.getString("roleName");
        JSONArray menus = (JSONArray) jsonObject.get("menu");
        List<String> menuList = menus.parseArray(menus.toString(),String.class);
        String menu = menuList.get(0);
        for (int i = 1; i < menuList.size(); i++) {
            // 使用字符分割路径
            menu = menu + " " + menuList.get(i);
        }
        System.out.println("新增角色模块："+menu);
        Role role = new Role();
        role.setId(UUIDUtil.getUUID());
        role.setRoleName(roleName);
        role.setMenu(menu);
        role.setCreateTime(String.valueOf(System.currentTimeMillis()));
        roleService.addRole(role);
        return ResultUtil.success("用户添加成功");
    }

    /**
     * 处理修改角色请求
     * @param json
     * @param request
     * @return
     */
    @PostMapping("/modify")
    public Object doModifyRole(@RequestBody String json, HttpServletRequest request){
        // 首先验证token
        String token = request.getHeader("token");
        /*try {
            tokenUtil.authToken(token);
        }catch (Exception e){
            // token不通过时返回给前端相应的数据
            return ResultUtil.error("无效的Token");
        }*/
        JSONObject jsonObject = JSONObject.parseObject(json);
        String roleName = jsonObject.getString("roleName");
        String id = jsonObject.getString("roleId");
        JSONArray menus = (JSONArray) jsonObject.get("menu");
        List<String> menuList = menus.parseArray(menus.toString(),String.class);
        String menu = menuList.get(0);
        for (int i = 1; i < menuList.size(); i++) {
            // 使用字符分割路径
            menu = menu + " " + menuList.get(i);
        }
        System.out.println("修改角色模块："+menu);
        Role role = new Role();
        role.setId(id);
        role.setRoleName(roleName);
        role.setMenu(menu);
        roleService.modifyRole(role);
        return ResultUtil.success("角色修改成功");
    }

}
