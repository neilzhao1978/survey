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

import com.neil.survey.module.Creator;
import com.neil.survey.module.Survey;
import com.neil.survey.repository.CreatorRepository;
import com.neil.survey.repository.SurveyRepository;
import com.neil.survey.util.ErrorCode;
import com.neil.survey.util.PageEntity;
import com.neil.survey.util.ResponseGenerator;
import com.neil.survey.util.RestResponseEntity;


@RestController
@RequestMapping("/api/creatorService")
public class CreatorController {

	@Autowired
	private CreatorRepository creatorRepository;
	
	@Autowired
	private SurveyRepository surveyRepo;
	
	@ResponseBody
	@RequestMapping(value = "/getAllCreatorList", method = RequestMethod.GET)
	public RestResponseEntity<List<Creator>> getAllCreatorList( @RequestParam(value = "page",required=true) PageEntity page,
			@RequestParam(value = "name",required=false) String name){
		
		PageRequest pageRequest = new PageRequest(page.getPageNumber(), page.getPageSize(), null);
		Page<Creator> creators = creatorRepository.findAll(pageRequest);

		if(creators!=null && creators.getSize()>0) {
//			for(Creator c:creators.getContent()) {
//				c.setSurveys(null);
//			}
			return ResponseGenerator.createSuccessResponse("Get creator list success.", creators.getContent().size(), creators.getContent(),creators.getTotalElements());
		}else {
			return ResponseGenerator.createFailResponse("Fail to get creator list", ErrorCode.DB_ERROR);
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/addCreator", method = RequestMethod.POST)
	public RestResponseEntity<Void> addCreator( @RequestBody Creator creator){
		Creator c = creatorRepository.save(creator);
		if(c!=null) {
			return ResponseGenerator.createSuccessResponse("Add/update creator  success.");
		}else {
			return ResponseGenerator.createFailResponse("Fail to add/update creator.", ErrorCode.DB_ERROR);
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/deleteCreator", method = RequestMethod.DELETE)
	public RestResponseEntity<Void> deleteCreator( @RequestBody Creator creator){
		try {			
			creatorRepository.delete(creator.getEmail());
			return ResponseGenerator.createSuccessResponse("delete creator  success.");
		}catch(Exception e) {			
			return ResponseGenerator.createFailResponse("Fail to add creator.", ErrorCode.DB_ERROR);
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/listSurvey", method = RequestMethod.GET)
	public RestResponseEntity<List<Survey>> listSurvey( @RequestParam(value = "page",required=true) PageEntity page,
			@RequestParam(value = "email",required=true) String email){

		PageRequest pageRequest = new PageRequest(page.getPageNumber(), page.getPageSize(), null);
		
		ExampleMatcher matcher = ExampleMatcher.matching()
				  .withMatcher("creator", match -> match.endsWith());
		
		Survey survey = new Survey();
		List<Creator> creators = creatorRepository.findByEmail(email);
		if(creators.size()>0) {
			survey.setCreator(creators.get(0));
			Example<Survey> example = Example.of(survey, matcher);
			
			Page<Survey> c = surveyRepo.findAll(example, pageRequest);
			if(c!=null) {
				return ResponseGenerator.createSuccessResponse("Get survey list success.", c.getContent().size(), c.getContent(),c.getTotalElements());
			}else {
				return ResponseGenerator.createFailResponse("Fail to get survey list.", ErrorCode.DB_ERROR);
			}
		}else {
			return ResponseGenerator.createFailResponse("Fail to get survey list.", ErrorCode.DB_ERROR);
		}
	}
	
}
