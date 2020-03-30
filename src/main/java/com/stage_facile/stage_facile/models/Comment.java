package com.stage_facile.stage_facile.models;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;

@Entity
public @Data class Comment {
	private @Id @GeneratedValue Long id;
	private @ManyToOne User writer;
	private @Column(columnDefinition="TEXT") String content;
	private Date postedOn;
	private @ElementCollection(targetClass = Long.class) Set<Long> upvoters;
	private @ElementCollection(targetClass = Long.class) Set<Long> downvoters;
	
}
