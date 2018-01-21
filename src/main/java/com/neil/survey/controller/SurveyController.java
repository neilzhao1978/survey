package com.neil.survey.controller;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OptionalDataException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.FileSystems;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import javax.xml.transform.TransformerException;


import  org.apache.batik.transcoder.image.*;
import org.apache.batik.anim.dom.SAXSVGDocumentFactory;
import org.apache.batik.dom.svg.SVGDocumentFactory;
import org.apache.batik.util.XMLResourceDescriptor;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.svg.SVGDocument;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.neil.batiktools.PlainRect;
import com.neil.batiktools.Rect;

import com.neil.batiktools.SaveAsPngTiles;
import com.neil.batiktools.SvgUtilities;
import com.neil.imagetools.BinaryColor;
import com.neil.survey.module.Answer;
import com.neil.survey.module.Brand;
import com.neil.survey.module.BrandResp;
import com.neil.survey.module.Brand_P;
import com.neil.survey.module.Component;
import com.neil.survey.module.Image;
import com.neil.survey.module.ImageCount;
import com.neil.survey.module.Survey;
import com.neil.survey.module.SurveyImageResult;
import com.neil.survey.module.VehicheResp;
import com.neil.survey.module.VehicleInfo_P;
import com.neil.survey.repository.AnswerRepository;
import com.neil.survey.repository.BrandRepository;
import com.neil.survey.repository.ImageRepository;
import com.neil.survey.repository.SurveyImageResultRepository;
import com.neil.survey.repository.SurveyRepository;
import com.neil.survey.util.ErrorCode;
import com.neil.survey.util.PageEntity;
import com.neil.survey.util.ResponseGenerator;
import com.neil.survey.util.RestResponseEntity;
import com.neil.survey.util.SortTools;

//drop view SURVEY_IMAGE_RESULT;
//drop table SURVEY_IMAGE_RESULT;
//create or replace view SURVEY_IMAGE_RESULT as select SURVEY_ID,IMAGE_ID,count(*) cnt,max(IMAGE_DESC) DESC from SERVEY_IMAGE  group by SURVEY_ID,IMAGE_ID order by cnt desc;

@RestController
@RequestMapping("/api/surveyService")

public class SurveyController {

	@Autowired
	private SurveyRepository surveyRepo;
	@Autowired
	private AnswerRepository answerRepo;

	@Autowired
	private RestTemplate template;
	@Autowired
	private BrandRepository brandRepo;
	@Autowired
	private ImageRepository imageRepo;
	
	@Autowired
	private SurveyImageResultRepository surveyImageResultRepo;
	
	// @Autowired
	// private CreatorRepository creatorRepo;

	private static final Logger logger = LoggerFactory.getLogger(SurveyController.class);

	@ResponseBody
	@RequestMapping(value = "/getAllSurveys", method = RequestMethod.GET)
	public RestResponseEntity<List<Survey>> getAllSurveys(
			@RequestParam(value = "page", required = true) PageEntity page) {

		Direction d = page.isDesc() ? Direction.DESC : Direction.ASC;
		String orderbyFiled = page.getOrderByFieldName() == null ? "name" : page.getOrderByFieldName();
		PageRequest pageRequest = new PageRequest(page.getPageNumber() - 1, page.getPageSize(),
				SortTools.basicSort(d, orderbyFiled));
		Page<Survey> surveys = surveyRepo.findAll(new Specification<Survey>() {
			@Override
			public Predicate toPredicate(Root<Survey> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Path<String> namePath = root.get("status");
				query.where(cb.notLike(namePath, "9")); // 这里可以设置任意条查询条件

				return null;
			}

		}, pageRequest);

		if (surveys != null && surveys.getSize() > 0) {
			for (Survey s : surveys.getContent()) {
				s.setAnswerCount(answerRepo.countBySurvey(s));
			}
			return ResponseGenerator.createSuccessResponse("获取问卷列表成功。", surveys.getContent().size(),
					surveys.getContent(), surveys.getTotalElements());
		} else {
			return ResponseGenerator.createFailResponse("获取问卷列表失败。", ErrorCode.DB_ERROR);
		}
	}

