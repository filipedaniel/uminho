#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <ctype.h>
#include <time.h>

#include "catalogo.h"
#include "contabilidade.h"
#include "compras.h"
#include "boolean.h"

#define SIZEMAX_COMPRAS 64
#define SIZEMAX_PRODUTOS 10
#define SIZEMAX_CLIENTES 10

#define ELEMS_POR_PAGINA 24

#define CLEAR_CONSOLE printf("\033[2J\033[1;1H");

#define PRESS_ENTER_TO_CONTINUE() { 		  \
	printf(COLOR_BLUE"\nPRESS ENTER TO CONTINUE\n\n"COLOR_RESET); \
	while (getchar() != '\n');   			  \
	CLEAR_CONSOLE;				\
}

/*Cores*/
#define COLOR_RED     "\x1b[31m"
#define COLOR_GREEN   "\x1b[32m"
#define COLOR_YELLOW  "\x1b[33m"
#define COLOR_BLUE    "\x1b[34m"
#define COLOR_RESET   "\x1b[0m"


/*#########################################################*/
/**********************************************************/
/*					FUNÇÃO DE TRIM					      */
/**********************************************************/

char *trimming(char *str){
	int i = 0;
    int j = 0;
    
    char *ptr = (char*)malloc(sizeof(char)*100);
    strcpy(ptr,str);

    while(ptr[j]!='\0'){
        if(ptr[j] == ' ' ){
          j++;
          ptr[i] = ptr[j];
      }
      else{
          i++;
          j++;
          ptr[i] = ptr[j];
      }
  }
  ptr[j-1]='\0';
  return ptr;
}


/**********************************************************/
/*					VALIDAR CAMPOS					      */
/**********************************************************/

/*valida codigo do produto*/
int check_codigo_produto(char* c){

	int ok = -1;
	
	if(strlen(c) > 6) return ok;
	if(isalpha(c[0])&&isalpha(c[1])&&isdigit(c[2])&&isdigit(c[3])&&isdigit(c[4])&&isdigit(c[5]))
			ok = 0;

	return ok;
} 

/*valida codigo de cliente*/
int check_codigo_cliente(char* c){
	int ok = -1;
	if(strlen(c) > 5) return ok;
	if(isalpha(c[0])&&isalpha(c[1])&&isdigit(c[2])&&isdigit(c[3])&&isdigit(c[4]))
			ok = 0;
	return ok;
} 

/*valida preço >=0*/
int checkPreco(float p){
	int ok = -1;
	if (p>=0)	ok = 0;
	return ok;
}

/*valida unidades compradas >*/
int checkUnidades(int u){
	int ok = -1;
	if (u>0) ok = 0;
	return ok;
}

/*valida mes, entre 1 e 12*/
int checkMes(int m){
	int ok = -1;
	if ((m>=1)&&(m<=12)) ok = 0;
	return ok;
}

/**********************************************************/
/*				LER E VALIDAR CAMPOS 				      */
/**********************************************************/

/*le o stdin codigo do produto*/
char* ler_CodigoProtudo(){
	char *r = NULL;
	char input[20];
	char *in;
	fgets (input, 20, stdin);
	in = trimming(input);
	if(strlen(in)>6) return r;
	if(isalpha(in[0]) && isalpha(in[1]) && 
		isdigit(in[2]) && isdigit(in[3]) &&
		 isdigit(in[4]) && isdigit(in[5]))
		 {r = in; in[0] = toupper(in[0]);
				  in[1] = toupper(in[1]);
				  in[6] = '\0';}
	return r;
}

/*le o stdin codigo de cliente*/
char* ler_CodigoCliente(){
	char *r = NULL;
	char input[20];
	char *in;
	fgets (input, 20, stdin);
	in = trimming(input);
	if(strlen(in)>5) return r;
	if(isalpha(in[0]) && isalpha(in[1]) && 
		isdigit(in[2]) && isdigit(in[3]) &&
		 isdigit(in[4]))
		 {r = in; in[0] = toupper(in[0]);
				  in[1] = toupper(in[1]);
				  in[5] = '\0';}
	return r;
}

/*le o stdin um caracter*/
char le_caracter(){
	char c = -1;
	char *in;
	char input[5];
	fgets (input,5,stdin);
	in = trimming(input);
	if(strlen(in)>2) return -1;
	if(isalpha(in[0]) && in[1] == '\0'){
		c = in[0];
		c = toupper(c);
	}
	return c;
}

/*le o stdin um inteiro*/
int le_inteiro(){
  	int n = -1;
	char input[20];
	char *in;
	fgets (input, 20, stdin);
	in = trimming(input);
	if(strlen(in)>2) return -1;
	if(isdigit(in[0]))
	{
		if(isdigit(in[1]) || in[1] == '\0')
			n = atoi(in);
	}
	return n;
}

