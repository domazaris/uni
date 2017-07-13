all: cmd reverse

cmd: cmd.c
	gcc cmd.c -Wall -pedantic -o cmd

reverse: reverse.c
	gcc reverse.c -Wall -pedantic -fsanitize=address -o reverse

clean:
	rm -rf cmd
	rm -rf reverse
