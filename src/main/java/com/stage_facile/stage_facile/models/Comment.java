package com.stage_facile.stage_facile.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "comments")
public @Data class Comment {
	@Id @GeneratedValue Long id;
	@OneToOne User user;
	@OneToOne Internship internship;
}
