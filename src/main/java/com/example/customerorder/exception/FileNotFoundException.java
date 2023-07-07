package com.example.customerorder.exception;

public class FileNotFoundException extends RuntimeException {

private String message;

	public FileNotFoundException() {}

	public FileNotFoundException(String msg)
	{
		super(msg);
		this.message = msg;
	}
}
