package com.language.model.expression;

import java.util.ArrayList;
import java.util.Map;

import com.language.Scope;

public class Funcion extends Sentencia {

	private Object value; 
	private ArrayList<Expresion> arguments;
	private TipoExpresion tipo;
	private int linea, col;

	public Resultado ejecutar(Scope variables, Map<String,FuncionDef> Funciones) {

		//if (Funciones.containsKey(this.value))
		
		
		Resultado ret = null;
		
		return ret;
	}


	public String getValor() {
		return value.toString();
	}

}
