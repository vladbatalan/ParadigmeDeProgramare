class ContactList(list):
	def search(self, name):
		matching_contacts = []
		for contact in self:
			if name in contact.name:
				matching_contacts.append(contact)
		return matching_contacts

class Contact:
	def __init__(self, name, email):
		self.name = name
		self.email = email
	
	def __repr__(self):
		return "Contact({}, {})".format(self.name, self.email)
	
class Agenda:
	all_contacts = ContactList()
	
	def add_contact(self, contact):
		self.all_contacts.append(contact)
	
	def print_agenda(self):
		for contact in self.all_contacts:
			print(contact)

class Friend(Contact):
	def __init__(self, name, email, phone):
		super().__init__(name, email)
		self.phone = phone
	def __repr__(self):
		return "Friend: ({}, {}, {})".format(self.name, self.email, self.phone)


if __name__ == '__main__':
	agenda = Agenda()
	agenda.add_contact(Contact('Ion Popescu', 'ion_popescu@gmail.com'))
	agenda.add_contact(Contact('Ion Barbu', 'john_barbu@gmail.com'))
	agenda.add_contact(Friend('Pitagora', 'pitagorica@math.com', '0766543423'))
	agenda.add_contact(Friend('Thales', 'similar_triangleist@math.com', '0789049433'))
	agenda.print_agenda()








