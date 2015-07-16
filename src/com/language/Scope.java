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
	
	public Map<String,Resultado> removeScope(){
		return this.scope.pop();
	}
	
	//SOBRE EL SCOPE LOCAL
	
	public void replaceScopeLocal(String key, Resultado valor){
		this.scope.peek().replace(key, valor);
	}
	
	public void putScopeLocal(String key, Resultado valor){
		this.scope.peek().put(key, valor);
	}
	
	public boolean containsKeyScopeLocal(String Key){
		return this.scope.peek().containsKey(Key);
	}
	
	public Resultado getScopeLocal(String Key){
		return this.scope.peek().get(Key);
	}
	
	//SOBRE CUALQUIER SCOPE
	
	public void replace(String key, Resultado valor){
		for (Map<String,Resultado> m : this.scope){
			if (m.containsKey(key)){
				m.replace(key, valor);
				return;
			}
		}
	}
	
	public boolean containsKey(String Key){
		for (Map<String,Resultado> m : this.scope){
			if (m.containsKey(Key))
				return true;
		}
		return false;
	}
	
	public Resultado get(String Key){
		Resultado ret = null;
		for (Map<String,Resultado> m : this.scope){
			if (m.containsKey(Key))
				return m.get(Key);
		}
		return ret;
	}
}
