:-include(entradaFlow1).
:-dynamic(varNumber/3).
symbolicOutput(0). % set to 1 to see symbolic output only; 0 otherwise.

%   variables:
%   s-X-Y-A-B q-> successor for X,Y is A,B
%   col-X-Y-C -> color form X,Y is C
%   a-X-Y-A-B -> X,Y can access A,B

writeClauses:- 
    input,
    exactlyOneColor,
    exactlyOneSuccessor,
    exactlyOneAntecessor,
    colorPropagation,
    noCicles.

noCicles:- 
    position(X,Y),
    succ(X,Y,A,B),
    notInitial(A,B),
    position(C,D),  
    writeClause([\+s-X-Y-A-B, \+a-C-D-X-Y, a-C-D-A-B]),
    fail.
noCicles:- noSelfAcces, noCicleAcces.

noCicleAcces:-
    position(X,Y),
    notFinal(X,Y),
    succ(X,Y,A,B),
    writeClause([\+s-X-Y-A-B, \+a-A-B-X-Y]),
    fail.
noCicleAcces.

noSelfAcces:-
    position(X,Y),
    writeClause([a-X-Y-X-Y]),
    fail.
noSelfAcces.

colorPropagation:-
    position(X,Y),
    notFinal(X,Y), 
    succ(X,Y,A,B),
    notInitial(A,B),
    color(C1),
    color(C2),
    C1 \= C2,
    writeClause([\+s-X-Y-A-B, \+col-A-B-C1, \+col-X-Y-C2]),
    fail.
colorPropagation.

exactlyOneSuccessor:- atLeastOneSuccessor, atMostOneSuccessor, forbiddenSuccessors.

exactlyOneAntecessor:- atLeastOneAntecessor, atMostOneAntecessor, forbiddenAntecessors.

forbiddenSuccessors:-
    final(X,Y),
    succ(X,Y,A,B),
    writeClause([\+s-X-Y-A-B]),
    fail.
forbiddenSuccessors.

forbiddenAntecessors:-
    initial(X,Y),
    ant(X,Y,A,B),
    writeClause([\+s-A-B-X-Y]),
    fail.
forbiddenAntecessors.

atLeastOneSuccessor:-
    position(X,Y),
    notFinal(X,Y),
    findall(s-X-Y-A-B, succ(X,Y,A,B), L),
    writeClause(L),
    fail.
atLeastOneSuccessor.

atMostOneSuccessor:-
    position(X,Y),
    notFinal(X,Y),
    succ(X,Y,A,B),
    notInitial(A,B),
    succ(X,Y,C,D),
    notInitial(C,D),
    notEqual(A,B,C,D),
    writeClause([\+s-X-Y-A-B, \+s-X-Y-C-D]),
    fail.
atMostOneSuccessor.

atLeastOneAntecessor:-
    position(X,Y),
    notInitial(X,Y),
    findall(s-A-B-X-Y, ant(X,Y,A,B), L),
    writeClause(L),
    fail.
atLeastOneAntecessor.

atMostOneAntecessor:-
    position(X,Y),
    notInitial(X,Y),
    ant(X,Y,A,B),
    notFinal(A,B),
    ant(X,Y,C,D),
    notFinal(C,D),
    notEqual(A,B,C,D),
    writeClause([\+s-A-B-X-Y, \+s-C-D-X-Y]),
    fail.
atMostOneAntecessor.

exactlyOneColor:- atLeastOneColor, atMostOneColor.

atMostOneColor:-
    position(X,Y),
    color(C1),
    color(C2),
    C1 \= C2,
    writeClause([\+col-X-Y-C1, \+col-X-Y-C2]),
    fail.
atMostOneColor.

atLeastOneColor:- 
    position(X,Y),
    findall(col-X-Y-C, color(C), L),
    writeClause(L),
    fail.
atLeastOneColor.

input:- 
    c(C,X,Y,A,B), 
    writeClause([col-X-Y-C]),
    writeClause([col-A-B-C]),
    fail.
