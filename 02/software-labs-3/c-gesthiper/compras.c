#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "compras.h"
#include "boolean.h"

/***************************************************************************/
/*				ESTRUTURAS DE DADOS DO MODULO COMPRAS  					   */
/***************************************************************************/

/*----------------------------------------- - - - - - - - - - - - - - - -
	Estrutura para definir as compras de um mês
------------------------------------------- - - - - - - - - - - - - - - -*/
typedef struct compramensal
{
	int comprasN;
	int comprasP;
	int total;
}CompraMensal;

/*----------------------------------------- - - - - - - - - - - - - - - -
	Estrutura(AVL) para guardar cada cliente e suas compras mensais
------------------------------------------- - - - - - - - - - - - - - - -*/
typedef struct cliente
{
	char* codC;
	CompraMensal totalCompras[12];
	unsigned int alt;
	struct cliente *right, *left;
}*Clientes;

/*----------------------------------------- - - - - - - - - - - - - - - - 
	Estrutura(AVL) para registar todas as compras para um produto
------------------------------------------- - - - - - - - - - - - - - - -*/
typedef struct compras
{
	char* codP;
	Clientes* listaClientes; /*Lista de clientes que compraram o produto*/
	unsigned int alt;
	struct compras *right, *left;
}NodeCompras;


/*##########################################################################*/

/***************************************************************************/
/*				Estruturas de dados para retorno da queries 			   */
/***************************************************************************/


/*----------------------------------------- - - - - - - - - - - - - - - - 
	Estrutura de retorno da maior parte das queries
------------------------------------------- - - - - - - - - - - - - - - -*/
struct resultCompras
{
	char* elem;
	struct resultCompras *next;
	struct resultCompras *prev;
};

/*----------------------------------------- - - - - - - - - - - - - - - - 
	Retorno da querie 8, sendo que retorna um par de ResultCompras
------------------------------------------- - - - - - - - - - - - - - - -*/
struct resultCompras8
{
	ResultCompras compras_normal;
	ResultCompras compras_promocao;
};

/*----------------------------------------- - - - - - - - - - - - - - - - 
	Retorno da querie 9 e 13
------------------------------------------- - - - - - - - - - - - - - - -*/

struct resultCompras9
{
	int total;
	ResultCompras produtos;
	struct resultCompras9 *next;
	struct resultCompras9 *prev;
};

/*----------------------------------------- - - - - - - - - - - - - - - - 
	Querie 11, retorna um par, numero de compras e numero de clientes
------------------------------------------- - - - - - - - - - - - - - - -*/
struct comprasEclientes
{
	int compras;
	int clientes;
};

/*----------------------------------------- - - - - - - - - - - - - - - - 
	Querie 12, contem: total de compras, total de clientes de um produto
------------------------------------------- - - - - - - - - - - - - - - -*/

struct resultCompras12
{
	int total_compras;
	int total_clientes;
	char* codigo_produto;
	struct resultCompras12 *next;
};

/*##########################################################################*/

/****************************************************************************/
/*			Funçoes para manipular cada estrutura de retorno				*/
/****************************************************************************/


/*----------------------------------------- - - - - - - - - - - - - - - - 
	struct resultCompras
------------------------------------------- - - - - - - - - - - - - - - -*/

/*retorna uma lista biligada para facilitar possivel navegação nos resultados*/

static ResultCompras criaNo(char* cod){
	ResultCompras no;
	if((no = (ResultCompras) malloc(sizeof(struct resultCompras))) == NULL) return NULL;
	if((no->elem = (char*) malloc(sizeof(cod))) == NULL){
		free(no);
		return NULL;
	}
	no->elem = strcpy(no->elem,cod);
	no->next = NULL;
	no->prev =NULL;
	return no;
}

static void destroiNoResultCompras(ResultCompras *no){
	free((*no)->elem);
	free(*no);
	*no = NULL;
}

static ResultCompras insereAux(ResultCompras r,char* cod);

static ResultCompras insereElementoResultCompras(ResultCompras r, char* cod){
	ResultCompras new;
	ResultCompras noAIns;
	if((new = criaNo(cod)) == NULL) return NULL;
	if(r == NULL || strcmp(r->elem,cod) > 0){
		new->next = r;
		if(new->next != NULL) 
			new->next->prev = new;
		r = new;
	}
	else{
		noAIns = insereAux(r,cod);
		if(noAIns != NULL){
			new->prev = noAIns;
			new->next = noAIns->next;
			noAIns->next = new;
			if(new->next != NULL) new->next->prev = new;
		}
	}
	return r;
}

static ResultCompras insereAux(ResultCompras r,char* cod){
	ResultCompras nAnt = NULL;
	ResultCompras nAct = r;
	while(nAct != NULL && strcmp(nAct->elem,cod) <= 0){
		nAnt = nAct;
		nAct = nAct->next;
	}
	return nAnt;
}

void destroiResultCompras(ResultCompras r){
	ResultCompras nAct, nAnt;
        nAct = r;
        while (nAct != NULL){
                nAnt = nAct;
                nAct = nAct->next;
                free (nAnt);
                }
}

static int existeElemResultCompras(ResultCompras *r, char* cod){
	int existe = 0;
	ResultCompras aux;
	aux = *r;	

	while(aux != NULL && existe != 1){
		if(strcmp(aux->elem,cod) == 0)	existe = 1;
		else {aux = aux->next;}

	}
	return existe;
}

int lenghtResultCompras(ResultCompras r){
	int t= 0;
	ResultCompras aux = r;
	while(aux != NULL){
		t++;
		aux = aux->next;
	}
	return t;
}

ResultCompras getNextResultCompras(ResultCompras t){
	return t->next;
}

ResultCompras getPrevResultCompras(ResultCompras t){
	return t->prev;
}

CodCliente getElemResultCompras(ResultCompras t){
	return t->elem;
}

/*----------------------------------------- - - - - - - - - - - - - - - - 
	struct resultCompras8
------------------------------------------- - - - - - - - - - - - - - - -*/
/*Par de ResultCompras, para destinguir entre compras normal e promoção*/

ResultCompras getResultadoComprasNormal(ResultCompras8 r){
	return r->compras_normal;
}

ResultCompras getResultadoComprasPromocao(ResultCompras8 r){
	return r->compras_promocao;
}

static ResultCompras8 setResultadoComprasNormal(ResultCompras8 r, char* codigo_cliente){
	r->compras_normal = insereElementoResultCompras(r->compras_normal,codigo_cliente);
	return r;
}

static ResultCompras8 setResultadoComprasPromocao(ResultCompras8 r, char* codigo_cliente){
	r->compras_promocao = insereElementoResultCompras(r->compras_promocao,codigo_cliente);
	return r;
}

