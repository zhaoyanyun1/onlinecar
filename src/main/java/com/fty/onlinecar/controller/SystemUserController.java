package com.fty.onlinecar.controller;


import com.fty.onlinecar.entity.SystemUser;
import com.fty.onlinecar.service.SystemUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class SystemUserController {

    @Autowired
    private SystemUserService systemUserService;
    @RequestMapping(value = "/systemuser",method = RequestMethod.GET)
    public ModelAndView index(){
        // 顾名思义 实体和数据 同时返回页面模板和数据
        ModelAndView mav = new ModelAndView("index");
        List<SystemUser> list = systemUserService.findAll();
        mav.addObject("list",list);
        return mav;
    }
}
