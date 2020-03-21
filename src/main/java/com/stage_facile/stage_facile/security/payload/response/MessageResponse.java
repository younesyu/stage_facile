package com.stage_facile.stage_facile.security.payload.response;

import lombok.Data;

public @Data class MessageResponse {
	private String message;
	
	public MessageResponse(String message) {
		this.message = message;
	}
}
