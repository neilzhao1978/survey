package com.neil.survey.module;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "ANSWER")
public class Answer implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6984068216709107924L;
	@Id
	@Column(length = 32)
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String answerId;
	private String replyerName;
	private String replyerPosition;
	private String replyTime;
	private String surveyId;
	
	
	public Set<Brand> getBrands() {
		return brands;
	}

	public void setBrands(Set<Brand> brands) {
		this.brands = brands;
	}

	public void setImages(Set<Image> images) {
		this.images = images;
	}

	
    @ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(name="ANSWER_BRAND",
      joinColumns={@JoinColumn(name="brandId",referencedColumnName="answerId")},
      inverseJoinColumns={@JoinColumn(name="answerId",referencedColumnName="brandId")})
    private Set<Brand> brands;
	
    @OneToMany(cascade=CascadeType.ALL)
    @JoinTable(name="ANSWER_IMAGE",
      joinColumns={@JoinColumn(name="imageId",referencedColumnName="answerId")},
      inverseJoinColumns={@JoinColumn(name="answerId",referencedColumnName="imageId")})
	private Set<Image> images;
    
	
//	@OneToMany(cascade = CascadeType.ALL)
//	AnswerBrand answerBrand;
//
//	@OneToMany(cascade = CascadeType.ALL)
//	AnswerBrand answerImage;
	
	@Override
	public String toString() {
		return "Answer [answerId=" + answerId + ", replyerName=" + replyerName + ", replyerPosition=" + replyerPosition
				+ ", replyTime=" + replyTime + "]";
	}
	public String getAnswerId() {
		return answerId;
	}
	public void setAnswerId(String answerId) {
		this.answerId = answerId;
	}
	public String getReplayerName() {
		return replyerName;
	}
	public void setReplayerName(String replayerName) {
		this.replyerName = replayerName;
	}
	public String getReplayerPosition() {
		return replyerPosition;
	}
	public void setReplayerPosition(String replayerPosition) {
		this.replyerPosition = replayerPosition;
	}
	public String getReplyTime() {
		return replyTime;
	}
	public void setReplyTime(String replyTime) {
		this.replyTime = replyTime;
	}
	public String getSurveyId() {
		return surveyId;
	}
	public void setSurveyId(String surveyId) {
		this.surveyId = surveyId;
	}


}