input:- inputAcess.

%%

inputAcess:- 
    c(_,X,Y,A,B),
    writeClause([a-X-Y-A-B]),
    fail.
inputAcess.

notEqual(X,_,Y,_):- X \= Y, !.
notEqual(_,X,_,Y):- X \= Y, !.

position(X,Y):-
    size(S),
    between(1,S,X),
    between(1,S,Y).

succ(X,Y,A,B):- size(S), A is X+1, B is Y, A > 0, B > 0, A =< S, B =< S.
succ(X,Y,A,B):- size(S), A is X-1, B is Y, A > 0, B > 0, A =< S, B =< S.
succ(X,Y,A,B):- size(S), A is X, B is Y+1, A > 0, B > 0, A =< S, B =< S.
succ(X,Y,A,B):- size(S), A is X, B is Y-1, A > 0, B > 0, A =< S, B =< S.

ant(X,Y,A,B):- succ(X,Y,A,B).

notFinal(X,Y):- not(final(X,Y)).

notInitial(X,Y):- not(c(_,X,Y,_,_)).

color(C):- c(C,_,_,_,_).

final(S,T):- c(_,_,_,S,T).

initial(S,T):- c(_,S,T,_,_).

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

body:-
    tell(clauses), writeClauses, told,
    tell(header),  writeHeader,  told,
    unix('cat header clauses > infile.cnf'),
    unix('picosat -o model infile.cnf'),
    unix('cat model'),
    see(model), readModel(M), seen,
    length(M,X),
    write(X), nl,
    assert(hola),
    tell(clauses), newClauses(M), told,
    X > 0,
    body.
body.

countSols:-
    findall(hola,true,L),
    length(L,N), N1 is N-1,
    write('The number of possible solutions is: '),
    write(N1), nl.

newClauses([]).
newClauses([X|L]):- wmod(X), newClauses(L).

wmod(Lit):- num2var(Lit,Var), w(\+Var),!.


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

main:- symbolicOutput(1), !, writeClauses, halt. % escribir bonito, no ejecutar
main:-  assert(numClauses(0)), assert(numVars(0)),
    body, countSols,
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

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

rgb( blue,         0,   0 ,    1).
rgb( brown,     0.72, 0.53,  0.4).
rgb( red,          1,    0,    0).
rgb( cyan,         0,    1,    1).
rgb( green,        0,  0.5,    0).
rgb( yellow,       1,    1,    0).
rgb( pink,         1, 0.75,  0.8).
rgb( violet,    0.54, 0.17, 0.89).
rgb( orange,       1, 0.55,    0).
rgb( darkblue,     0,    0, 0.55).
rgb( darkgreen,    0, 0.39,    0).
rgb( darkred,   0.55,    0,    0).
rgb( darkcyan,     0, 0.55, 0.55).
rgb( white,      0.9,  0.9,  0.9).
rgb( grey,       0.5,  0.5,  0.5).



writeHeaderPS:-
    writeln('%!PS'),
    writeln('matrix currentmatrix /originmat exch def'),
    writeln('/umatrix {originmat matrix concatmatrix setmatrix} def'),
    writeln('[28.3465 0 0 28.3465 10.5 100.0] umatrix').

writeGrid:-
    writeln('0.01 setlinewidth'),
    writeVertGrid,
    writeHorizGrid.

writeVertGrid:-
    size(R), size(C), C1 is C+1,
    between(1,R,I), between(1,C1,J), drawVertical(I,J),fail.
writeVertGrid.

writeHorizGrid:-
    size(R), size(C), R1 is R+1,
    between(1,R1,I), between(1,C,J), drawHorizontal(I,J),fail.
writeHorizGrid.

drawVertical(I,J):-
    size(R),size(C),
    Size is min(22/R,18/C),
    X is 1+(J-1)*Size,
    Y is 23-(I-1)*Size,
    write(X), write(' '), write(Y), write(' moveto'),nl,
    Y1 is Y-Size,
    write(X), write(' '), write(Y1), write(' lineto'),nl,
    writeln('stroke').

