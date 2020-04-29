from src.LanguageHandler import *
from src.Compiler import *


class ClientHandlerManager:
    def __init__(self):
        # create chain
        kotlin = KotlinHandler()
        python = PythonHandler()
        java = JavaHandler()
        bash = BashHandler()

        #establish connections
        kotlin.set_next_handler(python)
        python.set_next_handler(java)
        java.set_next_handler(bash)

        self.mainHandler = kotlin

    def request_command(self, file_path) -> LanguageCompiler:
        command = self.mainHandler.request_command_from_file(file_path)
        return command


if __name__ == "__main__":
    #Create request manager
    requestManager = ClientHandlerManager()

    #read filepath from stdin
    filePath = input('Please insert executing file path: ')

    #request finding the file
    command = requestManager.request_command(filePath)

    #execute file
    command.compile()
