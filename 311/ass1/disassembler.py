#! /usr/bin/python3
''' Disassembles machine code into WRAMPcode '''
import argparse

INSTRUCTIONS = {
    "0000" : {
        "0000" : "add",
        "0010" : "sub",
        "1011" : "and",
        "1111" : "xor",
        "1101" : "or"
    },
    "0001" : {
        "0000" : "addi",
        "0010" : "subi",
        "1011" : "andi",
        "1111" : "xori",
        "1101" : "ori"
    },
    "1000" : "lw",
    "1001" : "sw",
    "0100" : "j",
    "1011" : "bnez",
    "1010" : "beqz"
}

def convert(line):
    ''' Converts machine code line to WRAMP '''
    words = line.split()

    # Find function
    key = words[0]
    instruct = INSTRUCTIONS[key]
    if not isinstance(instruct, str):
        key = words[3]
        print(instruct[key])
    else:
        print(instruct)

def main():
    ''' main method '''
    # Parse args
    parser = argparse.ArgumentParser()
    parser.add_argument("-f", action="store", type=str, dest="input_file",
                        required=True, help="Input file")
    args = parser.parse_args()

    # Read file into variable
    ilines = None
    try:
        with open(args.input_file) as ifile:
            ilines = ifile.read().split("\n")
    except FileNotFoundError:
        print("ERROR File Not Found: " + args.input_file)
        exit(1)

    for line in ilines:
        if line and (not line.isspace()):
            convert(line)

if __name__ == "__main__":
    main()
