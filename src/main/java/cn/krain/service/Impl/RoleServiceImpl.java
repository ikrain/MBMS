package cn.krain.service.Impl;

import cn.krain.dao.RoleDao;
import cn.krain.entity.Role;
import cn.krain.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author CC
 * @data 2020/12/16 - 19:30
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;

    @Override
    public List<Role> queryRole(Role role) {
        return roleDao.selectRole(role);
    }

    @Override
    public void delRoleById(String id) {
        roleDao.deleteRoleById(id);
    }

    @Override
    public void addRole(Role role) {
        roleDao.insertRole(role);
    }

    @Override
    public void modifyRole(Role role) {
        roleDao.updateRole(role);
    }

    @Override
    public Role queryRoleById(String roleId) {
        return roleDao.selectRoleById(roleId);
    }
}
