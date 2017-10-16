//package com.neil.survey;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import com.neil.survey.module.Answer;
//import com.neil.survey.module.Brand;
//import com.neil.survey.module.Creator;
//import com.neil.survey.module.Image;
//import com.neil.survey.module.Survey;
//import com.neil.survey.repository.AnswerRepository;
//import com.neil.survey.repository.BrandRepository;
//import com.neil.survey.repository.CreatorRepository;
//import com.neil.survey.repository.ImageRepository;
//import com.neil.survey.repository.SurveyRepository;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//import java.util.UUID;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class SurveyTests {
//	
//	@Autowired
//	private CreatorRepository creatorRepo;
//	@Autowired
//	private SurveyRepository surveyRepo;
//	@Autowired
//	private AnswerRepository answerRepo;
//	@Autowired
//	private BrandRepository brandRepo;
//	@Autowired
//	private ImageRepository imageRepo;
//	
//	@Before
//    public void setUp() throws Exception {
//		Brand b = new Brand();
//		b.setBrandId("123456");
//		b.setBrandIconUrl("url");
//		b.setBrandName("asdfasdf");
//		brandRepo.save(b);
//		
//		Image i = new Image();
//		i.setImageId("image123");
//		i.setImageName("guozhao");
//		i.setImageType("porn");
//		i.setImageUrl("imageurl");
//		
//		imageRepo.save(i);
//		
//    }
//    
//    @Test 
//    public void getSurvey() {
//    	List<Survey> lst = surveyRepo.findAll();
//    	Survey s = lst.get(0);
//    	Set<Brand> brands = s.getBrands();
//    	List<Brand> lstBrand = new ArrayList<Brand>();
//    	lstBrand.addAll(brands);
//    	Brand x = lstBrand.get(0);
//    	System.out.println(x);
//    }
//	
//	@Test 
//	public void setSurvey() {
//		List<Survey> lst = surveyRepo.findAll();
//		Survey s = lst.get(0);
//		List<Brand> brands = brandRepo.findByBrandId("123456");
//		HashSet<Brand> h = new HashSet<Brand>(brands);
//		s.setBrands(h);
//		
//		List<Image> images = imageRepo.findByImageId("image123");
//		HashSet<Image> hImages = new HashSet<Image>(images);
//		s.setImages(hImages);
//		
//		surveyRepo.save(s);
//	}
//	
////	@Test
//	public void addCreator() {
//		Creator creator = new Creator();
//		creator.setEmail("g_zcm@163.com");
//		creator.setPwd("123456");
//
//		
//		Survey survey = new  Survey();
//		survey.setSurveyId(UUID.randomUUID().toString().replace("-", ""));
////		Set<Creator> creators = new HashSet<Creator>();
////		survey.setCreator(creators);
//		survey.setName("first survey");
//		survey.setReleaseTime(new Date());
//
//		surveyRepo.save(survey);
////		creator.addSurvey(survey);
//		
//		creatorRepo.save(creator);
//	}
//
////	@Test
//	public void getCreator() {
//		List<Creator> lst = creatorRepo.findByEmail("g_zcm@163.com");
////		if(lst.size()>0) {
//////			Set<Survey> surveys = lst.get(0).getSurveys();
////			Survey s = (Survey) surveys.toArray()[0];
////			System.out.print(s.getName());
////			lst.get(0).toString();
////		}
//	}
//
////	@Test
//	public void setAnswer() {
//		List<Creator> lst = creatorRepo.findByEmail("g_zcm@163.com");
//		Set<Survey> surveys = null;
//		Survey s = null;
////		if(lst.size()>0) {
////			surveys = lst.get(0).getSurveys();
////			 s = (Survey) surveys.toArray()[0];
////			System.out.print(s.getName());
////			lst.get(0).toString();
////		}
//		
//		Answer answer = new Answer();
//		answer.setAnswerId(UUID.randomUUID().toString().replace("-", ""));
//		answer.setReplyTime(new Date().toString());
//		answer.setReplayerName("zhao");
//		answer.setReplayerPosition("swe");
//		answer.setSurvey(s);
//		
//		answerRepo.save(answer);
//		
//	}
//	
////	@Test
//	public void getAnswer() {
//		List<Creator> lst = creatorRepo.findByEmail("g_zcm@163.com");
//		Set<Survey> surveys = null;
//		Survey s = null;
////		if(lst.size()>0) {
////			surveys = lst.get(0).getSurveys();
////			 s = (Survey) surveys.toArray()[0];
////			System.out.print(s.getName());
////			lst.get(0).toString();
////		}
//		
//
//		
//		List<Answer> answers = answerRepo.findBySurvey(s);
//		if(answers.size()>0) {
//			System.out.print(answers.get(0).getAnswerId());
//		}
//		
//	}
//	
////	@Test
//	public void deletCreator() {
////		creatorRepo.delete("neil zhao");
//	}
//	
//}
