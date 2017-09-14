all: lab4

lab4: lab4.c
	gcc lab4.c -o lab4

test: lab4
	./lab4 data/ref1.in results/rep1.out
	./lab4 data/ref2.in results/rep2.out
	cat results/rep1.out
	cat results/rep2.out

clean:
	rm -rf lab4