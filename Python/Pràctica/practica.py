#!/usr/bin/python
# -*- coding: utf-8 -*-

import sys
import unicodedata
import urllib
import xml.etree.ElementTree as ET
import csv
import re
from math import sin, cos, sqrt, atan2, radians, trunc

def parse(text):
	"removes special characters from a given text"
	
	return unicodedata.normalize('NFKD', unicode(text)).encode('ASCII', 'ignore').encode('utf-8')


def replace_all(text):
	"Replaces in a given word all special character for the standand ones"
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
	for i, j in table.iteritems():
		text = text.replace(i, j)
	return text


def coordinates_dist(x,y,nx,ny):
	"Calculates the distance between two points on earth given their coordinates"

	x = radians(x)
	y = radians(y)
	nx = radians(nx)
	ny = radians(ny)

	a = ny - y
	b = nx - x
	c = (sin(b/2))**2 + cos(x) * cos(nx) * (sin(a/2))**2
	d = 2 * atan2(sqrt(c), sqrt(1-c))
	
	return 6373.0 * d


def myQuickSort(plist):
	"Sorts the given list using the quicksort algotithm \
	implemented with comprehension lists"
	
	length = len(plist)
	if length == 0: return plist
	
	pivotpos = length/2
	pivot = plist[pivotpos]
	newList = plist[:pivotpos] + plist[pivotpos+1:]
	smaller = [x for x in newList if x[0] <= pivot[0]]
	bigger = [x for x in newList if x[0] > pivot[0]]

	return myQuickSort(smaller) + [pivot] + myQuickSort(bigger)


def inputReader():
	"Reads the input and initialices variables"

	if len(sys.argv) == 1:
		print "You haven't provided arguments"
		sys.exit()

	else:
		myData = { "nom": [], "barri": [], "lloc": []}
		tmpData = " ".join(sys.argv[1:])
		tmpData = re.sub("[ ]*,[ ]*" , ",", tmpData)
		tmpData = re.sub("[ ]*:[ ]*" , ":", tmpData)
		tmpData = tmpData.split(",")

		for x in tmpData:
			fst = x.split(":")[0]
			snd = x.split(":")[1]
			myData[fst].append(snd)

		return myData


def getDataUrl(url):
	"gets the data from a given url"

	sock = urllib.urlopen(url) 
	xmlSource = sock.read()              
	sock.close()
	return xmlSource


def getDataCsv(csvInput):
	"gets the date form a given csv"
	ifile  = open(csvInput, "r")
	reader = csv.reader(ifile, delimiter=';')
	ifile.close()

	return reader


def filterXmlEvents(xmlSource, dataList):
	"Given a xml variable, returns the xml resources that match the given data"

	root = ET.fromstring(xmlSource)

	actes = root.findall("*//acte")
	result = []
	for acte in actes:

		if dataList["barri"] != []:
			barri = acte.find("*//adreca_simple").find("barri")
			for data in dataList["barri"]:
				if re.search(replace_all(data), parse(barri.text), re.IGNORECASE | re.UNICODE):					
					break
			else:
				continue

		if dataList["lloc"] != []:
			lloc = acte.find("lloc_simple").find("nom")
			for data in dataList["lloc"]:
				if re.search(replace_all(data), parse(lloc.text), re.IGNORECASE | re.UNICODE):
					break
			else:
				continue

		if dataList["nom"] != []:
			nom = acte.find("nom")
			for data in dataList["nom"]:
				if re.search(replace_all(data), parse(nom.text), re.IGNORECASE | re.UNICODE):
					break
			else:
				continue

		result.append(acte)

	return result
				

