#ifndef _STACK_
#define _STACK_

#define STACK_MAX 1000



typedef struct sStack *Stack;


Stack Stack_Init(Stack);
int Stack_Top(Stack);
void Stack_Push(Stack, int );
int Stack_Pop(Stack);

#endif