package com.neil.survey.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.neil.survey.module.SurveyImageResult;

public interface SurveyImageResultRepository extends JpaRepository<SurveyImageResult,String> {
	List<SurveyImageResult> findBySurveyIdAndDesc(String surveyId,String desc);
}
