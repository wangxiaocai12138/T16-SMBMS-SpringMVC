package controller;

import com.alibaba.fastjson.JSONArray;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import pojo.Role;
import pojo.User;
import service.RoleService;
import service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    RoleService roleService;

    /**
     * 首页显示
     * @param model
     * @return
     */
    @RequestMapping("/userlist.html")
    public String getUser(Model model){
        List<User> users= userService.getUser(null);
        model.addAttribute("userList",users);
        List<Role> roles=roleService.getRole();
        model.addAttribute("roleList",roles);
        return "userlist";
    }

    /**
     * 添加页面前跳转
     * @return
     */
    @RequestMapping("/doadduser.html")
    public String doaddlist(@ModelAttribute User user){//添加对象模型
        return "useradd";
    }

    /**
     * 角色列表的ajax
     * @return
     */
    @RequestMapping(value = "/getrole.do",produces = "application/json; charset=utf-8")
    @ResponseBody //专用于异步请求注解
    public String getrole(){
        List<Role> roles=roleService.getRole();
        return JSONArray.toJSONString(roles);
    }

    /**
     * 用户名异步验证
     * @return
     */
    @RequestMapping("/usercode.do")
    @ResponseBody
    public String usercode(@RequestParam String userCode){
        List<User> users= userService.getUser(userCode);
        Map<String,String> map=new HashMap<>();
        if(users.size()>0){//说明重复
            map.put("userCode","exist");
        }else{
            map.put("userCode","true");
        }
        return JSONArray.toJSONString(map);
    }

    /**
     * 添加
     * @param user
     * @return
     */
    @RequestMapping("/adduser.html")
    public String adduser(User user, HttpSession session, HttpServletRequest request,
                          @RequestParam(value = "a_idPicPath",required = false)MultipartFile attach){
        //1、上传后的文件路径
        String idPicPath=null;
        //2、查看文件域是否为空
        if(!attach.isEmpty()){//true为空
            System.out.println("ssssssssssssssssssssssssssssssssssssssss");
            //3、定义上传目标的路径
            String path=session.getServletContext().//separator自适应的文件路径分隔符，减低代码的可入侵性
                    getRealPath("static"+ File.separator+"uploadfiles");
            //4、获取源文件名
            String oldFileName=attach.getOriginalFilename();
            //文件后缀
            String prefix= FilenameUtils.getExtension(oldFileName);
            //文件大小判断
            Integer filesize=5000000;
            if(attach.getSize()>filesize){//说明文件过大
                request.setAttribute("fileerror","上传文件过大！");
                return "useradd";
            }else if(prefix.equalsIgnoreCase("jpg")||
                    prefix.equalsIgnoreCase("jpeg")||
                    prefix.equalsIgnoreCase("png")||
                    prefix.equalsIgnoreCase("pneg")){//判断文件后缀
                //创建新文件名（格式：系统时间+随机数+xxx.jpg）
                String fileName=System.currentTimeMillis()
                            + RandomUtils.nextInt(1000000)+".jpg";
                //5、开始上传
                File targetFile=new File(path,fileName);
                if(!targetFile.exists()){//判断文件夹是否存在，不存在就创建
                    targetFile.mkdirs();
                }
                //6、接收文件流
                try {
                    attach.transferTo(targetFile);
                } catch (IOException e) {
                    e.printStackTrace();
                    request.setAttribute("fileerror","上传文件发生异常！");
                    return "useradd";
                }
                idPicPath=path+File.separator+fileName;//文件路径
                System.out.println("---"+idPicPath);
            }else{
                request.setAttribute("fileerror","上传文件格式不正确！");
                return "useradd";
            }
        }
        user.setIdPicPath(idPicPath);
        user.setCreatedBy(((User)(session.getAttribute("userSession"))).getId());
        user.setCreationDate(new Date());
        if(userService.adduser(user)){//说明添加成功
            return "redirect:/user/userlist.html";
        }else{
            return "useradd";
        }
    }
}
