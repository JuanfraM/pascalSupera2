package com.language.parser;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import java_cup.runtime.Symbol;

import com.language.exceptions.ParsingException;
import com.language.model.expression.Sentencia;

public class ExpressionParser {

	public static ArrayList<Sentencia> parse(FileInputStream expText) {
		
		Parser parser = new Parser(new Scanner(expText));
		try {
			Symbol topsym = parser.parse();

			ArrayList<Sentencia> exp = (ArrayList<Sentencia>) topsym.value;
			return exp;

		} catch (Throwable ex) {
			throw new ParsingException("Error parsing source: " + ex.getMessage());
		}

	}
}
