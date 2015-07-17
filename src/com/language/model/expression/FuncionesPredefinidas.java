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
			if((variable.getTipo()==TipoResultado.STRING)&&this.arguments.size()==0){	
				String largo = Integer.toString(variable.getValor().length());				
				ret = new Resultado(largo,TipoResultado.INTEGER);
			}
			else{
				throw new ParsingException(ParsingException.FUNC_PREDEF_LENGTH+this.lugar);
			}
		}//cuenta las ocurrencias de la subcadena en el string pasado o
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

		return ret;
	}

	public String getValor() {
		return this.value.toString();
	}

}