/*le o mes */
int le_mes(){
  	int n = -1;
	char input[20];
	char *in;
	fgets (input, 20, stdin);
	in = trimming(input);
	if(strlen(in)>2) return -1;
	if(isdigit(in[0]))
	{
		if(isdigit(in[1]) || in[1] == '\0')
			n = atoi(in);
			if(n<1 || n>12) return -1;
	}
	return n;
}



/**********************************************************/
/*					MINI PARSER							  */
/**********************************************************/

int parse(char* line,char** codP,float* preco,int* unidades, char*  tipo, char** codC, int* mes){
	int flag = -1;
	float p;
	int u;
	int m;
	int i = 0;
	char* cp1;
	char* cp2;
	char c = 0;
	char* array[6];
	char* aux;
	
	char* delim = " ";
	/*char* parse = strdup(line);*/

	char* parse = malloc(strlen(line)+1);
	parse = strcpy(parse,line);

	
	aux = strtok(parse,delim);


	while(aux && i<=5){
		/*array[i] = strdup(aux);*/

		array[i] = malloc(strlen(aux)+1);
		array[i] = strcpy(array[i],aux);

		aux = strtok(NULL,delim);
		i++;
	}
	if(aux || i<=5) {return -1;}

	p = atof(array[1]);
	u = atoi(array[2]);
	m = atoi(array[5]);

	if((checkPreco(p) == 0)&&(checkUnidades(u) == 0) 
		&&(checkMes(m) == 0)){
			flag = 0;

			/*cp1 = strdup(array[0]);*/
			
			cp1 = malloc(strlen(array[0])+1);
			cp1 = strcpy(cp1,array[0]);
			
			if(check_codigo_produto(cp1) == -1)
				return -1;

			*codP = cp1;
			*preco = p;
			*unidades = u;

			c = array[3][0]; 
			if(c != 'N') 
				if(c != 'P')
					return -1;
			*tipo = c;

			/*cp2 = strdup(array[4]);*/
			cp2 = malloc(strlen(array[4])+1);
			cp2 = strcpy(cp2,array[4]);


			if(check_codigo_cliente(cp2) == -1)
				return -1;
			*codC = cp2;
			*mes = m;
	}

	return flag;
}


/**********************************************************/
/*						MENU				   			  */
/**********************************************************/

void bemVindo(){
	printf("\n\n");
	printf(COLOR_RED"                 _   _     _ \n");                
	printf("  __ _  ___  ___| |_| |__ (_)_ __   ___ _ __ \n");
	printf(" / _` |/ _ \\/ __| __| '_ \\| | '_ \\ / _ \\ '__|\n");
	printf("| (_| |  __/\\__ \\ |_| | | | | |_) |  __/ |  \n"); 
	printf(" \\__, |\\___||___/\\__|_| |_|_| .__/ \\___|_|  \n"); 
	printf(" |___/                      |_|     \n"COLOR_RESET);  
	printf("\n");
	printf(COLOR_BLUE"    Bem Vindo!\n"COLOR_RESET);
}

void printMenu(){		
	printf(COLOR_GREEN"\n\nEscolha uma opção:\n\n");

	printf(COLOR_BLUE "(0)"COLOR_RESET"  - Sair.\n");
	printf(COLOR_BLUE "(1)"COLOR_RESET"  - Dados dos Ficheiros lidos.\n");
	printf(COLOR_BLUE "(2)"COLOR_RESET"  - Total de Produtos por 1ª letra.\n");
	printf(COLOR_BLUE "(3)"COLOR_RESET"  - Registo de vendas de um mês para um determinado produto.\n");
	printf(COLOR_BLUE "(4)"COLOR_RESET"  - Produtos que ninguém comprou.\n");
	printf(COLOR_BLUE "(5)"COLOR_RESET"  - Registo de compras anual de cliente.\n");
	printf(COLOR_BLUE "(6)"COLOR_RESET"  - Nomes de Clientes por 1ª letra.\n");
	printf(COLOR_BLUE "(7)"COLOR_RESET"  - Total de faturado entre dois meses.\n");
	printf(COLOR_BLUE "(8)"COLOR_RESET"  - Clientes que compraram um determinado Produto.\n");
	printf(COLOR_BLUE "(9)"COLOR_RESET"  - Produtos adquiridos por Cliente num determinado mês.\n");		
	printf(COLOR_BLUE "(10)"COLOR_RESET" - Clientes que realizaram compras todos os meses do ano.\n");
	printf(COLOR_BLUE "(11)"COLOR_RESET" - Ficheiro CSV com total de compras e clientes para cada mês.\n");
	printf(COLOR_BLUE "(12)"COLOR_RESET" - Produtos mais vendidos em todo ano.\n");
	printf(COLOR_BLUE "(13)"COLOR_RESET" - Top 3 produtos que um Cliente comprou.\n");
	printf(COLOR_BLUE "(14)"COLOR_RESET" - Nº de Clientes e Produtos não realizaram compras e que nunca foram vendidos.\n");
	
	printf("\nOPÇÃO:...\n");
	printf("> ");
}

