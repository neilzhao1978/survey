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
	@GeneratedValue(generator = "system-uuid")
	private String answerId;
	private String replyerName;
	private String replyerPosition;
	private String replyTime;
	
	
	public Set<Brand> getBrands() {
		return brands;
	}

	public void setBrands(Set<Brand> brands) {
		this.brands = brands;
	}

	public void setImages(Set<Image> images) {
		this.images = images;
	}

	
    @ManyToMany(cascade= {CascadeType.DETACH,CascadeType.PERSIST,CascadeType.REFRESH,CascadeType.REMOVE},fetch = FetchType.LAZY)
    @JoinTable(name="ANSWER_BRAND",
      joinColumns={@JoinColumn(name="answerId",referencedColumnName="answerId")},
      inverseJoinColumns={@JoinColumn(name="brandId",referencedColumnName="brandId")})
    private Set<Brand> brands;
	
    @ManyToMany(cascade={CascadeType.DETACH,CascadeType.PERSIST,CascadeType.REFRESH,CascadeType.REMOVE},fetch = FetchType.LAZY)
    @JoinTable(name="ANSWER_IMAGE",
      joinColumns={@JoinColumn(name="answerId",referencedColumnName="answerId")},
      inverseJoinColumns={@JoinColumn(name="imageId",referencedColumnName="imageId")})
	private Set<Image> images;
    
    
	@ManyToOne(cascade=CascadeType.ALL,optional = true,fetch = FetchType.LAZY)
	@JoinColumn(name="surveyId")
	private Survey survey;
    
	public Survey getSurvey() {
		return survey;
	}

	public void setSurvey(Survey survey) {
		this.survey = survey;
	}

	public String getReplyerName() {
		return replyerName;
	}

	public void setReplyerName(String replyerName) {
		this.replyerName = replyerName;
	}

	public String getReplyerPosition() {
		return replyerPosition;
	}

	public void setReplyerPosition(String replyerPosition) {
		this.replyerPosition = replyerPosition;
	}

	public Set<Image> getImages() {
		return images;
	}

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

}
