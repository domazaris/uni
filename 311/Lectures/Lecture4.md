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
* 
