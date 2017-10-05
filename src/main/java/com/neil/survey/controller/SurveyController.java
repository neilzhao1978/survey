package com.neil.survey.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/api/surveyService")
public class SurveyController {
	
	@Autowired
	private CreatorRepository creatorRepo;
	@Autowired
	private SurveyRepository surveyRepo;
	@Autowired
	private AnswerRepository answerRepo;
	@Autowired
	private BrandRepository brandRepo;
	@Autowired
	private ImageRepository imageRepo;

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
				s.getCreator().setSurveys(null);
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
			return ResponseGenerator.createSuccessResponse("Add/update creator  success.");
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
	
}
