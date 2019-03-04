#ifndef HASH_H
#define HASH_H

#include "uthash.h"

#define MAX_KEY 64

typedef struct svariable {
  int tipo;
  int addr;
  int cat;
  int tam;
} variable, *Variable;

typedef struct entry {
  char key[MAX_KEY];
  Variable var;
  UT_hash_handle hh;
} *HashTable;



Variable hash_get(HashTable hash, char *key);
void insereVariavel(HashTable *hash, char *key, int addr, int tipo, int tam);
int hash_size(HashTable hash);
void hash_clear(HashTable *h);
int getEndereco(HashTable hash, char *key);
int getTipo(HashTable hash, char *key);
int getCategoria(HashTable hash, char *key);
int getTamanho(HashTable hash, char *key);
void hash_print(HashTable *h);
void printTabelaVariaveis(HashTable *h);

#endif