#include <stdio.h>
#include <stdlib.h>


#include "stack.h"


struct sStack {
    int     data[STACK_MAX];
    int     size;
};


Stack Stack_Init(Stack S)
{
    if (S==NULL) {
        S = (Stack) malloc(sizeof(struct sStack));
    }
    S->size = 0;
    return S;
}

int Stack_Top(Stack S)
{
    if (S->size == 0) {
        fprintf(stderr, "Error: stack empty\n");
        return -1;
    } 

    return S->data[S->size-1];
}

void Stack_Push(Stack S, int d)
{
    if (S->size < STACK_MAX)
        S->data[S->size++] = d;
    else
        fprintf(stderr, "Error: stack full\n");
}

int Stack_Pop(Stack S)
{
    int ret;
    if (S->size == 0) {
        fprintf(stderr, "Error: stack empty\n");
        ret=-1;
    }
    else {
        S->size--;
        return S->data[S->size];
    }
    return -1;
}