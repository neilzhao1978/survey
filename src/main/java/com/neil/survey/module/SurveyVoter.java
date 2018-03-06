package com.neil.survey.module;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the SURVEY_VOTERS database table.
 * 
 */
@Entity
@Table(name="SURVEY_VOTERS")
@NamedQuery(name="SurveyVoter.findAll", query="SELECT s FROM SurveyVoter s")
public class SurveyVoter implements Serializable {
	private static final long serialVersionUID = 1L;

	private long designer;

	private long engineer;

	private long manager;

	private long sale;

	@Id
	@Column(name="SURVEY_ID")
	private String surveyId;

	private long total;

	public SurveyVoter() {
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

	public long getManager() {
		return this.manager;
	}

	public void setManager(long manager) {
		this.manager = manager;
	}

	public long getSale() {
		return this.sale;
	}

	public void setSale(long sale) {
		this.sale = sale;
	}

	public String getSurveyId() {
		return this.surveyId;
	}

	public void setSurveyId(String surveyId) {
		this.surveyId = surveyId;
	}

	public long getTotal() {
		return this.total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

}