void destroiResultCompras8(ResultCompras8 r){
	destroiResultCompras(r->compras_normal);
	destroiResultCompras(r->compras_promocao);
}


/*----------------------------------------- - - - - - - - - - - - - - - - 
	struct resultCompras9
------------------------------------------- - - - - - - - - - - - - - - -*/
/**/

static ResultCompras9 criaNoQuerie9(int i ,char* cod){
	ResultCompras9 no;
	if((no = (ResultCompras9) malloc(sizeof(struct resultCompras9))) == NULL) return NULL;
	no->total = i;
	no->produtos = criaNo(cod);
	no->next = NULL;
	no->prev =NULL;
	return no;
}

static ResultCompras9 insereAuxQuerie9(ResultCompras9 r,int t);

static ResultCompras9 insereElementoResultComprasQuerie9(ResultCompras9 r, int t, char* cod){
	ResultCompras9 new;
	ResultCompras9 noAIns;
	
	if((new = criaNoQuerie9(t,cod)) == NULL) return NULL;
	
	if(r == NULL || t > r->total){
		new->next = r;
		if(new->next != NULL) 
			new->next->prev = new;
		r = new;
	}
	else{
		noAIns = insereAuxQuerie9(r,t);
		if(noAIns != NULL){
			if(noAIns->total == t){
				insereElementoResultCompras(noAIns->produtos,cod);
			}else{
				new->prev = noAIns;
				new->next = noAIns->next;
				noAIns->next = new;
				if(new->next != NULL) new->next->prev = new;
			}
		}
	}
	return r;
}

static ResultCompras9 insereAuxQuerie9(ResultCompras9 r,int t){
	ResultCompras9 nAnt = NULL;
	ResultCompras9 nAct = r;

	while(nAct != NULL && t <= nAct->total){
		nAnt = nAct;
		nAct = nAct->next;
	}
	return nAnt;
}

int lenghtResultCompras9(ResultCompras9 r){
	int t= 0;
	while(r != NULL){
		t+=lenghtResultCompras(r->produtos);
		r = r->next;
	}
	return t;
}

ResultCompras9 getNextResultCompras9(ResultCompras9 t){
	return t->next;
}

ResultCompras9 getPrevResultCompras9(ResultCompras9 t){
	return t->prev;
}
ResultCompras getProdutosResultCompras9(ResultCompras9 t){
	return t->produtos;
}

int getTotalResultCompras(ResultCompras9 t){
	return t->total;
}

void destroiResultCompras9(ResultCompras9 r){
	ResultCompras9 nAct, nAnt;
        nAct = r;
        while (nAct != NULL){
                nAnt = nAct;
                nAct = nAct->next;
                destroiResultCompras(nAnt->produtos);
                free(nAnt);
        }
}

/*----------------------------------------- - - - - - - - - - - - - - - - 
	struct comprasEclientes
------------------------------------------- - - - - - - - - - - - - - - -*/
/*Par com total de compras e clientes*/

ComprasEClientes initComprasEClientes(){
	ComprasEClientes p = malloc(sizeof(struct comprasEclientes));
	p->compras = 0;
	p->clientes = 0;
	return p;
}

int getComprasElem(ComprasEClientes p){
	return p->compras;
}

int getClientesElem(ComprasEClientes p){
	return p->clientes;
}

static void setComprasElem(ComprasEClientes p, int e){
	p->compras = e;
}

static void setClientesElem(ComprasEClientes p, int e){
	p->clientes = e;
}

static void atualizaElemCompras(ComprasEClientes p, int e){
	p->compras += e;
}

static void atualizaElemClientes(ComprasEClientes p, int e){
	p->clientes += e;
}

/*----------------------------------------- - - - - - - - - - - - - - - - 
	struct resultCompras12
------------------------------------------- - - - - - - - - - - - - - - -*/
/*composta por: para um produto(cod) total compras e total clientes*/

static ResultCompras12 criaNoResult12(int total_compras, int total_clientes ,char* cod){
	ResultCompras12 no;

	if((no = (ResultCompras12) malloc(sizeof(struct resultCompras12))) == NULL) return NULL;
	no->codigo_produto = (char*) malloc(sizeof(cod));
	no->codigo_produto = strcpy(no->codigo_produto,cod);
	no->total_compras = total_compras;
	no->total_clientes = total_clientes;
	no->next = NULL;
	return no;
}

static void destroiNoResultCompras12(ResultCompras12 *r){
	free((*r)->next);
	free(*r);
	*r = NULL;
}

static ResultCompras12 insereElementoResultCompras12(ResultCompras12 r, int total_compras, int total_clientes, char* cod){
	ResultCompras12 new;
	ResultCompras12 noAnt = NULL;
	ResultCompras12 noActual = r;

	if((new = criaNoResult12(total_compras,total_clientes,cod)) == NULL) return NULL;

	while(noActual != NULL && total_compras < noActual->total_compras){
		noAnt = noActual;
		noActual = noActual->next;
	}
	if(noAnt == NULL){
		new->next = r;
		r = new;
	}
	else{
		new->next = noActual;
		noAnt->next = new;
	}
	return r;
}

void destroiResultCompras12(ResultCompras12 r){
	ResultCompras12 nAct,nAnt;
	nAct = r;
	while(nAct != NULL){
		nAnt = nAct;
        nAct = nAct->next;
        free (nAnt);
	}
}

int lenghtResultCompras12(ResultCompras12 r){
	int t= 0;
	while(r != NULL){
		t++;
		r = r->next;
	}
	return t;
}

ResultCompras12 getNextResultCompras12(ResultCompras12 t){
	return t->next;
}

CodProduto getCodigoProdutoResultCompras12(ResultCompras12 t){
	return t->codigo_produto;
}

int getTotaldeComprasResultCompras12(ResultCompras12 t){
	return t->total_compras;
}

int getTotaldeclientesCompras12(ResultCompras12 t){
	return t->total_clientes;
}


/*##########################################################################*/
/*##########################################################################*/

/****************************************************************************/
/*		 					FUNÇÕES DO MODULO DE COMPRAS					*/
/****************************************************************************/


/*Função de hash para aceder as tabelas de hash quer de clientes quer de produtos*/
static int hashFunc(char* key){
	return key[0]-'A';
}

/*----------------------------------------- - - - - - - - - - - - - - - - 
	Funcões sobre os Clientes
------------------------------------------- - - - - - - - - - - - - - - -*/
				
