package com.neil.survey.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.neil.survey.module.SurveyAnswerAllInfo;

public interface SurveyAnswerAllInfoRepository extends JpaRepository<SurveyAnswerAllInfo,String> {
	List<SurveyAnswerAllInfo> findBySurveyId(String surveyId);
}
