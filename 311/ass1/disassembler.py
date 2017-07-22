#! /usr/bin/python3
''' Disassembles machine code into WRAMP code '''
import argparse
import struct

MAX_IMM = int("11111111111111111111", 2)

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
        "1101" : "ori "
    },
    "1000" : "lw ",
    "1001" : "sw ",
    "0100" : "j ",
    "1011" : "bnez ",
    "1010" : "beqz "
}

def scan_labels(ilines):
    ''' Scans the given lines for labels and returns a dictionary of them '''
    # For each line check if there is a label reference
    labels = {}
    label_count = 0
    counter = 1
    for line in ilines:
        # Check if instruction mentions a label
        if line[0] == "0100" or line[0] == "1011" or line[0] == "1010":
            addr = "".join(line[3:])

            if addr not in labels.keys():
                # The addr doesnt exists
                program_counter = int(addr, 2)
                
                # If branch instruction
                if line[0] == "1011" or line[0] == "1010":
                    program_counter = program_counter + counter - MAX_IMM
                    if program_counter < 0:
                        program_counter += MAX_IMM + 1
                    elif program_counter > len(ilines):
                        program_counter -= MAX_IMM + 1

                # Add label to dictionary
                if not program_counter > len(ilines):
                    labels[program_counter] = "L" + str(label_count)
                    label_count += 1
        counter += 1

    return labels

def convert(ilines, labels):
    ''' Converts machine code line to WRAMP '''
    output = []
    program_counter = 0
    for line in ilines:
        output_line = []
        program_counter += 1

        # Check if there is a label for this PC
        if program_counter in labels.keys():
            output.append(labels[program_counter] + ":")

        # Find instruction
        key = line[0]
        instruct = INSTRUCTIONS[key]
        if not isinstance(instruct, str):
            key = line[3]
            output_line.append(instruct[key])
        else:
            output_line.append(instruct)

        # Get sources/destination
        if line[0] == "0000":
            # Destination
            output_line.append("$" + str(int(line[1], 2)) + ",")

            # Sources
            output_line.append("$" + str(int(line[2], 2)) + ",")
            output_line.append("$" + str(int(line[7], 2)))

        elif line[0] == "0001":
            # Destination
            output_line.append("$" + str(int(line[1], 2)) + ",")

            # Source
            output_line.append("$" + str(int(line[2], 2)) + ",")

            # imm
            output_line.append(hex(int("".join(line[4:]), 2)))

        else:
            # Values/Labels
            if line[0] == "1000" or line[0] == "1001":
                # Destination
                output_line.append("$" + str(int(line[1], 2)) + ",")

                # Stack
                addr = "".join(line[3:])
                stack = int(addr, 2)
                if stack > (MAX_IMM / 2):
                    stack -= MAX_IMM + 1
                
                # Register
                register = "($" + str(int(line[2], 2)) + ")"

                output_line.append(str(stack))
                output_line.append(register)
            else:
                # Source
                output_line.append("$" + str(int(line[2], 2)) + ",")

                # Labels
                addr = "".join(line[3:])
                addr_dec = int(addr, 2)

                 # If branch instruction (relative)
                if line[0] == "1011" or line[0] == "1010":
                    addr_dec = addr_dec + program_counter - MAX_IMM
                    if addr_dec < 0:
                        addr_dec += MAX_IMM + 1
                    elif addr_dec > len(ilines):
                        addr_dec -= MAX_IMM + 1
                
                try:
                    label = labels[addr_dec]
                    output_line.append(label)
                except KeyError:
                    output_line.append(hex(int(addr, 2)))
                    

        output.append("".join(output_line))
    return output

def nibbles(line):
    ''' Returns an array of nibbles from a given line '''
    nibbles = [line[i:i+4] for i in range(0, len(line), 4)]
    return nibbles

def read_file(filename):
    ''' Reads a file and returns an array of lines of nibbles '''
    # Read file into variable
    lines = []
    try:
        with open(filename, "rb") as ifile:
            out = struct.iter_unpack("I", ifile.read())
            for tup in out:
                line = "{0:032b}".format(int(str(tup).split("(")[1].split(",")[0]))
                lines.append(nibbles(line))
            return lines
    except FileNotFoundError:
        print("ERROR File Not Found: " + filename)
        exit(1)


def main():
    ''' main method '''
    # Parse args
    parser = argparse.ArgumentParser()
    parser.add_argument("-i", action="store", type=str, dest="input_file",
                        required=True, help="Input file")
    parser.add_argument("-o", action="store", type=str, dest="output_file",
                        required=True, help="Output file")
    args = parser.parse_args()

    # Read file into variable
    lines = read_file(args.input_file)
    for _ in lines:
        print(_)

    # get the pc of each label
    labels = scan_labels(lines)

    # Convert the machine code to WRAMP
    print(labels)
    output = convert(lines, labels)

    # Output to file
    with open(args.output_file, "w") as ofile:
        for line in output:
            print(line)
            ofile.write(line + "\n")

if __name__ == "__main__":
    main()
