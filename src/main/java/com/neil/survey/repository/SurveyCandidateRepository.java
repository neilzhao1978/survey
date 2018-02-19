package com.neil.survey.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.neil.survey.module.SurveyCandidate;

public interface SurveyCandidateRepository extends JpaRepository<SurveyCandidate,String> {
	List<SurveyCandidate> findBySurveyId(String surveyId);
}
