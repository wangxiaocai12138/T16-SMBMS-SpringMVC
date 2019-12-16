package service;

import pojo.User;

import java.util.List;

public interface UserService {
    /*登录*/
    User login(String userCode, String password);
    /*首页显示*/
    List<User> getUser(String userCode);
    /*添加*/
    boolean adduser(User user);
}
