package com.neil.survey.module;
// default package
// Generated 2017-4-14 17:05:01 by Hibernate Tools 4.3.1.Final

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

public class VehicleInfo_P implements java.io.Serializable {


	private static final long serialVersionUID = 1L;
	private Integer id;
	private int categoryId;
	private String categoryName;
	private int brandId;
	private String brandName;
	private byte entry;
	private String imageUrl1;
	private String imageUrl2;
	private String style;
	private String modelUrl;
	private String onSaleDate;
	private Date createTime;
	private String componentInfo;
	private String productCategory;
	private String videoUrl;
//	private Set<VehicleColor> vehicleColors = new HashSet<VehicleColor>(0);
//	private Set<VehicleTexture> vehicleTextures = new HashSet<VehicleTexture>(0);

	public VehicleInfo_P() {
	}
	
	public VehicleInfo_P(int id, String imageUrl1,String imageUrl2,String productCategory){
		this.id = id;
		this.imageUrl1 = imageUrl1;
		this.imageUrl2 = imageUrl2;
		this.productCategory = productCategory;
	}

	public VehicleInfo_P(int categoryId, int brandId, byte entry, String style, Date createTime, String componentInfo) {
		this.categoryId = categoryId;
		this.brandId = brandId;
		this.entry = entry;
		this.style = style;
		this.createTime = createTime;
		this.componentInfo = componentInfo;
	}

//	public VehicleInfo_P(int categoryId, int brandId, byte entry, String imageUrl1, String imageUrl2, String style,
//			String modelUrl, String onSaleDate, Date createTime, String componentInfo, Set<VehicleColor> vehicleColors,
//			Set<VehicleTexture> vehicleTextures) {
//		this.categoryId = categoryId;
//		this.brandId = brandId;
//		this.entry = entry;
//		this.imageUrl1 = imageUrl1;
//		this.imageUrl2 = imageUrl2;
//		this.style = style;
//		this.modelUrl = modelUrl;
//		this.onSaleDate = onSaleDate;
//		this.createTime = createTime;
//		this.componentInfo = componentInfo;
//		this.vehicleColors = vehicleColors;
//		this.vehicleTextures = vehicleTextures;
//	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getCategoryId() {
		return this.categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public int getBrandId() {
		return this.brandId;
	}

	public void setBrandId(int brandId) {
		this.brandId = brandId;
	}

	public byte getEntry() {
		return this.entry;
	}

	public void setEntry(byte entry) {
		this.entry = entry;
	}

	public String getImageUrl1() {
		return this.imageUrl1;
	}

	public void setImageUrl1(String imageUrl1) {
		this.imageUrl1 = imageUrl1;
	}

	public String getImageUrl2() {
		return this.imageUrl2;
	}

	public void setImageUrl2(String imageUrl2) {
		this.imageUrl2 = imageUrl2;
	}

	public String getStyle() {
		return this.style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getModelUrl() {
		return this.modelUrl;
	}

	public void setModelUrl(String modelUrl) {
		this.modelUrl = modelUrl;
	}

	public String getOnSaleDate() {
		return this.onSaleDate;
	}

	public void setOnSaleDate(String onSaleDate) {
		this.onSaleDate = onSaleDate;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getComponentInfo() {
		return this.componentInfo;
	}

	public void setComponentInfo(String componentInfo) {
		this.componentInfo = componentInfo;
	}

//	public Set<VehicleColor> getVehicleColors() {
//		return this.vehicleColors;
//	}
//
//	public void setVehicleColors(Set<VehicleColor> vehicleColors) {
//		this.vehicleColors = vehicleColors;
//	}
//
//	public Set<VehicleTexture> getVehicleTextures() {
//		return this.vehicleTextures;
//	}
//
//	public void setVehicleTextures(Set<VehicleTexture> vehicleTextures) {
//		this.vehicleTextures = vehicleTextures;
//	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	
}
