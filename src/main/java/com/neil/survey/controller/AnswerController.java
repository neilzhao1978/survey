package com.neil.survey.controller;

import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

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

import com.neil.survey.module.Answer;
import com.neil.survey.module.Survey;
import com.neil.survey.repository.AnswerRepository;
import com.neil.survey.repository.SurveyRepository;
import com.neil.survey.util.ErrorCode;
import com.neil.survey.util.PageEntity;
import com.neil.survey.util.ResponseGenerator;
import com.neil.survey.util.RestResponseEntity;

@RestController
@RequestMapping("/api/answerService")
@Transactional
public class AnswerController {
	@Autowired
	private SurveyRepository surveyRepo;
	@Autowired
	private AnswerRepository answerRepo;
	
	@ResponseBody
	@RequestMapping(value = "/getAllAnswerList", method = RequestMethod.GET)
	public RestResponseEntity<List<Answer>> listSurvey( @RequestParam(value = "page",required=true) PageEntity page,
			@RequestParam(value = "surveyId",required=true) String surveyId){

		PageRequest pageRequest = new PageRequest(page.getPageNumber()-1, page.getPageSize(), null);
		ExampleMatcher matcher = ExampleMatcher.matching()
				  .withMatcher("survey", match -> match.endsWith());
		
		Survey survey = surveyRepo.getBySurveyId(surveyId);
		Answer a = new Answer();
		a.setSurvey(survey);
		Example<Answer> example = Example.of(a, matcher);
		Page<Answer> answers = answerRepo.findAll(example, pageRequest);
		if(answers!=null) {
			return ResponseGenerator.createSuccessResponse("获取问卷回复列表成功。", answers.getContent().size(), answers.getContent(),answers.getTotalElements());
		}else {
			return ResponseGenerator.createFailResponse("获取问卷回复列表失败。", ErrorCode.DB_ERROR);
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/addAnswer", method = RequestMethod.POST)
	public RestResponseEntity<Void> addAnswer( @RequestBody Answer answer){
		answer.setAnswerId(UUID.randomUUID().toString().replaceAll("-", ""));
		Survey s = surveyRepo.getBySurveyId(answer.getSurvey().getSurveyId());

		if(s!=null){
			answer.setSurvey(s);
		}
			
		Answer a = answerRepo.save(answer);
		if(a!=null) {
			
			int crtCount = s.getAnswerCount()==null?0:s.getAnswerCount();
			crtCount+=1;
			s.setAnswerCount(crtCount);
			surveyRepo.save(s);
			return ResponseGenerator.createSuccessResponse("新增问卷回复成功。");
		}else {
			return ResponseGenerator.createFailResponse("新增问卷回复失败。", ErrorCode.DB_ERROR);
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/updateAnswer", method = RequestMethod.POST)
	public RestResponseEntity<Void> updateAnswer( @RequestBody Answer answer){
		Answer a = answerRepo.save(answer);
		if(a!=null) {
			return ResponseGenerator.createSuccessResponse("更新问卷回复成功。");
		}else {
			return ResponseGenerator.createFailResponse("更新问卷回复失败。", ErrorCode.DB_ERROR);
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/deleteAnswer", method = RequestMethod.DELETE)
	public RestResponseEntity<Void> deleteAnswer( @RequestBody Answer answer){
		try {
			answer.setSurvey(null);
			answerRepo.save(answer);
			answerRepo.delete(answer);
			return ResponseGenerator.createSuccessResponse("删除问卷回复成功。");
		}catch(Exception e) {
			return ResponseGenerator.createFailResponse("删除问卷回复失败。", ErrorCode.DB_ERROR);			
		}
	}
	
}
