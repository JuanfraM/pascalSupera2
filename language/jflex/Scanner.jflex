package com.language.parser;

import java.util.*;
import java_cup.runtime.*;
import com.language.exceptions.*;
import com.language.model.expression.*;

%%

%cup
%line
%unicode
%column

%class Scanner
%{
	private SymbolFactory sf;
	private StringBuffer string = new StringBuffer();

	int tabs = 0;
	int tabsAnterior = 0;

	public Scanner(java.io.InputStream r, SymbolFactory sf) {
		this(r);
		this.sf=sf;
	}

	private Symbol symbol(int type) {
		return new Symbol(type, yyline, yycolumn);
	}
	private Symbol symbol(int type, Object value) {
		return new Symbol(type, yyline, yycolumn, value);
	}
%}

%eofval{
    return symbol(sym.EOF);
%eofval}

LineTerminator = \r|\n|\r\n
InputCharacter = [^\r\n]
WhiteSpace     = {LineTerminator} | [ \t\f]

Comment = "#" {InputCharacter}* {LineTerminator}?

Identifier = [a-zA-Z][a-zA-Z0-9_]*

IntegerLiteral = 0 | [1-9][0-9]*
LongLiteral    = {IntegerLiteral} [lL]
FloatLiteral = (0 | [1-9][0-9]*)\.[0-9]+

%state STRING CSSTRING MLSTRING MLCSSTRING TAB

%%

