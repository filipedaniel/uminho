#ifndef _MAKELATEX
#define _MAKELATEX


#include "music_linkedlist.h"

typedef struct map{
	int count;
	char** simb;
	char** value;
}*Map,map; 


void makeLatex(Musicas musicas);
char* filterScharacters(char * src, Map mapa);
Map insertRule(Map p, char* simb, char* expr);
Map iniateMapLatex();

#endif
