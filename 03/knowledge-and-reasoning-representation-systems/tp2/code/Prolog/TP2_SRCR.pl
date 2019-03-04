%--------------------------------- - - - - - - - - - - - - - - - - - - - - - - - - - - - -
% SICStus PROLOG: Declarações iniciais
:- set_prolog_flag( discontiguous_warnings, off ).
:- set_prolog_flag( single_var_warnings, off ).
:- set_prolog_flag( unknown, fail ).

%--------------------------------- - - - - - - - - - - - - - - - - - - - - - - - - - - - -
% SICStus PROLOG: definicoes iniciais
:- op( 900,xfy,'::' ).
:- dynamic insert/1.
:- dynamic remove/1.
:- dynamic (-)/1.
:- dynamic matricula/1.
:- dynamic automovel/4.
:- dynamic cor/2.
:- dynamic registo/2.
:- dynamic proprietario/2.
:- dynamic anoMatricula/3.
:- dynamic estado/2.
:- dynamic excecao/1.
:- dynamic (::)/2.


%=========================================== Demo ======================================
%_______________________________________________________________________________________
% Extensao do predicado demo: Questao,Resposta -> {V,F,D}

demo( Questao,verdadeiro ) :-
    Questao.
demo( Questao, falso ) :-
    -Questao.
demo( Questao,desconhecido ) :-
    nao( Questao ),
    nao( -Questao ).

%_______________________________________________________________________________________
% Extensao do predicado demoLista: [Questao],[Resposta] -> {V,F,D}

demoLista([],[]).
demoLista([H|T],R) :- demo(H,X), demoLista(T,Y), concat([X],Y,R).


%_______________________________________________________________________________________
% Extensao do predicado demoLogico: [Questao],Resposta -> {V,F,D}

demoLogico(L,falso) :- (demoLista(L,Raux), member(falso,Raux)).
demoLogico(L,desconhecido) :- (demoLista(L,Raux), member(desconhecido,Raux)).
demoLogico(L,verdadeiro).


%=========================================== Não =======================================
% nao: Questao -> {V,F}

nao( Questao ) :- Questao, !, fail.
nao( Questao ).

%=========================================== Matricula =================================
%_______________________________________________________________________________________
% matricula :: Matricula -> {V,F}

% Representacao de conhecimento positivo
matricula( 'AA-BB-00' ).
matricula( 'AA-BB-10' ).
matricula( 'AA-BB-11' ).
matricula( 'AA-BB-22' ).
matricula( 'AA-BB-33' ).
matricula( 'AA-BB-44' ).
matricula( 'AA-BB-66' ).
matricula( 'PP-BB-90' ).


% Representacao de conhecimento negativo
-matricula( 'ZZ-YY-99' ).
-matricula( 'ZZ-YY-88' ).
-matricula( M ) :-
    nao( matricula( M ) ),
    nao( excecao( matricula( M ) ) ).


excecao(matricula('XX-YY-10')).

%_______________________________________________________________________________________
% AUX: -> Totos as Matriculas existentes
matriculas(R):- solucoes(M,matricula(M),R).


%_______________________________________________________________________________________
% INVARIANTES DE MATRICULA

% Para remover, não pode ter nada que refere a matricula a remover
-matricula(M) :: ((nao(automovel(M,_,_,_))),
                 (nao(cor(M,_))),
                 (nao(estado(M,_))),
                 (nao(anoMatricula(M,_,_))),
                 (nao(registo(M,_))),
                 (nao(proprietario(M,_)))).

% não permite Inserção de conhecimento repetido
+matricula(M) :: (solucoes(C,matricula(M),L) , comprimento(L,1)).

% só insere conhecimento se não houver conhecimento de que nao existe
+matricula(M) :: (nao(-matricula(M))).

% só insere conhecimento negativo se não houver conhecimento de que existe
+(-matricula(M)) :: (nao(matricula(M))).

% não permite Inserção de conhecimento repetido
+(-matricula(M)) :: (solucoes(C,-matricula(M),L) , comprimento(L,N), N=<2).



%=========================================== AUTOMOVEL =================================
%_______________________________________________________________________________________
% automovel :: Matricula, Fabricante, Marca, Modelo -> {V,F}

% Representacao de conhecimento positivo
automovel( 'AA-BB-10',mercedes,smart,fourtwo ).
automovel( 'AA-BB-11',renaut,renaut,clio ).
automovel( 'AA-BB-22',fiat,fiat,panda ).
automovel( 'AA-BB-33',ford,ford,fiesta ).
automovel( 'AA-BB-44',fiat,fiat,punto).


% Representacao de conhecimento negativo
-automovel( 'AA-BB-00',opel,opel,corsa ).

