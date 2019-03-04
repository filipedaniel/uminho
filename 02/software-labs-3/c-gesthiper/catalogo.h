#ifndef _CATALOGO_
#define _CATALOGO_

#include "boolean.h"

/*typedefs para uma utilização mais clara*/
typedef char Letra;
typedef char* Codigo;



/**********************************************************/
/*				Estrutura de Catalogo 	 				  */
/**********************************************************/
typedef struct catalogo *Catalogo;
/*API*/
Catalogo* initCatalogo();						/*Inicializa um catalogo*/
void deleteCatalogo(Catalogo*);					/*Apaga um catalogo*/
BOOLEAN existeNoCatalogo(Catalogo*,Codigo); 	/*Verifica se existe no catalogo um codigo cod 0-se sim /-1-se nao*/
Catalogo* addToCatalogo(Catalogo*,Codigo);		/*Insere um elemento(char*) no Catalogo*/
Catalogo* removeElemCatalogo(Catalogo*,Codigo); /*Revove um elemento(char*) do Catalogo*/



/**********************************************************/
/*				Estruturas de Retorno 	 				  */
/**********************************************************/

typedef struct result *Result;
	/*API*/

/*Retorna o proximo elemento de Result*/
Result getNext(Result);
/*Retorna o elemento anterior de Result*/
Result getPrev(Result);
/*Devolve o codifo pretencente ao Result*/
Codigo getElem(Result);
/*Retorna o numero de elementos de Result*/
int lenghtResult(Result);
/*Restri Result passado por parametro*/
void destroiResult(Result*);



/**********************************************************/
/*					Queries 	 						  */
/**********************************************************/

/*Querie 2*//*Dado um cotalo e uma letra, devolve um Result com todos os codigos que começam com essa letra*/
Result getCatalogoLetra(Catalogo*,Letra);




#endif
