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

import lombok.Data;

@Entity
public @Data class Review {
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
}
