package com.language.model.expression;

import java.util.ArrayList;
import java.util.Map;

import com.language.Ejecutar;
import com.language.Scope;
import com.language.exceptions.ParsingException;

public class For extends Sentencia{
	
	private Object iterador; 
	private ArrayList<Expresion> lista;
	private ArrayList<Sentencia> sentencias;
	private int linea, columna;
	private String lugar;
	
	public For(Object iterador, ArrayList<Expresion> lista,ArrayList<Sentencia> sentencias, int linea, int col) {
		super();
		this.iterador = iterador;
		this.lista = lista;
		this.sentencias = sentencias;
		this.linea = linea;
		this.columna = col;
		this.lugar = " en la linea "+linea+" y columna "+col;
		
	}
	
	@Override
	public String toString() {
		String res = null;				
		String bloque ="";
		for(Sentencia s : this.sentencias){
			bloque = bloque + s.toString();
		}	
		res = "For\n" + "variable: " + this.iterador.toString()
			 +"lista: " + this.lista.toString()
			 +"\nSuite: "+ bloque + "\n";			
		return res;
	}

	@Override
	public Resultado ejecutar(Scope variables, Map<String,FuncionDef> funciones, boolean loop) throws ParsingException {
		Resultado it = null;
		ArrayList<Resultado> lista_iterar= null;
		
		if (!variables.containsKeyScopeLocal((this.iterador.toString()))){
			variables.putScopeLocal(this.iterador.toString(), it);
		}
		
		Expresion e = this.lista.get(0);
		if(e.getTipo()==TipoExpresion.LIST){
			lista_iterar = new ArrayList<Resultado>();
			this.lista = e.getArguments();
			for(Expresion ex: this.lista){
				it = ex.ejecutar(variables, funciones,loop);
				lista_iterar.add(it);
			}
		}
		else if(e.getTipo()== TipoExpresion.ID) {
			Resultado variable = variables.get(e.getValor());
			if (variable.getTipo()!= TipoResultado.LIST)
				throw new ParsingException(ParsingException.ERROR_FOR1+this.lugar);
			lista_iterar= variable.getValores();
		}
		
		for (int i = 0; i< lista_iterar.size();i++){
			 variables.replaceScopeLocal(this.iterador.toString(), lista_iterar.get(i));
			 Resultado eje = Ejecutar.ejecutar(this.sentencias, variables, funciones,loop);
			//Verifico que no haya llegado a un break return
				if (eje != null){
					if (eje.getTipo() == TipoResultado.BREAK)
						return null;
					if (eje.getTipo() == TipoResultado.RETURN)
						return eje;					
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
