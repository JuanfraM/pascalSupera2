package com.language.model.expression;

import java.util.Map;

import com.language.Scope;

public abstract class Sentencia {

	public abstract Resultado ejecutar(Scope variables, Map<String,FuncionDef> Funciones);
	
	public abstract String getValor();
	
}
