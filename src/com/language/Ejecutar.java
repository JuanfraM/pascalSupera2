package com.language;

import java.util.ArrayList;
import java.util.Map;

import com.language.exceptions.ParsingException;
import com.language.model.expression.FuncionCall;
import com.language.model.expression.FuncionDef;
import com.language.model.expression.FuncionesPredefinidas;
import com.language.model.expression.Sentencia;
import com.language.model.expression.Variable;

public class Ejecutar {
	
	public static void ejecutar(ArrayList<Sentencia> Sentencias, Scope Variables, Map<String,FuncionDef> Funciones){
		
		for (Sentencia stmt : Sentencias) {

			//Asignacion de variables
			if (stmt instanceof Variable){
				if (Variables.containsKeyScopeLocal(stmt.getValor())){
					Variables.replaceScopeLocal(stmt.getValor(), stmt.ejecutar(Variables, Funciones));
				}
				else {
					Variables.putScopeLocal(stmt.getValor(), stmt.ejecutar(Variables, Funciones));
				}
			}
			
			//Funciones predefinidas
			else if (stmt instanceof FuncionesPredefinidas){
				stmt.ejecutar(Variables, Funciones);
			}
			
			//Funciones del usuario
			else if (stmt instanceof FuncionCall){
				stmt.ejecutar(Variables, Funciones);
			}
			
			//Funciones del usuario
			else if (stmt instanceof FuncionDef){
				if (!Funciones.containsKey(stmt.getValor())){
					Funciones.put(stmt.getValor(), (FuncionDef) stmt);
				}
				else
					throw new ParsingException("Ya existe una funcion con el nombre " + stmt.getValor());
			}		
		}
		
	}
}
