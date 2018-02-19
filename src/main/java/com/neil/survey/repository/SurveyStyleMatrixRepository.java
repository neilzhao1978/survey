package com.neil.survey.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.neil.survey.module.SurveyStyleMatrix;

public interface SurveyStyleMatrixRepository extends JpaRepository<SurveyStyleMatrix,String> {
	List<SurveyStyleMatrix> findBySurveyId(String surveyId);
}
