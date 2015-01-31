#!/usr/bin/python
# -*- coding: utf-8 -*-

# This code doesn't do anything,
# it's just lective to show how to use classes in python

# defclass
class Punt:
	"Class that represents points"
	puntCompt = 0					# Class value, shared with all instances

	def __init__( self, x=0, y=0):  # Format for constuctor
		"Constructor"
		self.x = x					# Instance value
		self.y = y
		Punt.puntCompt += 1

	def __del__(self):				# Format for destructor
		nom_classe = self.__class__.__name__
		print nom_classe, "destroyed"
		Punt.puntCompt -= 1

	def mostraPunt(self):			# Class method
		print "(%d,%d)" % (self.x, self.y)

pt1 = Punt()
pt2 = pt1
pt3 = Punt(3,2)
print id(pt1), id(pt2), id(pt3)
del pt1

# inheritance
class Fill(Punt):
	def fillMetode(self):
		print "Cridem al metode del fill"

c = Fill()
c1 = Fill(3,2)
c.fillMetode()
c1.mostraPunt()
print c1.x

# hide attributes
class Comptador:
	__ocultCompt = 0				# Hide attribute

	def compta(self):
		self.__ocultCompt += 1
		print. self.__ocultCompt

compt = Comptador()
compt.compta()
print compt.__ocultCompt			# Way to access the attribute

