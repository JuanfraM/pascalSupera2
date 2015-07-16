package com.language;

import java.util.ArrayList;
import java.util.Map;

import com.language.exceptions.ParsingException;
import com.language.model.expression.*;

public class Ejecutar {
	
	public static Resultado ejecutar(ArrayList<Sentencia> Sentencias, Scope Variables, Map<String,FuncionDef> Funciones, boolean loop){
		
		Resultado ret = null;
		
		for (Sentencia stmt : Sentencias) {

			//Asignacion de variables
			if (stmt instanceof Variable){
				if (Variables.containsKeyScopeLocal(stmt.getValor())){
					Variables.replaceScopeLocal(stmt.getValor(), stmt.ejecutar(Variables, Funciones, loop));
				}
				else {
					Variables.putScopeLocal(stmt.getValor(), stmt.ejecutar(Variables, Funciones, loop));
				}
			}
			
			//Funciones predefinidas
			else if (stmt instanceof FuncionesPredefinidas){
				stmt.ejecutar(Variables, Funciones, loop);
			}
			
			//Funciones del usuario
			else if (stmt instanceof FuncionCall){
				stmt.ejecutar(Variables, Funciones, loop);
			}
			
			//Funciones del usuario
			else if (stmt instanceof FuncionDef){
				if (!Funciones.containsKey(stmt.getValor())){
					Funciones.put(stmt.getValor(), (FuncionDef) stmt);
				}
				else
					throw new ParsingException("Ya existe una funcion con el nombre " + stmt.getValor());
			}		
			else if (stmt instanceof If){
				ret = stmt.ejecutar(Variables, Funciones, loop);
				if (ret != null)
					return ret;
			}
			else if (stmt instanceof While){
				ret = stmt.ejecutar(Variables, Funciones, loop);
				if (ret != null)
					return ret;
			}
			else if (stmt instanceof For){
				ret = stmt.ejecutar(Variables, Funciones, loop);
				if (ret != null)
					return ret;			
			}
			
			//Si leo un return corto con la ejecucion y devuelvo una lista de valores  
			else if (stmt instanceof Return){
				return stmt.ejecutar(Variables, Funciones, loop);
			}
			
			//si leo un continue corto la ejecucion y devuelvo un Resultado continue
			else if (stmt instanceof Continue){
				return stmt.ejecutar(Variables, Funciones, loop);
			}
			
			//si leo un continue corto la ejecucion y devuelvo un Resultado break
			else if (stmt instanceof Break){
				return stmt.ejecutar(Variables, Funciones, loop);
			}
		}
		return ret;
		
	}
}
