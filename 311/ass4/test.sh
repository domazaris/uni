#!/bin/bash
rm -rf results
mkdir -p results
rm -f /tmp/dom-orig_time.txt /tmp/dom-opt_time.txt

printf "\n---- Original ----\n"
bash -c "time ./bin/original resources/timberlea.pgm results/timberlea-edges_orig.pgm" &> /tmp/dom-orig_time.txt
cat /tmp/dom-orig_time.txt

printf "\n---- Optimized ----\n"
bash -c "time ./bin/sobel resources/timberlea.pgm results/timberlea-edges.pgm" &>  /tmp/dom-opt_time.txt
cat /tmp/dom-opt_time.txt

printf "\n---- Timing ----\n\n"

ORIG_TIME=$(cat /tmp/dom-orig_time.txt | cut -d'm' -f2 | head -n2 | tail -n1 | cut -d's' -f1)
OPT_TIME=$(cat /tmp/dom-opt_time.txt | cut -d'm' -f2 | head -n2 | tail -n1 | cut -d's' -f1)
SPEED_UP=$(echo "scale=2; $ORIG_TIME / $OPT_TIME" | bc -l)
printf "Got $SPEED_UP times speed up\n"

printf "\n---- Comparison ----\n\n"
md5sum results/timberlea-edges.pgm results/timberlea-edges_orig.pgm
cmp    results/timberlea-edges.pgm results/timberlea-edges_orig.pgm
