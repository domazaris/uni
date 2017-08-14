CC = gcc

all: incorrect

incorrect: incorrect.c fork.h
	$(CC) -Wall -pedantic -Werror -lpthread -o $@ $@.c

test: incorrect
	./incorrect

clean:
	rm -rf incorrect *.o
