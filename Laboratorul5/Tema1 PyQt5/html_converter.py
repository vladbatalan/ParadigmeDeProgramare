import os
import sys
import re
import sysv_ipc
from PyQt5.QtWidgets import QWidget, QApplication, QFileDialog
from PyQt5.uic import loadUi
from PyQt5 import QtCore

######## Message queue Test #########
def send_message(message_queue, message):
	message_queue.send(message)

def receive_messages(message_queue):
	for item in message_queue.receive():
		if type(item)== bytes:
			print("received: {}".format(item.decode()))


######## The app ##########
def debug_trace(ui=None):
	from pdb import set_trace
	QtCore.pyqtRemoveInputHook()
	set_trace()
	# QtCore.pyqtRestoreInputHook()

class HTMLConverter(QWidget):
	ROOT_DIR = os.path.dirname(os.path.abspath(__file__))

	def __init__(self):
		super(HTMLConverter, self).__init__()
		ui_path = os.path.join(self.ROOT_DIR, 'html_converter.ui')
		loadUi(ui_path, self)

		#attach click functions
		self.browse_btn.clicked.connect(self.browse)
		self.to_html_btn.clicked.connect(self.to_html)
		self.to_c_btn.clicked.connect(self.to_c)
		
		#the path to the file
		self.file_path = None
	
		#the content of the file converted into html
		self.html_content = ""

	def browse(self):
		options = QFileDialog.Options()
		options |= QFileDialog.DontUseNativeDialog
		file, _ = QFileDialog.getOpenFileName(self,
                                              caption='Select file',
                                              directory='',
                                              filter="Text Files (*.txt)",
                                              options=options)
		if file:
			self.file_path = file
			self.path_line_edit.setText(file)
			print(file)

	def to_html(self):
		html_string = ""			
		if(self.file_path):
			file_object = open(self.file_path, "r")
			html_string = file_object.read()
			if self.is_html() == 0:
				file_object.seek(0,0)
				content_lines = file_object.readlines()
				html_string = ""
				#add html tags
				for line in content_lines:
					if line == content_lines[0]:
						line = "<h3>" + line[0:-1] + "</h3>"
					else:
						line = "<p>" + line[0:-1] + "</p>"
								
					html_string = html_string + line + "\n"
				###### code for writing in file directly ####
				#file_object.seek(0,0)
				#file_object.truncate()
				#file_object.write(html_string)
				
				#print(html_string)
			file_object.close()
		
		self.html_content = html_string
		self.html_result_edit.setText(html_string)	
		print("To html Clicked()")

	def to_c(self):
		try:
			message_queue = sysv_ipc.MessageQueue(-1)
			send_message(message_queue, self.html_content)
			receive_messages(message_queue)
		except sysv_ipc.ExistentialError:
			print("Message queue not initialized. Please run the C programfirst")
		print("To C Clicked()")

	def is_html(self) -> bool:
		if(self.file_path):
			#open file and read
			file_object = open(self.file_path, "r")
			content = file_object.read()

			#regex search for html
			#if we find an html tag => html exists
			search = re.search('<\s*\w[^>]*>( .*?)<\s*/\s*\w*>',content)
			file_object.close()
			if search:
				print("We got an html!")
				return 1
		print("That is not an html!")
		return 0
	


if __name__ == '__main__':
	app = QApplication(sys.argv)
	window = HTMLConverter()
	window.show()
	sys.exit(app.exec_())



