package com.stage_facile.stage_facile.security.payload.response;

import lombok.Getter;
import lombok.Setter;

public class JwtResponse {
	private String token; 
	private String type = "Bearer";
	@Getter @Setter
	private Long id;
	@Getter @Setter
	private String username;
	@Getter @Setter
	private String role;
	
	public JwtResponse(String accessToken, Long id, String username, String role) {
		this.token = accessToken;
		this.id = id;
		this.username = username;
		this.role = role;
	}
	
	public String getAccessToken() {
		return token;
	}

	public void setAccessToken(String accessToken) {
		this.token = accessToken;
	}

	public String getTokenType() {
		return type;
	}

	public void setTokenType(String tokenType) {
		this.type = tokenType;
	}
}
