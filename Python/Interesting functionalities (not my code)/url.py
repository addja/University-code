#!/usr/bin/python

import urllib
import xml.etree.ElementTree as ET
                  
sock = urllib.urlopen("http://www.bcn.cat/tercerlloc/agendaAvui.xml") 
xmlSource = sock.read()                            
sock.close()

#print xmlSource                                     

root = ET.fromstring(xmlSource)

for nom in root.findall('*//nom'):
    if 'visit' in nom.text.lower():
        print nom.text

#for nom in root.findall('*//acte/nom'):
#    if 'visit' in nom.text.lower():
#        print nom.text
        
for acte in root.findall('*//acte'):
    print 'acte'
    fnom=acte.find('nom')
    if 'visit' in fnom.text.lower():
        print fnom.text
#        print acte.find('*//data_proper_acte').text
