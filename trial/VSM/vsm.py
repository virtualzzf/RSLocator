 #coding=utf-8
from __future__ import division
from collections import defaultdict

import math
import sys
import os

'''
Term Frequency = 1 + log(tf(t,d))
Inverse Document Frequency = log (N / tf(t,d) )
'''

class VectorSpaceModel:

    def __init__(self):
        reload(sys)  # had to deal with 'unicode' issues :/
        sys.setdefaultencoding('utf8')
        self.doc_set = {}
        self.N = -1  # corpus size
        self.dictionary = set()  # to contain all terms (i.e, words)
        self.postings = defaultdict(dict)  # key: terms, values: posting list ; posting list = doc_id, freq
        self.documentTermFrequency = defaultdict(int)  # key: terms , values: count of docs containing the key
                                                        # 'a term appear in how many docs'
        self.termFrequency = defaultdict(dict)  # key: doc id, values: list ; list = term, freq

    def initialize_terms_and_postings(self):
        self.N = len(self.doc_set)
        for doc_id in self.doc_set:
            document = self.doc_set[doc_id]
            terms = document.split()
            unique_terms = set(terms)
            self.dictionary = self.dictionary.union(unique_terms)
            for term in unique_terms:
                self.postings[term][doc_id] = terms.count(term)  # the value is the freq of the term in the document
                self.termFrequency[doc_id][term] = 1.0 + math.log(terms.count(term), 2)

    def initialize_document_frequencies(self):
        for term in self.dictionary:
            self.documentTermFrequency[term] = len(self.postings[term])

    def normalize_term_frequencies(self):
        for doc_id in self.termFrequency:
            vec = []
            for val in self.termFrequency[doc_id].values():
                vec.append(val)
            for term in self.termFrequency[doc_id]:
                self.termFrequency[doc_id][term] = self.termFrequency[doc_id][term] / self.norm_l2(vec)

    def inverse_document_frequency(self, term):
        # Return the idf of the term, if term not in dictionary then return 0.
        if term in self.dictionary:
            return math.log(self.N/self.documentTermFrequency[term], 2)
        else:
            return 0.0

    def tf_idf(self, term, doc_id):
        if term in self.dictionary:
            if term in self.termFrequency[doc_id]:
                return self.termFrequency[doc_id][term] * self.inverse_document_frequency(term)
            else:
                return 0
        else:
            return 0

    def norm_l2(self, vec):
        sum = 0.
        for val in vec:
            sum += val ** 2

        return math.sqrt(sum)

    def cosine_similarity(self, query, doc_id):
        dot_prod = 0.
        l_query = 0.
        l_doc = 0.
        cosine = 0.
        vec = []
        for term in query:
            vec.append(query.count(term))
        for term in query:
            tf_idf_q = query.count(term)/self.norm_l2(vec) * self.inverse_document_frequency(term);
            tf_idf_doc = self.tf_idf(term, doc_id)
            prod = tf_idf_q * tf_idf_doc
            dot_prod += prod
            l_query += tf_idf_q ** 2
            l_doc += tf_idf_doc ** 2

        l_query = math.sqrt(l_query)
        l_doc = math.sqrt(l_doc)

        if not (l_query == 0 or l_doc == 0):
            cosine = dot_prod / (l_query * l_doc)

        return cosine

    def search(self, line, qid, out):
        query = line.split()

        scores = sorted([(doc_id, self.cosine_similarity(query, doc_id))
                         for doc_id in self.doc_set],
                        key=lambda x: x[1],
                        reverse=True)
        # pick all the top 100% results
        bound = int(len(scores) * 1)
        if bound > 1:
            top_results = scores[0:bound]
            rank = 0
            for (doc_id, score) in top_results:
                print str(qid) + '\t' + str(rank) +'\t'+ str(doc_id) +'\t'+str(score)
                out.write(str(qid) + '\t' + str(rank) +'\t'+ str(doc_id) +'\t'+str(score)+'\n')
                out.flush()
                rank += 1
            print str(qid) + " write successfully!"
        else:
            print('There are no documents to search for !\n'
                  'Please make sure you provided the correct path to self.initialize_docs(..)')

    def initialize_docs(self, path):
        self.doc_set = {}
        i = 1
        # Read all the content into doc_set
        file = open(path)
        for line in file:
            self.doc_set[i] = line
            i += 1
        file.close()

    def begin(self):
        print '... begin VSM ...'

        print "initialize_docs"
        self.initialize_docs(path='./corpus.txt')
        print "initialize_terms_and_postings"
        self.initialize_terms_and_postings()
        print "initialize_document_frequencies"
        self.initialize_document_frequencies()
        print "normalize_term_frequencies"
        self.normalize_term_frequencies()

        query_file=open(r'./query.txt')
        query_id=0
        out_file=open('./result.txt','w')
        for line in query_file:
            print query_id
            self.search(line, query_id, out_file)
            query_id += 1
        query_file.close()
        out_file.close()

if __name__ == '__main__':
    VectorSpaceModel().begin()
