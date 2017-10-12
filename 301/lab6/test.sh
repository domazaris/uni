#! /bin/bash

echo "Hello World" > /tmp/file1.txt
echo "Hello Mar" > /tmp/file2.txt
/workspace/qcreate /tmp/queue1
/workspace/qdelete /tmp/queue1