compiler: lex.yy.o y.tab.o stack.o hash.o assembly.o
	gcc -ggdb -o compiler y.tab.o lex.yy.o stack.o hash.o  assembly.o -ll

hash.o: hash.c hash.h uthash.h
	gcc -c hash.c 

stack.o: stack.c stack.h
	gcc -c stack.c

assembly.o: assembly.c assembly.h
	gcc -c assembly.c
	
y.tab.o: y.tab.c
	gcc -c y.tab.c

lex.yy.o: lex.yy.c
	gcc -c lex.yy.c

y.tab.c, y.tab.h: anasyn.y
	yacc -d -t -v anasyn.y

lex.yy.c: analex.l y.tab.h
	flex analex.l

clear: 
	clear
clean:
	rm -rf *.o lex.yy.c y.output compiler

