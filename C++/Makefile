PROG= Fibonacci.cpp

CC=g++
CFLAGS=-Wall
OBJ=
BIN=prog

all: compile

compile:
	$(CC) -o $(BIN) $(OBJ) $(CFLAGS) $(PROG)

run: compile
	./$(BIN)

clean:
	rm -rf $(BIN) $(OBJ).o
