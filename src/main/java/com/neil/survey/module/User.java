package com.neil.survey.module;

import java.util.Arrays;
import java.util.Collection;

import javax.persistence.*;



@Entity
public class User {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3518054402546790653L;
	@Id
	private String email;
	private String name;
	private String psw;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPsw() {
		return psw;
	}
	public void setPsw(String psw) {
		this.psw = psw;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

}
