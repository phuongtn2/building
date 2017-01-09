package com.building.dto;

public class JsonErrorDto {
	private String statusCode;
	private String message;
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	@Override
	public String toString() {
		return "JsonErrorDto [statusCode=" + statusCode + ", message=" + message + "]";
	}


}
