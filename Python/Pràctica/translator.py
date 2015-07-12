#!/usr/bin/python
# -*- coding: utf-8 -*-


def replace_all(text, dic):
    for i, j in dic.iteritems():
        text = text.replace(i, j)
    return text

table = {
	"à": "a", "á": "a", "â": "a",
	"è": "e", "é": "e", "ê": "e",
	"ì": "i", "í": "i", "î": "i",
	"ò": "o", "ó": "o", "ô": "o",
	"ù": "u", "ú": "u", "û": "u",

	"À": "A", "Á": "A", "Â": "A",
	"È": "E", "É": "E", "Ê": "E",
	"Ì": "I", "Í": "I", "Î": "I",
	"Ò": "O", "Ó": "O", "Ô": "O",
	"Ù": "U", "Ú": "U", "Û": "U",
}

inputWord = raw_input("")
print replace_all(inputWord, table).encode("utf8")