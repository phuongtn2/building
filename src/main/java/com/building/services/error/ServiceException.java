package com.building.services.error;

import com.building.dto.JsonErrorDto;

public class ServiceException extends Exception {
	private JsonErrorDto error = null;

	//messageと組み合わせて利用する場合の引数
	private Object[] arguments = new Object[0];

	public ServiceException() {
		super();

	}

	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public ServiceException(String message) {
		super(message);
	}

	// 例外発生時にレスポンス内容を確定させる場合に利用
	public ServiceException(JsonErrorDto error) {
		super();
		this.error = error;
	}

	// 例外発生時にエラー内容と
	public ServiceException(String message, Object ... arguments) {
		super(message);
		this.arguments = arguments;
	}
	// 例外発生時にエラー内容と
	public ServiceException(String message, Throwable cause, Object ... arguments) {
		super(message, cause);
		this.arguments = arguments;
	}

	public JsonErrorDto getError() {
		return error;
	}

	public Object[] getArguments() {
		return arguments;
	}

	public void setArguments(Object ... arguments) {
		this.arguments = arguments;
	}

}
