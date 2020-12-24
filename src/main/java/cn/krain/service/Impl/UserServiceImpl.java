package cn.krain.service.Impl;

import cn.krain.dao.UserDao;
import cn.krain.entity.User;
import cn.krain.exception.LoginException;
import cn.krain.service.UserService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author CC
 * @data 2020/12/15 - 15:08
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    /**
     * @SneakyThrows：该注解用于相处 try catch 块代码
     * @param map
     * @return
     */
    @SneakyThrows
    @Override
    public User userLogin(Map<String, String> map) {
        User user = userDao.selectUserLogin(map);
        // 如果查询结果为空，则返回对应错误
        if (user==null){
            throw new LoginException("用户名或密码错误");
        }
        return user;
    }

    @Override
    public List<User> queryUser(String username, String roleName) {
        return userDao.selectAllUser(username,roleName);
    }

    @Override
    public void delUserById(String id) {
        userDao.deleteUserById(id);
    }

    @Override
    public void addUser(User user) {
        userDao.insertUser(user);
    }

    @Override
    public void modifyUser(User user) {
        userDao.updateUser(user);
    }
}
