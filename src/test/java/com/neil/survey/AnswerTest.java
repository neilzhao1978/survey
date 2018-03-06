//package com.neil.survey;
//
//import static org.junit.Assert.*;
//
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
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
//import com.neil.survey.module.Image;
//import com.neil.survey.repository.AnswerRepository;
//import com.neil.survey.repository.BrandRepository;
//import com.neil.survey.repository.CreatorRepository;
//import com.neil.survey.repository.ImageRepository;
//import com.neil.survey.repository.SurveyRepository;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class AnswerTest {
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
//    }
//	
//	@Test
//	public void testSetBrand() {
//		List<Brand> brands =  brandRepo.findAll();
//		Brand b = brands.get(0);
//		
//		
//		Set<Image> images = new HashSet<Image>(imageRepo.findAll());
//	
//		b.setImages(images);
//		brandRepo.save(b);
//	}
//	
////	@Test
//	public void testSetImages() {
//		List<Answer> answerList = answerRepo.findAll();
//		Answer answer = answerList.get(0);
//		
//		List<Image> images = imageRepo.findByImageId("image123");
//		HashSet<Image> hImages = new HashSet<Image>(images);
//
//		answer.setImages(hImages);
//		answerRepo.save(answer);
//	}
//	
////	@Test
//	public void testSetBrands() {
//		List<Answer> answerList = answerRepo.findAll();
//		Answer answer = answerList.get(0);
//		
//		List<Brand> brands = brandRepo.findByBrandId("123456");
//		HashSet<Brand> hBrands = new HashSet<Brand>(brands);
//
//		answer.setBrands(hBrands);
//		answerRepo.save(answer);
//	}
//
//
//}
