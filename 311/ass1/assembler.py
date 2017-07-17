#! /usr/bin/python3

import argparse

INSTRUCTIONS = {
                   "add"  : ["0000", "dddd", "ssss", "0000", "0000", "0000", "0000", "tttt"]#,
                #    "sub"  : "0000 dddd ssss 0010 0000 0000 0000 tttt",
                #    "and"  : "0000 dddd ssss 1011 0000 0000 0000 tttt",
                #    "xor"  : "0000 dddd ssss 1111 0000 0000 0000 tttt",
                #    "or"   : "0000 dddd ssss 1101 0000 0000 0000 tttt",
                #    "addi" : "0001 dddd ssss 0000 iiii iiii iiii iiii",
                #    "subi" : "0001 dddd ssss 0000 iiii iiii iiii iiii",
                #    "andi" : "0001 dddd ssss 0000 iiii iiii iiii iiii",
                #    "xori" : "0001 dddd ssss 0000 iiii iiii iiii iiii",
                #    "ori"  : "0001 dddd ssss 0000 iiii iiii iiii iiii",
                #    "lw"   : "1000 dddd ssss iiii iiii iiii iiii iiii",
                #    "sw"   : "1001 dddd ssss iiii iiii iiii iiii iiii",
                #    "j"    : "0100 0000 0000 iiii iiii iiii iiii iiii",
                #    "bnez" : "1011 0000 ssss iiii iiii iiii iiii iiii",
                #    "beqz" : "1010 0000 ssss iiii iiii iiii iiii iiii"
               }

def main():
    # Parse args
    parser = argparse.ArgumentParser()
    parser.add_argument("-f", action="store", type=str, dest="input_file", required=True, help="Input file")
    args = parser.parse_args()

    # Read file into variable
    ilines = None
    try:
        with open(args.input_file) as ifile:
            ilines = ifile.read().split("\n")
    except FileNotFoundError:
        print("ERROR File Not Found: " + args.input_file)
        exit(1)

    # Remove whitespace and comments
    cleaned_lines = []
    for line in ilines:
        line = line.lstrip()
        line = line.split("#")[0]
        if line and (not line.isspace()):
            # print(line)
            cleaned_lines.append(line)
    
    # Parse for instructions
    output = []
    for line in cleaned_lines:
        keyword = line.split()[0]
        try:
            output.append(" ".join(INSTRUCTIONS[keyword]))
        except KeyError:
            output.append('err: ' + keyword)
            # print("ERROR Unrecognized instruction: " + keyword)
            # exit(1)
            pass

    # Output into new file
    with open(args.input_file + ".output", "w") as ofile:
        for line in output:
            print(line)
            ofile.write(line + "\n")


if __name__ == "__main__":
    main()





## Code for gettings parts of array
# array = ['1', '2', '3', '4']
# print(array[1:])
