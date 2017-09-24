package com.neil.survey.module;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "BRAND_IMAGE")
public class BrandImage implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -662714721339894221L;
	@Id
	@Column(length = 32)
	private String brandId;
	@Id
	@Column(length = 32)
	private String imageId;
	public String getBrandId() {
		return brandId;
	}
	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}
	public String getImageId() {
		return imageId;
	}
	public void setImageId(String imageId) {
		this.imageId = imageId;
	}
	@Override
	public String toString() {
		return "BrandImage [brandId=" + brandId + ", imageId=" + imageId + "]";
	}
}
