all: fileop dlist

fileop: fileop.c
	gcc -g fileop.c -Wall -pedantic -Werror -o fileop

dlist: dlist.c
	gcc -g dlist.c -Wall -pedantic -Werror -o dlist

test: test_fileop test_dlist

test_fileop: fileop
	./test_fileop.sh

test_dlist: dlist
	ln -s /dev/zero ./devzero
	./test_dlist.sh .
	rm ./devzero

clean:
	rm -f fileop dlist
