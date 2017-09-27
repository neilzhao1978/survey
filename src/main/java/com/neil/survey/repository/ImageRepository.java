package com.neil.survey.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.neil.survey.module.Image;

public interface ImageRepository extends JpaRepository<Image,String> {

}
