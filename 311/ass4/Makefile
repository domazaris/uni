all: sobel orig

sobel: src/optimized/sobel.c src/optimized/imageio.c src/optimized/imageio.h
	mkdir -p bin; gcc src/optimized/sobel.c src/optimized/imageio.c -mavx -O3 -Wall -pedantic -Werror -g -lm -o bin/sobel

orig: src/original/sobel.c src/original/imageio.c src/original/imageio.h
	mkdir -p bin; gcc src/original/sobel.c src/original/imageio.c -O3 -lm -o bin/original

test: sobel orig
	./test.sh

stat: sobel orig
	rm -rf results; mkdir -p results
	perf stat -ddd ./bin/original resources/timberlea.pgm results/timberlea-edges_orig.pgm
	perf stat -ddd ./bin/sobel resources/timberlea.pgm results/timberlea-edges.pgm
	cmp results/timberlea-edges.pgm results/timberlea-edges_orig.pgm

clean:
	rm -rf results bin
