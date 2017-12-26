package com.neil.survey.controller;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.neil.survey.module.Brand;
import com.neil.survey.module.Survey;
import com.neil.survey.repository.AnswerRepository;
import com.neil.survey.repository.BrandRepository;
import com.neil.survey.repository.CreatorRepository;
import com.neil.survey.repository.ImageRepository;
import com.neil.survey.repository.SurveyRepository;
import com.neil.survey.util.ErrorCode;
import com.neil.survey.util.PageEntity;
import com.neil.survey.util.ResponseGenerator;
import com.neil.survey.util.RestResponseEntity;


@RestController
@RequestMapping("/api/brandService")
@Transactional
public class BrandController {
	@Autowired
	private SurveyRepository surveyRepo;
	@Autowired
	private AnswerRepository answerRepo;
	@Autowired
	private BrandRepository brandRepo;
	@Autowired
	private ImageRepository imageRepo;
	@Autowired
	private CreatorRepository creatorRepo;
	
	private static final Logger logger = LoggerFactory.getLogger(BrandController.class);
	
	@ResponseBody
	@RequestMapping(value = "/getAllBrands", method = RequestMethod.GET)
	public RestResponseEntity<List<Brand>> getAllBrands( @RequestParam(value = "page",required=true) PageEntity page){
		
		PageRequest pageRequest = new PageRequest(page.getPageNumber()-1, page.getPageSize(), null);
		Page<Brand> brands = brandRepo.findAll(pageRequest);

		if(brands!=null && brands.getSize()>0) {
			for(Brand b:brands.getContent()) {
				//TODO clear useless properties
			}
			return ResponseGenerator.createSuccessResponse("获取品牌列表成功。", brands.getContent().size(), brands.getContent(),brands.getTotalElements());
		}else {
			logger.error("获取品牌列表失败。");
			return ResponseGenerator.createFailResponse("获取品牌列表失败。", ErrorCode.DB_ERROR);
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/addBrand", method = RequestMethod.POST)
	public RestResponseEntity<Void> addBrand( @RequestBody Brand brand){
		Brand b = brandRepo.save(brand);
		if(b!=null) {
			return ResponseGenerator.createSuccessResponse("新增品牌成功。");
		}else {
			return ResponseGenerator.createFailResponse("新增品牌失败。", ErrorCode.DB_ERROR);
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/updateBrand", method = RequestMethod.POST)
	public RestResponseEntity<Void> updateBrand( @RequestBody Brand brand){
		Brand b = brandRepo.save(brand);
		if(b!=null) {
			return ResponseGenerator.createSuccessResponse("更新品牌成功。");
		}else {
			return ResponseGenerator.createFailResponse("更新品牌失败。", ErrorCode.DB_ERROR);
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/deleteBrand", method = RequestMethod.DELETE)
	public RestResponseEntity<Void> deleteCreator( @RequestBody Brand brand){
		try {	
			brand.setImages(null);
			brandRepo.save(brand);
			brandRepo.delete(brand);
			
			return ResponseGenerator.createSuccessResponse("删除品牌成功。");
		}catch(Exception e) {
			return ResponseGenerator.createFailResponse("删除品牌失败。", ErrorCode.DB_ERROR);
		}
	}
	
}
