/**
  * Copyright 2018 bejson.com 
  */
package com.neil.survey.module.stat;
import java.util.List;

/**
 * Auto-generated: 2018-02-14 22:17:13
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class DummySurveyData {

    private String surveyTitle;
    private Voters voters;
    private Style_matrix style_matrix;
    private List<Products> products;
    private List<Candidates> candidates;
    public void setSurveyTitle(String surveyTitle) {
         this.surveyTitle = surveyTitle;
     }
     public String getSurveyTitle() {
         return surveyTitle;
     }

    public void setVoters(Voters voters) {
         this.voters = voters;
     }
     public Voters getVoters() {
         return voters;
     }

    public void setStyle_matrix(Style_matrix style_matrix) {
         this.style_matrix = style_matrix;
     }
     public Style_matrix getStyle_matrix() {
         return style_matrix;
     }

    public void setProducts(List<Products> products) {
         this.products = products;
     }
     public List<Products> getProducts() {
         return products;
     }

    public void setCandidates(List<Candidates> candidates) {
         this.candidates = candidates;
     }
     public List<Candidates> getCandidates() {
         return candidates;
     }

}