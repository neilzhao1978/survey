package com.neil.survey.module;

import java.io.Serializable;
import javax.persistence.*;




/**
 * The persistent class for the SURVEY_STYLE_MATRIX database table.
 * 
 */
@Entity
@Table(name="SURVEY_STYLE_MATRIX")
@NamedQuery(name="SurveyStyleMatrix.findAll", query="SELECT s FROM SurveyStyleMatrix s")
public class SurveyStyleMatrix implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="SURVEY_ID")
	private String surveyId;

	private long x1;

	private long x2;

	private long y1;

	private long y2;

	private long z1;

	private long z2;

	public SurveyStyleMatrix() {
	}

	public String getSurveyId() {
		return this.surveyId;
	}

	public void setSurveyId(String surveyId) {
		this.surveyId = surveyId;
	}

	public long getX1() {
		return this.x1;
	}

	public void setX1(long x1) {
		this.x1 = x1;
	}

	public long getX2() {
		return this.x2;
	}

	public void setX2(long x2) {
		this.x2 = x2;
	}

	public long getY1() {
		return this.y1;
	}

	public void setY1(long y1) {
		this.y1 = y1;
	}

	public long getY2() {
		return this.y2;
	}

	public void setY2(long y2) {
		this.y2 = y2;
	}

	public long getZ1() {
		return this.z1;
	}

	public void setZ1(long z1) {
		this.z1 = z1;
	}

	public long getZ2() {
		return this.z2;
	}

	public void setZ2(long z2) {
		this.z2 = z2;
	}

}