package com.neil.survey.module;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "SURVEY")
public class Survey implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4296614439470290787L;
	@Id
	@Column(length = 32)
	private String surveyId;
	private String name;
	private String releaseTime;
	private String status;
	public String getSurveyId() {
		return surveyId;
	}
	public void setSurveyId(String surveyId) {
		this.surveyId = surveyId;
	}
	public String getName() {
		return name;
	}
	@Override
	public String toString() {
		return "Survey [surveyId=" + surveyId + ", name=" + name + ", releaseTime=" + releaseTime + ", status=" + status
				+ "]";
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getReleaseTime() {
		return releaseTime;
	}
	public void setReleaseTime(String releaseTime) {
		this.releaseTime = releaseTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

}