-automovel(M,F,MA,MO) :- (nao(automovel(M,F,MA,MO)), 
                         nao(excecao(automovel(M,F,MA,MO)))).

%_______________________________________________________________________________________
% AUX:
% -> Modelos de todos os automoveis de um proprietario
modeloPro(P,Mode) :-  findall(M,(proprietario(Mat,P), automovel(Mat,_,_,M)),  Mode).

% -> Se existe um automovel com matricula....
automovel(X) :- (automovel(X,_,_,_)).

% -> Todos os automoveis(matricula) de uma marca 
automoveisMarca(M,R):- solucoes(N,automovel(N,_,M,_),R).


%_______________________________________________________________________________________
% INVARIANTES DE AUTOMÓVEL

% Matricula tem de existir
+automovel(N,_,_,_) :: (matricula(N)).

% Matricula do automovel tem de ser unica
+automovel(N,_,_,_) :: (solucoes(N,automovel(N,_,_,_),L) , comprimento(L,1)).

% só insere conhecimento se não houver conhecimento de que nao existe
+automovel(M,X,Y,Z) :: (nao(-automovel(M,X,Y,Z))).

%  só insere conhecimento negativo se não houver conhecimento de que existe
+(-automovel(M,X,Y,Z)) :: (nao(automovel(M,X,Y,Z))).

% só insere conhecimento se não houver conhecimento de que nao existe
+automovel(M,X,Y,Z) :: (nao(-automovel(M,X,Y,Z))).

% só insere conhecimento negativo se não houver conhecimento de que existe
+(-automovel(M,X,Y,Z)) :: (nao(automovel(M,X,Y,Z))).

% não permite Inserção de conhecimento repetido
+(-automovel(M,X,Y,Z)) :: (solucoes(C,-automovel(M,X,Y,Z),L) , comprimento(L,N), N=<2).


%_______________________________________________________________________________________
% REPRESENTAÇÂO DE CONHECIMENTO INCERTO - (Tipo 1)
% - Sabe-se que o carro com matricula "aa-bb-55" é um ferrari, fabricado
%   pela mesma, só nao se sabe qual é o modelo

automovel( 'AA-BB-55' , ferrari , ferrari, desconhecido ).
excecao(automovel( A,F,M,MO )) :- automovel( A,F,M,desconhecido ).


%_______________________________________________________________________________________
% REPRESENTAÇÂO DE CONHECIMENTO IMPRECISO - (Tipo 2)
% - Sabe-se que o carro com matricula "aa-bb-66" é um volkswagen, fabricado
%   pela mesma, o modelo é se tem a certeza, ou é um golf ou um polo

excecao( automovel( 'AA-BB-66',volkswagen,volkswagen,golf )).
excecao( automovel( 'AA-BB-66',volkswagen,volkswagen,polo )).


%_______________________________________________________________________________________
% REPRESENTAÇÃO DE CONHECIMENTO INTERDITO - (Tipo 3)
% - Sabe-se que o novo carro da audi tem a matricula "aa-bb-77",
%   mas não se pode sabar qual o modelo

excecao(automovel(A,F,M,B)) :- automovel(A,F,M,impossivel).
nulo(impossivel).
automovel('PP-BB-90',aa,bb,impossivel).
+automovel(M2,F2,Ma2,Md2):: (solucoes(Mds,(automovel('PP-BB-90',aa,bb,Mds),nao(nulo(Mds))),S),comprimento(S,N),N==0).

%_______________________________________________________________________________________
% Predicado evolucaoAutomovelNulo 
evolucaoAutomovelNulo( automovel(M,F,Ma,impossivel) ) :-
    solucoes( Invariante,+automovel(M,F,Ma,impossivel)::Invariante,Lista ),
                    insert( automovel(M,F,Ma,impossivel) ),
                    teste( Lista ),                    
                    assert(
                        +automovel( M2,F2,Ma2,Md2 ) :: ( 
                            solucoes( Mds, (automovel(M,F,Ma,Mds), nao(nulo(Mds))), S),
                            comprimento(S,N),
                            N==0
                                ) 
                            ).


%=========================================== COR ========================================

%_______________________________________________________________________________________
% cor :: Matricula, Cor -> {V,F}

% Representacao de conhecimento positivo
cor('AA-BB-00',amarelo).
cor('AA-BB_11',preto).
cor('AA-BB-22',azul).
cor('AA-BB-33',vermelho).

% Representacao de conhecimento negativo
-cor(M,C):- (nao(cor(M,C)), nao(excecao(cor(M,C)))).

%_______________________________________________________________________________________
% INVARIANTES DE COR

% matricula tem de existir
+cor(M,_) :: (matricula(M)).

