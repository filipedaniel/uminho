#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "contabilidade.h"


/***************************************************************************/
/*			ESTRUTURAS DE DADOS DO MODULO CONTABILIDADE 				   */
/***************************************************************************/

/*----------------------------------------- - - - - - - - - - - - - - - -
	Estrutura para definir a contabilidade para cada mês
------------------------------------------- - - - - - - - - - - - - - - -*/
typedef struct contabilidadeMensal
{
	int vendidosN;
	int vendidosP;
	float totalN;
	float totalP;
}ContabilidadeMensal;

/*----------------------------------------- - - - - - - - - - - - - - - -
	Estrutura da AVL para registar a contabilidade
------------------------------------------- - - - - - - - - - - - - - - -*/
/*Para cada produto a sua contabilidade em todos os meses*/

typedef struct contabilidade{
	char* codP;
	ContabilidadeMensal totalMensal[12];
	unsigned alt;
	struct contabilidade *right, *left;
}Node;


/*#########################################################################*/
/***************************************************************************/
/*				Estruturas de dados para retorno da queries 			   */
/***************************************************************************/

/*----------------------------------------- - - - - - - - - - - - - - - - 
	Estrutura de retorno da maior parte das queries implementadas
------------------------------------------- - - - - - - - - - - - - - - -*/
struct resultContabilidade
{
	struct resultContabilidade *next;
	struct resultContabilidade *prev;
	char* elem;
};


/*----------------------------------------- - - - - - - - - - - - - - - - 
	Estrutura de retorna um par de inteitor
------------------------------------------- - - - - - - - - - - - - - - -*/
struct par
{
	int fst;
	int snd;
};


/*##########################################################################*/
/****************************************************************************/
/*			Funçoes para manipular cada estrutura de retorno				*/
/****************************************************************************/

/*----------------------------------------- - - - - - - - - - - - - - - - 
	struct resultContabilidade
------------------------------------------- - - - - - - - - - - - - - - -*/

static ResultContabilidade criaNo(char* cod){
	ResultContabilidade no;
	if((no = (ResultContabilidade) malloc(sizeof(struct resultContabilidade))) == NULL) return NULL;
	if((no->elem = (char*) malloc(sizeof(cod))) == NULL){
		free(no);
		return NULL;
	}
	no->elem = strcpy(no->elem,cod);
	no->next = NULL;
	no->prev =NULL;
	return no;
}

static void destroiNoResultContabilidade(ResultContabilidade *no){
	free((*no)->elem);
	free(*no);
	*no = NULL;
}

static ResultContabilidade insereAux(ResultContabilidade r,char* cod);

static ResultContabilidade insereElementoResultContabilidade(ResultContabilidade r, char* cod){
	ResultContabilidade new;
	ResultContabilidade noAIns;
	if((new = criaNo(cod)) == NULL) return NULL;
	if(r == NULL || strcmp(r->elem,cod) > 0){
		new->next = r;
		if(new->next != NULL) 
			new->next->prev = new;
		r = new;
	}
	else{
		noAIns = insereAux(r,cod); /*Nó anterior a inserir o novo elemento*/
		if(noAIns != NULL){
			new->prev = noAIns;
			new->next = noAIns->next;
			noAIns->next = new;
			if(new->next != NULL) new->next->prev = new;
		}
		else destroiNoResultContabilidade(&new);
	}
	return r;
}

static ResultContabilidade insereAux(ResultContabilidade r,char* cod){
	ResultContabilidade nAnt = NULL;
	ResultContabilidade nAct = r;

	while(nAct != NULL && strcmp(nAct->elem,cod) <= 0){
		nAnt = nAct;
		nAct = nAct->next;
	}
	return nAnt;
}

void destroiResultContabildade(ResultContabilidade r){
	ResultContabilidade nAct, nAnt;
        nAct = r;
        while (nAct != NULL){
			nAnt = nAct;
			nAct = nAct->next;
			free (nAnt);
		}
}

int lenghtResultContabilidade(ResultContabilidade r){
	int t= 0;
	while(r != NULL){
		t++;
		r = r->next;
	}
	return t;
}

ResultContabilidade getNextResultContabilidade(ResultContabilidade t){
	return t->next;
}

ResultContabilidade getPrevResultContabilidade(ResultContabilidade t){
	return t->prev;
}

char* getElemResultContabilidade(ResultContabilidade t){
	return t->elem;
}


