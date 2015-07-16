package com.language.model.expression;

import java.util.ArrayList;
import java.util.Map;

import com.language.Scope;
import com.language.exceptions.ParsingException;

public class Break extends Sentencia {

	private Object value; 
	private TipoExpresion tipo;
	private int linea, col;
	
	public Break (int linea, int col){
		this.value = "break";
		this.tipo = TipoExpresion.BREAK;
		this.linea = linea;
		this.col = col;
	}

	public Resultado ejecutar(Scope variables, Map<String, FuncionDef> Funciones, boolean loop) {
		if (!loop)
			throw new ParsingException("La expresion break solo se puede usar en un loop "+ this.linea + " " + this.col);
		return new Resultado(value.toString(),TipoResultado.BREAK);
	}

	public String getValor() {
		return value.toString();
	}
	
}