/**********************************************************/
/*					FUNÇÕES DE PRINT		   			  */
/**********************************************************/

/*print do resolt (catalogo) */
void printResult(Result r){
	Result aux = r;
	if(aux == NULL) return;
	while(aux != NULL){
		printf("%s\n",getElem(aux));
		aux = getNext(aux);
	}
}


/*auxiliar para conseguir imprimir um dado numero de elementos por pagina*/
Result printResultPagina(Result r, int indice){
	Result aux = r;
	int i = 0;
	if(aux == NULL) return NULL;
	
	if(indice>=0){
		while(aux != NULL && i<ELEMS_POR_PAGINA){
			printf("%s\n",getElem(aux));
			aux = getNext(aux);
			i++;
		}
	}
	else{
		while(i<ELEMS_POR_PAGINA*2){
			aux = getPrev(aux);
			i++;
		}

		i = 0;
		while(aux != NULL && i<ELEMS_POR_PAGINA){
			printf("%s\n",getElem(aux));
			aux = getNext(aux);
			i++;
		}
	}
	return aux;
}


void imprimePorPagina(Result r){
	int l = 0;
	int total_paginas = 0;
	int pagina = 1;
	int inicio = 0;
	int pode_imprimir = 1;
	int c = -1;

	l = lenghtResult(r);
	if(l<ELEMS_POR_PAGINA){
		printResult(r);
	}
	else{
		total_paginas = (int) l/ELEMS_POR_PAGINA + 1;
		while(pagina <= total_paginas)
		{
			int ok = -1;
			if(pode_imprimir == 1){
				CLEAR_CONSOLE;
				printf(COLOR_BLUE"\nExistem um total de %d registos - pagina: %d/%d\n"COLOR_RESET,l,pagina,total_paginas);
				r = printResultPagina(r,inicio);
			}
			if(pagina == total_paginas){
				printf(COLOR_YELLOW"       -Não existem mais registos-\n"COLOR_YELLOW);
				return;
			}
			while(ok != 0) /*ate f/b/q */
			{
				printf(COLOR_BLUE"- front / back / exit (f / b / e): "COLOR_RESET);
				while((c = le_caracter()) == -1){
						printf(COLOR_BLUE"- front / back / exit (f / b / e): "COLOR_RESET);
				}
				if(c == 'F') {	/*imprimir proxima pagina (inicio=1), sai do ciclo e pode imprimir*/
					inicio = 1;
					pagina++;
					ok = 0;
					pode_imprimir = 1;
				}
				else if( c == 'B'){
							if(pagina > 1){  /*Se existe pagina anterior*/
								inicio = -1; /* pagina anterior (-1) */
								pagina --; 
								ok = 0;
								pode_imprimir = 1; 
							}
							else{
								printf(COLOR_YELLOW"Não existe registos anteriores!\n"COLOR_RESET);
								pode_imprimir = 0;  /*não pode imprimir*/
							}
					}else if(c == 'E'){ 
								pagina = total_paginas+1; /*sai do ciclo principal*/
								ok = 0;
							}
			}
		}
	}
	return;
}

/*Imprimir contabilidade por pagina*/
ResultContabilidade printResultContabilidadePagina(ResultContabilidade r, int indice){
	ResultContabilidade aux = r;
	int i = 0;
	if(aux == NULL) return NULL;
	
	if(indice>=0){
		while(aux != NULL && i<ELEMS_POR_PAGINA){
			printf("%s\n",getElemResultContabilidade(aux));
			aux = getNextResultContabilidade(aux);
			i++;
		}
	}
	else{
		while(i<ELEMS_POR_PAGINA*2){
			aux = getPrevResultContabilidade(aux);
			i++;
		}

		i = 0;
		while(aux != NULL && i<ELEMS_POR_PAGINA){
			printf("%s\n",getElemResultContabilidade(aux));
			aux = getNextResultContabilidade(aux);
			i++;
		}
	}
	return aux;
}


