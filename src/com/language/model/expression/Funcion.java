package com.language.model.expression;

import java.util.ArrayList;
import java.util.Map;

import com.language.Scope;
import com.language.exceptions.ParsingException;

public class Funcion extends Sentencia {

	private Object value; 
	private ArrayList<Expresion> arguments;
	private TipoExpresion tipo;
	private int linea, col;

	public Resultado ejecutar(Scope variables, Map<String,FuncionDef> Funciones) {

		if (Funciones.containsKey(this.value)){
			
			FuncionDef definicion = Funciones.get(this.value);
			
			//if () 
			
		}
		else 
			throw new ParsingException("La funcion " + this.value.toString() + "no esta denfida en la posicion" + this.linea + " " + this.col);
		
		
		Resultado ret = null;
		
		return ret;
	}


	public String getValor() {
		return value.toString();
	}

}
