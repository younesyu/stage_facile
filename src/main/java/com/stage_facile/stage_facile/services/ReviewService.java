package com.stage_facile.stage_facile.services;

import java.util.Optional;

import com.stage_facile.stage_facile.models.Comment;
import com.stage_facile.stage_facile.models.Internship;
import com.stage_facile.stage_facile.models.Review;

public interface ReviewService {
	public void addReview(Internship internship, Review review);
	public void addComment(Review review, Comment comment);
	public void addComment(Long reviewId, Long userId, String content);	
	public void deleteComment(Review review, Comment comment);
	public void deleteComment(Long reviewId, Long commentId);
	public Optional<Review> find(Long id);
	public void upvote(Long reviewId, Long voterId);
	public void downvote(Long reviewId, Long voterId);
	public void upvoteComment(Long commentId, Long voterId);
	public void downvoteComment(Long commentId, Long voterId);
}
