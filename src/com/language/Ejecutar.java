package com.language;

import java.util.ArrayList;
import java.util.Map;

import com.language.model.expression.FuncionCall;
import com.language.model.expression.FuncionDef;
import com.language.model.expression.FuncionesPredefinidas;
import com.language.model.expression.If;
import com.language.model.expression.Sentencia;
import com.language.model.expression.Variable;
import com.language.model.expression.While;

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
			else if (stmt instanceof If){
				stmt.ejecutar(Variables, Funciones);
			}
			else if (stmt instanceof While){
				stmt.ejecutar(Variables, Funciones);
			}
		}
		
	}
}
