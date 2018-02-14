package com.neil.survey.module.stat;

import java.io.Serializable;
import javax.persistence.*;

class SurveyCandidateID implements Serializable{
	private static final long serialVersionUID = -1075434946176236462L;
	String surveyId;	
	String imageId;
}


/**
 * The persistent class for the SURVEY_CANDIDATES database table.
 * 
 */
@Entity
@IdClass(SurveyCandidateID.class)
@Table(name="SURVEY_CANDIDATES")
@NamedQuery(name="SurveyCandidate.findAll", query="SELECT s FROM SurveyCandidate s")
public class SurveyCandidate implements Serializable {
	private static final long serialVersionUID = 1L;

	private String brand;

	private long designer;

	private long engineer;
	
	@Id
	@Column(name="IMAGE_ID")
	private String imageId;

	private long manager;

	private String model;

	private long sale;

	@Column(name="STYLE_KEYWORD")
	private String styleKeyword;
	
	@Id
	@Column(name="SURVEY_ID")
	private String surveyId;

	private String texture;

	@Column(name="THUMB_URL")
	private String thumbUrl;

	private long total;

	private String year;

	public SurveyCandidate() {
	}

	public String getBrand() {
		return this.brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public long getDesigner() {
		return this.designer;
	}

	public void setDesigner(long designer) {
		this.designer = designer;
	}

	public long getEngineer() {
		return this.engineer;
	}

	public void setEngineer(long engineer) {
		this.engineer = engineer;
	}

	public String getImageId() {
		return this.imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

	public long getManager() {
		return this.manager;
	}

	public void setManager(long manager) {
		this.manager = manager;
	}

	public String getModel() {
		return this.model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public long getSale() {
		return this.sale;
	}

	public void setSale(long sale) {
		this.sale = sale;
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

	public long getTotal() {
		return this.total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public String getYear() {
		return this.year;
	}

	public void setYear(String year) {
		this.year = year;
	}

}