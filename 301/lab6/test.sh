#! /bin/bash
set -x
echo "Hello World" > /tmp/file1.txt
echo "Hello Mar" > /tmp/file2.txt
./qcreate /myqueue
./qread /myqueue

./fwatcher /myqueue /tmp/file1.txt &
./fwatcher /myqueue /tmp/file2.txt &

touch /tmp/file1.txt
echo "Hi UFO!" >> /tmp/file2.txt
cat /tmp/file2.txt

./qread /myqueue

./qread /myqueue
cp /tmp/file1.txt /tmp/file3.txt
./qread /myqueue

ps | grep fwatcher

killall fwatcher

./qdelete /myqueue
