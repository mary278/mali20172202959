package cn.smbms.controller;

import cn.smbms.pojo.Role;
import cn.smbms.service.role.RoleService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/roleService")
public class RoleController {
private static Logger logger=Logger.getLogger(RoleController.class);
    @Resource
    RoleService roleService;

    @RequestMapping(value = "/rolelist.html")
    public String list(Model model,@RequestParam(value = "queryUserRole",required = false)String queryUserRole){
        List<Role> list=roleService.getRoleList1(queryUserRole);
        logger.debug("============>"+queryUserRole);
        model.addAttribute("roleList",list);
        return "rolelist";
    }
}