def filterXmlWeather(xmlSource, date):
	"Searches in a xml source if it will rain or snow in Barcelona \
	and returns yes, no or noInfo"

	raintmp = list(xrange(5,11)) + [13] + list(xrange(23,33))

	root = ET.fromstring(xmlSource)

	# get id of Barcelona
	comarques = root.findall("comarca")
	for comarca in comarques:
		if re.search("Barcelona", comarca.attrib["nomCAPITALCO"], re.IGNORECASE | re.UNICODE):
			idBcn = comarca.attrib["id"]
	
	# get ids of rain or snow related weather
	weathers = root.findall("simbol")
	rain = []
	for weather in weathers:
		for word in ["new","pluja","plugim","tempesata","ruixat","xàfeg"]:
			if re.search(replace_all(word), parse(weather.attrib["nomsimbol"]), re.IGNORECASE | re.UNICODE):
				rain.append(weather.attrib["id"] + ".png")
				break


	# check weather of day in Barcelona
	prediccions = root.findall("prediccio")
	for prediccio in prediccions:
		if prediccio.attrib["idcomarca"] == idBcn:
			break

	for day in prediccio.iter("variable"):
		if day.attrib["data"] == date[0]:
			if float(date[1]) > 14.00:
				if day.attrib["simboltarda"] in rain:
					return "yes"
				else:
					return "no"
			else:
				if day.attrib["simbolmati"] in rain:
					return "yes"
				else:
					return "no"

	return "noInfo"


def bicis(event,xml):
	"Returns public bikes near the events from the xml"

	root = ET.fromstring(xml)
	stations = root.findall("station")

	result = []
	x = float(event.find("*//coordenades").find("googleMaps").attrib["lat"])
	y = float(event.find("*//coordenades").find("googleMaps").attrib["lon"])

	emptySlots = []
	hasBikes = []
	for station in stations:
		nx = float(station.find("lat").text)
		ny = float(station.find("long").text)

		tmp = coordinates_dist(x,y,nx,ny)
		if 0.5 >= tmp:
			slots = int(station.find("slots").text)
			bikes = int(station.find("bikes").text)
			if slots > 0:
				emptySlots.append((tmp,station))
			if bikes > 0:
				hasBikes.append((tmp,station))

	if emptySlots == []:
		return []
	emptySlots = myQuickSort(emptySlots)
	hasBikes = myQuickSort(hasBikes)
	result.append((emptySlots[:5],hasBikes[:5]))

	return result


def transports(event,csvBus,csvRails,hour):
	"Returns public transports near the events form the xml"
	
	x = float(event.find("*//coordenades").find("googleMaps").attrib["lat"])
	y = float(event.find("*//coordenades").find("googleMaps").attrib["lon"])

	# Get bus stations
	busRef = []
	busComp = []
	ifile  = open(csvBus, "r")
	reader = csv.reader(ifile, delimiter=';')
	rownum = 0
	for row in reader:
		if rownum != 0:
			# lat = col 4 / lon = col 5
			nx = float(row[4])
			ny = float(row[5])
			tmp = coordinates_dist(x,y,nx,ny)
			if 1.0 >= tmp:
				# equipment = col 6
				info = row[6].replace("--","")
				info = info.replace(" ","")
				info = info.split("-")
				if hour > 21.0 or hour < 6.0:
					if info[0] == "NITBUS":
						for bus in info[1:]:
							if bus in busRef:
								i = busRef.index(bus)
								tmpbus = busComp[i]
								if (tmpbus[0] > tmp):
									tmpbus = (tmp,bus)
									busComp[i] = tmpbus
							else:
								busRef.append(bus)
								busComp.append((tmp,bus))
				else:
					if info[0] == "BUS" or info[0] == "AEROBUS":
						for bus in info[1:]:
							if bus in busRef:
								i = busRef.index(bus)
								tmpbus = busComp[i]
								if (tmpbus[0] > tmp):
									tmpbus = (tmp,bus)
									busComp[i] = tmpbus
							else:
								busRef.append(bus)
								busComp.append((tmp,bus))
					elif info[0] != "NITBUS":	# Special cases like "Estació de Barcelona Nord"
						busRef.append(info[0])		
						busComp.append((tmp,info[0]))
		else : rownum += 1		
	ifile.close()
	busComp = myQuickSort(busComp)

	# Get rail stations
	railRef = []
	railComp = []
	ifile  = open(csvRails, "r")
	reader = csv.reader(ifile, delimiter=';')
	rownum = 0
	for row in reader:
		if rownum != 0:
			# lat = col 4 / lon = col 5
			nx = float(row[4])
			ny = float(row[5])
			tmp = coordinates_dist(x,y,nx,ny)
			if 1.0 >= tmp:
				# equipment = col 6
				info = row[6].split("-")[0]
				if info in railRef:
					i = railRef.index(info)
					tmprail = railComp[i]
					if (tmprail[0] > tmp):
						tmprail = (tmp,row[6])
						railComp[i] = tmprail
				else:
					railRef.append(info)
					railComp.append((tmp,row[6]))
		else : rownum += 1		
	ifile.close()
	railComp = myQuickSort(railComp)

	return (busComp,railComp)

