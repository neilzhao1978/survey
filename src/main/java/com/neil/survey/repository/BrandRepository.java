package com.neil.survey.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.neil.survey.module.Brand;

public interface BrandRepository extends JpaRepository<Brand,String> {
	List<Brand> findByBrandId(String brandId);
}
