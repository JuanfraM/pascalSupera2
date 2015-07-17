package com.language.model.expression;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.language.Ejecutar;
import com.language.Scope;
import com.language.exceptions.ParsingException;

public class FuncionCall extends Expresion {

	private Object value; 
	private ArrayList<Expresion> parametros;
	private int linea, col;
	
	public FuncionCall(Object value, ArrayList<Expresion> arguments,  int linea, int col) {
		super(value, arguments, TipoExpresion.FUNCION, col, col); 
		this.value = value;
		this.parametros = arguments;
		this.linea=linea;
		this.col=col;
	}

	public Resultado ejecutar(Scope variables, Map<String,FuncionDef> funciones, boolean loop) {

		Resultado ret = null;
		
		//Valido que la funcion este definida
		if (funciones.containsKey(this.value)){
			
			
			//Obtengo los parametros y las sentencias de la funcion
			FuncionDef definicion = funciones.get(this.value);
			ArrayList<String> parametrosDef = definicion.getParametros();
			ArrayList<Sentencia> sentencias = definicion.getSentencias();
			
			//Valido que me esten llegando la cantidad de parametros correcta
			if (this.parametros.size() == parametrosDef.size()){
				
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
						variablesRef.put(parametrosDef.get(i), parm.getValor());
					}
					
					//Agrego las variables al scope
					variables.putScopeLocal(parametrosDef.get(i), r);
					i++;
				}
				
				ret = Ejecutar.ejecutar(sentencias, variables, funciones, false);
				
				Map<String,Resultado> scopeViejo = variables.removeScope();
				for (String s : variablesRef.keySet()){
					Resultado valorRef = scopeViejo.get(s);
					String variableRef = variablesRef.get(s);
					if (variables.containsKeyScopeLocal(variableRef))
						variables.replaceScopeLocal(variableRef, valorRef);
				}
				
				return ret;
				
				
			}
			else 
				throw new ParsingException("Cantidad parametros incorrecta para la funcion " + this.value.toString() + " " + this.linea + " " + this.col);
			
		}
		else 
			throw new ParsingException("La funcion " + this.value.toString() + "no esta denfida en la posicion" + this.linea + " " + this.col);

	}


	public String getValor() {
		return value.toString();
	}

}
