package com.language.model.expression;

import java.util.ArrayList;
import java.util.Map;

import com.language.Scope;

public class For extends Sentencia{
	
	private Object iterador; 
	private ArrayList<Expresion> lista;
	private ArrayList<Sentencia> sentencias;
	
	public For(Object iterador, ArrayList<Expresion> lista,ArrayList<Sentencia> sentencias) {
		super();
		this.iterador = iterador;
		this.lista = lista;
		this.sentencias = sentencias;
		
	}
//	
//	@Override
//	public String toString() {
//		String res = null;				
//		String bloque ="";
//		for(Sentencia s : this.sentencias){
//			bloque = bloque + s.toString();
//		}	
//		res = "For\n" + "inicializacion: " + this.inicializacion.toString()
//			 +"condicion: " + this.condicion.toString() +  "actualizacion: "+ this.actualizacion.toString()
//			 +"\nBloque: "+ bloque + "\n";			
//		return res;
//	}
//
//	@Override
//	public Resultado ejecutar() throws ParsingException, Exception {
//		/** INICIALIZACION  */
//		if(this.inicializacion.getExp().getTipo() != tipoExpresion.NULL){
//			if(this.inicializacion.isVarDef()){
//				this.scope_local.addVariable(this.inicializacion);
//			}
//			this.inicializacion.ejecutar();
//		}
//		/** CONDICION NO NULA*/
//		if(this.condicion.getTipo() != tipoExpresion.NULL) {
//			Resultado res_cond = this.condicion.evaluar();
//			if(res_cond.getTipo() == tipoExpresion.BOOLEAN){
//				
//				/** ACTUALIZACION NO NULA */
//				if(this.actualizacion.getExp().getTipo() != tipoExpresion.NULL) {
//					if(this.actualizacion.isVarDef()){
//						this.scope_local.addVariable(this.actualizacion);
//					}
//					this.actualizacion.ejecutar();
//						
//					/** SIMULACION FOR */
//					boolean exp = ((tipoBoolean)res_cond).getValue();
//					while(exp){
//						try{
//							this.ejecutarSentencias(this.sentencias);
//							exp = ((tipoBoolean)this.condicion.evaluar()).getValue();
//							if(exp)
//								this.actualizacion.ejecutar();
//						}catch (ParsingException e){
//							if(e.getSentenciaType() == tipoSentencia.BREAK ){
//								exp = false;
//							}else if(e.getSentenciaType() == tipoSentencia.CONTINUE){
//								this.actualizacion.ejecutar();
//							}else{
//								throw e;
//							}
//						}
//					}
//				}
//				else{ /**ACTUALIZACION NULA*/
//					boolean exp = ((tipoBoolean)res_cond).getValue();
//					while(exp){
//						try{
//							this.ejecutarSentencias(this.sentencias);
//							exp = ((tipoBoolean)this.condicion.evaluar()).getValue();
//						}catch (ParsingException e){
//							if(e.getSentenciaType() == tipoSentencia.BREAK ){
//								exp = false;
//							}else if(e.getSentenciaType() == tipoSentencia.CONTINUE){
//								/**no hago nada, la actualizacion es nula*/
//							}else{
//								throw e;
//							}
//						}
//					}
//				}
//			/** ERROR SEMANTICO */
//			}else{
//				throw new ParsingException(ParsingException.FOR_EXP+condicion.getPosicion());
//			}
//		}
//		else{ /**CONDICION NULA*/
//			/**ACTUALIZACION NO NULA*/
//			if(this.actualizacion.getExp().getTipo() != tipoExpresion.NULL) {
//				if(this.actualizacion.isVarDef()){
//					this.scope_local.addVariable(this.actualizacion);
//				}
//				this.actualizacion.ejecutar();
//					
//				/** SIMULACION FOR */
//				boolean cond = true;/**for infinito, solo lo para el break*/
//				while(cond){
//					try{
//						this.ejecutarSentencias(this.sentencias);
//						this.actualizacion.ejecutar();
//					}catch (ParsingException e){
//						if(e.getSentenciaType() == tipoSentencia.BREAK ){
//							cond = false;
//						}else if(e.getSentenciaType() == tipoSentencia.CONTINUE){
//							this.actualizacion.ejecutar();
//						}else{
//							throw e;
//						}
//					}
//				}
//			} /**ACTUALIZACION NULA*/
//			else{
//				boolean cond = true;/**for infinito, solo lo para un break*/
//				while(cond){
//					try{
//						this.ejecutarSentencias(this.sentencias);
//					}catch (ParsingException e){
//						if(e.getSentenciaType() == tipoSentencia.BREAK ){
//							cond = false;
//						}else if(e.getSentenciaType() == tipoSentencia.CONTINUE){
//							/**no hago nada, la actualizacion es nula*/
//						}else{
//							throw e;
//						}
//					}
//				}
//			}
//		}
//			
//		return null;
//	}

	@Override
	public Resultado ejecutar(Scope variables, Map<String, FuncionDef> Funciones) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getValor() {
		// TODO Auto-generated method stub
		return null;
	}

}
