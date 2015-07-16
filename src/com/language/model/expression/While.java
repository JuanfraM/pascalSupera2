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
	public Resultado ejecutar(Scope variables, Map<String,FuncionDef> Funciones, boolean loop) throws ParsingException {
		boolean bandera;
		Resultado res = this.condicion.ejecutar(variables, Funciones,  loop);	
		/** CONDICION NO NULA*/
		if(res.getTipo() != TipoResultado.NONE) {
			if(res.getTipo() == TipoResultado.BOOL){	
				bandera = (Boolean.parseBoolean(res.getValor()));
				while(bandera){
					Resultado eje = Ejecutar.ejecutar(this.sentencias, variables, Funciones, true);
					//Verifico que no haya llegado a un break return
					if (eje != null){
						if (eje.getTipo() == TipoResultado.BREAK)
							return null;
						if (eje.getTipo() == TipoResultado.RETURN)
							return eje;
					}
					res = this.condicion.ejecutar(variables, Funciones, loop);
					bandera = (Boolean.parseBoolean(res.getValor()));
				}
			}
			else 
				throw new ParsingException("La condicion debe ser booleana ");
		}			
			
		return null;
	}

	@Override
	public String getValor() {
		// TODO Auto-generated method stub
		return null;
	}

}