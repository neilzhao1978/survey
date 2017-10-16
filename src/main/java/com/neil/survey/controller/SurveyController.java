package com.neil.survey.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

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
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.neil.survey.module.Answer;
import com.neil.survey.module.Brand;
import com.neil.survey.module.BrandResp;
import com.neil.survey.module.Brand_P;
import com.neil.survey.module.Component;
import com.neil.survey.module.Image;
import com.neil.survey.module.ImageCount;
import com.neil.survey.module.Survey;
import com.neil.survey.module.VehicheResp;
import com.neil.survey.module.VehicleInfo_P;
import com.neil.survey.repository.AnswerRepository;
import com.neil.survey.repository.BrandRepository;
import com.neil.survey.repository.ImageRepository;
import com.neil.survey.repository.SurveyRepository;
import com.neil.survey.util.ErrorCode;
import com.neil.survey.util.PageEntity;
import com.neil.survey.util.ResponseGenerator;
import com.neil.survey.util.RestResponseEntity;

@RestController
@RequestMapping("/api/surveyService")
public class SurveyController {
	

	@Autowired
	private SurveyRepository surveyRepo;
	@Autowired
	private AnswerRepository answerRepo;
	
	@Autowired
	private RestTemplate template;
	@Autowired
	private BrandRepository brandRepo;
	@Autowired
	private ImageRepository imageRepo;
//	@Autowired
//	private CreatorRepository creatorRepo;
	

