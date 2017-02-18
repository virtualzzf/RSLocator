#coding=utf-8
import re
import nltk

def sub_split(out_word):
	word=out_word.strip()

	#多个大写（专有名词)，其他为小写形式(以s结尾的负数形式除外)，予以分割
	split_pattern =re.compile(r'[A-Z]{3,}[a-rt-z]\w*')
	split_match= split_pattern.match(word)
	result_word=""
	if split_match:
		for i in range(len(word)-2):
			if(word[i].isupper() and word[i+1].isupper() and word[i+2].islower()):
				result_word+=word[i]+" "
			else:
				result_word+=word[i]
		result_word+=word[-2]
		result_word+=word[-1]
	else:
		result_word=word
	return result_word

def camel_to_space(camel_line):
	space_line=""
	for i in range(len(camel_line)-1):
		if(camel_line[i].islower() and camel_line[i+1].isupper()):
			space_line+=camel_line[i]+" "
		else:
			space_line+=camel_line[i]
	space_line+=camel_line[-1]
	element_list=re.findall(r'[A-Za-z0-9]+',space_line)
	
	word_list=[]
	for element in element_list:
		word_list.append(sub_split(element))
	result=" ".join(word_list)
	return result