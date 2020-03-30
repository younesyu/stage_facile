package com.stage_facile.stage_facile.services.impl;

import java.util.Date;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stage_facile.stage_facile.models.Comment;
import com.stage_facile.stage_facile.models.Internship;
import com.stage_facile.stage_facile.models.Review;
import com.stage_facile.stage_facile.repositories.CommentRepository;
import com.stage_facile.stage_facile.repositories.ReviewRepository;
import com.stage_facile.stage_facile.repositories.UserRepository;
import com.stage_facile.stage_facile.services.ReviewService;

@Service
public class ReviewServiceImpl implements ReviewService {

	@Autowired
	ReviewRepository reviewRepository;
	
	@Autowired
	CommentRepository commentRepository;
	
	@Autowired
	UserRepository userRepository;

	@Override
	public void addReview(Internship internship, Review review) {
		review.setInternship(internship);
		reviewRepository.save(review);
	}

	@Override
	public void addComment(Review review, Comment comment) {
		review.getReplies().add(comment);
		reviewRepository.save(review);
	}

	@Override
	public void addComment(Long reviewId, Long userId, String content) {
		this.reviewRepository.findById(reviewId).ifPresent(review -> {
			this.userRepository.findById(userId).ifPresent(user -> {
				Comment reply = new Comment();
				reply.setWriter(user);
				reply.setContent(content);
				reply.setPostedOn(new Date());
				reply = this.commentRepository.save(reply);
				
				this.addComment(review, reply);
			});
		});
		
	}

	@Override
	public void deleteComment(Review review, Comment comment) {
		Set<Comment> comments = review.getReplies();
		comments.remove(comment);
		review.setReplies(comments);
		reviewRepository.save(review);
		commentRepository.delete(comment);
	}

	@Override
	public void deleteComment(Long reviewId, Long commentId) {
		reviewRepository.findById(reviewId).ifPresent(review -> {
			commentRepository.findById(commentId).ifPresent(comment -> {
				this.deleteComment(review, comment);
			});
		});
		
	}

	@Override
	public Optional<Review> find(Long id) {
		return this.reviewRepository.findById(id);
	}

	@Override
	public void upvote(Long reviewId, Long voterId) {
		this.reviewRepository.findById(reviewId).ifPresent(review -> {
			review.getDownvoters().remove(voterId);
			
			if (!review.getUpvoters().add(voterId)) {
				review.getUpvoters().remove(voterId);
			}
			
			this.reviewRepository.save(review);
		});

	}

	@Override
	public void downvote(Long reviewId, Long voterId) {
		this.reviewRepository.findById(reviewId).ifPresent(review -> {
			review.getUpvoters().remove(voterId);
			
			if (!review.getDownvoters().add(voterId)) {
				review.getDownvoters().remove(voterId);
			}
			
			this.reviewRepository.save(review);
		});
	}

	@Override
	public void upvoteComment(Long commentId, Long voterId) {
		this.commentRepository.findById(commentId).ifPresent(comment -> {
			comment.getDownvoters().remove(voterId);
			
			if(!comment.getUpvoters().add(voterId)) {
				comment.getUpvoters().remove(voterId);
			}
			this.commentRepository.save(comment);
		});
		
	}

	@Override
	public void downvoteComment(Long commentId, Long voterId) {
		this.commentRepository.findById(commentId).ifPresent(comment -> {
			comment.getUpvoters().remove(voterId);
			
			if(!comment.getDownvoters().add(voterId)) {
				comment.getDownvoters().remove(voterId);
			}
			this.commentRepository.save(comment);
		});
		
	}

}
