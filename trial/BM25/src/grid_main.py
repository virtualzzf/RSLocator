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
	
	for k1_value in range(12,18):
		k1=k1_value/10.0
		for k2 in range(100,600,100):
			for b_value in range(3,8):
				b=b_value/10.0
				print 'k1='+str(k1)+' k2='+str(k2)+' b='+str(b)
				result_path='E:\\GridSearchBM25Component\\'+str(k1)+' '+str(k2)+' '+str(b)+' '+'Result.txt'
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
				out.close()

if __name__ == '__main__':
	main()