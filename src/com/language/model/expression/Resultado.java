package com.language.model.expression;

import java.util.ArrayList;

public class Resultado {
	
	private String valor;
	private TipoResultado tipo;
	private ArrayList<Resultado> valores;
	
	public Resultado (String valor, TipoResultado tipo){
		this.valor = valor;
		this.tipo = tipo;
		this.valores = null;
	}
	
	public Resultado (ArrayList<Resultado> valores, TipoResultado tipo){
		this.valores = valores;
		this.tipo = tipo;
		this.valor = null;
	}
	
	public String getValor(){
		return this.valor;
	}
	
	public ArrayList<Resultado> getValores(){
		return this.valores;
	}
	
	public TipoResultado getTipo(){
		return this.tipo;
	}
	
	public boolean equals(Resultado r){
		if(((this.getTipo()==TipoResultado.INTEGER && r.getTipo()==TipoResultado.INTEGER))||
				((this.getTipo()==TipoResultado.STRING && r.getTipo()==TipoResultado.STRING))||
				((this.getTipo()==TipoResultado.LONG && r.getTipo()==TipoResultado.LONG))||
				((this.getTipo()==TipoResultado.BOOL && r.getTipo()==TipoResultado.BOOL))){
			if(this.getValor().equals(r.getValor()))
				return true;
			else
				return false;
		}
		else if(((this.getTipo()==TipoResultado.LIST && r.getTipo()==TipoResultado.LIST))||
				((this.getTipo()==TipoResultado.TUPLA && r.getTipo()==TipoResultado.TUPLA))){
			ArrayList<Resultado> aux = r.getValores();
			int i=0;
			for(Resultado r1: this.valores){
				if(!r1.equals(aux.get(i)))
					return false;
				i++;
			}
			return true;
		}
		else
			return false;
	}
	
	public String toString(){
		if (this.tipo == TipoResultado.LIST){
	        String aux = "";
	        for ( Resultado r : this.valores) {
	            if(r.getTipo() == TipoResultado.STRING)
	                aux = aux + "'" + r.toString() + "'" + ", ";
	            else
	                aux = aux + r.toString() + ", ";
	        }
	        aux = aux.substring(0, aux.length()-2);
	        String respuesta = '[' + aux + ']';
	        
	        return respuesta;
		}
		else if (this.tipo == TipoResultado.TUPLA){
	        String aux = "";
	        if (this.valores.size() == 0)
	        	return "()";
	        for ( Resultado r : this.valores) {
	            if(r.getTipo() == TipoResultado.STRING)
	                aux = aux + "'" + r.toString() + "'" + ", ";
	            else
	                aux = aux + r.toString() + ", ";
	        }
	        aux = aux.substring(0, aux.length()-2);
	        String respuesta = '(' + aux + ')';
	        
	        return respuesta;
		}
		else 
			return this.valor;
	}

}
