% 1

% list lenght
len([],0).
len([_|L],M):- len(L,N), M is N+1.

% permutations
permutation([],[]).
permutation([X|L],P) :- first_perm(X,L,R), permutation(R,P).

% subsets
subset([],[]).
subset([X|C],[X|S]):- subset(C,S).
subset([_|C],S):- subset(C,S).

% 2

% product of the elements of a list
prod([],1).
prod([X|L1],P):- prod(L1,P1), P is X*P1.

% 3

% dot product of two lists
pescalar([],[],0).
pescalar([X|L1],[Y|L2],R):- pescalar(L1,L2,R2), R is R2+X*Y.

% 4

% tests membership of an elment given a list
member(X,[X|_]).
member(X,[_|L2]):- member(X,L2).

% intersection of two lists with no repeated elements
inter([],_,[]).
inter([X|L1],L2,[X|L3]):- member(X,L2),inter(L1,L2,L3).
inter([_|L1],L2,L3):- inter(L1,L2,L3). 

% union of two lists with no repeated elements
union([],L,L).
union([X|L1],L2,L3):- member(X,L2),union(L1,L2,L3).
union([X|L1],L2,[X|L3]):- union(L1,L2,L3).

% 5

% concatenation of two given lists
concat([],L,L).
concat([X|L1],L2,[X|L3]):- concat(L1,L2,L3).

% last element of a list using concat
tail(L,X):- concat(_,[X],L).

% invers of a list using concat
inverse([],[]).
inverse([X|L1],L2):- inverse(L1,L3), concat(L3,[X],L2).

% 6

% fibonacci numbers
fib(0,0).
fib(1,1).
fib(N,F):- N > 1, N1 is N-1, N2 is N-2, 
	fib(N1,F1), fib(N2,F2), F is F1+F2.

% 7

% all combitations to achieve a number using a dice an N rolls
dados(0,0,[]).
dados(X,N,[X1|L]):- X > 0, N > 0, member(X1,[1,2,3,4,5,6]),
	X2 is X-X1, N2 is N-1, dados(X2,N2,L).

% 8

% makes all the possible permutations for the frist position 
% to members of a list
first_perm(X,L,R):- concat(L1,[X|L2],L), concat(L1,L2,R).

% sums all the elements of a list
sum([],0).
sum([X|L],R):- sum(L,R2), R is X+R2.

% searches if an element of a list is the sum of all the others
suma_demas(L):- first_perm(X,L,R), sum(R,X), !.

% 9

% searches if an element is the sum of all the previous elements
suma_ants(L):- concat(L1,[X|_],L), sum(L1,X), !.

% 10

% calculates the cardinality of each element on the list
card([],[]).
card([X|L],[[X,N]|R]):- 
	card(L,C), 
	first_perm([X,N1],C,R), !,
	N is N1+1.
card([X|L],[[X,1]|R]):- card(L,R).
card(L):- card(L,R), write(R).

% 11

% finds if a given list is sorted or not
is_ordered([]).
is_ordered([_]).
is_ordered([X,Y|L]):-  X =< Y, is_ordered([Y|L]).

% 12 13 14 15 16

% makes a permutation on a list
permutation([], []).
permutation(L, [X|P]):- first_perm(X, L, R),
						permutation(R, P).

% sorts a given list (UNEFFICIENT)
% sorting(L1,L2):- permutacion(L1,L2), is_ordered(L2).

% sorts a given list using insertion sort
insertion(X,[],[X]):- !.
insertion(X,[Y|L],[X,Y|L]):- X =< Y.
insertion(X,[Y|L1],[Y|L2]):- insertion(X,L1,L2).

sorting([],[]).
sorting([X|L1],L2):- sorting(L1,L3), insertion(X,L3,L2).

% merge sort algorithm
merge([],L,L):- !.
merge(L,[],L):- !.
merge([X|L1],[Y|L2],[X|L]):- X =< Y, !, merge(L1,[Y|L2],L).
merge([X|L1],[Y|L2],[Y|L]):- merge([X|L1],L2,L).

split([],[],[]):- !.
split([A],[A],[]):- !.
split([A,B|L],[A|AL],[B|BL]):- split(L,AL,BL).

merge_sort([],[]):- !.
merge_sort([X],[X]):- !.
merge_sort(L,R):- 	split(L,L1,L2),
					merge_sort(L1,L3), merge_sort(L2,L4),
					merge(L3,L4,R).

% 17

% given a dictionaire of words, prints all the combinations
nmembers(_,0,[]):- !. 
nmembers(A,N,[X|L]):- N1 is N-1, nmembers(A,N1,L),
				  	  member(X,A).

writeTogether([]):- write(' '), !.
writeTogether([X|L]):- write(X), writeTogether(L).

dictionaire(_,0):- write([]), !.
dictionaire(A,N):- N > 0, nmembers(A,N,L), 
					writeTogether(L), fail.

% 18

% creates palindroms using a given list
palindroms_check([]).
palindroms_check([_]).
palindroms_check([X|L]):- concat(L1,[X],L),
							palindroms_check(L1).

palindroms(L):- setof(X,(permutation(L, X), palindroms_check(X)),Y),
				write(Y).

% 19

sendMoreMoney:-
	first_perm(S,[0,1,2,3,4,5,6,7,8,9],R1),
	first_perm(E,R1,R2), 
	first_perm(N,R2,R3),
	first_perm(D,R3,R4),
	first_perm(M,R4,R5), 
	first_perm(O,R5,R6), 
	first_perm(R,R6,R7),
	first_perm(Y,R7,_),
	Send is S * 1000 + E * 100 + N * 10 + D,
	More is M * 1000 + O * 100 + R * 10 + E,
	Money is M * 10000 + O * 1000 + N * 100 + E * 10 + Y,
	Tmp is Send + More,
	Money = Tmp,
	write(Send), write('+'), write(More), write('='), write(Money), nl,
	write('S='), write(S), nl, write('E='), write(E), nl, write('N='), write(N), nl, write('D='), write(D), nl,
	write('M='), write(M), nl, write('O='), write(O), nl, write('R='), write(R), nl, write('Y='), write(Y), nl.

sendMoreMoney:- write('there are no solutions'), nl.

% 20

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

% 21
% Done in pràctica 4