package com.ftn.upp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ftn.upp.model.Article;

public interface ArticleRepository extends JpaRepository<Article, Long>{

	Article findOneByTitle(String articleTitle);
	

}