package cn.smbms.controller;

import cn.smbms.tools.Constants;
import cn.smbms.tools.PageSupport;
import cn.smbms.pojo.Role;
import cn.smbms.pojo.User;
import cn.smbms.service.role.RoleService;
import cn.smbms.service.user.UserService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.mysql.jdbc.StringUtils;
import com.sun.deploy.net.HttpResponse;
import org.apache.commons.io.FilenameUtils;
//import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Controller
//@RequestMapping("/user")
public class UserController extends BaseController{

    private static Logger logger=Logger.getLogger(UserController.class);

    @Resource
    UserService userService;

    @Resource
    RoleService roleService;

    @RequestMapping(value="/userlist.html")
    public String getUserList(Model model,
                              @RequestParam(value="queryname",required=false) String queryUserName,
                              @RequestParam(value="queryUserRole",required=false) String queryUserRole,
                              @RequestParam(value="pageIndex",required=false) String pageIndex)throws Exception{
        logger.info("getUserList ---- > queryUserName: " + queryUserName);
        logger.info("getUserList ---- > queryUserRole: " + queryUserRole);
        logger.info("getUserList ---- > pageIndex: " + pageIndex);
        int _queryUserRole = 0;
        List<User> userList = null;
        //设置页面容量
        int pageSize = Constants.pageSize;
        //当前页码
        int currentPageNo = 1;

        if(queryUserName == null){
            queryUserName = "";
        }
        if(queryUserRole != null && !queryUserRole.equals("")){
            _queryUserRole = Integer.parseInt(queryUserRole);
        }

        if(pageIndex != null){
            try{
                currentPageNo = Integer.valueOf(pageIndex);
            }catch(NumberFormatException e){
                return "redirect:/user/syserror.html";
                //response.sendRedirect("syserror.jsp");
            }
        }
        //总数量（表）
        int totalCount	= userService.getUserCount(queryUserName,_queryUserRole);
        //总页数
        PageSupport pages=new PageSupport();
        pages.setCurrentPageNo(currentPageNo);
        pages.setPageSize(pageSize);
        pages.setTotalCount(totalCount);
        int totalPageCount = pages.getTotalPageCount();
        //控制首页和尾页
        if(currentPageNo < 1){
            currentPageNo = 1;
        }else if(currentPageNo > totalPageCount){
            currentPageNo = totalPageCount;
        }
        userList = userService.getUserList(queryUserName,_queryUserRole,currentPageNo,pageSize);
        model.addAttribute("userList", userList);
        List<Role> roleList = null;
        roleList = roleService.getRoleList();
        model.addAttribute("roleList", roleList);
        model.addAttribute("queryUserName", queryUserName);
        model.addAttribute("queryUserRole", queryUserRole);
        model.addAttribute("totalPageCount", totalPageCount);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("currentPageNo", currentPageNo);
        return "userlist";
    }

    @RequestMapping(value ="/useradd.html",method = RequestMethod.GET)
    public String addUser(@ModelAttribute("user") User user){
        return  "useradd";
    }


    /*@RequestMapping(value = "/addsave.html",method = RequestMethod.POST)
    public String addUserSave(User user,HttpSession session){
        user.setCreatedBy(((User)session.getAttribute(Constants.USER_SESSION)).getId());
        user.setCreationDate(new Date());
        if(userService.adduser(user)>0){
            return "redirect:/userlist.html";
        }
        return "useradd";
    }*/

    @RequestMapping(value = "/addsave.html",method = RequestMethod.POST)
    public String addUserSave(User user, HttpServletRequest request, HttpSession session, @RequestParam(value = "attachs",required = false)MultipartFile[] attachs){
        String idPicPath=null;
        String workPicPath=null;
        String errorInfo=null;
        boolean flag=true;
        String path=request.getSession().getServletContext().getRealPath("statics"+File.separator+"uploadfiles");
        logger.info("uploadFile path=====>"+path);
        //判断文件是否为空
        for (int i = 0; i <attachs.length ; i++) {
            MultipartFile attach=attachs[i];
            if (!attach.isEmpty()){
                if(i==0){
                    errorInfo="uploadFileError";
                }else if(i==1){
                    errorInfo="uploadWpError";
                }
                String oldFileName=attach.getOriginalFilename();//原文件名
                //logger.info("uploadFile  oldFileName====>"+oldFileName);
                String prefix=FilenameUtils.getExtension(oldFileName);//原文件名后缀
                //logger.info("uploadFile  prefix====>"+prefix);
                int filesize=5000000;
                //logger.debug("uploadFile  filesize====>"+attach.getSize());
                if(attach.getSize()>filesize){
                    request.setAttribute("errorInfo","上传大小不得超过5000kb");
                    flag=false;
                }else if(prefix.equalsIgnoreCase("jpg")||prefix.equalsIgnoreCase("png")||prefix.equalsIgnoreCase("jpeg")
                        ||prefix.equalsIgnoreCase("pneg")){//上传图片格式不正确
                    String fileName=System.currentTimeMillis()+RandomUtils.nextInt(1000000)+"_Personal.jpg";
                    File targetFile=new File(path,fileName);
                    if(!targetFile.exists()){
                        targetFile.mkdirs();
                    }
                    //保存
                    try {
                        attach.transferTo(targetFile);
                    }catch (Exception e){
                        e.printStackTrace();
                        request.setAttribute("errorInfo","上传失败！");
                        flag=false;
                    }
                    if(i==0){
                        idPicPath=File.separator+fileName;
                    }else if(i==1){
                        workPicPath=File.separator+fileName;
                    }
                    logger.debug("idPicPath:"+idPicPath);
                    logger.debug("workPicPath:"+workPicPath);

                }else{
                    request.setAttribute("errorInfo","*上传图片格式不正确");
                    flag=false;
                }
            }
        }

        if(flag){
            user.setCreatedBy(((User)session.getAttribute(Constants.USER_SESSION)).getId());
            user.setCreationDate(new Date());
            user.setIdPicPath(idPicPath);
            user.setWorkPicPath(workPicPath);
            if(userService.adduser(user)>0){
                return "redirect:/userlist.html";
            }
        }
        return "useradd";
    }

