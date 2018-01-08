package com.neil.survey.module;

import java.util.List;

public class ImageReplaceParam {
	private boolean containFeatureLine;
	private List<String> candidateImageId;
	private String partsName;
	private Integer limits;
	
	public boolean isContainFeatureLine() {
		return containFeatureLine;
	}
	public void setContainFeatureLine(boolean containFeatureLine) {
		this.containFeatureLine = containFeatureLine;
	}
	public List<String> getCandidateImageId() {
		return candidateImageId;
	}
	public void setCandidateImageId(List<String> candidateImageId) {
		this.candidateImageId = candidateImageId;
	}
	public String getPartsName() {
		return partsName;
	}
	public void setPartsName(String partsName) {
		this.partsName = partsName;
	}
	public Integer getLimits() {
		return limits;
	}
	public void setLimits(Integer limits) {
		this.limits = limits;
	}

}
