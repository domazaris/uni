#!/bin/bash
rm -f /tmp/djmha1-orig_time.txt /tmp/djmha1-opt_time.txt /tmp/djmha1-timberlea.pgm
cp resources/timberlea.pgm /tmp/djmha1-timberlea.pgm

printf "\n---- Original ----\n"
bash -c "time ./bin/original /tmp/djmha1-timberlea.pgm /tmp/djmha1-timberlea-edges_orig.pgm" &> /tmp/djmha1-orig_time.txt
cat /tmp/djmha1-orig_time.txt

printf "\n---- Optimized ----\n"
bash -c "time ./bin/sobel /tmp/djmha1-timberlea.pgm /tmp/djmha1-timberlea-edges.pgm" &>  /tmp/djmha1-opt_time.txt
cat /tmp/djmha1-opt_time.txt

printf "\n---- Timing ----\n\n"
ORIG_TIME=$(cat /tmp/djmha1-orig_time.txt | cut -d'm' -f2 | head -n2 | tail -n1 | cut -d's' -f1)
OPT_TIME=$(cat /tmp/djmha1-opt_time.txt | cut -d'm' -f2 | head -n2 | tail -n1 | cut -d's' -f1)
SPEED_UP=$(echo "scale=2; $ORIG_TIME / $OPT_TIME" | bc -l)
printf "Got $SPEED_UP times speed up\n"

printf "\n---- Comparison ----\n\n"
md5sum /tmp/djmha1-timberlea-edges.pgm /tmp/djmha1-timberlea-edges_orig.pgm
printf "\n"
cmp    /tmp/djmha1-timberlea-edges.pgm /tmp/djmha1-timberlea-edges_orig.pgm
