#ifndef _CONTABILIDADE_
#define _CONTABILIDADE_

#include "boolean.h"


/*typedefs para uma utilização mais clara*/
typedef char* CodigoProduto;
typedef float PrecoUnidade;
typedef int Unidades;
typedef int Mes;
typedef char Tipo;




/**********************************************************/
/*			Estrutura de Contabilidade 	 				  */
/**********************************************************/
typedef struct contabilidade *Contabilidade;
/*API*/
Contabilidade* initContabilidade();										/*Inicializa uma contabilidade*/
void deleteContabilidade(Contabilidade*);								/*Apaga uma contabilidade*/
BOOLEAN existeNaContabilidade(Contabilidade*,CodigoProduto);			/*Verifica se existe na contabilidade um codigo cod 0-se sim /-1-se nao*/
Contabilidade* addProdutoToContabilidade(Contabilidade*,CodigoProduto);	/*Insere um Produto(char*) na Contabilidade*/
Contabilidade* addCompraToContabilidade(Contabilidade*,CodigoProduto,PrecoUnidade,Unidades,Tipo,Mes); /*Insere um registo de Compra na Contabilidade*/
Contabilidade* removeElemContabilidade(Contabilidade*,CodigoProduto);	/*Revove um elemento(char*) da Contabilidade*/



/**********************************************************/
/*				Estruturas de Retorno 	 				  */
/**********************************************************/


/*---------------------------------- - - - - - - - - - - - 
	struct resultContabilidade
------------------------------------ - - - - - - - - - - -*/
typedef struct resultContabilidade *ResultContabilidade;
/*API*/

/*Retorna o numero de elementos de ResultContabilidade*/
int lenghtResultContabilidade(ResultContabilidade);
/*Dado um ResultCompras devolve o proximo ResultContabilidade*/
ResultContabilidade getNextResultContabilidade(ResultContabilidade);
/*Dado um ResultCompras devolve o ResultContabilidade anterior*/
ResultContabilidade getPrevResultContabilidade(ResultContabilidade);
/*Retorna o elemento(Codigo Produto) de um dado um ResultContabilidade*/
CodigoProduto getElemResultContabilidade(ResultContabilidade);
/*Destroi ResultContabilidade passado por parametro*/
void destroiResultContabildade(ResultContabilidade);




/*---------------------------------- - - - - - - - - - - - 
	struct par
------------------------------------ - - - - - - - - - - -*/
typedef struct par *Par;
/*API*/

/*Inicializa um Par*/
Par initPar();
/*Devolve primeiro elemento de Par(numero total de compras)*/
int getFstElem(Par);
/*Devolve segundo elemento de Par(numero total de clientes)*/
int getSndElem(Par);




/**********************************************************/
/*					Queries 	 						  */
/**********************************************************/

/*Querie 3*//*Totas as proximas queries são necessarias para responder a querie 3*/
			/*Dado uma registo de contabilidade, um codigo de produto e um mês, retorna o total(int) de vendas do tipo normal nesse mes*/
int totalDeVendasNormalMes(Contabilidade*,CodigoProduto,Mes);
			/*Dado uma registo de contabilidade, um codigo de produto e um mês, retorna o total(int) de vendas do tipo promoção nesse mes*/
int totalDeVendasPromocaoMes(Contabilidade*,CodigoProduto,Mes);
			/*Dado uma registo de contabilidade, um codigo de produto e um mês, retorna o total(float) faturado nesse mes*/
float totalFaturadoMes(Contabilidade*,CodigoProduto,Mes);

/*Querie 4*//*Dado um redisto de contabilidade retorna um ResultContabilidade com os codigos dos produto que ninguem comprou*/
ResultContabilidade produtosSemComprador(Contabilidade*);

/*Querie 7*//*Dado um registo de contabilidade, e dois meses, retorna um Par, 
			contendo no primeiro elemento o numero total de compras, e so segundo o numero de clientes no intervalo dos dois meses*/
Par totalComprasEFaturadoEntreMes(Contabilidade*,Mes,Mes);





#endif
