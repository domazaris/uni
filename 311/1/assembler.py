#! /usr/bin/python3

import argparse

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

    # Output into new file
    with open(args.input_file + ".output", "w") as ofile:
        for line in ilines:
            line = line.split("#")[0]
            if line and (not line.isspace()):
               print(line)
               ofile.write(line + "\n")

if __name__ == "__main__":
    main()