package dao;
import org.springframework.stereotype.Repository;
import pojo.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@Repository("userDao")
public class UserDaoImpl extends BaseDao implements UserDao{
    @Override
    public User login(String userCode, String password) {
        String sql="select * from smbms_user" +
                " where userCode=? and userPassword=? ";
        Object [] obj=new Object[]{userCode,password};
        ResultSet rs= super.executeQuery(sql,obj);
        User user=new User();
        try {
            if(rs.next()){
                user.setId(rs.getInt("id"));
                user.setUserName(rs.getString("userName"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<User> getUser(String userCode) throws SQLException {
        String sql="select * from smbms_user ";
        Object [] obj=null;
        if(userCode!=null&& userCode!=""){
            sql += "  where userCode=? ";
            obj=new Object[]{userCode};
        }
        System.out.println("----------"+sql);
        List<User> users=new ArrayList<>();
        ResultSet rs=super.executeQuery(sql,obj);
        while (rs.next()) {
            User user =new User();
            user.setId(rs.getInt("id"));
            user.setUserCode(rs.getString("userCode"));
            user.setUserName(rs.getString("userName"));
            user.setUserPassword(rs.getString("userPassword"));
            user.setGender(rs.getInt("gender"));
            user.setBirthday(rs.getDate("birthday"));
            user.setPhone(rs.getString("phone"));
            user.setAddress(rs.getString("address"));
            user.setUserRole(rs.getInt("userRole"));
            users.add(user);
        }
        return users;
    }

    @Override
    public Integer adduser(User user) {
        String sql="INSERT INTO smbms_user(userCode,userName," +
                "userPassword,gender," +
                "birthday,phone,address," +
                "createdBy,creationDate,userRole,idPicPath) values (?,?,?,?,?,?,?,?,?,?,?)";
        Object[] obj=new Object[]{user.getUserCode(),user.getUserName(),user.getUserPassword(),
                        user.getGender(),user.getBirthday(),user.getPhone(),user.getAddress(),
                        user.getCreatedBy(),user.getCreationDate(),user.getUserRole(),user.getIdPicPath()};
        Integer rows=super.executeUpdate(sql,obj);
        return rows;
    }
}
