package com.language.model.expression;

import java.util.ArrayList;

public class FuncionDef {
	
	private String valor;
	private ArrayList<String> parametros;
	private ArrayList<Sentencia> sentencias;
	
	public FuncionDef (String valor, ArrayList<String> parametros, ArrayList<Sentencia> sentencias){
		this.valor = valor;
		this.parametros = parametros;
		this.sentencias = sentencias;
	}
	
	
}
