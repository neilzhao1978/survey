package com.neil.survey.module;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
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
	private String pngImageUrl;
	private String parentImageId;
	private Boolean containFeatureLine;


	private float imageStyleX;
	private float imageStyleY;
	private float imageStyleZ;


	public float getImageStyleX() {
		return imageStyleX;
	}

	public void setImageStyleX(float imageStyleX) {
		this.imageStyleX = imageStyleX;
	}

	public float getImageStyleY() {
		return imageStyleY;
	}

	public void setImageStyleY(float imageStyleY) {
		this.imageStyleY = imageStyleY;
	}

	public float getImageStyleZ() {
		return imageStyleZ;
	}

	public void setImageStyleZ(float imageStyleZ) {
		this.imageStyleZ = imageStyleZ;
	}

	private String thumbUrl;//缩略图
	private String featureUrl;//特征线
	private String profileImageUrl;//剪影
	
	@Column(length = 20480)
	private String allKeyPoints;
	@Column(length = 10240)
	private String imageDesc;
	public String getImageId() {
		return imageId;
	}

	private Integer x;
	private Integer y;
	
	public Integer getLX(){
		if(x==null || w==null) return 0;
		return x+w;
	}

	public Integer getBY(){
		if(y==null || h==null) return 0;
		return y+h;
	}
	
	public Integer getX() {
		return x;
	}
	public void setX(Integer x) {
		this.x = x;
	}
	public Integer getY() {
		return y;
	}
	public void setY(Integer y) {
		this.y = y;
	}
	public Integer getW() {
		return w;
	}
	public void setW(Integer w) {
		this.w = w;
	}
	public Integer getH() {
		return h;
	}
	public void setH(Integer h) {
		this.h = h;
	}

	private Integer w;
	private Integer h;
	

	public Boolean getContainFeatureLine() {
		return containFeatureLine;
	}
	public void setContainFeatureLine(Boolean containFeatureLine) {
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
	
	public Image deepClone() throws IOException, OptionalDataException, ClassNotFoundException {// 将对象写到流里
		ByteArrayOutputStream bo = new ByteArrayOutputStream();
		ObjectOutputStream oo = new ObjectOutputStream(bo);
		oo.writeObject(this);// 从流里读出来
		ByteArrayInputStream bi = new ByteArrayInputStream(bo.toByteArray());
		ObjectInputStream oi = new ObjectInputStream(bi);
		oo.close();
		bo.close();

		return ((Image) oi.readObject());
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
	public String getFeatureUrl() {
		return featureUrl;
	}
	public void setFeatureUrl(String featureUrl) {
		this.featureUrl = featureUrl;
	}
	public String getProfileImageUrl() {
		return profileImageUrl;
	}
	public void setProfileImageUrl(String profileImageUrl) {
		this.profileImageUrl = profileImageUrl;
	}

	public String getPngImageUrl() {
		return pngImageUrl;
	}

	public void setPngImageUrl(String pngImageUrl) {
		this.pngImageUrl = pngImageUrl;
	}

	public String getThumbUrl() {
		return thumbUrl;
	}

	public void setThumbUrl(String thumbUrl) {
		this.thumbUrl = thumbUrl;
	}
	
	private String brand;
	private String moduel;
	private String year;
	private String style_keyword;
	private String texture;
	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getModuel() {
		return moduel;
	}

	public void setModuel(String moduel) {
		this.moduel = moduel;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getStyle_keyword() {
		return style_keyword;
	}

	public void setStyle_keyword(String style_keyword) {
		this.style_keyword = style_keyword;
	}

	public String getTexture() {
		return texture;
	}

	public void setTexture(String texture) {
		this.texture = texture;
	}
}
