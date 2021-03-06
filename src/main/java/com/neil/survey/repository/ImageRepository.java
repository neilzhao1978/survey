package com.neil.survey.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.neil.survey.module.Image;

public interface ImageRepository extends JpaRepository<Image,String> {
	List<Image> findByImageId(String imageId);
	List<Image> findByParentImageId(String parentImageId);
	
	List<Image> findByParentImageIdAndImageType(String parentImageId,String imageType);
	
//	@Query("select i from Image i where i.parentImageId in (?) and i.imageName like ? and containFeatureLine= ?")
//	List<Image> findByInputParam(List<String> ids, String partsName, boolean containFeature);
	
	List<Image> findByImageType(String imageType);
	
	List<Image> findByParentImageIdAndImageNameLike(String id, String partsName);
	List<Image> findByImageTypeAndParentImageIdAndImageNameLike(String imageType,String id, String partsName);
	List<Image> findByImageTypeAndImageNameLike(String imageType, String partsName);
	
	
	List<Image> findByImageIdIn(List<String> imageIds);
}
