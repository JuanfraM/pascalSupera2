package com.language;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.language.model.expression.Expresion;
import com.language.model.expression.Resultado;
import com.language.model.expression.Sentencia;
import com.language.model.expression.Variable;
import com.language.parser.ExpressionParser;

public class Main {

	public static void main(String[] args) {
		try {
			
			FileInputStream file = new FileInputStream("E:\\codigo.py");//args[0]);
			ArrayList<Sentencia> Sentencias = ExpressionParser.parse(file);
			
			Scope Variables = new Scope();
			
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
		catch (FileNotFoundException e) {
			System.out.println("No se encontro el archivo :" + args[0]);
		}
		catch (Exception e) {
			System.out.println("Error inesperado: " + e.toString());
		}
	}
	
}
