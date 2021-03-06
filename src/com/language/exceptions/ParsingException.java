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
	public static final String FUNC_PREDEF_POP1 = "la funcion debe aplicarse a un diccionario o una lista";
	public static final String FUNC_PREDEF_POP2 = "debe ingresar un parametro entero";
	public static final String FUNC_PREDEF_TYPE = "error en la funcion type, no es un tipo valido";
	public static final String FUNC_PREDEF_STR = "error en la conversion de tipo a string";
	public static final String FUNC_PREDEF_INT1 = "error en la conversion de tipo a int, debe ingresar un strig con valor entero";
	public static final String FUNC_PREDEF_INT2 = "error en la conversion de tipo a int";
	public static final String FUNC_PREDEF_GET1 = "la funcion debe aplicarse a un diccionario, una lista o una tupla";
	public static final String FUNC_PREDEF_GET2 = "los argumentos deben ser de tipo int";
	public static final String FUNC_PREDEF_GET3 = "el valor de fin debe ser mayor al valor de inicio";
	public static final String FUNC_PREDEF_GET4 = "fuera de rango";
	public static final String FUNC_PREDEF_GET5 = "cantidad de argumentos invalida";
	public static final String FUNC_PREDEF_GET6 = "los argumentos deben ser de tipo string";
	public static final String FUNC_PREDEF_SET1 = "la funcion debe aplicarse a un diccionario o una lista";
	public static final String FUNC_PREDEF_SET2 = "cantidad de argumentos invalida";
	public static final String FUNC_PREDEF_SET3 = "tipo de argumento invalido";
	public static final String FUNC_PREDEF_HAS_KEY1 = "la funcion debe aplicarse a un diccionario";
	public static final String FUNC_PREDEF_HAS_KEY2 = "cantidad de parametros incorrecta";
	public static final String FUNC_PREDEF_HAS_KEY3 = "el argumento debe ser de tipo string";
	public static final String FUNC_PREDEF_LIST = "la funcion debe aplicarse a una lista";
	
	
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
