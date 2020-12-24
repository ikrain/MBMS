package cn.krain.dao;

import cn.krain.entity.User;

import java.util.List;
import java.util.Map;

/**
 * @author CC
 * @data 2020/12/15 - 10:40
 */
public interface UserDao {
    User selectUserLogin(Map<String, String> map);

    //List<User> selectAllUser(Map<String, String> map);

    List<User> selectAllUser(String username, String roleName);

    void deleteUserById(String id);

    void insertUser(User user);

    void updateUser(User user);
}
