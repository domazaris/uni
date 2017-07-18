#! /usr/bin/python3
''' Assembles WRAMP code into machine code '''
import argparse

INSTRUCTIONS = {
    "add"  : ["0000", "dddd", "ssss", "0000", "0000", "0000", "0000", "tttt"],
    "sub"  : ["0000", "dddd", "ssss", "0010", "0000", "0000", "0000", "tttt"],
    "and"  : ["0000", "dddd", "ssss", "1011", "0000", "0000", "0000", "tttt"],
    "xor"  : ["0000", "dddd", "ssss", "1111", "0000", "0000", "0000", "tttt"],
    "or"   : ["0000", "dddd", "ssss", "1101", "0000", "0000", "0000", "tttt"],
    "addi" : ["0001", "dddd", "ssss", "0000", "iiii", "iiii", "iiii", "iiii"],
    "subi" : ["0001", "dddd", "ssss", "0000", "iiii", "iiii", "iiii", "iiii"],
    "andi" : ["0001", "dddd", "ssss", "0000", "iiii", "iiii", "iiii", "iiii"],
    "xori" : ["0001", "dddd", "ssss", "0000", "iiii", "iiii", "iiii", "iiii"],
    "ori"  : ["0001", "dddd", "ssss", "0000", "iiii", "iiii", "iiii", "iiii"],
    "lw"   : ["1000", "dddd", "ssss", "iiii", "iiii", "iiii", "iiii", "iiii"],
    "sw"   : ["1001", "dddd", "ssss", "iiii", "iiii", "iiii", "iiii", "iiii"],
    "j"    : ["0100", "0000", "0000", "iiii", "iiii", "iiii", "iiii", "iiii"],
    "bnez" : ["1011", "0000", "ssss", "iiii", "iiii", "iiii", "iiii", "iiii"],
    "beqz" : ["1010", "0000", "ssss", "iiii", "iiii", "iiii", "iiii", "iiii"]
    }

def space_out(string, length):
    ''' Adds a space every `length` characters '''
    return ' '.join(string[i:i+length] for i in range(0, len(string), length))

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

    # Remove whitespace and comments
    cleaned_lines = []
    for line in ilines:
        line = line.lstrip()
        line = line.split("#")[0]
        if line and (not line.isspace()):
            # print(line)
            cleaned_lines.append(line)

    # Parse labels and add to a map
    PROGRAM_COUNTER = 0
    LABELS = {}
    for line in cleaned_lines:
        # Fetched cmd, now increment pc
        PROGRAM_COUNTER += 1

        # Split line to get the operand
        operand = line.split()[0]

        if ":" in operand:
            # Found a label, add it to the map
            label = str(operand.split(":")[0])
            LABELS[label] = PROGRAM_COUNTER

    # Parse for instructions
    PROGRAM_COUNTER = 0
    output = []
    for line in cleaned_lines:
        # Fetched cmd, now increment pc
        PROGRAM_COUNTER += 1

        # Split line to get the operand
        operand = line.split()[0]

        # Skip any labels
        if ":" in operand:
            continue

        # Load machine code template
        out = INSTRUCTIONS[operand]

        # Parse to fill out template
        if out[0] == "0000":
            # Get the optargs
            optargs = line.split()[1].split(",")

            # Destination
            out[1] = "{0:04b}".format(int(optargs[0][1:]))

            # Sources
            out[2] = "{0:04b}".format(int(optargs[1][1:]))
            out[7] = "{0:04b}".format(int(optargs[2][1:]))

        elif out[0] == "0001" or out[0] == "1000" or out[0] == "1001":
            # Get the optargs
            optargs = line.split()[1].split(",")

            # Destination
            out[1] = "{0:04b}".format(int(optargs[0][1:]))

            # Source
            out[2] = "{0:04b}".format(int(optargs[1][1:]))

            # Get the address and convert to binary
            preaddr = optargs[2]
            addr = None
            if "0b" in preaddr:
                addr = "{0:016b}".format(int(preaddr[2:]))
            elif "0x" in preaddr:
                hex_addr = int(preaddr[2:], 16)
                addr = "{0:016b}".format(hex_addr)
            else:
                addr = "{0:016b}".format(int(preaddr))

            # add split up and add to the output
            addr = space_out(addr, 4)
            for val in range(4, 8):
                out[val] = addr.split()[val - 4]

        elif out[0] == "0100":
            # Jump - convert the pc to binary at the given label
            label = line.split()[1].split(",")[0]
            addr = "{0:020b}".format(int(LABELS[label]))

            # Split up into 4 bit segments
            addr = space_out(addr, 4)
            for val in range(3, 8):
                out[val] = addr.split()[val - 3]

        elif out[0] == "1011" or out[0] == "1010":
            # Get optargs
            optargs = line.split()[1].split(",")

            # Put register in source field
            out[2] = "{0:04b}".format(int(optargs[0][1:]))

            # Get address
            label = optargs[1]
            addr = "{0:020b}".format(int(LABELS[label]))

            # Space out address and add to output
            addr = space_out(addr, 4)
            for val in range(3, 8):
                out[val] = addr.split()[val - 3]

        # Store the new output line
        output.append(" ".join(out))

    # Output into new file
    with open(args.input_file.split(".")[0] + ".output", "w") as ofile:
        for line in output:
            print(line)
            ofile.write(line + "\n")

if __name__ == "__main__":
    main()
