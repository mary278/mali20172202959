package cn.smbms.controller;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;

import java.text.SimpleDateFormat;
import java.util.Date;


public class BaseController {
    public void initBinder(WebDataBinder dataBinder){
        System.out.println("initBinder=============");
        dataBinder.registerCustomEditor(Date.class,new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"),true));
    }
}