/*Inicializa Clientes*/
static Clientes* initClientes(){
	int i = 0;
	Clientes* c = malloc(26*sizeof(struct cliente));
	while(i<=25){
		c[i] = NULL;
		i++;
	}
	return c;
}

/*Apaga registo de Clientes*/
static void deleteClienteTree(Clientes t){
 	if (t == NULL) return;
 	else{
  		deleteClienteTree(t->left);
    	deleteClienteTree(t->right);
   	}
    free(t);
}

static void deleteClientes(Clientes* c){
	int i;
	for (i=0;i<=25;i++){
		if (c[i] != NULL){
			deleteClienteTree(c[i]);
		}
	}
}

/*Verifica se Cliente existe numa AVL de clientes*/
static int existeCliente(Clientes t, char* codC){
	if(!t) return -1;
	if(strcmp(t->codC,codC) == 0) return 0;
	else{
		if(strcmp(t->codC,codC)>0)
			return existeCliente(t->left,codC);
		else
			return existeCliente(t->right,codC);
	}
}

static void clientes_equilibrarAVL(Clientes *c);
static void addToClientesTree(Clientes *t, int unidades, char tipo, char* codC, int mes);
static void atualizaClientesTree(Clientes *t, int unidades, char tipo, char* codC, int mes);

/*Adiciona um registo as clientes - invoca a função: 
		-> atualizaClientesTree() 	caso o cliente já esteja registado como comprador do produto em questao, atualiza do dados
		-> addToClientesTree() 		caso o cliente não existir, passando a arvore AVL correspondente ao hash do código */
static Clientes* addToClientes(Clientes* c, int unidades, char tipo, char* codC, int mes){
	int key = hashFunc(codC);
	
	if(existeCliente(c[key],codC) == 0){
		atualizaClientesTree(&c[key],unidades,tipo,codC,mes);
	}
	else{
		addToClientesTree(&c[key],unidades,tipo,codC,mes);
	}
	return c;
}

/*Adiciona um cliente a uma arvore AVL */
static void addToClientesTree(Clientes *t, int unidades, char tipo, char* codC, int mes){
	if(*t == NULL){
		int unidadesP;
		int unidadesN;
		int total;
		int i=0;
		Clientes cl = malloc(sizeof(struct cliente));
		cl-> codC = malloc(strlen(codC)+1);
		cl-> codC = strcpy(cl->codC,codC);
		unidadesP = 0;
		unidadesN = 0;
		if(tipo=='N'){
			unidadesN += unidades;
			unidadesP = 0;
		}
		else{
			unidadesN = 0;
			unidadesP += unidades;
		}
		total = unidadesP + unidadesN;
		while(i<12){
			cl->totalCompras[i].comprasN = 0;
			cl->totalCompras[i].comprasP = 0;
			cl->totalCompras[i].total = 0;
			i++;
		}
		cl->totalCompras[mes-1].comprasN = unidadesN;
		cl->totalCompras[mes-1].comprasP = unidadesP;
		cl->totalCompras[mes-1].total = total;	
		*t=cl;
	}
	else{
		if(strcmp((*t)->codC,codC) > 0) 
			addToClientesTree(&(*t)->left,unidades,tipo,codC,mes);
		else 
			addToClientesTree(&(*t)->right,unidades,tipo,codC,mes);
		}
	clientes_equilibrarAVL(t);
}

/*Atualiza um registo de um cliente, com as unidades compradas bem como total pago no produto*/
static void atualizaClientesTree(Clientes *t, int unidades, char tipo, char* codC, int mes){
	if(strcmp((*t)->codC,codC) == 0){
		int total = 0;
		int unidadesN = 0;
		int unidadesP = 0;
		if(tipo=='N'){
			unidadesN += unidades;
			unidadesP = 0;
		}
		else{
			unidadesN = 0;
			unidadesP += unidades;
		}
		total = unidadesP + unidadesN;
		(*t)->totalCompras[mes-1].comprasN += unidadesN;
		(*t)->totalCompras[mes-1].comprasP += unidadesP;
		(*t)->totalCompras[mes-1].total += total;
	}
	else{
		if(strcmp((*t)->codC,codC)>0) {
			atualizaClientesTree(&(*t)->left,unidades,tipo,codC,mes);}
		else 
			atualizaClientesTree(&(*t)->right,unidades,tipo,codC,mes);
	}
}

/*Remove um elemento do registo de cliente - 
					invoca o função removeElemento() passando a AVL correnpondente ao hash do código*/
static void removeElementoCl(Clientes *t, char* cod);

static Clientes* removeElemClientes(Clientes* c, char* cod){
	int key = hashFunc(cod);
	removeElementoCl(&c[key],cod);
	return c;
}

static void removeElementoCl(Clientes *t, char* cod){
	if(*t == NULL) return;
	if(strcmp((*t)->codC,cod)>0)
		removeElementoCl(&(*t)->left,cod);
	else if(strcmp((*t)->codC,cod)<0)
			removeElementoCl(&(*t)->right,cod);
		else{							/*Encontrou!!*/
			Clientes old;
			if ((*t)->right == NULL) { /*Nao existe lado direito*/
			    old = *t; 
			    *t = (*t)->left; 
			    free (old); 
    		}
    		else if((*t)->left == NULL){ /*Nao existe lado esquerdo*/
				old = *t; 
      			*t = (*t)->right; 
      			free (old); 
			}
			else{
				Clientes heir = (*t)->left; 
		      	Clientes prev = *t; 
		      	while ( heir->right != NULL ) { 
		        	prev = heir; 
		        	heir = heir->right; 
		      	} 
		      	strcpy((*t)->codC,heir->codC);
		      	if ( prev != *t ) 
		        	prev->right = heir->left; 
		      	else
		        	prev->left = heir->left; 
		      	free (heir); 
    		} 
		}
		clientes_equilibrarAVL(t);
}


/*----------------------------------------- - - - - - - - - - - - - - - - 
	Funcões sobre as Compras
------------------------------------------- - - - - - - - - - - - - - - -*/


/*Inicializa Compras*/
Compras* initCompras(){
	int i=0;
	Compras* c = malloc(26*sizeof(NodeCompras));
	while(i<=25){
		c[i] = NULL;
		i++;
	}
	return c;
}

/*Apaga registo de Compras*/
static void deleteComprasTree(Compras t){
 	if (t == NULL) return;
 	deleteClientes(t->listaClientes);
  	deleteComprasTree(t->left);
    deleteComprasTree(t->right);
    free(t);
 }

void deleteCompras(Compras* c){
	int i;				
	for (i=0;i<=25;i++){
		if (c[i] != NULL){
			deleteComprasTree(c[i]);
		}
	}
 }

