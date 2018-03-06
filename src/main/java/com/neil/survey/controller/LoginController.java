package com.neil.survey.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

import com.neil.survey.WebSecurityConfig;
import com.neil.survey.inputout.UserParam;
import com.neil.survey.module.User;
import com.neil.survey.service.impl.LoginService;

@Controller
public class LoginController {

    @Autowired
    private LoginService loginService;

    @GetMapping("/")
    public ModelAndView index(@SessionAttribute(WebSecurityConfig.SESSION_KEY)String account,ModelAndView view){
    	view.setViewName("redirect:/static/main/query/views/queryList");
        return view;
    }

    @GetMapping("/login")
    public ModelAndView   login(ModelAndView view){
        view.setViewName("redirect:/static/main/query/views/login.html");
        return view;
    }

    @PostMapping("/user/ajaxLogin")
    public String loginVerify(@RequestBody UserParam userParam,HttpSession session){
        User user = new User();
        user.setEmail(userParam.getEmail());
        user.setPsw(userParam.getPassword());

        boolean verify = loginService.verifyLogin(user);
        if (verify) {
            session.setAttribute(WebSecurityConfig.SESSION_KEY, user.getEmail());
            return "static/main/query/views/queryList";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute(WebSecurityConfig.SESSION_KEY);
        return "redirect:/login";
    }
}