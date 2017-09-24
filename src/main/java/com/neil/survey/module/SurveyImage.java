package com.neil.survey.module;

import java.io.Serializable;

import javax.persistence.*;

@SuppressWarnings("serial")
@Entity
@Table(name = "SURVEY_IMAGE")
public class SurveyImage implements Serializable{
	@Id
	@Column(length = 32)
	private String surveyId;

	@Id
	@Column(length = 32)
	private String imageId;

	public String getSurveyId() {
		return surveyId;
	}

	public void setSurveyId(String surveyId) {
		this.surveyId = surveyId;
	}

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

}
