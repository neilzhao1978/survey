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

import com.neil.survey.module.Creator;
import com.neil.survey.repository.CreatorRepository;
import com.neil.survey.util.ErrorCode;
import com.neil.survey.util.PageEntity;
import com.neil.survey.util.ResponseGenerator;
import com.neil.survey.util.RestResponseEntity;


@RestController
@RequestMapping("/api/creatorService")
public class CCreatorController {

	@Autowired
	private CreatorRepository creatorRepository;
	
	@ResponseBody
	@RequestMapping(value = "/getAllCreatorList", method = RequestMethod.GET)
	public RestResponseEntity<List<Creator>> getAllCreatorList(@RequestBody @RequestParam("page") PageEntity page){
		
		PageRequest pageRequest = new PageRequest(page.getPageNumber(), page.getPageSize(), null);
		Page<Creator> creators = creatorRepository.findAll(pageRequest);

		if(creators!=null && creators.getSize()>0) {
			for(Creator c:creators.getContent()) {
				c.setSurveys(null);
			}
			return ResponseGenerator.createSuccessResponse("Get creator list success.", creators.getContent().size(), creators.getContent(),creators.getTotalElements());
		}else {
			return ResponseGenerator.createFailResponse("Fail to get creator list", ErrorCode.DB_ERROR);
		}
	}
	
	

}
