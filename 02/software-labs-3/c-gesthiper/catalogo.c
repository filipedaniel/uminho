#include <stdio.h>
#include <string.h>
#include <stdlib.h>

#include "catalogo.h"


/***************************************************************************/
/*				ESTRUTURAS DE DADOS DO MODULO CATALOGO 					   */
/***************************************************************************/

/*----------------------------------------- - - - - - - - - - - - - - - -
	Estrutura da AVL que constitui um Catalogo
------------------------------------------- - - - - - - - - - - - - - - -*/
struct catalogo
{
	char* cod;
	unsigned int alt;
	struct catalogo *right, *left;
};


/***************************************************************************/
/*				Estruturas de dados para retorno da queries 			   */
/***************************************************************************/

typedef struct result
{
	char* elem;
	struct result *next;
	struct result *prev;
}resultQueries;



/*##########################################################################*/

/****************************************************************************/
/*			Funçoes para manipular a estrutura de retorno				    */
/****************************************************************************/


static Result criaNo(char* cod){
	Result no;
	if((no = (Result) malloc(sizeof(struct result))) == NULL) return NULL;
	if((no->elem = (char*) malloc(sizeof(cod))) == NULL){
		free(no);
		return NULL;
	}
	no->elem = strcpy(no->elem,cod);
	no->next = NULL;
	no->prev =NULL;
	return no;
}

static void destroiNoResult(Result *no){
	free((*no)->elem);
	free(*no);
	*no = NULL;
}

static Result insereAux(Result r,char* cod);

static void insereElementoResult(Result *r, char* cod){
	Result new;
	Result noAIns;
	if((new = criaNo(cod)) == NULL) return;
	if(*r == NULL || strcmp((*r)->elem,cod) > 0){
		new->next = *r;
		if(new->next != NULL) 
			new->next->prev = new;
		*r = new;
	}
	else{
		noAIns = insereAux(*r,cod); /*Nó anterior a inserir o novo elemento*/
		if(noAIns != NULL){
			new->prev = noAIns;
			new->next = noAIns->next;
			noAIns->next = new;
			if(new->next != NULL) new->next->prev = new;
		}
		else destroiNoResult(&new);
	}
}

static Result insereAux(Result r,char* cod){
	Result nAnt = NULL;
	Result nAct = r;

	while(nAct != NULL && strcmp(nAct->elem,cod) <= 0){
		nAnt = nAct;
		nAct = nAct->next;
	}
	return nAnt;
}

void destroiResult(Result *r){
	Result No;
    while (*r != NULL){
		No = *r;
		*r = (*r)->next;
		destroiNoResult(&No);
    }
}

int lenghtResult(Result r){
	int t= 0;
	while(r != NULL){
		t++;
		r = r->next;
	}
	return t;
}

Result getNext(Result t){
	return t->next;
}

Result getPrev(Result t){
	return t->prev;
}

char* getElem(Result t){
	return t->elem;
}

/*Converter uma AVL para uma lista Biligada de forma ordenada*/
static void avlToResultOrder(Catalogo c, Result *r){
	if(c != NULL){
		avlToResultOrder(c->left,r);
		insereElementoResult(&(*r),c->cod);
		avlToResultOrder(c->right,r);
	}
}


/*##########################################################################*/

/****************************************************************************/
/*		 					FUNÇÕES DO MODULO DE CATALOGO					*/
/****************************************************************************/

/*Função de hash para aceder as tabelas de hash do catalogo*/
static int hashCatalogo(char* key){
	return key[0]-'A';
}

/*Inicializa Catalogo*/
Catalogo* initCatalogo(){
	Catalogo* c = malloc(26*sizeof(struct catalogo));
	int i = 0;
	while(i<=25){
		c[i] = NULL;
		i++;
	}
	return c;
}

/*Apaga Catalogo*/
static void deleteCatalogoTree(Catalogo t){
 	if (t == NULL) return;
  	deleteCatalogoTree(t->left);
    deleteCatalogoTree(t->right);
    free(t);
 }

void deleteCatalogo(Catalogo* c){
	int i = 0;
	for (i=0;i<=25;i++){
		if (c[i] == NULL) return;
		else{
			deleteCatalogoTree(c[i]);
		}
	}
}

/*Verifica a existência de um Código no Catalogo*/
int existeNoCatalogo(Catalogo* c,char* cod){
	int key = hashCatalogo(cod);
	int res = -1;
	Catalogo aux = malloc(sizeof(struct catalogo));
	aux = c[key];
	while(aux != NULL){
		if(strcmp(aux->cod,cod) == 0){
			res = 0;
			return res;
		}
		if(strcmp(aux->cod,cod)>0){
			aux = aux->left;
		}
		else{
			aux = aux->right;
		}
	}
	return res;
}

