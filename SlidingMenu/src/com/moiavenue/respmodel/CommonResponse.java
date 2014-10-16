package com.moiavenue.respmodel;

import org.codehaus.jackson.annotate.JsonProperty;

public class CommonResponse {

	@JsonProperty("message")
	private String errorMessage;
	@JsonProperty("status")
	private String status;

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
