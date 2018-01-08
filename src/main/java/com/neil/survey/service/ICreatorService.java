package com.neil.survey.service;

import com.neil.survey.module.Creator;

public interface ICreatorService {
	public int addCreator(Creator creator);
	public int updateCreator(Creator creator);
	public int deleteCreator(Creator creator);
	public Creator getCreator(String email);

}
