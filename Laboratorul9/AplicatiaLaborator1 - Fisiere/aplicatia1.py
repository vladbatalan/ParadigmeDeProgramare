import copy
from abc import ABCMeta


class File(metaclass = ABCMeta):
	title = ""
	author = ""
	paragraphs = []

	def read_from_stdin(self):
		self.title = input("Title: ")
		self.author = input("Author: ")
		number_of_paragraphs = int(input("Number of paragraphs: "))
		for index in range(0, number_of_paragraphs):
			self.paragraphs.append(input("Paragraph: "))


class HTMLFile(File):

	def print_html(self):
		print("<h2 allign=\"center\">{}</h2>".format(self.title))
		print("<h3 allign=\"right\">{}</h2>".format(self.author))

		for paragraph in self.paragraphs:
			print("<p>{}</p>".format(paragraph))


class JSONFile(File):

	def print_json(self):
		print("{")
		print("\t\"title\": \"{}\"".format(self.title))
		print("\t\"author\": \"{}\"".format(self.author))
		print("\t\"paragraphs\": [")

		for index in range(0, len(self.paragraphs)):
			string = "\t\t\"{}\"".format(self.paragraphs[index])
			if index != len(self.paragraphs) - 1:
				string += ","
			print(string)
		print("\t\t]")
		print("}")


class TextFile(File, metaclass = ABCMeta):
	template = ""

	def print_text(self):
		print("Template: {}".format(self.template))
		print("Titlu: {}".format(self.title))
		print("Autor: {}".format(self.author))
		print("Continut:")
		for paragraph in self.paragraphs:
			print(paragraph)

	def copy(self):
		return copy.copy(self)


class ArticleTextFile(TextFile):

	def __init__(self):
		self.template = "Article"

	def print_text(self):
		print("\t", self.title)
		print("\t  by ", self.author)
		for paragraph in self.paragraphs:
			print(paragraph)


class BlogTextFile(TextFile):
	def __init__(self):
		self.template = "Blog"

	def print_text(self):
		print(self.title)
		for paragraph in self.paragraphs:
			print(paragraph)
		print("")
		print("Written by ", self.author)


class FileFactory:
	def factory(self, file_type):
		if file_type == "HTML":
			return HTMLFile()
		if file_type == "JSON":
			return JSONFile()
		if file_type == "Blog":
			return BlogTextFile()
		if file_type == "Article":
			return ArticleTextFile()

		return None


if __name__ == "__main__":
	myFactory = FileFactory()

	myFile1 = myFactory.factory("HTML")
	myFile2 = myFactory.factory("JSON")
	myFile3 = myFactory.factory("Blog")
	myFile4 = myFactory.factory("Article")

	myFile4.read_from_stdin()

	myFile4.print_text()


