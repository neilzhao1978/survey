package com.neil.survey;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.neil.survey.module.Creator;
import com.neil.survey.module.Survey;
import com.neil.survey.repository.CreatorRepository;
import com.neil.survey.repository.SurveyRepository;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SurveyTests {
	
	@Autowired
	private CreatorRepository creatorRepo;
	@Autowired
	private SurveyRepository surveyRepo;
	
	@Test
	public void addCreator() {
		Creator creator = new Creator();
		creator.setEmail("g_zcm@163.com");
		creator.setPwd("123456");

		
		Survey survey = new  Survey();
		survey.setSurveyId(UUID.randomUUID().toString().replace("-", ""));
//		Set<Creator> creators = new HashSet<Creator>();
//		survey.setCreator(creators);
		survey.setName("first survey");
		survey.setReleaseTime(new Date());

		surveyRepo.save(survey);
		creator.addSurvey(survey);
		
		creatorRepo.save(creator);
	}

	@Test
	public void getCreator() {
		List<Creator> lst = creatorRepo.findByEmail("g_zcm@163.com");
		if(lst.size()>0) {
			Set<Survey> surveys = lst.get(0).getSurveys();
			Survey s = (Survey) surveys.toArray()[0];
			System.out.print(s.getName());
			lst.get(0).toString();
		}
	}

	@Test
	public void deletCreator() {
		creatorRepo.delete("neil zhao");
	}
	
}
