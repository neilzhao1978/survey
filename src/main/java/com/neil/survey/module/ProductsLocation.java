package com.neil.survey.module;

import java.util.List;

import com.neil.survey.inputout.stat.Products;




public class ProductsLocation {

   private String surveyName;
   private List<Products> products;
   public void setProducts(List<Products> products) {
        this.products = products;
    }
    public List<Products> getProducts() {
        return products;
    }
	public String getSurveyName() {
		return surveyName;
	}
	public void setSurveyName(String surveyName) {
		this.surveyName = surveyName;
	}

}