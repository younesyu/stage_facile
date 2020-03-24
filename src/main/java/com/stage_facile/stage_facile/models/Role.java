package com.stage_facile.stage_facile.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Data;

/**
 * Entité rôle (d'un utilisateur).
 * Voir l'énumération ERole pour obtenir les rôles possibles.
 */
@Entity
@Table(name = "roles", 
	uniqueConstraints = { @UniqueConstraint(columnNames = "name") })
public @Data class Role {
	private @Id @GeneratedValue Integer id;
	private @Enumerated @Column(length = 20) ERole name;
	
	public Role() { }

	public Role(ERole name) {
		this.name = name;
	}
}
