#!/usr/bin/python
# -*- coding: utf-8 -*-

import sys

print 'El programa', sys.argv[0], 'té', len(sys.argv)-1, 'arguments'	
print "hola " + sys.argv[1] + "!"

word = "Llenguatges"
print word[:7] + " de " + word

dic = {}
dic["prim"] = "el primer"
dic[2] = "el segon"
print dic
dic = {"nom": "albert","num":37899, "dept": "computer science"}
print dic
dic["prim"] = "el primer"
dic[2] = "el segon"
print dic

x = 23.0
print isinstance(x, float)

quadrats1 = [x**2 for x in range(10)]
print quadrats1

parells = [(x,y) for x in range(3) for y in range(3) if x!=y]
print parells

L = [1,2,3]
L1 = [3,4,5]
x = 4
if x in L: 
	print x, "hi es"
elif x in L1: 
	print x, "no hi es a la primera pero si a la segona"
else:
	print x, "no hi es a cap llista"

for i in range(len(L1)):
	if L[i] % 2 == 0:
		s = str(L[i])+" es parell"
	else:
		s = str(L[i])+" es senar"
	print s

n = raw_input("\nIntrodueix un string.")
print "hem llegit", n

def divideix(elem, llista):
	"retorna si l’element divideix a algun valor de la llista o no"
	for x in llista:
		if x % elem == 0: return True
	return False

print divideix(int(n), [2,3,4,5,6,7,8])

# lambda expression
def map(fun, llista):
	"retorna si l’aplicacio de la funcio a tot els elements"
	L = []
	for x in llista:
		L.append(fun(x))
	return L

print map(lambda arg: arg*2, [3,6,8,1])

# visibility is restricted to locality except global vars
global i = 0;