def printRes(events, bikes, trans, myData):
	"Prints the results on a html table"

	f = open('results.html', 'w')

	f.write("<!DOCTYPE html>\n<html lang='cat'>\n<head><meta charset='UTF-8'>  \
    	<title>results.html</title>\n</head> \
		<body>\n<h1 align='center'>Resultats de la consulta</h1>\n<hr><hr>\n \
		<h3 align='center'>Paràmetres d'entrada</h3>\n<table align='center'>")

	if myData["nom"] != []:
		f.write("<tr>\n<th>nom</th><td>"+myData["nom"][0])
		for x in myData["nom"][1:]:
			f.write(", "+x)
		f.write("</tr>")

	if myData["lloc"] != []:
		f.write("<tr>\n<th>lloc</th><td>"+myData["lloc"][0])
		for x in myData["lloc"][1:]:
			f.write(", "+x)
		f.write("</tr>")

	if myData["barri"] != []:
		f.write("<tr>\n<th>barri</th><td>"+myData["barri"][0])
		for x in myData["barri"][1:]:
			f.write(", "+x)
		f.write("</tr>")

	f.write("</table>\n<br><hr width='50%'><br>\n")

	it = 0
	for event in events:

		f.write("<h4 align='center'>Event</h4>")
		f.write("<table align='center' width='50%' border='1px'>")

		# name
		f.write("<tr>")
		f.write("<th>Nom</th>")
		name = event.find("nom").text.encode("utf-8")
		f.write("<td>"+name+"</td>")
		f.write("</tr>")

		# adress
		f.write("<tr>")
		f.write("<th>Adreça</th>")
		adreca = event.find("*//adreca_simple").find("carrer").text.encode("utf-8")
		barri = event.find("*//adreca_simple").find("barri").text.encode("utf-8")
		f.write("<td>"+adreca+", "+barri+"</td>")
		f.write("</tr>")

		# data
		f.write("<tr>")
		f.write("<th>Data i hora d'inici</th>")
		data = event.find("*//data_proper_acte").text.encode("utf-8")
		f.write("<td>"+data+"</td>")
		f.write("</tr>\n</table>")

		# bikes
		if bikes[it] != []:
			bike = bikes[it]
			bikeList = bike[0]
			f.write("<h4 align='center'>Parades de bicing properes per aparcar</h4>")	
			f.write("<table align='center' width='50%' border='1px'>")
			f.write("<tr><th>Adreça</th><th>Slots lliures</th><th>Distància (m)</th></tr>")
			for tmpbike in bikeList[0]:

				f.write("<tr>")
				adress = tmpbike[1].find("street").text.encode("utf-8")
				if tmpbike[1].find("streetNumber").text:
					adress += " " + tmpbike[1].find("streetNumber").text.encode("utf-8")
				f.write("<td>"+adress+"</td>")
				slots = tmpbike[1].find("slots").text
				f.write("<td>"+slots+"</td>")
				f.write("<td>"+str(trunc(tmpbike[0]*1000))+"</td>")
				f.write("</tr>")
			f.write("</table>")

			bikeList = bike[0]
			f.write("<h4 align='center'>Parades de bicing properes amb bicis disponibles</h4>")	
			f.write("<table align='center' width='50%' border='1px'>")
			f.write("<tr><th>Adreça</th><th>Bicis disponibles</th><th>Distància (m)</th></tr>")
			for tmpbike in bikeList[1]:

				f.write("<tr>")
				adress = tmpbike[1].find("street").text.encode("utf-8")
				if tmpbike[1].find("streetNumber").text:
					adress += " " + tmpbike[1].find("streetNumber").text.encode("utf-8")
				f.write("<td>"+adress+"</td>")
				slots = tmpbike[1].find("bikes").text
				f.write("<td>"+slots+"</td>")
				f.write("<td>"+str(trunc(tmpbike[0]*1000))+"</td>")
				f.write("</tr>")
			f.write("</table>")

		# rail transports
		if trans[it][1] != []:
			f.write("<h4 align='center'>Transports sobre rails propers</h4>")
			f.write("<table align='center' width='50%' border='1px'>")
			f.write("<tr><th>Estació</th><th>Distància (m)</th></tr>")
			for elem in trans[it][1]:
				f.write("<tr>")
				word = unicode(elem[1], encoding='cp1252',errors='replace').encode('utf-8')
				f.write("<td>"+word+"</td>")
				f.write("<td>"+str(trunc(elem[0]*1000))+"</td>")
				f.write("</tr>")
			f.write("</table>")

		# buses
		if trans[it][0] != []:
			f.write("<h4 align='center'>Parades de bus properes</h4>")
			f.write("<table align='center' width='50%' border='1px'>")
			f.write("<tr><th>Línea autobús</th><th>Distància (m)</th></tr>")
			for elem in trans[it][0]:
				f.write("<tr>")
				word = unicode(elem[1], encoding='cp1252',errors='replace').encode('utf-8')
				f.write("<td>"+word+"</td>")
				f.write("<td>"+str(trunc(elem[0]*1000))+"</td>")
				f.write("</tr>")
			f.write("</table>")
		

		f.write("</table>\n<br><hr width='50%'><br>\n")
		it += 1


	f.write("</body>\n</html>")	

	f.close()


