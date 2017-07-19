#! /usr/bin/python3
''' Disassembles machine code into WRAMPcode '''
import argparse

INSTRUCTIONS = {
    "0000" : {
        "0000" : "add ",
        "0010" : "sub ",
        "1011" : "and ",
        "1111" : "xor ",
        "1101" : "or "
    },
    "0001" : {
        "0000" : "addi ",
        "0010" : "subi ",
        "1011" : "andi ",
        "1111" : "xori ",
        "1101" : "ori"
    },
    "1000" : "lw ",
    "1001" : "sw ",
    "0100" : "j ",
    "1011" : "bnez ",
    "1010" : "beqz "
}


def scan_labels(ilines):
    
    # For each line check if there is a label reference
    labels = {}
    label_count = 0
    for line in ilines:
        if line and (not line.isspace()):
            # Check if instruction mentions a label
            words = line.split()
            if words[0] == "0100" or words[0] == "1011" or words[0] == "1010":
                addr = "".join(words[3:])
                if addr not in labels.keys():
                    # The addr doesnt exists
                    pc = int(addr, 2)
                    labels[pc] = "L" + str(label_count)
                    label_count += 1
    return labels

def convert(ilines, labels):
    ''' Converts machine code line to WRAMP '''
    pc = 0
    for line in ilines:
        if line and (not line.isspace()):
            output_line = []
            words = line.split()

            pc += 1            
            # Check if there is a label for this PC
            if pc in labels.keys():
                output_line.append(labels[pc] + ":")
                print("".join(output_line))
                output_line = []

            # Find instruction
            key = words[0]
            instruct = INSTRUCTIONS[key]
            if not isinstance(instruct, str):
                key = words[3]
                output_line.append(instruct[key])
            else:
                output_line.append(instruct)

            # Get sources/destination
            if words[0] == "0000":
                # Destination
                output_line.append("$" + str(int(words[1], 2)) + ",")

                # Sources
                output_line.append("$" + str(int(words[2], 2)) + ",")
                output_line.append("$" + str(int(words[7], 2)))

            elif words[0] == "0001":
                # Destination
                output_line.append("$" + str(int(words[1], 2)) + ",")

                # Source
                output_line.append("$" + str(int(words[2], 2)) + ",")

                # imm
                output_line.append(hex(int("".join(words[4:]), 2)))

            else:
                # Destinations
                if words[0] == "1000" and words[0] == "1001":
                    output_line.append("$" + str(int(words[1], 2)) + ",")

                # Sources
                if words[0] != "0100":
                    output_line.append("$" + str(int(words[2], 2)) + ",")

                # Values/Labels
                if words[0] == "1000" and words[0] == "1001":
                    # Values
                    output_line.append(hex(int("".join(words[4:]), 2)))
                else:
                    # Labels
                    addr = "".join(words[3:])
                    addr_dec = int(addr, 2)

                    label = labels[addr_dec]
                    output_line.append(label)

            output_line = "".join(output_line)
            print(output_line)

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

    labels = scan_labels(ilines)
    convert(ilines, labels)

if __name__ == "__main__":
    main()
