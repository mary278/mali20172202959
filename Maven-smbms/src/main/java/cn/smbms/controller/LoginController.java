package cn.smbms.controller;

import cn.smbms.tools.Constants;
import cn.smbms.pojo.User;
import cn.smbms.service.role.RoleService;
import cn.smbms.service.user.UserService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Resource
    UserService userService;

    @Resource
    RoleService roleService;

    private Logger logger = Logger.getLogger(LoginController.class);

    @RequestMapping(value="/login.html")
    public String login(){
        logger.debug("LoginController welcome SMBMS==================");
        return "login";
    }

    @RequestMapping(value="/sys/main.html")
    public String main(){
        return "frame";
    }

    @RequestMapping(value="/dologin.html",method=RequestMethod.POST)
    public String doLogin(@RequestParam String userCode, @RequestParam String userPassword, HttpServletRequest request, HttpSession session) throws Exception{
        logger.debug("doLogin====================================");
        //调用service方法，进行用户匹配
        User user = userService.login(userCode,userPassword);
        if(null != user){//登录成功
            session.setAttribute(Constants.USER_SESSION,user);
            if(!user.getUserPassword().equals(userPassword)){
                //request.setAttribute("error","密码输入错误");
                throw new RuntimeException("密码输入错误");
            }
            //页面跳转（frame.jsp）
           // return "redirect:/sys/main.html";
            return "frame";
        }else{
            //页面跳转（login.jsp）
            throw new RuntimeException("用户名不存在");
            //request.setAttribute("error","用户或者密码错误");
            //return "login";
        }
    }

    @RequestMapping(value = "/exlogin.html",method = RequestMethod.GET)
    public String rxLogin(@RequestParam String userCode,@
          RequestParam String userPassword)throws Exception{
        logger.debug("exLogin==========================");
        //调用Service方法，进行用户匹配
        User user=userService.login(userCode,userPassword);
        if(null==user){
            throw new RuntimeException("用户或者密码不正确！");
        }
        //return "redirect:/user/main.html";
        return "frame";
  }

  @ExceptionHandler(value ={RuntimeException.class})
  public String handlerException(RuntimeException e,HttpServletRequest request){
        request.setAttribute("error",e.getMessage());
        return  "login";
  }

  @RequestMapping(value ="/main.html")
  public String main(HttpSession session){
        if(session.getAttribute(Constants.USER_SESSION)==null){
            return "redirect:/user/login.html";
        }
        return "frame";
      }


    @RequestMapping(value="/syserror.html")
    public String sysError(){
        return "syserror";
    }


}
