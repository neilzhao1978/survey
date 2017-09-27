package com.neil.survey.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.neil.survey.module.Creator;

public interface CreatorRepository extends JpaRepository<Creator,String> {
	
	List<Creator> findByEmail(String Name);
	void deleteByEmail(String name);
}