/*Verifica se produto existe nas compras*/
int existeNasCompras(Compras* c,char* codP){
	int res = -1;
	int key = hashFunc(codP);
	Compras aux = malloc(sizeof(struct compras));
	aux = c[key];
	while(aux != NULL){
		if(strcmp(aux->codP,codP) == 0){
			res = 0;
			return res;
		}
		if(strcmp(aux->codP,codP)>0)
			aux=aux->left;
		else
			aux = aux->right;
	}
	return res;
}

/*Verifica se produto existe na arvore AVL a ele destinada, 
					diferente da anterior, esta apenas usada apénas dentro do modulo*/
static int existeProduto(Compras t, char* codP){
	if(!t) return -1;
	if(strcmp(t->codP,codP) == 0) return 0;
	else{
		if(strcmp(t->codP,codP)>0)
			return existeProduto(t->left,codP);
		else
			return existeProduto(t->right,codP);
	}
}


static void compras_equilibrarAVL(Compras *c);
static void atualizaCompra(Compras *t, char* codP, int unidades, char tipo, char* codC, int mes);
static void addToComprasTree(Compras *t, char* codP, int unidades, char tipo, char* codC, int mes);

/*Adiciona um registo de compras - invoca a função: 
		-> atualizaCompra() 	caso o produto referente há compra já esteja registado, atualiza do dados(seus clientes)
		-> addToComprasTree() 	caso o produto não exista, passando a arvore AVL correspondente ao hash do código e dados*/
Compras* addToCompras(Compras* c, char* codP, int unidades, char tipo, char* codC, int mes){
	int key = hashFunc(codP);
	 
	if(c[key]==NULL){
		Clientes* cl;
		Compras new = malloc(sizeof(NodeCompras));
		new-> codP = malloc(strlen(codP)+1);
		new-> codP = strcpy(new->codP,codP);
		cl = initClientes();
		cl = addToClientes(cl,unidades,tipo,codC,mes);
		new-> listaClientes = cl;
		new->right = NULL;
		new->left = NULL;
		c[key]=new;
	}
	else{
		if(existeProduto(c[key],codP) == 0){
			atualizaCompra(&c[key],codP, unidades, tipo, codC, mes);
		}
		else{
			addToComprasTree(&c[key],codP, unidades, tipo, codC, mes);
		}
	}
	return c;
}

/*Actualiza registo de Compras */
static void atualizaCompra(Compras *t, char* codP, int unidades, char tipo, char* codC, int mes){
	if(strcmp((*t)->codP,codP) == 0){
		(*t)->listaClientes = addToClientes((*t)->listaClientes,unidades,tipo,codC,mes);
	}
	else{
		if(strcmp((*t)->codP,codP)>0) 
			atualizaCompra(&(*t)->left,codP,unidades,tipo,codC,mes);
		else 
			atualizaCompra(&(*t)->right,codP,unidades,tipo,codC,mes);
	}
}

/*Adiciona novo registo de compra ha arvore AVL correspondente*/
static void addToComprasTree(Compras *t, char* codP, int unidades, char tipo, char* codC, int mes){
	if(*t == NULL){ 
		Compras new = malloc(sizeof(struct compras));
		new-> codP = malloc(strlen(codP)+1);
		new-> codP = strcpy(new->codP,codP);
		new->listaClientes = initClientes();
		new-> listaClientes = addToClientes(new->listaClientes,unidades,tipo,codC,mes);
		new->right = NULL;
		new->left = NULL;
		*t=new;
	}
	else{
		if(strcmp((*t)->codP,codP)>0) 
			addToComprasTree(&(*t)->left,codP,unidades,tipo,codC,mes);
		else 
			addToComprasTree(&(*t)->right,codP,unidades,tipo,codC,mes);
	}
	compras_equilibrarAVL(t);
}


/*Remove um elemento do registo de Compras - 
				invoca o função removeElemento() passando a arvore AVL correnpondente ao hash do código*/
static void removeElemento(Compras *t, char* cod);

Compras* removeElemCompras(Compras* c, char* cod){
	int key = hashFunc(cod);
	removeElemento(&c[key],cod);
	return c;
}

static void removeElemento(Compras *t, char* cod){
	if(*t == NULL) return;

	if(strcmp((*t)->codP,cod)>0)
		removeElemento(&(*t)->left,cod);
	else if(strcmp((*t)->codP,cod)<0)
			removeElemento(&(*t)->right,cod);
		else{
			Compras old;
			if ( (*t)->right == NULL ) { /*Nao existe lado direito*/
			    old = *t; 
			    *t = (*t)->left; 
			    free (old); 
    		}
    		else if((*t)->left == NULL){ /*Nao existe lado esquerdo*/
				old = *t; 
      			*t = (*t)->right; 
      			free (old); 
			
			}
			else{
				Compras heir = (*t)->left; 
		      	Compras prev = *t; 
		      	while ( heir->right != NULL ) { 
		        	prev = heir; 
		        	heir = heir->right; 
		      	} 
		      	strcpy((*t)->codP,heir->codP);
		      	if ( prev != *t ) 
		        	prev->right = heir->left; 
		      	else
		        	prev->left = heir->left; 
		      	free (heir); 
    		} 
		}
		compras_equilibrarAVL(t);
}


/*##########################################################################*/
/*##########################################################################*/

/****************************************************************************/
/*		 				FUNÇÕES DO PARA MANIPULAR AS AVL's					*/
/****************************************************************************/


/*----------------------------------------- - - - - - - - - - - - - - - - 
	Manipular as AVL de Compras
------------------------------------------- - - - - - - - - - - - - - - -*/

/*Altura da AVL de compras*/
static unsigned int compras_alturaAVL(Compras c){
	if(c == NULL) return 0;
	return c->alt;
}

/*Rotação simples para a direita*/
static void compras_rotacaoSimplesDireita(Compras *c){
	unsigned int altEsq, altDir;

	Compras no = (*c)->left;
	(*c)->left = no->right;
	no->right = *c;

	altEsq = compras_alturaAVL((*c)->left);
	altDir = compras_alturaAVL((*c)->right);
	(*c)->alt = altEsq > altDir ? altEsq + 1 : altDir + 1;

	altEsq = compras_alturaAVL(no->left);
	altDir = (*c)->alt;
	no->alt = altEsq > altDir ? altEsq +1 : altDir + 1;

	*c = no;
}

