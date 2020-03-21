package com.stage_facile.stage_facile.security.payload.request;

import java.util.Date;

import lombok.Data;

public @Data class SignupRequest {
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private Date birthDate;
	private Boolean gender;
	private String role;
}