% não admite cores duplicados
+cor(M,_) :: (solucoes(C,cor(M,_),L) , comprimento(L,1)).

% não permite Inserção de conhecimento repetido
+cor(M,C) :: (solucoes(C,cor(M,C),L) , comprimento(L,1)).

% só insere conhecimento se não houver conhecimento de que nao existe
+cor(M,C)  :: (nao(-cor(M,C))).

% só insere conhecimento negativo se não houver conhecimento de que existe
+(-cor(M,C) ) :: (nao(cor(M,C))).

% não permite Inserção de conhecimento repetido
+(-cor(M,C) ) :: (solucoes(C,-cor(M,C) ,L) , comprimento(L,N), N=<2).

%_____________________________________________________________________

% Predicado para ATUALIZAR a cor de um dado automovel
atualizarCor( M,C ) :- remocao( cor( M,_ ) ), evolucao( cor( M,C )).


%=========================================== ESTADO =====================================
%_______________________________________________________________________________________
% tipo_estado :: Estado -> {V,F}
% todos os tipo possiveis para representar o estado de um automovel
tipo_estado( bom ).
tipo_estado( razoavel ).
tipo_estado( mau ).
tipo_estado( novo ).

%_______________________________________________________________________________________
% estado :: Matricula, Estado -> {V,F}

% Representacao de conhecimento positivo
estado('AA-BB-00', bom).
estado('AA-BB-11', razoavel).
estado('AA-BB-33', razoavel).

-estado(M,E):- (nao(estado(M,E)), nao(excecao(estado(M,E)))).

%_______________________________________________________________________________________
% INVARIANTES DO ESTADO

% Matricula tem de existir
+estado(M,_) :: (matricula(M)).

% Tipo de estado têm que ser válido
+estado( _,T ) :: (tipo_estado( T )).

% Não permite Inserção de conhecimento repetido
+estado(M,E) :: (solucoes(C,estado(M,E),L) , comprimento(L,1)).

% Só insere conhecimento se não houver conhecimento de que nao existe
+estado(M,E) :: (nao(-estado(M,E))).

% Só insere conhecimento negativo se não houver conhecimento de que existe
+(-estado(M,E)) :: (nao(estado(M,E))).

% Não permite Inserção de conhecimento repetido
+(-estado(M,E)) :: (solucoes(C,-estado(M,E),L) , comprimento(L,N), N=<2).


%_______________________________________________________________________________________
% REPRESENTAÇÂO DE CONHECIMENTO IMPRECISO - (Tipo 2)
% - O o estado do automóvel com matricula "aa-bb-22", nao é novo,
%   nas nao se sabe se é bom ou razoavel

-estado('AA-BB-22',novo).
excecao(estado( 'AA-BB-22' , bom )).
excecao(estado( 'AA-BB-22' , razoavel )).



%=========================================== ANO MATRICULA =============================
%_______________________________________________________________________________________
% anoMatricula :: Matricula, Ano, Mês -> {V,F}

% Representacao de conhecimento positivo
anoMatricula('AA-BB-00', 2012, 9).
anoMatricula('AA-BB-11', 2013, 10).
anoMatricula('AA-BB-22', 2014, 11).
anoMatricula('AA-BB-33', 2015, 12).

% Representacao de conhecimento negativo
-anoMatricula(MA,AN,ME):- (nao(anoMatricula(MA,AN,ME)),
                          nao(excecao(anoMatricula(MA,AN,ME)))).

%_______________________________________________________________________________________
% INVARIANTES DE ANOMATRICULA

% matricula tem de existir
+anoMatricula(M,_,_) :: (matricula(M)).

% ano e mês tem de ser inteiros
+anoMatricula(_,A,M) :: (integer(A),integer(M), M=<12, M>=1).

% não permite Inserção de conhecimento repetido
+anoMatricula(M,A,Ms) :: (solucoes(C,anoMatricula(M,A,Ms),L) , comprimento(L,1)).

% só insere conhecimento se não houver conhecimento de que nao existe
+anoMatricula(M,A,Ms) :: (nao(-anoMatricula(M,A,Ms))).

% só insere conhecimento negativo se não houver conhecimento de que existe
+(-anoMatricula(M,A,Ms)) :: (nao(anoMatricula(M,A,Ms))).

% não permite Inserção de conhecimento repetido
+(-anoMatricula(M,A,Ms)) :: (solucoes(C,-anoMatricula(M,A,Ms),L) , comprimento(L,N), N=<2).


%=========================================== REGISTO ===================================
%_______________________________________________________________________________________
% registo :: Matricula, Proprietario -> {V,F}

