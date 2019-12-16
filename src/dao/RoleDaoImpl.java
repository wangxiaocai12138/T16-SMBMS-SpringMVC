package dao;

import org.springframework.stereotype.Repository;
import pojo.Role;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository("roleDao")
public class RoleDaoImpl extends BaseDao implements RoleDao {
    @Override
    public List<Role> getRole() throws SQLException {
        String sql="select * from smbms_role";
        List<Role> roles=new ArrayList<>();
        ResultSet rs = super.executeQuery(sql,null);
        while(rs.next()){
            Role _role = new Role();
            _role.setId(rs.getInt("id"));
            _role.setRoleCode(rs.getString("roleCode"));
            _role.setRoleName(rs.getString("roleName"));
            roles.add(_role);
        }
        return roles;
    }
}
