package com.neil.survey.module;

import java.io.Serializable;

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
