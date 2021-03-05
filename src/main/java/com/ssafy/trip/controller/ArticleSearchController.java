package com.ssafy.trip.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.trip.model.Article;
import com.ssafy.trip.repository.ArticleRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/search")
public class ArticleSearchController {
	@Autowired
	private ArticleRepository articleRepository;

	@GetMapping(value = { "/{user_num}/article/{keyword}" })
	public List<Article> blogSearch(@PathVariable(value = "user_num") int user_num,
			@PathVariable(value = "keyword") String keyword) {

		List<Article> blogSearch = articleRepository.findByTitleContainingAndNum(keyword, user_num);
		return blogSearch;
	}

//	제목만 검색 리스트
	@GetMapping("/searchArticle/{keyword}")
	public List<Article> responseList(@PathVariable(value = "keyword") String keyword) {

		List<Article> article = articleRepository.findByTitleContaining(keyword);

		return article;
	}

// 닉네임 검색 리스트
	@GetMapping("/searchUserArticle/{selected}")
	public List<Article> searchArticleAuth(@PathVariable(value = "selected") String selected) {
		List<Article> article = articleRepository.findByUserNicknameContaining(selected);

		return article;
	}
}
