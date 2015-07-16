package com.language.model.expression;

import java.util.ArrayList;
import java.util.Map;

import com.language.Scope;

public class Return extends Sentencia {

	private Object value; 
	private ArrayList<Expresion> arguments;
	private int linea, col;
	
	
	public Return (Object value, ArrayList<Expresion> arguments, int linea, int col){
		this.value = value;
		this.arguments = arguments;
		this.linea = linea;
		this.col = col;
	}
	
	public Return (Object value, int linea, int col){
		this.value = value;
		this.arguments = new ArrayList<Expresion>();
		this.linea = linea;
		this.col = col;
	}
	
	public Resultado ejecutar(Scope variables, Map<String, FuncionDef> Funciones, boolean loop) {
		if (this.arguments.isEmpty())
			return new Resultado ("None", TipoResultado.NONE);
		ArrayList <Resultado> a = new ArrayList <Resultado>();
		for (Expresion e : this.arguments){
			a.add(e.ejecutar(variables, Funciones, loop));
		}
		return new Resultado (a,TipoResultado.LIST);
	}

	public String getValor() {
		return value.toString();
	}
}
