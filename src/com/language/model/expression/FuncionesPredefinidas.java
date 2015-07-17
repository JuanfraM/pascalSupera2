package com.language.model.expression;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

import com.language.Scope;

public class FuncionesPredefinidas extends Sentencia {
	
	private Object value; 
	private ArrayList<Sentencia> arguments;
	private int linea, col;
	
	public FuncionesPredefinidas(Object value, int linea, int col){
		this.value = value;
		this.linea = linea;
		this.col = col;
		this.arguments = null;
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

	public Resultado ejecutar(Scope variables, Map<String,FuncionDef> Funciones, boolean loop) {
		Resultado ret = null;
		
		if (this.value == "print"){
			ret = new Resultado("True", TipoResultado.BOOL);
			System.out.println(this.arguments.get(0).ejecutar(variables, Funciones, loop).toString());
		}
		else if (this.value == "raw_input"){			
			if(this.arguments!=null){
				Expresion e = (Expresion) this.arguments.get(0);
				Resultado aux = e.ejecutar(variables, Funciones, loop);
				System.out.println (aux.getValor().toString());
			}
	        String entrada = "";
	        Scanner entradaEscaner = new Scanner (System.in); //Creación de un objeto Scanner
	        entrada = entradaEscaner.nextLine (); //Invocamos un método sobre un objeto Scanner
			entradaEscaner.close();
			ret = new Resultado(entrada,TipoResultado.STRING);
		}

		return ret;
	}

	public String getValor() {
		return this.value.toString();
	}

}
