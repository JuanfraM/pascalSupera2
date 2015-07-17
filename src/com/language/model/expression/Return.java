package com.language.model.expression;

import java.util.ArrayList;
import java.util.Map;

import com.language.Scope;

public class Return extends Sentencia {

	private Object value; 
	private ArrayList<Expresion> arguments;
	private int linea, col;
	
	
	public Return (ArrayList<Expresion> arguments, int linea, int col){
		this.value = "return";
		this.arguments = arguments;
		this.linea = linea;
		this.col = col;
	}
	
	public Return (int linea, int col){
		this.value = "return";
		this.arguments = new ArrayList<Expresion>();
		this.linea = linea;
		this.col = col;
	}
	
	public Resultado ejecutar(Scope variables, Map<String, FuncionDef> Funciones, boolean loop) {
		if (this.arguments.isEmpty())
			return new Resultado ("None", TipoResultado.NONE);
		if (this.arguments.size() == 1)
			return this.arguments.get(0).ejecutar(variables, Funciones, loop);
		ArrayList <Resultado> a = new ArrayList <Resultado>();
		for (Expresion e : this.arguments){
			a.add(e.ejecutar(variables, Funciones, loop));
		}
		return new Resultado (a,TipoResultado.RETURN);
	}

	public String getValor() {
		return value.toString();
	}
}
