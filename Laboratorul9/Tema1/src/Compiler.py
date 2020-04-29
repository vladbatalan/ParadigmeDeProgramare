import abc
import subprocess
import os


class LanguageCompiler(metaclass=abc.ABCMeta):
    @abc.abstractmethod
    def compile(self):
        pass


class KotlinCompiler(LanguageCompiler):
    def __init__(self, file_path):
        self.file_path = file_path

    def compile(self):
        print("Kotlin file Compile")
        print("Kotlin goes brrrrrrrrr")

class JavaCompiler(LanguageCompiler):
    def __init__(self, file_path):
        self.file_path = file_path

    def compile(self):
        print("Java file Compile")
        print(subprocess.Popen("javac " + self.file_path, shell=True))


class BashCompiler(LanguageCompiler):
    def __init__(self, file_path):
        self.file_path = file_path

    def compile(self):
        print("Bash file Compile")
        subprocess.Popen('./{}'.format(self.file_path), shell=True, executable="/bin/bash")

class PythonCompiler(LanguageCompiler):
    def __init__(self, file_path):
        self.file_path = file_path
    def compile(self):
        print(subprocess.run('python3 {}'.format(self.file_path), shell=True, check=True))