:-dynamic(varNumber/3).
symbolicOutput(0). % set to 1 to see symbolic output only; 0 otherwise.

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

%   variables:
%   c-ciudad
%	i-atractivo
%   r-ciudad-atractivo

%%%%%%%%%%%%%%%%%%%%%%%%%%%%

input:- 
	interessos(I),
	member(M,I),
	ciutatsAmbInteres(M,C),
	atLeastOne(C),
	fail.
input.

ciutatsAmbInteres(I,C):-
	findall(X, (atractius(X,L), member(I,L)), C).

atLeastOne(L):-
	findall(c-M, member(M,L), C),
    writeClause(C).

subset([],[]).
subset([X|C],[X|S]):-subset(C,S).
subset([_|C],S):-subset(C,S).

negatedClause([],[]).
negatedClause([X|L],[\+c-X|S]):- negatedClause(L,S).

atMostKCities(N):-
	ciutats(C),
	N1 is N+1,
	subset(C,S),
	length(S,N1),
	negatedClause(S,Neg),
	writeClause(Neg),
	fail.
atMostKCities(_).

writeClauses(N):- input, atMostKCities(N).

displaySol([]):- nl.
displaySol([X|L]):-
	num2var(X,c-M),
	write(M), write(' '),
	displaySol(L).

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

main:- symbolicOutput(1),!, writeClauses(3), halt. % escribir bonito, no ejecutar
main:- ciutats(C), length(C,N), solucionOptima(N,N1), N2 is N1+1, v-N2-M, 
	write('Solució: '), displaySol(M), halt.

reverseNat(0,0):- !.
reverseNat(N,N1):- N1 is N-1.
reverseNat(N,N2):- N1 is N-1, reverseNat(N1,N2).

solucionOptima(N,N1):-
	reverseNat(N,N1),
	retractall(varNumber(_,_,_)), 
    retractall(num2var(_,_)), retractall(numClauses(_)), 
    retractall(numVars(_)),
	assert(numClauses(0)), assert(numVars(0)),
	unix('rm header clauses'),
	tell(clauses), writeClauses(N1), told,
	tell(header),  writeHeader,  told,
	unix('cat header clauses > infile.cnf'),
	unix('picosat -o model infile.cnf'),
	unix('cat model'),
	see(model), readModel(M), assert(v-N1-M), seen,
	M == [].

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