
%{

#include <stdio.h>
#include <string.h>
#include <stdlib.h>

#include "stack.h"
#include "assembly.h"
#include "hash.h"


#define MAX_SIZE 100

#define INT 0
#define ARRAY 1
#define VARIAVEL 2 

#define INT_SIZE 1

#define TRUE 1
#define FALSE 0

#define ERRO -1

#define ERR_SINTAX "ERROR: Sintax error"
#define ERR_NOT_DEC "ERROR: Variable not defined"
#define ERR_VAR_EXISTS "ERROR: Variable already declared"
#define ERR_INDEX_OOB "ERROR: Array index out of bounds"
#define ERR_TYPE_ERROR "ERROR: Wrong type variable"	
#define ERR_INIT_ERROR "ERROR: Can't initialize arrays in declaration"
#define ERR_MISS_OP_BRACK " (maybe missing opening brackets?)"
#define ERR_MISS_CL_BRACK " (maybe missing closing brackets?)"

int yydebug=0;

// declaração de funções
extern int yylex();	
extern int yylineno;
extern FILE * yyin;


int yyerror(char *);
char* getOperator(char* simb);	
int isValidId(char* c);
int isValidArray(char* c, int n);
Stack Stack_Init(Stack);


//inicializações

HashTable ht = NULL;
Stack stlabel = NULL;
Assembly codeBuffer = NULL;
Assembly code = NULL;


int sp=0;
int lblCounter=1;

// flag to detect declaration block
int declBlock=1;

int addr=-1;
int tam=-1;
int tipo=-1;

// ficheiro de leitura
char* infile;
int interactive=0;

// usado para copiar string para a lista ligada
char* buffer;

// flag para determinar se houve erro
int error=0;;

// contador de parenteses abertos
int p=0;

// modo verbose
int verbose=0;
// flag com ultimo comando gerado
int cType;

%}

%union{	
	char* pal;
	int inteiro;
	struct  SFactor
	{
	  int type;
	  int valueI;
	  char *valueS;
	}factor;
}


%token <inteiro> num
%token <pal> id string OPR
%token ERROR PROGRAM PROGNAME VAR BBEGIN END IF ELSE WHILE 

%type <factor> Termo
%type <factor> Factor
%type <factor> ExpSimples

	

%start Program


%%

Program 	: 	PROGRAM PROGNAME ';' Corpo			{ insertStop(&code); if(verbose) printTabelaVariaveis(&ht) ; if(!error) printAssemblyToScreen(code) ; return(0); }
			;
Corpo 		:	Declaracoes BBEGIN 					{ insertStart(&codeBuffer);  assemblyCat(&code , codeBuffer); codeBuffer=NULL; }
				Codigo END			
			;

Declaracoes	:										{ declBlock=0; }
			|	VAR VarIds ';'						{ declBlock=0; }
			;

VarIds 		: 	Var 								
			|	VarIds ',' Var 						
			;										

Var			:	id 									{ if (declBlock) { 	if(getEndereco(ht,$1)!=-1) yyerror(ERR_VAR_EXISTS);
													  else { insereVariavel(&ht,$1,sp,INT,INT_SIZE); sp+=INT_SIZE; insertPushN(&codeBuffer, 1); }}
													}

			|	id'['num']'							{ if (declBlock) { if(getEndereco(ht,$1)!=-1) yyerror(ERR_VAR_EXISTS);
													  else { insereVariavel(&ht,$1,sp,ARRAY, $3); sp+=$3; insertPushN(&codeBuffer, $3); }}
													}
			|	Atribuicao

Codigo		:										
			|	Codigo Instrucao					{  if(codeBuffer!=NULL) assemblyCat(&code , codeBuffer); codeBuffer=NULL; }
			;

Instrucao	:	Atribuicao ';'
			|	Input ';'
			|	Output	';'
			|	Condicao
			|	Ciclo
			;

Atribuicao	:	id '=' ExpSimples					{ if(declBlock) { insereVariavel(&ht,$1,sp,INT,INT_SIZE); sp+=INT_SIZE; }
													  if(isValidId($1)) { insertStoreG(&codeBuffer, addr); }
													}

			|	id'['num']' '=' ExpSimples			{ if(declBlock) { yyerror(ERR_INIT_ERROR); }
													  if(isValidArray($1,$3)) { insertStoreG(&codeBuffer, (getEndereco(ht,$1)+$3)); }
													}

			;

Input		:	'?' id 								{ if(isValidId($2)) { insertRead(&codeBuffer); insertStoreG(&codeBuffer, getEndereco(ht,$2)); }}	
			|   '?' id'['num']' 					{ if(isValidArray($2,$4)) { insertRead(&codeBuffer); insertStoreG(&codeBuffer, (getEndereco(ht,$2)+$4)); }}	
			;

Output		: 	'!' string							{ insertPushS(&codeBuffer, $2);  insertWriteS(&codeBuffer); }
			|	'!' id 								{ if(isValidId($2)) { insertPushG(&codeBuffer, getEndereco(ht,$2));  insertWriteI(&codeBuffer); }}
			|	'!' id'['num']'						{ if(isValidArray($2,$4)) { insertPushG(&codeBuffer, getEndereco(ht,$2)+$4);  insertWriteI(&codeBuffer); }}
			;