drawHorizontal(I,J):-
    size(R),size(C),
    Size is min(22/R,18/C),
    X is 1+(J-1)*Size,
    Y is 23-(I-1)*Size,
    write(X), write(' '), write(Y), write(' moveto'),nl,
    X1 is X+Size,
    write(X1), write(' '), write(Y), write(' lineto'),nl,
    writeln('stroke').

writeInits:-
    c(C,X1,Y1,X2,Y2),
    writeInit(X1,Y1,C),
    writeInit(X2,Y2,C),
    fail.
writeInits.

writeInit(I,J,K):-
    size(R),size(C),
    Size is min(22/R,18/C),
    X is 1+(J-1)*Size +   Size/2,
    Y is 23-(I-1)*Size -  Size/2,
    Radius is Size/8,
    write(X), write(' '), write(Y), write(' '), write(Radius), writeln(' 0 360 arc'),
    writeln('gsave'),
    rgb(K,Red,Green,Blue),
    write(Red), write(' '), write(Green), write(' '), write(Blue), write(' setrgbcolor'),nl,
    writeln('fill'),
    writeln('grestore'),
    writeln('0.01 setlinewidth'),
    writeln('stroke').

writeSolution([X|M]):-
    writeLine(X),
    writeSolution(M).
writeSolution([]).

assertColors([]).
assertColors([X|L]):-
    num2var(X,col-I-J-K),!,
    assert(color(I,J,K)),
    assertColors(L).
assertColors([_|L]):-
    assertColors(L).
    
writeLine(P):-
    num2var(P,s-X1-Y1-X2-Y2),!,
    size(R),size(C),
    color(X1,Y1,K),
    color(X1,Y2,K),
    Size is min(22/R,18/C),
    X is 1+(Y1-1)*Size + Size/2,
    Y is 23-(X1-1)*Size - Size/2,
    XF is 1+(Y2-1)*Size + Size/2,
    YF is 23-(X2-1)*Size - Size/2,
    rgb(K,Red,Green,Blue),
    writeln('0.11 setlinewidth'),
    write(Red), write(' '), write(Green), write(' '), write(Blue), write(' setrgbcolor'),nl,
    write(X), write(' '), write(Y), write(' moveto'),nl,
    write(XF), write(' '), write(YF), write(' lineto'),nl,
    writeln('stroke').
writeLine(_).

displaySol(M):-
    tell('graph.ps'),
    writeHeaderPS,
    writeGrid,
    writeInits,
    assertColors(M),
    writeSolution(M),
    writeln('showpage'),
    told.

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%% v1

%% noCicles:- 
%%     position(X,Y),
%%     succ(X,Y,A,B),
%%     writeClause([\+s-A-B-X-Y, \+s-X-Y-A-B]),
%%     fail.
%% noCicles.

%% v2

%% noCicles:-
%%     position(X,Y),
%%     fitCicle(X,Y),
%%     succ(X,Y,A,B),
%%     fitCicle(A,B),
%%     cicle(X,Y,A,B,[(A,B)]),
%%     fail.
%% noCicles.

%% cicle(X,Y,X,Y,L):- append(L,[(X,Y)],L1), formatClause(L1,[]).
%% cicle(X,Y,A,B,L):-
%%     succ(A,B,C,D),
%%     fitCicle(C,D),
%%     not(member((C,D),L)),
%%     cicle(X,Y,C,D,[(C,D)|L]),
%%     fail.
%% cicle(_,_,_,_,_).

%% formatClause([(_,_)],L):- writeClause(L).
%% formatClause([(A,B)|X],L):-
%%     append([(C,D)],_,X),
%%     formatClause(X,[\+s-A-B-C-D|L]).

%% fitCicle(X,Y):-
%%     notFinal(X,Y),
%%     notInitial(X,Y).