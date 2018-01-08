package com.neil.survey.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import com.neil.survey.module.Survey;


public interface SurveyRepository extends JpaRepository<Survey,String> {
	void delete(Survey s);
	void deleteBySurveyId(String surveyId);
	Survey getBySurveyId(String surveyId);
	Page<Survey> findAll(Specification<Survey> spec, Pageable pageable);
}
