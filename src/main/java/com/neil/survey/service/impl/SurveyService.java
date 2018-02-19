package com.neil.survey.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neil.survey.inputout.stat.Candidates;
import com.neil.survey.inputout.stat.DummySurveyData;
import com.neil.survey.inputout.stat.ProductData;
import com.neil.survey.inputout.stat.Votes;
import com.neil.survey.module.Survey;
import com.neil.survey.module.SurveyCandidate;
import com.neil.survey.module.SurveyStyleMatrix;
import com.neil.survey.module.SurveyVoter;
import com.neil.survey.repository.SurveyAnswerAllInfoRepository;
import com.neil.survey.repository.SurveyCandidateRepository;
import com.neil.survey.repository.SurveyRepository;
import com.neil.survey.repository.SurveyStyleMatrixRepository;
import com.neil.survey.repository.SurveyVoterRepository;
import com.neil.survey.service.ISurveryService;


@Service
@Transactional
public class SurveyService implements ISurveryService {
	@Autowired
	private SurveyRepository surveyRepo;

	@Autowired
	private SurveyAnswerAllInfoRepository surveyAnswerAllInfoRepository;
	
	@Autowired
	private SurveyCandidateRepository surveyCandidateRepository;
	
	@Autowired
	private SurveyStyleMatrixRepository surveyStyleMatrixRepository;

	@Autowired
	private SurveyVoterRepository surveyVoterRepository;
	
	static private int candidateLimite = 12;
	@Override
	public DummySurveyData getSurveyStat(String surveyId) {
		DummySurveyData rtn = new DummySurveyData();
		
		Survey survey = surveyRepo.getBySurveyId(surveyId);
		if(survey == null) return null;
		rtn.setSurveyTitle(survey.getName());
		
		List<SurveyVoter> voters = surveyVoterRepository.findBySurveyId(surveyId);
		if(voters!=null && voters.size()>0){
//			voters.get(0).setSurveyId(null);
			rtn.setVoters(voters.get(0));
		}
		
		List<SurveyStyleMatrix> styelMatrix =  surveyStyleMatrixRepository.findBySurveyId(surveyId);
		if(styelMatrix!=null && styelMatrix.size()>0){
//			styelMatrix.get(0).setSurveyId(null);
			rtn.setStyle_matrix(styelMatrix.get(0));
		}
		
		List<SurveyCandidate> candidateDbs = surveyCandidateRepository.findBySurveyId(surveyId);
		if(candidateDbs !=null && candidateDbs.size()>0){
			List<Candidates> rtnCandidates = new ArrayList<Candidates>(0);
			for(int i=0;i<candidateDbs.size() && i<candidateLimite;i++){
				
				SurveyCandidate candidateDb = candidateDbs.get(i);
				Votes vote = new Votes();				
				vote.setTotal(candidateDb.getTotal());
				vote.setDesigner(candidateDb.getDesigner());
				vote.setEngineer(candidateDb.getEngineer());
				vote.setManager(candidateDb.getManager());
				vote.setSale(candidateDb.getSale());
				
				ProductData productData = new ProductData();
				productData.setBrand(candidateDb.getBrand());
				productData.setModel(candidateDb.getModel());
				
				String temp = candidateDb.getStyleKeyword();
				temp = temp.replaceAll("[\\[|\\]|\"]", "");
				productData.setStyle_keyword(temp);
				productData.setTexture(candidateDb.getTexture());
				productData.setThumb_url(candidateDb.getThumbUrl());
				productData.setYear(candidateDb.getYear());
				
				Candidates rtnCandidate = new Candidates();
				rtnCandidate.setProductData(productData);
				rtnCandidate.setVotes(vote);
				
				rtnCandidates.add(rtnCandidate);
			}
			rtn.setCandidates(rtnCandidates);
		}
		
		return rtn;
	}
	

}















