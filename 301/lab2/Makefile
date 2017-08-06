CC=gcc

all: Task1 Task2

Scheduler: Build LinkedList
	$(CC) -c -I include -g -o lib/$@.o src/$@.c

LinkedList: Build src/LinkedList.c include/LinkedList.h
	$(CC) -c -I include -g -o lib/$@.o src/$@.c

Task1: Build LinkedList
	$(CC) -I include -g -o bin/$@ src/$@.c lib/LinkedList.o

Task2: Build LinkedList Scheduler
	$(CC) -I include -g -o bin/$@ src/$@.c lib/LinkedList.o lib/Scheduler.o

Build:
	mkdir -p lib bin

test: Task1 Task2
	chronic ./bin/Task1 ./data/task1.csv
	chronic ./bin/Task2 ./data/task1.csv

clean:
	rm -rf lib bin
