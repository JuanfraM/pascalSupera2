package com.language;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import com.language.model.expression.Resultado;

public class Scope {
	
	private Stack<Map<String,Resultado>> scope;
	
	public Scope(){
		this.scope = new Stack<Map<String,Resultado>>();
		Map<String,Resultado> m = new HashMap<String,Resultado>();
		this.scope.push(m);
	}

	public void addScope(){
		Map<String,Resultado> m = new HashMap<String,Resultado>();
		this.scope.push(m);
	}
	
	public void removeScope(){
		this.scope.pop();
	}
	
	public boolean isVariableInScopeLocal (String Key){
		return this.scope.peek().containsKey(Key);
	}
	
	public void replaceScopeLocal(String key, Resultado valor){
		this.scope.peek().replace(key, valor);
	}
	
	public void addVariableScopeLocal(String key, Resultado valor){
		this.scope.peek().put(key, valor);
	}
	
	public boolean containsKey(String Key){
		for (Map<String,Resultado> m : this.scope){
			if (m.containsKey(Key))
				return true;
		}
		return false;
	}
	
	public Resultado get (String Key){
		Resultado ret = null;
		for (Map<String,Resultado> m : this.scope){
			if (m.containsKey(Key))
				return m.get(Key);
		}
		return ret;
	}
	
}
