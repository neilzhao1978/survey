package com.neil.survey.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neil.survey.repository.AnswerRepository;
import com.neil.survey.repository.BrandRepository;
import com.neil.survey.repository.CreatorRepository;
import com.neil.survey.repository.ImageRepository;
import com.neil.survey.repository.SurveyRepository;

@RestController
@RequestMapping("/api/imageService")
public class ImageController {
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

}
