package com.neil.survey.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.neil.survey.module.Book;


public interface ReadingListRepository extends JpaRepository<Book,Long> {
	List<Book> findById(Long id);
}
