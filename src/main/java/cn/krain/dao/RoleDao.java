package cn.krain.dao;

import cn.krain.entity.Role;

import java.util.List;

/**
 * @author CC
 * @data 2020/12/16 - 19:23
 */
public interface RoleDao {
    List<Role> selectRole(Role role);

    Role selectRoleById(String id);

    void deleteRoleById(String id);

    void insertRole(Role role);

    void updateRole(Role role);
}
