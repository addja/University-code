#!/usr/bin/env python

import os, sys

def fib (n):
	if n <= 1:
		return 1
	else:
		return fib(n-1) + fib(n-2)


def memo (f):
	
	d = {}
	
	def f2 (x):
		if x in d:
			return d[x]
		else:
			d[x] = f(x)
			return d[x]

	return f2


def fact (n):
	...



fib = memo(fib)
fact2 = memo(fact)
print fib(35)

