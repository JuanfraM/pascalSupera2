package com.language.model.expression;

import java.util.ArrayList;
import java.util.Map;

import com.language.Scope;

public class FuncionDef extends Sentencia {
	
	private Object valor;
	private ArrayList<String> parametros;
	private ArrayList<Sentencia> sentencias;
	
	public FuncionDef (Object valor, ArrayList<String> parametros, ArrayList<Sentencia> sentencias){
		this.valor = valor;
		this.parametros = parametros;
		this.sentencias = sentencias;
	}

	public Resultado ejecutar(Scope variables, Map<String, FuncionDef> Funciones) {
		return null;
	}

	public ArrayList<String> getParametros(){
		return this.parametros;
	}
	
	public ArrayList<Sentencia> getSentencias(){
		return this.sentencias;
	}
	
	public String getValor() {
		return this.valor.toString();
	}
	
	
}
