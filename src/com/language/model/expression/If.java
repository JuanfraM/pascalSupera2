package com.language.model.expression;

import java.util.ArrayList;

import com.language.Ejecutar;
import com.language.Scope;
import com.language.exceptions.ParsingException;
import com.language.model.expression.Expresion;


public class If extends Sentencia{
	
	private Expresion condicion;
	private ArrayList<Sentencia> sentencias;
	private ArrayList<Sentencia> sentenciasElse;
	private boolean tieneElse;
	
	public If(Expresion condicion, ArrayList<Sentencia> sentencias) {
		super();
		this.condicion = condicion;
		this.sentencias = sentencias;
		this.tieneElse =false;
	}
	
	public If(Expresion condicion, ArrayList<Sentencia> sentencias, ArrayList<Sentencia> sentenciasElse ){
		super();
		this.condicion = condicion;
		this.sentencias = sentencias;
		this.sentenciasElse = sentenciasElse;
		this.tieneElse = true;
	}

	public Expresion getCondicion() {
		return condicion;
	}

	public void setCondicion(Expresion condicion) {
		this.condicion = condicion;
	}

	public ArrayList<Sentencia> getSentencias() {
		return sentencias;
	}

	public void setSentencias(ArrayList<Sentencia> sentencias) {
		this.sentencias = sentencias;
	}

	@Override
	public String toString() {
		String res = null;
		String suite ="";
		for(Sentencia s : this.sentencias){
			suite = suite + s.toString();
		}
		
		res = "If:" + "condicion: " + this.condicion.toString() +  " :suite: "+ suite + "\n";
	
		if(this.tieneElse){
			String suite_else = "";
			for(Sentencia s : this.sentenciasElse)
				suite_else = suite_else + s.toString();
			res = res + "else_Sentencia\n" + suite_else + "\n";
		}
		return res;
	}

	@Override
	public Resultado ejecutar(Scope variables) throws ParsingException {	
		Resultado res = condicion.ejecutar(variables);	
		if(res.getTipo() == TipoResultado.BOOL){			
			ArrayList<Sentencia> suite = null;
			boolean ejecuta =  true;
			
			/** Si la condicion es VERDADERA */
			if(Boolean.parseBoolean(res.getValor())){
				suite = this.sentencias;
				
			/** Si la condicion es FALSA y tiene ELSE */
			}else if(this.tieneElse){
				suite = this.sentenciasElse;
				
			/** Si la condicion es FALSA y NO tiene ELSE*/
			}else{
				ejecuta = false;
			}
			if(ejecuta){
				Ejecutar.ejecutar(suite, variables);
			}
		/** ERROR SEMANTICO */
		}else{											//IF_EXP
			throw new ParsingException(ParsingException.ERROR);
		}
		return null;
	}

	@Override
	public String getValor() {
		// TODO Auto-generated method stub
		return null;
	}

}
