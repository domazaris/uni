#! /bin/bash

echo "ASSEMBLER TEST"
for TESTFILE in test1 test2 test3
do
	echo "Testing $TESTFILE"
	./assembler.py -i testfiles/$TESTFILE.s -o /tmp/out.bin
	diff <(xxd /tmp/out.bin) <(xxd testfiles/$TESTFILE.bin)
done
