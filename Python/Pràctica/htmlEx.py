#!/usr/bin/python

from collections import Counter, defaultdict

hello = ["hello","hi","hello","hello","pene"]
counter= Counter(hello)
bye = defaultdict(int)
print"<table>"
for word in counter.keys():
    print "<tr>"
    print "<td>" + str(word) + ":" + str(counter[word]) + "</td>"
    print "</tr>"
    bye[word] = counter[word]
print"</table>"

print xmlCurated[0].find("*//coordenades").find("googleMaps").attrib["lat"]
print xmlCurated[0].find("*//coordenades").find("googleMaps").attrib["lon"]