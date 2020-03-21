package com.stage_facile.stage_facile.security.payload.request;

import lombok.Data;

public @Data class LoginRequest {
	private String username;
	private String password;
}
