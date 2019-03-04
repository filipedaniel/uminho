%x musica letra

%{
/* Declaracoes C diversas */
/*Fazer os includes */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "makeLatex.h"


//Inicializações
Musicas musicas = NULL;
Musicas new = NULL;
Letra newLetra = NULL;
Map mapaLatex = NULL;

int flag = 0;
int nVertices = 0;
int estrofe = 0;

char* aux;


%}


%%	
<*>(?i:title[ \t]*?:[ \t]*?).* 	{ new = initMusicas();
				  mapaLatex = iniateMapLatex();
				  setTitle(new,filterScharacters(yytext+7,mapaLatex));
				  BEGIN musica;
							}
<musica>(?i:lyrics[ \t]*?:).*	{setLyric(new,yytext+8); }

<musica>(?i:music[ \t]*?:).*	{ setAutMusic(new,yytext+7); }

<musica>(?i:author[ \t]*?:).* 	{ setAuthor(new,yytext +8); }

<musica>(?i:singer[ \t]*?:).* 	{ setSinger(new,yytext +8); }

<musica>(?i:from[ \t]*?:).*	{ setFrom(new,yytext +6); }

<musica>\n[ \t\n]+		{ BEGIN letra; }

<musica>\n			;

<musica>(?i:[a-zA-Z]+:).*	;

<letra>"##".*			{  
				 setLetra(new,newLetra);
				 setEstrofe(new,estrofe,nVertices);
				 nVertices = 0;
				 estrofe = 0;
				 flag = 0;

				 newLetra = NULL;
				 musicas = insertMusic(musicas,new);

				 BEGIN musica;
				}
				
<letra>[^\n]*			{
				 newLetra = insertLetra(newLetra,filterScharacters(yytext,mapaLatex));
				 nVertices++; 
				 flag=1;
				}	
<letra>"\n"			;			
	
<letra>"\n\n" 			{	
				 if(nVertices != 0 && flag == 1){			
				  setEstrofe(new,estrofe,nVertices);
				  nVertices = 0;
				  estrofe++;
				 }
				  flag = 0;
				}
				
<*><<EOF>> 			{
				 setEstrofe(new,estrofe,nVertices);
				 setLetra(new,newLetra);
				 musicas = insertMusic(musicas,new); 
				 yyterminate();	 
				}
%%



int yywrap()
{ return(1);  }

int main(int argc, char **argv)
{
	++argv, --argc;  /* skip over program name */
	if ( argc > 0 )
	     yyin = fopen( argv[0], "r" );
	else
	     yyin = stdin;

	yylex();

	makeLatex(musicas);
	fclose(yyin);
	return 0;
}
