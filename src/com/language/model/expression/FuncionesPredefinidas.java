package com.language.model.expression;

import java.util.ArrayList;
import java.util.Map;

import com.language.Scope;

public class FuncionesPredefinidas extends Sentencia {
	
	private Object value; 
	private ArrayList<Sentencia> arguments;
	private int linea, col;
	
	public FuncionesPredefinidas(Object value, int linea, int col){
		this.value = value;
		this.linea = linea;
		this.col = col;
	}
	
	public FuncionesPredefinidas(Object value, Sentencia arg, int linea, int col){
		this.value = value;
		this.linea = linea;
		this.col = col;
		this.arguments = new ArrayList<Sentencia>();
		this.arguments.add(arg);
	}
	
	public FuncionesPredefinidas(Object value, Sentencia arg1, Sentencia arg2, int linea, int col){
		this.value = value;
		this.linea = linea;
		this.col = col;
		this.arguments = new ArrayList<Sentencia>();
		this.arguments.add(arg1);
		this.arguments.add(arg2);
	}

	public Resultado ejecutar(Scope variables, Map<String,FuncionDef> Funciones) {
		Resultado ret = null;
		
		if (this.value == "print"){
			ret = new Resultado("True", TipoResultado.BOOL);
			System.out.println(this.arguments.get(0).ejecutar(variables, Funciones).toString());
		}

		return ret;
	}

	public String getValor() {
		return this.value.toString();
	}

}
