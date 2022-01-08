package com.yurora.controller;

import com.yurora.pojo.Admin;
import com.yurora.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 唐孝顺
 * @date 2022/1/4 15:09
 */
@SuppressWarnings({"all"})
@Controller
@RequestMapping("/admin")
public class AdminAction {
    @Autowired
    private AdminService adminService;

    //实现登录判断，并进行相应的跳转
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String login(String name, String pwd, HttpServletRequest request){
        Admin admin  = adminService.login(name,pwd);
        if(admin != null){
            //登录成功
            //提示信息
            request.setAttribute("admin",admin);
            return "main";
        }else{
            //登录失败
            //提示信息
            request.setAttribute("errmsg","用户名或密码不正确！");
            return "login";
        }

    }

}
