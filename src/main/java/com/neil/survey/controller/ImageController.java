package com.neil.survey.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

import com.neil.survey.module.Brand;
import com.neil.survey.module.Image;
import com.neil.survey.repository.BrandRepository;
import com.neil.survey.repository.ImageRepository;
import com.neil.survey.util.ErrorCode;
import com.neil.survey.util.PageEntity;
import com.neil.survey.util.ResponseGenerator;
import com.neil.survey.util.RestResponseEntity;

@RestController
@RequestMapping("/api/imageService")
public class ImageController {

	@Autowired
	private ImageRepository imageRepo;
	
	@Autowired
	private BrandRepository brandRepo;

	@ResponseBody
	@RequestMapping(value = "/getAllImages", method = RequestMethod.GET)
	public RestResponseEntity<List<Image>> getAllImages(@RequestParam(value = "page", required = true) PageEntity page,
			@RequestParam(value = "type", required = false) String type) {

		PageRequest pageRequest = new PageRequest(page.getPageNumber(), page.getPageSize(), null);
		ExampleMatcher matcher = ExampleMatcher.matching().withMatcher("imageType", match -> match.endsWith());
		Image i = new Image();
		i.setImageType(type);
		Example<Image> example = Example.of(i, matcher);
		Page<Image> images = imageRepo.findAll(example, pageRequest);
		if (images != null && images.getSize() > 0) {
			return ResponseGenerator.createSuccessResponse("Get image list success.", images.getContent().size(),
					images.getContent(), images.getTotalElements());
		} else {
			return ResponseGenerator.createFailResponse("Fail to get image list", ErrorCode.DB_ERROR);
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
			return ResponseGenerator.createSuccessResponse("Get brand image list success.", images.size(),images, images.size());
		}else {
			return ResponseGenerator.createFailResponse("Fail to get brand image list", ErrorCode.DB_ERROR);
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/getDetailImagesByParentId", method = RequestMethod.GET)
	public RestResponseEntity<List<Image>> getDetailImagesByParentId(
			@RequestParam(value = "parentId", required = false) String parentId) {
		List<Image> images = imageRepo.findByParentImageId(parentId);
		if(images.size()>0) {
			return ResponseGenerator.createSuccessResponse("Get brand image list success.", images.size(),images, images.size());
		}else {
			return ResponseGenerator.createFailResponse("Fail to get brand image list", ErrorCode.DB_ERROR);
		}
	}
	
    @Value("${web.upload-path}")
    private String path;
	
	@RequestMapping(value = "/uploadImage", method = RequestMethod.POST)
	@ResponseBody
	public RestResponseEntity<Void> handleFileUpload(HttpServletRequest request) {
		MultipartHttpServletRequest params = ((MultipartHttpServletRequest) request);
		List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
		String type = params.getParameter("type");
		String desc = params.getParameter("desc");
		MultipartFile file = null;
		BufferedOutputStream stream = null;
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
					
					FileCopyUtils.copy(f, new File(path+fileName));
					
					Image image = new Image();
					image.setImageId(imageUUID);
					image.setImageName(file.getOriginalFilename());
					image.setImageType(type);
					image.setImageUrl(path+fileName);
					image.setImageDesc(desc);
					Image b = imageRepo.save(image);
					
				} catch (Exception e) {
					stream = null;
					return ResponseGenerator.createFailResponse("Fail to upload image."+e.getMessage(), ErrorCode.DB_ERROR);
				}
			} else {
				return ResponseGenerator.createFailResponse("Fail to upload image."+" Because image is null.", ErrorCode.DB_ERROR);
			}
		}
		return ResponseGenerator.createSuccessResponse("Add/upload image success.");
	}

	@ResponseBody
	@RequestMapping(value = "/updateImage", method = RequestMethod.POST)
	public RestResponseEntity<Void> updateImage(@RequestBody Image image) {
		Image b = imageRepo.save(image);
		if (b != null) {
			return ResponseGenerator.createSuccessResponse("Add/update image success.");
		} else {
			return ResponseGenerator.createFailResponse("Fail to add/update image.", ErrorCode.DB_ERROR);
		}
	}

	@ResponseBody
	@RequestMapping(value = "/deleteImage", method = RequestMethod.DELETE)
	public RestResponseEntity<Void> deleteCreator(@RequestBody Image image) {
		try {
			imageRepo.delete(image);
			return ResponseGenerator.createSuccessResponse("delete image success.");
		} catch (Exception e) {
			return ResponseGenerator.createFailResponse("Fail to delete image.", ErrorCode.DB_ERROR);
		}
	}

}
