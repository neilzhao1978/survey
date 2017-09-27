package com.neil.survey.module;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
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
	private Date releaseTime;
	private String status;

	@ManyToOne(cascade=CascadeType.ALL,optional = true)
	@JoinColumn(name="email")
	private Creator creator;
	
	public Creator getCreator() {
		return creator;
	}
	public void setCreator(Creator creator) {
		this.creator = creator;
	}
	
	@OneToMany(cascade=CascadeType.ALL,mappedBy ="survey",fetch = FetchType.EAGER)
	private Set<Answer> answers = new HashSet<Answer>();
	
	public void addAnswer(Answer answer) {
		answer.setSurvey(this);
		this.answers.add(answer);
	}
	
	@ManyToMany(cascade=CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinTable(name="SURVEY_BRAND",
      joinColumns={@JoinColumn(name="surveyId",referencedColumnName="surveyId")},
      inverseJoinColumns={@JoinColumn(name="brandId",referencedColumnName="brandId")})
	private Set<Brand> brands;
//	
//    @ManyToMany(cascade=CascadeType.ALL)
//    @JoinTable(name="SURVEY_IMAGE",
//      joinColumns={@JoinColumn(name="surveyId",referencedColumnName="surveyId")},
//      inverseJoinColumns={@JoinColumn(name="imageId",referencedColumnName="imageId")})
//	private Set<Image> images;
    
//	public Set<Creator> getCreator() {
//		return creator;
//	}
//	public void setCreator(Set<Creator> creator) {
//		this.creator = creator;
//	}
//	public Set<Answer> getAnswers() {
//		return answers;
//	}
//	public void setAnswers(Set<Answer> answers) {
//		this.answers = answers;
//	}
	public Set<Brand> getBrands() {
		return brands;
	}
	public void setBrands(Set<Brand> brands) {
		this.brands = brands;
	}
//	public Set<Image> getImages() {
//		return images;
//	}
//	public void setImages(Set<Image> images) {
//		this.images = images;
//	}
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
	public Date getReleaseTime() {
		return releaseTime;
	}
	public void setReleaseTime(Date releaseTime) {
		this.releaseTime = releaseTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
//	public String getEmail() {
//		return email;
//	}
//	public void setEmail(String email) {
//		this.email = email;
//	}


}
