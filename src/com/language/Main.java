package com.language;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import com.language.model.expression.Sentencia;
import com.language.parser.ExpressionParser;

public class Main {

	public static void main(String[] args) {
		try {
			
			FileInputStream file = new FileInputStream("E:\\codigo.py");//args[0]);
			ArrayList<Sentencia> Sentencias = ExpressionParser.parse(file);
			
			Scope Variables = new Scope();
			
			Ejecutar.ejecutar(Sentencias, Variables);
			
		} 
		catch (FileNotFoundException e) {
			System.out.println("No se encontro el archivo :" + args[0]);
		}
		catch (Exception e) {
			System.out.println("Error inesperado: " + e.toString());
		}
	}
	
}
