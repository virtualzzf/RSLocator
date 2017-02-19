import re
import nltk
from nltk.corpus import stopwords
from head import key_words

delete_words=[u'may',u'often',u'would',u'therefore',u'found',u'could',u'soon',u'still',u'need',u'like',u'look',u'use']

def getIdentifier(inPath, outPath):
	infile=open(inPath)
	outfile=open(outPath,'w')
	
	for line in infile:
		camelCaseWords=re.findall(r'\w*[a-z][A-Z]\w*|[A-Z]\w*',line)
		lower=' '.join(camelCaseWords).lower()
		lower_list=lower.split()
		rmKey=[w for w in lower_list if not w in key_words]
		rmOri=[w for w in rmKey if not w in delete_words]
		nonstop=[w for w in rmOri if not w in stopwords.words('english')]	
		long=[w for w in nonstop if len(w)>2]
		report_string=' '.join(long)
		outfile.write(report_string+"\n")
		
	infile.close()
	outfile.close()

getIdentifier(r'.\Data\raw_report.txt', r'.\Data\identifier.txt')

