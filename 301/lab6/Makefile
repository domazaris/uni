all: fwatcher qcreate qdelete

fwatcher: fwatcher.c
	gcc fwatcher.c -Wall -pedantic -Werror -o fwatcher

qcreate: qcreate.c
	gcc qcreate.c -lrt -Wall -pedantic -Werror -o qcreate

qdelete: qdelete.c
	gcc qdelete.c -lrt -Wall -pedantic -Werror -o qdelete

test: fwatcher qcreate qdelete
	./test.sh

clean:
	rm -rf fwatcher qdelete qcreate
