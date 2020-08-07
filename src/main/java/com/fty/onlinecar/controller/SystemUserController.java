package com.fty.onlinecar.controller;


import com.fty.onlinecar.entity.SystemUser;
import com.fty.onlinecar.service.SystemUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class SystemUserController {

    @Autowired
    private SystemUserService systemUserService;

    @RequestMapping(value = "/systemUser",method = RequestMethod.GET)
    public ModelAndView index(){
        // 顾名思义 实体和数据 同时返回页面模板和数据
        ModelAndView mav = new ModelAndView("index");
        List<SystemUser> list = systemUserService.all();
        mav.addObject("list",list);
        return mav;
    }


    @GetMapping(value = {"/toLogin"})
    public String toLogin() {
        System.out.println("toLogin");
        return "systemUserToLogin";
    }

    @PostMapping(value = {"systemUserLogin"})
    public String login(HttpServletRequest request){
        String account = request.getParameter("log_username");
        String passWd = request.getParameter("log_password");
        SystemUser user = systemUserService.findBy("account",account);
        System.out.println(user);
        if(user==null){
            request.setAttribute("error","用户名错误！");
            return "systemUserToLogin";
        }
        if(!passWd.equals(user.getPassword())){
            request.setAttribute("error","密码错误！");
            return "systemUserToLogin";
        }
        request.setAttribute("name",user.getName());
        return "main";
    }
}
