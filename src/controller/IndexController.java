package controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//controller控制器层  ,实现Controller控制器需要继承AbstractController
@Controller
@RequestMapping("/index")
public class IndexController /*extends AbstractController*/ {
    //实现方法
   /* protected ModelAndView handleRequestInternal(HttpServletRequest request,
                                                 HttpServletResponse response) throws Exception {
        System.out.println("hello！SpringMVC~~~");
        *//*返回逻辑视图名*//*
        return new ModelAndView("index");
    }*/

    @RequestMapping("/AAAA")//这个是我们访问的路径
    public ModelAndView index(){
        System.out.println("欢迎来到controller层");
        return new ModelAndView("index");//这个是我们的逻辑视图名
    }
    @RequestMapping("/AAAA2")//这个是我们访问的路径
    public String index2(){
        System.out.println("欢迎来到controller层");
        return "index";//这个是我们的逻辑视图名
    }
    /*路径传参*/
    @RequestMapping("/index")//这个是我们访问的路径
    public String index3(@RequestParam("userName") Integer name){
        System.out.println("欢迎来到controller层,我是"+name);
        return "index";//这个是我们的逻辑视图名
    }
    /*路径传参*/
    @RequestMapping("/index2")//这个是我们访问的路径
    public String index4(@RequestParam(value = "userName",required = false,defaultValue = "0") Integer name){
        System.out.println("欢迎来到controller层,我是"+name);
        return "index";//这个是我们的逻辑视图名
    }
    /*获取对象传到前台页面,方式一*/
    @RequestMapping("/index5")//这个是我们访问的路径
    public ModelAndView index5(@RequestParam String userName){
        System.out.println("欢迎来到controller层,我是"+userName);
        ModelAndView mav=new ModelAndView();
        //存值，类似与request的setAttribute方法
        mav.addObject("userName",userName);
        //设置逻辑视图名
        mav.setViewName("index");//这个是我们的逻辑视图名
        return mav;
    }
    /*获取对象传到前台页面,方式二*/
    @RequestMapping("/index6")//这个是我们访问的路径
    public String index6(@RequestParam String userName, Model model){
        System.out.println("欢迎来到controller层,我是"+userName);
        model.addAttribute(userName);
        return "index";
    }
}
