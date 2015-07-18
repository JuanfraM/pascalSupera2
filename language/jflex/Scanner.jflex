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
	boolean salto = false;

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
        if (!salto){
        	salto = true;
            return symbol(sym.NEWLINE);
        }
        if (tabsAnterior>0){
            tabsAnterior--;
            return symbol(sym.NOTAB);
        }
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

	"+" 				{ return symbol(sym.OPAMAS, "+"); }
	"-" 				{ return symbol(sym.OPAMENOS, "-"); }
	"*" 				{ return symbol(sym.OPAPROD, "*"); }
	"**" 				{ return symbol(sym.OPAEXP, "**"); }
	"/" 				{ return symbol(sym.OPADIV, "/"); }
	"//" 				{ return symbol(sym.OPADIVENTERA, "//"); }
	"%" 				{ return symbol(sym.OPAMOD, "%"); }

	"&" 				{ return symbol(sym.OPBABDAND, "&"); }
	"|" 				{ return symbol(sym.OPBABOR, "|"); }
	"^" 				{ return symbol(sym.OPBABXOR, "^"); }
	"~" 				{ return symbol(sym.OPBABNOT, "~"); }
	"<<" 				{ return symbol(sym.OPBABSHIFTL, "<<"); }
	">>" 				{ return symbol(sym.OPBABSHIFTR, ">>"); }

	"and" 				{ return symbol(sym.AND, "and"); }
	"or" 				{ return symbol(sym.OR, "or"); }
	"not" 				{ return symbol(sym.NOT, "not"); }
	"==" 				{ return symbol(sym.OPBIGUAL, "=="); }
	"!=" 				{ return symbol(sym.OPBDISTINTO, "!="); }
	"<" 				{ return symbol(sym.OPBMENOR, "<"); }
	">" 				{ return symbol(sym.OPBMAYOR, ">"); }
	"<=" 				{ return symbol(sym.OPBMENORIGUAL, "<="); }
	">=" 				{ return symbol(sym.OPBMAYORIGUAL, ">="); }

	"=" 				{ return symbol(sym.ASIG, "="); }
	"["					{ return symbol(sym.LCORCHETE, "["); }
	"]"					{ return symbol(sym.RCORCHETE, "]"); }
	"{"					{ return symbol(sym.LLLAVE, "{"); }
	"}"					{ return symbol(sym.RLLAVE, "}"); }
	"(" 				{ return symbol(sym.LPAREN, "("); }
	")" 				{ return symbol(sym.RPAREN, ")"); }
	"."					{ return symbol(sym.PUNTO, "."); }
	","					{ return symbol(sym.COMA, ","); }
	";"					{ return symbol(sym.PUNTOYCOMA, ";"); }
	":"					{ return symbol(sym.DOSPUNTOS, ":"); }

	True				{ return symbol(sym.TRUE , "True" ); }
	False				{ return symbol(sym.FALSE, "False"); }
	None				{ return symbol(sym.NONE, "None"); }

	if					{ return symbol(sym.IF, "if"); }
	else				{ return symbol(sym.ELSE, "else"); }
	while				{ return symbol(sym.WHILE, "while"); }
	break				{ return symbol(sym.BREAK, "break"); }
	continue			{ return symbol(sym.CONTINUE, "continue"); }
	return				{ return symbol(sym.RETURN, "return"); }
	for					{ return symbol(sym.FOR, "for"); }
	def					{ return symbol(sym.DEF, "def"); }
	in					{ return symbol(sym.IN, "in"); }

	has_key				{ return symbol(sym.DATAFUNCION, "has_key"); }
	items				{ return symbol(sym.DATAFUNCION, "items"); }
	keys				{ return symbol(sym.DATAFUNCION, "keys"); }
	pop					{ return symbol(sym.DATAFUNCION, "pop"); }
	values				{ return symbol(sym.DATAFUNCION, "values"); }
	count				{ return symbol(sym.DATAFUNCION, "count"); }
	find				{ return symbol(sym.DATAFUNCION, "find"); }
	join				{ return symbol(sym.DATAFUNCION, "join"); }
	split				{ return symbol(sym.DATAFUNCION, "split"); }
	replace				{ return symbol(sym.DATAFUNCION, "replace"); }
	length				{ return symbol(sym.DATAFUNCION, "length"); }
	append				{ return symbol(sym.DATAFUNCION, "append"); }
	extend				{ return symbol(sym.DATAFUNCION, "extend"); }
	index				{ return symbol(sym.DATAFUNCION, "index"); }
	insert				{ return symbol(sym.DATAFUNCION, "insert"); }
	size				{ return symbol(sym.DATAFUNCION, "size"); }

	print				{ return symbol(sym.PRINT, "print"); }
	raw_input			{ return symbol(sym.RAW_INPUT, "raw_input"); }
	type				{ return symbol(sym.TYPE, "type"); }
	int					{ return symbol(sym.FUNCION, "int"); }
	float				{ return symbol(sym.FUNCION, "float"); }
	str					{ return symbol(sym.FUNCION, "str"); }
	tuple				{ return symbol(sym.FUNCION, "tuple"); }
	list				{ return symbol(sym.FUNCION, "list"); }
	dict				{ return symbol(sym.FUNCION, "dict"); }

	{Identifier}		{ return symbol(sym.ID, yytext()); }

	\"                 	{ string.setLength(0); yybegin(STRING); }

	\'                 	{ string.setLength(0); yybegin(CSSTRING); }

	\"\"\"             	{ string.setLength(0); yybegin(MLSTRING); }

	\'\'\'              { string.setLength(0); yybegin(MLCSSTRING); }

	{IntegerLiteral}	{ return symbol(sym.INTEGER, yytext()); }

	{LongLiteral}		{ return symbol(sym.LONG, yytext()); }

	{FloatLiteral} 		{ return symbol(sym.FLOAT, yytext()); }

	{LineTerminator}/[\t]					{ 	tabs = 0; 
												yybegin(TAB); 
												return symbol(sym.NEWLINE);
											}

	{LineTerminator}/[a-zA-Z0-9]			{
												tabs = 0;
												if (!salto){
													yypushback(1);
													salto = true;	
													return symbol(sym.NEWLINE);
												}
												if (tabs < tabsAnterior) {
													yypushback(1);
													tabsAnterior--;
													return symbol(sym.NOTAB);
												}
												else if (tabs > tabsAnterior){
													tabsAnterior = tabs;
													return symbol(sym.TAB);
												}
												else {
													salto = false;
												}
											}
										
	{LineTerminator}/{Comment}			{ return symbol(sym.NEWLINE); }

	{Comment}        	{ /* ignore */ }

	{WhiteSpace}	    { /* ignore */ }

}

<STRING>{

	\"                	{
							yybegin(YYINITIAL);
							return symbol(sym.STRING, string.toString());
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
							return symbol(sym.STRING, string.toString());
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
							return symbol(sym.STRING, string.toString());
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
							return symbol(sym.STRING, string.toString());
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
											yypushback(1);
											tabsAnterior--;
											return symbol(sym.NOTAB);
										}
										else if (tabs > tabsAnterior){
											tabsAnterior = tabs;
											return symbol(sym.TAB);
										}
									}

	\t/[ \r\n(\r\n)\f]				{ yybegin(YYINITIAL); }
}
. 					{
						throw new ParsingException("Illegal character at line " + yyline + ", column " + yycolumn + " >> " + yytext());
					}
