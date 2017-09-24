package com.neil.survey.module;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "CREATOR_SURVEY")
public class CreatorSurvey implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5772377904666152761L;
	@Id
	@Column(length = 32)
	private String surveyId;
	@Id
	@Column(length = 32)
	private String creatorId;
	public String getSurveyId() {
		return surveyId;
	}
	public void setSurveyId(String surveyId) {
		this.surveyId = surveyId;
	}
	public String getCreatorId() {
		return creatorId;
	}
	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}


}
