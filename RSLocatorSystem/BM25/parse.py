__author__ = 'Nick Hirakawa'

import re


class CorpusParser:

	def __init__(self, filename):
		self.filename = filename
		self.corpus = dict()

	def parse(self):
		src=open(self.filename)
		src_index=1
		for x in src:
			text = x.split()
			docid = str(src_index)
			self.corpus[docid] = text
			src_index+=1
		src.close

	def get_corpus(self):
		return self.corpus


class QueryParser:

	def __init__(self, filename):
		self.filename = filename
		self.queries = []

	def parse(self):
		with open(self.filename) as f:
			lines = ''.join(f.readlines())
		self.queries = [x.rstrip().split() for x in lines.split('\n')[:]]

	def get_queries(self):
		return self.queries


if __name__ == '__main__':
	qp = QueryParser('text/queries.txt')
	print qp.get_queries()