	@ResponseBody
	@RequestMapping(value = "/addSurvey", method = RequestMethod.POST)
	public RestResponseEntity<Void> addSurvey(@RequestBody Survey survey) {

		Survey s = surveyRepo.save(survey);
		if (s != null) {

			return ResponseGenerator.createSuccessResponse("新增问卷成功。");
		} else {
			return ResponseGenerator.createFailResponse("新增问卷成功。", ErrorCode.DB_ERROR);
		}
	}

	@ResponseBody
	@RequestMapping(value = "/deleteSurvey", method = RequestMethod.POST)
	public RestResponseEntity<Void> deleteSurvey(@RequestBody Survey survey) {
		try {
			// surveyRepo.delete(survey);
			Survey s = surveyRepo.findOne(survey.getSurveyId());
			s.setStatus("9");// 删除
			// answerRepo.deleteBySurvey(s);
			// s.setCreator(null);
			// s.setBrands(null);
			// s.setImages(null);
			surveyRepo.save(s);// TODO 有问题。
			// surveyRepo.deleteBySurveyId(s.getSurveyId());
			return ResponseGenerator.createSuccessResponse("删除问卷成功。");
		} catch (Exception e) {
			return ResponseGenerator.createFailResponse("删除问卷失败。", ErrorCode.DB_ERROR);
		}
	}

	@ResponseBody
	@RequestMapping(value = "/closeOpenSurvey", method = RequestMethod.POST)
	public RestResponseEntity<Void> closeOpenSurvey(@RequestBody Survey survey) {
		Survey s = surveyRepo.findOne(survey.getSurveyId());
		s.setStatus(survey.getStatus());
		Survey s1 = surveyRepo.save(s);
		if (s1 != null) {
			return ResponseGenerator.createSuccessResponse("打开关闭问卷成功。");
		} else {
			return ResponseGenerator.createFailResponse("打开关闭问卷失败。", ErrorCode.DB_ERROR);
		}
	}

	@ResponseBody
	@RequestMapping(value = "/updateSurvey", method = RequestMethod.POST)
	public RestResponseEntity<Void> updateSurvey(@RequestBody Survey survey) {
		Survey s = surveyRepo.save(survey);
		if (s != null) {
			return ResponseGenerator.createSuccessResponse("更新问卷成功。");
		} else {
			return ResponseGenerator.createFailResponse("更新问卷失败。", ErrorCode.DB_ERROR);
		}
	}

	@ResponseBody
	@RequestMapping(value = "/getSurveyDetail", method = RequestMethod.GET)
	public RestResponseEntity<Survey> getSurveyDetail(
			@RequestParam(value = "surveyId", required = true) String surveyId) {
		Survey s = surveyRepo.getBySurveyId(surveyId);

		if (s != null) {
			s.getCreator().setPwd(null);
			return ResponseGenerator.createSuccessResponse("获取问卷明细成功.", 1, s, null);
		} else {
			return ResponseGenerator.createFailResponse("获取问卷明细失败.", ErrorCode.DB_ERROR);
		}
	}

	@ResponseBody
	@RequestMapping(value = "/copySurvey", method = RequestMethod.GET)
	public RestResponseEntity<Survey> copySurvey(@RequestParam(value = "surveyId", required = true) String surveyId) {
		Survey s = surveyRepo.getBySurveyId(surveyId);
		Survey s1 = null;
		try {
			s1 = s.deepClone();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResponseGenerator.createFailResponse("复制问卷失败.", ErrorCode.DB_ERROR);
		}
		s1.setSurveyId(UUID.randomUUID().toString());
		s1.setName(s1.getName() + "复制");

		s1.setReleaseTime(new Date());
		Survey x = surveyRepo.save(s1);

		if (x != null) {
			x.getCreator().setPwd(null);
			return ResponseGenerator.createSuccessResponse("复制问卷成功.", 1, x, null);
		} else {
			return ResponseGenerator.createFailResponse("复制问卷失败.", ErrorCode.DB_ERROR);
		}
	}

