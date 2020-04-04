class BaseClass:
	num_base_calls = 0
	
	def call_me(self, caller):
		print("Apel metoda din BaseClass, caller=", caller)
		self.num_base_calls += 1

class LeftSubclass(BaseClass):
	num_left_calls = 0
	
	def call_me(self, caller):
		print("Apel metoda din LeftSubclass, caller=", caller)
		BaseClass.call_me(self, "LeftSubclass")
		self.num_left_calls += 1


class RightSubclass(BaseClass):
	num_right_calls = 0
	
	def call_me(self, caller):
		print("Apel metoda din RightSubclass, caller=", caller)
		BaseClass.call_me(self, "RightSubclass")
		self.num_right_calls += 1

class Subclass(LeftSubclass, RightSubclass):
	num_sub_calls = 0
	
	def call_me(self, caller):
		print("Apel metoda din Subclass, caller=", caller)
		LeftSubclass.call_me(self, "Subclass")
		RightSubclass.call_me(self, "Subclass")
		self.num_sub_calls += 1


if __name__ == '__main__':
	subclass_instance = Subclass()
	print(Subclass.__mro__)  #method resolution order
	subclass_instance.call_me("__main__")
	print("sub:{}, left:{}, right:{}, base:{}".format(subclass_instance.num_sub_calls, subclass_instance.num_left_calls, subclass_instance.num_right_calls, subclass_instance.num_base_calls))

		