	@ResponseBody
	@RequestMapping(value = "/getAllSurveys", method = RequestMethod.GET)
	public RestResponseEntity<List<Survey>> getAllSurveys( @RequestParam(value = "page",required=true) PageEntity page){
		
		PageRequest pageRequest = new PageRequest(page.getPageNumber(), page.getPageSize(), null);
		Page<Survey> surveys = surveyRepo.findAll(pageRequest);

		if(surveys!=null && surveys.getSize()>0) {
			for(Survey s:surveys.getContent()) {
//				s.setAnswers(null);
//				s.setBrands(null);
//				s.setImages(null);
//				s.getCreator().setSurveys(null);
				s.getCreator().setPwd(null);
			}
			return ResponseGenerator.createSuccessResponse("Get survey list success.", surveys.getContent().size(), surveys.getContent(),surveys.getTotalElements());
		}else {
			return ResponseGenerator.createFailResponse("Fail to get survey list", ErrorCode.DB_ERROR);
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/addSurvey", method = RequestMethod.POST)
	public RestResponseEntity<Void> addSurvey( @RequestBody Survey survey){
		Survey s = surveyRepo.save(survey);
		if(s!=null) {
			
			
			return ResponseGenerator.createSuccessResponse("Add/update creator  success.");
		}else {
			return ResponseGenerator.createFailResponse("Fail to add/update creator.", ErrorCode.DB_ERROR);
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/deleteSurvey", method = RequestMethod.DELETE)
	public RestResponseEntity<Void> deleteCreator( @RequestBody Survey survey){
		try {	
//			surveyRepo.delete(survey);
			survey.setCreator(null);
			surveyRepo.save(survey);
			surveyRepo.delete(survey.getSurveyId());
			return ResponseGenerator.createSuccessResponse("delete survey  success.");
		}catch(Exception e) {			
			return ResponseGenerator.createFailResponse("Fail to delete survey.", ErrorCode.DB_ERROR);
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/updateSurvey", method = RequestMethod.POST)
	public RestResponseEntity<Void> updateSurvey( @RequestBody Survey survey){
		Survey s = surveyRepo.save(survey);
		if(s!=null) {
			return ResponseGenerator.createSuccessResponse("Add/update creator success.");
		}else {
			return ResponseGenerator.createFailResponse("Fail to add/update creator.", ErrorCode.DB_ERROR);
		}
	}

	@ResponseBody
	@RequestMapping(value = "/getSurveyDetail", method = RequestMethod.GET)
	public RestResponseEntity<Survey> getSurveyDetail( @RequestParam(value = "surveyId",required=true) String surveyId){
		Survey s = surveyRepo.getBySurveyId(surveyId);

		if(s!=null) {
			s.getCreator().setPwd(null);
			return ResponseGenerator.createSuccessResponse("get survey detail success.",1,s,null);
		}else {
			return ResponseGenerator.createFailResponse("Fail to add/update creator.", ErrorCode.DB_ERROR);
		}
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/getSurveyResult", method = RequestMethod.GET)
	public RestResponseEntity<List<ImageCount>> getSurveyResult( @RequestParam(value = "surveyId",required=true) String surveyId,
			@RequestParam(value = "imageType",required=true) String imageType){

		Survey survey = surveyRepo.getBySurveyId(surveyId);

		List<Answer> answers = answerRepo.findBySurvey(survey);
		List<Image> allImages = new ArrayList<Image>(); 
		for(Answer a : answers) {
			allImages.addAll(a.getImages());
		}
		
		Map<String,ImageCount> imageCount = new HashMap<String,ImageCount>();
		for(Image i : allImages) {
			if(i.getImageType().equalsIgnoreCase(imageType)) {
				if(imageCount.containsKey(i.getImageId())) {
					imageCount.get(i.getImageId()).increaseCount();;
				}else {
					ImageCount x = new ImageCount();
					x.setI(i);
					x.setCount(1);
					imageCount.put(i.getImageId(), x);
				}
			}
		}
		
		List<ImageCount> result = new ArrayList<ImageCount>();
		result.addAll(imageCount.values());
		Collections.sort(result);
		
		if(answers!=null) {
			return ResponseGenerator.createSuccessResponse("Get result list success.", result.size(), result, result.size());
		}else {
			return ResponseGenerator.createFailResponse("Fail to get result list.", ErrorCode.DB_ERROR);
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/sychDb", method = RequestMethod.GET)
	public RestResponseEntity<Void> sychDb(){
		BrandResp brandResp = null;
		brandResp = template.getForObject("http://139.196.236.34:8778/equipment/esApi/getAllBrand", BrandResp.class);
		List<Brand_P> x = brandResp.object;
		
		for(Brand_P b :x) {
			Brand brand= new Brand ();
			brand.setBrandId(b.getId().toString());
			brand.setBrandIconUrl(b.getIcon());
			brand.setBrandName(b.getName());
			brand.setDesc(b.getDescription());
			
			VehicheResp vehicheResp = null;
			vehicheResp = template.getForObject("http://139.196.236.34:8778/equipment/esApi/getProductByBrand/{id}", VehicheResp.class,b.getId().toString());
			
			Set<Image> images = new HashSet<Image>();
			
			for(VehicleInfo_P v:vehicheResp.object) {
				Image i = new Image();
				i.setImageId(v.getId().toString());
				i.setImageDesc("brand:"+v.getBrandName()+".category:"+v.getCategoryName());
				
				
				List<Component> componets = JSON.parseArray(v.getComponentInfo(), Component.class);  
				
				for(Component c :componets) {
					Image detail = new Image();
					detail.setImageId(UUID.randomUUID().toString().replaceAll("-", ""));
					detail.setImageDesc(c.name);
					detail.setImageName(c.name);
					detail.setImageType("DETAIL");
					detail.setImageUrl(c.image.src);
					detail.setParentImageId(i.getImageId());
					imageRepo.save(detail);
				}
				
				i.setImageName(v.getProductCategory());
				i.setImageType("WHOLE");
				i.setImageUrl(v.getImageUrl1());
				i.setParentImageId(null);
				images.add(i);
				
				imageRepo.save(i);
			}
			brand.setImages(images);
			brandRepo.save(brand);
		}
		
		return ResponseGenerator.createSuccessResponse("Db sych success.");

	}
}



