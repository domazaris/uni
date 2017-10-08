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
Replace most of the computaion with AVX (simd) so that it can run more instructions per cycle

#### Iteration
Remove 2 redundant iterations of the image to leave just one.

#### Memory Allocation
Only allocate one output image and no intermediate

#### Variable sizes
int to size_t/ssize_t
