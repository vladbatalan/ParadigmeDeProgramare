from os import path
import os, sys


def existing_file_filter(file_paths):
    for file_path in file_paths:
        if path.exists(file_path):
            yield  file_path

def txt_file_filter(file_paths):
    for file_path in file_paths:
        if(".txt" in file_path):
            yield file_path

def number_of_lines(file_paths):
    file_paths = existing_file_filter(file_paths)
    for file_path in file_paths:
        fd = open(file_path, "r")

        text = fd.read()

        fd.close()
        yield len(text.splitlines())

def display_name_and_lines(file_paths):
    file_paths = [file for file in existing_file_filter(file_paths)]
    for file_path in file_paths:
        name = os.path.basename(file_path)
        lines = [nr for nr in number_of_lines([file_path])]
        yield name + ": " + str(lines.__getitem__(0))


if __name__ == "__main__":
    file_paths = [
        "src/file1.txt",
        "src/file2.txt",
        "src/file3.txt",
        "src/file4.html",
        "src/file5.txt"
    ]

    print(file_paths)
    print([file_path for file_path in existing_file_filter(file_paths)])
    print([file_path for file_path in txt_file_filter(file_paths)])
    print([file_path for file_path in number_of_lines(file_paths)])
    print([file_path for file_path in display_name_and_lines(file_paths)])