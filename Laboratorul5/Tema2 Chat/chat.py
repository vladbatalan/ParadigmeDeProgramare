import os
import sys
from PyQt5.QtWidgets import QWidget, QApplication, QFileDialog
from PyQt5.uic import loadUi
from PyQt5 import QtCore


######## The app ##########
def debug_trace(ui=None):
	from pdb import set_trace
	QtCore.pyqtRemoveInputHook()
	set_trace()

class chatWindow(QWidget):
	ROOT_DIR = os.path.dirname(os.path.abspath(__file__))

	def __init__(self):
		super(chatWindow, self).__init__()
		ui_path = os.path.join(self.ROOT_DIR, 'chat.ui')
		loadUi(ui_path, self)

		#attach click functions
		##self.browse_btn.clicked.connect(self.browse)

if __name__ == '__main__':
	app = QApplication(sys.argv)
	window = chatWindow()
	window.show()
	sys.exit(app.exec_())



