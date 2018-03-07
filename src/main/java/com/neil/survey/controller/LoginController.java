package com.neil.survey.controller;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

import com.neil.survey.WebSecurityConfig;
import com.neil.survey.inputout.LoginResult;
import com.neil.survey.inputout.UserParam;
import com.neil.survey.module.User;
import com.neil.survey.service.impl.LoginService;

@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @GetMapping("/")
    public ModelAndView index(@SessionAttribute(WebSecurityConfig.SESSION_KEY)String account,ModelAndView view){
    	view.setViewName("redirect:/static/main/query/views/login.html");
        return view;
    }

//    @GetMapping("/login")
//    public ModelAndView login(ModelAndView view){
//        view.setViewName("redirect:/static/main/query/views/login.html");
//        return view;
//    }

	@ResponseBody
	@RequestMapping(value = "/api/loginService", method = RequestMethod.POST)
    public LoginResult loginVerify(@RequestBody UserParam userParam,HttpSession session) throws IOException{
        User user = new User();
        user.setEmail(userParam.getEmail());
        user.setPsw(userParam.getPassword());

        boolean verify = loginService.verifyLogin(user);
        LoginResult rtn = new LoginResult();
        if (verify) {
            session.setAttribute(WebSecurityConfig.SESSION_KEY, user.getEmail());
            rtn.setMessage("login success.");
            rtn.setResultCode(200);
//            Cookie cookie = new Cookie("PATH","/static/main/query/views/queryList.html");
//            response.addCookie(cookie);

//            response.setStatus(200);
//            response.sendRedirect("/static/main/query/views/queryList.html");
//            view.setViewName("redirect:/static/main/query/views/queryList.html");
//            return view;
        } else {
            rtn.setMessage("login fail.");
            rtn.setResultCode(400);
//        	response.setStatus(404);
//        	response.sendRedirect("/static/main/query/views/login.html");
//        	view.setViewName("redirect:/static/main/query/views/login.html");
//            return view;
        }
        return rtn;
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute(WebSecurityConfig.SESSION_KEY);
        return "redirect:/login";
    }
}