/*----------------------------------------- - - - - - - - - - - - - - - - 
	struct par
------------------------------------------- - - - - - - - - - - - - - - -*/

Par initPar(){
	Par p = malloc(sizeof(struct par));
	p->fst = 0;
	p->snd = 0;
	return p;
}

int getFstElem(Par p){
	return p->fst;
}

int getSndElem(Par p){
	return p->snd;
}

static void setFstElem(Par p, int e){
	p->fst = e;
}

static void setSndElem(Par p, int e){
	p->snd = e;
}

static void atualizaFstElem(Par p, int e){
	p->fst += e;
}

static void atualizaSndElem(Par p, int e){
	p->snd += e;
}


/*##########################################################################*/
/****************************************************************************/
/*		 				FUNÇÕES DO MODULO DE CONTABILIDADE					*/
/****************************************************************************/


/*Função de hash para aceder as tabelas de hash de produtos*/
static int hashContabilidade(char* key){
	return key[0]-'A';
}

/*Inicializa Contabilidade*/
Contabilidade* initContabilidade(){
	int i = 0;
	Contabilidade* c = malloc(26*sizeof(Node));
	while(i<=25){
		c[i] = NULL;
		i++;
	}
	return c;
}

/*Apaga contabilidade*/
static void deleteContabilidadeTree(Contabilidade c){
 	if (c == NULL) return;
  	deleteContabilidadeTree(c->left);
    deleteContabilidadeTree(c->right);
    free(c);
 }

void deleteContabilidade(Contabilidade* c){
	int i = 0;
	while(i<=25){
		if (c[i] == NULL) return;
		else
			deleteContabilidadeTree(c[i]);
		i++;
	}
 }

/*Verifica se um elemento (código) existe na contabilidade*/
int existeNaContabilidade(Contabilidade* c,char* cod){
	int res = -1;
	int key = hashContabilidade(cod);
	Contabilidade aux = malloc(sizeof(struct contabilidade));
	aux = c[key];
	while(aux != NULL){
			if(strcmp(aux->codP,cod) == 0){
				res = 0;
				return res;
			}
			if(strcmp(aux->codP,cod)>0)
				aux=aux->left;
			else
				aux = aux->right;
	}
	return res;
}

/*Verifica se um elemento (codigo existe na AVL correspondente aos elementos 
								com o mesma hash do elemnto a verificar)*/
static int existeProduto(Contabilidade tree, char* codP){
	if(!tree) return -1;
	if(strcmp(tree->codP,codP) == 0) return 0;
	else{
		if(strcmp(tree->codP,codP)>0)
			return existeProduto(tree->left,codP);
		else
			return existeProduto(tree->right,codP);
	}
}


static void equilibrarAVL(Contabilidade *c);
static void addContabilidadeTree(Contabilidade *tree, char* codP,float preco, int unidades,char tipo,int mes);
static void atualizaContabilidade(Contabilidade *t, char* codP,float preco, int unidades,char tipo,int mes);

/*Adiciona Um elemento a Contabilidade - invoca a função: 
		-> addContabilidadeTree() caso o código não existir, passando a AVL correspondente ao hash do código 
		-> atualizaContabilidade() caso o código já existir na contabilidade, entao atualiza os valores. 									*/
Contabilidade* addCompraToContabilidade(Contabilidade* c, char* codP,float preco,int unidades, char tipo, int mes){
	int key = hashContabilidade(codP);
	if(existeProduto(c[key],codP) == 0)
		atualizaContabilidade(&c[key],codP,preco,unidades,tipo,mes);
	else
		addContabilidadeTree(&c[key],codP,preco,unidades,tipo,mes);
	return c;
}

/*Insere um elemento na AVL correspondente a contabilidade do código*/
static void addContabilidadeTree(Contabilidade *tree, char* codP,float preco, int unidades,char tipo,int mes){
	if(*tree == NULL) {
		int i=0;
		Contabilidade new = malloc(sizeof(Node));
		new-> codP = malloc(strlen(codP)+1);
		new-> codP = strcpy(new->codP,codP);
		while(i<12){
			new->totalMensal[i].vendidosN = 0;
			new->totalMensal[i].vendidosP = 0;
			new->totalMensal[i].totalN = 0;
			new->totalMensal[i].totalP = 0;
			i++;
		}
		if(tipo=='N'){
			new->totalMensal[mes-1].vendidosN = unidades;
			new->totalMensal[mes-1].totalN = preco*unidades;
		}
		else{
			new->totalMensal[mes-1].vendidosP = unidades;
			new->totalMensal[mes-1].totalP = preco*unidades;
		}
		new->right = NULL;
		new->left = NULL;
		*tree = new;
		}
		else{
			if(strcmp((*tree)->codP,codP)>0)
				addContabilidadeTree(&(*tree)->left,codP,preco,unidades,tipo,mes);
			else 
				addContabilidadeTree(&(*tree)->right,codP,preco,unidades,tipo,mes);
		}
		equilibrarAVL(tree);
}

