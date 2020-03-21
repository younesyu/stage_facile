package com.stage_facile.stage_facile.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "roles")
public @Data class Role {
	private @Id @GeneratedValue Integer id;
	private @Enumerated @Column(length = 20) ERole name;
	
	public Role() { }

	public Role(ERole name) {
		this.name = name;
	}
}
