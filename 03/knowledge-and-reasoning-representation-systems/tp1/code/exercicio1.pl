%_____________________________________________________________________
% SIST. REPR. CONHECIMENTO E RACIOCINIO - LEI/3

%_____________________________________________________________________
% Exercicio 1 - Grupo 29

%_____________________________________________________________________
% SICStus PROLOG: Declaracoes iniciais

:- set_prolog_flag( discontiguous_warnings,off ).
:- set_prolog_flag( single_var_warnings,off ).
:- set_prolog_flag( unknown,fail ).


%_____________________________________________________________________
% SICStus PROLOG: definicoes iniciais

:- op(900,xfy,'::' ).
:- dynamic filho/2.
:- dynamic natural/2.


% Conhecimento-------------------------

filho(pedro,joao).
filho(raul,joao).
filho(jose,joao).
filho(filipe,pedro).
filho(pedro,ana).
filho(raul,ana).
filho(jose,ana).
filho(rui,jose).
filho(rui,rita).
filho(andre,rui).
filho(andre,maria).
filho(carlos,andre).
filho(marco,andre).

natural(raul,guimaraes).
natural(jose,porto).
natural(ana,lisboa).
natural(carlos,guimaraes).
natural(filipe,braga).

% --------------------------------------


%_____________________________________________________________________
% Extensao do predicado natural: Individuo,Local -> {V,F}

% Invariante Estrutural: nao permitir a insercao de conhecimento repetido

+natural( I,L ) :: (solucoes( (I,L),(natural( I,L )),S ),
    comprimento( S,N ),
    N == 1).

% Invariante Referencial: nao admitir mais do que uma naturalidade para o mesmo individuo

+natural( I,L ) :: ( solucoes( Y, (natural(I,Y)), S ),
    comprimento(S,N),
    N==1).


%_____________________________________________________________________
% Extensao do predicado filho: Filho,Pai -> {V,F}

% Invariante Estrutural:  nao permitir a insercao de conhecimento repetido

+filho( F,P ) :: (solucoes((F,P),(filho(F,P)),S),
    comprimento(S,N),
    N==1).

% Invariante Referencial: nao admitir mais do que 2 progenitores para um mesmo individuo

+filho( F,P ) :: ( solucoes( Y, (filho(F,Y)), S ),
    comprimento(S,N),
    N=<2).


% Invariante Referencial: nao admitir que um progenitores seja descendente so seu filho

+filho( F,P ) :: nao(descendente(P,F,N)).


%_____________________________________________________________________
% Extensao do predicado tios: Individuo, Resultado -> {V,F}

filhos(I,R) :- solucoes(F,filho(F,I),R).


%_____________________________________________________________________
% Extensao do predicado pai: Pai,Filho -> {V,F}

pai(P,F) :- filho(F,P).


%_____________________________________________________________________
% Extensao do predicado tios: Individuo, Resultado -> {V,F}

pais(I,R) :- solucoes(P,pai(P,I),R).


%_____________________________________________________________________
% Extensao do predicado tio: Tio,Sobrinho -> {V,F}

tio(T,S) :- irmao(T,I), pai(I,S).


%_____________________________________________________________________
% Extensao do predicado tios: Individuo, Resultado -> {V,F}

tios(I,R) :- solucoes(T,tio(T,I),S),
    elimRepetidos(S,R).

%tios(I,R) :- solucoes(P,tio(P,I),R).


%_____________________________________________________________________
% Extensao do predicado sobrinho: Sobrinho, Tio -> {V,F}

sobrinho(S,T) :- tio(T,S).


%_____________________________________________________________________
% Extensao do predicado sobrinhos: Individuo, Resultado -> {V,F}

sobrinhos(I,R) :- solucoes(S,sobrinho(S,I),S),
    elimRepetidos(S,R).


%_____________________________________________________________________
% Extensao do predicado irmao: Irmao1, Irmao2 -> {V,F}

irmao(I1,I2) :- pai(P,I1), pai(P,I2), I1 \== I2.


%_____________________________________________________________________
% Extensao do predicado irmaos: Individuo, Resultado -> {V,F}

irmaos(I,R) :- solucoes(Ir,irmao(Ir,I),S),
    elimRepetidos(S,R).


%_____________________________________________________________________
% Extensao do predicado primo: Primo1, Primo2 -> {V,F}

primo(P1,P2) :- filho(P1,Pai1), filho(P2,Pai2), irmao(Pai1,Pai2).


%_____________________________________________________________________
% Extensao do predicado primos: Individuo, Resultado -> {V,F}

primos(I,R) :- solucoes(P,primo(P,I),S),
    elimRepetidos(S,R).


%_____________________________________________________________________
% Extensao do predicado avo: Avo, Neto -> {V,F}

avo(A,N) :- filho(N,Y), pai(A,Y).


%_____________________________________________________________________
% Extensao do predicado avos: Individuo, Resultado -> {V,F}

avos(I,R) :- solucoes(P,avo(P,I),S),
    elimRepetidos(S,R).


%_____________________________________________________________________
% Extensao do predicado neto: Neto, Avo -> {V,F}

neto(N,A) :- avo(A,N).


%_____________________________________________________________________
% Extensao do predicado netos: Individuo, Resultado -> {V,F}

netos(I,R) :- solucoes(P,neto(P,I),R).


%_____________________________________________________________________
% Extensao do predicado bisavo: Bisavo, Bisneto -> {V,F}

bisavo(X,Y) :- avo(X,Z), pai(Z,Y).


%_____________________________________________________________________
% Extensao do predicado primos: Individuo, Resultado -> {V,F}

bisavos(I,R) :- solucoes(P,bisavo(P,I),S),
    elimRepetidos(S,R).


