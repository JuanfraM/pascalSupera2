package com.language.exceptions;

@SuppressWarnings("serial")
public class ParsingException extends RuntimeException {
	
	public static final String ERROR = "ha ocurrido un error;";
	public static final String ERROR_FOR1 = "debes ingresar una lista;";
	public static final String ERROR_FOR2 = "debes ingresar una lista con mas de un elemento;";
	public static final String ERROR_WHILE = "la condicion debe ser booleana;";
	public static final String FUNC_PREDEF_COUNT = "no hay una funcion predefinida para este tipo de variable;";
	public static final String FUNC_PREDEF_LENGTH = "error al invocar la funcion length;";
	public static final String FUNC_PREDEF_COUNT1 = "error al invocar la funcion count,debe tener un parametro;";
	public static final String FUNC_PREDEF_COUNT2 = "error al invocar la funcion count,debe ingresar un string como parametro;";
	public static final String ASIGNACION_ARGUMENTOS = "la cantidad de argumentos para la asignacion no es correcta";
	public static final String FUNC_PREDEF_FIND = "en la funcion find;";
	public static final String FUNC_PREDEF_FIND1 = "la funcion debe ser para un tipo string;";
	public static final String FUNC_PREDEF_FIND2 = "debe ingresar un string como parametro;";
	public static final String FUNC_PREDEF_FIND3 = "debe ingresar un entero como segundo parametro;";
	public static final String FUNC_PREDEF_SPLIT1 = "debe ingresar un string como parametro;";
	public static final String FUNC_PREDEF_SPLIT2 = "debe ingresar un parametro string;";
	
	
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
