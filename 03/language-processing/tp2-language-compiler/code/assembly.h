#ifndef _ASSEMBLY_
#define _ASSEMBLY_




typedef struct assembly *Assembly;


void insertCode(Assembly *l, char* c);
int lenghtAssembly(Assembly m);
void printAssemblyToFile(Assembly l,char* filename);
void printAssemblyToScreen(Assembly l);
void assemblyCat(Assembly *l1, Assembly l2);



void insertStart(Assembly *l);
void insertStop(Assembly *l);
void insertRead(Assembly *l);
void insertWriteI(Assembly *l);
void insertWriteS(Assembly *l);
void insertPushI(Assembly *l, int i);
void insertPushN(Assembly *l, int i);
void insertPushG(Assembly *l, int i);
void insertPushS(Assembly *l, char* s);
void insertStoreG(Assembly *l, int i);
void insertJump(Assembly *l, int i);
void insertJZ(Assembly *l, int i);
void insertLabel(Assembly *l, int i);
void insertAdd(Assembly *l);
void insertSub(Assembly *l);
void insertMul(Assembly *l);
void insertDiv(Assembly *l);

#endif
