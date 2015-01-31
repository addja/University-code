#!/usr/bin/python
# -*- coding: utf-8 -*-

def myListProd(plist):
	"Computes the product of all the elements of the given list"

	return reduce(lambda y, acc: acc*y, plist, 1)

def myListProdEvens(plist):
	"Computes the product of all the even elements of the given list"

	filtered = filter (lambda x: x % 2 == 0, plist)
	return reduce(lambda y, acc: acc*y, filtered, 1)

def myReverse(plist):
	"Reverses the given list using the high order function reduce"

	return reduce(lambda acc, x: [x]+acc, plist, [])

def myAparitions(plist, elem):
	"Counts the number of aparitions of a given element on\
	the elements of the given list"

	return map(lambda x: len(filter(lambda z: z == elem, x)), plist)


# To call functions

# Toxic input
#inputList = eval(raw_input("Introduce the list: "))
#print inputList

# Curatred input
#inputList = raw_input("Introduce the list: ")
#inputList = map(int, inputList[1:-1].split(","))

#print "Product of the elements of a list:", myListProd(inputList)
#print "Product of the even elements of a list:", myListProdEvens(inputList)
#print "Reverse of the list:", myReverse(inputList)
#print "The number of aparitions is:", myAparitions(inputList,elem)