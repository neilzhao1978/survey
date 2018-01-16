package com.neil.survey.inputout;

import java.util.List;


public class ImageWholeRe{
	private String wholeImageUrl;
	private List<ImagePartRe> allParts;
	private int w;
	private int h;
	public String getWholeImageUrl() {
		return wholeImageUrl;
	}
	public void setWholeImageUrl(String wholeImageUrl) {
		this.wholeImageUrl = wholeImageUrl;
	}
	public List<ImagePartRe> getAllParts() {
		return allParts;
	}
	public void setAllParts(List<ImagePartRe> allParts) {
		this.allParts = allParts;
	}
	public int getW() {
		return w;
	}
	public void setW(int w) {
		this.w = w;
	}
	public int getH() {
		return h;
	}
	public void setH(int h) {
		this.h = h;
	}
	
}

