package com.cao.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.cao.POJO.Permission;
import com.cao.POJO.Role;
import com.cao.POJO.User;
import com.cao.dao.PermissionDao;
import com.cao.dao.RoleDao;
import com.cao.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service(interfaceClass = UserService.class)
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private PermissionDao permissionDao;

    @Override
    public User findByUsername(String username) {
        //根据username查询
        User user = userDao.findByUsername(username);
        if (user != null) {
            Integer userId = user.getId();
            //根据userId查询roles
            Set<Role> roles = roleDao.findByUserId(userId);
            if (roles != null && roles.size() > 0) {
                for (Role role : roles) {
                    Integer roleId = role.getId();
                    //根据roleId查询permissions
                    Set<Permission> permissions = permissionDao.findByRoleId(roleId);
                    if (permissions != null && permissions.size() > 0) {
                        //将查询到的permissions给对应的role
                        role.setPermissions(permissions);
                    }
                }
                //将储存有permission的roles给user
                user.setRoles(roles);
            }
            return user;
        }
        return null;
    }
}
