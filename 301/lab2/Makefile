all: LinkedList

LinkedList: src/LinkedList.c include/LinkedList.h
	mkdir -p lib && gcc -c -I include -o lib/$@.o src/$@.c

clean:
	rm -rf lib
	rm -rf bin
