package com.neil.survey.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.apache.batik.transcoder.TranscoderException;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.neil.imagetools.BinaryColor;
import com.neil.survey.inputout.ImagePartRe;
import com.neil.survey.inputout.ImageWholeRe;
import com.neil.survey.module.Brand;
import com.neil.survey.module.GeneCombineImage;
import com.neil.survey.module.Image;
import com.neil.survey.module.ProfileCombine;
import com.neil.survey.repository.BrandRepository;
import com.neil.survey.repository.ImageRepository;
import com.neil.survey.service.IImageProcessService;
import com.neil.survey.util.ErrorCode;
import com.neil.survey.util.PageEntity;
import com.neil.survey.util.ResponseGenerator;
import com.neil.survey.util.RestResponseEntity;

@RestController
@RequestMapping("/api/imageService")
@Transactional
public class ImageController {

	@Autowired
	private ImageRepository imageRepo;
	
	@Autowired
	private BrandRepository brandRepo;

	@Autowired
	private IImageProcessService imageProcessService;
	
	@ResponseBody
	@RequestMapping(value = "/getAllImages", method = RequestMethod.GET)
	public RestResponseEntity<List<Image>> getAllImages(@RequestParam(value = "page", required = true) PageEntity page,
			@RequestParam(value = "type", required = false) String type) {

		PageRequest pageRequest = new PageRequest(page.getPageNumber()-1, page.getPageSize(), null);
		ExampleMatcher matcher = ExampleMatcher.matching().withMatcher("imageType", match -> match.exact());
		Image i = new Image();
		i.setImageType(type);
		Example<Image> example = Example.of(i, matcher);
		Page<Image> images = imageRepo.findAll(example, pageRequest);
		if (images != null && images.getSize() > 0) {
			return ResponseGenerator.createSuccessResponse("获取图像列表成功。", images.getContent().size(),
					images.getContent(), images.getTotalElements());
		} else {
			return ResponseGenerator.createFailResponse("获取图像列表失败。", ErrorCode.DB_ERROR);
		}
	}

