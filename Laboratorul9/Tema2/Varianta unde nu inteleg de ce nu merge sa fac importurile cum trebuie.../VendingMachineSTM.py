from Observable import TakeMoneySTM
from Observable import SelectProductSTM
from Observer import ChoiceObserver
from Observer import DisplayObserver


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
            print("1 -> insert 10 bani\n")
            print("2 -> insert 50 bani\n")
            print("3 -> insert 1 leu\n")
            print("4 -> insert 5 lei\n")
            print("5 -> insert 10 lei\n")

            client_option = input("Alege optiune: ")

            if len(client_option) != 1 or client_option[0] not in range(ord('1'), ord('6')):
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
                    print("Alege o optiune:\n")
                    print("1 -> Alege alt produs\n")
                    print("2 -> Primeste restul\n")

                    client_option = input('Optiunea aleasa: ')
                    if len(client_option) != 1 or client_option[0] not in range(ord('1'), ord('3')):
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
