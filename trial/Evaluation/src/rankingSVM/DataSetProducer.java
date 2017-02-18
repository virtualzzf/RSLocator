package rankingSVM;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import dataStructure.FeatureMatrix;

import utils.Initialize;
import utils.LoadData;
import utils.PublicValue;

/**用于生成数据集文件，包括训练集和测试集*/
public class DataSetProducer {

	/**三项得分矩阵二维数组*/
	private FeatureMatrix[][] matrix;
	/**正样本数组*/
	private Set[] posSample;
	/**负样本数组*/
	private Set[] negSample;
	/**查询数量*/
	private int reportNum;
	private int srcNum;
	private DecimalFormat f=new DecimalFormat("0.0000000");
	
	/**用于从BM25结果中载入数据，形成符合RankingSVM格式的文件，此处共三个特征：代码得分、组件得分、标识符得分，以及答案*/
	public DataSetProducer(String codePath, String componentPath, String identifierPath, String answerPath,
			int rNum,int sNum){
		
		reportNum=rNum;
		srcNum=sNum;
		/**三项得分矩阵二维数组*/
		matrix=new FeatureMatrix[reportNum][srcNum];
		/**正样本数组*/
		posSample=new HashSet[reportNum];
		/**负样本数组*/
		negSample=new HashSet[reportNum];
		
		loadData(codePath, componentPath, identifierPath, answerPath, matrix, posSample, negSample);

	}
	/**生成记录三个分数的二维数组*/
	public void loadData(String codePath, String componentPath, String identifierPath, String answerPath,
			FeatureMatrix[][] matrix, Set[] positive, Set[] negative){
		
		File codeFile=new File(codePath), componentFile=new File(componentPath),identifierFile=new File(identifierPath);
		String line;
		int query=0,src=0;
		double score,max=1.0;
		int[] codeRest=new int[reportNum], componetRest=new int[reportNum], identifierRest=new int[reportNum];
		
		/**初始化过程*/
		for(int i=0;i<reportNum;i++){
			positive[i]=new HashSet();
			negative[i]=new HashSet();
		}
		LoadData.loadAnswer(answerPath, positive, reportNum);
		for(query=0;query<matrix.length;query++){
			for(src=0;src<matrix[0].length;src++)
				matrix[query][src]=new FeatureMatrix(0.0,0.0,0.0);
		}
		Initialize.initArray(codeRest, 2);
		Initialize.initArray(componetRest, 2);
		Initialize.initArray(identifierRest, 2);
		
		/**读数据过程*/
		try {
			BufferedReader codeIn=new BufferedReader(new InputStreamReader(new FileInputStream(codeFile),"utf-8"));
			BufferedReader compoentIn=new BufferedReader(new InputStreamReader(new FileInputStream(componentFile),"utf-8"));
			BufferedReader identifierIn=new BufferedReader(new InputStreamReader(new FileInputStream(identifierFile),"utf-8"));
			/**读取代码相似度得分*/
			while(true){
				//文件格式是，0:query编号（从0开始）、1:排名编号（从0开始）、2:文件编号（从1开始）、3:得分
				line=codeIn.readLine();
				if(line!=null){
					String[] values=line.split("\t");
					query=Integer.valueOf(values[0]);
					if(Integer.valueOf(values[1])==0){
						if(Double.valueOf(values[3])!=0.0000000){
							max=Double.valueOf(values[3]);
						}else{
							max=1.0;
						}
					}
					src=Integer.valueOf(values[2]);
					score=Double.valueOf(values[3])/max;
					matrix[query][src-1].setCodeScore(score);
					if(codeRest[query]>0){
						if(!positive[query].contains(src)&&!negative[query].contains(src)){
							negative[query].add(src);
							codeRest[query]--;
						}
					}
				}
				else{
					break;
				}
			}
			/**读取组件相似度得分*/
			while(true){
				line=compoentIn.readLine();
				if(line!=null){
					String[] values=line.split("\t");
					query=Integer.valueOf(values[0]);
					if(Integer.valueOf(values[1])==0){
						if(Double.valueOf(values[3])!=0.0000000){
							max=Double.valueOf(values[3]);
						}else{
							max=1.0;
						}
					}
					src=Integer.valueOf(values[2]);
					score=Double.valueOf(values[3])/max;
					matrix[query][src-1].setComponentScore(score);
					if(componetRest[query]>0){
						if(!positive[query].contains(src)&&!negative[query].contains(src)){
							negative[query].add(src);	
							componetRest[query]--;
						}
					}
				}
				else{
					break;
				}
			}
		/**读取标识符相似度得分*/
		while(true){
			line=identifierIn.readLine();
			if(line!=null){
				String[] values=line.split("\t");
				query=Integer.valueOf(values[0]);
				if(Integer.valueOf(values[1])==0){
					if(Double.valueOf(values[3])!=0.0000000){
						max=Double.valueOf(values[3]);
					}else{
						max=1.0;
					}
				}
				src=Integer.valueOf(values[2]);
				score=Double.valueOf(values[3])/max;
				matrix[query][src-1].setIdentifierScore(score);
				if(identifierRest[query]>0){
					if(!positive[query].contains(src)&&!negative[query].contains(src)){
						negative[query].add(src);	
						identifierRest[query]--;
					}
				}
			}
			else{
				break;
			}
		}
		} catch (UnsupportedEncodingException e) {
			System.out.println("UnsupportedEncodingException");
		} catch (FileNotFoundException e) {
			System.out.println("FileNotFoundException");
		}catch (IOException e){
			System.out.println("IOException");}
	}
	
