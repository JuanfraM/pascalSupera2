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
	private ArrayList<Expresion> arguments;
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
	
	public FuncionesPredefinidas(Object value,Object id_variable ,Expresion arg, int linea, int col){
		super(value, TipoExpresion.FUNCTION_PREDEF, linea, col);
		this.value = value;
		this.linea = linea;
		this.id_variable = id_variable;
		this.col = col;
		if(arg==null){
			this.arguments = new ArrayList<Expresion>();
		}
		else if(arg!=null && arg.getArguments()==null){
			this.arguments = new ArrayList<Expresion>();
			this.arguments.add(arg);
		}
		else
			this.arguments = arg.getArguments();
		this.lugar = " en la linea "+linea+" y columna "+col;
	}
	
	public FuncionesPredefinidas(Object value, Expresion arg, int linea, int col){
		super(value, TipoExpresion.FUNCTION_PREDEF, linea, col);
		this.value = value;
		this.linea = linea;
		this.col = col;
		this.arguments = new ArrayList<Expresion>();
		this.arguments.add(arg);
		this.lugar = " en la linea "+linea+" y columna "+col;
	}
	
	public FuncionesPredefinidas(Object value, Expresion arg1, Expresion arg2, int linea, int col){
		super(value, TipoExpresion.FUNCTION_PREDEF, linea, col);
		this.value = value;
		this.linea = linea;
		this.col = col;
		this.arguments = new ArrayList<Expresion>();
		this.arguments.add(arg1);
		this.arguments.add(arg2);
		this.lugar = " en la linea "+linea+" y columna "+col;
	}

	public FuncionesPredefinidas(Object value, ArrayList<Expresion> arg, int linea, int col){
		super(value, TipoExpresion.FUNCTION_PREDEF, linea, col);
		this.value = value;
		this.linea = linea;
		this.col = col;
		if (arg == null)
			this.arguments = new ArrayList<Expresion>();
		this.arguments = arg;
		this.lugar = " en la linea "+linea+" y columna "+col;
	}
	
	public Resultado ejecutar(Scope variables, Map<String,FuncionDef> Funciones, boolean loop)throws ParsingException {
		Resultado ret = new Resultado();
		
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
		}//devuelve el tipo de dato ingresado
		else if (this.value == "type"){			
			
			Expresion e = (Expresion) this.arguments.get(0);
			Resultado aux = e.ejecutar(variables, Funciones, loop);
			String resultado;
			switch (aux.getTipo()){
			
				case INTEGER:
					resultado = "<type 'int'>";
					ret = new Resultado(resultado, TipoResultado.TYPE);
					break;
				case FLOAT:
					resultado = "<type 'float'>";
					ret = new Resultado(resultado, TipoResultado.TYPE);
					break;
				case LONG:
					resultado = "<type 'long'>";
					ret = new Resultado(resultado, TipoResultado.TYPE);
					break;
				case STRING:
					resultado = "<type 'string'>";
					ret = new Resultado(resultado, TipoResultado.TYPE);
					break;
				case TUPLA:
					resultado = "<type 'tupla'>";
					ret = new Resultado(resultado, TipoResultado.TYPE);
					break;
				case LIST:
					resultado = "<type 'list'>";
					ret = new Resultado(resultado, TipoResultado.TYPE);
					break;
				case BOOL:
					resultado = "<type 'bool'>";
					ret = new Resultado(resultado, TipoResultado.TYPE);
					break;
				case DICT:
					resultado = "<type 'diccionario'>";
					ret = new Resultado(resultado, TipoResultado.TYPE);
					break;
				case TYPE:
					resultado = "<type 'type'>";
					ret = new Resultado(resultado, TipoResultado.TYPE);
					break;
				default:
					throw new ParsingException(ParsingException.FUNC_PREDEF_TYPE+this.lugar);
					
			}
		}//str(X) convierte el valor X a string
		else if (this.value == "str"){			
			
			Expresion e = (Expresion) this.arguments.get(0);
			Resultado aux = e.ejecutar(variables, Funciones, loop);
			
			String resultado;
			switch (aux.getTipo()){
			
				case INTEGER:
					resultado = aux.getValor();
					ret = new Resultado(resultado, TipoResultado.STRING);
					break;
				case FLOAT:
					resultado = aux.getValor();
					ret = new Resultado(resultado, TipoResultado.STRING);
					break;
				case LONG:
					resultado = aux.getValor();
					ret = new Resultado(resultado, TipoResultado.STRING);
					break;
				case STRING:
					resultado = aux.getValor();
					ret = new Resultado(resultado, TipoResultado.STRING);
					break;
				case TUPLA:
					resultado = aux.toString();
					ret = new Resultado(resultado, TipoResultado.STRING);
					break;
				case LIST:
					resultado = aux.toString();
					ret = new Resultado(resultado, TipoResultado.STRING);
					break;
				case BOOL:
					resultado = aux.getValor();
					ret = new Resultado(resultado, TipoResultado.STRING);
					break;
				case DICT:
					resultado = aux.toString();
					ret = new Resultado(resultado, TipoResultado.STRING);
					break;
				default:
					throw new ParsingException(ParsingException.FUNC_PREDEF_STR+this.lugar);
					
			}
		}
		//int(X) convierte el valor X a int
		else if (this.value == "int"){			
			
			Expresion e = (Expresion) this.arguments.get(0);
			Resultado aux = e.ejecutar(variables, Funciones, loop);
			Integer i;
			String resultado;
			switch (aux.getTipo()){
			
				case INTEGER:
					resultado = aux.getValor();
					ret = new Resultado(resultado, TipoResultado.INTEGER);
					break;
				case FLOAT:
					resultado = aux.getValor();
					Float f = Float.valueOf(resultado);
					i = f.intValue();
					ret = new Resultado(i.toString(), TipoResultado.INTEGER);
					break;
				case LONG:
					resultado = aux.getValor();
					Long l = Long.valueOf(resultado);
					i = l.intValue();
					ret = new Resultado(i.toString(), TipoResultado.INTEGER);
					break;
				case STRING:
					resultado = aux.getValor();
					try {i = Integer.valueOf(resultado);}
					catch(NumberFormatException p){
						throw new ParsingException(ParsingException.FUNC_PREDEF_INT1+this.lugar);
					}
					ret = new Resultado(resultado, TipoResultado.INTEGER);
					break;						
				case BOOL:
					resultado = aux.getValor();
					if(resultado.equals("True"))
						resultado = "1";
					else
						resultado="0";
					ret = new Resultado(resultado, TipoResultado.INTEGER);
					break;						
				default:
					throw new ParsingException(ParsingException.FUNC_PREDEF_INT2+this.lugar);
					
			}
		}
		else if(this.value=="length"){
			Resultado variable = variables.get(this.id_variable.toString());
			if((variable.getTipo()==TipoResultado.STRING)&&this.arguments.size()==0){	
				String largo = Integer.toString(variable.getValor().length());				
				ret = new Resultado(largo,TipoResultado.INTEGER);
			}
			else{
				throw new ParsingException(ParsingException.FUNC_PREDEF_LENGTH+this.lugar);
			}
		}
		
		//cuenta las ocurrencias de la subcadena en el string pasado o
		// cuenta las ocurrencias del elemento pasado en la lista
		else if (this.value == "count"){
			Resultado variable = variables.get(this.id_variable.toString());
			if(this.arguments.size()!=1)
				throw new ParsingException(ParsingException.FUNC_PREDEF_COUNT1+this.lugar);
			
			Expresion e = (Expresion)this.arguments.get(0);
			Resultado elemento = e.ejecutar(variables, Funciones, loop);	
			
			if(variable.getTipo()==TipoResultado.STRING){	
				if(elemento.getTipo()!=TipoResultado.STRING)
					throw new ParsingException(ParsingException.FUNC_PREDEF_COUNT2+this.lugar);
				String subcadena = elemento.getValor().toString();
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
				elemento = e.ejecutar(variables, Funciones, loop);	
				ArrayList<Resultado> lista= variable.getValores();
				int count = 0;
				for(Resultado r: lista){
					if (r.equals(elemento))
						count++;
				}				
				String largo = Integer.toString(count);
				ret = new Resultado(largo,TipoResultado.INTEGER);
			}
			else { 
				throw new ParsingException(ParsingException.FUNC_PREDEF_COUNT+this.lugar);
			}
		}
		//localiza la subcadena en el string pasado
		// localiza la subcadena en el string pasado a partir de una direccion
		else if (this.value == "find"){
			Resultado v = variables.get(this.id_variable.toString());
			if(v.getTipo()!=TipoResultado.STRING)
				throw new ParsingException(ParsingException.FUNC_PREDEF_FIND1+this.lugar);					
			
			String variable = v.getValor();
			
			//s.find(substring)
			if(this.arguments.size()==1){
				Expresion e = (Expresion)this.arguments.get(0);
				Resultado elemento = e.ejecutar(variables, Funciones, loop);
				if(elemento.getTipo()!=TipoResultado.STRING)
					throw new ParsingException(ParsingException.FUNC_PREDEF_FIND2+this.lugar);
				
				String subcadena = elemento.getValor();
				int index = variable.indexOf(subcadena);
				ret = new Resultado(Integer.toString(index),TipoResultado.INTEGER);
			}//s.find(substring,start)								
			else if(this.arguments.size()==2){
				//substring
				Expresion e = (Expresion)this.arguments.get(0);
				Resultado elemento = e.ejecutar(variables, Funciones, loop);
				if(elemento.getTipo()!=TipoResultado.STRING)
					throw new ParsingException(ParsingException.FUNC_PREDEF_FIND2+this.lugar);
				
				String subcadena = elemento.getValor();
				
				//start
				Expresion e1 = (Expresion)this.arguments.get(1);
				Resultado st = e1.ejecutar(variables, Funciones, loop);
				if(st.getTipo()!=TipoResultado.INTEGER)
					throw new ParsingException(ParsingException.FUNC_PREDEF_FIND3+this.lugar);
				
				int start = Integer.valueOf(st.getValor());
				
				int index = variable.indexOf(subcadena,start);
				ret = new Resultado(Integer.toString(index),TipoResultado.INTEGER);
			}				
			else { 
				throw new ParsingException(ParsingException.FUNC_PREDEF_FIND+this.lugar);
			}
		}		
		// devuelve una lista de cadenas, resultado de separar la cadena con el separador ingresado
		else if (this.value == "split"){
			Resultado v = variables.get(this.id_variable.toString());
			if(v.getTipo()!=TipoResultado.STRING)
				throw new ParsingException(ParsingException.FUNC_PREDEF_FIND1+this.lugar);					
			
			String variable = v.getValor();
			
			//s.find(substring)
			if(this.arguments.size()==1){
				Expresion e = (Expresion)this.arguments.get(0);
				Resultado elemento = e.ejecutar(variables, Funciones, loop);
				if(elemento.getTipo()!=TipoResultado.STRING)
					throw new ParsingException(ParsingException.FUNC_PREDEF_SPLIT1+this.lugar);
				
				String subcadena = elemento.getValor();
				String[] lista= variable.split(subcadena);
				ArrayList<Resultado> l = new ArrayList<Resultado>();
				Resultado aux;
				for(int i=0;i< lista.length;i++){
					aux= new Resultado(lista[i],TipoResultado.STRING);
					l.add(aux);
				}
				ret = new Resultado(l,TipoResultado.LIST);
			}			
			else { 
				throw new ParsingException(ParsingException.FUNC_PREDEF_SPLIT2+this.lugar);
			}
		}
		// quita el elemento de la lista o el diccionario
		else if (this.value == "pop"){
			Resultado v = variables.getScopeLocal(this.id_variable.toString());
			if((v.getTipo()!=TipoResultado.LIST)&&(v.getTipo()!=TipoResultado.DICT))
				throw new ParsingException(ParsingException.FUNC_PREDEF_POP1+this.lugar);	
	
			//s.pop(string)
			if(this.arguments.size()==1){
				Expresion e = (Expresion)this.arguments.get(0);
				Resultado elemento = e.ejecutar(variables, Funciones, loop);
				
				Resultado variable;
				if(v.getTipo()==TipoResultado.LIST){
					if(elemento.getTipo()!=TipoResultado.INTEGER)
						throw new ParsingException(ParsingException.FUNC_PREDEF_POP2+this.lugar);
					
					int index = Integer.parseInt(elemento.getValor());
					v.getValores().remove(index);
				}
				//Es diccionario
				else {
					
				}
				ret = new Resultado();
			
			}			
			else { 
				throw new ParsingException(ParsingException.FUNC_PREDEF_SPLIT2+this.lugar);
			}
		}
		
		//GET obtiene el elemento de una lista 
		else if(this.value=="get"){
			Resultado var = this.arguments.get(0).ejecutar(variables, Funciones, loop);
			if (var.getTipo() == TipoResultado.LIST || var.getTipo() == TipoResultado.TUPLA){
				if (this.arguments.size() == 2){
					Resultado arg = this.arguments.get(1).ejecutar(variables, Funciones, loop);
					if (arg.getTipo() != TipoResultado.INTEGER && arg.getTipo() != TipoResultado.NONE )
						throw new ParsingException(ParsingException.FUNC_PREDEF_GET2+this.lugar);
					
					if (arg.getTipo() == TipoResultado.NONE)
						ret = var;
					else{
						int index = Integer.parseInt(arg.getValor());
						int size = var.getValores().size();
						
						if (!(index < size))
							throw new ParsingException(ParsingException.FUNC_PREDEF_GET4+this.lugar);
						
						if (index >= 0)
							ret = var.getValores().get(index);
						else
							ret = var.getValores().get(size + index);
					}
					
					
				}
				else if (this.arguments.size() == 3){
					Resultado arg1 = this.arguments.get(1).ejecutar(variables, Funciones, loop);
					Resultado arg2 = this.arguments.get(2).ejecutar(variables, Funciones, loop);
					if (arg1.getTipo() != TipoResultado.INTEGER && arg1.getTipo() != TipoResultado.NONE ||
						arg2.getTipo() != TipoResultado.INTEGER && arg2.getTipo() != TipoResultado.NONE	)
						throw new ParsingException(ParsingException.FUNC_PREDEF_GET2+this.lugar);
					
					int inicio = 0;
					int fin = var.getValores().size(); 
					if (arg1.getTipo() == TipoResultado.INTEGER)
						inicio = Integer.parseInt(arg1.getValor());
					if (arg2.getTipo() == TipoResultado.INTEGER)
						fin = Integer.parseInt(arg2.getValor());
					
					int size = var.getValores().size();
					if (fin < 0 && fin < 0){
						int finAux = size + fin;
						int inicioAux = size + inicio;
						inicio = finAux;
						fin = inicioAux;
					}
						
					if (!(fin <= size && inicio < size))
						throw new ParsingException(ParsingException.FUNC_PREDEF_GET4+this.lugar);
					
					if (inicio > fin)
						throw new ParsingException(ParsingException.FUNC_PREDEF_GET3+this.lugar);
					
					ArrayList<Resultado> retList = new ArrayList<Resultado>();
					for (Resultado r : var.getValores().subList(inicio, fin)){
						retList.add(r);
					}
					
					ret = new Resultado(retList, var.getTipo());

					
				}
				else {
					Resultado arg1 = this.arguments.get(1).ejecutar(variables, Funciones, loop);
					Resultado arg2 = this.arguments.get(2).ejecutar(variables, Funciones, loop);
					Resultado arg3 = this.arguments.get(3).ejecutar(variables, Funciones, loop);
					if (arg1.getTipo() != TipoResultado.INTEGER && arg1.getTipo() != TipoResultado.NONE ||
						arg2.getTipo() != TipoResultado.INTEGER && arg2.getTipo() != TipoResultado.NONE	||
						arg3.getTipo() != TipoResultado.INTEGER && arg3.getTipo() != TipoResultado.NONE)
							throw new ParsingException(ParsingException.FUNC_PREDEF_GET2+this.lugar);
					
					int inicio = 0;
					int fin = var.getValores().size(); 
					int salto = 1;
					if (arg1.getTipo() == TipoResultado.INTEGER)
						inicio = Integer.parseInt(arg1.getValor());
					if (arg2.getTipo() == TipoResultado.INTEGER)
						fin = Integer.parseInt(arg2.getValor());
					if (arg3.getTipo() == TipoResultado.INTEGER)
						salto = Integer.parseInt(arg3.getValor());
					
					int size = var.getValores().size();
					if (fin < 0 && fin < 0){
						int finAux = size + fin;
						int inicioAux = size + inicio;
						inicio = finAux;
						fin = inicioAux;
					}
					
					if (!(fin <= size && inicio < size))
						throw new ParsingException(ParsingException.FUNC_PREDEF_GET4+this.lugar);
							
					if (inicio > fin)
						throw new ParsingException(ParsingException.FUNC_PREDEF_GET3+this.lugar);
						
					ArrayList<Resultado> retList = new ArrayList<Resultado>();
					int contador = 1;
					for(Resultado r : var.getValores().subList(inicio, fin)){
						if (contador == salto){
							contador = 0;
							retList.add(r);
						}
						contador++;
					}	
					ret = new Resultado(retList, var.getTipo());
					
				}
			}
			else if (var.getTipo() == TipoResultado.DICT){
				if (this.arguments.size() == 2){
					Resultado arg = this.arguments.get(1).ejecutar(variables, Funciones, loop);
					if (arg.getTipo() == TipoResultado.STRING){
						for(Resultado r : var.getValores()){
							if (r.getValores().get(0).equals(arg)){
								ret = r.getValores().get(1);
								break;
							}
						}	
					}
					else
						throw new ParsingException(ParsingException.FUNC_PREDEF_GET6+this.lugar);
				}
				else 
					throw new ParsingException(ParsingException.FUNC_PREDEF_GET5+this.lugar);
			}
			else{
				throw new ParsingException(ParsingException.FUNC_PREDEF_GET1+this.lugar);
			}
		}
		
		else if(this.value=="set"){
			if (this.arguments.size() != 2)
				throw new ParsingException(ParsingException.FUNC_PREDEF_SET2+this.lugar);
			Resultado var = this.arguments.get(0).ejecutar(variables, Funciones, loop);
			Resultado arg = this.arguments.get(1).ejecutar(variables, Funciones, loop);
			if (var.getTipo() == TipoResultado.LIST){
				if (arg.getTipo() != TipoResultado.INTEGER)
					throw new ParsingException(ParsingException.FUNC_PREDEF_SET3+this.lugar);
			}
			else if (var.getTipo() == TipoResultado.DICT){
				if (arg.getTipo() != TipoResultado.STRING)
					throw new ParsingException(ParsingException.FUNC_PREDEF_SET3+this.lugar);
			}
			else{
				throw new ParsingException(ParsingException.FUNC_PREDEF_SET1+this.lugar);
			}
		}

		return ret;
	}

	public String getValor() {
		return this.value.toString();
	}

}
