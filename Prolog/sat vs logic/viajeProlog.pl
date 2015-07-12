% Datos entrada
ciutats([paris,bangkok,montevideo,windhoek,male,delhi,reunion,lima,banff]).

interessos([paisatges,cultura,etnies,gastronomia,esport,relax]).

atractius( paris,     [cultura,gastronomia]      ).
atractius( bangkok,   [paisatges,relax,esport]   ).
atractius( montevideo,[gastronomia,relax]        ).
atractius( windhoek,  [etnies,paisatges]         ).
atractius( male,      [paisatges,relax,esport]   ).
atractius( delhi,     [cultura,etnies]           ).
atractius( reunion,   [esport,relax,gastronomia] ).
atractius( lima,      [paisatges,esport,cultura] ).
atractius( banff,     [esport,paisatges]         ).

%%%%%%%%%%%%%%%%%%%%%%%%%%%%

nat(0).
nat(N):- nat(N1), N is N1+1.

subset([],[]).
subset([X|C],[X|S]):-subset(C,S).
subset([_|C],S):-subset(C,S).

interessosCiutats([],[]).
interessosCiutats([X|L],[I|S]):-
	atractius(X,I),
	interessosCiutats(L,S).

members(_,[]).
members(L,[X|S]):-
	member(X,L),
	members(L,S).

compleixInteressos(L):-
	interessosCiutats(L,L1),
	flatten(L1,L2),
	interessos(I),
	members(L2,I).

generaSol(N,L):-
	ciutats(C),
	subset(C,L),
	length(L,N),
	compleixInteressos(L).
generaSol(N,_):-
	ciutats(C),
	length(C,N1),
	N1 < N,
	write('Problema no satisfactible'), nl,
	halt.

solucionOptima:-
	nat(N),
	generaSol(N,S),
	write('Solució: '), write(S).