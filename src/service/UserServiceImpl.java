package service;

import dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pojo.User;

import java.sql.SQLException;
import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    public UserDao userDao;

    @Override
    public User login(String userCode, String password) {
        return userDao.login(userCode,password);
    }

    @Override
    public List<User> getUser(String userCode) {
        try {
            return userDao.getUser(userCode);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean adduser(User user) {
        if(userDao.adduser(user)>0){
            return true;
        }
        return false;
    }
}
