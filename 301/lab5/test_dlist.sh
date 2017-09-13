#! /bin/bash
echo "-----------------------"
echo
DIR=$1
echo "Testing directory $DIR"
./dlist $DIR
ls -hla $DIR
echo
echo "-----------------------"
