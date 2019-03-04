
#include "hash.h"
#include <stdio.h>
#include <stdlib.h>

#define DEFAULT_CAT 0


void insereVariavel(HashTable *hash, char *key, int addr, int tipo, int tam) {

  struct entry *temp = malloc(sizeof(struct entry)), *old,*temp2;
  Variable v = malloc(sizeof(variable));
  v->tipo=tipo;
  v->addr=addr;
  v->cat=DEFAULT_CAT;
  v->tam=tam;

  strcpy(temp->key, key);
  temp->var = v;
  HASH_REPLACE_STR(*hash, key, temp, old);

  if (old) free(old);
}


int getEndereco(HashTable hash, char *key) {
  Variable v = hash_get(hash, key);
  if(v==NULL) return -1;
  else return v->addr;
}

int getTipo(HashTable hash, char *key) {
  Variable v = hash_get(hash, key);
  if(v==NULL) return -1;
  else return v->tipo;
}

int getCategoria(HashTable hash, char *key) {
  Variable v = hash_get(hash, key);
  if(v==NULL) return -1;
  else return v->cat;
}
int getTamanho(HashTable hash, char *key) {
  Variable v = hash_get(hash, key);
  if(v==NULL) return -1;
  else return v->tam;
}


Variable hash_get(HashTable hash, char *key) {
  struct entry *temp;

  HASH_FIND_STR(hash, key, temp);

  if (temp == NULL) return NULL;
  else return temp->var;
}


int hash_size(HashTable hash) {
  return HASH_COUNT(hash);
}

void hash_clear(HashTable *h) {
	struct entry *entry,*temp;
	HASH_ITER(hh,(*h),entry,temp){
		HASH_DEL((*h),entry);
		free(entry->var);
		free(entry);
	}
}

void hash_print(HashTable *h) {
  struct entry *entry,*temp;
  HASH_ITER(hh,(*h),entry,temp){
    Variable v = entry->var;
        printf("|     %s          %d           %d          %d            %d        |\n",
                    entry->key, v->addr, v->tipo, v->cat,  v->tam);
  }
}

void printTabelaVariaveis(HashTable *h) {
    if(h){
      printf("\n\t\tTabela de identificadores\n\n");
      printf(" - - Nome - - Endereco - - Tipo - - Categoria - - Tamanho - - \n");
      hash_print(h);
      printf(" - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -\n");
      printf("\n\n");
    } 
}
