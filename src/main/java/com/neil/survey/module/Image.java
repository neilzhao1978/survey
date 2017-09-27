package com.neil.survey.module;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "IMAGE")
public class Image implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6796133803560587005L;
	@Id
	@Column(length = 32)
	private String imageId;
	private String imageName;
	private String imageType;
	private String imageUrl;
	public String getImageId() {
		return imageId;
	}
	
    @ManyToMany(mappedBy = "images")
	private Set<Answer> answers;

    @ManyToMany(mappedBy = "images")
	private Set<Answer> brands;

//    @ManyToMany(mappedBy = "images")
//	private Set<Survey> Surveys;
    
	public Set<Answer> getAnswers() {
		return answers;
	}
	public void setAnswers(Set<Answer> answers) {
		this.answers = answers;
	}
	public Set<Answer> getBrands() {
		return brands;
	}
	public void setBrands(Set<Answer> brands) {
		this.brands = brands;
	}
//	public Set<Survey> getSurveys() {
//		return Surveys;
//	}
//	public void setSurveys(Set<Survey> surveys) {
//		Surveys = surveys;
//	}
	@Override
	public String toString() {
		return "Image [imageId=" + imageId + ", imageName=" + imageName + ", imageType=" + imageType + ", imageUrl="
				+ imageUrl + "]";
	}
	public void setImageId(String imageId) {
		this.imageId = imageId;
	}
	public String getImageName() {
		return imageName;
	}
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	public String getImageType() {
		return imageType;
	}
	public void setImageType(String imageType) {
		this.imageType = imageType;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

}
