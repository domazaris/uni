CC = gcc
LIBS = -lpthread

all: deadlock correct

deadlock: deadlock.c fork.h
	$(CC) -Wall -pedantic -Werror -o $@ $@.c $(LIBS)

correct: correct.c fork.h
	$(CC) -Wall -pedantic -Werror -o $@ $@.c $(LIBS)

test: deadlock correct
	./deadlock
	./correct

clean:
	rm -rf deadlock correct
