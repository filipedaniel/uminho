#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "assembly.h"

#define MAX_DIGITS 10

#define LABEL 0
#define OTHER 1

#define LABEL_PREFIX "L000"

extern int cType;

struct assembly{
  char *code;
  struct assembly *next;
};

/*---------API-----------*/

void insertCode(Assembly *l, char* c){
	Assembly noActual = *l;

	Assembly no = (Assembly)malloc(sizeof(struct assembly));
	no->code = strdup(c);
	no->next = NULL;
	
	if(noActual == NULL) *l = no;
	else{
		while(noActual->next != NULL)
			noActual = noActual->next;
		noActual->next = no;
	}
}


void assemblyCat(Assembly *l1, Assembly l2){
	Assembly noActual = *l1;	
	if(noActual == NULL) *l1 = l2;
	else{
		while(noActual->next != NULL)
			noActual = noActual->next;
		noActual->next = l2;
	}
}


int lenghtAssembly(Assembly m){
  Assembly aux = m;
  int t= 0;
  while(aux != NULL){
    t++;
    aux = aux->next;
  }
  return t;
}


void printAssemblyToFile(Assembly l,char* filename){
	FILE* f;
	f = fopen(filename, "w+");
	Assembly aux = NULL;
	aux = l;
	while(aux!=NULL){
		fprintf(f, "%s",aux->code);
		aux = aux->next;
	}
}

void printAssemblyToScreen(Assembly l){
	Assembly aux = NULL;
	aux = l;
	while(aux!=NULL){
		printf("%s",aux->code);
		aux = aux->next;
	}
}

/*-----------INSERTS-----------*/

void insertStart(Assembly *l) {

	insertCode(l,"START\n");
	cType=OTHER;
}
void insertStop(Assembly *l) {
	insertCode(l,"STOP\n");
	cType=OTHER;
}
void insertRead(Assembly *l) {
	insertCode(l,"READ\n");
	cType=OTHER;
}
void insertWriteI(Assembly *l) {
	insertCode(l,"WRITEI\n");
	cType=OTHER;
}
void insertWriteS(Assembly *l) {
	insertCode(l,"WRITES\n");
	cType=OTHER;
}
void insertAdd(Assembly *l) {
	insertCode(l,"ADD\n");
	cType=OTHER;
}
void insertSub(Assembly *l) {
	insertCode(l,"SUB\n");
	cType=OTHER;
}
void insertMul(Assembly *l) {
	insertCode(l,"MUL\n");
	cType=OTHER;
}
void insertDiv(Assembly *l) {
	insertCode(l,"DIV\n");
	cType=OTHER;
}
void insertPushI(Assembly *l, int i) {
	char* command = strdup("PUSHI");

	char* buffer = malloc(sizeof(char)*(strlen(command)+MAX_DIGITS));
	sprintf(buffer, "%s %d\n",command,i);
	insertCode(l,buffer);
	cType=OTHER;
}
void insertPushN(Assembly *l, int i) {
	char* command = strdup("PUSHN");

	char* buffer = malloc(sizeof(char)*(strlen(command)+MAX_DIGITS));
	sprintf(buffer, "%s %d\n",command,i);
	insertCode(l,buffer);
	cType=OTHER;
}
void insertPushG(Assembly *l, int i) {
	char* command = strdup("PUSHG");

	char* buffer = malloc(sizeof(char)*(strlen(command)+MAX_DIGITS));
	sprintf(buffer, "%s %d\n",command,i);
	insertCode(l,buffer);
	cType=OTHER;
}
void insertPushS(Assembly *l, char* s) {
	char* command = strdup("PUSHS");

	char* buffer = malloc(sizeof(char)*(strlen(command)+strlen(s)+3));
	sprintf(buffer, "%s %s\n",command,s);
	insertCode(l,buffer);
	cType=OTHER;
}
void insertStoreG(Assembly *l, int i) {
	char* command = strdup("STOREG");

	char* buffer = malloc(sizeof(char)*(strlen(command)+MAX_DIGITS));
	sprintf(buffer, "%s %d\n",command,i);
	insertCode(l,buffer);
	cType=OTHER;
}
void insertJump(Assembly *l, int i) {
	char* command = strdup("JUMP");

	char* buffer = malloc(sizeof(char)*(strlen(command)+strlen(LABEL_PREFIX)+MAX_DIGITS));
	sprintf(buffer, "%s %s%d\n",command,LABEL_PREFIX,i);
	insertCode(l,buffer);
	cType=OTHER;
}
void insertJZ(Assembly *l, int i) {
	char* command = strdup("JZ");

	char* buffer = malloc(sizeof(char)*(strlen(command)+MAX_DIGITS));
	sprintf(buffer, "%s %s%d\n",command,LABEL_PREFIX,i);
	insertCode(l,buffer);
	cType=OTHER;
}
void insertLabel(Assembly *l, int i) {
	if(cType==LABEL) {
		insertCode(l,"NOP\n"); cType=OTHER;
	}

	char* command = strdup(LABEL_PREFIX);

	char* buffer = malloc(sizeof(char)*(strlen(command)+MAX_DIGITS));
	sprintf(buffer, "%s%d:\n",command,i);
	insertCode(l,buffer);
	cType=LABEL;
}
