package cn.smbms.controller;

import cn.smbms.tools.Constants;
import cn.smbms.tools.PageSupport;
import cn.smbms.pojo.Provider;
import cn.smbms.pojo.User;
import cn.smbms.service.provider.ProviderService;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.math.RandomUtils;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
//import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang.math.RandomUtils;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.Date;
import java.util.List;


@Controller
@RequestMapping("/provider")
public class ProviderController {

    private static Logger logger=Logger.getLogger(ProviderController.class);
    @Resource
    ProviderService providerService;

    @RequestMapping(value ="/providerlist.html")
    public String proList(Model model, @RequestParam(value = "queryProCode",required = false)String queryProCode,
                          @RequestParam(value = "queryProName",required = false)String queryProName,
                          @RequestParam(value="pageIndex",required=false) String pageIndex){
        List<Provider> proList = null;
        //设置页面容量
        int pageSize = Constants.pageSize;
        //当前页码
        int currentPageNo = 1;

        if(queryProCode == null){
            queryProCode = "";
        }

        if(queryProName == null){
            queryProName = "";
        }

        if(pageIndex != null){
            try{
                currentPageNo = Integer.valueOf(pageIndex);
            }catch(NumberFormatException e){
                return "redirect:/user/syserror.html";
            }
        }
        //总数量（表）
        int totalCount	= providerService.proCount(queryProCode,queryProName);
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
        proList = providerService.getProList(queryProCode,queryProName,currentPageNo,pageSize);
        model.addAttribute("providerList", proList);
        model.addAttribute("queryProName", queryProName);
        model.addAttribute("queryProCode", queryProCode);
        model.addAttribute("totalPageCount", totalPageCount);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("currentPageNo", currentPageNo);
        model.addAttribute("param",pages);
        return "providerlist";
    }

    @RequestMapping(value = "/providerlist1.html")
    public String list(Model model){
       List<Provider> list=providerService.list();
       model.addAttribute("providerList",list);
       return "providerlist";
    }

    @RequestMapping(value = "/addPro.html")
    public String addPro(@ModelAttribute("provider")Provider provider){
       return "provideradd";
    }


    @RequestMapping(value = "/addprovidersave.html",method = RequestMethod.POST)
    public String add(Provider provider, HttpSession session, HttpServletRequest request, @RequestParam(value = "attachs",required = false)MultipartFile[] attachs){
        //provider.setCreatedBy(1);
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
            provider.setCreatedBy(((User)session.getAttribute(Constants.USER_SESSION)).getId());
            provider.setCreationDate(new Date());
            provider.setCompanyLicPicPath(idPicPath);
            provider.setOrgCodePicPath(workPicPath);
            if(providerService.addProvide(provider)>0){
                return "redirect:/provider/providerlist.html";
            }
        }
            return "provideradd";
    }

    @RequestMapping(value = "/proview/{id}",method = RequestMethod.GET)
    public String proview(@PathVariable String id,Model model){
       Provider provider=providerService.ProView(id);
       model.addAttribute("provider",provider);
       return "providerview";
    }

    @RequestMapping(value = "/providermodify.html",method = RequestMethod.GET)
    public String getProById(@RequestParam String proid,Model model){
       Provider provider=providerService.ProView(proid);
       model.addAttribute("provider",provider);
       return "providermodify";
    }

    @RequestMapping(value = "/providermodifysave.html",method = RequestMethod.POST)
    public String save(Provider provider, HttpSession session){
            //logger.debug("供应商id======》"+provider.getId());
            //provider.setModifyBy(1);
            provider.setCreatedBy(((User)session.getAttribute(Constants.USER_SESSION)).getId());
            provider.setModifyDate(new Date());
            if(providerService.modifyPro(provider)>0){
                return "redirect:/provider/providerlist.html";
            }
       return "provideradd";
    }
}
