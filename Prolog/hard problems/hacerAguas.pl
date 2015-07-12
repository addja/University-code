camino( E,E, C,C ).
camino( EstadoActual, EstadoFinal, CaminoHastaAhora, CaminoTotal ):-
	unPaso( EstadoActual, EstSiguiente ),
	\+member(EstSiguiente,CaminoHastaAhora),
	camino( EstSiguiente, EstadoFinal, [EstSiguiente|CaminoHastaAhora], CaminoTotal ).

solucionOptima:-
	nat(N),
	camino([0,0],[0,4],[[0,0]],C),
	length(C,N),	
	write(C).

nat(0).
nat(N):- nat(N1), N is N1+1.

unPaso([X,Y],[X1,Y1]):- % llenar primer cubo
	X1 is 5, Y1 = Y.
unPaso([X,Y],[X1,Y1]):- % llenar segundo cubo 
	Y1 is 8, X1 = X.
unPaso([X,Y],[X1,Y1]):- % vaciar primer cubo 
	X1 is 0, Y1 = Y.
unPaso([X,Y],[X1,Y1]):- % vaciar segundo cubo 
	Y1 is 0, X1 = X.
unPaso([X,Y],[X1,Y1]):-  % pasar el contenido del primero al segundo 
	Tmp1 is 8 - Y, Tmp2 is min(Tmp1,X), X1 is X - Tmp2, Y1 is Y + Tmp2.
unPaso([X,Y],[X1,Y1]):-  % pasar el contenido del segundo al primero
	Tmp1 is 5 - X, Tmp2 is min(Tmp1,Y), X1 is X + Tmp2, Y1 is Y - Tmp2.