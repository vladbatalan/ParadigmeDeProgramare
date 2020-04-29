import abc


##################### Observables #########################
class Observable(metaclass=abc.ABCMeta):
    def __init__(self):
        self.observers = []

    def attach(self, observer):
        self.observers.append(observer)

    def detach(self, observer):
        self.observers.remove(observer)

    @abc.abstractmethod
    def notifyAll(self):
        pass


class SelectProductSTM(Observable):
    def __init__(self):
        super().__init__()
        self.select_product_state = SelectProduct(self)
        self.coca_cola_state = CocaCola(self)
        self.pepsi_state = Pepsi(self)
        self.sprite_state = Sprite(self)
        self.beer_state = Beer(self)
        self._current_state = self.select_product_state

    def notifyAll(self):
        for observer in self.observers:
            observer.update()

    @property
    def current_state(self):
        return self._current_state

    @current_state.setter
    def current_state(self, value):
        self._current_state = value
        self.notifyAll()


class TakeMoneySTM(Observable):
    def __init__(self):
        super().__init__()
        self.wait_state = WaitingForClient(self)
        self.insert_money_state = InsertMoney(self)
        self._money = 0
        self.current_state = self.wait_state

    def notifyAll(self):
        for observer in self.observers:
            observer.update(self.money)

    @property
    def money(self):
        return self._money

    @money.setter
    def money(self, new_money_amount):
        self._money = new_money_amount
        self.notifyAll()


######################### Observers #################################
class ChoiceObserver:
    def __init__(self, observable: SelectProductSTM):
        self.observable = observable
        self.current_state = observable.current_state

    def update(self):
        self.current_state = self.observable.current_state


class DisplayObserver:
    def __init__(self):
        self.money = 0

    def update(self, money):
        self.money = money
        print("Bani existenti in masina: ", self.money)


######################### States ################################
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
            print("Alege un produs:")
            print("1 -> CocaCola")
            print("2 -> Pepsi")
            print("3 -> Sprite")
            print("4 -> Beer")

            client_input = input("Desired product code: ")
            if int(client_input) not in range(1,5):
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
        print("Ati ales: ", self.state_machine.current_state.__class__.__name__)
        print("Pret: ", self.state_machine.current_state.price, ' lei\n')


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


######################## Vending Machine #############################
class VendingMachineSTM:
    def __init__(self):
        self.take_money_STM = TakeMoneySTM()
        self.select_prod_STM = SelectProductSTM()

        # the observer we use to notify the machine when ca choice is made
        self.choice_observer = ChoiceObserver(self.select_prod_STM)
        self.select_prod_STM.attach(self.choice_observer)
        self.take_money_STM.attach(DisplayObserver())

    def choose_product(self):
        self.select_prod_STM.current_state = self.select_prod_STM.select_product_state
        self.select_prod_STM.select_product_state.choose()

    def insert_money(self):
        self.take_money_STM.current_state = self.take_money_STM.insert_money_state

        while True:
            print("1 -> insert 10 bani")
            print("2 -> insert 50 bani")
            print("3 -> insert 1 leu")
            print("4 -> insert 5 lei")
            print("5 -> insert 10 lei")

            client_option = input("Alege optiune: ")
            print('\n')

            if int(client_option) not in range(1,6):
                print("Optiune invalida! Introduce-ti din nou.\n")
            else:
                break

        if client_option == "1":
            self.take_money_STM.insert_money_state.insert_10bani()

        if client_option == "2":
            self.take_money_STM.insert_money_state.insert_50bani()

        if client_option == "3":
            self.take_money_STM.insert_money_state.insert_1leu()

        if client_option == "4":
            self.take_money_STM.insert_money_state.insert_5lei()

        if client_option == "5":
            self.take_money_STM.insert_money_state.insert_10lei()

        self.take_money_STM.current_state = self.take_money_STM.wait_state

    def proceed_to_checkout(self):
        # the choice must have been made already
        if self.select_prod_STM.current_state != self.select_prod_STM.select_product_state:
            # check if there are enough money
            if self.take_money_STM.money >= self.choice_observer.current_state.price:
                # has enough
                print("Product is comming: ", self.choice_observer.current_state.__class__.__name__, "\n")
                self.take_money_STM.money -= self.choice_observer.current_state.price

                while True:
                    print("Alege o optiune:")
                    print("1 -> Alege alt produs")
                    print("2 -> Primeste restul")

                    client_option = input('Optiunea aleasa: ')
                    print('\n')
                    if int(client_option) not in range(1,3):
                        print("Optiune invalida! Va rugam sa alegeti din nou.\n")
                    else:
                        break

                # choose another product
                if client_option == "1":
                    self.choose_product()

                # return remaining money
                if client_option == "2":
                    print("Rest: ", self.take_money_STM.money)
                    self.take_money_STM.money = 0
            else:
                print("Va rog inserati bani!")

        else:
            print("Trebuie sa alegeti un produs!")


######################### Client Interface #################################
class VendingMachineInterface:
    def __init__(self):
        self.main_machine = VendingMachineSTM()

    def do_task(self):
        while True:
            print("\nAlegeti o optiune:")
            print("1 -> Alegeti un produs:")
            print("2 -> Inserati bani:")
            print("3 -> Realizati achizitia:")
            print("4 -> Parasiti aparatul:")

            client_option = input("Optiunea aleasa: ")
            print('\n')

            if int(client_option) not in range(1, 5):
                print("Optiune invalida! Alegeti din nou.\n")
            else:
                break

        if client_option == "1":
            self.main_machine.choose_product()

        if client_option == "2":
            self.main_machine.insert_money()

        if client_option == "3":
            self.main_machine.proceed_to_checkout()

        if client_option == "4":
            exit(0)


if __name__ == "__main__":
    juice_vending_machine = VendingMachineInterface()

    while True:
        juice_vending_machine.do_task()