	@ResponseBody
	@RequestMapping(value = "/getSurveyResult", method = RequestMethod.GET)
	public RestResponseEntity<List<ImageCount>> getSurveyResult(
			@RequestParam(value = "surveyId", required = true) String surveyId,
			@RequestParam(value = "imageType", required = true) String imageType) {

		Survey survey = surveyRepo.getBySurveyId(surveyId);

		List<Answer> answers = answerRepo.findBySurvey(survey);
		List<Image> allImages = new ArrayList<Image>();
		for (Answer a : answers) {
			allImages.addAll(a.getImages());
		}

		Map<String, ImageCount> imageCount = new HashMap<String, ImageCount>();
		for (Image i : allImages) {
			if (i.getImageType().equalsIgnoreCase(imageType)) {
				if (imageCount.containsKey(i.getImageId())) {
					imageCount.get(i.getImageId()).increaseCount();
					;
				} else {
					ImageCount x = new ImageCount();
					x.setI(i);
					x.setCount(1);
					imageCount.put(i.getImageId(), x);
				}
			}
		}

		List<ImageCount> result = new ArrayList<ImageCount>();
		result.addAll(imageCount.values());
		Collections.sort(result);

		if (answers != null) {
			return ResponseGenerator.createSuccessResponse("获取问卷结果成功。", result.size(), result, result.size());
		} else {
			return ResponseGenerator.createFailResponse("获取问卷结果失败。", ErrorCode.DB_ERROR);
		}
	}

	@Value("${web.upload-path}")
	private String path;

	@ResponseBody
	@RequestMapping(value = "/getQRcode", method = RequestMethod.GET)
	public ResponseEntity<byte[]> getQRcode(
			@RequestParam(value = "pathStringCode", required = true) String pathStringCode)
			throws WriterException, IOException {
		String filePath = path;
		String fileName = UUID.randomUUID().toString().replaceAll("-", "");

		String content = pathStringCode;
		int width = 300; // 图像宽度
		int height = 300; // 图像高度
		String format = "png";// 图像类型
		Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
		hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);// 生成矩阵
		java.nio.file.Path path = FileSystems.getDefault().getPath(filePath, fileName);
		MatrixToImageWriter.writeToPath(bitMatrix, format, path);// 输出图像
		return ResponseGenerator.createQRImageResponse(path.toString());
	}

	@Value("${web.brandServerAddr}")
	private String serverAddr;

	@Value("${web.ip}")
	private String ip;

	@Value("${server.port}")
	private String port;

	@ResponseBody
	@RequestMapping(value = "/sychDb", method = RequestMethod.GET)
	public RestResponseEntity<Void> sychDb() throws IOException {
		//
//		String base64String = "whuang123";  
//        byte[] result = Base64.encodeBase64(base64String.getBytes()); 
		BrandResp brandResp = null;
		brandResp = template.getForObject(serverAddr + "getAllBrand", BrandResp.class);
		List<Brand_P> x = brandResp.object;

		for (Brand_P b : x) {
			try{
				Brand brand = new Brand();
				brand.setBrandId(b.getId().toString());
				brand.setBrandIconUrl(b.getIcon());
				brand.setBrandName(b.getName());
				brand.setDesc(b.getDescription());

				VehicheResp vehicheResp = null;
				vehicheResp = template.getForObject(serverAddr + "getProductByBrand/{id}", VehicheResp.class,b.getId().toString());

				Set<Image> images = new HashSet<Image>();

				for (VehicleInfo_P v : vehicheResp.object) {
					if(v.getCategoryName().contains("压路机")){
						try {
							handleWholeImage(b, images, v);
							brand.setImages(images);
							brandRepo.save(brand);
						} catch (Exception e) {
							logger.error("insert whole image erro.");
							e.printStackTrace();
						}
					}
				}
			}catch(Exception e){
				logger.error("insert brand erro.");
				e.printStackTrace();
			}
		}
		return ResponseGenerator.createSuccessResponse("Db synch success.");
	}

	@ResponseBody
	@RequestMapping(value = "/getProductList", method = RequestMethod.GET)
	public RestResponseEntity<Set<String>> getProductList(
			@RequestParam(value = "surveyId", required = true) String surveyId,
			@RequestParam(value = "productType", required = true) String productType
			) throws Exception{
		Set<String> rtn = new TreeSet<String>();
		List<SurveyImageResult> lst = surveyImageResultRepo.findBySurveyIdAndDesc(surveyId, productType);
		for(SurveyImageResult r:lst){
			rtn.add(r.getImageId());
		}
		
		List<Image> lst2 = imageRepo.findByImageTypeAndImageNameLike("WHOLE",productType);
		for(Image r:lst2){
			rtn.add(r.getImageId());
		}
		
		if (rtn != null && rtn.size() >0) {
			return ResponseGenerator.createSuccessResponse("获取问卷结果产品ID成功.", rtn.size(), rtn, null);
		} else {
			return ResponseGenerator.createFailResponse("获取问卷结果产品ID失败.", ErrorCode.DB_ERROR);
		}
	}
	
	
