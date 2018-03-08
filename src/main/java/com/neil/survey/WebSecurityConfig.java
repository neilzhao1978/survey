package com.neil.survey;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.neil.survey.controller.BrandController;

/**
 * 登录配置 博客出处：http://www.cnblogs.com/GoodHelper/
 *
 */
@Configuration
public class WebSecurityConfig extends WebMvcConfigurerAdapter {

    /**
     * 登录session key
     */
    public final static String SESSION_KEY = "user";

	private static final Logger logger = LoggerFactory.getLogger(WebSecurityConfig.class);
    
    @Bean
    public SecurityInterceptor getSecurityInterceptor() {
        return new SecurityInterceptor();
    }

    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration addInterceptor = registry.addInterceptor(getSecurityInterceptor());
        addInterceptor.excludePathPatterns("/api/loginService");
        addInterceptor.excludePathPatterns("/**/login.html");
        addInterceptor.excludePathPatterns("/**/survey.html");
        addInterceptor.excludePathPatterns("/error");
//        addInterceptor.excludePathPatterns("/login");
        addInterceptor.excludePathPatterns("/api/answerService/addAnswer");
        addInterceptor.excludePathPatterns("/api/surveyService/getSurveyDetail");       
        
        
        addInterceptor.addPathPatterns("/**");
    }

    private class SecurityInterceptor extends HandlerInterceptorAdapter {

        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
                throws Exception {
        	logger.info(request.getRequestURL().toString());
            HttpSession session = request.getSession();
            if (session.getAttribute(SESSION_KEY) != null){            	
            	return true;
            }else{
                String url = "/static/main/query/views/login.html";
                logger.info("redirect to" + url);
                response.sendRedirect(url);
                return false;
            }


        }
    }
}