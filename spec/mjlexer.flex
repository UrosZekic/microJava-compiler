package rs.ac.bg.etf.pp1;

import java_cup.runtime.Symbol;

%%

%{

	// ukljucivanje informacije o poziciji tokena
	private Symbol new_symbol(int type) {
		return new Symbol(type, yyline+1, yycolumn);
	}
	
	// ukljucivanje informacije o poziciji tokena
	private Symbol new_symbol(int type, Object value) {
		return new Symbol(type, yyline+1, yycolumn, value);
	}
	private int currLine = -1;
	private int currCol = -1;
	private String currError = "";
	private int errorLineStart = -1;
	private int errorColStart = -1;
	private void reportError(String err) {
		    if (currLine == yyline+1 && currCol == yycolumn) {
                currError += err;
		    } 
		    else {
                if (!currError.equals("")) {
                 System.out.println("Leksicka greska,nedozvoljeni tokeni " + currError + "  u liniji " + errorLineStart + " na koloni " + errorColStart);
                }
                currError = err;
                errorLineStart = yyline+1;
                errorColStart = yycolumn+1;
		    	}

		    currLine = yyline+1;
		    currCol = yycolumn+1;
		}

%}

%cup
%line
%column

%xstate COMMENT

%eofval{
	reportError("");
	return new_symbol(sym.EOF);
%eofval}

%%

" " 	{ }
"\b" 	{ }
"\t" 	{ }
"\r\n" 	{ }
"\n"	{ }
"\f" 	{ }

"program"   { return new_symbol(sym.PROG, yytext());}
"print" 	{ return new_symbol(sym.PRINT, yytext()); }
"read" 		{ return new_symbol(sym.READ, yytext()); }
"return" 	{ return new_symbol(sym.RETURN, yytext()); }
"void" 		{ return new_symbol(sym.VOID, yytext()); }
"+" 		{ return new_symbol(sym.PLUS, yytext()); }
"-" 		{ return new_symbol(sym.MINUS, yytext()); }
"*" 		{ return new_symbol(sym.MUL, yytext()); }
"/" 		{ return new_symbol(sym.DIV, yytext()); }
"%" 		{ return new_symbol(sym.MOD, yytext()); }
"++" 		{ return new_symbol(sym.INC, yytext()); }
"--" 		{ return new_symbol(sym.DEC, yytext()); }
"=" 		{ return new_symbol(sym.EQUAL, yytext()); }
";" 		{ return new_symbol(sym.SEMI, yytext()); }
"," 		{ return new_symbol(sym.COMMA, yytext()); }
"(" 		{ return new_symbol(sym.LPAREN, yytext()); }
")" 		{ return new_symbol(sym.RPAREN, yytext()); }
"{" 		{ return new_symbol(sym.LBRACE, yytext()); }
"}"			{ return new_symbol(sym.RBRACE, yytext()); }
"[" 		{ return new_symbol(sym.LBRACKET, yytext()); }
"]"			{ return new_symbol(sym.RBRACKET, yytext()); }
"new" 		{ return new_symbol(sym.NEW, yytext()); }
"const" 		{ return new_symbol(sym.CONST, yytext()); }

"//" {yybegin(COMMENT);}
<COMMENT> . {yybegin(COMMENT);}
<COMMENT> "\r\n" { yybegin(YYINITIAL); }
<COMMENT>"\r" {yybegin(YYINITIAL);}
<COMMENT>"\n" {yybegin(YYINITIAL);}

[0-9]+  { return new_symbol(sym.NUMBER, new Integer (yytext())); }
("true"|"false") { return new_symbol (sym.BOOL, yytext()); }
([a-z]|[A-Z])[a-z|A-Z|0-9|_]* 	{return new_symbol (sym.IDENT, yytext()); }
"'"."'" {return new_symbol (sym.CHARS, new Character (yytext().charAt(1)));}

. { reportError(yytext()); }
