#RSLocator
Source code and data set of my thesis submitted to Nanjing University of Posts and Telecommunications for the degree of master of engineering

#Prototype System
The file folder "RSLocatorSystem" is the prototype system showed in my thesis. It's a MyEclipse project. You can import it into your Eclipse and edit it. To run it, you should implement NLTK(http://www.nltk.org/) first. 

#Trial
The file folder "trial" contains data set and source code of trials in my thesis. 

(1)Query set and Corpus of Apache Spark are in the "SparkDataSet". Bug reports of 1.6.0 and 1.6.1 are in the file "bugReport.txt", and source code of 1.5.2 is in the file "sourceCode.txt". The corpus of file paths is the file "path.txt". The three corpus mentioned above are in the same order. The file "codeQuery.txt", "codeCorpus.txt", "componentQuery.txt", "componentCorpus.txt", "identifierQuery.txt", "identifierCorpus.txt" are preprocessed queries and corpus, which are able to be used to conduct experiments directly.

(2)The preprocess scipts are in the "preprocess" file folder, which is writeen by Python. To run these scripts, you need to implement NLTK. The "reportPreprocess.py" is used to preprocess bug reports, and "srcPreprocess.py" is used to preprocess source code file. In this trial, all the reports are in one file, in which one report is in a line. So does the source code.

(3)"BM25" and "VSM" are the information retrieve models. Befor running it, you need to create two files named "query.txt" and "corpus.txt". Thankss to uyaseen(https://github.com/uyaseen/vsm) and nhirakawa(https://github.com/nhirakawa/BM25).

(4)The Ranking SVM tool is in the file folder "ranking SVM". Its author is  Thorsten Joachims(http://www.cs.cornell.edu/people/tj/svm_light/svm_rank.html). Before ranking, test set should be created. So you should run "DataSetProducer.java" in the Evaluation/src/ranking SVM. In my trial, I used the linux version. To create test sets in correct format, you should run a shell script similar to "rank.sh". In the prototype system, windows version is used.

(5)To evalute the performance of trial, you need a small programme, and it's in the "Evalution". It's writeen in Java.To run it, you need to change some variables, such as file path, in the "PublicValue.java".
