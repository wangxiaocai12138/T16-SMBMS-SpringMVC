package dao;

import pojo.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDao {
    /*登录*/
    User login(String userCode,String password);
    /*用户首页显示*/
    List<User> getUser(String userCode) throws SQLException;
    /*添加*/
    Integer adduser(User user);
}
