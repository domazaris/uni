CC = gcc
LIBS = -lpthread

all: incorrect

incorrect: incorrect.c fork.h
	$(CC) -Wall -pedantic -Werror -o $@ $@.c $(LIBS)

test: incorrect
	./incorrect

clean:
	rm -rf incorrect *.o
