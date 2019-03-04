#ifndef _MUSIC_LINKEDLIST
#define _MUSIC_LINKEDLIST




typedef struct letra{
  char *line;
  struct letra *next;
}*Letra,letras;


typedef struct musica{
  char *title;
  char *author_lyric;
  char *author_music;
  char *author;
  char *from;
  char *singer;
  int* estrofe;
  struct letra *letra;
  struct musica *next;
} *Musicas, Musica;	



Musicas initMusicas();
Letra initLetra();


/*GET´S*/
Musicas getMusic(Musicas m, int indice);

/*INSERT´S*/
Musicas insertMusic(Musicas m, Musicas new);
Letra insertLetra(Letra l, char* v);

/*SET´S*/
void setTitle(Musicas m, char *t);
void setLyric(Musicas m, char *l);
void setAutMusic(Musicas m, char *a);
void setAuthor(Musicas m, char *a);
void setFrom(Musicas m, char *f);
void setSinger(Musicas m, char *s);
void setLetra(Musicas m, Letra l);
void setEstrofe(Musicas m, int i ,int n);

/*LENGHT'S*/
int lenghtMusicas(Musicas m);
int lenghtLetra(Letra m);

#endif
