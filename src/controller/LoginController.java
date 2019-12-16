package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pojo.User;
import service.UserService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    public UserService userService;

    /**
     * 进入登录页面
     * @return
     */
    @RequestMapping("/login.html")
    public String login(){
        return "login";
    }

    @RequestMapping("/dologin.html")
    public String dologin(@RequestParam String userCode, @RequestParam String userPassword,
                          HttpSession session, Model model) throws Exception {
        User user= userService.login(userCode,userPassword);
        if(user!=null){
            session.setAttribute("userSession",user);
            return "frame";
        }else{
            model.addAttribute("error","用户名或者密码错误！");
            return "login";
        }
    }

    /**
     * 注销
     * @param session
     * @return
     */
    @RequestMapping("/loginout.html")
    public String loginout(HttpSession session){
        session.removeAttribute("userSession");
        return "redirect:/login.html";
    }
    /**
     * 局部异常处理
     */
   /* @ExceptionHandler(value = {RuntimeException.class})
    public String exception(RuntimeException e, HttpServletRequest model){
        model.setAttribute("e",e);
        return "error";
    }*/
}
