#! /usr/bin/python
import csv
 
#ifile  = open('test.csv', "r")
ifile  = open('restaurants.csv', "r")
reader = csv.reader(ifile, delimiter='\t')
 
rownum = 0
for row in reader:
    # Save header row.
    if rownum == 0:
        header = row
    else:
        colnum = 0
        for col in row:
            print '%s: %s' % (header[colnum], col)
            colnum += 1
             
    rownum += 1
 
ifile.close()
