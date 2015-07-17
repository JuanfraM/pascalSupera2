package com.language.model.expression;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.language.Scope;
import com.language.exceptions.ParsingException;

public class Variable extends Sentencia {

	private ArrayList <String> ids; 
	private Object id;
	private Expresion indice;
	private Expresion argument;
	private int linea, col;
	boolean coleccion;
	String lugar;
	
	public Variable(ArrayList <String> ids, Expresion argument, int linea, int col, boolean coleccion){
		this.ids = ids;
		this.argument = argument;	
		this.linea = linea;
		this.col = col;
		this.coleccion = coleccion;
		this.lugar = " en la linea "+linea+" y columna "+col;
	}
	
	public Variable(Object id, Expresion indice, Expresion argument, int linea, int col, boolean coleccion){
		this.id = id;
		this.indice = indice;
		this.argument = argument;	
		this.linea = linea;
		this.col = col;
		this.coleccion = coleccion;
		this.lugar = " en la linea "+linea+" y columna "+col;
	}
	
	public Resultado ejecutar (Scope variables, Map<String,FuncionDef> Funciones, boolean loop){
		
		Resultado r = this.argument.ejecutar(variables, Funciones, loop);
		if (!coleccion){
			if (r.getTipo() == TipoResultado.RETURN){
				if (ids.size()==1){
					if (variables.containsKeyScopeLocal(ids.get(0))){
						variables.replaceScopeLocal(ids.get(0), new Resultado (r.getValores(), TipoResultado.TUPLA));
					}
					else {
						variables.putScopeLocal(ids.get(0), new Resultado (r.getValores(), TipoResultado.TUPLA));
					}	
				}
				else if (ids.size()==r.getValores().size()){
					int i = 0;
					for (String id : ids){
						if (variables.containsKeyScopeLocal(id)){
							variables.replaceScopeLocal(id, r.getValores().get(i));
						}
						else {
							variables.putScopeLocal(id,r.getValores().get(i));
						}
						i ++;
					}
				}
				else
					throw new ParsingException(((Integer)this.col).toString(), ((Integer)this.linea).toString(), ParsingException.ASIGNACION_ARGUMENTOS);
			}
			else {
				if (ids.size()>1)
					throw new ParsingException(((Integer)this.col).toString(), ((Integer)this.linea).toString(), ParsingException.ASIGNACION_ARGUMENTOS);
				else{
					if (variables.containsKeyScopeLocal(ids.get(0))){
						variables.replaceScopeLocal(ids.get(0), r);
					}
					else {
						variables.putScopeLocal(ids.get(0),r);
					}
				}
					
			}
		}
		else {
			Resultado var = variables.getScopeLocal(this.id.toString());
			Resultado indice = this.indice.ejecutar(variables, Funciones, loop);
			Resultado arg = this.argument.ejecutar(variables, Funciones, loop);
			if (var.getTipo() == TipoResultado.LIST){
				if (indice.getTipo() != TipoResultado.INTEGER)
					throw new ParsingException(ParsingException.FUNC_PREDEF_SET3+this.lugar);
				var.getValores().set(Integer.parseInt(indice.getValor()), arg);
			}
			else if (var.getTipo() == TipoResultado.DICT){
				if (indice.getTipo() != TipoResultado.STRING)
					throw new ParsingException(ParsingException.FUNC_PREDEF_SET3+this.lugar);
				for (Resultado res : var.getValores() ){
					if (res.getValores().get(0).getValor().equals(indice.getValor())){
						res.getValores().set(1, arg);
						break;
					}
				}
			}
			else{
				throw new ParsingException(ParsingException.FUNC_PREDEF_SET1+this.lugar);
			}
		}
		return new Resultado ();
		
		
	}
	
	public String getValor(){
		return "=";
	}
}
