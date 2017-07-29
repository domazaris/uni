all: LinkedList Task1

LinkedList: Build src/LinkedList.c include/LinkedList.h
	gcc -c -I include -g -o lib/$@.o src/$@.c

Task1: Build LinkedList
	gcc -I include -g -o bin/$@ src/$@.c lib/LinkedList.o

Build:
	mkdir -p lib bin

clean:
	rm -rf lib
	rm -rf bin
