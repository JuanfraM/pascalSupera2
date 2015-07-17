package com.language.model.expression;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.language.Ejecutar;
import com.language.Scope;
import com.language.exceptions.ParsingException;

public class Expresion extends Sentencia {

	private Object value; 
	private ArrayList<Expresion> arguments;
	private TipoExpresion tipo;
	private int linea, col;
	
	public Expresion(Object value, TipoExpresion tipo, int linea, int col){
		this.value = value;
		this.tipo = tipo;
		this.linea = linea;
		this.col = col;
	}
	
	public Expresion(Object value, Expresion arg, TipoExpresion tipo, int linea, int col){
		this.value = value;
		this.tipo = tipo;
		this.linea = linea;
		this.col = col;
		this.arguments = new ArrayList<Expresion>();
		this.arguments.add(arg);
	}
	
	public Expresion(Object value, Expresion arg1, Expresion arg2, TipoExpresion tipo, int linea, int col){
		this.value = value;
		this.tipo = tipo;
		this.linea = linea;
		this.col = col;
		this.arguments = new ArrayList<Expresion>();
		this.arguments.add(arg1);
		this.arguments.add(arg2);
	}
	
	public Expresion(Object value, ArrayList<Expresion> arguments, TipoExpresion tipo, int linea, int col) {
		this.value = value;
		this.arguments = arguments;
		this.tipo = tipo;
		this.linea = linea;
		this.col = col;
		if (this.arguments == null) {
			this.arguments = new ArrayList<Expresion>();
		}
	}