/* Actualiza a contabilidade de um determinado elemento (código)*/
static void atualizaContabilidade(Contabilidade *t, char* codP,float preco, int unidades,char tipo,int mes){
	if(strcmp((*t)->codP,codP) == 0){
		if(tipo == 'N'){
			(*t)->totalMensal[mes-1].vendidosN += unidades;
			(*t)->totalMensal[mes-1].totalN += unidades*preco;
		}
			else{
				(*t)->totalMensal[mes-1].vendidosP += unidades;
				(*t)->totalMensal[mes-1].totalP += unidades*preco;
			}
	}
	else{
		if(strcmp((*t)->codP,codP)>0) 
			atualizaContabilidade(&(*t)->left,codP,preco,unidades,tipo,mes);
		else 
			atualizaContabilidade(&(*t)->right,codP,preco,unidades,tipo,mes);
	}
}

/* Como é obrigatório caregar todos os produtos mesmo os que nunca tenham sido vendidos
esta função insere um determinado produto com os dados contabilisticos a zero.			*/
static void addProduto(Contabilidade *t, Contabilidade new);

Contabilidade* addProdutoToContabilidade(Contabilidade* c, char* codP){
	int i = 0;
	int key = hashContabilidade(codP);

	Contabilidade new = malloc(sizeof(struct contabilidade));
	new->codP = malloc(strlen(codP)+1);
	new->codP = strcpy(new->codP,codP);
	
	while(i<12){
			new->totalMensal[i].vendidosN = 0;
			new->totalMensal[i].vendidosP = 0;
			new->totalMensal[i].totalN = 0;
			new->totalMensal[i].totalP = 0;
			i++;
	}
	new->right = NULL;
	new->left = NULL;
	addProduto(&c[key],new);
	return c;
}

static void addProduto(Contabilidade *t, Contabilidade new){
	if(*t == NULL) *t=new;
	else{
		if(strcmp((*t)->codP,new->codP)>0) 
			addProduto(&(*t)->left,new);
		else 
			 addProduto(&(*t)->right,new);
	}
	equilibrarAVL(t);
}


/*Remove um elemento da contabilidade */
static void removeElemento(Contabilidade *t, char* cod);

Contabilidade* removeElemContabilidade(Contabilidade* c, char* cod){
	int key = hashContabilidade(cod);
	removeElemento(&c[key],cod);
	return c;
}

