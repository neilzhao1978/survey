package com.neil.survey;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SurveyApplication {

//	  @RequestMapping("/")
//	     String home() {
//	         return "Hello World!";
//	     }
	
	public static void main(String[] args) {
		SpringApplication.run(SurveyApplication.class, args);
	}
}
