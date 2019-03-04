#ifndef _VENDAS_
#define _VENDAS_

#include "boolean.h"

/*typedefs para uma utilização mais clara*/
typedef char* CodProduto;
typedef char* CodCliente;
typedef char TipoDaCompra;
typedef int UnidadesCompradas;
typedef int MesDaCompra;


/**********************************************************/
/*				Estrutura de Compras 	 				  */
/**********************************************************/
typedef struct compras *Compras;
/*API*/
Compras* initCompras();										/*Inicializa uma instancia de compras*/
void deleteCompras(Compras*);								/*Apaga um instancia de compras*/
Compras* addToCompras(Compras*,CodProduto,UnidadesCompradas,TipoDaCompra,CodCliente,MesDaCompra); 	/*Insere um Registo nas compras*/
Compras* removeElemCompras(Compras* c, CodProduto);			/*Remove um todos o registo de um produto das Compras*/
BOOLEAN existeNasCompras(Compras*,CodProduto);  			/*Verifica se existe regisntado nas compras um codigo cod 0-se sim /-1-se nao*/




/**********************************************************/
/*				Estruturas de Retorno 	 				  */
/**********************************************************/

/*---------------------------------- - - - - - - - - - - - 
	struct resultCompras
------------------------------------ - - - - - - - - - - -*/
typedef struct resultCompras *ResultCompras;
	/*API*/

/*Retorna o numero de elementos de ResultCompras*/
int lenghtResultCompras(ResultCompras);
/*Dado um ResultCompras devolve o proximo ResultCompras*/
ResultCompras getNextResultCompras(ResultCompras);
/*Dado um ResultCompras devolve o ResultCompras anterior*/
ResultCompras getPrevResultCompras(ResultCompras);
/*Retorna o elemento(Codigo Produto) de um dado um ResultCompras*/
CodProduto getElemResultCompras(ResultCompras);
/*Destroi ResultCompras passado por parametro*/
void destroiResultCompras(ResultCompras);




/*---------------------------------- - - - - - - - - - - - 
	struct resultCompras8
------------------------------------ - - - - - - - - - - -*/
typedef struct resultCompras8 *ResultCompras8;
	/*API*/

/*Dado um ResultCompras8 retorna um ResultCompras contendo o registo de compras do tipo normal*/
ResultCompras getResultadoComprasNormal(ResultCompras8);
/*Dado um ResultCompras8 retorna um ResultCompras contendo o registo de compras do tipo promoção*/
ResultCompras getResultadoComprasPromocao(ResultCompras8);
/*Destroi ResultCompras8 passado por parametro*/
void destroiResultCompras8(ResultCompras8);




/*---------------------------------- - - - - - - - - - - - 
	struct resultCompras9
------------------------------------ - - - - - - - - - - -*/
typedef struct resultCompras9 *ResultCompras9;
	/*API*/

/*Dado um ResultCompras9 devolve o proximo ResultCompras9*/
ResultCompras9 getNextResultCompras9(ResultCompras9);
/*Dado um ResultCompras9 devolve o ResultCompras9 anterior*/
ResultCompras9 getPrevResultCompras9(ResultCompras9);
/*Retorna o elemento(int)(total de compras) de um dado um ResultCompras9*/
int getTotalResultCompras(ResultCompras9);
/*Devolve um ResultCompras contendo todos os produtos com os produtos com 
					total de compras igual ao obtido na função anterior*/
ResultCompras getProdutosResultCompras9(ResultCompras9);
/*Retorna o numero de elementos de ResultCompras9*/
int lenghtResultCompras9(ResultCompras9);
/*Destroi ResultCompras9 passado por parametro*/
void destroiResultCompras9(ResultCompras9);




/*---------------------------------- - - - - - - - - - - - 
	struct comprasEclientes
------------------------------------ - - - - - - - - - - -*/
typedef struct comprasEclientes *ComprasEClientes;
	/*API*/

/*Inicializa initComprasEClientes*/
ComprasEClientes initComprasEClientes();
/*Retorna elemento(int), total de compras*/
int getComprasElem(ComprasEClientes);
/*Retorna elemento(int), total de clientes*/
int getClientesElem(ComprasEClientes);


/*---------------------------------- - - - - - - - - - - - 
	struct resultCompras12
------------------------------------ - - - - - - - - - - -*/
typedef struct resultCompras12 *ResultCompras12;
	/*API*/

/*Dado um ResultCompras12 devolve o proximo ResultCompras12*/
ResultCompras12 getNextResultCompras12(ResultCompras12);
/*Retorna o elemento(codigo produto)*/
CodProduto getCodigoProdutoResultCompras12(ResultCompras12);
/*Retorna o elemento(int), total de compras do produto em questao*/
int getTotaldeComprasResultCompras12(ResultCompras12);
/*Retorna o elemento(int), total de clientes do produto em questao*/
int getTotaldeclientesCompras12(ResultCompras12);
/*Retorna o numero de elementos de ResultCompras12*/
int lenghtResultCompras12(ResultCompras12);
/*Destroi ResultCompras12 passado por parametro*/
void destroiResultCompras12(ResultCompras12);




/**********************************************************/
/*					Queries 	 						  */
/**********************************************************/


/*Querie 5*//*Dodo o registo de compras e um código de cliente retorna uma array de inteiros com 12 posições contendo a total de 
			compras para cada um dos meses do cliente referido*/
int * getInfoComprasDeCliente(Compras*,CodCliente);

/*Querie 8*//*Dado um registo de compras e um codigo de produto retorna um ResultCompras8 com as API disponivel acima, contendo
			o registo dos produtos que o cliente comprou, tanto em modo normal como em promoção.*/
ResultCompras8 getRegistoTotaldeClientes(Compras*,CodProduto);

/*Querie 9*//*Dado um registo de compras, um codigo de produto e um mes, retorna um ResultCompras9 com as API disponivel acima, 
			contendo os produtos mais comprados nesse mes bem como os seus valores*/
ResultCompras9 getProdutosMaisComprados(Compras*,CodCliente,MesDaCompra);

/*Querie 10*//*Dado um registo de compras devolve um ResultCompras contendo os codigos dos clientes que mais compraram durannte o
			ano, ordenado por ordem descendente de compras*/
ResultCompras getClientesQueComprouTodoAno(Compras*);

/*Querie 11*//*Dado um registo de compras e um mes devolve um ComprasEClientes com o total de compras e clientes no mes em questao*/
ComprasEClientes getComprasEClientesMes(Compras*,MesDaCompra);

/*Querie 12*//*Dado um registo de compras, o top que desejar, devolve um ResultCompras12, com a API acima retorna um ResultCompras12 
			com os códigos dos produtos, total de compras e vendas dos produtos que constituem o top desejado*/
ResultCompras12 topProdutos(Compras*,int top);

/*Querie 13*//*Dado um registo de compras e um código de cliente devolve um ResultCompras9 com os produtos(total comprado) e produtos  
			com esse total(ResultCompras) do cliente fornecido */
ResultCompras9 topProdutosCompradosPorCliente(Compras*,CodCliente);

/*Querie 14*//*Dado um registo de compras devolve o total(int) dos distintos clientes que efetuaram compras*/
int numeroTotalDeClientes(Compras*);






#endif
