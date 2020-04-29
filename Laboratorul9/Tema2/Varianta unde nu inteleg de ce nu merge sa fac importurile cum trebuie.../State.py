import abc
from Observable import SelectProductSTM, TakeMoneySTM


class State(metaclass=abc.ABCMeta):
    pass


class Beer(State):
    def __init__(self, state_machine: SelectProductSTM):
        self.state_machine = state_machine
        self.price = 7


class Sprite(State):
    def __init__(self, state_machine: SelectProductSTM):
        self.state_machine = state_machine
        self.price = 2


class WaitingForClient(State):
    def __init__(self, state_machine: TakeMoneySTM):
        self.state_machine = state_machine

    def client_arrived(self):
        self.state_machine.current_state = self.state_machine.insert_money_state
        # money = 0 ?


class SelectProduct(State):
    def __init__(self, state_machine: SelectProductSTM):
        self.state_machine = state_machine
        self.price = 0

    def choose(self):
        while True:
            print("Choose a product:\n")
            print("1 -> CocaCola\n")
            print("2 -> Pepsi\n")
            print("3 -> Sprite\n")
            print("4 -> Beer\n")

            client_input = input("Desired product code: ")
            if len(client_input) != 1 and ord(client_input[0]) not in range(ord('1'), ord('5')):
                print("Wrong input! Please insert again!\n")
            else:
                break

        if client_input == "1":
            self.state_machine.current_state = self.state_machine.coca_cola_state

        if client_input == "2":
            self.state_machine.current_state = self.state_machine.pepsi_state

        if client_input == "3":
            self.state_machine.current_state = self.state_machine.sprite_state

        if client_input == "4":
            self.state_machine.current_state = self.state_machine.beer_state


class Pepsi(State):
    def __init__(self, state_machine: SelectProductSTM):
        self.state_machine = state_machine
        self.price = 3.5


class InsertMoney(State):
    def __init__(self, state_machine: TakeMoneySTM):
        self.state_machine = state_machine

    def insert_10bani(self):
        self.state_machine.money += 0.1

    def insert_50bani(self):
        self.state_machine.money += 0.5

    def insert_1leu(self):
        self.state_machine.money += 1

    def insert_5lei(self):
        self.state_machine.money += 5

    def insert_10lei(self):
        self.state_machine.money += 10


class CocaCola(State):
    def __init__(self, state_machine: SelectProductSTM):
        self.state_machine = state_machine
        self.price = 3
