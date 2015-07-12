#!/usr/bin/python

import sys
import subprocess

for i in range(0,256):
	try:
		output = subprocess.check_output(["python", "aes_modp3.py", str(i)])
		print "yes :^) " + str(i)
	except Exception, e:
		print "no :^( " + str(i)