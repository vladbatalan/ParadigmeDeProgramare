import abc
from src.Compiler import LanguageCompiler, BashCompiler, PythonCompiler, KotlinCompiler, JavaCompiler


class LanguageHandler(metaclass=abc.ABCMeta):
    @abc.abstractmethod
    def request_command_from_file(self, file_path) -> LanguageCompiler:
        pass

    @abc.abstractmethod
    def set_next_handler(self, my_handler):
        pass


class KotlinHandler(LanguageHandler):
    def __init__(cls):
        cls.next_handler = None
        cls.word_dictionary = [
            'val ',
            'fun ',
        ]

    def request_command_from_file(self, file_path) -> LanguageCompiler:
        my_file = open(file_path, mode='r')
        #get content
        content_of_file = my_file.read()
        #close file
        my_file.close()

        is_kotlin_file = False
        #check the kotlin file condition from my_file text

        for word in self.word_dictionary:
            if str(content_of_file).find(word) != -1:
                is_kotlin_file = True

        #finished checking
        if is_kotlin_file == False:
            if self.next_handler == None:
                #the end -> cannot find file
                raise Exception("Cannot find maching language for the file given")
            else:
                #send the request to other
                return self.next_handler.request_command_from_file(file_path)
        else:
            return KotlinCompiler(file_path)


    def set_next_handler(self, my_handler:LanguageHandler):
        self.next_handler = my_handler

class PythonHandler(LanguageHandler):
    def __init__(cls):
        cls.next_handler = None
        cls.word_dictionary = [
            '__name__',
            '__init__',
            'def ',
        ]

    def request_command_from_file(self, file_path) -> LanguageCompiler:
        my_file = open(file_path, mode='r')
        #get content
        content_of_file = my_file.read()
        my_file.close()

        is_python_file = False
        #check the python file condition from my_file text

        for word in self.word_dictionary:
            if str(content_of_file).find(word) != -1:
                is_python_file = True

        #finished checking
        if is_python_file == False:
            if self.next_handler == None:
                #the end -> cannot find file
                raise Exception("Cannot find maching language for the file given")
            else:
                #send the request to other
                return self.next_handler.request_command_from_file(file_path)
        else:
            return PythonCompiler(file_path)

    def set_next_handler(self, my_handler:LanguageHandler):
            self.next_handler = my_handler

class BashHandler(LanguageHandler):
    def __init__(cls):
        cls.next_handler = None
        cls.word_dictionary = [
            '#!/bin/bash',
            'echo ',
        ]

    def request_command_from_file(self, file_path) -> LanguageCompiler:
        my_file = open(file_path, mode='r')
        #get content
        content_of_file = my_file.read()
        my_file.close()

        is_bash_file = False
        #check the bash file condition from my_file text

        for word in self.word_dictionary:
            if str(content_of_file).find(word) != -1:
                is_bash_file = True

        #finished checking
        if is_bash_file == False:
            if self.next_handler == None:
                #the end -> cannot find file
                raise Exception("Cannot find maching language for the file given")
            else:
                #send the request to other
                return self.next_handler.request_command_from_file(file_path)
        else:
            return BashCompiler(file_path)

    def set_next_handler(self, my_handler:LanguageHandler):
        self.next_handler = my_handler

class JavaHandler(LanguageHandler):
    def __init__(cls):
        cls.next_handler = None
        cls.word_dictionary = [
            ' main(String[] args)',
            'System.out.print'
        ]

    def request_command_from_file(self, file_path) -> LanguageCompiler:
        my_file = open(file_path, mode='r')
        #get content
        content_of_file = my_file.read()
        my_file.close()

        is_java_file = False
        #check the java file condition from my_file text
        for word in self.word_dictionary:
            if str(content_of_file).find(word) != -1:
                is_java_file = True

        #finished checking
        if is_java_file == False:
            if self.next_handler == None:
                #the end -> cannot find file
                raise Exception("Cannot find maching language for the file given")
            else:
                #send the request to other
                return self.next_handler.request_command_from_file(file_path)
        else:
            return JavaCompiler(file_path)

    def set_next_handler(self, my_handler:LanguageHandler):
        self.next_handler = my_handler