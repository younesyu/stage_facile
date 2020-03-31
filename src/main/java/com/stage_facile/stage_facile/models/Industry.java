package com.stage_facile.stage_facile.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * Entité secteur d'activité.
 */
@Entity
@Table(name = "industries")
public class Industry {
	private @Id @GeneratedValue Long id;
	private String name;
	
	public Industry() {}
	public Industry(String name) {
		this.name = name;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
