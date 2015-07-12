% factorial
fact(0,1):-!.
fact(X,F):- X1 is X - 1, fact(X1,F1), F is X * F1.

% natural numbers generator
nat(0).
nat(N):- nat(N1), N is N1 + 1.
% querry that writes them all -> nat(N), write(N), nl, fail.

% mcm of two numbers
mcm(X,Y,M):- nat(N), N>0, M is N * X, 0 is M mod Y.

% list lenght
len([],0).
len([_|L],M):- len(L,N), M is N+1.

% permutations
pert_con_resto(X,L,Resto):- concat(L1,[X|L2], L ), 
concat(L1, L2, Resto).

permutation([],[]).
permutation(L,[X|P]) :- pert_con_resto(X,L,R), permutation(R,P).

% prime factors
prime_factors(1,[]) :- !.
prime_factors(N,[F|L]):- nat(F), F>1, 0 is N mod F,
N1 is N // F, prime_factors(N1,L),!.

% subsets
subset([],[]).
subset([X|C],[X|S]):-subset(C,S).
subset([_|C],S):-subset(C,S).

% operations to generate a number with a list of numebers
cifras(L,N):- subset(L,S), permutation(S,P), expresion(P,E),
N is E, write(E),nl,fail.

expresion([X],X).
expresion(L,E1+E2):- concat(L1,L2,L), L1\=[],L2\=[],
expresion(L1,E1), expresion(L2,E2).
expresion(L,E1-E2):- concat(L1,L2,L), L1\=[],L2\=[],
expresion(L1,E1), expresion(L2,E2).
expresion(L,E1*E2):- concat(L1,L2,L), L1\=[],L2\=[],
expresion(L1,E1), expresion(L2,E2).

% derivates
der(X,X,1):-!.
der(C,_,0):- number(C).
der(A+B,X,DA+DB):- der(A,X,DA),der(B,X,DB).
der(A-B,X,DA-DB):- der(A,X,DA),der(B,X,DB).
der(A*B,X,A*DB+B*DA):- der(A,X,DA),der(B,X,DB).
der(sin(A),X,cos(A)*DA):- der(A,X,DA).
der(cos(A),X,-sin(A)*DA):- der(A,X,DA).
der(eˆA,X,DA*eˆA):- der(A,X,DA).
der(ln(A),X,DA*1/A):- der(A,X,DA).

% simplify equations
simplify(E,E1):- step(E,E2),!, simplify(E2,E1).
simplify(E,E).

step(A+B,A+C):- step(B,C),!.
step(B+A,C+A):- step(B,C),!.
step(A*B,A*C):- step(B,C),!.
step(B*A,C*A):- step(B,C),!.
step(0*_,0):-!.
step(_*0,0):-!.
step(1*X,X):-!.
step(X*1,X):-!.
step(0+X,X):-!.
step(X+0,X):-!.
step(N1+N2,N3):- number(N1), number(N2), N3 is N1+N2,!.
step(N1*N2,N3):- number(N1), number(N2), N3 is N1*N2,!.
step(N1*X+N2*X,N3*X):- number(N1), number(N2), N3 is N1+N2,!.
step(N1*X+X*N2,N3*X):- number(N1), number(N2), N3 is N1+N2,!.
step(X*N1+N2*X,N3*X):- number(N1), number(N2), N3 is N1+N2,!.
step(X*N1+X*N2,N3*X):- number(N1), number(N2), N3 is N1+N2,!.
