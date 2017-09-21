package com.neil.survey.module;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "SURVEY_BRAND")
public class SurveyBrand implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8092987450013710130L;
	@Id
	@Column(length = 32)
	private String surveyId;
	@Id
	@Column(length = 32)
	private String brandId;
	public String getSurveyId() {
		return surveyId;
	}
	public void setSurveyId(String surveyId) {
		this.surveyId = surveyId;
	}
	public String getBrandId() {
		return brandId;
	}
	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}


}
