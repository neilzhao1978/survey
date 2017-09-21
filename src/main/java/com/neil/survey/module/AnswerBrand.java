package com.neil.survey.module;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "ANSWER_BRAND")
public class AnswerBrand implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3680228117467769516L;
	@Id
	@Column(length = 32)
	private String answerId;
	@Id
	@Column(length = 32)
	private String brandId;
	public String getAnswerId() {
		return answerId;
	}
	public void setAnswerId(String answerId) {
		this.answerId = answerId;
	}
	public String getBrandId() {
		return brandId;
	}
	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}
}
