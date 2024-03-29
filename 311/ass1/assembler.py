#! /usr/bin/python3
'''
Author: Dominic Azaris
ID:     1297845
Descripton: Assembles WRAMP code into machine code

'''
import argparse

MAX_IMM = int("11111111111111111111", 2)

INSTRUCTIONS = {
    "add"  : ["0000", "dddd", "ssss", "0000", "0000", "0000", "0000", "tttt"],
    "sub"  : ["0000", "dddd", "ssss", "0010", "0000", "0000", "0000", "tttt"],
    "and"  : ["0000", "dddd", "ssss", "1011", "0000", "0000", "0000", "tttt"],
    "xor"  : ["0000", "dddd", "ssss", "1111", "0000", "0000", "0000", "tttt"],
    "or"   : ["0000", "dddd", "ssss", "1101", "0000", "0000", "0000", "tttt"],
    "addi" : ["0001", "dddd", "ssss", "0000", "iiii", "iiii", "iiii", "iiii"],
    "subi" : ["0001", "dddd", "ssss", "0010", "iiii", "iiii", "iiii", "iiii"],
    "andi" : ["0001", "dddd", "ssss", "1011", "iiii", "iiii", "iiii", "iiii"],
    "xori" : ["0001", "dddd", "ssss", "1111", "iiii", "iiii", "iiii", "iiii"],
    "ori"  : ["0001", "dddd", "ssss", "1101", "iiii", "iiii", "iiii", "iiii"],
    "lw"   : ["1000", "dddd", "ssss", "iiii", "iiii", "iiii", "iiii", "iiii"],
    "sw"   : ["1001", "dddd", "ssss", "iiii", "iiii", "iiii", "iiii", "iiii"],
    "j"    : ["0100", "0000", "0000", "iiii", "iiii", "iiii", "iiii", "iiii"],
    "bnez" : ["1011", "0000", "ssss", "iiii", "iiii", "iiii", "iiii", "iiii"],
    "beqz" : ["1010", "0000", "ssss", "iiii", "iiii", "iiii", "iiii", "iiii"]
    }

def space_out(string, length):
    ''' Adds a space every `length` characters '''
    return " ".join(string[i:i+length] for i in range(0, len(string), length))

def get_args():
    ''' Creates a parser and returns args '''
    parser = argparse.ArgumentParser()
    parser.add_argument("-i", action="store", type=str, dest="input_file",
                        required=True, help="Input file")
    parser.add_argument("-o", action="store", type=str, dest="output_file",
                        required=True, help="Output file")
    args = parser.parse_args()
    return args

def convert_to_bytes(lines):
    ''' Converts each 8 bits into a byte from a given string '''
    bytes_out = []
    for line in lines:
        nibbles = line.split(" ")
        tmp = []
        for val in [7, 5, 3, 1]:
            byte = nibbles[val - 1] + nibbles[val]
            bytes_out.append(int(byte, 2))
            tmp.append(int(byte, 2))
    byte_array = bytearray(bytes_out)
    return byte_array

def read_file(input_file):
    ''' Reads a file and return output '''
    ilines = None
    try:
        with open(input_file) as ifile:
            ilines = ifile.read().split("\n")
            return ilines
    except FileNotFoundError:
        print("ERROR File Not Found: " + input_file)
        exit(1)

def clean_whitespace(lines):
    ''' Removes comments and whitespace '''
    cleaned_lines = []
    for line in lines:
        line = line.lstrip()
        line = line.split("#")[0]
        if line and (not line.isspace()):
            cleaned_lines.append(line)

    return cleaned_lines

def parse_labels(lines):
    ''' Parses input for labels '''
    program_counter = 1
    labels = {}
    for line in lines:
        # Split line to get the operand
        operand = line.split()[0]

        if ":" in operand:
            # Found a label, add it to the map
            label = str(operand.split(":")[0])
            labels[label] = program_counter
        else:
            # PC only increments when not a label
            program_counter += 1

    return labels

