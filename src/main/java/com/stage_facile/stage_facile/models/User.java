package com.stage_facile.stage_facile.models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;

import lombok.Data;

@Entity
@Table(name = "users", 
		uniqueConstraints = { @UniqueConstraint(columnNames = "email") })
public @Data class User {

	private @Id @GeneratedValue Long id;
	private @Email String email;
	private String password;
	private String firstName;
	private String lastName;
	private Date birthDate;
	private Boolean gender;
	private @ManyToOne Role role;
	private Boolean validated;

	public User() {}
	
	public User(String email, String password, String firstName, String lastName, 
			Date birthDate, Boolean gender) {
		this.email = email;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthDate = birthDate;
		this.gender = gender;
	}
	
	public void validateModerator(User user) {
		if(this.role.getName().equals(ERole.ROLE_ADMIN)) {
			user.setValidated(true);
		}
	}
}