def main():
	print "Script runing..."

	# Get data
	print "	- getting information from sources"

	url = "http://w10.bcn.es/APPS/asiasiacache/peticioXmlAsia?id=199"
	xmlEvents = getDataUrl(url)
	url ="http://static-m.meteo.cat/content/opendata/ctermini_comarcal.xml"
	xmlWeather = getDataUrl(url)
	url = "http://wservice.viabicing.cat/getstations.php?v=1"
	xmlBicis = getDataUrl(url)
	myData = inputReader()
	# Those from below is just to see if the data is available
	csvRails = "TRANSPORTS.csv"
	getDataCsv(csvRails)
	csvBus = "ESTACIONS_BUS.csv"
	getDataCsv(csvBus)

	# Work with data
	print "	- working with data"

	xmlCurated = filterXmlEvents(xmlEvents,myData)

	biciList = []
	transList = []
	for event in xmlCurated:
		date = event.find("*//data_proper_acte")
		date =  date.text.split(" ")
		date[0] = date[0].replace("/","-")
		rain = filterXmlWeather(xmlWeather, date)
		if rain == "no":
			biciList.append(bicis(event,xmlBicis))
			if [] == biciList[len(biciList)-1]:
				transList.append(transports(event,csvBus,csvRails,float(date[1])))
			else:
				transList.append(([],[]))
		elif rain == "yes":
			transList.append(transports(event,csvBus,csvRails,float(date[1])))
			biciList.append([])
		else:
			biciList.append(bicis(event,xmlBicis))
			transList.append(transports(event,csvBus,csvRails,float(date[1])))
	
	# Printing the date
	print "	- creating html file"
	printRes(xmlCurated,biciList,transList, myData)

	print "Done! Results print in results.html"


# Call the main method
if __name__ == "__main__":
    main()