    @RequestMapping(value = "/add.html",method = RequestMethod.GET)
    public String add(@ModelAttribute("user")User user){
         return "useradd";
    }

    @RequestMapping(value = "/add.html",method = RequestMethod.POST)
    public String addSave(/*@Valid*/ User user, /*BindingResult bindingResult,*/HttpSession session){
        /*if(bindingResult.hasErrors()){
            logger.debug("ass user validated  has error======");
            return "useradd";
        }*/
        user.setCreatedBy(((User)session.getAttribute(Constants.USER_SESSION)).getId());
        user.setCreationDate(new Date());
        if(userService.adduser(user)>0){
            return "redirect:/userlist.html";
        }
        return "useradd";
    }

    @RequestMapping(value = "/usermodify.html",method = RequestMethod.GET)
    public String getUserById(@RequestParam String uid,Model model){
        logger.debug("getUserById uid========"+uid);
        User user=userService.getUserById(uid);
        model.addAttribute("user",user);
        return "usermodify";
    }

    @RequestMapping(value = "/usermodifysave.html",method = RequestMethod.POST)
    public String modifyUserSave(User user,HttpSession session){
        logger.debug("modifyUserSave userid========"+user.getId());
        user.setCreatedBy(((User)session.getAttribute(Constants.USER_SESSION)).getId());
        user.setModifyDate(new Date());
        if(userService.modify(user)){
            return "redirect:/userlist.html";
        }
        return "usermodify";
    }

    @RequestMapping(value = "/view/{id}",method = RequestMethod.GET)
    public String view(@PathVariable String id, Model model){
        logger.debug("view uid========"+id);
        User user=userService.getUserById(id);
        model.addAttribute("user",user);
        return "userview";
    }

    /*@RequestMapping(value = "/delid.html",method = RequestMethod.GET)
    @ResponseBody
    public String del(@RequestParam String uid){
           String delResult="false";
        if(userService.del(uid)>0){
            delResult=JSON.toJSONString("true");
            logger.debug("delResult  ======="+delResult);
        }
           return delResult;
    }*/

    @RequestMapping(value="/deletesave.html",method=RequestMethod.POST)
    public String deleteUserById(String uid, HttpSession session){

        int result = userService.del(uid);

        HashMap<String, String> resultMap = new HashMap<String, String>();

        if(result<0){
            resultMap.put("delResult", "notexist");
        }else{
            if(userService.del(uid)>0){
                resultMap.put("delResult", "true");
            }else{
                resultMap.put("delResult", "false");
            }
        }
        return JSONArray.toJSONString(resultMap);
    }

    @RequestMapping(value = "/ucexist.html")
    @ResponseBody
    public Object userCodeIsExit(@RequestParam String userCode){
       logger.debug("userCodeIsExit   userCode:"+userCode);
        HashMap<String,String> resultMap=new HashMap<String, String>();
        if(StringUtils.isNullOrEmpty(userCode)){
           resultMap.put("userCode","exist");
        }else {
            User user=userService.selectUserCodeExist(userCode);
            if(user!=null)
               resultMap.put("userCode","exist");
            else
                resultMap.put("userCode","noexist");
        }
        return JSONArray.toJSONString(resultMap);
    }

    @RequestMapping(value = "/views",method = RequestMethod.GET)
    @ResponseBody
    public User view(@RequestParam String id){
       logger.debug("view id:"+id);
        User user=new User();
        try {
            user=userService.getUserById(id);
        }catch (Exception e){
            e.printStackTrace();
        }
        return user;
    }

    /*@RequestMapping(value = "/view",method = RequestMethod.GET)
    @ResponseBody
    public Object view(@RequestParam String id){
        logger.debug("view id:"+id);
        String cjson="";
           if(id==null||"".equals(id)){
            return "nodata";
           }else{
               try {
                   User user=userService.getUserById(id);
                   cjson=JSON.toJSONString(user);
                   logger.debug("cjson:"+cjson);
               }catch (Exception e){
                   e.printStackTrace();
                   return "failed";
               }
                   return cjson;
           }
    }*/

    @RequestMapping(value = "/pwdModify",method = RequestMethod.GET)
    public String pwdModify(){
       return "pwdmodify";
    }

    /*@RequestMapping(value = "/pwdModify",method = RequestMethod.GET)
    @ResponseBody
    public String pwdModify(@RequestParam String oldpassword){
       return "";
    }*/
}
