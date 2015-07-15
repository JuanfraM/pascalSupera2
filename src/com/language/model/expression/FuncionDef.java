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

	@Override
	public Resultado ejecutar(Scope variables, Map<String, FuncionDef> Funciones) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getValor() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
