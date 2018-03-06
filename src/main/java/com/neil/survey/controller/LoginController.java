package com.neil.survey.controller;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.neil.survey.WebSecurityConfig;
import com.neil.survey.inputout.UserParam;
import com.neil.survey.module.User;
import com.neil.survey.service.impl.LoginService;

@RestController
@RequestMapping("/")
@Transactional
public class LoginController {

    @Autowired
    private LoginService loginService;

    @GetMapping("/")
    public String index(@SessionAttribute(WebSecurityConfig.SESSION_KEY)String account,Model model){
        return "static/main/query/views/queryList";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @PostMapping("/loginVerify")
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