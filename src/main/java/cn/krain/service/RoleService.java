package cn.krain.service;

import cn.krain.entity.Role;

import java.util.List;

/**
 * @author CC
 * @data 2020/12/16 - 19:23
 */
public interface RoleService {

    List<Role> queryRole(Role role);

    void delRoleById(String id);

    void addRole(Role role);

    void modifyRole(Role role);

    Role queryRoleById(String roleId);
}
