CC = gcc
CFLAGS = -Wall -O2 -std=c89 -pedantic -Wno-unused-function


default: all

all: clear clean GESTHIPER

GESTHIPER: main.o catalogo.o contabilidade.o compras.o 
	@$(CC) $(CFLAGS) -o GESTHIPER main.o catalogo.o contabilidade.o compras.o

main.o: main.c catalogo.h contabilidade.h compras.h
	@$(CC) $(CFLAGS) -c main.c

catalogo.o: catalogo.c catalogo.h
	@$(CC) $(CFLAGS) -c catalogo.c

contabilidade.o: contabilidade.c contabilidade.h
	@$(CC) $(CFLAGS) -c contabilidade.c

compras.o: compras.c compras.h
	@$(CC) $(CFLAGS) -c compras.c

clear:
	@clear

clean:
	@rm -f *.o GESTHIPER