/*Rotação simples para a esquerda*/
static void compras_rotacaoSimplesEsquerda(Compras *c){
	unsigned int altEsq,altDir;

	Compras no = (*c)->right;
	(*c)->right = no->left;
	no->left = *c;

	altEsq = compras_alturaAVL((*c)->left);
	altDir = compras_alturaAVL((*c)->right);
	(*c)->alt = altEsq > altDir ? altEsq + 1 : altDir + 1;

	altEsq = (*c)->alt;
	altDir = compras_alturaAVL(no->right);
	no->alt = altEsq > altDir ? altEsq +1 : altDir + 1;

	*c = no;
}

/*Rotação dupla: direita/esquerda*/
static void compras_rotacaoDuplaDireitaEsquerda(Compras *c){
	compras_rotacaoSimplesDireita(&(*c)->right);
	compras_rotacaoSimplesEsquerda(c);
}

/*Rotação dupla: esquerda/direita*/
static void compras_rotacaoDuplaEsquerdaDireita(Compras *c){
	compras_rotacaoSimplesEsquerda(&(*c)->left);
	compras_rotacaoSimplesDireita(c);
}

/*Balancear a avl de compras*/
static void compras_equilibrarAVL(Compras *c){
	unsigned int altDir,altEsq;
	if(*c == NULL) return;

	altEsq = compras_alturaAVL((*c)->left);
	altDir = compras_alturaAVL((*c)->right);

	if(altEsq - altDir == 2){	/*Sub arvore esquerda desiquilibrada*/

		altEsq = compras_alturaAVL((*c)->left->left);
		altDir = compras_alturaAVL((*c)->left->right);
		if(altEsq >= altDir){
			compras_rotacaoSimplesDireita(c);}
		else
			compras_rotacaoDuplaEsquerdaDireita(c);
	}
	else{
		if(altDir - altEsq == 2){	/*Sub arvore direita desiquilibrada*/
			altDir = compras_alturaAVL((*c)->right->right);
			altEsq = compras_alturaAVL((*c)->right->left);
			if(altDir >= altEsq)
				compras_rotacaoSimplesEsquerda(c);
			else
				compras_rotacaoDuplaDireitaEsquerda(c);
		}
		else 
			(*c)->alt = altEsq > altDir ? altEsq +1 : altDir +1;
	}
}


/*----------------------------------------- - - - - - - - - - - - - - - - 
	Manipular as AVL de Clientes
------------------------------------------- - - - - - - - - - - - - - - -*/

/*Altura da AVL de clientes*/
static unsigned int clientes_alturaAVL(Clientes c){
	if(c == NULL) return 0;
	return c->alt;
}

/*Rotação simples para a direita*/
static void clientes_rotacaoSimplesDireita(Clientes *c){
	unsigned int altEsq, altDir;

	Clientes no = (*c)->left;
	(*c)->left = no->right;
	no->right = *c;

	altEsq = clientes_alturaAVL((*c)->left);
	altDir = clientes_alturaAVL((*c)->right);
	(*c)->alt = altEsq > altDir ? altEsq + 1 : altDir + 1;

	altEsq = clientes_alturaAVL(no->left);
	altDir = (*c)->alt;
	no->alt = altEsq > altDir ? altEsq +1 : altDir + 1;

	*c = no;
}

/*Rotação simples para a esquerda*/
static void clientes_rotacaoSimplesEsquerda(Clientes *c){
	unsigned int altEsq,altDir;

	Clientes no = (*c)->right;
	(*c)->right = no->left;
	no->left = *c;

	altEsq = clientes_alturaAVL((*c)->left);
	altDir = clientes_alturaAVL((*c)->right);
	(*c)->alt = altEsq > altDir ? altEsq + 1 : altDir + 1;

	altEsq = (*c)->alt;
	altDir = clientes_alturaAVL(no->right);
	no->alt = altEsq > altDir ? altEsq +1 : altDir + 1;

	*c = no;
}

/*Rotação dupla: direita/esquerda*/
static void clientes_rotacaoDuplaDireitaEsquerda(Clientes *c){
	clientes_rotacaoSimplesDireita(&(*c)->right);
	clientes_rotacaoSimplesEsquerda(c);
}

/*Rotação dupla: esquerda/direita*/
static void clientes_rotacaoDuplaEsquerdaDireita(Clientes *c){
	clientes_rotacaoSimplesEsquerda(&(*c)->left);
	clientes_rotacaoSimplesDireita(c);
}

/*Balancear a avl de clientes*/
static void clientes_equilibrarAVL(Clientes *c){
	unsigned int altDir,altEsq;
	if(*c == NULL) return;

	altEsq = clientes_alturaAVL((*c)->left);
	altDir = clientes_alturaAVL((*c)->right);

	if(altEsq - altDir == 2){	/*Sub arvore esquerda desiquilibrada*/

		altEsq = clientes_alturaAVL((*c)->left->left);
		altDir = clientes_alturaAVL((*c)->left->right);
		if(altEsq >= altDir){
			clientes_rotacaoSimplesDireita(c);}
		else
			clientes_rotacaoDuplaEsquerdaDireita(c);
	}
	else{
		if(altDir - altEsq == 2){	/*Sub arvore direita desiquilibrada*/
			altDir = clientes_alturaAVL((*c)->right->right);
			altEsq = clientes_alturaAVL((*c)->right->left);
			if(altDir >= altEsq)
				clientes_rotacaoSimplesEsquerda(c);
			else
				clientes_rotacaoDuplaDireitaEsquerda(c);
		}
		else 
			(*c)->alt = altEsq > altDir ? altEsq +1 : altDir +1;
	}
}

static int contarNosClientes(Clientes c){
   if(c == NULL)
        return 0;
   else
        return 1 + contarNosClientes(c->left) + contarNosClientes(c->right);
}

/*##########################################################################*/
/*##########################################################################*/

/****************************************************************************/
/*		 							QUERIES 								*/
/****************************************************************************/


/*----------------------------------------- - - - - - - - - - - - - - - - 
	Querie 5
------------------------------------------- - - - - - - - - - - - - - - -*/
/*Dado um codigo de cliente, criar uma tabela com o nuumero total de produtos comprados, 
mes a mes (para meses em que nao comprou a entrada devera ficar a 0). 
A tabela devera ser apresentada em ecra. O utilizador devera ter a
opcao de guardar tal tabela num ficheiro de texto;*/

static int * totalDeComprasClientes(Clientes *c, int r[], char* codC){
	int ok = 1; 
	Clientes cl;
	if(*c == NULL) { return r;}
	cl = *c;
	while(cl != NULL && ok){
		if(strcmp(cl->codC,codC)==0){
			int i;
			int x;
			for(i=0;i<12;i++){
				x = cl->totalCompras[i].total;
				r[i] += x;
			}
			ok = 0;
		}
		else{
			if(strcmp(cl->codC,codC) > 0)
					cl = cl->left;
						else cl = cl->right;	
		}	
	}
	return r;
}

