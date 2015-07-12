datosEjemplo( [[1,2,6],[1,6,7],[2,3,8],[6,7,9],[6,8,9],[1,2,4],[3,5,6],[3,5,7],
[5,6,8],[1,6,8],[4,7,9],[4,6,9],[1,4,6],[3,6,9],[2,3,5],[1,4,5],
[1,6,7],[6,7,8],[1,2,4],[1,5,7],[2,5,6],[2,3,5],[5,7,9],[1,6,8]] ).

nat(0).
nat(N):- nat(N1), N is N1+1.

insertLikeASet([X|L1],[Y|L2],[S|L3]):-
	union([X],Y,S),
	insertLikeASet(L1,L2,L3).
insertLikeASet([],[],[]).

generaSol([X|L],S,N):-
	generaSol(L,S1,N),
	permutation(X,X1),
	insertLikeASet(X1,S1,S),
	flatten(S,S2),
	length(S2,N1),
	N1 =< N.
generaSol([],[[],[],[]],_).

solucionOptima:-
	datosEjemplo(L),
	nat(N),
	generaSol(L,S,N),
	write(S).