	@ResponseBody
	@RequestMapping(value = "/getProductImagesByBrandId", method = RequestMethod.GET)
	public RestResponseEntity<List<Image>> getProductImagesByBrandId(
			@RequestParam(value = "brandId", required = false) String brandId) {
		List<Brand> brands = brandRepo.findByBrandId(brandId);
		List<Image> images = new ArrayList<Image>();
		Brand b = null;
		if(brands.size()>0) {
			b = brands.get(0);
			Set<Image> imagesT = b.getImages();
			images.addAll(imagesT);
			return ResponseGenerator.createSuccessResponse("获取品牌相关图像列表成功。", images.size(),images, images.size());
		}else {
			return ResponseGenerator.createFailResponse("获取品牌相关图像列表失败。", ErrorCode.DB_ERROR);
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/getDetailImagesByParentId", method = RequestMethod.GET)
	public RestResponseEntity<List<Image>> getDetailImagesByParentId(
			@RequestParam(value = "parentId", required = false) String parentId) {
		List<Image> images = imageRepo.findByParentImageId(parentId);
		if(images.size()>0) {
			return ResponseGenerator.createSuccessResponse("获取细节图像列表成功。", images.size(),images, images.size());
		}else {
			return ResponseGenerator.createFailResponse("获取细节图像列表失败。", ErrorCode.DB_ERROR);
		}
	}
	
    @Value("${web.upload-path}")
    private  String path;
	
    @Value("${server.port}")
    private  String port;
    
    @Value("${web.ip}")
    private  String ip;
    

    
	@RequestMapping(value = "/uploadImage", method = RequestMethod.POST)
	@ResponseBody
	public RestResponseEntity<Void> handleFileUpload(HttpServletRequest request) {
		MultipartHttpServletRequest params = ((MultipartHttpServletRequest) request);
		List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
		String type = params.getParameter("type");
		String desc = params.getParameter("desc");
		MultipartFile file = null;
		BufferedOutputStream stream = null;
		
		 String host = ip;
		 
//		Enumeration allNetInterfaces;
//		try {
//			allNetInterfaces = NetworkInterface.getNetworkInterfaces();
//			InetAddress ip = null;
//			while (allNetInterfaces.hasMoreElements()) {
//				NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
//				System.out.println(netInterface.getName());
//				Enumeration addresses = netInterface.getInetAddresses();
//				while (addresses.hasMoreElements()) {
//					ip = (InetAddress) addresses.nextElement();
//					if (ip != null && ip instanceof Inet4Address) {
//						host = ip.getHostAddress();
////						System.out.println("本机的IP = " + ip.getHostAddress());
//					}
//				}
//			}
//		} catch (Exception e1) {
//			return ResponseGenerator.createFailResponse("Fail to upload image."+" Cannnot get host ip", ErrorCode.DB_ERROR);
//		}

		 
//	        try {
//	            host = "192.168.0.247";//TODO
//	        } catch (Exception e) {
//	        	return ResponseGenerator.createFailResponse("Fail to upload image."+" Cannnot get host ip", ErrorCode.DB_ERROR);
//	        }
		
		for (int i = 0; i < files.size(); ++i) {
			
			file = files.get(i);
			
			String imageUUID = UUID.randomUUID().toString().replace("-", "");
			 String fileName = imageUUID;
			 String suffix =file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1);
			 fileName +=".";
			 fileName +=suffix;
			
			if (!file.isEmpty()) {
				try {
					byte[] bytes = file.getBytes();
					File f = new File(fileName);
					stream = new BufferedOutputStream(new FileOutputStream(f));
					stream.write(bytes);
					stream.close();
					File dirUpload = new File(path+"/upload/");
					if(!dirUpload.exists()){
						dirUpload.mkdir();
					}
					
					FileCopyUtils.copy(f, new File(path+"/upload/"+fileName));
					
					Image image = new Image();
					image.setImageId(imageUUID);
					image.setImageName(file.getOriginalFilename());
					image.setImageType(type);
					String url = "http://"+host+":"+port+"/allimages/upload/";
					image.setImageUrl(url+fileName);
					image.setImageDesc(desc);
					Image b = imageRepo.save(image);
					
				} catch (Exception e) {
					stream = null;
					return ResponseGenerator.createFailResponse("上传图像失败。"+e.getMessage(), ErrorCode.DB_ERROR);
				}
			} else {
				return ResponseGenerator.createFailResponse("上传图像失败."+"图像为空。", ErrorCode.DB_ERROR);
			}
		}
		return ResponseGenerator.createSuccessResponse("新增/上传图像成功。");
	}

	@ResponseBody
	@RequestMapping(value = "/updateImage", method = RequestMethod.POST)
	public RestResponseEntity<Void> updateImage(@RequestBody Image image) {
		Image b = imageRepo.save(image);
		if (b != null) {
			return ResponseGenerator.createSuccessResponse("新增/上传图像成功。");
		} else {
			return ResponseGenerator.createFailResponse("新增/上传图像失败。", ErrorCode.DB_ERROR);
		}
	}

	@ResponseBody
	@RequestMapping(value = "/deleteImage", method = RequestMethod.DELETE)
	public RestResponseEntity<Void> deleteCreator(@RequestBody Image image) {
		try {
			imageRepo.delete(image);
			return ResponseGenerator.createSuccessResponse("删除图像成功.");
		} catch (Exception e) {
			return ResponseGenerator.createFailResponse("删除图像失败.", ErrorCode.DB_ERROR);
		}
	}
	

	
	@ResponseBody
	@RequestMapping(value = "/geneProfileImage", method = RequestMethod.POST)
	public RestResponseEntity<String> geneProfileImage(@RequestBody ProfileCombine profileCombine) throws IOException, TranscoderException {
			byte[] result = imageProcessService.getCombinedImage(profileCombine);
			return ResponseGenerator.createSuccessResponse("产生成功",1,Base64.encodeBase64String(result),null);
	}
	
//	@ResponseBody
//	@RequestMapping(value = "/getCartoonWholeImage", method = RequestMethod.GET)
//	public RestResponseEntity<ImageWholeRe> getCartoonWholeImage(@RequestParam(value = "imageId",required=true) String imageId){
//		try{
//			
//			ImageWholeRe rtn = new ImageWholeRe();
//			
//			List<Image> rtnImages = imageProcessService.getCartoonBaseImage(imageId);
//			if(rtnImages.size()>0){
//				Image whole = rtnImages.get(0);
//				rtn.setWholeImageUrl(whole.getImageUrl());
//				rtn.setH(whole.getH());
//				rtn.setW(whole.getW());
//				List<ImagePartRe> parts = new ArrayList<ImagePartRe>();
//				for(int i = 1;i<rtnImages.size();i++){
//					ImagePartRe part = new ImagePartRe();
//					part.setH(rtnImages.get(i).getH());
//					part.setW(rtnImages.get(i).getW());
//					part.setX(rtnImages.get(i).getX());
//					part.setY(rtnImages.get(i).getY());
//					part.setName(rtnImages.get(i).getImageName());
//					part.setUrl(null);
//					parts.add(part);
//				}
//				rtn.setAllParts(parts);
//			}else{
//				return ResponseGenerator.createFailResponse("获取产品整体图像失败.", ErrorCode.DB_ERROR);
//			}
//			
//			return ResponseGenerator.createSuccessResponse("获取产品整体图像成功。",1,rtn,null);
//		}catch(Exception e){
//			return ResponseGenerator.createFailResponse("获取产品整体图像失败.", ErrorCode.DB_ERROR);
//		}
//	}
	
//	@ResponseBody
//	@RequestMapping(value = "/getCartoonReplaceImage", method = RequestMethod.GET)
//	public RestResponseEntity<List<ImagePartRe>> getCartoonReplaceImage(@RequestParam(value = "imageId",required=true) String imageId,
//			@RequestParam(value = "partName",required=true) String partName){
//		try{
//			List<ImagePartRe> rtParts = new ArrayList<ImagePartRe>();
//			List<Image> rtn = imageProcessService.getCartoonReplaceImage(imageId,partName,"WHOLE");
//			if(rtn.size()>0){
//				for(Image i : rtn){
//					ImagePartRe part = new ImagePartRe();
//					part.setH(i.getH());
//					part.setW(i.getW());
//					part.setX(i.getX());
//					part.setY(i.getY());
//					part.setUrl(i.getImageUrl());
//					part.setName(i.getImageName());
//					rtParts.add(part);
//				}
//				return ResponseGenerator.createSuccessResponse("获取产品可替换图像成功。",rtParts.size(),rtParts,null);
//			}else{
//				return ResponseGenerator.createFailResponse("获取产品可替换图像失败.", ErrorCode.DB_ERROR);
//			}
//			
//		}catch(Exception e){
//			return ResponseGenerator.createFailResponse("获取产品可替换图像失败.", ErrorCode.DB_ERROR);
//		}
//	}
	
	

	@Value("${color.master}")
	private   String masterColor;
	
	@Value("${color.driverRoom}")
	private   String driverRoomColor;
	
	@Value("${color.wheel}")
	private   String wheelColor;
	
	@Value("${color.rearHood}")
	private   String rearHoodColor;
	
    
    

    
	
	@ResponseBody
	@RequestMapping(value = "/processImage", method = RequestMethod.POST)
	public RestResponseEntity<ImagePartRe> processImage(@RequestBody GeneCombineImage geneCombineImage) throws IOException{
		
		ImagePartRe part = new ImagePartRe();
		List<String> partNms = new ArrayList<String>();
		List<Image> srcImages = new ArrayList<Image>();
		List<Image> drivers = null;
		List<Image> wheels = null;
		List<Image> rearHoods = null;
		List<String> imageIds = new ArrayList<String>();
		
		if(geneCombineImage.getMode().equalsIgnoreCase("stitch")){
			if(geneCombineImage.getDriverRoom()!=null&&geneCombineImage.getDriverRoom().length()>0){
				partNms.add("司机室");
				drivers = imageProcessService.getCartoonReplaceImage(geneCombineImage.getDriverRoom(),"司机室");
				srcImages.addAll(drivers);
			}
			if(geneCombineImage.getWheel()!=null&&geneCombineImage.getWheel().length()>0){
				partNms.add("钢轮支架");
				wheels = imageProcessService.getCartoonReplaceImage(geneCombineImage.getWheel(),"钢轮支架");
				srcImages.addAll(wheels);
			}
			if(geneCombineImage.getRearHood()!=null&&geneCombineImage.getRearHood().length()>0){
				partNms.add("后罩");
				rearHoods = imageProcessService.getCartoonReplaceImage(geneCombineImage.getRearHood(),"后罩");
				srcImages.addAll(rearHoods);
			}
		}else if (geneCombineImage.getMode().equalsIgnoreCase("overlap")){
//			if(geneCombineImage.getMaster()!=null&&geneCombineImage.getMaster().length()>0){
//				imageIds.add(geneCombineImage.getMaster());
//			}
			if(geneCombineImage.getDriverRoom()!=null&&geneCombineImage.getDriverRoom().length()>0){
				imageIds.add(geneCombineImage.getDriverRoom());
			}
			if(geneCombineImage.getWheel()!=null&&geneCombineImage.getWheel().length()>0) {
				imageIds.add(geneCombineImage.getWheel());
			}
			if(geneCombineImage.getRearHood()!=null&&geneCombineImage.getRearHood().length()>0){
				imageIds.add(geneCombineImage.getRearHood());
			}
		}
		
		List<String> imageUrls = new ArrayList<String>();
		List<String> featureUrls = new ArrayList<String>();
		List<Image> baseImages = imageProcessService.getCartoonBaseImage(geneCombineImage.getMaster(),partNms);
		if(geneCombineImage.getMode().equalsIgnoreCase("stitch")){
			//////////////////
		    Map<String,Integer> colorMap = new HashMap<String,Integer>();

	    	Integer masterColorI = Integer.valueOf(masterColor,16);
	    	Integer driverRoomColorI = Integer.valueOf(driverRoomColor,16);
	    	Integer wheelColorI = Integer.valueOf(wheelColor,16);
	    	Integer rearHoodColorI = Integer.valueOf(rearHoodColor,16);
	    	colorMap.put("master", masterColorI);
	    	colorMap.put("driverRoom", driverRoomColorI);
	    	colorMap.put("wheel", wheelColorI);
	    	colorMap.put("rearHood", rearHoodColorI);
			//////////////////

			
			
			String[] str = BinaryColor.combineStitchImage(baseImages.get(0), baseImages.subList(1, baseImages.size()), srcImages,colorMap);
			
			part.setCombinedImage(str[0]);
			part.setCombinedFeature(str[1]);
			part.setH(baseImages.get(0).getH());
			part.setW(baseImages.get(0).getW());
			part.setX(null);
			part.setY(null);
			part.setUrl(baseImages.get(0).getImageUrl());
			part.setName(baseImages.get(0).getImageName());
			return ResponseGenerator.createSuccessResponse("获取产品可替换图像成功。",1,part,null);
		}else if (geneCombineImage.getMode().equalsIgnoreCase("overlap")){
			List<Image> images = imageProcessService.getCartoonBaseImage(imageIds);
			for(Image image:images){
				imageUrls.add(image.getPngImageUrl());
				featureUrls.add(image.getFeatureUrl());
			}
			String image = BinaryColor.combineWholeImage(baseImages.get(0).getPngImageUrl(), imageUrls);//0处为master。
			String line = BinaryColor.combineWholeImage(baseImages.get(0).getFeatureUrl(), featureUrls);
			
			part.setCombinedImage(image);
			part.setCombinedFeature(line);
			part.setH(baseImages.get(0).getH());
			part.setW(baseImages.get(0).getW());
			part.setX(null);
			part.setY(null);
			part.setUrl(baseImages.get(0).getImageUrl());
			part.setName(baseImages.get(0).getImageName());
			return ResponseGenerator.createSuccessResponse("获取产品可替换图像成功。",1,part,null);
		}
		return ResponseGenerator.createFailResponse("获取产品可替换图像失败。",ErrorCode.DB_ERROR);
	}
	
	
	//	@ResponseBody
	//	@RequestMapping(value = "/getCartoonWholeImage", method = RequestMethod.GET)
	//	public RestResponseEntity<ImageWholeRe> getCartoonWholeImage(@RequestParam(value = "imageId",required=true) String imageId){
	//		try{
	//			
	//			ImageWholeRe rtn = new ImageWholeRe();
	//			
	//			List<Image> rtnImages = imageProcessService.getCartoonBaseImage(imageId);
	//			if(rtnImages.size()>0){
	//				Image whole = rtnImages.get(0);
	//				rtn.setWholeImageUrl(whole.getImageUrl());
	//				rtn.setH(whole.getH());
	//				rtn.setW(whole.getW());
	//				List<ImagePartRe> parts = new ArrayList<ImagePartRe>();
	//				for(int i = 1;i<rtnImages.size();i++){
	//					ImagePartRe part = new ImagePartRe();
	//					part.setH(rtnImages.get(i).getH());
	//					part.setW(rtnImages.get(i).getW());
	//					part.setX(rtnImages.get(i).getX());
	//					part.setY(rtnImages.get(i).getY());
	//					part.setName(rtnImages.get(i).getImageName());
	//					part.setUrl(null);
	//					parts.add(part);
	//				}
	//				rtn.setAllParts(parts);
	//			}else{
	//				return ResponseGenerator.createFailResponse("获取产品整体图像失败.", ErrorCode.DB_ERROR);
	//			}
	//			
	//			return ResponseGenerator.createSuccessResponse("获取产品整体图像成功。",1,rtn,null);
	//		}catch(Exception e){
	//			return ResponseGenerator.createFailResponse("获取产品整体图像失败.", ErrorCode.DB_ERROR);
	//		}
	//	}
		
	//	@ResponseBody
	//	@RequestMapping(value = "/getCartoonReplaceImage", method = RequestMethod.GET)
	//	public RestResponseEntity<List<ImagePartRe>> getCartoonReplaceImage(@RequestParam(value = "imageId",required=true) String imageId,
	//			@RequestParam(value = "partName",required=true) String partName){
	//		try{
	//			List<ImagePartRe> rtParts = new ArrayList<ImagePartRe>();
	//			List<Image> rtn = imageProcessService.getCartoonReplaceImage(imageId,partName,"WHOLE");
	//			if(rtn.size()>0){
	//				for(Image i : rtn){
	//					ImagePartRe part = new ImagePartRe();
	//					part.setH(i.getH());
	//					part.setW(i.getW());
	//					part.setX(i.getX());
	//					part.setY(i.getY());
	//					part.setUrl(i.getImageUrl());
	//					part.setName(i.getImageName());
	//					rtParts.add(part);
	//				}
	//				return ResponseGenerator.createSuccessResponse("获取产品可替换图像成功。",rtParts.size(),rtParts,null);
	//			}else{
	//				return ResponseGenerator.createFailResponse("获取产品可替换图像失败.", ErrorCode.DB_ERROR);
	//			}
	//			
	//		}catch(Exception e){
	//			return ResponseGenerator.createFailResponse("获取产品可替换图像失败.", ErrorCode.DB_ERROR);
	//		}
	//	}
		
		
//		@ResponseBody
//		@RequestMapping(value = "/processImage", method = RequestMethod.POST)
//		public RestResponseEntity<ImagePartRe> processImage(@RequestBody GeneCombineImage geneCombineImage) throws IOException{
//			
//			ImagePartRe part = new ImagePartRe();
//			List<String> partNms = new ArrayList<String>();
//			List<Image> srcImages = new ArrayList<Image>();
//			List<Image> drivers = null;
//			List<Image> wheels = null;
//			List<Image> rearHoods = null;
//			List<String> imageIds = new ArrayList<String>();
//			
//			if(geneCombineImage.getMode().equalsIgnoreCase("stitch")){
//				if(geneCombineImage.getDriverRoom()!=null&&geneCombineImage.getDriverRoom().length()>0){
//					partNms.add("司机室");
//					drivers = imageProcessService.getCartoonReplaceImage(geneCombineImage.getDriverRoom(),"司机室");
//					srcImages.addAll(drivers);
//				}
//				if(geneCombineImage.getWheel()!=null&&geneCombineImage.getWheel().length()>0){
//					partNms.add("钢轮支架");
//					wheels = imageProcessService.getCartoonReplaceImage(geneCombineImage.getWheel(),"钢轮支架");
//					srcImages.addAll(wheels);
//				}
//				if(geneCombineImage.getRearHood()!=null&&geneCombineImage.getRearHood().length()>0){
//					partNms.add("后罩");
//					rearHoods = imageProcessService.getCartoonReplaceImage(geneCombineImage.getRearHood(),"后罩");
//					srcImages.addAll(rearHoods);
//				}
//			}else if (geneCombineImage.getMode().equalsIgnoreCase("overlap")){
//	//			if(geneCombineImage.getMaster()!=null&&geneCombineImage.getMaster().length()>0){
//	//				imageIds.add(geneCombineImage.getMaster());
//	//			}
//				if(geneCombineImage.getDriverRoom()!=null&&geneCombineImage.getDriverRoom().length()>0){
//					imageIds.add(geneCombineImage.getDriverRoom());
//				}
//				if(geneCombineImage.getWheel()!=null&&geneCombineImage.getWheel().length()>0) {
//					imageIds.add(geneCombineImage.getWheel());
//				}
//				if(geneCombineImage.getRearHood()!=null&&geneCombineImage.getRearHood().length()>0){
//					imageIds.add(geneCombineImage.getRearHood());
//				}
//			}
//			
//			List<String> imageUrls = new ArrayList<String>();
//			List<String> featureUrls = new ArrayList<String>();
//			List<Image> baseImages = imageProcessService.getCartoonBaseImage(geneCombineImage.getMaster(),partNms);
//			if(geneCombineImage.getMode().equalsIgnoreCase("stitch")){
//				String[] str = BinaryColor.combineStitchImage(baseImages.get(0), baseImages.subList(1, baseImages.size()), srcImages);
//				
//				part.setCombinedImage(str[0]);
//				part.setCombinedFeature(str[1]);
//				part.setH(baseImages.get(0).getH());
//				part.setW(baseImages.get(0).getW());
//				part.setX(null);
//				part.setY(null);
//				part.setUrl(baseImages.get(0).getImageUrl());
//				part.setName(baseImages.get(0).getImageName());
//				return ResponseGenerator.createSuccessResponse("获取产品可替换图像成功。",1,part,null);
//			}else if (geneCombineImage.getMode().equalsIgnoreCase("overlap")){
//				List<Image> images = imageProcessService.getCartoonBaseImage(imageIds);
//				for(Image image:images){
//					imageUrls.add(image.getPngImageUrl());
//					featureUrls.add(image.getFeatureUrl());
//				}
//				String image = BinaryColor.combineWholeImage(baseImages.get(0).getPngImageUrl(), imageUrls);//0处为master。
//				String line = BinaryColor.combineWholeImage(baseImages.get(0).getFeatureUrl(), featureUrls);
//				
//				part.setCombinedImage(image);
//				part.setCombinedFeature(line);
//				part.setH(baseImages.get(0).getH());
//				part.setW(baseImages.get(0).getW());
//				part.setX(null);
//				part.setY(null);
//				part.setUrl(baseImages.get(0).getImageUrl());
//				part.setName(baseImages.get(0).getImageName());
//				return ResponseGenerator.createSuccessResponse("获取产品可替换图像成功。",1,part,null);
//			}
//			return ResponseGenerator.createFailResponse("获取产品可替换图像失败。",ErrorCode.DB_ERROR);
//		}

	@ResponseBody
	@RequestMapping(value = "/getCartoonReplaceImageExt", method = RequestMethod.GET)
	public RestResponseEntity<ImagePartRe> getCartoonReplaceImageExt(
			@RequestParam(value = "baseImageId",required=true) String baseImageId,
			@RequestParam(value = "replaceImageId",required=false) String replaceImageId,
			@RequestParam(value = "partName",required=false) String partName){
		try{

			replaceImageId = replaceImageId==null?"null":replaceImageId;
			partName = partName==null?"%%":partName;
			ImagePartRe part = new ImagePartRe();
			
			List<Image> baseImages = imageProcessService.getCartoonBaseImage(baseImageId,null);//debug partName
			
			List<Image> replaceImages = imageProcessService.getCartoonReplaceImage(replaceImageId,partName);
			if(baseImages.size()==2 && replaceImages.size()==1){//baseImages 返回模板和被替换部件，所以为2，replaceImages只能为一个。
				
				String image = BinaryColor.combineImage(baseImages.get(0).getPngImageUrl(),replaceImages.get(0).getImageUrl(),
						baseImages.get(1).getX(),baseImages.get(1).getY());
				String line = BinaryColor.combineImage(baseImages.get(0).getFeatureUrl(),replaceImages.get(0).getFeatureUrl(),
						baseImages.get(1).getX(),baseImages.get(1).getY());
				
				part.setCombinedImage(image);
				part.setCombinedFeature(line);
				part.setH(baseImages.get(0).getH());
				part.setW(baseImages.get(0).getW());
				part.setX(null);
				part.setY(null);
				part.setUrl(baseImages.get(0).getImageUrl());
				part.setName(baseImages.get(0).getImageName());

				return ResponseGenerator.createSuccessResponse("获取产品可替换图像成功。",1,part,null);
			}else if (baseImages.size()==1 && replaceImages.size()==0){
				String image = BinaryColor.combineImage(baseImages.get(0).getPngImageUrl(),null,0,0);
				String line = BinaryColor.combineImage(baseImages.get(0).getFeatureUrl(),null,0,0);
				
				part.setCombinedImage(image);
				part.setCombinedFeature(line);
				part.setH(baseImages.get(0).getH());
				part.setW(baseImages.get(0).getW());
				part.setX(null);
				part.setY(null);
				part.setUrl(baseImages.get(0).getImageUrl());
				part.setName(baseImages.get(0).getImageName());

				return ResponseGenerator.createSuccessResponse("获取产品可替换图像成功。",1,part,null);
			}else{				
				return ResponseGenerator.createFailResponse("获取产品可替换图像失败.", ErrorCode.DB_ERROR);
			}
			
		}catch(Exception e){
			return ResponseGenerator.createFailResponse("获取产品可替换图像失败.", ErrorCode.DB_ERROR);
		}
	}

	
}
