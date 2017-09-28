package com.neil.survey.service;

import com.neil.survey.module.Answer;

public interface IAnswerService {
	public void createAnswer(Answer answer);
	public void deleteAnswer(Answer answer);
	public void updateAnswer(Answer answer);
	public void findAnswer(String Answer);
	
}
