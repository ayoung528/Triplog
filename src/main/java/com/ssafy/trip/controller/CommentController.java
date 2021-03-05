package com.ssafy.trip.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.trip.help.CommentResponse;
import com.ssafy.trip.model.Comment;
import com.ssafy.trip.repository.CommentRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/comment")
public class CommentController {
	@Autowired
	private CommentRepository commentRepository;

	@GetMapping("/{articlenum}")
	public List<CommentResponse> getComment(@PathVariable(value = "articlenum") Long num) {

		List<Comment> comments = commentRepository.findByArticlenumAndReplyOrderByCreatedat(num, null);
		List<CommentResponse> commentResponses = new ArrayList<CommentResponse>();
		for (Comment comment : comments) {
			List<Comment> cocomments = commentRepository.findByReplyOrderByCreatedat(comment);

			commentResponses.add(new CommentResponse(comment, cocomments));
		}
		return commentResponses;
	}

	@DeleteMapping("/{num}")
	public ResponseEntity<?> deleteComment(@PathVariable(value = "num") Long num) {
		Comment comment = commentRepository.findByNum(num);
		commentRepository.deleteAllByReply(comment);

		commentRepository.delete(comment);

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@DeleteMapping("/content/{content}")
	public ResponseEntity<?> deleteContentComment(@PathVariable(value = "content") String content) {

		Comment comment = commentRepository.findByContent(content);
		commentRepository.deleteAllByReply(comment);

		commentRepository.delete(comment);

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@PostMapping("/")
	public ResponseEntity<?> createComment(@RequestBody Comment comment) {

		commentRepository.save(comment);

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@PostMapping("/{content}")
	public ResponseEntity<?> createCocomment(@PathVariable(value = "content") String content,
			@RequestBody Comment comment) {

		Comment reply = commentRepository.findByContent(content);
		comment.setReply(reply);
		commentRepository.save(comment);

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@PutMapping("/{num}")
	public ResponseEntity<?> putCocomment(@PathVariable(value = "num") Long num, @RequestBody Comment comment) {

		Comment saveComment = commentRepository.findByNum(num);
		saveComment.setContent(comment.getContent());

		saveComment.setUserimg(comment.getUserimg());

		saveComment.setUsernickname(comment.getUsernickname());

		commentRepository.save(saveComment);

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
