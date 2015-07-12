PROG= airScheduling.cpp

CC= g++
CFLAGS= -Wall
EXTRA= graph.cpp debug.cpp edmondsKarp.cpp
OBJ= graph.o debug.o edmondsKarp.o
BIN= airScheduling

all: compile

compile:
	$(CC) -c $(EXTRA) $(CFLAGS)
	$(CC) -o $(BIN) $(OBJ) $(CFLAGS) $(PROG)

run: compile
	./$(BIN)

clean:
	rm -rf $(BIN) $(OBJ)
