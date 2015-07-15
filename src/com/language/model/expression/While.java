package com.language.model.expression;

import java.util.ArrayList;
import java.util.Map;

import com.language.Ejecutar;
import com.language.Scope;
import com.language.exceptions.ParsingException;

public class While extends Sentencia{
	
	private ArrayList<Sentencia> sentencias;
	private Expresion condicion;
	
	public While( Expresion condicion, ArrayList<Sentencia> sentencias) {
		super();
		this.sentencias = sentencias;
		this.condicion = condicion;
	}
	
	@Override
	public String toString() {
		String res = null;				
		String bloque ="";
		for(Sentencia s : this.sentencias){
			bloque = bloque + s.toString();
		}	
		res = "While\n" +"condicion: " + this.condicion.toString() +"\nBloque: "+ bloque + "\n";			
		return res;
	}

	@Override
	public Resultado ejecutar(Scope variables, Map<String,FuncionDef> Funciones) throws ParsingException {
		boolean bandera;
		Resultado res = this.condicion.ejecutar(variables, Funciones);	
		/** CONDICION NO NULA*/
		if(res.getTipo() != TipoResultado.NONE) {
			Resultado resultado_condicion = this.condicion.ejecutar(variables, Funciones);
			if(resultado_condicion.getTipo() == TipoResultado.BOOL){	
				bandera = (Boolean.parseBoolean(resultado_condicion.getValor()));
				while(bandera){
					Ejecutar.ejecutar(this.sentencias, variables, Funciones);
					resultado_condicion = this.condicion.ejecutar(variables, Funciones);
					bandera = (Boolean.parseBoolean(resultado_condicion.getValor()));
				}
			}
		}			
			
		return null;
	}

	@Override
	public String getValor() {
		// TODO Auto-generated method stub
		return null;
	}

}