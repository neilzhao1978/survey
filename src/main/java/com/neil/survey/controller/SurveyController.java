package com.neil.survey.controller;

import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OptionalDataException;
import java.net.URL;
import java.nio.file.FileSystems;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.apache.batik.anim.dom.SAXSVGDocumentFactory;
import org.apache.batik.dom.svg.SVGDocumentFactory;
import org.apache.batik.util.XMLResourceDescriptor;
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
import com.neil.batiktools.SaveAsJPEGTiles;
import com.neil.batiktools.SvgUtilities;
import com.neil.survey.module.Answer;
import com.neil.survey.module.Brand;
import com.neil.survey.module.BrandResp;
import com.neil.survey.module.Brand_P;
import com.neil.survey.module.Component;
import com.neil.survey.module.Image;
import com.neil.survey.module.ImageCount;
import com.neil.survey.module.Survey;
import com.neil.survey.module.VehicheResp;
import com.neil.survey.module.VehicleInfo_P;
import com.neil.survey.repository.AnswerRepository;
import com.neil.survey.repository.BrandRepository;
import com.neil.survey.repository.ImageRepository;
import com.neil.survey.repository.SurveyRepository;
import com.neil.survey.util.ErrorCode;
import com.neil.survey.util.PageEntity;
import com.neil.survey.util.ResponseGenerator;
import com.neil.survey.util.RestResponseEntity;
import com.neil.survey.util.SortTools;

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
		BrandResp brandResp = null;
		brandResp = template.getForObject(serverAddr + "getAllBrand", BrandResp.class);
		List<Brand_P> x = brandResp.object;

		String parser = XMLResourceDescriptor.getXMLParserClassName();
		SVGDocumentFactory f = new SAXSVGDocumentFactory(parser);

		for (Brand_P b : x) {
			Brand brand = new Brand();
			brand.setBrandId(b.getId().toString());
			brand.setBrandIconUrl(b.getIcon());
			brand.setBrandName(b.getName());
			brand.setDesc(b.getDescription());

			VehicheResp vehicheResp = null;
			vehicheResp = template.getForObject(serverAddr + "getProductByBrand/{id}", VehicheResp.class,
					b.getId().toString());

			Set<Image> images = new HashSet<Image>();

			for (VehicleInfo_P v : vehicheResp.object) {
				try {
					Image i = new Image();
					i.setImageId(v.getId().toString());
					i.setImageDesc("brand:" + b.getName() + ".category:" + v.getCategoryName());
					List<Point2D> keyPointes = SvgUtilities.getAllKeyPoits(v.getImageUrl1());

					URL url = new URL(v.getImageUrl1());
					SVGDocument doc = (SVGDocument) f.createSVGDocument(v.getImageUrl1(),
							new BufferedInputStream(url.openStream(), 2 * 1024 * 1024));

					doc.getChildNodes();

					Element n1 = doc.getElementById("特征线");
					Element n2 = doc.getElementById("产品图片");

					List<Component> componets = JSON.parseArray(v.getComponentInfo(), Component.class);
					for (Component c : componets) {
						try {
							Image detail = new Image();
							detail.setImageId(c.id + "");
							detail.setImageDesc(c.name);
							detail.setImageName(c.name);
							detail.setImageType("DETAIL");

							detail.setParentImageId(i.getImageId());
							if (c.image.customData == null) {
								logger.info("sub area is null.");
								continue;
							}
							detail.setX((int) (c.image.customData.boundW * c.image.customData.x));
							detail.setY((int) (c.image.customData.boundH * c.image.customData.y));
							detail.setW(c.image.customData.w);
							detail.setH(c.image.customData.h);

							SaveAsJPEGTiles saver = new SaveAsJPEGTiles();
							n1.setAttribute("display", "none");
							n2.setAttribute("display", "block");
							String imageUUID = UUID.randomUUID().toString().replace("-", "");
							String imageFileName = path + imageUUID + ".jpg";

							String urlDetail = "http://" + ip + ":" + port + "/static/images/";
							detail.setImageUrl(urlDetail + imageUUID + ".jpg");

							File newFile = new File(imageFileName);
							if (!newFile.exists()) {
								newFile.createNewFile();
							}
							InputStream in = SvgUtilities.documentToString(doc);
							saver.tile(in, imageFileName,
									new Rectangle(detail.getX(), detail.getY(), detail.getW(), detail.getH()));

							//////////////////////////////
							n1.setAttribute("display", "block");
							n2.setAttribute("display", "none");
							String featureUUID = UUID.randomUUID().toString().replace("-", "");
							String featureFileName = path + featureUUID + ".jpg";

							detail.setImageUrl(urlDetail + featureFileName + ".jpg");

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
					String temp = keyPointes.toString().replaceAll("Point2D.Double", "").replaceAll("Point2D.Float","");
					i.setAllKeyPoints(temp.substring(1, temp.length() - 1));
					i.setImageName(v.getProductCategory());
					i.setImageType("WHOLE");
					i.setImageUrl(v.getImageUrl1() + ";" + v.getImageUrl2());
					i.setParentImageId(null);
					images.add(i);

					imageRepo.save(i);
				} catch (Exception e) {
					logger.error("insert whole image erro.");
					e.printStackTrace();
				}
				brand.setImages(images);
				brandRepo.save(brand);
			}

		}

		return ResponseGenerator.createSuccessResponse("Db sych success.");

	}
}
