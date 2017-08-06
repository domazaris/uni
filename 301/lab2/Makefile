CC=gcc

all: Task1 Scheduler

Scheduler: Build LinkedList
	$(CC) -c -I include -g -o lib/$@.o src/$@.c

LinkedList: Build src/LinkedList.c include/LinkedList.h
	$(CC) -c -I include -g -o lib/$@.o src/$@.c

Task1: Build LinkedList
	$(CC) -I include -g -o bin/$@ src/$@.c lib/LinkedList.o

Build:
	mkdir -p lib bin

clean:
	rm -rf lib
	rm -rf bin
