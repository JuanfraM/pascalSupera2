package com.language.model.expression;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.language.Scope;
import com.language.exceptions.ParsingException;

public class FuncionCall extends Sentencia {

	private Object value; 
	private ArrayList<Expresion> parametros;
	private int linea, col;
	
	public FuncionCall (Object value, ArrayList<Expresion> parametros, int linea, int col){
		this.value = value;
		this.parametros = parametros;
		this.linea=linea;
		this.col=col;
	}

	public Resultado ejecutar(Scope variables, Map<String,FuncionDef> funciones, boolean loop) {

		Resultado ret = null;
		
		//Valido que la funcion este definida
		if (funciones.containsKey(this.value)){
			
			
			//Obtengo los parametros y las sentencias de la funcion
			FuncionDef definicion = funciones.get(this.value);
			ArrayList<String> parametros = definicion.getParametros();
			ArrayList<Sentencia> sentencias = definicion.getSentencias();
			
			//Valido que me esten llegando la cantidad de parametros correcta
			if (this.parametros.size() == definicion.getParametros().size()){
				
				//Creo el scope de ejecucion de la funcion
				variables.addScope();
				
				int i = 0;
				
				//Almacena los parametros que se pasan por referencia 
				Map<String,String> variablesRef = new HashMap<String,String> ();
				
				//Recorro la lista de parametros y los agrego como variables de scope
				for (Expresion parm : this.parametros){
					
					Resultado r = parm.ejecutar(variables, funciones, false);
					
					//Si es del tipo lista lo agrego a la lista de parametros por referencia
					if (parm.getTipo() == TipoExpresion.ID && r.getTipo() == TipoResultado.LIST){
						variablesRef.put(parametros.get(i), parm.getValor());
					}
					
					//Agrego las variables al scope
					variables.putScopeLocal(parametros.get(i), r);		
				}
				
				
				
			}
			else 
				throw new ParsingException("Cantidad parametros incorrecta para la funcion " + this.value.toString() + " " + this.linea + " " + this.col);
			
		}
		else 
			throw new ParsingException("La funcion " + this.value.toString() + "no esta denfida en la posicion" + this.linea + " " + this.col);
		
		
		
		
		return ret;
	}


	public String getValor() {
		return value.toString();
	}

}
