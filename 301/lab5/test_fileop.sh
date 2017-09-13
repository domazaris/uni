echo "-----------------------"
echo
touch testfile
./fileop testfile 0 w test1
./fileop testfile 0 r 5
./fileop testfile 1008 w test2
./fileop testfile 1008 r 5
ls -hla testfile
rm testfile
echo
echo "-----------------------"
