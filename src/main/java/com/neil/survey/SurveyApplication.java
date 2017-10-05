package com.neil.survey;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.neil.servlet.IndexFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.neil.servlet.H2Listener;
import com.neil.servlet.IndexServlet;
import com.neil.survey.util.RequestBodyConverter;

@SpringBootApplication
@ServletComponentScan
public class SurveyApplication{

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
    
    
//    @Bean(name="conversionService")
//    public ConversionService getConversionService() {
//        ConversionServiceFactoryBean bean = new ConversionServiceFactoryBean();
//        Set<RequestBodyConverter> converters = new HashSet<RequestBodyConverter>();
//        converters.add(new RequestBodyConverter());
//        bean.setConverters(converters); 
//        bean.afterPropertiesSet();
//        return bean.getObject();
//    }
    
    @Bean
    public FilterRegistrationBean indexFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean(new org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter());
        registration.addUrlPatterns("/*");
        return registration;
    }
    @Bean
    public ServletListenerRegistrationBean<H2Listener> servletListenerRegistrationBean(){
        ServletListenerRegistrationBean<H2Listener> servletListenerRegistrationBean = new ServletListenerRegistrationBean<H2Listener>();
        servletListenerRegistrationBean.setListener(new H2Listener());
        return servletListenerRegistrationBean;
    }
	
    @Bean
    public HttpMessageConverters fastJsonHttpMessageConverters(){
        //1.需要定义一个convert转换消息的对象;
        FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
        //2:添加fastJson的配置信息;
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
        //3处理中文乱码问题
        List<MediaType> fastMediaTypes = new ArrayList<>();
        fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
//        fastMediaTypes.add(MediaType.APPLICATION_FORM_URLENCODED);
        //4.在convert中添加配置信息.
        fastJsonHttpMessageConverter.setSupportedMediaTypes(fastMediaTypes);
        fastJsonHttpMessageConverter.setFastJsonConfig(fastJsonConfig);
        HttpMessageConverter<?> converter = fastJsonHttpMessageConverter;
        return new HttpMessageConverters(converter);

    }

    
	public static void main(String[] args) {
		SpringApplication.run(SurveyApplication.class, args);
	}
}
