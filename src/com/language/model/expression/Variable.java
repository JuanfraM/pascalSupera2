package com.language.model.expression;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.language.Scope;

public class Variable extends Sentencia {

	private Object id; 
	private Expresion argument;
	
	public Variable(Object id, Expresion argument){
		this.id = id;
		this.argument = argument;	
	}
	
	public Resultado ejecutar (Scope variables){
		return this.argument.ejecutar(variables);
	}
	
	public String getValor(){
		return id.toString();
	}
}
