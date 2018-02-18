package com.neil.survey.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.neil.survey.module.SurveyVoter;

public interface SurveyVoterRepository extends JpaRepository<SurveyVoter,String> {
	List<SurveyVoter> findBySurveyId(String surveyId);
}
