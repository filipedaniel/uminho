alphadigit		[a-zA-Z0-9_]
alpha 			[a-zA-Z]
digit 			[0-9]

%x declaracoes programName var code
%option yylineno

%{

#include <stdio.h>
#include "y.tab.h"

extern int p;

%}


%% 

<*>[ \t\n]				;
<*>(?i:program)				{ BEGIN programName; return(PROGRAM); }
<*>[\[\]\{\}\?\!+\-*\/=]		{ return (*yytext); } 
<*>\(					{ p++; return (*yytext); }
<*>\)					{ p--; return (*yytext); }
<*>{digit}+				{ yylval.inteiro = atoi(yytext); return (num);   }

<programName>[a-zA-Z0-9]*   		{ return(PROGNAME); }
<programName>;.*\n			{ BEGIN declaracoes; return(';'); }

<declaracoes>"var"	  		{ BEGIN var; return(VAR); }
<declaracoes>(?i:begin)			{ BEGIN code; return (BBEGIN);}
<var>;	 				{ BEGIN declaracoes; return(';'); }
<var>,					{ return(','); }
<var>{alphadigit}+			{ yylval.pal = strdup(yytext); return(id); }


<code>\/\/.*				;
<code>;					{ return (';'); }
<code>(?i:end)				{ return(END); }
<code>"if"				{ return (IF);    }
<code>"else"				{ return (ELSE);  }
<code>"while"				{ return (WHILE); }
<code>(==)|(>=)|(<=)|(<)|(>) 		{ yylval.pal = strdup(yytext); return (OPR);   }
<code>{alphadigit}+ 			{ yylval.pal = strdup(yytext); return (id); }	
<code>\"(\\.|[^"])*\"			{ yylval.pal = strdup(yytext); return (string); }

<*>.					{ return (ERROR); }
%%