//	@ResponseBody
//	@RequestMapping(value = "/mergeSubImage", method = RequestMethod.GET)
//	public RestResponseEntity<Void> mergeSubImage() throws Exception{
//		String parser = XMLResourceDescriptor.getXMLParserClassName();
//		SVGDocumentFactory f = new SAXSVGDocumentFactory(parser);
//		List<Image> wholeImages = imageRepo.findByImageType("WHOLE");
//		for(Image wholeImage:wholeImages){
//			try{
//				String x[] = wholeImage.getImageUrl().split(";");
//				URL url = new URL(x[0]);
//				SVGDocument doc = (SVGDocument) f.createSVGDocument(x[0],new BufferedInputStream(url.openStream(), 2 * 1024 * 1024));
//
//				List<Image> cabs = imageRepo.findByParentImageIdAndImageNameLike(wholeImage.getImageId(), "司机室%");
//				if(cabs.size()>0){
//					Image newCab = merge(cabs,wholeImage.getImageId(), doc);
//					imageRepo.save(newCab);
////					for(Image i :cabs){
////						imageRepo.delete(i);
////					}
//				}
//				
//				List<Image> butts = imageRepo.findByParentImageIdAndImageNameLike(wholeImage.getImageId(), "下车%");
//				if(butts.size()>0){
////					Image newButt = merge(butts,wholeImage.getImageId(), doc);
////					imageRepo.save(newButt);
////					for(Image i :butts){
////						imageRepo.delete(i);
////					}
//				}
//				
//				List<Image> underpans  = imageRepo.findByParentImageIdAndImageNameLike(wholeImage.getImageId(), "底盘%");
//				if(underpans.size()>0){
////					Image underpan  = merge(underpans,wholeImage.getImageId(), doc);
////					imageRepo.save(underpan);
////					for(Image i :underpans){
////						imageRepo.delete(i);
////					}
//				}
//				
//				List<Image> uppers = imageRepo.findByParentImageIdAndImageNameLike(wholeImage.getImageId(), "上车%");
//				if(uppers.size()>0){
////					Image upper = merge(uppers,wholeImage.getImageId(), doc);
////					imageRepo.save(upper);
////					for(Image i :uppers){
////						imageRepo.delete(i);
////					}
//				}
//
//				List<Image> braces = imageRepo.findByParentImageIdAndImageNameLike(wholeImage.getImageId(), "钢轮支架%");
//				if(braces.size()>0){
//					Image brace = merge(braces,wholeImage.getImageId(), doc);
//					imageRepo.save(brace);
////					for(Image i :braces){
////						imageRepo.delete(i);
////					}
//				}
//				
//				List<Image> balanceWeights = imageRepo.findByParentImageIdAndImageNameLike(wholeImage.getImageId(), "配重%");
//				if(balanceWeights.size()>0){
////					Image balanceWeight = merge(balanceWeights,wholeImage.getImageId(), doc);
////					imageRepo.save(balanceWeight);
////					for(Image i :balanceWeights){
////						imageRepo.delete(i);
////					}
//				}
//				
//				List<Image> ladders = imageRepo.findByParentImageIdAndImageNameLike(wholeImage.getImageId(), "爬梯%");
//				if(ladders.size()>0){
////					Image ladder = merge(ladders,wholeImage.getImageId(), doc);
////					imageRepo.save(ladder);
////					for(Image i :ladders){
////						imageRepo.delete(i);
////					}
//				}
//				
//				List<Image> realCowls = imageRepo.findByParentImageIdAndImageNameLike(wholeImage.getImageId(), "后罩%");
//				if(realCowls.size()>0){
//					Image realCowl = merge(realCowls,wholeImage.getImageId(), doc);
//					imageRepo.save(realCowl);
////					for(Image i :realCowls){
////						imageRepo.delete(i);
////					}
//				}
//				
//				List<Image> vibratingDrums = imageRepo.findByParentImageIdAndImageNameLike(wholeImage.getImageId(), "振动轮%");
//				if(vibratingDrums.size()>0){
////					Image vibratingDrum = merge(vibratingDrums,wholeImage.getImageId(), doc);
////					imageRepo.save(vibratingDrum);
////					for(Image i :vibratingDrums){
////						imageRepo.delete(i);
////					}
//				}
//			}catch(Exception e){
//				logger.debug("error", e);
//				continue;
//			}
//		}
//		return ResponseGenerator.createSuccessResponse("merge subimage success.");
//	}
	
	private Point getMinPoint(List<Image> imageGroup){
		int xMin=Integer.MAX_VALUE;
		int yMin=Integer.MAX_VALUE;
		for(Image image:imageGroup){
			if(image.getX() <= xMin){
				xMin = image.getX();
			}
			if(image.getY() <= yMin){
				yMin = image.getY();
			}
		}
		return new Point(xMin,yMin);
	}
	
	private Point getMaxPoint(List<Image> imageGroup){
		int xMax=0;
		int yMax=0;
		for(Image image:imageGroup){
			if(image.getLX() >= xMax){
				xMax = image.getLX();
			}
			if(image.getBY() >= yMax){
				yMax = image.getBY();
			}
		}
		return new Point(xMax,yMax);
	}
	
	private boolean isContainFeature(List<Image> imageGroup){
		for(Image i : imageGroup){
			if(i.getContainFeatureLine()) return true;
		}
		return false;
	}
	
	private Image merge(List<Image> imageGroup,String id,SVGDocument doc) throws Exception{
		
		Element featureLine = doc.getElementById("特征线");
		Element eleProductImage = (Element) (featureLine.getElementsByTagName("image").item(0));
		
		Point max = getMaxPoint(imageGroup);
		Point min = getMinPoint(imageGroup);
		
		Image i = new Image();
		i.setAllKeyPoints(null);
		i.setContainFeatureLine(isContainFeature(imageGroup));
		i.setFeatureUrl(null);
		i.setH(max.y-min.y);
		i.setImageDesc(imageGroup.get(0).getImageDesc());
		
		String imageUUID = UUID.randomUUID().toString().replace("-", "");
		i.setImageId(imageUUID);
		
		String imageName = imageGroup.get(0).getImageName();
		String temp[]=imageName.split("/");
		i.setImageName(temp[0]);
		i.setImageType("PART");

		/**处理图**/
		eleProductImage.setAttribute("display", "block");
		displayFeatureLine(featureLine,"none");
		SaveAsPngTiles saver = new SaveAsPngTiles();
		String imageFileName = path +id+"/merged/"+ imageUUID + ".png";
		File newFileFolder = new File(path +id+"/merged/");
		if (!newFileFolder.exists()) {
			newFileFolder.mkdir();
		}
		String urlDetail = "http://" + ip + ":" + port + "/static/images/"+id+"/merged/"+imageUUID + ".png";
		File newFile = new File(imageFileName);
		if (!newFile.exists()) {
			newFile.createNewFile();
		}
		InputStream in = SvgUtilities.documentToString(doc);
		saver.tile(in, imageFileName,
				new Rectangle(min.x,min.y,max.x-min.x,max.y-min.y));
		
		i.setImageUrl(urlDetail);
		/**处理图end**/

		/**处理线**/
		String lineUUID = UUID.randomUUID().toString().replace("-", "");
		eleProductImage.setAttribute("display", "none");
		displayFeatureLine(featureLine,"block");
		String lineFileName = path +id+"/merged/"+ lineUUID + ".png";

		String urlLineDetail = "http://" + ip + ":" + port + "/static/images/"+id+"/merged/"+lineUUID + ".png";
		File newLineFile = new File(lineFileName);
		if (!newLineFile.exists()) {
			newLineFile.createNewFile();
		}
		InputStream in1 = SvgUtilities.documentToString(doc);
		saver.tile(in1, lineFileName,
				new Rectangle(min.x,min.y,max.x-min.x,max.y-min.y));
		
		i.setFeatureUrl(urlLineDetail);
		/**处理线end**/
		
		i.setParentImageId(imageGroup.get(0).getParentImageId());
		i.setProfileImageUrl(null);
		i.setW(max.x-min.x);
		i.setX(min.x);
		i.setY(min.y);
		return i;
	}
	
	private void handleWholeImage(Brand_P b, Set<Image> images, VehicleInfo_P v)
			throws Exception {
		String parser = XMLResourceDescriptor.getXMLParserClassName();
		SVGDocumentFactory f = new SAXSVGDocumentFactory(parser);
		
		Image imageDb = new Image();
		imageDb.setImageId(v.getId().toString());
		imageDb.setImageDesc("brand:" + b.getName() + ".category:" + v.getCategoryName());
		/**处理特征点**/
		List<Point2D> keyPoints = SvgUtilities.getAllKeyPoits(v.getImageUrl1());
		String temp = keyPoints.toString().replaceAll("Point2D.Double", "").replaceAll("Point2D.Float","");//to get key points.
		imageDb.setAllKeyPoints(temp.substring(1, temp.length() - 1));
		/**处理特征点结束**/
		
		/**隐藏原图中不需要的部分，并将“特征线”进行处理**/
		URL url = new URL(v.getImageUrl1());
		SVGDocument doc = (SVGDocument) f.createSVGDocument(v.getImageUrl1(),new BufferedInputStream(url.openStream(), 2 * 1024 * 1024));
		Element eleSvg = (Element)doc.getElementsByTagName("svg").item(0);
		
		Element featureLine = doc.getElementById("特征线");
		Element productImage = doc.getElementById("产品图片");
		
		List<Component> componets = JSON.parseArray(v.getComponentInfo(), Component.class);

		int boundW = 0;
		int boundH = 0;
		if(componets.size()>0){
			boolean set = false;
			int size = 0;
			do{
				try{
					Component c = componets.get(size);
					boundW = c.image.customData.boundW;//获取高宽
					boundH = c.image.customData.boundH;

					set=true;
				}catch(Exception e){
					continue;
				}finally{					
					size++;
				}
			}while(!set&&size<componets.size());
		}
		
		String viewBox = eleSvg.getAttribute("viewBox");
		String[] cor = viewBox.split(" {1,}");
		Float tureW = Float.parseFloat(cor[2]);
		Float tureH = Float.parseFloat(cor[3]);
		
		float ratioX = 0f;
		float ratioY = 0f;
		if(boundW == 0){
			ratioX = 1f;
		}else{
			ratioX=((float)boundW)/((float)tureW);
		}
		
		if(boundH == 0){
			ratioY = 1f;
		}else{
			ratioY=((float)boundH)/((float)tureH);
		}
		
		
		imageDb.setX(0);
		imageDb.setY(0);
		imageDb.setW(tureW.intValue());
		imageDb.setH(tureH.intValue());
		eleSvg.setAttribute("width", imageDb.getW()+"");
		eleSvg.setAttribute("height", imageDb.getH()+"");
		
		//不要那个"产品图片"，没有价值
		productImage.setAttribute("display", "none");
		productImage.getParentNode().removeChild(productImage);
		
		featureLine.setAttribute("display", "block");
		
		//处理“特征线”图层的显示。
		Element eleProductImage = (Element) (featureLine.getElementsByTagName("image").item(0));
		NamedNodeMap eleProductImageAttr = eleProductImage.getAttributes();
		String oldImageStype = "";
		if(eleProductImageAttr.getNamedItem("style")!=null){
			oldImageStype = eleProductImageAttr.getNamedItem("style").getNodeValue();
			eleProductImageAttr.getNamedItem("style").setNodeValue("overflow:visible;opacity:1.0;");
		}
		if(eleProductImageAttr.getNamedItem("opacity")!=null){
			eleProductImageAttr.getNamedItem("opacity").setNodeValue("1.0");
		}
		String imageType = "";
		String oldImageString = eleProductImageAttr.getNamedItemNS("http://www.w3.org/1999/xlink", "href").getNodeValue();
		String pngImageString = new String(oldImageString);
		String wholeImageString="";
		if(oldImageString.contains("image/jpeg")){
			imageType= "jpeg";
			wholeImageString=oldImageString.replaceAll("data:image/jpeg;base64,", "");
		}else if(oldImageString.contains("image/png")){
			imageType = "png";
			wholeImageString=oldImageString.replaceAll("data:image/png;base64,", "");
		}
		/**end**/
		
		
		/**************处理剪影,产生svg*****************/
		StringBuilder outBase64String = new StringBuilder();//String build
		BinaryColor.convertProfile(new String(wholeImageString), outBase64String,new Color(255,255,255,0), new Color(0,0,0,255), "png");//put it into png all at this phase.
		String imageHead = "data:image/png;base64,";
		String imageStr= imageHead+outBase64String.toString();
		eleProductImageAttr.getNamedItemNS("http://www.w3.org/1999/xlink", "href").setNodeValue(imageStr);
		String profileUUID = UUID.randomUUID().toString().replace("-", "");
		String profileFileName = path+"svg/" + profileUUID + ".svg";
		File newFileFolder = new File(path+"svg/");
		if (!newFileFolder.exists()) {
			newFileFolder.mkdir();
		}
		SvgUtilities.saveDoc2SvgFile(doc, profileFileName);
		String urlProfile = "http://" + ip + ":" + port + "/static/images/svg/";
		imageDb.setProfileImageUrl(urlProfile + profileUUID + ".svg");
		/**************处理剪影结束,产生svg*****************/
		

		/**************处理产生png,要两张，一张产品的，一张特征线*****************/
		StringBuilder outBase64StringTrans = new StringBuilder();//String build
		BinaryColor.convertTransBack(new String(wholeImageString), outBase64StringTrans);
	
		String imageStrTrans= imageHead+outBase64StringTrans.toString();
		
		eleProductImageAttr.getNamedItemNS("http://www.w3.org/1999/xlink", "href").setNodeValue(imageStrTrans);
		String pngUUID = UUID.randomUUID().toString().replace("-", "");
		String pngFileName = path+"png/" + pngUUID + ".png";
		
		String pngFeatureUUID = UUID.randomUUID().toString().replace("-", "");
		String pngFeatureFileName = path+"png/" + pngFeatureUUID + ".png";
		
		File newPngFileFolder = new File(path+"png/");
		if (!newPngFileFolder.exists()) {
			newPngFileFolder.mkdir();
		}
		displayFeatureLine(featureLine,"none");
		
//		SvgUtilities.saveDoc2SvgFile(doc, "D:/1/2.svg");
		
		BinaryColor.convertDom2Png(doc, pngFileName);
		
		eleProductImage.setAttribute("display", "none");
		displayFeatureLine(featureLine,"block");
		
		BinaryColor.convertDom2Png(doc, pngFeatureFileName);
		
		String urlPng = "http://" + ip + ":" + port + "/static/images/png/";
		imageDb.setPngImageUrl(urlPng+ pngUUID + ".png");
//		String urlFeaturePng = "http://" + ip + ":" + port + "/static/images/png/";
		imageDb.setFeatureUrl(urlPng+ pngFeatureUUID + ".png");
		
		imageDb.setImageName(v.getProductCategory());
		imageDb.setImageType("WHOLE");
		imageDb.setImageUrl(v.getImageUrl1());//+ ";" + v.getImageUrl2()
		imageDb.setParentImageId(null);
		
		for (Component c : componets) {
			handleSubImage(imageDb, keyPoints, doc, c, v.getId().toString(),ratioX,ratioY);//处理子图
		}
		
		/****合并子图****/

		List<Image> cabs = imageRepo.findByParentImageIdAndImageNameLike(imageDb.getImageId(), "司机室%");
		if(cabs.size()>0){
			Image newCab = merge(cabs,imageDb.getImageId(), doc);
			imageRepo.save(newCab);
		}

		List<Image> braces = imageRepo.findByParentImageIdAndImageNameLike(imageDb.getImageId(), "钢轮支架%");
		if(braces.size()>0){
			Image brace = merge(braces,imageDb.getImageId(), doc);
			imageRepo.save(brace);
		}

		List<Image> realCowls = imageRepo.findByParentImageIdAndImageNameLike(imageDb.getImageId(), "后罩%");
		if(realCowls.size()>0){
			Image realCowl = merge(realCowls,imageDb.getImageId(), doc);
			imageRepo.save(realCowl);
		}
		
		/****End****/
		imageDb.setContainFeatureLine(true);
		imageRepo.save(imageDb);
		images.add(imageDb);//for brands.
	}

	private void handleSubImage(Image i, List<Point2D> keyPointes, SVGDocument doc, Component c,String id,float xRatio,float yRatio) {
		try {
			Element featureLine = doc.getElementById("特征线");

			Element eleProductImage = (Element) (featureLine.getElementsByTagName("image").item(0));
			
			Image detail = new Image();
			detail.setImageId(UUID.randomUUID().toString().replaceAll("-", ""));
			detail.setImageDesc(c.name);
			detail.setImageName(c.name);
			detail.setImageType("DETAIL");

			detail.setParentImageId(i.getImageId());
			if (c.image.customData == null) {
				logger.info("sub area is null.");
				return;
			}
			detail.setX((int) (c.image.customData.boundW * c.image.customData.x/xRatio));
			detail.setY((int) (c.image.customData.boundH * c.image.customData.y/yRatio));
			detail.setW((int)(c.image.customData.w/xRatio));
			detail.setH((int)(c.image.customData.h/yRatio));

			/**处理图**/
			displayFeatureLine(featureLine,"none");
			eleProductImage.setAttribute("display", "block");
			SaveAsPngTiles saver = new SaveAsPngTiles();

			String imageUUID = UUID.randomUUID().toString().replace("-", "");
			String imageFileName = path +id+"/"+ imageUUID + ".png";
			
			File newFileFolder = new File(path +id);
			if (!newFileFolder.exists()) {
				newFileFolder.mkdir();
			}
			
			String urlDetail = "http://" + ip + ":" + port + "/static/images/"+id+"/";
			detail.setImageUrl(urlDetail + imageUUID + ".png");

			File newFile = new File(imageFileName);
			if (!newFile.exists()) {
				newFile.createNewFile();
			}
			InputStream in = SvgUtilities.documentToString(doc);
			saver.tile(in, imageFileName,
					new Rectangle(detail.getX(), detail.getY(), detail.getW(), detail.getH()));
			/**处理图end**/
			
			/**处理线**/
			displayFeatureLine(featureLine,"block");
			eleProductImage.setAttribute("display", "none");
			String featureUUID = UUID.randomUUID().toString().replace("-", "");
			String featureFileName = path + id+"/"+featureUUID + ".png";

			detail.setFeatureUrl(urlDetail + featureUUID + ".png");

			File newfeatureFile = new File(featureFileName);
			if (!newfeatureFile.exists()) {
				newfeatureFile.createNewFile();
			}
			InputStream in2 = SvgUtilities.documentToString(doc);
			saver.tile(in2, featureFileName,
					new Rectangle(detail.getX(), detail.getY(), detail.getW(), detail.getH()));

			PlainRect r = new PlainRect(detail.getX(), detail.getY(), detail.getW(), detail.getH());
			boolean containFeatureLine = false;
			for (Point2D p : keyPointes) {
				containFeatureLine = r.isInside(p.getX(), p.getY());
				if (containFeatureLine) {
					break;
				}
			}
			detail.setContainFeatureLine(containFeatureLine);
			imageRepo.save(detail);
		} catch (Exception e) {
			logger.error("insert detail image erro.");
			e.printStackTrace();
		}
	}
	
	/**
	 * 显示或隐藏特征线，真正的线。
	 * @param featureLine
	 * @param display
	 */
	private void displayFeatureLine(Element featureLine,String display){
		int paths= featureLine.getElementsByTagName("path").getLength();
		for(int i=0; i<paths;i++){
			Element path = (Element) featureLine.getElementsByTagName("path").item(i);
			path.setAttribute("display", display);
		}
		
		int polylines= featureLine.getElementsByTagName("polyline").getLength();
		for(int i=0; i<polylines;i++){
			Element polyline = (Element) featureLine.getElementsByTagName("polyline").item(i);
			polyline.setAttribute("display", display);
		}
		
		int lines= featureLine.getElementsByTagName("line").getLength();
		for(int i=0; i<lines;i++){
			Element line = (Element) featureLine.getElementsByTagName("line").item(i);
			line.setAttribute("display", display);
		}
	}
	
}
