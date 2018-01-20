package com.neil.survey.inputout;

public class ImagePartRe{

	private Integer x;
	private Integer y;
	private Integer w;
	private Integer h;
	private String combinedImage;
	private String combinedFeature;
	private String name;
	private String url;
	
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
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCombinedFeature() {
		return combinedFeature;
	}
	public void setCombinedFeature(String combinedFeature) {
		this.combinedFeature = combinedFeature;
	}
	public String getCombinedImage() {
		return combinedImage;
	}
	public void setCombinedImage(String combinedImage) {
		this.combinedImage = combinedImage;
	}

}