all: sobel

sobel:
	gcc src/sobel.c src/imageio.c -O3 -lm -o sobel

orig:
	gcc src/orig.c src/imageio.c -O3 -lm -o orig

test: sobel orig
	mkdir -p results
	time ./sobel resources/timberlea.pgm results/timberlea-edges.pgm
	time ./orig  resources/timberlea.pgm results/timberlea-edges_orig.pgm
	cmp results/timberlea-edges.pgm results/timberlea-edges_orig.pgm

clean:
	rm -rf results sobel orig
