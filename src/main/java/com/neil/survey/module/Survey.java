package com.neil.survey.module;

import java.io.Serializable;
import java.util.Set;

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


	
	
    @OneToMany(cascade=CascadeType.ALL)
    @JoinTable(name="SURVEY_ANSWER",
      joinColumns={@JoinColumn(name="answerId",referencedColumnName="surveyId")},
      inverseJoinColumns={@JoinColumn(name="surveyId",referencedColumnName="answerId")})
	private Set<Answer> answers;

    @ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(name="SURVEY_BRAND",
      joinColumns={@JoinColumn(name="brandId",referencedColumnName="surveyId")},
      inverseJoinColumns={@JoinColumn(name="surveyId",referencedColumnName="brandId")})
	private Set<Brand> brands;
	
    @ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(name="SURVEY_IMAGE",
      joinColumns={@JoinColumn(name="imageId",referencedColumnName="surveyId")},
      inverseJoinColumns={@JoinColumn(name="surveyId",referencedColumnName="imageId")})
	private Set<Image> images;
    
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
