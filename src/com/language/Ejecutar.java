package com.language;

import java.util.ArrayList;

import com.language.model.expression.Sentencia;
import com.language.model.expression.Variable;

public class Ejecutar {
	
	public static void ejecutar(ArrayList<Sentencia> Sentencias, Scope Variables){
		for (Sentencia stmt : Sentencias) {
			if (stmt instanceof Variable){
				if (Variables.isVariableInScopeLocal(stmt.getValor())){
					Variables.replaceScopeLocal(stmt.getValor(), stmt.ejecutar(Variables));
					System.out.println("Asigno variable --- " + stmt.getValor().toString() + " = " + Variables.get(stmt.getValor().toString()));
				}
				else {
					Variables.addVariableScopeLocal(stmt.getValor(), stmt.ejecutar(Variables));
					System.out.println("Declaro variable --- " + stmt.getValor().toString() + " = " + Variables.get(stmt.getValor().toString()));
				}
			}
		}
	}
}
