package com.shsxt.xm.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by lp on 2017/12/7.
 */
@Controller
public class IndexController {

    /**
     * 返回主页视图
     * @return
     */
    @RequestMapping("index")
    public  String index(HttpServletRequest request){
        request.setAttribute("ctx",request.getContextPath());
        return "index";
    }

    /**
     * 返回登录视图
     * @return
     */
    @RequestMapping("login")
    public  String login(HttpServletRequest request){
        request.setAttribute("ctx",request.getContextPath());
        return "login";
    }

    @RequestMapping("quickLoginPage")
    public  String quickLoginPage(HttpServletRequest request){
        request.setAttribute("ctx",request.getContextPath());
        return "quick_login";
    }

    /**
     * 返回注册视图
     * @return
     */
    @RequestMapping("register")
    public  String register(HttpServletRequest request){
        request.setAttribute("ctx",request.getContextPath());
        return "register";
    }
}
