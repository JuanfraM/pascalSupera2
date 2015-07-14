package com.language.exceptions;

@SuppressWarnings("serial")
public class ParsingException extends RuntimeException {
	
	public static final String ERROR = "ha ocurrido un error";

	public ParsingException(String message, Throwable cause) {
		super(message, cause);
	}

	public ParsingException(String message) {
		super(message);
	}

	public ParsingException(Throwable cause) {
		super(cause);
	}

}
