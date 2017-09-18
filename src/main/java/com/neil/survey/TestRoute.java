package com.neil.survey;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestRoute {
	  @RequestMapping("/")
	     String home() {
	         return "Hello World!";
	     }
}
