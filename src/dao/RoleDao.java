package dao;

import pojo.Role;

import java.sql.SQLException;
import java.util.List;

public interface RoleDao {
    /*查询角色列表*/
    List<Role> getRole() throws SQLException;
}
