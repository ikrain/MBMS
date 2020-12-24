package cn.krain.service;

import cn.krain.entity.User;

import java.util.List;
import java.util.Map;

/**
 * @author CC
 * @data 2020/12/15 - 10:50
 */
public interface UserService {
    /**
     * 用户登录查询接口
     * @param map
     * @return
     */
    User userLogin(Map<String, String> map);

    /**
     * 查询系统用户（查询条件不定，可能存在条件，也可能没有）
     * @return
     */
    List<User> queryUser(String username, String roleName);

    /**
     * 根据ID删除用户
     * @param id
     */
    void delUserById(String id);

    /**
     * 增加新的用户
     * @param user
     */
    void addUser(User user);

    /**
     * 修改用户信息
     * @param user
     */
    void modifyUser(User user);
}
