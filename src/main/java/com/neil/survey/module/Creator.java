package com.neil.survey.module;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "CREATOR")
public class Creator implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3035099497755335338L;
	@Id
	@Column(length = 32)
	private String email;
	private String pwd;
	
//    @ManyToMany(cascade=CascadeType.ALL)
//    @JoinTable(name="CREATOR_SURVEY",
//      joinColumns={@JoinColumn(name="creatorId",referencedColumnName="creatorId")},
//      inverseJoinColumns={@JoinColumn(name="surveyId",referencedColumnName="surveyId")})
//    private Set<Survey> surveys;
	
	@OneToMany(cascade=CascadeType.ALL,mappedBy ="creator")
//	@JoinColumn(name="email")
	private Set<Survey> surveys;
	

	public Set<Survey> getSurveys() {
		return surveys;
	}
	public void setSurveys(Set<Survey> surveys) {
		this.surveys = surveys;
	}
	
	public void addSurvey(Survey survey) {
		survey.setCreator(this);
		this.surveys.add(survey);
	}
	
	@Override
	public String toString() {
		return "Creator [ name=" + email + ", pwd=" + pwd + "]";
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

}
