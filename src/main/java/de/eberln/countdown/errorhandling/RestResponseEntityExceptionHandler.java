package de.eberln.countdown.errorhandling;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler({NullPointerException.class})
	public ResponseEntity<ErrorResponse> handleNullPointerException(NullPointerException e, WebRequest webRequest) {
		
		System.err.println(e.getMessage());
		
		ErrorResponse responseBody = new ErrorResponse(HttpStatus.NOT_FOUND, e.getMessage(), new Date());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
	}
	
	
	@ExceptionHandler({IllegalArgumentException.class})
	public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException e, WebRequest webRequest) {
		
		System.err.println(e.getMessage());
		
		ErrorResponse responseBody = new ErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage(), new Date());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
	}
	
	
	@ExceptionHandler({FileNotFoundException.class})
	public ResponseEntity<ErrorResponse> handleFileNotFoundException(FileNotFoundException e, WebRequest webRequest) {
		
		System.err.println(e.getMessage());
		
		ErrorResponse responseBody = new ErrorResponse(HttpStatus.NOT_FOUND, e.getMessage(), new Date());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
	}

	
	@ExceptionHandler({IOException.class})
	public ResponseEntity<ErrorResponse> handleIOException(IOException e, WebRequest webRequest) {
		
		System.err.println(e.getMessage());
		
		ErrorResponse responseBody = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), new Date());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
	}
	
	
	@ExceptionHandler({ParseException.class})
	public ResponseEntity<ErrorResponse> handleParseException(ParseException e, WebRequest webRequest) {
		
		System.err.println(e.getMessage());
		
		ErrorResponse responseBody = new ErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage(), new Date());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
	}
	
	
}
