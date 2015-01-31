CC=g++
CFLAGS=-lGLU -lGL -lglut
OBJ =model.o prac4.o
BIN=prac4

all: compile run

compile:
	$(CC) -c model.cpp
	$(CC) -c prac4.cpp
	$(CC) -o $(BIN) $(OBJ) $(CFLAGS)

run:
	./$(BIN)

clean:
	rm -rf $(BIN) $(OBJ)
