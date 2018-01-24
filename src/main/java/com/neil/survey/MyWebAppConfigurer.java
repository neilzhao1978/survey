package com.neil.survey;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MyWebAppConfigurer 
        extends WebMvcConfigurerAdapter {

	
    @Value("${web.upload-path}")
    private String path;
    
    @Value("${web.static}")
    private String staticfile;
    
//	file:${web.static},file:${web.upload-path}
	
	///static/**,/allimages/**
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/allimages/**").addResourceLocations("file:"+path);
        registry.addResourceHandler("/static/**").addResourceLocations("file:"+staticfile);
        super.addResourceHandlers(registry);
    }

}