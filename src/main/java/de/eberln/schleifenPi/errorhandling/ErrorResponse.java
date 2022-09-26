package de.eberln.schleifenPi.errorhandling;

import java.util.Date;

import org.springframework.http.HttpStatus;

public class ErrorResponse {

	private HttpStatus code;
	
	private String message;
	
	private String date;
	
	public ErrorResponse() {
		
	}
	
	public ErrorResponse(HttpStatus code, String message, Date date) {
		this.code = code;
		this.message = message;
		this.date = date.toString();
	}

	public HttpStatus getCode() {
		return code;
	}

	public void setCode(HttpStatus code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
	
	
}
