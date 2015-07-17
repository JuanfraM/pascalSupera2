package com.language.model.expression;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.language.Scope;
import com.language.exceptions.ParsingException;

public class Variable extends Sentencia {

	private ArrayList <String> ids; 
	private Expresion argument;
	private int linea, col;
	
	public Variable(ArrayList <String> ids, Expresion argument, int linea, int col){
		this.ids = ids;
		this.argument = argument;	
		this.linea = linea;
		this.col = col;
	}
	
	public Resultado ejecutar (Scope variables, Map<String,FuncionDef> Funciones, boolean loop){
		
		Resultado r = this.argument.ejecutar(variables, Funciones, loop);
		
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
		return new Resultado ();
		
		
	}
	
	public String getValor(){
		return "=";
	}
}
