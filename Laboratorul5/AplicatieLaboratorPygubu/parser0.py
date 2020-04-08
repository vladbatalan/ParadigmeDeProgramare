import os
import tkinter as tk
import pygubu


class Parser:
	ROOT_DIR = os.path.dirname(os.path.abspath(__file__))
	def __init__(self, master):
		self.master = master
		# 1: Create a builder
		self.builder = builder = pygubu.Builder()

		# 2: Load an ui file
		ui_path = os.path.join(self.ROOT_DIR, 'parser0.ui')
		builder.add_from_file(ui_path)

		# 3: Create the widget using a master as parent
		self.parser = builder.get_object('Parser', master)
		

		#result bar
		self.result_text =  self.builder.get_object('result_text')

		#the add button for a new list
		self.add_list_btn = self.builder.get_object('add_list_btn')
		
		#the text for integer_list
		self.integer_list_text = self.builder.get_object('integer_list_text')

		#the added buttons
		self.filter_odd_btn = self.builder.get_object('filter_odd_btn')
		self.filter_primes_btn = self.builder.get_object('filter_primes_btn')
		self.sum_numbers_btn = self.builder.get_object('sum_numbers_btn')

		#add commands for buttons
		self.add_list_btn['command'] = self.add_list
		self.filter_odd_btn['command'] = self.filter_odd
		self.filter_primes_btn['command'] = self.filter_primes
		self.sum_numbers_btn['command'] = self.sum_numbers
		

		#connect
		builder.connect_callbacks(self)
		#no list for the momment
		self.integer_list = None

	def add_list(self):

		result = self.integer_list_text.get("1.0", tk.END)
		result = result.strip().replace(' ', '')
		result = [int(item) for item in result.split(',')]
		
		self.integer_list = result
		self.change_result_text()
		print(result)
	def filter_odd(self):
		if self.integer_list == None:
			print("List is empty!")
			return
		print("Filtering the odd numbers")
		for number in self.integer_list:
			if number % 2 == 0:
				self.integer_list.remove(number)
		self.change_result_text()
	def filter_primes(self):
		if self.integer_list == None:
			print("List is empty!")
			return
		print("Filtering the prime numbers")
		
		for item in self.integer_list:
			if item == 1:
				self.integer_list.remove(item)
				continue
			if item == 2:
				continue
			#check if it is prime
			#suppose it is
			ok = 1
			if item % 2 == 0:
				ok = 0
			div = 3
			while div*div < item:
				if item % div == 0:
					ok = 0
				div = div + 2
			if ok == 0:
				self.integer_list.remove(item)
		self.change_result_text()
	def sum_numbers(self):
		if self.integer_list == None:
			print("List is empty!")
			return
		print("Summing all the numbers")
		
		total_sum = sum(self.integer_list)
		self.integer_list.clear()
		self.integer_list.append(total_sum)
		self.change_result_text()
	
	def change_result_text(self):
		#construct text for display in msg
		txt = ""
		for item in self.integer_list:
			if txt != "":
				txt += ", "
			txt += str(item)
		#replace text 
		self.result_text.delete(1.0, tk.END)
		self.result_text.insert(1.0, "Result: " + txt)		



if __name__ == '__main__':
	root = tk.Tk()
	root.title('Exemplul 1 cu Tkinter si PyGubu')
	app = Parser(root)
	root.mainloop()




