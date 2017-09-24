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
	private String creatorId;
	private String name;
	private String pwd;
	
	
    @OneToMany(cascade=CascadeType.ALL)
    @JoinTable(name="CREATOR_SURVEY",
      joinColumns={@JoinColumn(name="surveyId",referencedColumnName="creatorId")},
      inverseJoinColumns={@JoinColumn(name="creatorId",referencedColumnName="surveyId")})
    private Set<Survey> surveys;
	
	public String getCreatorId() {
		return creatorId;
	}
	@Override
	public String toString() {
		return "Creator [creatorId=" + creatorId + ", name=" + name + ", pwd=" + pwd + "]";
	}
	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

}
