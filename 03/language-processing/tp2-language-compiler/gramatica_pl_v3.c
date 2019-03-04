
Program 	: 	PROGRAM nome ';' Corpo
			;

Corpo 		:	Declaracoes BEGIN Codigo END
			;

Declaracoes	:	VAR VarIds ';'
			;

VarIds 		: 	Var 
			|	VarIds ',' Var
			;

Var			:	id
			|	id'['Expressao']'
			;

Codigo		:
			|	Codigo Instrucao
			;

Instrucao	:	Atribuicao ';'
			|	Input ';'
			|	Output	';'
			|	Condicao
			|	Ciclo
			;

Atribuição	:	Var '=' Expressao
			;

Input		:	<< Var
			;

Output		: 	>> string
			|	>> Var
			;

Condicao	:	IF '(' Expressao ')' '{' Codigo '}' ElseIf
			;

ElseIf		:
			|	ELSE '{' Codigo '}'
			;

Ciclo		:	WHILE '(' Expressao ')' '{' Codigo '}'
			;

Expressao 	: 	ExpSimples
			|	ExpSimples OPR ExpSimples
			;

ExpSimples	:	Termo
			|	ExpSimples '+' Termo
			|	ExpSimples '-' Termo
			|	ExpSimples OR Termo
			;

Termo 		:	Fator
			|	Termo '*' Fator
			|	Termo '/' Fator
			|	Termo AND Fator
			;

Fator 		:	num
			|	id
			|	id'['Expression']'
			|	'-' Factor
			|	'!' Factor
			| 	'(' Expression ')'
			;

/********************************************/
/********************************************/
/********************************************/
/********************************************/
/********************************************/




