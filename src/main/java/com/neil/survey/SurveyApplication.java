package com.neil.survey;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import com.neil.servlet.IndexFilter;
import com.neil.servlet.H2Listener;
import com.neil.servlet.IndexServlet;

@SpringBootApplication(scanBasePackages={"com.neil.*"})

public class SurveyApplication {

//	  @RequestMapping("/")
//	     String home() {
//	         return "Hello World!";
//	     }
	

    
    @Bean
    public ServletRegistrationBean h2ServletRegistration() {
        ServletRegistrationBean registration = new ServletRegistrationBean(new org.h2.server.web.WebServlet());
        
        registration.addInitParameter("webAllowOthers", "");
        registration.addInitParameter("trace", "");
        registration.addUrlMappings("/console/*");
        return registration;
    }

    @Bean
    public FilterRegistrationBean indexFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean(new IndexFilter());
        registration.addUrlPatterns("/");
        return registration;
    }
    @Bean
    public ServletListenerRegistrationBean<H2Listener> servletListenerRegistrationBean(){
        ServletListenerRegistrationBean<H2Listener> servletListenerRegistrationBean = new ServletListenerRegistrationBean<H2Listener>();
        servletListenerRegistrationBean.setListener(new H2Listener());
        return servletListenerRegistrationBean;
    }
	
	public static void main(String[] args) {
		SpringApplication.run(SurveyApplication.class, args);
	}
}
