#coding=utf-8

from parse import *
from query import QueryProcessor
import operator

def main(queryPath, corpusPath, resultPath, k1Value,k2Value, bValue):
	qp = QueryParser(queryPath)
	cp = CorpusParser(corpusPath)
	qp.parse()
	queries = qp.get_queries()
	cp.parse()
	corpus = cp.get_corpus()
	proc = QueryProcessor(queries, corpus)

	k1=k1Value
	k2=k2Value
	b=bValue
	result_path=resultPath
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

main(r'.\Data\report.txt', r'.\Corpus\CodeCorpus.txt', r'.\Data\CodeResult.txt', 1.5, 100, 0.3)
main(r'.\Data\component.txt', r'.\Corpus\ComponentCorpus.txt', r'.\Data\ComponentResult.txt', 1.2, 100, 0.5)
main(r'.\Data\identifier.txt', r'.\Corpus\IdentifierCorpus.txt', r'.\Data\IdentifierResult.txt', 1.4, 200, 0.3)