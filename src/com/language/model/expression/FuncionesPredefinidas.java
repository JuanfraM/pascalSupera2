package com.language.model.expression;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.language.Scope;
import com.language.exceptions.ParsingException;

public class FuncionesPredefinidas extends Expresion {
	
	private Object value; 
	private Object id_variable;
	private ArrayList<Sentencia> arguments;
	private int linea, col;
	public String lugar;
	
	public FuncionesPredefinidas(Object value, int linea, int columna){
		super(value, TipoExpresion.FUNCTION_PREDEF, linea, columna);
		this.value = value;
		this.linea = linea;
		this.col = columna;
		this.arguments = null;
		this.lugar = " en la linea "+linea+" y columna "+columna;
	}
	
	public FuncionesPredefinidas(Object value,Object id_variable ,Sentencia arg, int linea, int col){
		super(value, TipoExpresion.FUNCTION_PREDEF, linea, col);
		this.value = value;
		this.linea = linea;
		this.id_variable = id_variable;
		this.col = col;
		this.arguments = new ArrayList<Sentencia>();
		this.arguments.add(arg);
		this.lugar = " en la linea "+linea+" y columna "+col;
	}
	
	public FuncionesPredefinidas(Object value, Sentencia arg, int linea, int col){
		super(value, TipoExpresion.FUNCTION_PREDEF, linea, col);
		this.value = value;
		this.linea = linea;
		this.col = col;
		this.arguments = new ArrayList<Sentencia>();
		this.arguments.add(arg);
		this.lugar = " en la linea "+linea+" y columna "+col;
	}
	
	public FuncionesPredefinidas(Object value, Sentencia arg1, Sentencia arg2, int linea, int col){
		super(value, TipoExpresion.FUNCTION_PREDEF, linea, col);
		this.value = value;
		this.linea = linea;
		this.col = col;
		this.arguments = new ArrayList<Sentencia>();
		this.arguments.add(arg1);
		this.arguments.add(arg2);
		this.lugar = " en la linea "+linea+" y columna "+col;
	}

	public Resultado ejecutar(Scope variables, Map<String,FuncionDef> Funciones, boolean loop)throws ParsingException {
		Resultado ret = null;
		
		if (this.value == "print"){
			ret = new Resultado("True", TipoResultado.BOOL);
			System.out.println(this.arguments.get(0).ejecutar(variables, Funciones, loop).toString());
		}
		else if (this.value == "raw_input"){			
			if(this.arguments!=null){
				Expresion e = (Expresion) this.arguments.get(0);
				Resultado aux = e.ejecutar(variables, Funciones, loop);
				System.out.println (aux.getValor().toString());
			}
	        String entrada = "";
	        Scanner entradaEscaner = new Scanner (System.in); //Creación de un objeto Scanner
	        entrada = entradaEscaner.nextLine (); //Invocamos un método sobre un objeto Scanner
			entradaEscaner.close();
			ret = new Resultado(entrada,TipoResultado.STRING);
		}
		else if(this.value=="length"){
			Resultado variable = variables.get(this.id_variable.toString());
			if(variable.getTipo()==TipoResultado.STRING){	
				String largo = Integer.toString(variable.getValor().length());				
				ret = new Resultado(largo,TipoResultado.INTEGER);
			}
			else{
				
			}
		}//cuenta las ocurrencias de la subcadena en el string pasado o
		// cuenta las ocurrencias del elemento pasado en la lista
		else if (this.value == "count"){
			Resultado variable = variables.get(this.id_variable.toString());
			if(variable.getTipo()==TipoResultado.STRING){	
				Expresion e = (Expresion)this.arguments.get(0);
				Resultado aux = e.ejecutar(variables, Funciones, loop);			
				String subcadena = aux.getValor().toString();
				String v = variable.getValor();
				Pattern pattern = Pattern.compile(subcadena);
				Matcher matcher = pattern.matcher(v);
				int count = 0;
				while (matcher.find()) {
				     count++;
				}
				String largo = Integer.toString(count);				
				ret = new Resultado(largo,TipoResultado.INTEGER);
			}
			else if(variable.getTipo()==TipoResultado.LIST)
			{
				Expresion e = (Expresion)this.arguments.get(0);
				Resultado aux = e.ejecutar(variables, Funciones, loop);			
				String elemento = aux.getValor().toString();
				ArrayList<Resultado> lista= variable.getValores();
				int count = 0;
				for(Resultado r: lista){
					if (r.getValor().equals(elemento))
						count++;
				}
				
				String largo = Integer.toString(count);
				ret = new Resultado(largo,TipoResultado.INTEGER);
			}
			else {
				throw new ParsingException(ParsingException.ERROR_FUNC_PREDEF1+this.lugar);
			}
		}

		return ret;
	}

	public String getValor() {
		return this.value.toString();
	}

}
