package com.language.model.expression;

import java.util.ArrayList;
import java.util.Map;

import com.language.Ejecutar;
import com.language.Scope;
import com.language.exceptions.ParsingException;
import com.language.model.expression.Expresion;


public class If extends Sentencia{
	
	private Expresion condicion;
	private ArrayList<Sentencia> sentencias;
	private ArrayList<Sentencia> sentenciasElse;
	private boolean hayElse;
	private int linea, columna;
	private String lugar;
	
	public If(Expresion condicion, ArrayList<Sentencia> sentencias,int columna,int linea) {
		super();
		this.condicion = condicion;
		this.sentencias = sentencias;
		this.hayElse =false;
		this.columna = columna;
		this.lugar = " en la linea "+linea+" y columna "+columna;
	}
	
	public If(Expresion condicion, ArrayList<Sentencia> sentencias, ArrayList<Sentencia> sentenciasElse,int columna,int linea ){
		super();
		this.condicion = condicion;
		this.sentencias = sentencias;
		this.sentenciasElse = sentenciasElse;
		this.hayElse = true;
		this.columna = columna;
		this.lugar = " en la linea "+linea+" y columna "+columna;
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
	
		if(this.hayElse){
			String suite_else = "";
			for(Sentencia s : this.sentenciasElse)
				suite_else = suite_else + s.toString();
			res = res + "else_Sentencia\n" + suite_else + "\n";
		}
		return res;
	}

	@Override
	public Resultado ejecutar(Scope variables, Map<String,FuncionDef> Funciones, boolean loop) throws ParsingException {	
		Resultado res = condicion.ejecutar(variables, Funciones, loop);	
		if(res.getTipo() == TipoResultado.BOOL){			
			ArrayList<Sentencia> suite = null;
			boolean ejecuta =  true;
			
			/** Si la condicion es VERDADERA */
			if(Boolean.parseBoolean(res.getValor())){
				suite = this.sentencias;
				
			/** Si la condicion es FALSA y hay ELSE */
			}else if(this.hayElse){
				suite = this.sentenciasElse;
				
			/** Si la condicion es FALSA y NO hay ELSE*/
			}else{
				ejecuta = false;
			}
			if(ejecuta){
				Resultado eje = Ejecutar.ejecutar(suite, variables, Funciones, loop);
				if (eje != null){
					return eje;	
				}
			}
		/** ERROR SEMANTICO */
		}else{											//IF_EXP
			throw new ParsingException(ParsingException.ERROR+this.lugar);
		}
		return null;
	}

	@Override
	public String getValor() {
		// TODO Auto-generated method stub
		return null;
	}

}
