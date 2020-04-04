#tema acasa
import os
import math
import struct

class GenericFile:
	def get_path(self) -> str:
		pass
	def get_freq(self) -> str:
		pass
	def get_info(self) ->str:
		pass

class TextASCII(GenericFile):
	def __init__(self, path_absolut, frecvente):
		self.path_absolut = path_absolut
		self.frecvente = frecvente
	def get_path(self) -> str:
		return self.path_absolut
	def get_frec(self) -> str:
		return self.frecvente
	def get_info(self) ->str:
		strg = self.get_path() + "\n\tis ASCII text"
		return strg

class TextUNICODE(GenericFile):
	def __init__(self, path_absolut, frecvente):
		self.path_absolut = path_absolut
		self.frecvente = frecvente
	def get_path(self) -> str:
		return self.path_absolut
	def get_frec(self) -> str:
		return self.frecvente
	def get_info(self) ->str:
		strg = self.get_path() + "\n\tis UNICODE text"
		return strg


class Binary(GenericFile):
	def __init__(self, path_absolut, frecvente):
		self.path_absolut = path_absolut
		self.frecvente = frecvente
	def get_path(self) -> str:
		return self.path_absolut
	def get_frec(self) -> str:
		return self.frecvente
	def get_info(self) ->str:
		strg = self.get_path() + "\n\tis Binary file"
		return strg

class XMLFile(TextASCII):
	def __init__(self, path_absolut, frecvente, first_tag):
		TextASCII.__init__(self, path_absolut, frecvente)
		self.first_tag = first_tag
	def get_first_tag(self) -> str:
		return self.first_tag
	def get_info(self) ->str:
		strg = self.get_path() + "\n\tis XML file with:\n"
		strg += "\tfirst_tag = {}".format(self.first_tag)
		return strg

class BMP(Binary):
	def __init__(self, path_absolut, frecvente, width, height, bpp):
		super().__init__(path_absolut, frecvente)
		self.width = width
		self.height = height
		self.bpp = bpp
	def show_info(self):
		print("width:{}, height:{}, bpp:{}".format(self.width, self.height, self.bpp))
	def get_info(self) ->str:
		strg = self.get_path() + "\n\tis BMP file with:\n"
		strg+= "\twidth = " + str(self.width) + " height = " + str(self.height) + " bpp = " + str(self.bpp)
		return strg





def get_frequence(content) -> {}:
	frecv = {}
	for i in range(0, 256):
		frecv[i] = 0
	for ch in content:
		frecv[ch] += 1
	return frecv

def is_UNICODE(frecv, content_len) -> bool:
	if frecv[0]/content_len > 0.3:
		return 1
	return 0
	## the BOMs we know, by their pattern
	#bomDict={ # bytepattern : name
	#	(0x00, 0x00, 0xFE, 0xFF) : "utf_32_be",        	
	#	(0xFF, 0xFE, 0x00, 0x00) : "utf_32_le",
	#	(0xFE, 0xFF, None, None) : "utf_16_be", 
	#	(0xFF, 0xFE, None, None) : "utf_16_le", 
	#	(0xEF, 0xBB, 0xBF, None) : "utf_8",
	#}

	## go to beginning of file and get the first 4 bytes
	#oldFP = fp.tell()
	#fp.seek(0)
	#magic = fp.read(4)
	#b = bytearray(magic)
	## try bom detection using 4 bytes, 3 bytes, or 2 bytes
	#bomDetection = bomDict.get((b[0], b[1], b[2], b[3]))
	#if not bomDetection :
	#	bomDetection = bomDict.get((b[0], b[1], b[2], None))
	#	if not bomDetection :
	#		bomDetection = bomDict.get((b[0], b[1], None, None))

	## if BOM detected, we're done :-)
	#if bomDetection :
	#	fp.seek(oldFP)
	#	return 1
	#print("Unicode test: ", frecv[0]/content_len)
	#if frecv[0]/content_len > 0.30:
		#return 1
	#return 0

def is_ASCII(frecv, content_len) -> bool:
	#trecem prin elementele de frecventa mare
	high_frec = [9, 10, 13]
	for i in range(32, 128):
		high_frec.append(i)
	
	high_frec_prob = 0
	for ch in frecv:
		if ch in high_frec:
			high_frec_prob += frecv[ch] / content_len
	low_frec_prob = 1 - high_frec_prob
	
	high_frec_prob /= len(high_frec)
	low_frec_prob /= (256 - len(high_frec))
	print("Ascii test: {} - {}".format(high_frec_prob, low_frec_prob))
	if high_frec_prob - low_frec_prob > 0.001:
		return 1
	return 0
		
