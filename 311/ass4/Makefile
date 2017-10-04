all: sobel orig

sobel:
	gcc src/sobel.c src/imageio.c -O3 -lm -o sobel

orig:
	gcc src/orig.c src/imageio.c -O3 -lm -o orig

test: sobel orig
	./test.sh

clean:
	rm -rf results sobel orig
