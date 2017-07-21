#! /bin/bash

echo "ASSEMBLER TEST"
for TESTFILE in test1 test2 test3
do
	echo "Testing $TESTFILE"
	./assembler.py -f testfiles/$TESTFILE.s
	diff <(xxd $TESTFILE.bin) <(xxd testfiles/$TESTFILE.bin) -y
done
