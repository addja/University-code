#!/usr/bin/python
# -*- coding: utf-8 -*-

# Pregunta 1 apartado c
# Práctica criptografía clave secreta

# aes_original.py -> código del aes correcto
# aes_mod_p1c.py -> código del aes
# donde mixColumns es la identidad


# librerias

import sys
import subprocess


# script

# cifra los M con mixColumns como la identidad
print "RESULTADOS CON MIXCOLUMNS COMO LA IDENTIDAD"

a = subprocess.check_output(["python", "aes_mod_p1c.py", "a"])
b = subprocess.check_output(["python", "aes_mod_p1c.py", "b"])
c = subprocess.check_output(["python", "aes_mod_p1c.py", "c"])
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

