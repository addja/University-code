#!/usr/bin/python
# -*- coding: utf-8 -*-

# Pregunta 1 apartado a
# Práctica criptografía clave secreta

# aes.py -> código del aes correcto
# aes_mod_p1.py -> código del aes
# donde subbytes es la identidad


# librerias

import binascii
import os
import sys
import subprocess


# definiciones de funciones

def string_bits(texto):
	"Combierte un string a bits"
	return bin(int(binascii.hexlify(texto), 16))	

def bits_correctos(cadena, bit):
	"Compreba que el valor de bit sea correcto"
	if not bit.isdigit():
		print "input invalido"
 		sys.exit()
 	if int(bit) > (len(cadena)-2) or int(bit) < 0:
 		print "bit fuera de rango"
 		sys.exit()

def numeros_diferentes(b1, b2):
 	"Comprueba que los numeros dados son diferentes"
 	if b1 == b2:
 		print "Los bits proporcionados no son diferentes"
 		sys.exit()

def modifica_bit(bits, b):
	"Cambia el valor del bit b de un número binaro dado"
	x = list(bits)
  	i = len(x) - int(b) - 1
	if x[i] == "0": 
		x[i] = "1"
	else:
		x[i] = "0"
	return "".join(x)


# script

# lee el texto que quiere ser cifrado
texto = str(raw_input("Introduce el texto que quieres cifrar: "))


# imprime datos sobre el mensaje original (M)
print "Mensaje original: " + texto
texto_bits = string_bits(texto)
print "Mensaje original en bits: " + texto_bits
print

# obtine los mensajes modificados en 1 bit (Mi, Mj) y en ambos (Mij)
bit1 = raw_input("¿Que bit quieres cambiar? (0 - " 
	+ str(len(texto_bits)-2) + ") ")
bits_correctos(texto_bits, bit1)
bit2 = raw_input("¿Que otro bit quieres cambiar? (0 - " 
	+ str(len(texto_bits)-2) + ") ")
print
bits_correctos(texto_bits, bit2)
numeros_diferentes(int(bit1), int(bit2))
textoMi_bits = modifica_bit(texto_bits, bit1)
textoMi = binascii.unhexlify('%x' % int(textoMi_bits,2))
print "Mensaje modificado en el bit " + bit1 + ": " + textoMi
print "Mensaje en bits: " + textoMi_bits
print
textoMj_bits = modifica_bit(texto_bits, bit2)
textoMj = binascii.unhexlify('%x' % int(textoMj_bits,2))
print "Mensaje modificado en el bit " + bit2 + ": " + textoMj
print "Mensaje en bits: " + textoMj_bits
print
textoMij_bits = modifica_bit(textoMi_bits, bit2)
textoMij = binascii.unhexlify('%x' % int(textoMij_bits,2))
print "Mensaje modificado en el bit " + bit1 + " y en el bit" + bit2,
print ": " + textoMij
print "Mensaje en bits: " + textoMij_bits
print


# cifra los M, Mi, Mj y Mij con subbytes como la identidad
# imprime los resultados y hage la xor de Mi Mj y Mij entre ellos
print "RESULTADOS CON SUBBYTES COMO LA IDENTIDAD"
cM = subprocess.check_output(["python", "aes_mod_p1.py", texto])
cMi = subprocess.check_output(["python", "aes_mod_p1.py", textoMi])
cMj = subprocess.check_output(["python", "aes_mod_p1.py", textoMj])
cMij = subprocess.check_output(["python", "aes_mod_p1.py", textoMij])

print "(M) Texto cifrado original: " + str(cM),
print "(Mi) Texto cifrado con modificación en el bit " + bit1 + ": " + str(cMi),
print "(Mj) Texto cifrado con modificación en el bit " + bit2 + ": " + str(cMj),
print "(Mij) Texto cifrado con modificación en el bit " + bit1, 
print " y en el bit " + bit2 + ": " + str(cMij)

cMi = eval(cMi)
cMj = eval(cMj)
cMij = eval(cMij)

for x in range(0,len(cMi)):
	cMi[x] = int(cMi[x]) ^ int(cMj[x]) ^ int(cMij[x])
print "Mi xor Mj xor Mij = " + str(cMi)
print "M = " + str(cM)


print "-*-*-*-*-*-*-*-*-*-*-*-*-*"
print


# cifra los M, Mi, Mj y Mij con el aes original
# imprime los resultados y hage la xor de Mi Mj y Mij entre ellos
print "RESULTADOS CON AES ORIGINAL"
cM = subprocess.check_output(["python", "aes_original.py", texto])
cMi = subprocess.check_output(["python", "aes_original.py", textoMi])
cMj = subprocess.check_output(["python", "aes_original.py", textoMj])
cMij = subprocess.check_output(["python", "aes_original.py", textoMij])

print "(M) Texto cifrado original: " + str(cM),
print "(Mi) Texto cifrado con modificación en el bit " + bit1 + ": " + str(cMi),
print "(Mj) Texto cifrado con modificación en el bit " + bit2 + ": " + str(cMj),
print "(Mij) Texto cifrado con modificación en el bit " + bit1, 
print " y en el bit " + bit2 + ": " + str(cMij)

cMi = eval(cMi)
cMj = eval(cMj)
cMij = eval(cMij)

for x in range(0,len(cMi)):
	cMi[x] = int(cMi[x]) ^ int(cMj[x]) ^ int(cMij[x])
print "Mi xor Mj xor Mij = " + str(cMi)
print "M = " + str(cM)