static int * getComprasDeClienteTree(Compras *c, int r[], char* codC){
	if(*c != NULL){
		int key = hashFunc(codC);
		Clientes cl = (*c)->listaClientes[key];
		r = totalDeComprasClientes(&cl,r,codC);
	
	r = getComprasDeClienteTree(&(*c)->left,r,codC);
	r = getComprasDeClienteTree(&(*c)->right,r,codC);
	}
	return r;
}

/* - -
	A função principal (getInfoComprasDeCliente), para cada posição da hash de AVL's, invoca a função
   getComprasDeClienteTree() que para cada nodo da avl, invoca a função totalDeComprasClientes() que, 
   para o cliente pretendido soma o total de compras e cada um dos diferentes meses, ao array que será
   devolvido como resultado 						
- -*/

int * getInfoComprasDeCliente(Compras* c, char* codC){
	int k;
	int i;
	Compras cmp = NULL;
	int * r = malloc(sizeof(int)*12);
	for(k=0;k<12;k++) r[k] = 0;
	for (i=0;i<26;i++){
		cmp = c[i];
		if(cmp != NULL){ 
			r = getComprasDeClienteTree(&cmp,r,codC);
		}
	}
	return r;
}

/*----------------------------------------- - - - - - - - - - - - - - - - 
	Querie 8
------------------------------------------- - - - - - - - - - - - - - - -*/
/*Dado um registo de compras e um codigo de produto retorna um ResultCompras8, contendo
o registo dos produtos que o cliente comprou, tanto em modo normal como em promoção.*/

static ResultCompras8 getClientesdeProdutoTree(ResultCompras8 r,Clientes *cl){
	if(*cl != NULL){
		int i;
		int ok = 1;
	
		for(i=0;i<12 && ok ;i++){
			if((*cl)->totalCompras[i].comprasN != 0){
				r = setResultadoComprasNormal(r,(*cl)->codC);
				ok = 0;
			}
		}
		ok = 1;
		for(i=0;i<12 && ok ;i++){
			if((*cl)->totalCompras[i].comprasP != 0){
				r = setResultadoComprasPromocao(r,(*cl)->codC);
				ok = 0;
			}
		}
	r = getClientesdeProdutoTree(r,&(*cl)->left);
	r = getClientesdeProdutoTree(r,&(*cl)->right);
	}
	return r;
}

static ResultCompras8 getClientesdeProduto(ResultCompras8 r, Clientes* cl){
	int i = 0;
	Clientes claux = NULL;
	for(i=0;i<26;i++){
		claux = cl[i];
		if(claux != NULL){
			r = getClientesdeProdutoTree(r,&claux);
		}
	}
	return r;
}

/* - -
	Dado um código de produto, verifica se na posição do array obtida atraves da função de hash
	para o código pretendido, se o produto existe, ou seja, existe compras para esse produto, 
	cao exista, invoca a função  getClientesdeProduto(...) responsavel por invocar uma outra 
	função, getClientesdeProdutoTree(...), com o conjunto de clientes que comprarm o produto 
	em questão, esta ultima função para cada cliente verifica se o mesmo efetuou uma compra
	do tipo normal ou promoção, inserindo o código do produto do cliente na estrutura de retorno.
- -*/
ResultCompras8 getRegistoTotaldeClientes(Compras* c, char* cod){
	int key = hashFunc(cod);
	int ok = 1;
	Compras c1 = NULL;
	
	ResultCompras8 r = (ResultCompras8)malloc(sizeof(struct resultCompras8));
	Clientes* cl = NULL;
	c1 = c[key];
	if(c1 != NULL){
		while(c1 != NULL && ok==1){
			if(strcmp(c1->codP,cod) == 0){
				cl = c1->listaClientes;
				r = getClientesdeProduto(r,cl);
				ok = 0;
			}
			else{
				if(strcmp(c1->codP,cod) > 0)
					c1 = c1->left;
						else c1 = c1->right;	
			}	
		}
	}
	return r;
}


/*----------------------------------------- - - - - - - - - - - - - - - - 
	Querie 9
------------------------------------------- - - - - - - - - - - - - - - -*/
/*Dado um registo de compras, um codigo de produto e um mes, retorna um ResultCompras9 com as API disponivel acima, 
contendo os produtos mais comprados nesse mes bem como os seus valores*/

static int getTotalUnidadesComprados(Clientes *cl, char* codC, int mes){
	int total = 0;
	if(*cl == NULL) return 0;
	else{
		int ok = 1;
		Clientes c = *cl;
		while(c != NULL && ok==1){
			if(strcmp(c->codC,codC) == 0){
				total = c->totalCompras[mes-1].total;
				ok = 0;
			}
			else{
				if(strcmp(c->codC,codC) > 0)
					c = c->left;
						else c = c->right;	
			}
		}
	}
	return total;
}

static ResultCompras9 getTotalUnidadesCompradosTree(ResultCompras9 r, Compras *c, char* cod, int mes){
	if(*c != NULL){
		int key = hashFunc(cod);
		int total = 0;
		Clientes* cl = NULL;
		Clientes clientes = NULL;
		cl = (*c)->listaClientes;
		clientes = cl[key];
		total =  getTotalUnidadesComprados(&clientes,cod,mes);
		if(total != 0 ){
			r = insereElementoResultComprasQuerie9(r,total,(*c)->codP);
		}
		r = getTotalUnidadesCompradosTree(r,&(*c)->left,cod,mes);
		r = getTotalUnidadesCompradosTree(r,&(*c)->right,cod,mes);
	}
	return r;
}

/*- -
	Na função principal, getProdutosMaisComprados(...), recebendo um código de cliente e um mês, de forma como
	a estrura de compras esta organizado, esta função para cada posição da hash, invoca a função 
	getTotalUnidadesCompradosTree(...), esta que para cada nodo de cada avl, invoca a função 
	getTotalUnidadesComprados(...), passando o código do cliente e o mês pretendido, esta retorna o total de 
	compras para o cliente pretendido no produto que está a ser analizado. De seguida, insere o codigo do produto
	e o total de compras para o mês pedido ordenadamente por total de compras, repetindo este processo para
	todos os nodos da avl. 
*/

ResultCompras9 getProdutosMaisComprados(Compras* c, char* cod, int mes){
	int i = 0;
	
	ResultCompras9 r = NULL;
	while(i<26){
		r = getTotalUnidadesCompradosTree(r,&c[i],cod,mes);
		i++;
	}
	return r;
}


