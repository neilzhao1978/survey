package com.neil.survey;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/")
public class TestRoute {
	
	
//	private ReadingListRepository readingListRepository;
//	
//	@Autowired
//	public TestRoute(ReadingListRepository readingListRepository) {
//		this.readingListRepository = readingListRepository;
//	}
////	  @RequestMapping("/")
////	     String home() {
////	         return "Hello World!";
////	     }
//	  
//	  @RequestMapping(value ="/{id}",method = RequestMethod.GET)
//	  public String Books(@PathVariable("id") Long id,Model model){
//		  List<Book> books = readingListRepository.findById(id);
//		  if(books.size()>0) {
//			  return books.get(0).getName();
//		  }else {
//			  return "";
//		  }
//	  }
//	  
//	  @RequestMapping(value="/add", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},  method = RequestMethod.POST)    
//	  @ResponseBody  public Book add(@RequestBody Book pu,
//		        BindingResult bindingResult){  
//		  readingListRepository.save(pu);
//	      return pu;  
//	    }  
	  
	  
}
