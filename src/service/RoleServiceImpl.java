package service;

import dao.RoleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pojo.Role;

import java.sql.SQLException;
import java.util.List;
@Service("roleService")
public class RoleServiceImpl implements RoleService  {
    @Autowired
    RoleDao roleDao;
    @Override
    public List<Role> getRole() {
        try {
            return roleDao.getRole();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