%_____________________________________________________________________
% Extensao do predicado bisneto: Bisneto, Bisavo -> {V,F}

bisneto(B,Y) :- bisavo(Y,B).


%_____________________________________________________________________
% Extensao do predicado bisnetos: Individuo, Resultado -> {V,F}

bisnetos(Y,R) :- solucoes(P,bisneto(P,Y),S),
    elimRepetidos(S,R).


%_____________________________________________________________________
% Extensao do predicado casado: Individuo1, Individuo2 -> {V,F}

casado(I1,I2) :- filho(X,I1), filho(X,I2), I1 \== I2.


%_____________________________________________________________________
% Extensao do predicado descendente: Descendente,Ascendente -> {V,F}

descendente(X,Y) :- filho(X,Y).
descendente(X,Y) :- filho(X,Z), descendente(Z,Y).


%_____________________________________________________________________
% Extensão do predicado descendentes: Individuo,Grau,Solucao -> {V, F}

descendentes(I, R) :- solucoes(Y, descendente(Y, I), S),
    elimRepetidos(S,R).

%_____________________________________________________________________
% Extensao do predicado ascendente: Ascendente,Descendente -> {V,F}

ascendente(X,Y) :- descendente(Y,X).


%_____________________________________________________________________
% Extensão do predicado ascendentes: Individuo,Grau,Solucao -> {V, F}

ascendentes(I, R) :- solucoes(A, ascendente(A, I), S),
    elimRepetidos(S,R).


%_____________________________________________________________________
% Extensao do predicado descendentegrau: Descendente,Ascendente,Grau -> {V,F}

descendentegrau(X,Y,1) :- filho(X,Y).
descendentegrau(X,Y,G) :- filho(X,Z), descendentegrau(Z,Y,N), G is N+1.


%_____________________________________________________________________
% Extensao do predicado ascendentegrau: Ascendente,Descendente,Grau -> {V,F}

ascendentegrau(X,Y,G) :- descendentegrau(Y,X,G).


%_____________________________________________________________________
% Extensão do predicado descendentesategrau: Individuo,Grau,Solucao -> {V,F}

descendentesategrau(I,0,[]).
descendentesategrau(I,G,Res) :-
    solucoes(X,descendentegrau(X,I,G),R),
    Y is G-1,
    descendentesategrau(I,Y,R2),
    concat(R,R2,S),
    elimRepetidos(S,Res).

%_____________________________________________________________________
% Extensão do predicado ascendentesategrau: Individuo,Grau,Solucao -> {V,F}

ascendentesategrau(I,0,[]).
ascendentesategrau(I,G,Res) :-
    solucoes(X,ascendentegrau(X,I,G),R),
    Y is G-1,
    ascendentesategrau(I,Y,R2),
    concat(R,R2,S),
    elimRepetidos(S,Res).


%_____________________________________________________________________
% Extensão do predicado relacao: Individuo1, Individuo2, Resultado -> {V,F}

relacao(X,Y,filho) :- filho(X,Y).
relacao(X,Y,pai) :- pai(X,Y).
relacao(X,Y,tio) :- tio(X,Y).
relacao(X,Y,sobrinho) :- sobrinho(X,Y).
relacao(X,Y,primo) :- primo(X,Y).
relacao(X,Y,irmao) :- irmao(X,Y).
relacao(X,Y,casado) :- casado(X,Y).
relacao(X,Y,avo) :- avo(X,Y).
relacao(X,Y,neto) :- neto(X,Y).
relacao(X,Y,bisavo) :- bisavo(X,Y).
relacao(X,Y,bisneto) :- bisneto(X,Y).
relacao(X,Y,descendente) :- descendente(X,Y).
relacao(X,Y,ascendente) :- ascendente(X,Y).


%_____________________________________________________________________
% Extensao do predicado que permite a evolucao do conhecimento insersao: Termo -> {V,F}

insercao(Termo) :- solucoes(Invariante, +Termo::Invariante, Lista),
    evolucao(Termo),
    teste(Lista).

evolucao(Termo) :- assert(Termo).
evolucao(Termo) :- retract(Termo), !, fail.

teste([]).
teste([R|LR]) :- R, teste(LR).


%_____________________________________________________________________
% Extensao do predicado que permite a remoção do conhecimento: Termo -> {V,F}

remocao(Termo) :- retract(Termo).


%_____________________________________________________________________
% Extensao do predicado comprimento: Lista, Resultado -> {V,F}

comprimento([],0).
comprimento([C|L], R) :- comprimento(L,G), R is G+1.


%_____________________________________________________________________
% Extensao do predicado concat: Lista, Resultado -> {V,F}

concat([],L,L).
concat([H|T],L,[H|Y]) :- concat(T,L,Y).


%_____________________________________________________________________
% Extensao do predicado nao: Questao -> {V,F}

nao(Q) :- Q,!,fail.
nao(Q).


%_____________________________________________________________________
% Extensão do predicado solucoes: X,Teorema,Solucoes -> {V, F}

solucoes( X,Y,Z ) :- findall(X,Y,Z).


%_____________________________________________________________________
% Extensão do predicado elimRepetidos: Lista, Resultado -> {V, F}

elimRepetidos([], []).
elimRepetidos([H|T], R) :- elimElemento(T, H, Raux),
    elimRepetidos(Raux, R2),
    R = [H|R2].


%_____________________________________________________________________
% Extensão do predicado elimElemento: Lista, Elemento, Resultado -> {V, F}

elimElemento([], _, []).
elimElemento([H|T], H, R) :- elimElemento(T, H, R).
elimElemento([H|T], E, R)	:- H \== E,
    elimElemento(T, E, R2),
    R = [H|R2].






