% Representacao de conhecimento positivo
registo('AA-BB-00', joaquim_albero).
registo('AA-BB-00', pedro_sousa).


% Representacao de conhecimento negativo
-registo(M,R):- (nao(registo(M,R)),
                 nao(excecao(registo(M,R)))).


%_______________________________________________________________________________________
% AUX:
% -> Todos os Proprietarios de uma determinado automovel
listareg(Y,R) :- solucoes(N,registo(Y,N),R).

%_______________________________________________________________________________________
% INVARIANTES DE REGISTO

% matricula tem de existir
+registo(M,_) :: (matricula(M)).

% não permite Inserção de conhecimento repetido
+registo(M,P) :: (solucoes(C,registo(M,P),L) , comprimento(L,1)).

% só insere conhecimento se não houver conhecimento de que nao existe
+registo(M,P) :: (nao(-registo(M,P))).

% só insere conhecimento negativo se não houver conhecimento de que existe
+(-registo(M,P)) :: (nao(registo(M,P))).

% não permite Inserção de conhecimento repetido
+(-registo(M,P)) :: (solucoes(C,-registo(M,P),L) , comprimento(L,N), N=<2).


%=========================================== PROPRIETARIO ==============================
%_______________________________________________________________________________________
% proprietario :: Matricula, Proprietario -> {V,F}

% Representacao de conhecimento positivo
proprietario('AA-BB-00',pedro).
proprietario('AA-BB-11',daniel).

% Representacao de conhecimento negativo
-proprietario('AA-AA-00',alberto).

-proprietario(M,P):- (nao(proprietario(M,P)),
                      nao(excecao(proprietario(M,P)))).

%_______________________________________________________________________________________

% Predicado para ATUALIZAR o proprietário de um dado automovel
atualizarProprietario( M,P ) :- (remocao( proprietario( M,_ ) ),
                                 evolucao( proprietario( M,P ))).

%_______________________________________________________________________________________
% INVARIANTES DE PROPRIETARIOS

% nao admite proprietario duplicados
+proprietario(M,_) :: (solucoes(P,proprietario(M,_),L) , comprimento(L,1)).

% matricula tem de existir
+proprietario(M,_) :: (matricula(M)).

% só insere conhecimento se não houver conhecimento de que nao existe
+proprietario(M,P) :: (nao(-proprietario(M,P))).

% só insere conhecimento negativo se não houver conhecimento de que existe
+(-proprietario(M,P)) :: (nao(proprietario(M,P))).

% não permite Inserção de conhecimento repetido
+(-proprietario(M,P)) :: (solucoes(C,-proprietario(M,P),L) , comprimento(L,N), N=<2).

%_______________________________________________________________________________________
% REPRESENTAÇÂO DE CONHECIMENTO INCERTO - (TIPO 1)

% - Sabe-se que existe um carro com matricula "aa-bb-33".
%   só nao se sabe onde quem é o Proprietario.

proprietario( 'AA-B3-33' , desconhecido ).
excecao(proprietario( A , P )) :- proprietario( A , desconhecido ).


%=========================================== AUXILIARES ================================

%_______________________________________________________________________________________
% Inserção de informação
% insert( Facto )

insert( F ) :- assert( F ).
insert( F ) :- retract( F ), !, fail.

%_______________________________________________________________________________________
% Remoção de informação
% remove( Facto )

remove( F ) :- retract( F ).
remove( F ) :- assert( F ), !, fail.

%_______________________________________________________________________________________
% Extensão do predicado que permite a adição de conhecimento

evolucao( Termo ) :- solucoes( Invariante,+Termo::Invariante,Lista ),
                     insert( Termo ),
                     teste( Lista ).

%_______________________________________________________________________________________
% Extensão do predicado que permite a remocao de conhecimento

remocao( Termo ) :- Termo,
                    solucoes( Invariante,-Termo::Invariante,Lista ),
                    remove( Termo ),
                    teste( Lista ).

%_______________________________________________________________________________________
% Extensao do predicado solucoes

solucoes( T,Q,L ) :- findall( T,Q,L ).

%_______________________________________________________________________________________
% Extensao do predicado comprimento

comprimento( [],0 ).
comprimento( [_|T],R ) :- comprimento( T,R2 ), R is R2+1.

%_______________________________________________________________________________________
% Extensao do predicado teste

teste( [] ).
teste( [H|T] ) :- H, teste( T ).

%_______________________________________________________________________________________
% Extensao do predicado concat

concat([],L,L).
concat([H|T],L,[H|Y]) :- concat(T,L,Y).

%_______________________________________________________________________________________
% Extensao do predicado excecao, para não permitir excecões duplicadas
+excecao(T) :: (solucoes(C,excecao(T),L) , comprimento(L,1)).


