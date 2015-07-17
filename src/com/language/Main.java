package com.language;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.language.exceptions.ParsingException;
import com.language.model.expression.FuncionCall;
import com.language.model.expression.FuncionDef;
import com.language.model.expression.Resultado;
import com.language.model.expression.Sentencia;
import com.language.parser.ExpressionParser;

public class Main {

	public static void main(String[] args) {
		try {
			
			FileInputStream file = new FileInputStream("/home/juanfra/workspace/pascalSupera2/test");//args[0]);
			ArrayList<Sentencia> Sentencias = ExpressionParser.parse(file);
			if (Sentencias.isEmpty())
				return;
				
			Scope Variables = new Scope();
			Map<String,FuncionDef> Funciones = new HashMap<String,FuncionDef>();
			
			Ejecutar.ejecutar(Sentencias, Variables, Funciones, false);
			
		} 
		catch (FileNotFoundException e) {
			System.out.println("No se encontro el archivo :" + args[0]);
		}
		catch (Exception e) {
			System.out.println(e.toString());
		}
	}
	
}
