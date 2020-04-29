from State import SelectProduct
from State import CocaCola
from State import Pepsi
from State import Sprite
from State import Beer
from State import WaitingForClient
from State import InsertMoney
import abc


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
