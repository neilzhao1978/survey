package com.neil.survey.module;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "IMAGE")
public class Image implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6796133803560587005L;
	@Id
	@Column(length = 32)
//	@GenericGenerator(name = "system-uuid", strategy = "uuid")
//	@GeneratedValue(generator = "system-uuid")
	private String imageId;
	private String imageName;
	private String imageType;
	private String imageUrl;
	private String parentImageId;
	private boolean containFeatureLine;


	@Column(length = 10240)
	private String allKeyPoints;
	@Column(length = 10240)
	private String imageDesc;
	public String getImageId() {
		return imageId;
	}

	private int x;
	private int y;
	private int w;
	private int h;
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getW() {
		return w;
	}
	public void setW(int w) {
		this.w = w;
	}
	public int getH() {
		return h;
	}
	public void setH(int h) {
		this.h = h;
	}
	public boolean getContainFeatureLine() {
		return containFeatureLine;
	}
	public void setContainFeatureLine(boolean containFeatureLine) {
		this.containFeatureLine = containFeatureLine;
	}
	public String getAllKeyPoints() {
		return allKeyPoints;
	}
	public void setAllKeyPoints(String allKeyPoints) {
		this.allKeyPoints = allKeyPoints;
	}
//    @ManyToMany(mappedBy = "images",fetch = FetchType.LAZY)
//	private Set<Answer> answers;
//
//    @ManyToMany(mappedBy = "images",fetch = FetchType.LAZY)
//	private Set<Answer> brands;
//
//    @ManyToMany(mappedBy = "images",fetch = FetchType.LAZY)
//	private Set<Survey> surveys;
    
//	public Set<Answer> getAnswers() {
//		return answers;
//	}
//	public void setAnswers(Set<Answer> answers) {
//		this.answers = answers;
//	}
//	public Set<Answer> getBrands() {
//		return brands;
//	}
//	public void setBrands(Set<Answer> brands) {
//		this.brands = brands;
//	}
//	public Set<Survey> getSurveys() {
//		return surveys;
//	}
//	public void setSurveys(Set<Survey> surveys) {
//		this.surveys = surveys;
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

	public String getParentImageId() {
		return parentImageId;
	}

	public void setParentImageId(String parentImageId) {
		this.parentImageId = parentImageId;
	}

	public String getImageDesc() {
		return imageDesc;
	}

	public void setImageDesc(String imageDesc) {
		this.imageDesc = imageDesc;
	}

}
