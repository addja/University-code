:-include(sud1).
:-dynamic(varNumber/3).
symbolicOutput(0). % set to 1 to see symbolic output only; 0 otherwise.

writeClauses:- exactlyOnePerBox, noRepeated, filledBoxes.

exactlyOnePerBox:- atLeastOnePerBox, atMostOnePerBox.

atLeastOnePerBox:- col(I), row(J), 
	findall( I-J-K, range9(K), C ), writeClause(C), fail.
atLeastOnePerBox.

atMostOnePerBox:- col(I), col(J), range9(K1), range9(K2), K1 < K2,
	writeClause( [ \+I-J-K1, \+I-J-K2 ] ), fail.
atMostOnePerBox.

noRepeated:- noRepeatedOnRow, noRepeatedOnCol, noRepeatedOnSquare.

noRepeatedOnRow:- row(I), range9(K), col(J1), col(J2), J1 < J2,
	writeClause( [ \+I-J1-K, \+I-J2-K ] ), fail.
noRepeatedOnRow.

noRepeatedOnCol:- col(J), range9(K), row(I1), row(I2), I1 < I2,
	writeClause( [ \+I1-J-K, \+I2-J-K ] ), fail.
noRepeatedOnCol.

noRepeatedOnSquare:- range2(I), range2(J),
	I1 is I*3+1, J1 is J*3+1, square(I1,J1), fail.
noRepeatedOnSquare.

square(I,J):- I1 is I+2, J1 is J+2,
	range9(K),
	findall(X-Y-K, (between(I,I1,X), between(J,J1,Y)), C),
	oneCombination(C).
square(_,_).

filledBoxes:- filled(X,Y,Z), writeClause([X-Y-Z]), fail.
filledBoxes.

%%

displaySol([],_):- write('|'), writeLine.
displaySol(L,9):- write('|'), writeLine, displaySol(L,0).
displaySol(L,-1):- writeLine, displaySol(L,0).
displaySol([Nv|S],N):-
	num2var(Nv,I-J-K), write('|'), 
	write(K), N1 is N+1, displaySol(S,N1).

writeLine:- nl, write('-------------------'), nl.

range9(I):- between(1,9,I).
range2(I):- between(0,2,I).
col(I):- range9(I).
row(I):- range9(I).

oneCombination(C):-
	member(X,C), cut(X,C,L), member(Y,L),
	writeClause( [ \+X, \+Y ] ), fail.
oneCombination(_).

cut(X,C,L):- append(_,[X|L],C).

notEqual(X,_,Y,_):- X \= Y, !.
notEqual(_,X,_,Y):- X \= Y, !.


% ========== No need to change the following: =====================================

main:- symbolicOutput(1), !, writeClauses, halt. % escribir bonito, no ejecutar
main:-  assert(numClauses(0)), assert(numVars(0)),
	tell(clauses), writeClauses, told,
	tell(header),  writeHeader,  told,
	unix('cat header clauses > infile.cnf'),
	unix('picosat -v -o model infile.cnf'),
	unix('cat model'),
	see(model), readModel(M), seen, displaySol(M,-1),
	halt.

var2num(T,N):- hash_term(T,Key), varNumber(Key,T,N),!.
var2num(T,N):- retract(numVars(N0)), N is N0+1, assert(numVars(N)), hash_term(T,Key),
	assert(varNumber(Key,T,N)), assert( num2var(N,T) ), !.

writeHeader:- numVars(N),numClauses(C),write('p cnf '),write(N), write(' '),write(C),nl.

countClause:-  retract(numClauses(N)), N1 is N+1, assert(numClauses(N1)),!.
writeClause([]):- symbolicOutput(1),!, nl.
writeClause([]):- countClause, write(0), nl.
writeClause([Lit|C]):- w(Lit), writeClause(C),!.
w( Lit ):- symbolicOutput(1), write(Lit), write(' '),!.
w(\+Var):- var2num(Var,N), write(-), write(N), write(' '),!.
w(  Var):- var2num(Var,N),           write(N), write(' '),!.
unix(Comando):-shell(Comando),!.
unix(_).

readModel(L):- get_code(Char), readWord(Char,W), readModel(L1), addIfPositiveInt(W,L1,L),!.
readModel([]).

addIfPositiveInt(W,L,[N|L]):- W = [C|_], between(48,57,C), number_codes(N,W), N>0, !.
addIfPositiveInt(_,L,L).

readWord(99,W):- repeat, get_code(Ch), member(Ch,[-1,10]), !, get_code(Ch1), readWord(Ch1,W),!.
readWord(-1,_):-!, fail. %end of file
readWord(C,[]):- member(C,[10,32]), !. % newline or white space marks end of word
readWord(Char,[Char|W]):- get_code(Char1), readWord(Char1,W), !.
%========================================================================================
