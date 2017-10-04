all: sobel orig

sobel: src/sobel.c
	gcc src/sobel.c src/imageio.c -mavx -O3 -Wall -pedantic -Werror -lm -o sobel

orig: src/orig.c
	gcc src/orig.c src/imageio.c -O3 -lm -o orig

test: sobel orig
	./test.sh

stat: sobel orig
	perf stat -ddd ./orig resources/timberlea.pgm results/timberlea-edges_orig.pgm
	perf stat -ddd ./sobel resources/timberlea.pgm results/timberlea-edges.pgm

clean:
	rm -rf results sobel orig
