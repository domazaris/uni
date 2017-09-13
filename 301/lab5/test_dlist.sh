#! /bin/bash
echo "-----------------------"
echo
DIR=$1
echo "Testing directory $DIR"
./dlist $DIR
echo
ls -hla $DIR
echo
echo "-----------------------"
