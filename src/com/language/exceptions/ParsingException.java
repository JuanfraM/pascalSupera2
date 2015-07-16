package com.language.exceptions;

@SuppressWarnings("serial")
public class ParsingException extends RuntimeException {
	
	public static final String ERROR = "ha ocurrido un error";
	public static final String ERROR_FOR1 = "debes ingresar una lista";
	public static final String ERROR_FOR2 = "debes ingresar una lista con mas de un elemento";
	public static final String ERROR_WHILE = "La condicion debe ser booleana";

	public ParsingException(String message, Throwable cause) {
		super(message, cause);
	}

	public ParsingException(String columna, String fila, String error) {
		super("Error en la columna: " + columna + " fila: " + fila + " - " + error);
	}
	
	public ParsingException(String message) {
		super(message);
	}

	public ParsingException(Throwable cause) {
		super(cause);
	}

}
