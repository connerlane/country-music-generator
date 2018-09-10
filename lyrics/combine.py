from os import listdir

with open('combined.txt', 'w') as outfile:
    for fname in listdir("."):
        with open(fname) as infile:
            for line in infile:
                outfile.write(line)