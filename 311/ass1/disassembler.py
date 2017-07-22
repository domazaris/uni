#! /usr/bin/python3
''' Disassembles machine code into WRAMPcode '''
import argparse
import struct
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
    ''' Scans the given lines for labels and returns a dictionary of them '''
    # For each line check if there is a label reference
    labels = {}
    label_count = 0
    for line in ilines:
        # Check if instruction mentions a label
        if line[0] == "0100" or line[0] == "1011" or line[0] == "1010":
            addr = "".join(line[3:])
            if addr not in labels.keys():
                # The addr doesnt exists
                program_counter = int(addr, 2)
                labels[program_counter] = "L" + str(label_count)
                label_count += 1
    return labels

def convert(ilines, labels):
    ''' Converts machine code line to WRAMP '''
    output = []
    program_counter = 0
    for line in ilines:
        output_line = []

        # Check if there is a label for this PC
        if program_counter in labels.keys():
            output_line.append(labels[program_counter] + ":")
            output.append("".join(output_line))
            output_line = []
            program_counter += 1
            continue

        program_counter += 1

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
            # Destinations
            if line[0] == "1000" and line[0] == "1001":
                output_line.append("$" + str(int(line[1], 2)) + ",")

            # Sources
            if line[0] != "0100":
                output_line.append("$" + str(int(line[2], 2)) + ",")

            # Values/Labels
            if line[0] == "1000" and line[0] == "1001":
                # Values
                output_line.append(hex(int("".join(line[4:]), 2)))
            else:
                # Labels
                addr = "".join(line[3:])
                addr_dec = int(addr, 2)

                label = labels[addr_dec]
                output_line.append(label)

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
    parser.add_argument("-f", action="store", type=str, dest="input_file",
                        required=True, help="Input file")
    args = parser.parse_args()

    # Read file into variable
    lines = read_file(args.input_file)

    # get the pc of each label
    labels = scan_labels(lines)

    # Convert the machine code to WRAMP
    output = convert(lines, labels)

    for _ in output:
        print(_)

    # # Output to file
    # ofile_name = args.input_file.split("/")[-1].split(".")[0] + ".s"
    # with open(ofile_name, "w") as ofile:
    #     for line in output:
    #         ofile.write(line + "\n")

if __name__ == "__main__":
    main()
