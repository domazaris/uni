#!/bin/bash
mkdir -p results

printf "\n---- Original ----\n"
time ./orig  resources/timberlea.pgm results/timberlea-edges_orig.pgm

printf "\n---- Optimized ----\n"
time ./sobel resources/timberlea.pgm results/timberlea-edges.pgm

printf "\n---- Comparison ----\n\n"
md5sum results/timberlea-edges.pgm results/timberlea-edges_orig.pgm
cmp results/timberlea-edges.pgm results/timberlea-edges_orig.pgm
