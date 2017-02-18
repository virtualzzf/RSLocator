import nltk
import re
from nltk.corpus import stopwords
from camelCaseSplit import camel_to_space
from head import key_words

del_verb=[u'do']
del_other=[u'often',u'must',u'may',u'this',u'would',u'need',u'look',u'like',u'therefore',
		u'found',u'soon',u'could',u'since']
		
infile=open(r'.\filename+srcCode.txt')
outfile=open(r'.\corpus.txt','w')

for line in infile:
	src_noncamel=camel_to_space(line)
	src_lower=src_noncamel.lower()
	src_list=re.findall(r'[A-Za-z0-9]+',src_lower)
	src_rmKey=[w for w in src_list if not w in key_words]
	src_rmOther=[w for w in src_rmKey if not w in del_other]
	src_nonstop=[w for w in src_rmOther if not w in stopwords.words('english')]	
	porter = nltk.PorterStemmer()
	src_stem=[porter.stem(t) for t in src_nonstop]
	src_delVeb=[w for w in src_stem if not w in del_verb]
	src_long=[w for w in src_delVeb if len(w)>2]
	src_string=' '.join(src_long)
	outfile.write(src_string+"\n")
infile.close()
outfile.close()
