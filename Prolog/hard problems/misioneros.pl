camino( E,E, C,C ).
camino( EstadoActual, EstadoFinal, CaminoHastaAhora, CaminoTotal ):-
	unPaso( EstadoActual, EstSiguiente ),
	\+member(EstSiguiente,CaminoHastaAhora),
	camino( EstSiguiente, EstadoFinal, [EstSiguiente|CaminoHastaAhora], CaminoTotal ).

solucionOptima:-
	nat(N),
	camino(
		[ [3,3,1]-[0,0,0] ] , % tres misioneros y tres canivales + canoa en una orilla
		[ [0,0,0]-[3,3,1] ] , % tres misioneros y tres canivales + canoa en la otra orilla
		[ [[3,3,1]-[0,0,0]] ] ,
		C
	),
	length(C,N),	
	writeNice(C).

writeNice([]).
writeNice([[X]|L]):- displayStep(X), nl, writeNice(L).

nat(0).
nat(N):- nat(N1), N is N1+1.

orillaOk(0,_). 			% nunca + canivales que misioneros
orillaOk(X,Y):- X >= Y.	% nunca + canivales que misioneros

movimiento(A,B):- member(A,[0,1,2]), member(B,[0,1,2]), A+B < 3, A+B > 0.

unPaso([ [X,Y,1]-[Z,W,0] ] , [ [X1,Y1,0]-[Z1,W1,1] ]):- 
	movimiento(A,B),
	X >= A, Y >= B,
	X1 is X-A, Y1 is Y-B,
	Z1 is Z+A, W1 is W+B,
	orillaOk(X1,Y1), orillaOk(Z1,W1).

unPaso([ [X,Y,0]-[Z,W,1] ] , [ [X1,Y1,1]-[Z1,W1,0] ]):-
	unPaso([ [Z,W,1]-[X,Y,0] ] , [ [Z1,W1,0]-[X1,Y1,1] ]).

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

% Print a nice output

displayStep([X,Y,0]-[A,B,1]):-
	write(X), write(' misioneros, '),
	write(Y), write(' canivales'),
	write('        '),
	write(' ||| '),
	write(A), write(' misioneros, '),
	write(B), write(' canivales'),
	write(' + canoa').
displayStep([X,Y,1]-[A,B,0]):-
	write(X), write(' misioneros, '),
	write(Y), write(' canivales'),
	write(' + canoa'),
	write(' ||| '),
	write(A), write(' misioneros, '),
	write(B), write(' canivales').