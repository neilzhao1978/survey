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
@Table(name = "BRAND")
public class Brand implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -662714721339894221L;
	@Id
	@Column(length = 32)
//	@GenericGenerator(name="system-uuid", strategy="uuid")
//	@GeneratedValue(generator = "system-uuid")
	private String brandId;
	private String brandName;
	private String brandIconUrl;
	private String desc;
	
    //@ManyToMany注释表示Student是多对多关系的一边，mappedBy属性定义了Student为双向关系的维护端
    //Teacher表是关系的维护者，owner side，有主导权，它有个外键指向Student表。 
//    @ManyToMany(mappedBy = "brands",fetch = FetchType.LAZY)
//	private Set<Answer> answers;

//    @ManyToMany(mappedBy = "brands",fetch = FetchType.LAZY)
//	private Set<Survey> surveys;
    
	@ManyToMany(cascade={CascadeType.DETACH,CascadeType.PERSIST,CascadeType.REFRESH},fetch = FetchType.LAZY)
    @JoinTable(name="BRAND_IMAGE",
      joinColumns={@JoinColumn(name="brandId",referencedColumnName="brandId")},
      inverseJoinColumns={@JoinColumn(name="imageId",referencedColumnName="imageId")})
	private Set<Image> images;
    

	public Brand deepClone() throws IOException, OptionalDataException, ClassNotFoundException {// 将对象写到流里
		ByteArrayOutputStream bo = new ByteArrayOutputStream();
		ObjectOutputStream oo = new ObjectOutputStream(bo);
		oo.writeObject(this);// 从流里读出来
		ByteArrayInputStream bi = new ByteArrayInputStream(bo.toByteArray());
		ObjectInputStream oi = new ObjectInputStream(bi);
		oo.close();
		bo.close();

		return ((Brand) oi.readObject());
	}
	
//    public Set<Answer> getAnswers() {
//		return answers;
//	}
//	public void setAnswers(Set<Answer> answers) {
//		this.answers = answers;
//	}
//	public Set<Survey> getSurveys() {
//		return surveys;
//	}
//	public void setSurveys(Set<Survey> surveys) {
//		this.surveys = surveys;
//	}
	public Set<Image> getImages() {
		return images;
	}
	public void setImages(Set<Image> images) {
		this.images = images;
	}

	
	
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
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}

	


}
