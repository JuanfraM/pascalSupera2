package com.language.model.expression;

import java.util.Map;

public abstract class Sentencia {

	public abstract Resultado ejecutar(Map<String,Resultado> variables);
	
	public abstract String getValor();
	
}
