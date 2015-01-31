#!/usr/bin/python

from HTMLParser import HTMLParser
import urllib

# creem una subclasse i sobreescribim el metodes del han
class MHTMLParser(HTMLParser):
    def handle_starttag(self, tag, attrs):
        print "Hem trobat un tag d'inici:", tag
    def handle_endtag(self, tag):
        print "Hem trobat un tag de final:", tag
    def handle_data(self, data):
        print "Hem trobat dades:", data

# instanciem el parser i li passem HTML de la url
parser = MHTMLParser()
sock = urllib.urlopen("http://wservice.viabicing.cat/getstations.php?v=1") 
htmlSource = sock.read()                            
sock.close()

parser.feed(htmlSource)


