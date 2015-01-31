#! /usr/bin/python
# -*- coding: utf-8 -*-

#crideu-lo amb 
#./reEx1.py Llista.txt

import sys
import re

f = open(sys.argv[1], 'r') # obre l'arxiu

lines = f.readlines()  

f.close()

for l in lines:   # itera sobre les linies de l'arxiu
    m=re.search('ez[ ,]',l.lower()) #cerca si algun cognom en minuscules acaba en ez
    if m:
        print m.string,      # mostra els noms que li hem passat (en minuscules) que satisfan la condició
        print l[m.end():]    # mostra l'string original des del punt que ha acabat el matching fins el final 

print 
print 

for l in lines:   # itera sobre les linies de l'arxiu
    if re.search('ez[ ,]',l.lower()): #cerca si algun cognom en minuscules acaba en ez
        print l,      # mostra els noms que satisfan la condició

print 
print 

#posa el nom davant dels cognoms. Els cognoms acaben per ',' i el nom per 'Grup'
for l in lines:   # itera sobre les linies de l'arxiu
    s=re.sub(r'([^,]+), (.*)(?=Grup)',r'\2\1; ',l) #substitueix expressio
    print s,      # mostra el resultat de la sustitucio


