CC=gcc
FLAGS=-pedantic

all: Task1 Task2

Scheduler: Build LinkedList
	$(CC) $(FLAGS) -c -I include -g -o lib/$@.o src/$@.c

LinkedList: Build src/LinkedList.c include/LinkedList.h
	$(CC) $(FLAGS) -c -I include -g -o lib/$@.o src/$@.c

Task1: Build LinkedList
	$(CC) $(FLAGS) -I include -g -o bin/$@ src/$@.c lib/LinkedList.o

Task2: Build LinkedList Scheduler
	$(CC) $(FLAGS) -I include -g -o bin/$@ src/$@.c lib/LinkedList.o lib/Scheduler.o

Build:
	mkdir -p lib bin

test: Task1 Task2
	chronic ./bin/Task1 ./data/task1.csv
	./bin/Task2 ./data/task2.csv

clean:
	rm -rf lib bin
