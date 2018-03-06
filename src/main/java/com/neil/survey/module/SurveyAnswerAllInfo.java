package com.neil.survey.module;

import java.io.Serializable;
import javax.persistence.*;





/**
 * The persistent class for the SURVEY_ANSWER_ALL_INFO database table.
 * 
 */
@Entity
@Table(name="SURVEY_ANSWER_ALL_INFO")
@NamedQuery(name="SurveyAnswerAllInfo.findAll", query="SELECT s FROM SurveyAnswerAllInfo s")
public class SurveyAnswerAllInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	private String brand;


	@Column(name="IMAGE_ID")
	private String imageId;

	@Column(name="IMAGE_NAME")
	private String imageName;

	private String model;

	@Column(name="REPLYER_POSITION")
	private String replyerPosition;

	@Column(name="STYLE_KEYWORD")
	private String styleKeyword;
	
	@Id
	@Column(name="SURVEY_ID")
	private String surveyId;

	private String texture;

	@Column(name="THUMB_URL")
	private String thumbUrl;

	private String year;

	public SurveyAnswerAllInfo() {
	}

	public String getBrand() {
		return this.brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getImageId() {
		return this.imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

	public String getImageName() {
		return this.imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public String getModel() {
		return this.model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getReplyerPosition() {
		return this.replyerPosition;
	}

	public void setReplyerPosition(String replyerPosition) {
		this.replyerPosition = replyerPosition;
	}

	public String getStyleKeyword() {
		return this.styleKeyword;
	}

	public void setStyleKeyword(String styleKeyword) {
		this.styleKeyword = styleKeyword;
	}

	public String getSurveyId() {
		return this.surveyId;
	}

	public void setSurveyId(String surveyId) {
		this.surveyId = surveyId;
	}

	public String getTexture() {
		return this.texture;
	}

	public void setTexture(String texture) {
		this.texture = texture;
	}

	public String getThumbUrl() {
		return this.thumbUrl;
	}

	public void setThumbUrl(String thumbUrl) {
		this.thumbUrl = thumbUrl;
	}

	public String getYear() {
		return this.year;
	}

	public void setYear(String year) {
		this.year = year;
	}

}