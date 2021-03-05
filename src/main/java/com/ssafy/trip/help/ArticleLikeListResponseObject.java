package com.ssafy.trip.help;


import com.ssafy.trip.model.Article;
import com.ssafy.trip.model.MemberUser;

public class ArticleLikeListResponseObject {
	private Article article;
	private MemberUser writer;
	
	public ArticleLikeListResponseObject(Article article, MemberUser writer) {

		this.article = article;
		this.writer = writer;
		
	}

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	public MemberUser getWriter() {
		return writer;
	}

	public void setWriter(MemberUser writer) {
		this.writer = writer;
	}

	@Override
	public String toString() {
		return "ArticleLikeListResponse [article=" + article + ", writer=" + writer + "]";
	}	
}
