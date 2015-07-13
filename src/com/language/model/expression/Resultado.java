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
		else 
			return this.valor;
	}

}
