package com.neil.survey.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.neil.survey.module.Survey;


public interface SurveyRepository extends JpaRepository<Survey,String> {
	void delete(Survey s);
	
	Survey getBySurveyId(String surveyId);

}
