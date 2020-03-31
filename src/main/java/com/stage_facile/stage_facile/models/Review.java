package com.stage_facile.stage_facile.models;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class Review {
	private @Id Long id;
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 
    private @OneToOne(fetch = FetchType.LAZY) @MapsId Internship internship;
	
	// Détails du retour d'expérience
	private @Column(columnDefinition="TEXT") String content;
	
	// Notes et moyenne
	private EReview teamCommunication;
	private EReview easeOfIntegration;
	private EReview mentorship;
	private EReview subject;
	private EReview workload;
	private EReview wouldRecommend;
	private float averageReview;
	
	// Réponses et votes
	private @OneToMany Set<Comment> replies;
	private @ElementCollection(targetClass = Long.class) Set<Long> upvoters;
	private @ElementCollection(targetClass = Long.class) Set<Long> downvoters;
	
	public Review() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Internship getInternship() {
		return internship;
	}

	public void setInternship(Internship internship) {
		this.internship = internship;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public EReview getTeamCommunication() {
		return teamCommunication;
	}

	public void setTeamCommunication(EReview teamCommunication) {
		this.teamCommunication = teamCommunication;
	}

	public EReview getEaseOfIntegration() {
		return easeOfIntegration;
	}

	public void setEaseOfIntegration(EReview easeOfIntegration) {
		this.easeOfIntegration = easeOfIntegration;
	}

	public EReview getMentorship() {
		return mentorship;
	}

	public void setMentorship(EReview mentorship) {
		this.mentorship = mentorship;
	}

	public EReview getSubject() {
		return subject;
	}

	public void setSubject(EReview subject) {
		this.subject = subject;
	}

	public EReview getWorkload() {
		return workload;
	}

	public void setWorkload(EReview workload) {
		this.workload = workload;
	}

	public EReview getWouldRecommend() {
		return wouldRecommend;
	}

	public void setWouldRecommend(EReview wouldRecommend) {
		this.wouldRecommend = wouldRecommend;
	}

	public float getAverageReview() {
		return averageReview;
	}

	public void setAverageReview(float averageReview) {
		this.averageReview = averageReview;
	}

	public Set<Comment> getReplies() {
		return replies;
	}

	public void setReplies(Set<Comment> replies) {
		this.replies = replies;
	}

	public Set<Long> getUpvoters() {
		return upvoters;
	}

	public void setUpvoters(Set<Long> upvoters) {
		this.upvoters = upvoters;
	}

	public Set<Long> getDownvoters() {
		return downvoters;
	}

	public void setDownvoters(Set<Long> downvoters) {
		this.downvoters = downvoters;
	}
	
}
