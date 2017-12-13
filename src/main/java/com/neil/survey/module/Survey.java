package com.neil.survey.module;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "SURVEY")
public class Survey implements Serializable,Cloneable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4296614439470290787L;
	@Id
	@Column(length = 32)
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@GeneratedValue(generator = "system-uuid")
	private String surveyId;
	
	private String name;
	private Date releaseTime;
	private String status;

	@Column(nullable=true)
	private Integer maxUserBrandCount;
	@Column(nullable=true)
	private Integer maxUserIndustryImageCount=1;
	@Column(nullable=true)
	private Integer maxUserAnimalImageCount=1;
	@Column(nullable=true)
	private Integer maxUserBuildingImageCount=1;
	@Column(nullable=true)
	private Integer maxUserArtImageCount=1;
	@Column(nullable=true)
	private Integer maxUserOthersImageCount=1;
	
	public Integer getMaxUserIndustryImageCount() {
		return maxUserIndustryImageCount;
	}
	public void setMaxUserIndustryImageCount(Integer maxUserIndustryImageCount) {
		this.maxUserIndustryImageCount = maxUserIndustryImageCount;
	}
	public Integer getMaxUserAnimalImageCount() {
		return maxUserAnimalImageCount;
	}
	public void setMaxUserAnimalImageCount(Integer maxUserAnimalImageCount) {
		this.maxUserAnimalImageCount = maxUserAnimalImageCount;
	}
	public Integer getMaxUserBuildingImageCount() {
		return maxUserBuildingImageCount;
	}
	public void setMaxUserBuildingImageCount(Integer maxUserBuildingImageCount) {
		this.maxUserBuildingImageCount = maxUserBuildingImageCount;
	}
	public Integer getMaxUserArtImageCount() {
		return maxUserArtImageCount;
	}
	public void setMaxUserArtImageCount(Integer maxUserArtImageCount) {
		this.maxUserArtImageCount = maxUserArtImageCount;
	}
	public Integer getMaxUserOthersImageCount() {
		return maxUserOthersImageCount;
	}
	public void setMaxUserOthersImageCount(Integer maxUserOthersImageCount) {
		this.maxUserOthersImageCount = maxUserOthersImageCount;
	}


	 public Survey deepClone() throws IOException,
	    OptionalDataException,ClassNotFoundException{//将对象写到流里
		 ByteArrayOutputStream bo=new ByteArrayOutputStream();
	  ObjectOutputStream oo=new ObjectOutputStream(bo);
	  oo.writeObject(this);//从流里读出来
	  ByteArrayInputStream bi=new ByteArrayInputStream(bo.toByteArray());
	  ObjectInputStream oi=new ObjectInputStream(bi);
	  return((Survey)oi.readObject());
	 }
	
	//INDUSTRY,ANIMAL,BUILDING,ART,OTHERS
	@Column(nullable=true)
	private Integer answerCount;

	@ManyToOne(cascade={CascadeType.DETACH},optional = true,fetch = FetchType.LAZY)
	@JoinColumn(name="email")
	private Creator creator;
	
	public Creator getCreator() {
		return creator;
	}
	public void setCreator(Creator creator) {
		this.creator = creator;
	}
	
//	@OneToMany(cascade={CascadeType.DETACH,CascadeType.PERSIST,CascadeType.REFRESH,CascadeType.REMOVE},mappedBy ="survey",fetch = FetchType.LAZY)
//	private Set<Answer> answers = new HashSet<Answer>();
//	
//	public Set<Answer> getAnswers() {
//		return answers;
//	}
//	public void setAnswers(Set<Answer> answers) {
//		this.answers = answers;
//	}
//	public void addAnswer(Answer answer) {
//		answer.setSurvey(this);
//		this.answers.add(answer);
//	}
	
	@ManyToMany(cascade={CascadeType.DETACH,CascadeType.PERSIST,CascadeType.REFRESH,CascadeType.REMOVE},fetch = FetchType.LAZY)
    @JoinTable(name="SURVEY_BRAND",
      joinColumns={@JoinColumn(name="surveyId",referencedColumnName="surveyId")},
      inverseJoinColumns={@JoinColumn(name="brandId",referencedColumnName="brandId")})
	private Set<Brand> brands;

    @ManyToMany(cascade={CascadeType.DETACH,CascadeType.PERSIST,CascadeType.REFRESH,CascadeType.REMOVE},fetch = FetchType.LAZY)
    @JoinTable(name="SURVEY_IMAGE",
      joinColumns={@JoinColumn(name="surveyId",referencedColumnName="surveyId")},
      inverseJoinColumns={@JoinColumn(name="imageId",referencedColumnName="imageId")})
	private Set<Image> images;
    

	public Set<Brand> getBrands() {
		return brands;
	}
	public void setBrands(Set<Brand> brands) {
		this.brands = brands;
	}
	public Set<Image> getImages() {
		return images;
	}
	public void setImages(Set<Image> images) {
		this.images = images;
	}
	public String getSurveyId() {
		return surveyId;
	}
	public void setSurveyId(String surveyId) {
		this.surveyId = surveyId;
	}
	public String getName() {
		return name;
	}
	@Override
	public String toString() {
		return "Survey [surveyId=" + surveyId + ", name=" + name + ", releaseTime=" + releaseTime + ", status=" + status
				+ "]";
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getReleaseTime() {
		return releaseTime;
	}
	public void setReleaseTime(Date releaseTime) {
		this.releaseTime = releaseTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getMaxUserBrandCount() {
		return maxUserBrandCount;
	}
	public void setMaxUserBrandCount(Integer maxUserBrandCount) {
		this.maxUserBrandCount = maxUserBrandCount;
	}

	public Integer getAnswerCount() {
		return answerCount;
	}
	public void setAnswerCount(Integer answerCount) {
		this.answerCount = answerCount;
	}
}
