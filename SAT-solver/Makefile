PROG= mySAT.cpp

CC=g++
CFLAGS= -O3
OBJ=
BIN=mySAT

all: compile

compile:
	$(CC) -o $(BIN) $(OBJ) $(CFLAGS) $(PROG)

run: compile
	./run.sh > output.txt

clean:
	rm -rf $(BIN) $(OBJ).o
