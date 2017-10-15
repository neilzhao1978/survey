package com.neil.survey.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.neil.survey.module.Image;

public interface ImageRepository extends JpaRepository<Image,String> {
	List<Image> findByImageId(String imageId);
	List<Image> findByParentImageId(String parentImageId);
}
