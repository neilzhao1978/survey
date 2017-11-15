package com.neil.survey.controller;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.neil.survey.module.Brand;
import com.neil.survey.module.Survey;
import com.neil.survey.repository.AnswerRepository;
import com.neil.survey.repository.BrandRepository;
import com.neil.survey.repository.CreatorRepository;
import com.neil.survey.repository.ImageRepository;
import com.neil.survey.repository.SurveyRepository;
import com.neil.survey.util.ErrorCode;
import com.neil.survey.util.PageEntity;
import com.neil.survey.util.ResponseGenerator;
import com.neil.survey.util.RestResponseEntity;

@RestController
@RequestMapping("/api/brandService")
@Transactional
public class BrandController {
	@Autowired
	private SurveyRepository surveyRepo;
	@Autowired
	private AnswerRepository answerRepo;
	@Autowired
	private BrandRepository brandRepo;
	@Autowired
	private ImageRepository imageRepo;
	@Autowired
	private CreatorRepository creatorRepo;
	
	@ResponseBody
	@RequestMapping(value = "/getAllBrands", method = RequestMethod.GET)
	public RestResponseEntity<List<Brand>> getAllBrands( @RequestParam(value = "page",required=true) PageEntity page){
		
		PageRequest pageRequest = new PageRequest(page.getPageNumber()-1, page.getPageSize(), null);
		Page<Brand> brands = brandRepo.findAll(pageRequest);

		if(brands!=null && brands.getSize()>0) {
			for(Brand b:brands.getContent()) {
				//TODO clear useless properties
			}
			return ResponseGenerator.createSuccessResponse("Get brand list success.", brands.getContent().size(), brands.getContent(),brands.getTotalElements());
		}else {
			return ResponseGenerator.createFailResponse("Fail to get brand list", ErrorCode.DB_ERROR);
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/addBrand", method = RequestMethod.POST)
	public RestResponseEntity<Void> addBrand( @RequestBody Brand brand){
		Brand b = brandRepo.save(brand);
		if(b!=null) {
			return ResponseGenerator.createSuccessResponse("Add/update brand success.");
		}else {
			return ResponseGenerator.createFailResponse("Fail to add/update brand.", ErrorCode.DB_ERROR);
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/updateBrand", method = RequestMethod.POST)
	public RestResponseEntity<Void> updateBrand( @RequestBody Brand brand){
		Brand b = brandRepo.save(brand);
		if(b!=null) {
			return ResponseGenerator.createSuccessResponse("Add/update brand success.");
		}else {
			return ResponseGenerator.createFailResponse("Fail to add/update brand.", ErrorCode.DB_ERROR);
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/deleteBrand", method = RequestMethod.DELETE)
	public RestResponseEntity<Void> deleteCreator( @RequestBody Brand brand){
		try {	
			brand.setImages(null);
			brandRepo.save(brand);
			brandRepo.delete(brand);
			
			return ResponseGenerator.createSuccessResponse("delete brand success.");
		}catch(Exception e) {			
			return ResponseGenerator.createFailResponse("Fail to delete brand.", ErrorCode.DB_ERROR);
		}
	}
	
}
