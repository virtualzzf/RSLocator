__author__ = 'Nick Hirakawa'

from invdx import build_data_structures
from rank import score_BM25
import operator


class QueryProcessor:
	def __init__(self, queries, corpus):
		self.queries = queries
		self.index, self.dlt = build_data_structures(corpus)

	def run(self, k1_value, k2_value, b_value):
		results = []
		for query in self.queries:
			results.append(self.run_query(query, k1_value, k2_value, b_value))
		return results

	def run_query(self, query, k1_value, k2_value, b_value):
		query_result = dict()
		query_fre=dict()
		for word in query:
			if not query_fre.has_key(word):
				query_fre[word]=1
			else:
				query_fre[word]+=1
#		print query_fre
		for term in query:
			if term in self.index:
				doc_dict = self.index[term] # retrieve index entry
				for docid, freq in doc_dict.iteritems(): #for each document and its word frequency
					score = score_BM25(n=len(doc_dict), f=freq, qf=query_fre[term], r=0, N=len(self.dlt),
									   dl=self.dlt.get_length(docid), avdl=self.dlt.get_average_length(),
									   k1=k1_value, k2=k2_value, b=b_value) # calculate score
					if docid in query_result: #this document has already been scored once
						query_result[docid] += score
					else:
						query_result[docid] = score
		return query_result
		
		