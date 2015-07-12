camino( E,E, C,C ).
camino( EstadoActual, EstadoFinal, CaminoHastaAhora, CaminoTotal ):-
	unPaso( EstadoActual, EstSiguiente ),
	noPodes(EstSiguiente,EstadoFinal),
	\+member(EstSiguiente,CaminoHastaAhora),
	camino( EstSiguiente, EstadoFinal, [EstSiguiente|CaminoHastaAhora], CaminoTotal ).

solucionOptima:-
	nat(N),
	camino(
		[ [[1,2,5,8],1]-[[],0],0 ] , % 1,2,5 y 8 en un lado del puente con la linterna en el instante 0
		[ [[],0]-[[1,2,5,8],1],N ] , % 1,2,5 y 8 en el otro lado del puente con la linterna en el instante N
		[ [[[1,2,5,8],1]-[[],0],0] ] ,
		C
	),
	C = [[_,N1]|_],
	N1 = N,	
	writeNice(C).

writeNice([]).
writeNice([[X,N]|L]):- writeNice(L), displayStep(X,N), nl.

nat(0).
nat(N):- nat(N1), N is N1+1.

movimiento(A,B,L):- member(A,L), member(B,L), A < B.
movimiento(A,L):- member(A,L).

noPodes([_,T1] , [_,T2]):- T2 >= T1.

removeElems(A,B,L,L3):-
	delete(L,A,L2),
	delete(L2,B,L3).
removeElem(A,L,L2):-
	delete(L,A,L2).

addElems(A,B,L,L3):-
	L2 = [A,B|L],
	sort(L2,L3).
addElem(A,L,L3):-
	L2 = [A|L],
	sort(L2,L3).

unPaso([[L1,1]-[L2,0],T1], [[L3,0]-[L4,1],T2]):-  % van dos
	movimiento(A,B,L1),
	T2 is T1 + max(A,B),
	removeElems(A,B,L1,L3),
	addElems(A,B,L2,L4).

unPaso([[L1,1]-[L2,0],T1], [[L3,0]-[L4,1],T2]):- % va uno
	movimiento(A,L1),
	T2 is T1 + A,
	removeElem(A,L1,L3),
	addElem(A,L2,L4).

unPaso([[L1,0]-[L2,1],T1] , [[L3,1]-[L4,0],T2] ):- 
	unPaso([[L2,1]-[L1,0],T1] , [[L4,0]-[L3,1],T2]).

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

% Print a nice output

displayStep([X,0]-[Y,1],Z):-
	write('En el instante: '), write(Z), write(' '),
	write(X), write('               '),
	write(' ]===[ '),
	write(Y), write(' + linterna').
displayStep([X,1]-[Y,0],Z):-
	write('En el instante: '), write(Z), write(' '),
	write(X),
	write(' + linterna'),
	write(' ]===[ '),
	write(Y).
	