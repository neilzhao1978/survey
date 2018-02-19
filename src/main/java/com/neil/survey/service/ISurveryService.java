package com.neil.survey.service;

import com.neil.survey.inputout.stat.DummySurveyData;

public interface ISurveryService {
	DummySurveyData getSurveyStat(String surveyId);
}
