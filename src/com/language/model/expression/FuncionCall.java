package com.language.model.expression;

import java.util.ArrayList;
import java.util.Map;

import com.language.Scope;
import com.language.exceptions.ParsingException;

public class FuncionCall extends Sentencia {

	private Object value; 
	private ArrayList<Expresion> parametros;
	private int linea, col;
	
	public FuncionCall (Object value, ArrayList<Expresion> parametros, int linea, int col){
		this.value = value;
		this.parametros = parametros;
		this.linea=linea;
		this.col=col;
	}

	public Resultado ejecutar(Scope variables, Map<String,FuncionDef> Funciones) {

		if (Funciones.containsKey(this.value)){
			
			FuncionDef definicion = Funciones.get(this.value);
			
			if (this.parametros.size() == definicion.getParametros().size()){
				
			}
			else 
				throw new ParsingException("La funcion " + this.value.toString() + "requiere mas parametros" + this.linea + " " + this.col);
			
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
