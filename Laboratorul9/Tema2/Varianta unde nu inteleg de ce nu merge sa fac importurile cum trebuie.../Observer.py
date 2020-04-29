from Observable import SelectProductSTM

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
        print("From display observer: money inserted = ", self.money)
