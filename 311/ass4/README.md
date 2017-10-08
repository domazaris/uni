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

### Explained

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


### Statistics
I used the linux tool `perf stat` to record events about the programs. The benchmarks were all run on a
machine with the following cpu information:

```
dom@dev-dom ~/w/u/3/ass4> lscpu
Architecture:          x86_64
CPU op-mode(s):        32-bit, 64-bit
Byte Order:            Little Endian
CPU(s):                4
On-line CPU(s) list:   0-3
Thread(s) per core:    1
Core(s) per socket:    4
Socket(s):             1
NUMA node(s):          1
Vendor ID:             GenuineIntel
CPU family:            6
Model:                 158
Model name:            Intel(R) Core(TM) i5-7500 CPU @ 3.40GHz
Stepping:              9
CPU MHz:               899.804
CPU max MHz:           3800.0000
CPU min MHz:           800.0000
BogoMIPS:              6816.00
Virtualization:        VT-x
L1d cache:             32K
L1i cache:             32K
L2 cache:              256K
L3 cache:              6144K
NUMA node0 CPU(s):     0-3
Flags:                 fpu vme de pse tsc msr pae mce cx8 apic sep mtrr pge mca cmov pat pse36 clflush dts acpi mmx fxsr sse sse2 ss ht tm pbe syscall nx pdpe1gb rdtscp lm constant_tsc art arch_perfmon pebs bts rep_good nopl xtopology nonstop_tsc aperfmperf tsc_known_freq pni pclmulqdq dtes64 monitor ds_cpl vmx smx est tm2 ssse3 sdbg fma cx16 xtpr pdcm pcid sse4_1 sse4_2 x2apic movbe popcnt tsc_deadline_timer aes xsave avx f16c rdrand lahf_lm abm 3dnowprefetch intel_pt tpr_shadow vnmi flexpriority ept vpid fsgsbase tsc_adjust bmi1 hle avx2 smep bmi2 erms invpcid rtm mpx rdseed adx smap clflushopt xsaveopt xsavec xgetbv1 xsaves dtherm ida arat pln pts hwp hwp_notify hwp_act_window hwp_epp

```

#### Original
```
perf stat -ddd ./bin/original resources/timberlea.pgm results/timberlea-edges_orig.pgm

 Performance counter stats for './bin/original resources/timberlea.pgm results/timberlea-edges_orig.pgm':

       2148.197038      task-clock (msec)         #    0.992 CPUs utilized          
               304      context-switches          #    0.142 K/sec                  
                 0      cpu-migrations            #    0.000 K/sec                  
             2,248      page-faults               #    0.001 M/sec                  
     7,959,869,266      cycles                    #    3.705 GHz                      (61.69%)
    12,393,886,895      instructions              #    1.56  insn per cycle           (69.36%)
     1,828,870,674      branches                  #  851.351 M/sec                    (69.41%)
           232,854      branch-misses             #    0.01% of all branches          (69.69%)
     2,625,368,245      L1-dcache-loads           # 1222.126 M/sec                    (69.71%)
       639,971,379      L1-dcache-load-misses     #   24.38% of all L1-dcache hits    (69.72%)
       254,279,731      LLC-loads                 #  118.369 M/sec                    (69.35%)
         9,957,983      LLC-load-misses           #    7.83% of all LL-cache hits     (69.16%)
   <not supported>      L1-icache-loads                                             
         6,570,923      L1-icache-load-misses                                         (69.17%)
     2,608,231,947      dTLB-loads                # 1214.149 M/sec                    (68.98%)
            72,458      dTLB-load-misses          #    0.00% of all dTLB cache hits   (61.42%)
             1,064      iTLB-loads                #    0.495 K/sec                    (61.20%)
             4,017      iTLB-load-misses          #  377.54% of all iTLB cache hits   (61.17%)
   <not supported>      L1-dcache-prefetches                                        
   <not supported>      L1-dcache-prefetch-misses                                   

       2.165310899 seconds time elapsed

```

#### Optimized
```
perf stat -ddd ./bin/sobel resources/timberlea.pgm results/timberlea-edges.pgm

 Performance counter stats for './bin/sobel resources/timberlea.pgm results/timberlea-edges.pgm':

        146.446416      task-clock (msec)         #    0.955 CPUs utilized          
                21      context-switches          #    0.143 K/sec                  
                 1      cpu-migrations            #    0.007 K/sec                  
             1,411      page-faults               #    0.010 M/sec                  
       537,461,263      cycles                    #    3.670 GHz                      (59.70%)
     1,235,333,377      instructions              #    2.30  insn per cycle           (67.90%)
       179,975,271      branches                  # 1228.950 M/sec                    (66.01%)
           105,215      branch-misses             #    0.06% of all branches          (66.13%)
       338,360,958      L1-dcache-loads           # 2310.476 M/sec                    (65.60%)
        38,499,777      L1-dcache-load-misses     #   11.38% of all L1-dcache hits    (65.60%)
         1,235,370      LLC-loads                 #    8.436 M/sec                    (67.44%)
           693,897      LLC-load-misses           #  112.34% of all LL-cache hits     (69.75%)
   <not supported>      L1-icache-loads                                             
         5,460,368      L1-icache-load-misses                                         (76.77%)
       265,301,817      dTLB-loads                # 1811.597 M/sec                    (77.40%)
            20,535      dTLB-load-misses          #    0.01% of all dTLB cache hits   (67.06%)
               203      iTLB-loads                #    0.001 M/sec                    (64.65%)
             5,067      iTLB-load-misses          # 2496.06% of all iTLB cache hits   (62.40%)
   <not supported>      L1-dcache-prefetches                                        
   <not supported>      L1-dcache-prefetch-misses                                   

       0.153365717 seconds time elapsed

```
