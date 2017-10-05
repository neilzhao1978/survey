package com.neil.survey.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.neil.survey.module.Image;
import com.neil.survey.repository.ImageRepository;
import com.neil.survey.util.ErrorCode;
import com.neil.survey.util.PageEntity;
import com.neil.survey.util.ResponseGenerator;
import com.neil.survey.util.RestResponseEntity;

@RestController
@RequestMapping("/api/imageService")
public class ImageController {

	@Autowired
	private ImageRepository imageRepo;


	@ResponseBody
	@RequestMapping(value = "/getAllImages", method = RequestMethod.GET)
	public RestResponseEntity<List<Image>> getAllImages( @RequestParam(value = "page",required=true) PageEntity page,
			@RequestParam(value = "type",required=false) String type){
		
		PageRequest pageRequest = new PageRequest(page.getPageNumber(), page.getPageSize(), null);
		ExampleMatcher matcher = ExampleMatcher.matching()
				  .withMatcher("imageType", match -> match.endsWith());
		Image i = new Image();
		i.setImageType(type);
		Example<Image> example = Example.of(i, matcher);
		Page<Image> images =  imageRepo.findAll(example, pageRequest);
		if(images!=null && images.getSize()>0) {
			return ResponseGenerator.createSuccessResponse("Get image list success.", images.getContent().size(), images.getContent(),images.getTotalElements());
		}else {
			return ResponseGenerator.createFailResponse("Fail to get image list", ErrorCode.DB_ERROR);
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/addImage", method = RequestMethod.POST)
	public RestResponseEntity<Void> addImage( @RequestBody Image image){
		Image b = imageRepo.save(image);
		if(b!=null) {
			return ResponseGenerator.createSuccessResponse("Add/update image success.");
		}else {
			return ResponseGenerator.createFailResponse("Fail to add/update image.", ErrorCode.DB_ERROR);
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/updateImage", method = RequestMethod.POST)
	public RestResponseEntity<Void> updateImage( @RequestBody Image image){
		Image b = imageRepo.save(image);
		if(b!=null) {
			return ResponseGenerator.createSuccessResponse("Add/update image success.");
		}else {
			return ResponseGenerator.createFailResponse("Fail to add/update image.", ErrorCode.DB_ERROR);
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/deleteImage", method = RequestMethod.DELETE)
	public RestResponseEntity<Void> deleteCreator( @RequestBody Image image){
		try {	
			imageRepo.delete(image);
			
			return ResponseGenerator.createSuccessResponse("delete image success.");
		}catch(Exception e) {			
			return ResponseGenerator.createFailResponse("Fail to delete image.", ErrorCode.DB_ERROR);
		}
	}
	
}
