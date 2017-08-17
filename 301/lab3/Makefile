CC = gcc
LIBS = -lpthread

all: deadlock

deadlock: deadlock fork.h
	$(CC) -Wall -pedantic -Werror -o $@ $@.c $(LIBS)

test: deadlock
	./deadlock

clean:
	rm -rf deadlock *.o