	/**输出结果*/
	public void writeToFile(String trainPath, String testPath){
		
		PrintWriter pw1=null,pw2=null;
		File f1=new File(trainPath),f2;
		
		try {
			pw1=new PrintWriter(f1);
		} catch (FileNotFoundException e) {
			System.out.println("打不开文件");}

		/**生成训练集*/
		for(int i=0;i<reportNum;i++){
			printTrain(pw1, i);
		}
		
		/**生成测试集*/
		for(int i=0;i<reportNum;i++){
			String path=testPath+i+".dat";
			f2=new File(path);
			try {
				pw2=new PrintWriter(f2);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			for(int j=0;j<srcNum;j++){
					printTest(pw2,i,j);
			}
		}
	}
	/**私有方法，将完整测试集数据写入文件*/
	private void printTest(PrintWriter pw, int i, int j){
		if(posSample[i].contains(j+1)){
			pw.print("2 qid:"+(i+1)+" ");
			pw.print("1:"+f.format(matrix[i][j].getCodeScore())+" ");
			pw.print("2:"+f.format(matrix[i][j].getComponentScore())+" ");
			pw.print("3:"+f.format(matrix[i][j].getIdentifierScore()));
			pw.println();
		}
		else{
			pw.print("1 qid:"+(i+1)+" ");
			pw.print("1:"+f.format(matrix[i][j].getCodeScore())+" ");
			pw.print("2:"+f.format(matrix[i][j].getComponentScore())+" ");
			pw.print("3:"+f.format(matrix[i][j].getIdentifierScore()));
			pw.println();	
		}
		pw.flush();
	}
	/**私有方法，将训练集正负样本Set的数据写入文件*/
	private void printTrain(PrintWriter pw, int i){
		
		for(Iterator posIterator = posSample[i].iterator();posIterator.hasNext();){
			int j=(Integer) posIterator.next()-1;
			pw.print("2 qid:"+(i+1)+" did:"+(j+1)+" ");
			pw.print("1:"+f.format(matrix[i][j].getCodeScore())+" ");
			pw.print("2:"+f.format(matrix[i][j].getComponentScore())+" ");
			pw.print("3:"+f.format(matrix[i][j].getIdentifierScore()));
			pw.println();
			pw.flush();
	    } 
			
		for(Iterator negIterator = negSample[i].iterator();negIterator.hasNext();){
			int j=(Integer) negIterator.next()-1;
			pw.print("1 qid:"+(i+1)+" did:"+(j+1)+" ");
			pw.print("1:"+f.format(matrix[i][j].getCodeScore())+" ");
			pw.print("2:"+f.format(matrix[i][j].getComponentScore())+" ");
			pw.print("3:"+f.format(matrix[i][j].getIdentifierScore()));
			pw.println();
			pw.flush();
	    } 
	}
	
	public static void main(String[] args) {

		DataSetProducer svm=new DataSetProducer(PublicValue.VSMcodeResultPath, 
				PublicValue.VSMcomponentResultPath, 
				PublicValue.VSMidentifierResultPath,
				PublicValue.answerPath, PublicValue.reportNum,PublicValue.srcNum);
		svm.writeToFile(PublicValue.trainSetPath,PublicValue.testSetPath);
	}
}
