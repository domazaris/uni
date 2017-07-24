# Lecture 4 - Digital Electronics with FPGAs

## Digital logic
* Mostly will use logic gate notations
* Truth tables will be useful
* Combinational logic == stateless
* Sequential logic == can be stateful ( contains memory )

## Verilog
* VHTL is the other most popular hardware description language
* Verilog popular in US & canada, VHTL popular in Europe
* Modules cannot be recursive
* Instantiation done at compile time, not runtime
* One module per source file
* **Unit tests expected in assignments**
* Design process:
  1. Write design 
  2. Write Test bench 
  3. Run simulator, produces register values
* Test bench will need a clock signal - do first
* **Vivado will be used** - is proprietary
* Circuits should reset to known values on startup - initialise
* Can't assign to wire at clock cycle - only asynchronously
* Can assign register at clock cycle
* *if* statements are implemented as mux's

## Going beyond simulation
* Can use FPGAs or ASICs for hardware implementations
* ASICs faster and more power efficient, but more expensive
* CPUs use same technology as ASICs, but not application specific

## Logic Slices
* LUT: Look Up Table
* LUTs are used as truth tables
* Logic slices contain multiple LUTs
* CLB: Configurable Logic Block - colletion of logic slices

## DSP Slices
* For arithmetic opterations
* Faster than LUTs 

## Block RAM slices
* Used for:
  * Register files
  * Simulate main CPU memory
* Can use logic slices for these functions, but inefficient
  * "Distributed RAM"
  * Is not a good idea


## "Compilation"
* **Synthesis**: converts Verilog code into a netlist
  * **netlist**: describes digital circuit using flip-flops and logic gaes & wires
* **Implementation**: Maps netlist to routing resources on the FPGA
  * Dominant sub-step of this is known as **place and route**
* Optimisation steps can take a long time
