all: fileop dlist

fileop: fileop.c
	gcc fileop.c -Wall -pedantic -Werror -o fileop

dlist: dlist.c
	gcc dlist.c -Wall -pedantic -Werror -o dlist

test: fileop
	./test_fileop.sh
	./test_dlist.sh .

clean:
	rm -f fileop dlist
