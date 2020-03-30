package com.stage_facile.stage_facile.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stage_facile.stage_facile.models.Comment;
import com.stage_facile.stage_facile.models.Review;
import com.stage_facile.stage_facile.repositories.ReviewRepository;
import com.stage_facile.stage_facile.services.ReviewService;

/**
 * Contrôleur pour l'API Commentaires
 */
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/reviews")
public class ReviewController {

	@Autowired
	ReviewService reviewService;
	
	@Autowired
	ReviewRepository reviewRepository;
	
	@GetMapping("/all")
	public List<Review> findAll() {
		return (List<Review>) this.reviewRepository.findAll();
	}
	
	@GetMapping("/replies")
	public List<Comment> getReplies() {
		List<Comment> comments = new ArrayList<>();
		this.reviewRepository.findAll().forEach(review -> {
			comments.addAll(review.getReplies());
		});
		return comments;
	}

	@PostMapping("/addReply")
	public void addReply(@RequestBody Map<String, Object> requestBody) {
		Long reviewId = new Long((int) requestBody.get("reviewId"));
		Long userId = new Long((int) requestBody.get("posterId"));
		String content = (String) requestBody.get("content");
		
		this.reviewService.addComment(reviewId, userId, content);
		
	}
	
	@PostMapping("/deleteReply")
	public void deleteReply(@RequestBody Map<String, Object> requestBody) {
		Long reviewId = new Long((int) requestBody.get("reviewId"));
		Long commentId = new Long((int) requestBody.get("commentId"));
		
		this.reviewService.deleteComment(reviewId, commentId);
		
	}
	
	
	@PostMapping("/upvoteReview")
	public void upvoteReview(@RequestBody Map<String, Object> requestBody) {
		Long reviewId = new Long((int) requestBody.get("reviewId"));
		Long voterId = new Long((int) requestBody.get("voterId"));
		
		this.reviewService.upvote(reviewId, voterId);
	}
	
	@PostMapping("/downvoteReview")
	public void downvoteReview(@RequestBody Map<String, Object> requestBody) {
		Long reviewId = new Long((int) requestBody.get("reviewId"));
		Long voterId = new Long((int) requestBody.get("voterId"));
		
		this.reviewService.downvote(reviewId, voterId);
	}
	
	@PostMapping("/upvoteComment")
	public void upvoteComment(@RequestBody Map<String, Object> requestBody) {
		Long commentId = new Long((int) requestBody.get("commentId"));
		Long voterId = new Long((int) requestBody.get("voterId"));
		
		this.reviewService.upvoteComment(commentId, voterId);
	}
	
	@PostMapping("/downvoteComment")
	public void downvoteComment(@RequestBody Map<String, Object> requestBody) {
		Long commentId = new Long((int) requestBody.get("commentId"));
		Long voterId = new Long((int) requestBody.get("voterId"));
		
		this.reviewService.downvoteComment(commentId, voterId);
	}
	
	
}
