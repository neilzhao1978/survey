package com.neil.survey.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.neil.survey.module.User;


public interface UserRepository extends JpaRepository<User,String> {
	List<User> findByEmailAndPsw(String email,String psw);
}
