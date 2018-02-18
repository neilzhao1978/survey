/**
  * Copyright 2018 bejson.com 
  */
package com.neil.survey.inputout.stat;
import java.util.List;

import com.neil.survey.module.SurveyStyleMatrix;
import com.neil.survey.module.SurveyVoter;

/**
 * Auto-generated: 2018-02-14 22:17:13
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class DummySurveyData {

    private String surveyTitle;
    private SurveyVoter voters;
    private SurveyStyleMatrix style_matrix;
    private List<Products> products;
    private List<Candidates> candidates;
    public void setSurveyTitle(String surveyTitle) {
         this.surveyTitle = surveyTitle;
     }
     public String getSurveyTitle() {
         return surveyTitle;
     }

    public void setVoters(SurveyVoter voters) {
         this.voters = voters;
     }
     public SurveyVoter getVoters() {
         return voters;
     }

    public void setStyle_matrix(SurveyStyleMatrix style_matrix) {
         this.style_matrix = style_matrix;
     }
     public SurveyStyleMatrix getStyle_matrix() {
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