/*----------------------------------------- - - - - - - - - - - - - - - - 
	Querie 10
------------------------------------------- - - - - - - - - - - - - - - -*/
/*Dado um registo de compras devolve um ResultCompras contendo os codigos dos clientes que mais compraram durannte o
ano, ordenado por ordem descendente de compras*/

static ResultCompras checkTotalDeClientesTree(ResultCompras r,Clientes *cl){
	if(*cl != NULL){
		Clientes c = *cl;
		int i = 0;
		int ok = 0;
		while(i<12){
			if(c->totalCompras[i].total == 0){
				ok = 1; 
				i = 12;
			}
			else
				i++;
		}
		if(ok == 0){  /*Compras em todos os meses;*/
			r = insereElementoResultCompras(r,c->codC);
		}
	
	r = checkTotalDeClientesTree(r,&(*cl)->left);
	r = checkTotalDeClientesTree(r,&(*cl)->right);
	}
	return r;
}

static ResultCompras checkTotalDeClientes(ResultCompras r, Compras *c){
	if(*c != NULL){
		int i = 0;
		Clientes clientes = NULL;
		Clientes* cl = NULL;
		cl = (*c)->listaClientes;
		while(i<26){
			clientes = cl[i];
			r = checkTotalDeClientesTree(r,&clientes);
			i++;
		}
	
	r = checkTotalDeClientes(r,&(*c)->left);
	r = checkTotalDeClientes(r,&(*c)->right);
	}
	return r;
}

/*- -
	A função getClientesQueComprouTodoAno(), para cada posição da tabela hash invoca a função
	checkTotalDeClientes(), esta com o objetivo de para cada posição da hash de clientes invoca
	a função checkTotalDeClientesTree(...) que para cada cliente verifica se todas as posições 
	da array que contém a informação das compras para aquele produto, está com todos os valores
	diferentes de 0, ou seja, efetuou compras daquele produto todo o ano.
	PROBLEMA!! so verifica se comprou todos os meses um determinado produto!
- -*/
ResultCompras getClientesQueComprouTodoAno(Compras* c){
	int i=0;

	Compras c1 = NULL;
	ResultCompras r = NULL;

	while(i<26){
		c1 = c[i];
		r = checkTotalDeClientes(r,&c1);
		i++;
	}
	return r;
}


/*----------------------------------------- - - - - - - - - - - - - - - - 
	Querie 11
------------------------------------------- - - - - - - - - - - - - - - -*/
/*Dado um registo de compras e um mes devolve um ComprasEClientes com o total de compras e clientes no mes em questao*/

static ComprasEClientes totalComprasEclientesmesTree(ComprasEClientes cc, ResultCompras *r, Clientes *cl, int mes){
	if(*cl != NULL){
			int total = (*cl)->totalCompras[mes-1].total;
			if(total != 0){
				atualizaElemCompras(cc,total);
				if(existeElemResultCompras(r,(*cl)->codC) == 0)
					*r = insereElementoResultCompras(*r,(*cl)->codC);
			}
		cc = totalComprasEclientesmesTree(cc,r,&(*cl)->left,mes);
		cc = totalComprasEclientesmesTree(cc,r,&(*cl)->right,mes);
	}
	return cc;
}


static ComprasEClientes totalComprasEclientesmes(ComprasEClientes cc, ResultCompras *r, Compras *c, int mes){
	if(*c != NULL){
			int i = 0;
			Clientes* cl = NULL;
			Clientes clientes = NULL;

			cl = (*c)->listaClientes;
			while(i<26){
				clientes = cl[i];
				cc = totalComprasEclientesmesTree(cc,r,&clientes,mes);
				i++;
			}
		cc = totalComprasEclientesmes(cc,r,&(*c)->left,mes);
		cc = totalComprasEclientesmes(cc,r,&(*c)->right,mes);
	}
	return cc;
}

/*- -
	Esta função, para um determinado mês, retorna um (ComprasEClientes), constituido por um par de valores,
	total de compras e total de clientes para o mes pretendido. A função getComprasEClientesMes(...), para 
	cada  posição da hash de produtos, invoca a função totalComprasEclientesmes(...), esfa função que 
	percorre a avl de produtos, e para cada nodo invoca a função totalComprasEclientesmesTree(...) que 
	para cada posição da hash de clientes para o mes pretendido se o total de compras for diferente de 0, 
	atualiza o total de compras com o total e, de forma a garantir que não existem clientes repetidos
	verifica se o codigo do cliente em questão existe numa lista de clientes passado argomento da função, 
	caso não existe insere, por fim, na função principal a total de elementos do lista de clientes é o 
	total de clientes distintos.
- -*/

ComprasEClientes getComprasEClientesMes(Compras* c, int mes){
	int i = 0;
	ComprasEClientes cc = initComprasEClientes();
	ResultCompras r = NULL; /*Para evitar ter clientes repetidos...*/
	Compras compras;
	compras = NULL;
	
	while(i<26){
		compras = c[i];
		cc = totalComprasEclientesmes(cc,&r,&compras,mes);
		i++;
	}
	atualizaElemClientes(cc,lenghtResultCompras(r));
	return cc;
}


/*----------------------------------------- - - - - - - - - - - - - - - - 
	Querie 12
------------------------------------------- - - - - - - - - - - - - - - -*/
/*Dado um registo de compras, o top que desejar, devolve um ResultCompras12, com a API acima retorna um ResultCompras12 
com os códigos dos produtos, total de compras e vendas dos produtos que constituem o top desejado*/

static ComprasEClientes totalComprasEclientesTree(ComprasEClientes p, Clientes *cl){
	if(*cl != NULL){
		int i = 0;
		int compras = 0;
		while(i<12){
			compras += (*cl)->totalCompras[i].total;
			i++;
		}
		p->compras += compras;
		p->clientes += 1;
	p = totalComprasEclientesTree(p,&(*cl)->left);
	p = totalComprasEclientesTree(p,&(*cl)->right);
	}
	return p;
}


static ResultCompras12 totalComprasEclientes(ResultCompras12 r,Compras *c, int top){
	if(*c != NULL){
			int i = 0;
			Clientes* cl = NULL;
			Clientes clientes = NULL;
			ComprasEClientes p = initComprasEClientes();
			cl = (*c)->listaClientes;
			while(i<26){
				clientes = cl[i];
				p = totalComprasEclientesTree(p,&clientes);
				i++;
			}
			if(lenghtResultCompras12(r)<top){
				r = insereElementoResultCompras12(r,p->compras,p->clientes,(*c)->codP);
			}
			else{
				ResultCompras12 aux = r;
				int ok = 1;
				int compras = p->compras;
				while(aux!=NULL && ok){
					if(aux->total_compras < compras){
						aux->codigo_produto = strcpy(aux->codigo_produto,(*c)->codP);
						aux->total_compras = compras;
						aux->total_clientes = p->clientes;
						ok=0;
					}
					aux=aux->next;
				}
			}
	r = totalComprasEclientes(r,&(*c)->left,top);
	r = totalComprasEclientes(r,&(*c)->right,top);
	}
	return r;
}


