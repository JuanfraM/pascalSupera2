package com.language.model.expression;

import java.util.ArrayList;

import com.language.Scope;

public class Funcion extends Sentencia {

	private Object value; 
	private ArrayList<Expresion> arguments;
	private TipoExpresion tipo;
	private int linea, col;

	public Resultado ejecutar(Scope variables) {

		return null;
	}


	public String getValor() {

		return null;
	}

}