/*print result (contabilidade)*/
void printResultContabilidade(ResultContabilidade r){
	ResultContabilidade aux = r;
	if(aux == NULL) return;
	while(aux != NULL){
		printf("%s\n",getElemResultContabilidade(aux));
		aux = getNextResultContabilidade(aux);
	}
}

/*print result (compras)*/
void printResultCompras(ResultCompras r){
	ResultCompras aux = r;
	if(aux == NULL) return;
	while(aux != NULL){
		printf("%s\n",getElemResultCompras(aux));
		aux = getNextResultCompras(aux);
	}
}

/*print result9 (compras)*/
void printResultComprasQuerie9(ResultCompras9 r){
	ResultCompras9 aux = r;
	if(aux == NULL) return;
	while(aux != NULL){
		printf("      Quantidade: %d:\n",getTotalResultCompras(aux));
		printResultCompras(getProdutosResultCompras9(aux));
		aux = getNextResultCompras9(aux);
	}
}

/*print result12 (compras)*/
void printResultComprasQuerie12(ResultCompras12 r, int top){
	int n = 0;
	ResultCompras12 aux = r;
	if(top == 0) top = lenghtResultCompras12(aux);
	if(aux == NULL) return;
	while(aux != NULL && n<top){
		printf("Codigo produto: %s:\n",getCodigoProdutoResultCompras12(aux));
		printf("Compras:  %d\n", getTotaldeComprasResultCompras12(aux));
		printf("Clientes: %d\n", getTotaldeclientesCompras12(aux));
		aux = getNextResultCompras12(aux);
		n++;
	}
}



