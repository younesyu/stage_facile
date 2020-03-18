package com.stage_facile.stage_facile.models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "users")
public @Data class User {

    private @Id @GeneratedValue Long id;
	private String password;
    private String email;
    private String firstName;
    private String lastName;
    private Date birthDate;
    private Boolean gender;
    
}