static void equilibrarAVL(Catalogo *c);
static void addCatalogoTree(Catalogo *tree, Catalogo new);

/*Adiciona Uma elemento ao Catalogo - invoca a função:
		-> addCatalogoTree() passando a  AVL correspondente ao hash do código */
Catalogo* addToCatalogo(Catalogo* c, char* cod){
	int key = hashCatalogo(cod);
	Catalogo new = malloc(sizeof(struct catalogo));
	new-> cod = malloc(strlen(cod)+1);
	new -> cod = strcpy(new->cod,cod);
	new -> right = NULL;
	new -> left = NULL;
	addCatalogoTree(&c[key],new);
	return c;
}
	
/*Insere um elemento na AVL correspondente ao catalogo*/
static void addCatalogoTree(Catalogo *tree, Catalogo new){
	if(*tree == NULL) {*tree = new;}
	else{
		if(strcmp((*tree)->cod,new->cod)>0) 
			addCatalogoTree(&(*tree)->left,new);
		else 
			addCatalogoTree(&(*tree)->right,new);
	}
	equilibrarAVL(tree);
}

static void removeElemento(Catalogo *t, char* cod);

	/*Remove um elemento do catalogo - invoca o função removeElemento() passando a 
														AVL correnpondente ao hash do código*/
Catalogo* removeElemCatalogo(Catalogo* c, char* cod){
	int key = hashCatalogo(cod);
	removeElemento(&c[key],cod);
	return c;
}

static void removeElemento(Catalogo *t, char* cod){
	if(*t == NULL) return;
	if(strcmp((*t)->cod,cod)>0)
		removeElemento(&(*t)->left,cod);
	else if(strcmp((*t)->cod,cod)<0)
			removeElemento(&(*t)->right,cod);
		else{
			Catalogo old;
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
				Catalogo heir = (*t)->left; 
		      	Catalogo prev = *t; 
		      	while ( heir->right != NULL ) { 
		        	prev = heir; 
		        	heir = heir->right; 
		      	} 
		      	strcpy((*t)->cod,heir->cod);
		      	if ( prev != *t ) 
		        	prev->right = heir->left; 
		      	else
		        	prev->left = heir->left; 
		      	free (heir); 
    		} 
		}
		equilibrarAVL(t);
}


/*##########################################################################*/

/****************************************************************************/
/*		 				FUNÇÕES DO PARA MANIPULAR A AVL					*/
/****************************************************************************/

/*Altura da AVL*/
static unsigned int alturaAVL(Catalogo c){
	if(c == NULL) return 0;
	return c->alt;
}

/*Rotação simples para a direita*/
static void rotacaoSimplesDireita(Catalogo *c){
	unsigned int altEsq, altDir;

	Catalogo no = (*c)->left;
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
static void rotacaoSimplesEsquerda(Catalogo *c){
	unsigned int altEsq,altDir;

	Catalogo no = (*c)->right;
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
static void rotacaoDuplaDireitaEsquerda(Catalogo *c){
	rotacaoSimplesDireita(&(*c)->right);
	rotacaoSimplesEsquerda(c);
}

/*Rotação dupla: esquerda/direita*/
static void rotacaoDuplaEsquerdaDireita(Catalogo *c){
	rotacaoSimplesEsquerda(&(*c)->left);
	rotacaoSimplesDireita(c);
}

/*Balancear a AVL*/
static void equilibrarAVL(Catalogo *c){
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

/*Total de nos na AVL*/
static int contaNosCatalogo(Catalogo c){
   if(c == NULL) return 0;
   else
        return 1 + contaNosCatalogo(c->left) + contaNosCatalogo(c->right);
}


/*##########################################################################*/
/*##########################################################################*/

/****************************************************************************/
/*		 							QUERIES 								*/
/****************************************************************************/

/***** Querie 2 ***||*** Quierie 6 *
1 - Determinar a lista e o total de produtos cujo código se inicia por uma dada letra (maiúscula); Apresentar tal lista ao utilizador 
		e permitir que o mesmo navegue na mesma, sendo tal lista apresentada por ordem alfabética;

5 - Determinar a lista de todos os autores cujo nome se inicia pela letra dada como
		parâmetro (maiúscula ou minuscula devera ser indiferente);	*/

/*- -
	Dado um cotalo e uma letra, devolve um Result com todos os codigos que começam com essa letra,
	invocando a função avlToResultOrder(...) para transformar a avl num Result.
- -*/
 Result getCatalogoLetra(Catalogo* c,char l){
	int key = l-'A';
	Result no = NULL;
	Catalogo aux = malloc(sizeof(struct catalogo));
	
	aux = c[key];
	avlToResultOrder(aux,&no);
	return no;
}



/*##########################################################################*/
/*##########################################################################*/





