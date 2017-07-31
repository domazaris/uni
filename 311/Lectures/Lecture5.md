# Lecture 5 - Verilog

## Synthesizable Verilog
* Java Classes ~= modules
* Everything statically allocated
* Vivado generates block diagrams from code
* Sequential logic must be in always blocks
* assign statements only for combinational logic
* = (blocking)
* <= (non-blocking)
* Non blocking assignmets map to hardware more directly
* {} is now `begin` & `end`
* 