Condicao	:	IF '(' Expressao ')' 				{ Stack_Push(stlabel,lblCounter++); insertJZ(&codeBuffer, Stack_Top(stlabel)); } 
			    '{' Codigo '}' 				
			    ElseIf 
			;

ElseIf		:										{ insertLabel(&codeBuffer, Stack_Pop(stlabel)); }
			|	ELSE 								{ Stack_Push(stlabel,lblCounter++); insertJump(&codeBuffer, Stack_Top(stlabel));
											  		  int previous = Stack_Pop(stlabel); insertLabel(&codeBuffer, Stack_Pop(stlabel)); Stack_Push(stlabel,previous); } 
				'{' Codigo '}' 						{ insertLabel(&codeBuffer, Stack_Pop(stlabel));  }
			;

Ciclo		:	WHILE 								{ Stack_Push(stlabel,lblCounter++); insertLabel(&codeBuffer, Stack_Top(stlabel));  } 
				'(' Expressao ')' 					{ Stack_Push(stlabel,lblCounter++);  insertJZ(&codeBuffer, Stack_Top(stlabel)); }  
				'{' Codigo '}' 						{ int previous = Stack_Pop(stlabel);  insertJump(&codeBuffer,  Stack_Pop(stlabel));
											 		  Stack_Push(stlabel,previous); insertLabel(&codeBuffer, Stack_Pop(stlabel));
													}
			;

Expressao 	: 	ExpSimples					
			|	Expressao OPR ExpSimples			{ insertCode(&codeBuffer, getOperator($2)); }
			;

ExpSimples	:	Termo						
			|	ExpSimples '+' Termo 				{ insertAdd(&codeBuffer); }
			|	ExpSimples '-' Termo				{ insertSub(&codeBuffer); }
			;


Termo 		:	Factor 						
			|	Termo '*' Factor  					{  insertMul(&codeBuffer);	}
			|	Termo '/' Factor 					{  insertDiv(&codeBuffer);	}
			;


Factor 		:	num									{ $$.valueI = $1; $$.type=INT; insertPushI(&codeBuffer, $1); }
			|	id   								{ if(isValidId($1)) { $$.valueS = $1; $$.type=VARIAVEL; insertPushG(&codeBuffer, getEndereco(ht, $1));} }
			|	id '['num']'   						{ if(isValidArray($1,$3)) { $$.valueS = $1; $$.valueI = $3; $$.type=ARRAY; insertPushG(&codeBuffer, (getEndereco(ht, $1)+$3)); } }

			|   '(' ExpSimples ')' 					{ if($2.type==INT) { $$.type=INT; $$.valueI = $2.valueI; }
										      		  else if($2.type==ARRAY) { $$.type=ARRAY; $$.valueI = $2.valueI; $$.valueS = $2.valueS; }
									  	      		  else if($2.type==VARIAVEL) { $$.type=VARIAVEL; $$.valueS = $2.valueS; }
									  	    		}
			;


%%

int isValidId(char* c) {
	addr =  getEndereco(ht,c); tipo = getTipo(ht,c);
	if(addr==-1) { yyerror(ERR_NOT_DEC); return 0; }
	else if(tipo!=INT) { yyerror(ERR_TYPE_ERROR); return 0; }
	else return 1;
}
int isValidArray(char* c, int n) {
	addr =  getEndereco(ht,c); tipo = getTipo(ht,c); tam = getTamanho(ht,c);
	if(addr==-1) { yyerror(ERR_NOT_DEC); return 0; }
	else if(tipo!=ARRAY) { yyerror(ERR_TYPE_ERROR); return 0; }
	else if( n >(tam-1)) { yyerror(ERR_INDEX_OOB); return 0; }
	else return 1;
}

char* getOperator(char* simb) {
	if(strcmp(simb,"==")==0) return "EQUAL\n";
	if(strcmp(simb,">=")==0) return "SUPEQ\n";
	if(strcmp(simb,"<=")==0) return "INFEQ\n";
	if(strcmp(simb,"<")==0) return "INF\n";
	if(strcmp(simb,">")==0) return "SUP\n";
	return "";
}


int yyerror(char *msg) {

	char * message = strdup("");

	if(strcmp("syntax error",msg)==0) {
		msg = strdup(ERR_SINTAX);
		if(p!=0) {
			if(p>0) { message = strdup(ERR_MISS_CL_BRACK); }
			else { message = strdup(ERR_MISS_OP_BRACK); }
		}
	}

	printf("%s%s",msg,message);
	if(!interactive) {
		error=1;
		printf(" ( %s : %d )\n\n", infile, yylineno);
		char command[50];
		sprintf(command, "awk 'NR==%d' %s", yylineno,infile);

		system(command);
	} 
	printf("\n");

	codeBuffer=NULL;

	return 0;
}

int main( int argc, char **argv )
{
	buffer = malloc(sizeof(char)*MAX_SIZE); 
	stlabel = Stack_Init(stlabel);

	++argv, --argc; 
	
	if ( argc > 0 ) {
		if( strcmp(argv[0],"-v")==0) {
			verbose=1;
	   		infile=strdup(argv[1]);
	   	} else {
	   		infile=strdup(argv[0]);
	   	}
		yyin = fopen( infile, "r" );
		if(yyin==0) { printf("%s - File not found, entering interactive mode!\n", infile); interactive=1; }
		else interactive=0;
	}  
	else {
	   yyin = stdin;
	   interactive=1;
	}


  	yyparse();

  	fclose(yyin);


	return 0;
}