static void removeElemento(Contabilidade *t, char* cod){
	if(*t == NULL) return;

	if(strcmp((*t)->codP,cod)>0)
		removeElemento(&(*t)->left,cod);
	else if(strcmp((*t)->codP,cod)<0)
			removeElemento(&(*t)->right,cod);
		else{
			Contabilidade old;
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
				Contabilidade heir = (*t)->left; 
		      	Contabilidade prev = *t; 
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
		equilibrarAVL(t);
}

/*Retorna a numero de nós de uma AVL Contabilidade*/
static int contaNosContabiliadade(Contabilidade c){
   if(c == NULL)
        return 0;
   else
        return 1 + contaNosContabiliadade(c->left) + contaNosContabiliadade(c->right);
}


/*##########################################################################*/
/****************************************************************************/
/*		 				FUNÇÕES DO PARA MANIPULAR A AVL					*/
/****************************************************************************/

/*Altura da AVL de compras*/
static unsigned int alturaAVL(Contabilidade c){
	if(c == NULL) return 0;
	return c->alt;
}

/*Rotação simples para a direita*/
static void rotacaoSimplesDireita(Contabilidade *c){
	unsigned int altEsq, altDir;

	Contabilidade no = (*c)->left;
	(*c)->left = no->right;
	no->right = *c;

	altEsq = alturaAVL((*c)->left);
	altDir = alturaAVL((*c)->right);
	(*c)->alt = altEsq > altDir ? altEsq + 1 : altDir + 1;

	altEsq = alturaAVL(no->left);
	altDir = (*c)->alt;
	no->alt = altEsq > altDir ? altEsq +1 : altDir + 1;

	*c = no;
}

/*Rotação simples para a esquerda*/
static void rotacaoSimplesEsquerda(Contabilidade *c){
	unsigned int altEsq,altDir;

	Contabilidade no = (*c)->right;
	(*c)->right = no->left;
	no->left = *c;

	altEsq = alturaAVL((*c)->left);
	altDir = alturaAVL((*c)->right);
	(*c)->alt = altEsq > altDir ? altEsq + 1 : altDir + 1;

	altEsq = (*c)->alt;
	altDir = alturaAVL(no->right);
	no->alt = altEsq > altDir ? altEsq +1 : altDir + 1;

	*c = no;
}

/*Rotação dupla: direita/esquerda*/
static void rotacaoDuplaDireitaEsquerda(Contabilidade *c){
	rotacaoSimplesDireita(&(*c)->right);
	rotacaoSimplesEsquerda(c);
}

/*Rotação dupla: esquerda/direita*/
static void rotacaoDuplaEsquerdaDireita(Contabilidade *c){
	rotacaoSimplesEsquerda(&(*c)->left);
	rotacaoSimplesDireita(c);
}

/*Balancear a avl de compras*/
static void equilibrarAVL(Contabilidade *c){
	unsigned int altDir,altEsq;
	if(*c == NULL) return;

	altEsq = alturaAVL((*c)->left);
	altDir = alturaAVL((*c)->right);

	if(altEsq - altDir == 2){	/*Sub arvore esquerda desiquilibrada*/

		altEsq = alturaAVL((*c)->left->left);
		altDir = alturaAVL((*c)->left->right);
		if(altEsq >= altDir){
			rotacaoSimplesDireita(c);}
		else
			rotacaoDuplaEsquerdaDireita(c);
	}
	else{
		if(altDir - altEsq == 2){	/*Sub arvore direita desiquilibrada*/
			altDir = alturaAVL((*c)->right->right);
			altEsq = alturaAVL((*c)->right->left);
			if(altDir >= altEsq)
				rotacaoSimplesEsquerda(c);
			else
				rotacaoDuplaDireitaEsquerda(c);
		}
		else 
			(*c)->alt = altEsq > altDir ? altEsq +1 : altDir +1;
	}
}


/*##########################################################################*/
/*##########################################################################*/

/****************************************************************************/
/*		 							QUERIES 								*/
/****************************************************************************/


/*----------------------------------------- - - - - - - - - - - - - - - - 
	Querie 5
------------------------------------------- - - - - - - - - - - - - - - -*/
/* Dado um mês e um código de produto válidos, determinar e apresentar o 
   número total de vendas em modo N e em modo P, e o total facturado com 
   esse produto em tal mes ;*/

/*Para esta query foi optado por dividir o problema em tres, 
		total de vendas normal, 
		total de vemdas promoção,
		total faturado 	*/

/*Total de vendas em tipo normal(N) de um produto para um determinado mes*/
static int totalDeVendasNormal(Contabilidade *c, char* cod,int mes){
	int total = 0;
	if(*c == NULL) return total;
	if(strcmp((*c)->codP,cod)==0){
		total = (*c)->totalMensal[mes-1].vendidosN;
		return total;}
	if(strcmp((*c)->codP,cod)>0)
		return totalDeVendasNormal(&(*c)->left,cod,mes);
	else
		return totalDeVendasNormal(&(*c)->right,cod,mes);
}
int totalDeVendasNormalMes(Contabilidade* c,char* cod,int mes){
	int key = hashContabilidade(cod);
	return totalDeVendasNormal(&c[key],cod,mes);
}

/*Total de vendas em tipo promução(P) de um produto para um determinado mes*/
static int totalDeVendasPromocao(Contabilidade*c, char* cod, int mes){
	int total = 0;
	if(*c == NULL) return total;
	if(strcmp((*c)->codP,cod)==0) {
		total = (*c)->totalMensal[mes-1].vendidosP;
		return total;}
	if(strcmp((*c)->codP,cod)>0)
		return totalDeVendasPromocao(&(*c)->left,cod,mes);
	else
		return totalDeVendasPromocao(&(*c)->right,cod,mes);
}
int totalDeVendasPromocaoMes(Contabilidade* c,char* cod,int mes){
	int key = hashContabilidade(cod);
	return totalDeVendasPromocao(&c[key],cod,mes);
}

/**/

/*Total de faturado de compras tipo normal(N) e promoção(P) de um produto para um determinado mes*/
static float totalFaturado(Contabilidade *c, char* cod, int mes){
	float total = 0;
	if(*c == NULL) return total;
	if(strcmp((*c)->codP,cod)==0) {
		total += (*c)->totalMensal[mes-1].totalN;
		total += (*c)->totalMensal[mes-1].totalP;
		return total;
	}
	if(strcmp((*c)->codP,cod)>0)
		return totalFaturado(&(*c)->left,cod,mes);
	else
		return totalFaturado(&(*c)->right,cod,mes);
}
float totalFaturadoMes(Contabilidade *c,char* cod,int mes){
	int key = hashContabilidade(cod);
	float total = 0;
	total = totalFaturado(&c[key],cod,mes);
	return total;
}

/*----------------------------------------- - - - - - - - - - - - - - - - 
	Querie 4
------------------------------------------- - - - - - - - - - - - - - - -*/
/* Determinar a lista de códigos de produtos (e o seu numero total), que ninguém comprou;*/

static ResultContabilidade checkProduto(ResultContabilidade r, Contabilidade *c){
	if(*c != NULL){
		int i = 0;
		int ok = -1;
		for (i = 0; i<12 && ok == -1; i++){
			if( ((*c)->totalMensal[i].vendidosN != 0) || ((*c)->totalMensal[i].vendidosP != 0)){
				ok = 0;
			}
		}
		if(ok != 0){
			r = insereElementoResultContabilidade(r,(*c)->codP);
		}
		r = checkProduto(r,&(*c)->left);
		r = checkProduto(r,&(*c)->right);
	}
	return r;
}

/*- -
	Para cada posição da hash de produtos, invoca a função checkProduto(...) que para cada nodo de cada
	avl, verifica se os campos referentes aos valores de vendas de cada produto esta a zero, ou seja, 
	não existiu compras, casso se verifique adiciona a codigo do produto na estrutura de retorno.
- -*/

ResultContabilidade produtosSemComprador(Contabilidade* c){	
	int i=0;
	ResultContabilidade r = NULL;
	for (i=0;i<26;i++){
		r = checkProduto(r,&c[i]);
	}
	return r;
}


/*----------------------------------------- - - - - - - - - - - - - - - - 
	Querie 7
------------------------------------------- - - - - - - - - - - - - - - -*/
/*Dado um intervalo fechado de meses, por exemplo [2..6], determinar o 
  total de compras registadas nesse intervalo e o total facturado;*/


/*Devolve um par como resultado, total de compras e total faturado*/
static Par querie7aux(Par p, Contabilidade *c, int m1, int m2){
	if(*c != NULL){
		int i = 0;
		int totalCompras = 0;
		float totalFaturado = 0;
		for (i=m1; i <= m2; i++)
		{	
			totalCompras = ((*c)->totalMensal[i-1].vendidosN) + ((*c)->totalMensal[i-1].vendidosP);
			totalFaturado = ((*c)->totalMensal[i-1].totalN) + ((*c)->totalMensal[i-1].totalP);
			atualizaFstElem(p,totalCompras);
			atualizaSndElem(p,totalFaturado);
		}
		querie7aux(p,&(*c)->left,m1,m2);
		querie7aux(p,&(*c)->right,m1,m2);
	}
	return p;
}

/*- -
	Dado dois meses, a função totalComprasEFaturadoEntreMes(...) para cada posição da hash de produtos
	invoca uma função auxiliar, querie7aux(...), esta que para cada nodo de cada avl, deremina o total 
	vendido e total faturado, entre os dois meses pedidos.
- -*/

Par totalComprasEFaturadoEntreMes(Contabilidade* c, int mes1, int mes2){
	int i;
	int m1;
	int m2;
	Par p = initPar();
	if(mes1<mes2){m1=mes1;m2=mes2;} else{m1=mes2;m2=mes1;} 
	for (i=0;i<26;i++)
	{
		p = querie7aux(p,&c[i],m1,m2);
	}
	return p;
}


/*##########################################################################*/
/*##########################################################################*/