	public Resultado ejecutar(Scope variables, Map<String,FuncionDef> Funciones, boolean loop){
		
		Resultado ret = null;
		
		if (this.tipo == TipoExpresion.INTEGER){
			ret = new Resultado(this.value.toString(), TipoResultado.INTEGER);
		}
		else if (this.tipo == TipoExpresion.FLOAT){
			ret = new Resultado(this.value.toString(), TipoResultado.FLOAT);
		}
		else if (this.tipo == TipoExpresion.BOOL){
			ret = new Resultado(this.value.toString(), TipoResultado.BOOL);
		}
		else if (this.tipo == TipoExpresion.NONE){
			ret = new Resultado(this.value.toString(), TipoResultado.NONE);
		}
		else if (this.tipo == TipoExpresion.STRING){
			ret = new Resultado(this.value.toString(), TipoResultado.STRING);
		}
		else if (this.tipo == TipoExpresion.LONG ){
			ret = new Resultado(this.value.toString().replaceAll("[lL]", ""), TipoResultado.LONG);
		}
		else if(this.tipo == TipoExpresion.ID){
			
			
			if (variables.containsKey(this.value.toString())){
				ret = variables.get(this.value.toString());
			}
			else{
				throw new ParsingException("La variabled " + this.value.toString() + " no se encuentra definida en la" +
											" linea: " + this.linea + " columna: " + this.col );
			}
		}
		
		
		else if(this.tipo == TipoExpresion.LIST || this.tipo == TipoExpresion.TUPLA){
			ArrayList<Resultado> arg = new ArrayList<Resultado>();
			for (Expresion a : this.arguments){
				arg.add(a.ejecutar(variables, Funciones, loop));
			}
			if (this.tipo == TipoExpresion.LIST)
				ret = new Resultado(arg, TipoResultado.LIST);
			else 
				ret = new Resultado(arg, TipoResultado.TUPLA);
		}
		
		//UNARIAS
		
		else if (this.tipo == TipoExpresion.UNARIA){
			Resultado arg = this.arguments.get(0).ejecutar(variables, Funciones, loop);
					
			if (this.value.toString() == "not"){
				if (arg.getTipo() == TipoResultado.BOOL)
					ret = new Resultado(((Boolean)(!Boolean.parseBoolean(arg.getValor()))).toString(), TipoResultado.BOOL);
				else
					throw new ParsingException("Tipo de datos no valido linea: " + this.linea + " columna: " + this.col );
			}
			else if (this.value.toString() == "+"){
				if (arg.getTipo() == TipoResultado.LONG || arg.getTipo() == TipoResultado.FLOAT || arg.getTipo() == TipoResultado.INTEGER)
					ret = new Resultado(arg.getValor(), arg.getTipo());
				else
					throw new ParsingException("Tipo de datos no valido linea: " + this.linea + " columna: " + this.col );
			}
			else if (this.value.toString() == "-"){
				if (arg.getTipo() == TipoResultado.LONG) 
					ret = new Resultado(((Long)(-Long.parseLong(arg.getValor()))).toString(), TipoResultado.LONG);
				else if (arg.getTipo() == TipoResultado.FLOAT)
					ret = new Resultado(((Float)(-Float.parseFloat(arg.getValor()))).toString(), TipoResultado.FLOAT);
				else if (arg.getTipo() == TipoResultado.INTEGER)
					ret = new Resultado(((Integer)(-Integer.parseInt(arg.getValor()))).toString(), TipoResultado.INTEGER);
				else
					throw new ParsingException("Tipo de datos no valido linea: " + this.linea + " columna: " + this.col );
					
			}
			else if (this.value.toString() == "~"){
				if (arg.getTipo() == TipoResultado.INTEGER)
					ret = new Resultado(((Integer)(~Integer.parseInt(arg.getValor()))).toString(), TipoResultado.INTEGER);
				else if (arg.getTipo() == TipoResultado.LONG)
					ret = new Resultado(((Long)(~Long.parseLong(arg.getValor()))).toString(), TipoResultado.LONG);
				else
					throw new ParsingException("Tipo de datos no valido linea: " + this.linea + " columna: " + this.col );
			}
			
		}
		
		//BINARIAS
		
		else if (this.tipo == TipoExpresion.BINARIA){
			Resultado arg1 = this.arguments.get(0).ejecutar(variables, Funciones, loop);
			Resultado arg2 = this.arguments.get(1).ejecutar(variables, Funciones, loop);
			
			//BOOLEANAS
			
			if (this.value.toString() == "or"){
				if (arg1.getTipo() == TipoResultado.BOOL && arg2.getTipo() == TipoResultado.BOOL){
					Boolean or = Boolean.parseBoolean(arg1.getValor()) || Boolean.parseBoolean(arg2.getValor());
					ret = new Resultado(or.toString(), TipoResultado.BOOL);
				}		
				else
					throw new ParsingException("Tipo de datos no valido linea: " + this.linea + " columna: " + this.col );
			}
			else if (this.value.toString() == "and"){
				if (arg1.getTipo() == TipoResultado.BOOL && arg2.getTipo() == TipoResultado.BOOL){
					Boolean and = Boolean.parseBoolean(arg1.getValor()) && Boolean.parseBoolean(arg2.getValor());
					ret = new Resultado(and.toString(), TipoResultado.BOOL);
				}		
				else
					throw new ParsingException("Tipo de datos no valido linea: " + this.linea + " columna: " + this.col );
			}
			
			//COMPARACION
			
			else if (this.value.toString() == "<"){
				if (arg1.getTipo() == TipoResultado.INTEGER && arg2.getTipo() == TipoResultado.INTEGER){
					Boolean b = Integer.parseInt(arg1.getValor()) < Integer.parseInt(arg2.getValor());
					ret = new Resultado(b.toString(), TipoResultado.BOOL);
				}
				else if (arg1.getTipo() == TipoResultado.INTEGER && arg2.getTipo() == TipoResultado.LONG){
					Boolean b = Integer.parseInt(arg1.getValor()) < Long.parseLong(arg2.getValor());
					ret = new Resultado(b.toString(), TipoResultado.BOOL);
				}
				else if (arg1.getTipo() == TipoResultado.INTEGER && arg2.getTipo() == TipoResultado.FLOAT){
					Boolean b = Integer.parseInt(arg1.getValor()) < Float.parseFloat(arg2.getValor());
					ret = new Resultado(b.toString(), TipoResultado.BOOL);
				}
				else if (arg1.getTipo() == TipoResultado.LONG && arg2.getTipo() == TipoResultado.INTEGER){
					Boolean b = Long.parseLong(arg1.getValor()) < Integer.parseInt(arg2.getValor());
					ret = new Resultado(b.toString(), TipoResultado.BOOL);
				}
				else if (arg1.getTipo() == TipoResultado.LONG && arg2.getTipo() == TipoResultado.LONG){
					Boolean b = Long.parseLong(arg1.getValor()) < Long.parseLong(arg2.getValor());
					ret = new Resultado(b.toString(), TipoResultado.BOOL);
				}
				else if (arg1.getTipo() == TipoResultado.LONG && arg2.getTipo() == TipoResultado.FLOAT){
					Boolean b = Long.parseLong(arg1.getValor()) < Float.parseFloat(arg2.getValor());
					ret = new Resultado(b.toString(), TipoResultado.BOOL);
				}
				else if (arg1.getTipo() == TipoResultado.FLOAT && arg2.getTipo() == TipoResultado.INTEGER){
					Boolean b = Float.parseFloat(arg1.getValor()) < Integer.parseInt(arg2.getValor());
					ret = new Resultado(b.toString(), TipoResultado.BOOL);
				}
				else if (arg1.getTipo() == TipoResultado.FLOAT && arg2.getTipo() == TipoResultado.LONG){
					Boolean b = Float.parseFloat(arg1.getValor()) < Long.parseLong(arg2.getValor());
					ret = new Resultado(b.toString(), TipoResultado.BOOL);
				}
				else if (arg1.getTipo() == TipoResultado.FLOAT && arg2.getTipo() == TipoResultado.FLOAT){
					Boolean b = Float.parseFloat(arg1.getValor()) < Float.parseFloat(arg2.getValor());
					ret = new Resultado(b.toString(), TipoResultado.BOOL);
				}
				else
					throw new ParsingException("Tipo de datos no valido linea: " + this.linea + " columna: " + this.col );
			}
			else if (this.value.toString() == ">"){
				if (arg1.getTipo() == TipoResultado.INTEGER && arg2.getTipo() == TipoResultado.INTEGER){
					Boolean b = Integer.parseInt(arg1.getValor()) > Integer.parseInt(arg2.getValor());
					ret = new Resultado(b.toString(), TipoResultado.BOOL);
				}
				else if (arg1.getTipo() == TipoResultado.INTEGER && arg2.getTipo() == TipoResultado.LONG){
					Boolean b = Integer.parseInt(arg1.getValor()) > Long.parseLong(arg2.getValor());
					ret = new Resultado(b.toString(), TipoResultado.BOOL);
				}
				else if (arg1.getTipo() == TipoResultado.INTEGER && arg2.getTipo() == TipoResultado.FLOAT){
					Boolean b = Integer.parseInt(arg1.getValor()) > Float.parseFloat(arg2.getValor());
					ret = new Resultado(b.toString(), TipoResultado.BOOL);
				}
				else if (arg1.getTipo() == TipoResultado.LONG && arg2.getTipo() == TipoResultado.INTEGER){
					Boolean b = Long.parseLong(arg1.getValor()) > Integer.parseInt(arg2.getValor());
					ret = new Resultado(b.toString(), TipoResultado.BOOL);
				}
				else if (arg1.getTipo() == TipoResultado.LONG && arg2.getTipo() == TipoResultado.LONG){
					Boolean b = Long.parseLong(arg1.getValor()) > Long.parseLong(arg2.getValor());
					ret = new Resultado(b.toString(), TipoResultado.BOOL);
				}
				else if (arg1.getTipo() == TipoResultado.LONG && arg2.getTipo() == TipoResultado.FLOAT){
					Boolean b = Long.parseLong(arg1.getValor()) > Float.parseFloat(arg2.getValor());
					ret = new Resultado(b.toString(), TipoResultado.BOOL);
				}
				else if (arg1.getTipo() == TipoResultado.FLOAT && arg2.getTipo() == TipoResultado.INTEGER){
					Boolean b = Float.parseFloat(arg1.getValor()) > Integer.parseInt(arg2.getValor());
					ret = new Resultado(b.toString(), TipoResultado.BOOL);
				}
				else if (arg1.getTipo() == TipoResultado.FLOAT && arg2.getTipo() == TipoResultado.LONG){
					Boolean b = Float.parseFloat(arg1.getValor()) > Long.parseLong(arg2.getValor());
					ret = new Resultado(b.toString(), TipoResultado.BOOL);
				}
				else if (arg1.getTipo() == TipoResultado.FLOAT && arg2.getTipo() == TipoResultado.FLOAT){
					Boolean b = Float.parseFloat(arg1.getValor()) > Float.parseFloat(arg2.getValor());
					ret = new Resultado(b.toString(), TipoResultado.BOOL);
				}
				else
					throw new ParsingException("Tipo de datos no valido linea: " + this.linea + " columna: " + this.col );
			}
			else if (this.value.toString() == ">="){
				if (arg1.getTipo() == TipoResultado.INTEGER && arg2.getTipo() == TipoResultado.INTEGER){
					Boolean b = Integer.parseInt(arg1.getValor()) >= Integer.parseInt(arg2.getValor());
					ret = new Resultado(b.toString(), TipoResultado.BOOL);
				}
				else if (arg1.getTipo() == TipoResultado.INTEGER && arg2.getTipo() == TipoResultado.LONG){
					Boolean b = Integer.parseInt(arg1.getValor()) >= Long.parseLong(arg2.getValor());
					ret = new Resultado(b.toString(), TipoResultado.BOOL);
				}
				else if (arg1.getTipo() == TipoResultado.INTEGER && arg2.getTipo() == TipoResultado.FLOAT){
					Boolean b = Integer.parseInt(arg1.getValor()) >= Float.parseFloat(arg2.getValor());
					ret = new Resultado(b.toString(), TipoResultado.BOOL);
				}
				else if (arg1.getTipo() == TipoResultado.LONG && arg2.getTipo() == TipoResultado.INTEGER){
					Boolean b = Long.parseLong(arg1.getValor()) >= Integer.parseInt(arg2.getValor());
					ret = new Resultado(b.toString(), TipoResultado.BOOL);
				}
				else if (arg1.getTipo() == TipoResultado.LONG && arg2.getTipo() == TipoResultado.LONG){
					Boolean b = Long.parseLong(arg1.getValor()) >= Long.parseLong(arg2.getValor());
					ret = new Resultado(b.toString(), TipoResultado.BOOL);
				}
				else if (arg1.getTipo() == TipoResultado.LONG && arg2.getTipo() == TipoResultado.FLOAT){
					Boolean b = Long.parseLong(arg1.getValor()) >= Float.parseFloat(arg2.getValor());
					ret = new Resultado(b.toString(), TipoResultado.BOOL);
				}
				else if (arg1.getTipo() == TipoResultado.FLOAT && arg2.getTipo() == TipoResultado.INTEGER){
					Boolean b = Float.parseFloat(arg1.getValor()) >= Integer.parseInt(arg2.getValor());
					ret = new Resultado(b.toString(), TipoResultado.BOOL);
				}
				else if (arg1.getTipo() == TipoResultado.FLOAT && arg2.getTipo() == TipoResultado.LONG){
					Boolean b = Float.parseFloat(arg1.getValor()) >= Long.parseLong(arg2.getValor());
					ret = new Resultado(b.toString(), TipoResultado.BOOL);
				}
				else if (arg1.getTipo() == TipoResultado.FLOAT && arg2.getTipo() == TipoResultado.FLOAT){
					Boolean b = Float.parseFloat(arg1.getValor()) >= Float.parseFloat(arg2.getValor());
					ret = new Resultado(b.toString(), TipoResultado.BOOL);
				}
				else
					throw new ParsingException("Tipo de datos no valido linea: " + this.linea + " columna: " + this.col );
			}
			else if (this.value.toString() == "<="){
				if (arg1.getTipo() == TipoResultado.INTEGER && arg2.getTipo() == TipoResultado.INTEGER){
					Boolean b = Integer.parseInt(arg1.getValor()) <= Integer.parseInt(arg2.getValor());
					ret = new Resultado(b.toString(), TipoResultado.BOOL);
				}
				else if (arg1.getTipo() == TipoResultado.INTEGER && arg2.getTipo() == TipoResultado.LONG){
					Boolean b = Integer.parseInt(arg1.getValor()) <= Long.parseLong(arg2.getValor());
					ret = new Resultado(b.toString(), TipoResultado.BOOL);
				}
				else if (arg1.getTipo() == TipoResultado.INTEGER && arg2.getTipo() == TipoResultado.FLOAT){
					Boolean b = Integer.parseInt(arg1.getValor()) <= Float.parseFloat(arg2.getValor());
					ret = new Resultado(b.toString(), TipoResultado.BOOL);
				}
				else if (arg1.getTipo() == TipoResultado.LONG && arg2.getTipo() == TipoResultado.INTEGER){
					Boolean b = Long.parseLong(arg1.getValor()) <= Integer.parseInt(arg2.getValor());
					ret = new Resultado(b.toString(), TipoResultado.BOOL);
				}
				else if (arg1.getTipo() == TipoResultado.LONG && arg2.getTipo() == TipoResultado.LONG){
					Boolean b = Long.parseLong(arg1.getValor()) <= Long.parseLong(arg2.getValor());
					ret = new Resultado(b.toString(), TipoResultado.BOOL);
				}
				else if (arg1.getTipo() == TipoResultado.LONG && arg2.getTipo() == TipoResultado.FLOAT){
					Boolean b = Long.parseLong(arg1.getValor()) <= Float.parseFloat(arg2.getValor());
					ret = new Resultado(b.toString(), TipoResultado.BOOL);
				}
				else if (arg1.getTipo() == TipoResultado.FLOAT && arg2.getTipo() == TipoResultado.INTEGER){
					Boolean b = Float.parseFloat(arg1.getValor()) <= Integer.parseInt(arg2.getValor());
					ret = new Resultado(b.toString(), TipoResultado.BOOL);
				}
				else if (arg1.getTipo() == TipoResultado.FLOAT && arg2.getTipo() == TipoResultado.LONG){
					Boolean b = Float.parseFloat(arg1.getValor()) <= Long.parseLong(arg2.getValor());
					ret = new Resultado(b.toString(), TipoResultado.BOOL);
				}
				else if (arg1.getTipo() == TipoResultado.FLOAT && arg2.getTipo() == TipoResultado.FLOAT){
					Boolean b = Float.parseFloat(arg1.getValor()) <= Float.parseFloat(arg2.getValor());
					ret = new Resultado(b.toString(), TipoResultado.BOOL);
				}
				else
					throw new ParsingException("Tipo de datos no valido linea: " + this.linea + " columna: " + this.col );
			}
			else if (this.value.toString() == "!="){
				if (arg1.getTipo() == TipoResultado.INTEGER && arg2.getTipo() == TipoResultado.INTEGER){
					Boolean b = Integer.parseInt(arg1.getValor()) != Integer.parseInt(arg2.getValor());
					ret = new Resultado(b.toString(), TipoResultado.BOOL);
				}
				else if (arg1.getTipo() == TipoResultado.INTEGER && arg2.getTipo() == TipoResultado.LONG){
					Boolean b = Integer.parseInt(arg1.getValor()) != Long.parseLong(arg2.getValor());
					ret = new Resultado(b.toString(), TipoResultado.BOOL);
				}
				else if (arg1.getTipo() == TipoResultado.INTEGER && arg2.getTipo() == TipoResultado.FLOAT){
					Boolean b = Integer.parseInt(arg1.getValor()) != Float.parseFloat(arg2.getValor());
					ret = new Resultado(b.toString(), TipoResultado.BOOL);
				}
				else if (arg1.getTipo() == TipoResultado.LONG && arg2.getTipo() == TipoResultado.INTEGER){
					Boolean b = Long.parseLong(arg1.getValor()) != Integer.parseInt(arg2.getValor());
					ret = new Resultado(b.toString(), TipoResultado.BOOL);
				}
				else if (arg1.getTipo() == TipoResultado.LONG && arg2.getTipo() == TipoResultado.LONG){
					Boolean b = Long.parseLong(arg1.getValor()) != Long.parseLong(arg2.getValor());
					ret = new Resultado(b.toString(), TipoResultado.BOOL);
				}
				else if (arg1.getTipo() == TipoResultado.LONG && arg2.getTipo() == TipoResultado.FLOAT){
					Boolean b = Long.parseLong(arg1.getValor()) != Float.parseFloat(arg2.getValor());
					ret = new Resultado(b.toString(), TipoResultado.BOOL);
				}
				else if (arg1.getTipo() == TipoResultado.FLOAT && arg2.getTipo() == TipoResultado.INTEGER){
					Boolean b = Float.parseFloat(arg1.getValor()) != Integer.parseInt(arg2.getValor());
					ret = new Resultado(b.toString(), TipoResultado.BOOL);
				}
				else if (arg1.getTipo() == TipoResultado.FLOAT && arg2.getTipo() == TipoResultado.LONG){
					Boolean b = Float.parseFloat(arg1.getValor()) != Long.parseLong(arg2.getValor());
					ret = new Resultado(b.toString(), TipoResultado.BOOL);
				}
				else if (arg1.getTipo() == TipoResultado.FLOAT && arg2.getTipo() == TipoResultado.FLOAT){
					Boolean b = Float.parseFloat(arg1.getValor()) != Float.parseFloat(arg2.getValor());
					ret = new Resultado(b.toString(), TipoResultado.BOOL);
				}
				else if (arg1.getTipo() == TipoResultado.STRING && arg2.getTipo() == TipoResultado.STRING){
					Boolean b = arg1.getValor() != arg2.getValor();
					ret = new Resultado(b.toString(), TipoResultado.BOOL);
				}		
				else if (arg1.getTipo() == TipoResultado.BOOL && arg2.getTipo() == TipoResultado.BOOL){
					Boolean b = Boolean.parseBoolean(arg1.getValor()) != Boolean.parseBoolean(arg2.getValor());
					ret = new Resultado(b.toString(), TipoResultado.BOOL);
				}
				else
					throw new ParsingException("Tipo de datos no valido linea: " + this.linea + " columna: " + this.col );
			}
			else if (this.value.toString() == "=="){
				if (arg1.getTipo() == TipoResultado.INTEGER && arg2.getTipo() == TipoResultado.INTEGER){
					Boolean b = Integer.parseInt(arg1.getValor()) == Integer.parseInt(arg2.getValor());
					ret = new Resultado(b.toString(), TipoResultado.BOOL);
				}
				else if (arg1.getTipo() == TipoResultado.INTEGER && arg2.getTipo() == TipoResultado.LONG){
					Boolean b = Integer.parseInt(arg1.getValor()) == Long.parseLong(arg2.getValor());
					ret = new Resultado(b.toString(), TipoResultado.BOOL);
				}
				else if (arg1.getTipo() == TipoResultado.INTEGER && arg2.getTipo() == TipoResultado.FLOAT){
					Boolean b = Integer.parseInt(arg1.getValor()) == Float.parseFloat(arg2.getValor());
					ret = new Resultado(b.toString(), TipoResultado.BOOL);
				}
				else if (arg1.getTipo() == TipoResultado.LONG && arg2.getTipo() == TipoResultado.INTEGER){
					Boolean b = Long.parseLong(arg1.getValor()) == Integer.parseInt(arg2.getValor());
					ret = new Resultado(b.toString(), TipoResultado.BOOL);
				}
				else if (arg1.getTipo() == TipoResultado.LONG && arg2.getTipo() == TipoResultado.LONG){
					Boolean b = Long.parseLong(arg1.getValor()) == Long.parseLong(arg2.getValor());
					ret = new Resultado(b.toString(), TipoResultado.BOOL);
				}
				else if (arg1.getTipo() == TipoResultado.LONG && arg2.getTipo() == TipoResultado.FLOAT){
					Boolean b = Long.parseLong(arg1.getValor()) == Float.parseFloat(arg2.getValor());
					ret = new Resultado(b.toString(), TipoResultado.BOOL);
				}
				else if (arg1.getTipo() == TipoResultado.FLOAT && arg2.getTipo() == TipoResultado.INTEGER){
					Boolean b = Float.parseFloat(arg1.getValor()) == Integer.parseInt(arg2.getValor());
					ret = new Resultado(b.toString(), TipoResultado.BOOL);
				}
				else if (arg1.getTipo() == TipoResultado.FLOAT && arg2.getTipo() == TipoResultado.LONG){
					Boolean b = Float.parseFloat(arg1.getValor()) == Long.parseLong(arg2.getValor());
					ret = new Resultado(b.toString(), TipoResultado.BOOL);
				}
				else if (arg1.getTipo() == TipoResultado.FLOAT && arg2.getTipo() == TipoResultado.FLOAT){
					Boolean b = Float.parseFloat(arg1.getValor()) == Float.parseFloat(arg2.getValor());
					ret = new Resultado(b.toString(), TipoResultado.BOOL);
				}
				else if (arg1.getTipo() == TipoResultado.STRING && arg2.getTipo() == TipoResultado.STRING){
					Boolean b = arg1.getValor() == arg2.getValor();
					ret = new Resultado(b.toString(), TipoResultado.BOOL);
				}		
				else if (arg1.getTipo() == TipoResultado.BOOL && arg2.getTipo() == TipoResultado.BOOL){
					Boolean b = Boolean.parseBoolean(arg1.getValor()) == Boolean.parseBoolean(arg2.getValor());
					ret = new Resultado(b.toString(), TipoResultado.BOOL);
				}
				else
					throw new ParsingException("Tipo de datos no valido linea: " + this.linea + " columna: " + this.col );
			}
			
			//BIT A BIT
			
			else if (this.value.toString() == "|"){
				if (arg1.getTipo() == TipoResultado.INTEGER && arg2.getTipo() == TipoResultado.INTEGER){
					Integer b = Integer.parseInt(arg1.getValor()) | Integer.parseInt(arg2.getValor());
					ret = new Resultado(b.toString(), TipoResultado.INTEGER);
				}
				else if (arg1.getTipo() == TipoResultado.INTEGER && arg2.getTipo() == TipoResultado.LONG){
					Long  b = Integer.parseInt(arg1.getValor()) | Long.parseLong(arg2.getValor());
					ret = new Resultado(b.toString(), TipoResultado.LONG);
				}	
				else if (arg1.getTipo() == TipoResultado.LONG && arg2.getTipo() == TipoResultado.INTEGER){
					Long b = Long.parseLong(arg1.getValor()) | Integer.parseInt(arg2.getValor());
					ret = new Resultado(b.toString(), TipoResultado.LONG);
				}
				else if (arg1.getTipo() == TipoResultado.LONG && arg2.getTipo() == TipoResultado.LONG){
					Long b = Long.parseLong(arg1.getValor()) | Long.parseLong(arg2.getValor());
					ret = new Resultado(b.toString(), TipoResultado.LONG);
				}
				else
					throw new ParsingException("Tipo de datos no valido linea: " + this.linea + " columna: " + this.col );
			}
			else if (this.value.toString() == "^"){
				if (arg1.getTipo() == TipoResultado.INTEGER && arg2.getTipo() == TipoResultado.INTEGER){
					Integer b = Integer.parseInt(arg1.getValor()) ^ Integer.parseInt(arg2.getValor());
					ret = new Resultado(b.toString(), TipoResultado.INTEGER);
				}
				else if (arg1.getTipo() == TipoResultado.INTEGER && arg2.getTipo() == TipoResultado.LONG){
					Long  b = Integer.parseInt(arg1.getValor()) ^ Long.parseLong(arg2.getValor());
					ret = new Resultado(b.toString(), TipoResultado.LONG);
				}	
				else if (arg1.getTipo() == TipoResultado.LONG && arg2.getTipo() == TipoResultado.INTEGER){
					Long b = Long.parseLong(arg1.getValor()) ^ Integer.parseInt(arg2.getValor());
					ret = new Resultado(b.toString(), TipoResultado.LONG);
				}
				else if (arg1.getTipo() == TipoResultado.LONG && arg2.getTipo() == TipoResultado.LONG){
					Long b = Long.parseLong(arg1.getValor()) ^ Long.parseLong(arg2.getValor());
					ret = new Resultado(b.toString(), TipoResultado.LONG);
				}
				else
					throw new ParsingException("Tipo de datos no valido linea: " + this.linea + " columna: " + this.col );
			}
			else if (this.value.toString() == "&"){
				if (arg1.getTipo() == TipoResultado.INTEGER && arg2.getTipo() == TipoResultado.INTEGER){
					Integer b = Integer.parseInt(arg1.getValor()) & Integer.parseInt(arg2.getValor());
					ret = new Resultado(b.toString(), TipoResultado.INTEGER);
				}
				else if (arg1.getTipo() == TipoResultado.INTEGER && arg2.getTipo() == TipoResultado.LONG){
					Long  b = Integer.parseInt(arg1.getValor()) & Long.parseLong(arg2.getValor());
					ret = new Resultado(b.toString(), TipoResultado.LONG);
				}	
				else if (arg1.getTipo() == TipoResultado.LONG && arg2.getTipo() == TipoResultado.INTEGER){
					Long b = Long.parseLong(arg1.getValor()) & Integer.parseInt(arg2.getValor());
					ret = new Resultado(b.toString(), TipoResultado.LONG);
				}
				else if (arg1.getTipo() == TipoResultado.LONG && arg2.getTipo() == TipoResultado.LONG){
					Long b = Long.parseLong(arg1.getValor()) & Long.parseLong(arg2.getValor());
					ret = new Resultado(b.toString(), TipoResultado.LONG);
				}
				else
					throw new ParsingException("Tipo de datos no valido linea: " + this.linea + " columna: " + this.col );	
			}
			else if (this.value.toString() == ">>"){
				if (arg1.getTipo() == TipoResultado.INTEGER && arg2.getTipo() == TipoResultado.INTEGER){
					Integer b = Integer.parseInt(arg1.getValor()) >> Integer.parseInt(arg2.getValor());
					ret = new Resultado(b.toString(), TipoResultado.INTEGER);
				}
				else if (arg1.getTipo() == TipoResultado.INTEGER && arg2.getTipo() == TipoResultado.LONG){
					Integer  b = Integer.parseInt(arg1.getValor()) >> Long.parseLong(arg2.getValor());
					ret = new Resultado(b.toString(), TipoResultado.INTEGER);
				}	
				else if (arg1.getTipo() == TipoResultado.LONG && arg2.getTipo() == TipoResultado.INTEGER){
					Long b = Long.parseLong(arg1.getValor()) >> Integer.parseInt(arg2.getValor());
					ret = new Resultado(b.toString(), TipoResultado.LONG);
				}
				else if (arg1.getTipo() == TipoResultado.LONG && arg2.getTipo() == TipoResultado.LONG){
					Long b = Long.parseLong(arg1.getValor()) >> Long.parseLong(arg2.getValor());
					ret = new Resultado(b.toString(), TipoResultado.LONG);
				}
				else
					throw new ParsingException("Tipo de datos no valido linea: " + this.linea + " columna: " + this.col );
			}
			else if (this.value.toString() == "<<"){
				if (arg1.getTipo() == TipoResultado.INTEGER && arg2.getTipo() == TipoResultado.INTEGER){
					Integer b = Integer.parseInt(arg1.getValor()) << Integer.parseInt(arg2.getValor());
					ret = new Resultado(b.toString(), TipoResultado.INTEGER);
				}
				else if (arg1.getTipo() == TipoResultado.INTEGER && arg2.getTipo() == TipoResultado.LONG){
					Integer  b = Integer.parseInt(arg1.getValor()) << Long.parseLong(arg2.getValor());
					ret = new Resultado(b.toString(), TipoResultado.INTEGER);
				}	
				else if (arg1.getTipo() == TipoResultado.LONG && arg2.getTipo() == TipoResultado.INTEGER){
					Long b = Long.parseLong(arg1.getValor()) << Integer.parseInt(arg2.getValor());
					ret = new Resultado(b.toString(), TipoResultado.LONG);
				}
				else if (arg1.getTipo() == TipoResultado.LONG && arg2.getTipo() == TipoResultado.LONG){
					Long b = Long.parseLong(arg1.getValor()) << Long.parseLong(arg2.getValor());
					ret = new Resultado(b.toString(), TipoResultado.LONG);
				}
				else
					throw new ParsingException("Tipo de datos no valido linea: " + this.linea + " columna: " + this.col );
			}
			
			//ARITMETICAS
			
			else if (this.value.toString() == "+"){
				if (arg1.getTipo() == TipoResultado.INTEGER && arg2.getTipo() == TipoResultado.INTEGER){
					Integer b = Integer.parseInt(arg1.getValor()) + Integer.parseInt(arg2.getValor());
					ret = new Resultado(b.toString(), TipoResultado.INTEGER);
				}
				else if (arg1.getTipo() == TipoResultado.INTEGER && arg2.getTipo() == TipoResultado.LONG){
					Long b = Integer.parseInt(arg1.getValor()) + Long.parseLong(arg2.getValor());
					ret = new Resultado(b.toString(), TipoResultado.LONG);
				}
				else if (arg1.getTipo() == TipoResultado.INTEGER && arg2.getTipo() == TipoResultado.FLOAT){
					Float b = Integer.parseInt(arg1.getValor()) + Float.parseFloat(arg2.getValor());
					ret = new Resultado(b.toString(), TipoResultado.FLOAT);
				}
				else if (arg1.getTipo() == TipoResultado.LONG && arg2.getTipo() == TipoResultado.INTEGER){
					Long b = Long.parseLong(arg1.getValor()) + Integer.parseInt(arg2.getValor());
					ret = new Resultado(b.toString(), TipoResultado.LONG);
				}
				else if (arg1.getTipo() == TipoResultado.LONG && arg2.getTipo() == TipoResultado.LONG){
					Long b = Long.parseLong(arg1.getValor()) + Long.parseLong(arg2.getValor());
					ret = new Resultado(b.toString(), TipoResultado.LONG);
				}
				else if (arg1.getTipo() == TipoResultado.LONG && arg2.getTipo() == TipoResultado.FLOAT){
					Float b = Long.parseLong(arg1.getValor()) + Float.parseFloat(arg2.getValor());
					ret = new Resultado(b.toString(), TipoResultado.FLOAT);
				}
				else if (arg1.getTipo() == TipoResultado.FLOAT && arg2.getTipo() == TipoResultado.INTEGER){
					Float b = Float.parseFloat(arg1.getValor()) + Integer.parseInt(arg2.getValor());
					ret = new Resultado(b.toString(), TipoResultado.FLOAT);
				}
				else if (arg1.getTipo() == TipoResultado.FLOAT && arg2.getTipo() == TipoResultado.LONG){
					Float b = Float.parseFloat(arg1.getValor()) + Long.parseLong(arg2.getValor());
					ret = new Resultado(b.toString(), TipoResultado.FLOAT);
				}
				else if (arg1.getTipo() == TipoResultado.FLOAT && arg2.getTipo() == TipoResultado.FLOAT){
					Float b = Float.parseFloat(arg1.getValor()) + Float.parseFloat(arg2.getValor());
					ret = new Resultado(b.toString(), TipoResultado.FLOAT);
				}
				else
					throw new ParsingException("Tipo de datos no valido linea: " + this.linea + " columna: " + this.col );
			}
			else if (this.value.toString() == "-"){
				if (arg1.getTipo() == TipoResultado.INTEGER && arg2.getTipo() == TipoResultado.INTEGER){
					Integer b = Integer.parseInt(arg1.getValor()) - Integer.parseInt(arg2.getValor());
					ret = new Resultado(b.toString(), TipoResultado.INTEGER);
				}
				else if (arg1.getTipo() == TipoResultado.INTEGER && arg2.getTipo() == TipoResultado.LONG){
					Long b = Integer.parseInt(arg1.getValor()) - Long.parseLong(arg2.getValor());
					ret = new Resultado(b.toString(), TipoResultado.LONG);
				}
				else if (arg1.getTipo() == TipoResultado.INTEGER && arg2.getTipo() == TipoResultado.FLOAT){
					Float b = Integer.parseInt(arg1.getValor()) - Float.parseFloat(arg2.getValor());
					ret = new Resultado(b.toString(), TipoResultado.FLOAT);
				}
				else if (arg1.getTipo() == TipoResultado.LONG && arg2.getTipo() == TipoResultado.INTEGER){
					Long b = Long.parseLong(arg1.getValor()) - Integer.parseInt(arg2.getValor());
					ret = new Resultado(b.toString(), TipoResultado.LONG);
				}
				else if (arg1.getTipo() == TipoResultado.LONG && arg2.getTipo() == TipoResultado.LONG){
					Long b = Long.parseLong(arg1.getValor()) - Long.parseLong(arg2.getValor());
					ret = new Resultado(b.toString(), TipoResultado.LONG);
				}
				else if (arg1.getTipo() == TipoResultado.LONG && arg2.getTipo() == TipoResultado.FLOAT){
					Float b = Long.parseLong(arg1.getValor()) - Float.parseFloat(arg2.getValor());
					ret = new Resultado(b.toString(), TipoResultado.FLOAT);
				}
				else if (arg1.getTipo() == TipoResultado.FLOAT && arg2.getTipo() == TipoResultado.INTEGER){
					Float b = Float.parseFloat(arg1.getValor()) - Integer.parseInt(arg2.getValor());
					ret = new Resultado(b.toString(), TipoResultado.FLOAT);
				}
				else if (arg1.getTipo() == TipoResultado.FLOAT && arg2.getTipo() == TipoResultado.LONG){
					Float b = Float.parseFloat(arg1.getValor()) - Long.parseLong(arg2.getValor());
					ret = new Resultado(b.toString(), TipoResultado.FLOAT);
				}
				else if (arg1.getTipo() == TipoResultado.FLOAT && arg2.getTipo() == TipoResultado.FLOAT){
					Float b = Float.parseFloat(arg1.getValor()) - Float.parseFloat(arg2.getValor());
					ret = new Resultado(b.toString(), TipoResultado.FLOAT);
				}
				else
					throw new ParsingException("Tipo de datos no valido linea: " + this.linea + " columna: " + this.col );
			}
			else if (this.value.toString() == "*"){
				if (arg1.getTipo() == TipoResultado.INTEGER && arg2.getTipo() == TipoResultado.INTEGER){
					Integer b = Integer.parseInt(arg1.getValor()) * Integer.parseInt(arg2.getValor());
					ret = new Resultado(b.toString(), TipoResultado.INTEGER);
				}
				else if (arg1.getTipo() == TipoResultado.INTEGER && arg2.getTipo() == TipoResultado.LONG){
					Long b = Integer.parseInt(arg1.getValor()) * Long.parseLong(arg2.getValor());
					ret = new Resultado(b.toString(), TipoResultado.LONG);
				}
				else if (arg1.getTipo() == TipoResultado.INTEGER && arg2.getTipo() == TipoResultado.FLOAT){
					Float b = Integer.parseInt(arg1.getValor()) * Float.parseFloat(arg2.getValor());
					ret = new Resultado(b.toString(), TipoResultado.FLOAT);
				}
				else if (arg1.getTipo() == TipoResultado.LONG && arg2.getTipo() == TipoResultado.INTEGER){
					Long b = Long.parseLong(arg1.getValor()) * Integer.parseInt(arg2.getValor());
					ret = new Resultado(b.toString(), TipoResultado.LONG);
				}
				else if (arg1.getTipo() == TipoResultado.LONG && arg2.getTipo() == TipoResultado.LONG){
					Long b = Long.parseLong(arg1.getValor()) * Long.parseLong(arg2.getValor());
					ret = new Resultado(b.toString(), TipoResultado.LONG);
				}
				else if (arg1.getTipo() == TipoResultado.LONG && arg2.getTipo() == TipoResultado.FLOAT){
					Float b = Long.parseLong(arg1.getValor()) * Float.parseFloat(arg2.getValor());
					ret = new Resultado(b.toString(), TipoResultado.FLOAT);
				}
				else if (arg1.getTipo() == TipoResultado.FLOAT && arg2.getTipo() == TipoResultado.INTEGER){
					Float b = Float.parseFloat(arg1.getValor()) * Integer.parseInt(arg2.getValor());
					ret = new Resultado(b.toString(), TipoResultado.FLOAT);
				}
				else if (arg1.getTipo() == TipoResultado.FLOAT && arg2.getTipo() == TipoResultado.LONG){
					Float b = Float.parseFloat(arg1.getValor()) * Long.parseLong(arg2.getValor());
					ret = new Resultado(b.toString(), TipoResultado.FLOAT);
				}
				else if (arg1.getTipo() == TipoResultado.FLOAT && arg2.getTipo() == TipoResultado.FLOAT){
					Float b = Float.parseFloat(arg1.getValor()) * Float.parseFloat(arg2.getValor());
					ret = new Resultado(b.toString(), TipoResultado.FLOAT);
				}
				else
					throw new ParsingException("Tipo de datos no valido linea: " + this.linea + " columna: " + this.col );
			}
			else if (this.value.toString() == "//"){
				if (arg1.getTipo() == TipoResultado.INTEGER && arg2.getTipo() == TipoResultado.INTEGER){
					Integer b = Integer.parseInt(arg1.getValor()) / Integer.parseInt(arg2.getValor());
					ret = new Resultado(b.toString(), TipoResultado.INTEGER);
				}
				else if (arg1.getTipo() == TipoResultado.INTEGER && arg2.getTipo() == TipoResultado.LONG){
					Long b = Integer.parseInt(arg1.getValor()) / Long.parseLong(arg2.getValor());
					ret = new Resultado(b.toString(), TipoResultado.LONG);
				}
				else if (arg1.getTipo() == TipoResultado.INTEGER && arg2.getTipo() == TipoResultado.FLOAT){
					Float b = Integer.parseInt(arg1.getValor()) / Float.parseFloat(arg2.getValor());
					ret = new Resultado(((Integer)b.intValue()).toString(), TipoResultado.INTEGER);
				}
				else if (arg1.getTipo() == TipoResultado.LONG && arg2.getTipo() == TipoResultado.INTEGER){
					Long b = Long.parseLong(arg1.getValor()) / Integer.parseInt(arg2.getValor());
					ret = new Resultado(b.toString(), TipoResultado.LONG);
				}
				else if (arg1.getTipo() == TipoResultado.LONG && arg2.getTipo() == TipoResultado.LONG){
					Long b = Long.parseLong(arg1.getValor()) / Long.parseLong(arg2.getValor());
					ret = new Resultado(b.toString(), TipoResultado.LONG);
				}
				else if (arg1.getTipo() == TipoResultado.LONG && arg2.getTipo() == TipoResultado.FLOAT){
					Float b = Long.parseLong(arg1.getValor()) / Float.parseFloat(arg2.getValor());
					ret = new Resultado(((Integer)b.intValue()).toString(), TipoResultado.INTEGER);
				}
				else if (arg1.getTipo() == TipoResultado.FLOAT && arg2.getTipo() == TipoResultado.INTEGER){
					Float b = Float.parseFloat(arg1.getValor()) / Integer.parseInt(arg2.getValor());
					ret = new Resultado(((Integer)b.intValue()).toString(), TipoResultado.INTEGER);
				}
				else if (arg1.getTipo() == TipoResultado.FLOAT && arg2.getTipo() == TipoResultado.LONG){
					Float b = Float.parseFloat(arg1.getValor()) / Long.parseLong(arg2.getValor());
					ret = new Resultado(((Integer)b.intValue()).toString(), TipoResultado.INTEGER);
				}
				else if (arg1.getTipo() == TipoResultado.FLOAT && arg2.getTipo() == TipoResultado.FLOAT){
					Float b = Float.parseFloat(arg1.getValor()) / Float.parseFloat(arg2.getValor());
					ret = new Resultado(((Integer)b.intValue()).toString(), TipoResultado.INTEGER);
				}
				else
					throw new ParsingException("Tipo de datos no valido linea: " + this.linea + " columna: " + this.col );
			}
			else if (this.value.toString() == "/"){
				if (arg1.getTipo() == TipoResultado.INTEGER && arg2.getTipo() == TipoResultado.INTEGER){
					float b = Integer.parseInt(arg1.getValor()) / Integer.parseInt(arg2.getValor());
					ret = new Resultado(((Float)b).toString(), TipoResultado.FLOAT);
				}
				else if (arg1.getTipo() == TipoResultado.INTEGER && arg2.getTipo() == TipoResultado.LONG){
					float b = Integer.parseInt(arg1.getValor()) / Long.parseLong(arg2.getValor());
					ret = new Resultado(((Float)b).toString(), TipoResultado.FLOAT);
				}
				else if (arg1.getTipo() == TipoResultado.INTEGER && arg2.getTipo() == TipoResultado.FLOAT){
					float b = Integer.parseInt(arg1.getValor()) / Float.parseFloat(arg2.getValor());
					ret = new Resultado(((Float)b).toString(), TipoResultado.FLOAT);
				}
				else if (arg1.getTipo() == TipoResultado.LONG && arg2.getTipo() == TipoResultado.INTEGER){
					float b = Long.parseLong(arg1.getValor()) / Integer.parseInt(arg2.getValor());
					ret = new Resultado(((Float)b).toString(), TipoResultado.FLOAT);
				}
				else if (arg1.getTipo() == TipoResultado.LONG && arg2.getTipo() == TipoResultado.LONG){
					float b = Long.parseLong(arg1.getValor()) / Long.parseLong(arg2.getValor());
					ret = new Resultado(((Float)b).toString(), TipoResultado.FLOAT);
				}
				else if (arg1.getTipo() == TipoResultado.LONG && arg2.getTipo() == TipoResultado.FLOAT){
					float b = Long.parseLong(arg1.getValor()) / Float.parseFloat(arg2.getValor());
					ret = new Resultado(((Float)b).toString(), TipoResultado.FLOAT);
				}
				else if (arg1.getTipo() == TipoResultado.FLOAT && arg2.getTipo() == TipoResultado.INTEGER){
					float b = Float.parseFloat(arg1.getValor()) / Integer.parseInt(arg2.getValor());
					ret = new Resultado(((Float)b).toString(), TipoResultado.FLOAT);
				}
				else if (arg1.getTipo() == TipoResultado.FLOAT && arg2.getTipo() == TipoResultado.LONG){
					float b = Float.parseFloat(arg1.getValor()) / Long.parseLong(arg2.getValor());
					ret = new Resultado(((Float)b).toString(), TipoResultado.FLOAT);
				}
				else if (arg1.getTipo() == TipoResultado.FLOAT && arg2.getTipo() == TipoResultado.FLOAT){
					float b = Float.parseFloat(arg1.getValor()) / Float.parseFloat(arg2.getValor());
					ret = new Resultado(((Float)b).toString(), TipoResultado.FLOAT);
				}
				else
					throw new ParsingException("Tipo de datos no valido linea: " + this.linea + " columna: " + this.col );
			}
			else if (this.value.toString() == "%"){
				if (arg1.getTipo() == TipoResultado.INTEGER && arg2.getTipo() == TipoResultado.INTEGER){
					Integer b = Integer.parseInt(arg1.getValor()) % Integer.parseInt(arg2.getValor());
					ret = new Resultado(b.toString(), TipoResultado.INTEGER);
				}
				else if (arg1.getTipo() == TipoResultado.INTEGER && arg2.getTipo() == TipoResultado.LONG){
					Long b = Integer.parseInt(arg1.getValor()) % Long.parseLong(arg2.getValor());
					ret = new Resultado(b.toString(), TipoResultado.LONG);
				}
				else if (arg1.getTipo() == TipoResultado.INTEGER && arg2.getTipo() == TipoResultado.FLOAT){
					Float b = Integer.parseInt(arg1.getValor()) % Float.parseFloat(arg2.getValor());
					ret = new Resultado(b.toString(), TipoResultado.FLOAT);
				}
				else if (arg1.getTipo() == TipoResultado.LONG && arg2.getTipo() == TipoResultado.INTEGER){
					Long b = Long.parseLong(arg1.getValor()) % Integer.parseInt(arg2.getValor());
					ret = new Resultado(b.toString(), TipoResultado.LONG);
				}
				else if (arg1.getTipo() == TipoResultado.LONG && arg2.getTipo() == TipoResultado.LONG){
					Long b = Long.parseLong(arg1.getValor()) % Long.parseLong(arg2.getValor());
					ret = new Resultado(b.toString(), TipoResultado.LONG);
				}
				else if (arg1.getTipo() == TipoResultado.LONG && arg2.getTipo() == TipoResultado.FLOAT){
					Float b = Long.parseLong(arg1.getValor()) % Float.parseFloat(arg2.getValor());
					ret = new Resultado(b.toString(), TipoResultado.FLOAT);
				}
				else if (arg1.getTipo() == TipoResultado.FLOAT && arg2.getTipo() == TipoResultado.INTEGER){
					Float b = Float.parseFloat(arg1.getValor()) % Integer.parseInt(arg2.getValor());
					ret = new Resultado(b.toString(), TipoResultado.FLOAT);
				}
				else if (arg1.getTipo() == TipoResultado.FLOAT && arg2.getTipo() == TipoResultado.LONG){
					Float b = Float.parseFloat(arg1.getValor()) % Long.parseLong(arg2.getValor());
					ret = new Resultado(b.toString(), TipoResultado.FLOAT);
				}
				else if (arg1.getTipo() == TipoResultado.FLOAT && arg2.getTipo() == TipoResultado.FLOAT){
					Float b = Float.parseFloat(arg1.getValor()) % Float.parseFloat(arg2.getValor());
					ret = new Resultado(b.toString(), TipoResultado.FLOAT);
				}
				else
					throw new ParsingException("Tipo de datos no valido linea: " + this.linea + " columna: " + this.col );
			}
			else if (this.value.toString() == "**"){
				if (arg1.getTipo() == TipoResultado.INTEGER && arg2.getTipo() == TipoResultado.INTEGER){
					Integer b = (int) Math.pow(Integer.parseInt(arg1.getValor()), Integer.parseInt(arg2.getValor()));
					ret = new Resultado(b.toString(), TipoResultado.INTEGER);
				}
				else if (arg1.getTipo() == TipoResultado.INTEGER && arg2.getTipo() == TipoResultado.LONG){
					Long b = (long) Math.pow(Integer.parseInt(arg1.getValor()) , Long.parseLong(arg2.getValor()));
					ret = new Resultado(b.toString(), TipoResultado.LONG);
				}
				else if (arg1.getTipo() == TipoResultado.INTEGER && arg2.getTipo() == TipoResultado.FLOAT){
					Float b = (float) Math.pow(Integer.parseInt(arg1.getValor()) , Float.parseFloat(arg2.getValor()));
					ret = new Resultado(b.toString(), TipoResultado.FLOAT);
				}
				else if (arg1.getTipo() == TipoResultado.LONG && arg2.getTipo() == TipoResultado.INTEGER){
					Long b = (long) Math.pow(Long.parseLong(arg1.getValor()) , Integer.parseInt(arg2.getValor()));
					ret = new Resultado(b.toString(), TipoResultado.LONG);
				}
				else if (arg1.getTipo() == TipoResultado.LONG && arg2.getTipo() == TipoResultado.LONG){
					Long b = (long) Math.pow(Long.parseLong(arg1.getValor()) , Long.parseLong(arg2.getValor()));
					ret = new Resultado(b.toString(), TipoResultado.LONG);
				}
				else if (arg1.getTipo() == TipoResultado.LONG && arg2.getTipo() == TipoResultado.FLOAT){
					Float b = (float) Math.pow(Long.parseLong(arg1.getValor()) , Float.parseFloat(arg2.getValor()));
					ret = new Resultado(b.toString(), TipoResultado.FLOAT);
				}
				else if (arg1.getTipo() == TipoResultado.FLOAT && arg2.getTipo() == TipoResultado.INTEGER){
					Float b = (float) Math.pow(Float.parseFloat(arg1.getValor()) , Integer.parseInt(arg2.getValor()));
					ret = new Resultado(b.toString(), TipoResultado.FLOAT);
				}
				else if (arg1.getTipo() == TipoResultado.FLOAT && arg2.getTipo() == TipoResultado.LONG){
					Float b = (float) Math.pow(Float.parseFloat(arg1.getValor()) , Long.parseLong(arg2.getValor()));
					ret = new Resultado(b.toString(), TipoResultado.FLOAT);
				}
				else if (arg1.getTipo() == TipoResultado.FLOAT && arg2.getTipo() == TipoResultado.FLOAT){
					Float b = (float) Math.pow(Float.parseFloat(arg1.getValor()) , Float.parseFloat(arg2.getValor()));
					ret = new Resultado(b.toString(), TipoResultado.FLOAT);
				}
				else
					throw new ParsingException("Tipo de datos no valido linea: " + this.linea + " columna: " + this.col );
			}
			
		}
		
		return ret;
	}
	
	public ArrayList<Expresion> getArguments(){
		return this.arguments;
	}
	
	public TipoExpresion getTipo(){
		return this.tipo;
	}

	public String getValor() {
		return this.value.toString();
	}
}
