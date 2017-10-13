all: fwatcher qcreate qdelete qread

fwatcher: fwatcher.c
	gcc fwatcher.c -lrt -Wall -pedantic -Werror -o fwatcher

qcreate: qcreate.c
	gcc qcreate.c -lrt -Wall -pedantic -Werror -o qcreate

qdelete: qdelete.c
	gcc qdelete.c -lrt -Wall -pedantic -Werror -o qdelete

qread: qread.c
	gcc qread.c -lrt -Wall -pedantic -Werror -o qread

test: fwatcher qcreate qdelete qread
	./test.sh

clean:
	rm -rf fwatcher qdelete qcreate