/*- -
	Dado o top que se deseja, a função topprodutos(...) tem de percorrer todos os produtos, para isso,
	para cada posição da hash de produtos, invoca a função totalComprasEclientes(...), esta que para cada
	posição da hash de clientes, invoca a função totalComprasEclientesTree(...) que para cada nodo da avl
	de produtos, atualiza o total de compras, bem como imprementa o numero de clientes de nodo para nodo.
	Quando acada de percorrer todos a a avl e retorna a função totalComprasEclientes(...), esta verifica 
	que, se a lista de retorna aindan nao tem o numero total do top desejado, insere o total de compras, 
	clientes e código, caso já esteja completo, percorre essa lista, se o total de compras for superior
	a um dos nodos que já esteja inserido, substitui os valores, assim a lista fica permanetemente ordenada
	por numero de compras.
- -*/

ResultCompras12 topProdutos(Compras* c, int top){
	int i = 0;
	Compras compras = NULL;
	ResultCompras12 r = NULL;
	while(i<26){
		compras = c[i];
		r = totalComprasEclientes(r,&compras,top);
		i++;
	}
	return r;
}


/*----------------------------------------- - - - - - - - - - - - - - - - 
	Querie 13
------------------------------------------- - - - - - - - - - - - - - - -*/
/*Dado um registo de compras e um código de cliente devolve um ResultCompras9 com os produtos(total comprado) e produtos  
com esse total(ResultCompras) do cliente fornecido */

static int totalUnidadesCompradasPorCliente(Clientes *cl, char* codigo_cliente){
	int total = 0;
	if(*cl == NULL) return 0;
	else{
		Clientes c = *cl;
		int i = 0;
		int ok = 1;
		while(c != NULL && ok){
			if(strcmp(c->codC,codigo_cliente) == 0){
				while(i<12){
					total += c->totalCompras[i].total;
					i++;
				}
				ok = 0;
			}
			else{
				if(strcmp(c->codC,codigo_cliente) > 0)
					c = c->left;
						else c = c->right;	
			}
		}
	}
	return total;
}

static ResultCompras9 protudosComprdosPorClienteTree(ResultCompras9 r, Compras *c, char* codigo_cliente){
	if(*c != NULL){
		int key = hashFunc(codigo_cliente);
		int total = 0;
		Clientes* cl = NULL;
		Clientes clientes = NULL;
		cl = (*c)->listaClientes;
		clientes = cl[key];
		if(existeCliente(clientes, codigo_cliente) == 0){
			total =  totalUnidadesCompradasPorCliente(&clientes,codigo_cliente);
			r = insereElementoResultComprasQuerie9(r,total,(*c)->codP);
		}
		r = protudosComprdosPorClienteTree(r,&(*c)->left,codigo_cliente);
		r = protudosComprdosPorClienteTree(r,&(*c)->right,codigo_cliente);
	}
	return r;
}


static ResultCompras9 getTop3Result(ResultCompras9 r){
	ResultCompras9 new = NULL;
	ResultCompras rc = NULL;
	int t = 0;
	int top = 3;
	if(r == NULL) return NULL;
	if((t = lenghtResultCompras9(r)) <= 3) return r;
	else{
		while(r!=NULL && top){
			rc = getProdutosResultCompras9(r);
			while(rc!=NULL && top){
				new = insereElementoResultComprasQuerie9(new,r->total,rc->elem);
				rc = rc->next;
				top--;
			}
			r = r->next;
		}
	}
	return new;
}

/*- -
	Dado um codigo de cliente, esta função percorre todas as posições da hash de compras, para cada uma
	invoca a função protudosComprdosPorClienteTree(...), esta que para cada nodo verifica se o cliente
	existe como comprador do produto do nodo em questão, se sim invova a função 
	totalUnidadesCompradasPorCliente(...) de forma a obter o numero total de compras do cliente para
	aquele produto, inserindo o cliente com o total de compras na estrutura, repetindo o proceso para 
	todos os produtos. Quando terminar, a função principal incoca a função getTop3Result(...) de forma
	a retornar o top 3. 
- -*/

ResultCompras9 topProdutosCompradosPorCliente(Compras* c, char* codigo_cliente){
	int i = 0;
	Compras compras = NULL;
	ResultCompras9 r = NULL;
	while(i<26){
		compras = c[i];
		r = protudosComprdosPorClienteTree(r,&compras,codigo_cliente);
		i++;
	}
	r = getTop3Result(r);
	return r;
}

/*----------------------------------------- - - - - - - - - - - - - - - - 
	Querie 14
------------------------------------------- - - - - - - - - - - - - - - -*/
/*Retorna o numero dos diferentes clientes que efetuaram compras*/

static ResultCompras totalClientesTree(ResultCompras r, Clientes *cl){
	if(*cl != NULL){
		if(existeElemResultCompras(&r,(*cl)->codC) == 0)
				r = insereElementoResultCompras(r,(*cl)->codC);
		r = totalClientesTree(r,&(*cl)->left);
		r = totalClientesTree(r,&(*cl)->right);
	}
	return r;
}

static ResultCompras totalClientes(ResultCompras r, Compras *c){
	if(*c != NULL){
		int i = 0;
		Clientes* cl = NULL;
		Clientes clientes = NULL;

		cl = (*c)->listaClientes;
		while(i<26){
			clientes = cl[i];
			r = totalClientesTree(r,&clientes);
			i++;
		}
		r = totalClientes(r,&(*c)->left);
		r = totalClientes(r,&(*c)->right);
	}
	return r;
}

/*- -
	A função numeroTotalDeClientes(...) devolve o numero de clientes distindos que efetuaram compras, 
	para cada produto, para cada clinte que encontra, verifica se já existe na estrutura, se já existe
	é porque já comprou outro produto anteriormente, se não existe insere. Por fim retorna o numero
	total de elementos da estrutura.
- -*/

int numeroTotalDeClientes(Compras* c){
	int i = 0;
	ResultCompras clientes = NULL;   /*Para evitar ter clientes repetidos...*/
	Compras compras = NULL;
	while(i<26){
		compras = c[i];
		clientes = totalClientes(clientes,&compras);
		i++;
	}
	i = lenghtResultCompras(clientes);
	return i;
}




/*##########################################################################*/
/*##########################################################################*/





