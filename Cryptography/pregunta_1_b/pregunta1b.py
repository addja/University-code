#!/usr/bin/python
# -*- coding: utf-8 -*-

# Pregunta 1 apartado b
# Práctica criptografía clave secreta

# aes_original.py -> código del aes correcto
# aes_mod_p1b.py -> código del aes
# donde shiftRows es la identidad


# librerias

import sys
import subprocess


# script

# cifra los M con shiftrows como la identidad
print "RESULTADOS CON SHIFTROWS COMO LA IDENTIDAD"

a = subprocess.check_output(["python", "aes_mod_p1b.py", "a"])
b = subprocess.check_output(["python", "aes_mod_p1b.py", "b"])
c = subprocess.check_output(["python", "aes_mod_p1b.py", "c"])
print "Resultado de cifrar a: " + a,
print "Resultado de cifrar b: " + b,
print "Resultado de cifrar c: " + c

print "-*-*-*-*-*-*-*-*-*-*-*-*-*"
print

# cifra los M con el aes original
print "RESULTADOS CON AES ORIGINAL"

a = subprocess.check_output(["python", "aes_original.py", "a"])
b = subprocess.check_output(["python", "aes_original.py", "b"])
c = subprocess.check_output(["python", "aes_original.py", "c"])
print "Resultado de cifrar a: " + a,
print "Resultado de cifrar b: " + b,
print "Resultado de cifrar c: " + c,

