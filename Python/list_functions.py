#!/usr/bin/python
# -*- coding: utf-8 -*-

def myLen(plist):
	"returns the length of a given list"
	length = 0
	for x in plist:
		length += 1 
	return length

def myMax(plist):
	"returns the max value of a given non-empty list"
	max = plist[0]
	for x in plist[1:]:
		if x > max: max = x
	return max

def myAverage(plist):
	"returns the average of a given non-empty list"
	length = 0
	sumatory = 0
	for x in plist:
		length += 1 
		sumatory += x
	return float(sumatory/length)

def myFlatten(plist):
	"given a list of lists returns a single list with all the elements \
	form the origunal in just one list"
	auxList = []
	for x in plist:
		if isinstance(x, list): auxList += myFlatten(x)
		else: auxList += [x]
	return auxList 

def myInsertion(plist, x):
	"Inserts a given value on a sorted list keeping it sorted"
	for i in range(len(plist)):
		if plist[i] > x: return plist[:i] + [x] + plist [i:]
	return plist + [x]

def myOddEvenFilter(plist):
	"Given a list of numbers, creates a list of two lists; \
	one with the odds and another with the evens"
	# a little bit ineficient
	#odds = filter(lambda arg: arg%2 != 0, plist)
	#evens = filter(lambda arg: arg%2 == 0, plist)
	odds = []
	evens = []
	for x in plist:
		if x%2 == 0: evens += [x]
		else: odds += [x]
	return [odds] + [evens]

def myPrimeDivisors(elem):
	"Given a number returns the list of its prime divisors"
	copyelem = elem
	result = []
	div = 2
	while div <= elem/2 and copyelem != 0:
		if copyelem % div == 0:
			result += [div]
			while copyelem%div == 0:
				copyelem /= div
		else: div += 1
	return result

def myMerger(plist1, plist2):
	"Given two ordered lists unifies them making a unic sorted list"
	tmpList = []
	it1 = 0;
	it2 = 0;
	for i in range(len(plist1)+len(plist2)):
		
		if plist1[it1] > plist2[it2]: 
			tmpList += [plist2[it2]]
			it2 += 1
		else: 
			tmpList += [plist1[it1]]
			it1 += 1

		if it1 == len(plist1):
			tmpList += plist2[it2:]
			break

		if it2 == len(plist2):
			tmpList += plist1[it1:]
			break

	return tmpList

def myMergeSort(plist):
	"Sorts the given list using the mergesort algotithm"
	
	if len(plist) == 1: 
		return plist

	length = len(plist)
	firstHalf = plist[:length/2]
	secondHalf = plist[length/2:]
	firstHalf = myMergeSort(firstHalf)
	secondHalf = myMergeSort(secondHalf)

	return myMerger(firstHalf,secondHalf)

def myQuickSort(plist):
	"Sorts the given list using the quicksort algotithm \
	implemented with comprehension lists"
	
	length = len(plist)
	if length == 0: return plist
	
	pivotpos = length/2
	pivot = plist[pivotpos]
	newList = plist[:pivotpos] + plist[pivotpos+1:]
	smaller = [x for x in newList if x <= pivot]
	bigger = [x for x in newList if x > pivot]

	return myQuickSort(smaller) + [pivot] + myQuickSort(bigger)


# To call functions

# Toxic input
#inputList = eval(raw_input("Introduce the list: "))
#print inputList

# Curatred input
#inputList = raw_input("Introduce the list: ")
#inputList = map(int, inputList[1:-1].split(","))

#elem = eval(raw_input("Introduce element: "))

#print "Lenght:", myLen(inputList)
#print "Maximum:", myMax(inputList)
#print "Average:", myAverage(inputList)
#print "Fatten list:", myFlatten(inputList)
#print "List with the element inserted:", myInsertion(inputList, elem)
#print "Odds and evens separated:", myOddEvenFilter(inputList)
#print "Prime divisors of the element:", myPrimeDivisors(elem)
#print "Ordered list using mergesort:", myMergeSort(inputList)
#print "Ordered list using quicksort:", myQuickSort(inputList)