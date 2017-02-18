#coding=utf-8

import nltk
import re
from nltk.corpus import stopwords
from camelCaseSplit import camel_to_space
from head import del_ori,del_trans,key_words

infile=open(r'.\bugReport.txt')
outfile=open(r'.\query.txt','w')

for line in infile:
	report_noncamel=camel_to_space(line)
	report_lower=report_noncamel.lower()
	report_list=re.findall(r'[A-Za-z0-9]+',report_lower)
	report_rmKey=[w for w in report_list if not w in key_words]
	report_rmOri=[w for w in report_rmKey if not w in del_ori]
	report_nonstop=[w for w in report_rmOri if not w in stopwords.words('english')]	
	porter = nltk.PorterStemmer()
	report_stem=[porter.stem(t) for t in report_nonstop]
	report_rmTran=[w for w in report_stem if not w in del_trans]
	report_long=[w for w in report_rmTran if len(w)>2]
	report_string=' '.join(report_long)
	outfile.write(report_string+"\n")

infile.close()
outfile.close()
