package com.stage_facile.stage_facile.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "industries")
public @Data class Industry {
	private @Id @GeneratedValue Long id;
	private String name;
	
	public Industry() {}
	public Industry(String name) {
		this.name = name;
	}
}