<YYINITIAL>{

	"+" 				{ System.out.println("OPAMAS"); return symbol(sym.OPAMAS, "+"); }
	"-" 				{ System.out.println("OPAMENOS"); return symbol(sym.OPAMENOS, "-"); }
	"*" 				{ System.out.println("OPAPROD"); return symbol(sym.OPAPROD, "*"); }
	"**" 				{ System.out.println("OPAEXP"); return symbol(sym.OPAEXP, "**"); }
	"/" 				{ System.out.println("OPADIV"); return symbol(sym.OPADIV, "/"); }
	"//" 				{ System.out.println("OPADIVENTERA"); return symbol(sym.OPADIVENTERA, "//"); }
	"%" 				{ System.out.println("OPAMOD"); return symbol(sym.OPAMOD, "%"); }

	"&" 				{ System.out.println("OPBABDAND"); return symbol(sym.OPBABDAND, "&"); }
	"|" 				{ System.out.println("OPBABOR"); return symbol(sym.OPBABOR, "|"); }
	"^" 				{ System.out.println("OPBABXOR"); return symbol(sym.OPBABXOR, "^"); }
	"~" 				{ System.out.println("OPBABNOT"); return symbol(sym.OPBABNOT, "~"); }
	"<<" 				{ System.out.println("OPBABSHIFTL"); return symbol(sym.OPBABSHIFTL, "<<"); }
	">>" 				{ System.out.println("OPBABSHIFTR"); return symbol(sym.OPBABSHIFTR, ">>"); }

	"and" 				{ System.out.println("AND"); return symbol(sym.AND, "and"); }
	"or" 				{ System.out.println("OR"); return symbol(sym.OR, "or"); }
	"not" 				{ System.out.println("NOT"); return symbol(sym.NOT, "not"); }
	"==" 				{ System.out.println("OPBIGUAL"); return symbol(sym.OPBIGUAL, "=="); }
	"!=" 				{ System.out.println("OPBDISTINTO"); return symbol(sym.OPBDISTINTO, "!="); }
	"<" 				{ System.out.println("OPBMENOR"); return symbol(sym.OPBMENOR, "<"); }
	">" 				{ System.out.println("OPBMAYOR"); return symbol(sym.OPBMAYOR, ">"); }
	"<=" 				{ System.out.println("OPBMENORIGUAL"); return symbol(sym.OPBMENORIGUAL, "<="); }
	">=" 				{ System.out.println("OPBMAYORIGUAL"); return symbol(sym.OPBMAYORIGUAL, ">="); }

	"=" 				{ System.out.println("ASIG"); return symbol(sym.ASIG, "="); }
	"["					{ System.out.println("LCORCHETE"); return symbol(sym.LCORCHETE, "["); }
	"]"					{ System.out.println("RCORCHETE"); return symbol(sym.RCORCHETE, "]"); }
	"{"					{ System.out.println("LLLAVE"); return symbol(sym.LLLAVE, "{"); }
	"}"					{ System.out.println("RLLAVE"); return symbol(sym.RLLAVE, "}"); }
	"(" 				{ System.out.println("LPAREN"); return symbol(sym.LPAREN, "("); }
	")" 				{ System.out.println("RPAREN"); return symbol(sym.RPAREN, ")"); }
	"."					{ System.out.println("PUNTO"); return symbol(sym.PUNTO, "."); }
	","					{ System.out.println("COMA"); return symbol(sym.COMA, ","); }
	";"					{ System.out.println("PUNTOYCOMA"); return symbol(sym.PUNTOYCOMA, ";"); }
	":"					{ System.out.println("DOSPUNTOS"); return symbol(sym.DOSPUNTOS, ":"); }

	True				{ System.out.println("TRUE"); return symbol(sym.TRUE , "True" ); }
	False				{ System.out.println("FALSE"); return symbol(sym.FALSE, "False"); }

	if					{ System.out.println("IF"); return symbol(sym.IF, "if"); }
	else				{ System.out.println("ELSE"); return symbol(sym.ELSE, "else"); }
	while				{ System.out.println("WHILE"); return symbol(sym.WHILE, "while"); }
	break				{ System.out.println("BREAK"); return symbol(sym.BREAK, "break"); }
	continue			{ System.out.println("CONTINUE"); return symbol(sym.CONTINUE, "continue"); }
	for					{ System.out.println("FOR"); return symbol(sym.FOR, "for"); }
	def					{ System.out.println("DEF"); return symbol(sym.DEF, "def"); }
	in					{ System.out.println("IN"); return symbol(sym.IN, "in"); }

	has_key				{ System.out.println("DATAFUNCION"); return symbol(sym.DATAFUNCION, "has_key"); }
	items				{ System.out.println("DATAFUNCION"); return symbol(sym.DATAFUNCION, "items"); }
	keys				{ System.out.println("DATAFUNCION"); return symbol(sym.DATAFUNCION, "keys"); }
	pop					{ System.out.println("DATAFUNCION"); return symbol(sym.DATAFUNCION, "pop"); }
	values				{ System.out.println("DATAFUNCION"); return symbol(sym.DATAFUNCION, "has_key"); }
	count				{ System.out.println("DATAFUNCION"); return symbol(sym.DATAFUNCION, "count"); }
	find				{ System.out.println("DATAFUNCION"); return symbol(sym.DATAFUNCION, "find"); }
	join				{ System.out.println("DATAFUNCION"); return symbol(sym.DATAFUNCION, "join"); }
	split				{ System.out.println("DATAFUNCION"); return symbol(sym.DATAFUNCION, "split"); }
	replace				{ System.out.println("DATAFUNCION"); return symbol(sym.DATAFUNCION, "replace"); }
	length				{ System.out.println("DATAFUNCION"); return symbol(sym.DATAFUNCION, "length"); }
	append				{ System.out.println("DATAFUNCION"); return symbol(sym.DATAFUNCION, "append"); }
	extend				{ System.out.println("DATAFUNCION"); return symbol(sym.DATAFUNCION, "extend"); }
	index				{ System.out.println("DATAFUNCION"); return symbol(sym.DATAFUNCION, "index"); }
	insert				{ System.out.println("DATAFUNCION"); return symbol(sym.DATAFUNCION, "insert"); }
	size				{ System.out.println("DATAFUNCION"); return symbol(sym.DATAFUNCION, "size"); }

	print				{ System.out.println("FUNCION"); return symbol(sym.FUNCION, "print"); }
	raw_input			{ System.out.println("FUNCION"); return symbol(sym.FUNCION, "raw_input"); }
	int					{ System.out.println("FUNCION"); return symbol(sym.FUNCION, "int"); }
	float				{ System.out.println("FUNCION"); return symbol(sym.FUNCION, "float"); }
	str					{ System.out.println("FUNCION"); return symbol(sym.FUNCION, "str"); }
	tuple				{ System.out.println("FUNCION"); return symbol(sym.FUNCION, "tuple"); }
	list				{ System.out.println("FUNCION"); return symbol(sym.FUNCION, "list"); }
	dict				{ System.out.println("FUNCION"); return symbol(sym.FUNCION, "dict"); }

	{Identifier}		{ System.out.println("ID: " + yytext()); return symbol(sym.ID, yytext()); }

	\"                 	{ string.setLength(0); yybegin(STRING); }

	\'                 	{ string.setLength(0); yybegin(CSSTRING); }

	\"\"\"             	{ string.setLength(0); yybegin(MLSTRING); }

	\'\'\'              { string.setLength(0); yybegin(MLCSSTRING); }

	{IntegerLiteral}	{ System.out.println("INTEGER: " + yytext()); return symbol(sym.INTEGER, yytext()); }

	{LongLiteral}		{ System.out.println("LONG: " + yytext()); return symbol(sym.LONG, yytext()); }

	{FloatLiteral} 		{ System.out.println("FLOAT: " + yytext()); return symbol(sym.FLOAT, yytext()); }

	{LineTerminator}/[\t]					{ tabs = 0; yybegin(TAB); }

	{LineTerminator}/[a-zA-Z0-9]			{
												tabs = 0;
												if (tabs < tabsAnterior) {
													int contador = tabsAnterior - tabs;
													tabsAnterior = tabs;
													for (int i = 0; i < contador; i++)
														System.out.println("NOTAB"); return symbol(sym.NOTAB);
												}
												else if (tabs > tabsAnterior){
													tabsAnterior = tabs;
													System.out.println("TAB"); return symbol(sym.TAB);
												}
											}

	{Comment}        	{ /* ignore */ }

	{WhiteSpace}	    { /* ignore */ }

}

