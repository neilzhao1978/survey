package com.neil.survey.module;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "BRAND")
public class Brand implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -662714721339894221L;
	@Id
	@Column(length = 32)
	private String brandId;
	private String brandName;
	private String brandIconUrl;
	public String getBrandId() {
		return brandId;
	}
	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}
	@Override
	public String toString() {
		return "Brand [brandId=" + brandId + ", brandName=" + brandName + ", brandIconUrl=" + brandIconUrl + "]";
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public String getBrandIconUrl() {
		return brandIconUrl;
	}
	public void setBrandIconUrl(String brandIconUrl) {
		this.brandIconUrl = brandIconUrl;
	}

	


}
