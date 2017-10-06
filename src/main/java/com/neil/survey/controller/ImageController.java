package com.neil.survey.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
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

import com.neil.survey.module.Image;
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

	
    @Value("${web.upload-path}")
    private String path;
	
	@RequestMapping(value = "/uploadImage", method = RequestMethod.POST)
	@ResponseBody
	public RestResponseEntity<Void> handleFileUpload(HttpServletRequest request) {
		MultipartHttpServletRequest params = ((MultipartHttpServletRequest) request);
		List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
		String type = params.getParameter("type");
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

//	@ResponseBody
//	@RequestMapping(value = "/addImage", method = RequestMethod.POST)
//	public RestResponseEntity<Void> addImage(@RequestBody Image image, @RequestParam("file") MultipartFile file) {
//
//		if (!file.isEmpty()) {
//			try {
//				// 这里只是简单例子，文件直接输出到项目路径下。
//				// 实际项目中，文件需要输出到指定位置，需要在增加代码处理。
//				// 还有关于文件格式限制、文件大小限制，详见：中配置。
//				BufferedOutputStream out = new BufferedOutputStream(
//						new FileOutputStream(new File(file.getOriginalFilename())));
//				out.write(file.getBytes());
//				out.flush();
//				out.close();
//			} catch (FileNotFoundException e) {
//				e.printStackTrace();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		} else {
//			return ResponseGenerator.createFailResponse("Fail to add/update image.", ErrorCode.DB_ERROR);
//		}
//
//		Image b = imageRepo.save(image);
//		if (b != null) {
//			return ResponseGenerator.createSuccessResponse("Add/update image success.");
//		} else {
//			return ResponseGenerator.createFailResponse("Fail to add/update image.", ErrorCode.DB_ERROR);
//		}
//	}

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