<STRING>{

	\"                	{
							yybegin(YYINITIAL);
							System.out.println("STRING: "  + string.toString()); return symbol(sym.STRING, string.toString());
						}

	[^\n\r\"\\]+       	{ string.append( yytext() ); }

	\\t               	{ string.append('\t'); }

	\\n               	{ string.append('\n'); }

	\\r               	{ string.append('\r'); }

	\\\"              	{ string.append('\"'); }

	\\                	{ string.append('\\'); }

}

<CSSTRING>{

	\'                	{
							yybegin(YYINITIAL);
							System.out.println("STRING: "  + string.toString()); return symbol(sym.STRING, string.toString());
						}

	[^\n\r\'\\]+       	{ string.append( yytext() ); }

	\\t               	{ string.append('\t'); }

	\\n               	{ string.append('\n'); }

	\\r               	{ string.append('\r'); }

	\\\'              	{ string.append('\''); }

	\\                	{ string.append('\\'); }

}

<MLSTRING>{
	\"\"\"              {
							yybegin(YYINITIAL);
							System.out.println("STRING: "  + string.toString()); return symbol(sym.STRING, string.toString());
						}

	[^\n\r\"\\]+       	{ string.append( yytext() ); }

	\t               	{ string.append( yytext() ); }

	\n               	{ string.append( yytext() ); }

	\r               	{ string.append( yytext() ); }

	\"              	{ string.append( yytext() ); }

	\                	{ string.append( yytext() ); }
}

<MLCSSTRING>{
	\'\'\'              {
							yybegin(YYINITIAL);
							System.out.println("STRING: "  + string.toString()); return symbol(sym.STRING, string.toString());
						}

	[^\n\r\'\\]+       	{ string.append( yytext() ); }

	\t               	{ string.append( yytext() ); }

	\n               	{ string.append( yytext() ); }

	\r               	{ string.append( yytext() ); }

	\'              	{ string.append( yytext() ); }

	\                	{ string.append( yytext() ); }
}

<TAB>{

	\t								{ tabs++; }

	\t/[a-zA-Z0-9]					{
										yybegin(YYINITIAL);
										tabs++;
										if (tabs < tabsAnterior) {
											int contador = tabsAnterior - tabs;
											tabsAnterior = tabs;
											for (int i = 0; i < contador; i++)
												System.out.println("NOTAB"); return symbol(sym.NOTAB);
										}
										else if (tabs > tabsAnterior){
											tabsAnterior = tabs;
											System.out.println("TAB"); return symbol(sym.TAB);
										}

									}

	\t/[ \r\n(\r\n)\f]				{ yybegin(YYINITIAL); }
}
. 					{
						throw new ParsingException("Illegal character at line " + yyline + ", column " + yycolumn + " >> " + yytext());
					}
