#!/usr/bin/python

from HTMLParser import HTMLParser

# creem una subclasse i sobreescribim el metodes del han
class MHTMLParser(HTMLParser):
    def handle_starttag(self, tag, attrs):
        print "Hem trobat un tag d'inici:", tag
    def handle_endtag(self, tag):
        print "Hem trobat un tag de final:", tag
    def handle_data(self, data):
        print "Hem trobat dades:", data

# instanciem el parser i li passem HTML
parser = MHTMLParser()
parser.feed('<html><head><title>Test</title></head>'
            '<body><h1>Parse me!</h1></body></html>')