/**********************************************************/
/*						MAIN				   			  */
/**********************************************************/
int main(int argc, char *argv[])
{
	clock_t beginClock, endClock;
	time_t beginTime,endTime;

	int okFlag = 1;

	int totalProdutos = 0;
	char* filenameP;
	char lineP[SIZEMAX_PRODUTOS];
	FILE* fprodutos;
	Catalogo* catalogoProdutos;


	int totalClientes = 0;
	char* filenameC;
	char lineC[SIZEMAX_CLIENTES];
	FILE* fclientes;
	Catalogo* catalogoClientes;
	Contabilidade* contabilidade;


	int totalCompras = 0;
	char* filenameCmp;
	char lineCmp[SIZEMAX_COMPRAS];
	FILE* fcompras;

	char* codP = malloc(SIZEMAX_PRODUTOS);
	float preco = 0;
	int unidades = 0;
	char tipo = 0;
	char* codC= malloc(SIZEMAX_CLIENTES);
	int mes= 0;
	int compras_validas = 0;
	Compras* compras;

	catalogoProdutos = initCatalogo();
	catalogoClientes = initCatalogo();
	contabilidade = initContabilidade();
	compras = initCompras();

			/* Aceder os ficheiros */
	
	filenameP = malloc(strlen(argv[1])+1);
	filenameP = strcpy(filenameP,argv[1]);
	fprodutos = fopen(filenameP,"r");
	
	filenameC = malloc(strlen(argv[2])+1);
	filenameC = strcpy(filenameP,argv[2]);
	fclientes = fopen(filenameC,"r");
	
	filenameCmp = malloc(strlen(argv[3])+1);
	filenameCmp = strcpy(filenameCmp,argv[3]);
	fcompras = fopen(filenameCmp,"r");

		
	/*Construir as estruturas*/
	printf(COLOR_BLUE"A carregar...\n"COLOR_RESET);
	beginTime = time(NULL);
	
	/*Produtos*/
	if(fprodutos){
		char* codProd;
		while(fgets(lineP,SIZEMAX_PRODUTOS,fprodutos) != NULL){
			codProd = strtok(lineP,"\r\n");
			if(check_codigo_produto(codProd) == 0){
				catalogoProdutos = addToCatalogo(catalogoProdutos,codProd);
				contabilidade = addProdutoToContabilidade(contabilidade,codProd);	
				totalProdutos++;
			}
		}
	}
	else{printf("Erro ao ler de ficheiro de Produtos!\n");okFlag = 0;}

	/*Clientes*/
	if(fclientes){
		char* codCli;
		while(fgets(lineC,SIZEMAX_CLIENTES,fclientes) != NULL){
			codCli = strtok(lineC,"\r\n");
			if(check_codigo_cliente(codCli) == 0){
				catalogoClientes= addToCatalogo(catalogoClientes,codCli);
				totalClientes++;
			}
		}
	}
	else{printf("Erro ao ler o ficheiro de Clientes!\n");okFlag = 0;}

	/*Compras;*/

	if(fcompras){
		int ok = -1;
		char* regComp;
		while(fgets(lineCmp,SIZEMAX_COMPRAS,fcompras) != NULL){
				regComp = strtok(lineCmp,"\r\n");

				ok = parse(regComp,&codP,&preco,&unidades,&tipo,&codC,&mes);
				
				if(ok == -1){
					printf("ERRO NA LEITURA NA LINHA: %d!!!\n",totalCompras+1);
				}
				else{
					if((existeNoCatalogo(catalogoClientes,codC) == True) && (existeNoCatalogo(catalogoProdutos,codP) == True)){
						contabilidade = addCompraToContabilidade(contabilidade,codP,preco,unidades,tipo,mes);
						compras = addToCompras(compras,codP,unidades,tipo,codC,mes);
						compras_validas++;
					}
				}
				totalCompras++;
		}
	}
	else {printf("Erro ao ler o ficheiro de Compras!\n"); okFlag = 0;}

	endTime = time(NULL);

	fclose(fprodutos);
	fclose(fclientes);
	fclose(fcompras);


	if(okFlag == 0){ return 0;}
	
	CLEAR_CONSOLE;
	
	bemVindo();
	printf(COLOR_GREEN"\nTempo para carregar os ficheiros:"COLOR_RESET" %lds\n",endTime-beginTime);
	printMenu();

	while(okFlag)
	{
		int opcao; 
		while((opcao = le_inteiro()) == -1 ){
			printf("Opção invalida!\n");
			printf("> ");
		}	
		switch (opcao)
		{
			case 0:
			{
				okFlag = 0;
				printf(COLOR_BLUE"Até a proxima!\n"COLOR_RESET);
				break;
			}
			case 1:
			{
				CLEAR_CONSOLE;
				printf(COLOR_GREEN"\nNome do ficheiro de Produtos:"COLOR_RESET" %s\n",filenameP);
				printf(COLOR_GREEN"Nome do ficheiro de Clientes:"COLOR_RESET" %s\n",filenameC);
				printf(COLOR_GREEN"Nome do ficheiro de Compras:"COLOR_RESET" %s\n",filenameCmp);
				printf(COLOR_GREEN"Total de Produtos:"COLOR_RESET" %d\n",totalProdutos);
				printf(COLOR_GREEN"Total de Clientes:"COLOR_RESET" %d\n",totalClientes);
				printf(COLOR_GREEN"Total de Compras:"COLOR_RESET" %d\n",totalCompras);
				printf(COLOR_GREEN"Total de Compras validas:"COLOR_RESET" %d\n",compras_validas);
				PRESS_ENTER_TO_CONTINUE();
				printMenu();
				break;
			}				
			case 2:
			{
				char l = -1;
				Result q2 = NULL;
				printf("- Letra:\n");
				printf("	> ");
				while((l = le_caracter()) == -1){
					printf("Letra Inválida!\n");
					printf("	> ");
				}
				CLEAR_CONSOLE;
				q2 = getCatalogoLetra(catalogoProdutos,l);
				if(q2==NULL) {printf(COLOR_BLUE"\nNão existe registos!\n\n\n"COLOR_RESET);}
				else {
					imprimePorPagina(q2);
				}
				PRESS_ENTER_TO_CONTINUE();
				printMenu();
				break;
			}
			case 3:
			{
				int tN;
				int tP;
				float tF;
				int mes;
				char *cod = (char*)malloc(sizeof(char)*SIZEMAX_PRODUTOS);;
				printf("- Código de Produto: \n");
				printf("	> ");
				while((cod = ler_CodigoProtudo()) == NULL){
					printf("Código Inválido!\n");
					printf("	> ");
				}
				printf("	- Mês: ");
				while((mes = le_mes()) == -1){
					printf("Mês inválido: 1 .. 12!\n");
					printf("	- Mês: ");
				}	
				tN = totalDeVendasNormalMes(contabilidade,cod,mes);
				tP = totalDeVendasPromocaoMes(contabilidade,cod,mes);
				tF = totalFaturadoMes(contabilidade,cod,mes);
				CLEAR_CONSOLE;
				printf(COLOR_BLUE"\nContabilidade do produto: %s, no mês: %d \n\n"COLOR_RESET,cod,mes);
				printf(COLOR_YELLOW"Total Vendas Normal:"COLOR_RESET"   %d\n",tN);
				printf(COLOR_YELLOW"Total Vendas Promoção:"COLOR_RESET" %d\n",tP);
				printf(COLOR_YELLOW"Total Faturado:"COLOR_RESET"        %0.2f€\n",tF);
				free(cod);
				PRESS_ENTER_TO_CONTINUE();
				printMenu();
				break;
			}
			case 4:
			{
				int n = 0;
				ResultContabilidade r = produtosSemComprador(contabilidade);
				CLEAR_CONSOLE;
				if(r==NULL){printf(COLOR_BLUE"\nNão existe registos!\n"COLOR_RESET);}
				else{
					int total_paginas = 0;
					int pagina = 1;
					int inicio = 0;
					int pode_imprimir = 1;
					int c = -1;

					n = lenghtResultContabilidade(r);
					if(n<ELEMS_POR_PAGINA){
						printResultContabilidade(r);
					}
					else{
						total_paginas = (int) n/ELEMS_POR_PAGINA + 1;
						while(pagina <= total_paginas)
						{
							int ok = -1;
							if(pode_imprimir == 1){
								CLEAR_CONSOLE;
								printf(COLOR_BLUE"\nExistem - %d - produtos que ninguem Comprou! - pagina: %d/%d\n"COLOR_RESET,n,pagina,total_paginas);
								r = printResultContabilidadePagina(r,inicio);
							}	
							if(pagina == total_paginas){
								printf(COLOR_YELLOW"       -Não existem mais registos-\n"COLOR_YELLOW);
								break;
							}
							while(ok != 0) /*ate f/b/q */
							{
								printf(COLOR_BLUE"- front / back / exit (f / b / e): "COLOR_RESET);
								while((c = le_caracter()) == -1){
									printf(COLOR_BLUE"- front / back / exit (f / b / e): "COLOR_RESET);
								}
								if(c == 'F') {	/*imprimir proxima pagina (inicio=1), sai do ciclo e pode imprimir*/
									inicio = 1;
									pagina++;
									ok = 0;
									pode_imprimir = 1;
								}
								else if( c == 'B'){
									if(pagina > 1){  /*Se existe pagina anterior*/
										inicio = -1; /* pagina anterior (-1) */
										pagina --; 
										ok = 0;
										pode_imprimir = 1; 
									}
									else{
										printf(COLOR_YELLOW"Não existe registos anteriores!\n"COLOR_RESET);
										pode_imprimir = 0;  /*não pode imprimir*/
									}
								}else if(c == 'E'){ 
										pagina = total_paginas+1; /*sai do ciclo principal*/
										ok = 0;
									}
							}
						}
					}
				}
				PRESS_ENTER_TO_CONTINUE();
				printMenu();
				break;
			}
			case 5:
			{
				int i = 0;
				int c;
				int * r; /*Retorna um array de inteiros*/
				char *cod = (char*)malloc(sizeof(char)*SIZEMAX_CLIENTES);
				printf("- Código de Cliente: ");
				while((cod = ler_CodigoCliente()) == NULL){
					printf("Código Inválido!\n");
					printf("> ");
				}
				r = getInfoComprasDeCliente(compras,cod);
				CLEAR_CONSOLE;
				printf(" -----------------------\n");
				printf("| "COLOR_BLUE"Registo compras %s |\n"COLOR_RESET,cod);
				printf("|=======================|\n");
				while(i<12)
				{
					printf("|   "COLOR_BLUE"Mês %2d   |"COLOR_RESET"  %4d\t|\n",i+1,r[i]);
					i++;
				}
				printf(" ------------------------\n");

				i = 0;
				printf(COLOR_BLUE"- Quer guardar informação em ficheiro? (y ou n): "COLOR_RESET);
				
				while((c = le_caracter()) == -1){
					printf("Invalido!\n");
					printf("> ");
				}
				if(c == 'y' || c == 'Y')
				{
					FILE* f;
					char* begin = "Cliente ";
					char* end = " - Registo de Compras.txt";
					char* fileName = calloc((6+strlen(begin)+strlen(end)),sizeof(char));
					strcat(fileName,begin);
					strcat(fileName,cod);
					strcat(fileName,end);

					f = fopen(fileName,"w+");
					fprintf(f," -----------------------\n");
					fprintf(f,"| Registo compras %s |\n",cod);
					fprintf(f,"|=======================|\n");
					while(i<12){
						fprintf(f,"|   Mês %2d   |  %4d\t|\n",i+1,r[i]);
						i++;
					}
					fprintf(f," ------------------------\n");
					printf(COLOR_BLUE"\nInformação guardada no ficheiro: %s\n"COLOR_RESET,fileName);
					fclose(f);
					free(fileName);
				}
				free(cod);
				PRESS_ENTER_TO_CONTINUE();
				printMenu();
				break;
			}
			case 6:
			{
				char letra = -1;
				Result q6 = NULL;
				printf("Letra:\n");
				printf("	> ");
				while((letra = le_caracter()) == -1){
					printf("Letra Inválida!\n");
					printf("	> ");
				}
				CLEAR_CONSOLE;
				q6 = getCatalogoLetra(catalogoClientes,letra);
				if(q6==NULL) {printf(COLOR_BLUE"Não existe registos!\n\n\n"COLOR_RESET);}
				else{
					imprimePorPagina(q6);
				}
				PRESS_ENTER_TO_CONTINUE();
				printMenu();
				break;
			}
			case 7:
			{
				int mes1;
				int mes2;
				Par p;
				printf("	- Mês 1: ");
				while((mes1 = le_mes()) == -1){
					printf("Mês inválido: 1 .. 12!\n");
					printf("	- Mês 1: ");
				}	
				printf("	- Mês 2: ");
				while((mes2 = le_mes()) == -1){
					printf("Mês inválido: 1 .. 12!\n");
					printf("	- Mês 2: ");
				}
				CLEAR_CONSOLE;
				p = totalComprasEFaturadoEntreMes(contabilidade,mes1,mes2);
				printf(COLOR_BLUE"Registo entre os Meses [%d...%d]:\n"COLOR_RESET,mes1, mes2);
				printf(COLOR_YELLOW"     - Total de compras  : %d unidades\n"COLOR_RESET,getFstElem(p));
				printf(COLOR_YELLOW"     - Total de faturado : %d€\n"COLOR_RESET,getSndElem(p));
				PRESS_ENTER_TO_CONTINUE();
				printMenu();
				break;
			}
			case 8:
			{
				char *cod = (char*)malloc(sizeof(char)*SIZEMAX_PRODUTOS);
				ResultCompras8 r = NULL;
				ResultCompras rn = NULL;
				ResultCompras rp = NULL;
				int total_cliente_normal;
				int total_cliente_promocao;
				int ok = 1;
				while(ok){
					printf("- Código de Produto: ");
					while((cod = ler_CodigoProtudo()) == NULL){
						printf("Código Inválido!\n");
						printf("> ");
					}
					if(existeNoCatalogo(catalogoProdutos,cod) == 0)
						ok = 0;
					else{printf(COLOR_YELLOW"Código não existe!\n"COLOR_RESET);}
				}
				beginClock = clock();
				r = getRegistoTotaldeClientes(compras,cod);
				rn = getResultadoComprasNormal(r);
				rp = getResultadoComprasPromocao(r);
				total_cliente_normal = lenghtResultCompras(rn);
				total_cliente_promocao = lenghtResultCompras(rp);
				endClock= clock();
				CLEAR_CONSOLE;
				printf(COLOR_BLUE"Para o produto %s:\n\n"COLOR_RESET,cod);
				printf(COLOR_BLUE"Existe um total de %d clientes que fizeram compras normal\n"COLOR_RESET,total_cliente_normal);
				printResultCompras(rn);
				printf(COLOR_BLUE"\nExiste um total de %d clientes que fizeram compras promoção\n"COLOR_RESET,total_cliente_promocao);
				printResultCompras(rp);
				printf(COLOR_GREEN"Tempo gasto pelo CPU: %f \n"COLOR_RESET, (double)(endClock - beginClock)/CLOCKS_PER_SEC);
				PRESS_ENTER_TO_CONTINUE();
				printMenu();
				break;
			}
			case 9:
			{
				ResultCompras9 r = NULL;
				char *codigo_cliente = (char*)malloc(sizeof(char)*SIZEMAX_CLIENTES);
				int mes;
				int ok = 1;
				while(ok){
					printf("- Código de Cliente: ");
					while((codigo_cliente = ler_CodigoCliente()) == NULL){
						printf("Código Inválido!\n");
						printf("> ");
					}
					if(existeNoCatalogo(catalogoClientes,codigo_cliente) == 0)
						ok = 0;
					else{printf(COLOR_YELLOW"Código não existe!\n"COLOR_RESET);}
				}
				printf("	- Mês: ");
				while((mes = le_inteiro()) == -1 ){
					printf("Mês inválido: 1 .. 12!\n");
					printf("	- Mês: ");
				}
				beginClock = clock();
				r= getProdutosMaisComprados(compras,codigo_cliente,mes);
				endClock= clock();
				CLEAR_CONSOLE;
				printf(COLOR_GREEN"Tempo gasto pelo CPU: %f \n"COLOR_RESET, (double)(endClock - beginClock)/CLOCKS_PER_SEC);
				printf(COLOR_BLUE"Para o cliente %s no mês %d:\n\n"COLOR_RESET,codigo_cliente,mes);
				printResultComprasQuerie9(r);
				free(codigo_cliente);
				PRESS_ENTER_TO_CONTINUE();
				printMenu();
				break;
			}
			case 10: 
			{
				/*So esta a testar para um produto de cada vez*/
				ResultCompras r = NULL;
				r= getClientesQueComprouTodoAno(compras);
				CLEAR_CONSOLE;
				if(lenghtResultCompras(r) == 0)
					printf(COLOR_BLUE"\nNão existe registos de clientes que compraram o mesmo produto todos os meses do ano\n"COLOR_RESET);
				else{
					printf(COLOR_BLUE"\nClientes que compraram o mesmo produto todos os meses do ano\n"COLOR_RESET);
					printResultCompras(r);
				}
				PRESS_ENTER_TO_CONTINUE();
				printMenu();
				break;
			}
			case 11:
			{
				ComprasEClientes cc = initComprasEClientes();
				int mes = 1;
				FILE * file;
				file = fopen("Estatisticas.csv", "w+");
				fprintf(file, "\"Mês\",\"#Compras\",\"#Clientes\"\n");
				beginTime = time(NULL);
				while(mes<=12){
					cc = getComprasEClientesMes(compras,mes);
					fprintf(file, "\"%d\",\"%d\",\"%d\"\n",mes,getComprasElem(cc),getClientesElem(cc));
					mes++;
				}	
				fclose(file);
				endTime= time(NULL);
				CLEAR_CONSOLE;
				printf(COLOR_GREEN"Tempo gasto: %ld segundos \n"COLOR_RESET, endTime-beginTime);
				printf(COLOR_BLUE"\nFicheiro Estatisticas.csv criado!\n"COLOR_RESET);
				PRESS_ENTER_TO_CONTINUE();
				printMenu();
				break;
			}
			case 12:
			{
				int top = 0;
				ResultCompras12 r = NULL;
				
				printf("TOP > ");
				while((top = le_inteiro()) == -1 ){
					printf("TOP > ");
				}	
				beginClock = clock();
				r = topProdutos(compras,top);
				endClock = clock();
				CLEAR_CONSOLE;
				printf("\n");
				printf(COLOR_GREEN"Tempo gasto pelo CPU: %f \n"COLOR_RESET, (double)(endClock - beginClock)/CLOCKS_PER_SEC);
				printf(COLOR_BLUE"\nTop %d produtos vendidos:\n\n"COLOR_RESET,top);
				printResultComprasQuerie12(r,top);
				PRESS_ENTER_TO_CONTINUE();
				printMenu();
				break;
			}
			case 13:
			{
				ResultCompras9 r = NULL;
				char* codigo;
				int ok = 1;
				while(ok){
					printf("- Código de Cliente: ");
					while((codigo = ler_CodigoCliente()) == NULL){
						printf("Código Inválido!\n");
						printf("> ");
					}
					if(existeNoCatalogo(catalogoClientes,codigo) == 0)
						ok = 0;
					else{printf(COLOR_YELLOW"Código não existe!\n"COLOR_RESET);}
				}
				beginTime = time(NULL); 
				r = topProdutosCompradosPorCliente(compras,codigo);
				endTime = time(NULL);
				CLEAR_CONSOLE;
				printf(COLOR_GREEN"Tempo gasto: %ld segundos \n"COLOR_RESET, endTime-beginTime);
				printf(COLOR_BLUE"\nTOP3: (Quantidade/Produto)\n\n"COLOR_RESET);
				printResultComprasQuerie9(r);
				PRESS_ENTER_TO_CONTINUE();
				printMenu();
				break;
			}
			case 14:
			{
				int produtos;
				int clientes;
				ResultContabilidade r = NULL;
				beginTime = time(NULL); 
				r = produtosSemComprador(contabilidade);
				produtos = lenghtResultContabilidade(r);
				clientes = numeroTotalDeClientes(compras);
				endTime = time(NULL);
				CLEAR_CONSOLE;
				printf(COLOR_GREEN"Tempo gasto: %ld segundos \n"COLOR_RESET, endTime-beginTime);
				printf(COLOR_BLUE"\nTotal de Produtos que ninguem comprou:"COLOR_RESET"        %d\n",produtos);
				printf(COLOR_BLUE"Total de Clientes que não realizaram compras:"COLOR_RESET" %d\n",totalClientes-clientes);
				PRESS_ENTER_TO_CONTINUE();
				printMenu();
				break;
			}
			default: {
				printf ("Opção inválida!\n");
				printf("> ");
				break;
			}
		}		
}

	deleteCatalogo(catalogoProdutos);
	deleteCatalogo(catalogoClientes);
	deleteContabilidade(contabilidade);
	deleteCompras(compras);

	return 0;
}

