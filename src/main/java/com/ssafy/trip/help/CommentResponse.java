package com.ssafy.trip.help;

import java.util.List;

import com.ssafy.trip.model.Article;
import com.ssafy.trip.model.Comment;

public class CommentResponse {
	private Comment comment ;
	private List<Comment> cocomments;
	public CommentResponse(Comment comment, List<Comment> cocomments) {
		this.comment = comment;
		this.cocomments = cocomments;
	}
	public Comment getComment() {
		return comment;
	}
	public void setComment(Comment comment) {
		this.comment = comment;
	}
	public List<Comment> getCocomments() {
		return cocomments;
	}
	public void setCocomments(List<Comment> cocomments) {
		this.cocomments = cocomments;
	}
}