def is_BINARY(frecv, content_len) -> bool:
	ideal_prob = content_len/256
	
	#check if uniform distribution
	#parcurgem fiecare frecventa si vedem cat de departe este de valoarea idiala
	delta = 0
	for elem in frecv:
		delta += abs(frecv[elem] - ideal_prob)/content_len
	#print("delta = ", delta)
	if delta < 1:
		return 1
	return 0
	
def is_BMP(bmp) -> bool:
	magic = bmp.read(4).decode("UTF-8")
	print("Magic is: ",str(magic))
	if str(magic) != b'BM':
		print("is not a BMP file")
		return 0
	return 1
	#print('Type:', bmp.read(4).decode('utf-8'))
	#print('Size: %s' % struct.unpack('I', bmp.read(4)))
	#print('Reserved 1: %s' % struct.unpack('H', bmp.read(2)))
	#print('Reserved 2: %s' % struct.unpack('H', bmp.read(2)))
	#print('Offset: %s' % struct.unpack('I', bmp.read(4)))

	#print('DIB Header Size: %s' % struct.unpack('I', bmp.read(4)))
	#print('Width: %s' % struct.unpack('I', bmp.read(4)))
	#print('Height: %s' % struct.unpack('I', bmp.read(4)))
	#print('Colour Planes: %s' % struct.unpack('H', bmp.read(2)))
	#print('Bits per Pixel: %s' % struct.unpack('H', bmp.read(2)))
	#print('Compression Method: %s' % struct.unpack('I', bmp.read(4)))
	#print('Raw Image Size: %s' % struct.unpack('I', bmp.read(4)))
	#print('Horizontal Resolution: %s' % struct.unpack('I', bmp.read(4)))
	#print('Vertical Resolution: %s' % struct.unpack('I', bmp.read(4)))
	#print('Number of Colours: %s' % struct.unpack('I', bmp.read(4)))
	#print('Important Colours: %s' % struct.unpack('I', bmp.read(4)))
	#return 1

def is_XML(content) -> bool:
	#search for < and > and if there are more than lines
	#file must be xml
	print('XML test:')
	nrBrackets = 0
	for ch in content:
		if ch == ord('<') or ch == ord('>'):
			nrBrackets += 1
	print("nr brackets = ",nrBrackets)
	
	nrLines = len(content.splitlines())
	print('Total lines: ', nrLines)
	if nrBrackets/2 > nrLines:
		print("true")
		return 1
	return 0

def XML_first_tag(content) ->str:
	start = content.find(ord('<'))
	if content[start + 1] == ord('?'):
		start = content.find(ord('<'), start+1)
	end = content.find(ord('>'), start)
	
	tag1 = str(content[start+1 : end]).split(' ')[0]
	return tag1
		

def check_file_type(file_path) -> GenericFile:
	
	genFile = 0
	print("\nopen file: ", file_path)
	f = open(file_path, 'rb')
	##try:
		
	print("Opening ", file_path)
	content = f.read()
	frecv = get_frequence(content)
	
	if is_ASCII(frecv, len(content)):
		if is_XML(content):
			print('is_xml')
			first_tag = XML_first_tag(content) 
			genFile = XMLFile(file_path, frecv, first_tag)
		else:
			print('is_ascii')
			genFile = TextASCII(file_path, frecv)	
			
	if is_UNICODE(frecv, len(content)):
		print('is_unicode')
		genFile = TextUNICODE(file_path, frecv)
	if is_BINARY(frecv, len(content)):
		if is_BMP(f):
			print('is_bmp')
			genFile = BMP(file_path, frecv, -1, -1, -1)
		else:
			print('is_binary')
			genFile = Binary(file_path, frecv)
	##finally:
	f.close()
	return genFile;
	

if __name__ == '__main__':
	genFileList = []
	unknownList = []	

	ROOT_DIR = os.path.dirname(os.path.abspath(__file__))
	for root, subdirs, files in os.walk(ROOT_DIR):
		for file in os.listdir(root):
			file_path = os.path.join(root, file)
			newFile = check_file_type(file_path)
			if(newFile != 0):
				genFileList.append(newFile)
			else:
				unknownList.append(file_path)
	for string in unknownList:
		print("Unknown File: ", string, "\n")		


	for index in range(0, len(genFileList)):
		print("[", index, "]: ")
		print(genFileList[index].get_info())
			


