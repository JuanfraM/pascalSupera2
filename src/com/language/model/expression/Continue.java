package com.language.model.expression;

import java.util.Map;

import com.language.Scope;

public class Continue extends Sentencia {
	
	private Object value; 
	private TipoExpresion tipo;
	private int linea, col;
	
	public Continue (int linea, int col){
		this.value = "Continue";
		this.tipo = TipoExpresion.CONTINUE;
		this.linea = linea;
		this.col = col;
	}

	public Resultado ejecutar(Scope variables, Map<String, FuncionDef> Funciones, boolean loop) {
		return new Resultado(value.toString(),TipoResultado.CONTINUE);
	}

	public String getValor() {
		return value.toString();
	}

}
