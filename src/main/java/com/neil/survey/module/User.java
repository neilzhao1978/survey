//package com.neil.survey.module;
//
//import java.util.Arrays;
//import java.util.Collection;
//
//import javax.persistence.*;
//
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//@Entity
//public class User implements UserDetails{
//	/**
//	 * 
//	 */
//	private static final long serialVersionUID = -3518054402546790653L;
//	@Id
//	private String email;
//	private String name;
//	private String psw;
//	
//	public String getName() {
//		return name;
//	}
//	public void setName(String name) {
//		this.name = name;
//	}
//	public String getPsw() {
//		return psw;
//	}
//	public void setPsw(String psw) {
//		this.psw = psw;
//	}
//	@Override
//	public Collection<? extends GrantedAuthority> getAuthorities() {
//		return Arrays.asList(new SimpleGrantedAuthority("READER"));
//
//	}
//	@Override
//	public String getPassword() {
//		return psw;
//	}
//	@Override
//	public String getUsername() {
//		return name;
//	}
//	@Override
//	public boolean isAccountNonExpired() {
//		return true;
//	}
//	@Override
//	public boolean isAccountNonLocked() {
//		return true;
//	}
//	@Override
//	public boolean isCredentialsNonExpired() {
//		return true;
//	}
//	@Override
//	public boolean isEnabled() {
//		return true;
//	}
//	
//
//
//}
