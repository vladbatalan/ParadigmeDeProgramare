from VendingMachineSTM import VendingMachineSTM


class VendingMachineInterface:
    def __init__(self):
        self.main_machine = VendingMachineSTM()

    def do_task(self):
        while True:
            print("Alegeti o optiune:\n")
            print("1 -> Alegeti un produs:\n")
            print("2 -> Inserati bani:\n")
            print("3 -> Realizati achizitia:\n")

            client_option = input("Optiunea aleasa: ")

            if len(client_option) != 1 or client_option[0] not in range(ord('1'), ord('4')):
                print("Optiune invalida! Alegeti din nou.\n")
            else:
                break

        if client_option == "1":
            self.main_machine.choose_product()

        if client_option == "2":
            self.main_machine.insert_money()

        if client_option == "3":
            self.main_machine.proceed_to_checkout()


if __name__ == "__main__":
    juice_vending_machine = VendingMachineInterface()

    while True:
        juice_vending_machine.do_task()