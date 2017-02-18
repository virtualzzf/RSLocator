package utils;

/**公共变量，例如代码得分路径、答案路径、驼峰字路径、组件路径、报告行数、源代码数量*/
public interface PublicValue {

	/**原始对照结果*/
	String partBM25codeResultPath="E:/研究生/毕业论文/1.6.0+1.6.1实验/实验结果/BM25网格搜索结果/参数优化/codeResult.txt";//仅仅是三分之一的结果
	String BM25codeResultPath="E:/研究生/毕业论文/1.6.0+1.6.1实验/实验结果/BM25网格搜索结果/codeResult.txt";
	String partVSMcodeResultPath="E:/研究生/毕业论文/1.6.0+1.6.1实验/实验结果/SVM网格搜索结果/权重优化/codeResult.txt";
	String VSMcodeResultPath="E:/研究生/毕业论文/1.6.0+1.6.1实验/实验结果/SVM网格搜索结果/codeResult.txt";
	/**驼峰字结果**/
	String partBM25identifierResultPath="E:/研究生/毕业论文/1.6.0+1.6.1实验/实验结果/BM25网格搜索结果/参数优化/identifierResult.txt";
	String BM25identifierResultPath="E:/研究生/毕业论文/1.6.0+1.6.1实验/实验结果/BM25网格搜索结果/identifierResult.txt";
	String partVSMidentifierResultPath="E:/研究生/毕业论文/1.6.0+1.6.1实验/实验结果/SVM网格搜索结果/权重优化/identifierResult.txt";
	String VSMidentifierResultPath="E:/研究生/毕业论文/1.6.0+1.6.1实验/实验结果/SVM网格搜索结果/identifierResult.txt";
	/**组件查询路径结果**/
	String partBM25componentResultPath="E:/研究生/毕业论文/1.6.0+1.6.1实验/实验结果/BM25网格搜索结果/参数优化/componentResult.txt";
	String BM25componentResultPath="E:/研究生/毕业论文/1.6.0+1.6.1实验/实验结果/BM25网格搜索结果/componentResult.txt";
	String partVSMcomponentResultPath="E:/研究生/毕业论文/1.6.0+1.6.1实验/实验结果/SVM网格搜索结果/权重优化/componentResult.txt";
	String VSMcomponentResultPath="E:/研究生/毕业论文/1.6.0+1.6.1实验/实验结果/SVM网格搜索结果/componentResult.txt";
	/**答案*/
	String partAnswerPath="E:/研究生/毕业论文/1.6.0+1.6.1实验/part1answer.txt";
	String answerPath="E:/研究生/毕业论文/1.6.0+1.6.1实验/report-file.csv";
	
	/**bug report行数*/
	int partReportNum=86;
	int reportNum=258;
	/**源代码行数*/
	int srcNum=2638;
	int top=10;
	
	/**RankingSVM数据*/
	String trainSetPath="./trainSet.txt";
	String testSetPath="./testSet/";
	String rankResultPath="./result/";
}
