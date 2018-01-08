package com.neil.survey.module;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "ANSWER_IMAGE")
public class AnswerImage implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2383594713266955979L;
	@Id
	@Column(length = 32)
	private String answerId;
	@Id
	@Column(length = 32)
	private String imageId;
	public String getAnswerId() {
		return answerId;
	}
	public void setAnswerId(String answerId) {
		this.answerId = answerId;
	}
	public String getImageId() {
		return imageId;
	}
	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

}
