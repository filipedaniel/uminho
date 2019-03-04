CC = gcc
CFLAGS = -O2
default: exec

clear: 
	clear
clean:
	@rm -rf *.o lex.yy.c prog converted

lex.yy.c: readFile.l music_linkedlist.h makeLatex.h
	@flex readFile.l

prog: lex.yy.c music_linkedlist.c makeLatex.c
	@$(CC) $(CFLAGS) -o prog lex.yy.c music_linkedlist.c makeLatex.c

exec:	prog
	@cp prog IN
	@for i in `ls IN/*.lyr` ;do echo "$$i"; IN/prog "$$i"; done;

all: clear clean

