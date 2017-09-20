package com.neil.survey.module;


import org.springframework.data.jpa.repository.JpaRepository;


public interface UserListRepository extends JpaRepository<User,String> {
}
