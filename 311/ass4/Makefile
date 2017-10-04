all: sobel

sobel:
	gcc src/sobel.c src/imageio.c -O3 -lm -o sobel

test: sobel
	mkdir -p results
	time ./sobel resources/timberlea.pgm results/timberlea-edges.pgm

clean:
	rm -rf results sobel
