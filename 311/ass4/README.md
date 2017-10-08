## COMP311-17B Assignment 4
### Dominic Azaris - 1297845 - djmha1

---

## Usage:
#### Compile
```
make
```

#### Run comparison with original vs optimised
```
make test
```

## Optimisations

#### AVX
Replace most of the computaion with AVX (simd) so that it can run more instructions per cycle. I managed to change 
both the filters and energy response to all use avx instructions.

#### Iteration
The original filter iterates over the image three times. I have reduced it to iterate only once.

#### Memory Allocation
Only allocate one output image and no intermediate images. This saves on both memory consumption and allows
more relevant data to be stored in the CPU caches.

#### Variable sizes
Changed any int to size_t/ssize_t so that the cpu does not have to fill in the empty space in the reg.
