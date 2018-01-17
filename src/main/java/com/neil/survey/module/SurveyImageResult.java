package com.neil.survey.module;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;




class SurveyImageResultID implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3419651007408234679L;
	String surveyId;	
	String imageId;
}


@Entity
@IdClass(SurveyImageResultID.class)
@Table(name = "SURVEY_IMAGE_RESULT")
public class SurveyImageResult implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6778811545193744542L;

	@Id
	@Column(length = 32)
	private String surveyId;

	@Id
	@Column(length = 32)
	private String imageId;
	
	private int cnt;
	
	private String desc;

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

	public int getCnt() {
		return cnt;
	}

	public void setCnt(int cnt) {
		this.cnt = cnt;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
}
