#coding=utf-8

from parse import *
from query import QueryProcessor
import operator

def main():
	qp = QueryParser(filename=r'..\text\query.txt')
	cp = CorpusParser(filename=r'..\text\corpus.txt')
	qp.parse()
	queries = qp.get_queries()
	cp.parse()
	corpus = cp.get_corpus()
	proc = QueryProcessor(queries, corpus)

	k1=1.2
	k2=100
	b=0.5
	result_path=r'.\Result.txt'
	out=open(result_path,'w')

	results = proc.run(k1, k2, b)
	qid = 0
	for result in results:
		sorted_x = sorted(result.iteritems(), key=operator.itemgetter(1))
		sorted_x.reverse()
		index = 0
		#这里可指定输出topK个
		for i in sorted_x[:]:
			out.write(str(qid)+'\t'+str(index)+'\t'+i[0]+'\t'+str(i[1])+'\n')
			out.flush()
			index += 1
		qid += 1
		print qid
	out.close()

if __name__ == '__main__':
	main()