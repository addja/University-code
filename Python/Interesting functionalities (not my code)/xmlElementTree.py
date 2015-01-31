#!/usr/bin/python

import xml.etree.ElementTree as ET

#root = ET.fromstring(moviedata)
tree = ET.parse('movies.xml')
root = tree.getroot()

def showAll(root,blanks):
    print blanks, root.tag, root.attrib, root.text
    for child in root:
        showAll (child,blanks+"  ")

#print root.tag
#print root.attrib

showAll(root,"")

# tots els 'year' que son fills de 'movie' fills del nivell principal

for elem in root.findall('movie'):
    for att in elem.attrib:
        print elem.attrib[att]


# elements a nivell superior
#root.findall(".")

# nodes 'year' que son fills de nodes amb title='Transformers'
for tra in root.findall("*[@title='Transformers']/year"):
    print tra.text