def parse_instructions(lines, labels):
    ''' Parses input for instructions and converts to binary '''
    program_counter = 0
    output = []
    for line in lines:
        # Split line to get the operand
        operand = line.split()[0]

        # Skip any labels
        if ":" in operand:
            continue

        # Fetched cmd, now increment pc
        program_counter += 1

        # Check for .word
        if ".word" in operand:
            word = line.split()[-1]

            # Convert
            word = int(word[2:], 16)
            out = space_out("{0:032b}".format(word), 4)
            output.append(out)
            continue

        # Load machine code template
        out = INSTRUCTIONS[operand]

        # Parse to fill out template
        if out[0] == "0000":
            # Get the optargs
            optargs = line.split()[1].split(",")

            # Destination
            out[1] = "{0:04b}".format(int(optargs[0][1:], 10))

            # Sources
            out[2] = "{0:04b}".format(int(optargs[1][1:], 10))
            out[7] = "{0:04b}".format(int(optargs[2][1:], 10))

        elif out[0] == "0001" or out[0] == "1000" or out[0] == "1001":
            # Get the optargs
            optargs = line.split()[1].split(",")

            # Destination
            out[1] = "{0:04b}".format(int(optargs[0][1:], 10))

            # Source
            preaddr = None
            if out[0] == "0001":
                out[2] = "{0:04b}".format(int(optargs[1][1:], 10))
                preaddr = optargs[2]

                # Get the address and convert to binary
                addr = None
                if "0b" in preaddr:
                    addr = "{0:016b}".format(int(preaddr[2:], 2))
                elif "0x" in preaddr:
                    hex_addr = int(preaddr[2:], 16)
                    addr = "{0:016b}".format(hex_addr)
                else:
                    addr = "{0:016b}".format(int(preaddr))

                # add split up and add to the output
                addr = space_out(addr, 4)
                for val in range(4, 8):
                    out[val] = addr.split()[val - 4]

            else:
                imm = optargs[1].split("(")[0]
                src = optargs[1].split("$")[-1].split(")")[0]

                src = "{0:04b}".format(int(src))
                out[2] = src

                # Get the address and convert to binary
                addr_int = 0
                if "0b" in imm:
                    addr_int = int(imm[2:], 2)
                elif "0x" in imm:
                    addr_int = int(imm[2:], 16)
                else:
                    addr_int = int(imm)
                    if addr_int < 0:
                        addr_int = MAX_IMM + addr_int + 1

                addr = "{0:020b}".format(addr_int)

                # add split up and add to the output
                addr = space_out(addr, 4)
                for val in range(3, 8):
                    out[val] = addr.split()[val - 4]

        elif out[0] == "0100":
            # Jump - convert the pc to binary at the given label
            label = line.split()[1].split(",")[0]

            addr = None
            try:
                addr = "{0:020b}".format(int(label))
            except ValueError:
                addr = "{0:020b}".format(int(labels[label]))

            # Split up into 4 bit segments
            addr = space_out(addr, 4)
            for val in range(3, 8):
                out[val] = addr.split()[val - 3]

        elif out[0] == "1011" or out[0] == "1010":
            # Branch ifs
            # Get optargs
            optargs = line.split()[1].split(",")

            # Put register in source field
            out[2] = "{0:04b}".format(int(optargs[0][1:], 10))

            # Get address
            label = optargs[1]
            addr = None
            addr_int = 0
            try:
                # Check for regular int
                decimal = int(label)
                addr_int = int(decimal)
            except ValueError:
                if "0x" in label:
                    # Check for hex
                    addr_int = int(label, 16)
                elif "0b" in label:
                    # Check for binary
                    addr_int = int(label, 2)
                else:
                    # addr will be relative to pc
                    addr_int = int(labels[label]) - program_counter - 1

            if addr_int < 0:
                addr_int = MAX_IMM + addr_int + 1

            addr = "{0:020b}".format(addr_int)


            # Space out address and add to output
            addr = space_out(addr, 4)
            for val in range(3, 8):
                out[val] = addr.split()[val - 3]

        # Store the new output line
        output.append(" ".join(out))
    return output

def assemble(input_file):
    ''' Opens a file and assembles it to machine code '''
    # Read file into variable
    ilines = read_file(input_file)

    # Remove whitespace and comments
    cleaned_lines = clean_whitespace(ilines)

    # Parse labels and add to a map
    labels = parse_labels(cleaned_lines)

    # Parse for instructions
    output = parse_instructions(cleaned_lines, labels)

    # Convert each 8 bits to a byte
    byte_array = convert_to_bytes(output)

    return byte_array

def main():
    ''' main method '''
    # Parse args
    args = get_args()

    # Assemble
    output = assemble(args.input_file)

    # Output into new file
    with open(args.output_file, "wb") as ofile:
        ofile.write(output)

if __name__ == "__main__":
    main()
