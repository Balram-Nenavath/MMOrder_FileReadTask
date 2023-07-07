package com.example.customerorder.exception;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class DataValidationException  extends RuntimeException {

private String message;
	
	public DataValidationException () {}
	
	public DataValidationException (String msg)
	{
		super(msg);
		this.message = msg;
	}
}
