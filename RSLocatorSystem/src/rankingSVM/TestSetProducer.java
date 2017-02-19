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

public class TestSetProducer {

	private FeatureMatrix[][] matrix;
	private int reportNum;
	private int srcNum;
	private DecimalFormat f=new DecimalFormat("0.0000000");
	
	public TestSetProducer(String codePath, String componentPath, String identifierPath,
			int rNum,int sNum){
		
		reportNum=rNum;
		srcNum=sNum;
		matrix=new FeatureMatrix[reportNum][srcNum];
		
		loadData(codePath, componentPath, identifierPath,  matrix);

	}

	public void loadData(String codePath, String componentPath, String identifierPath,
			FeatureMatrix[][] matrix){
		
		File codeFile=new File(codePath), componentFile=new File(componentPath),identifierFile=new File(identifierPath);
		String line;
		int query=0,src=0;
		double score,max=1.0;
		
		for(query=0;query<matrix.length;query++){
			for(src=0;src<matrix[0].length;src++)
				matrix[query][src]=new FeatureMatrix(0.0,0.0,0.0);
		}

		try {
			BufferedReader codeIn=new BufferedReader(new InputStreamReader(new FileInputStream(codeFile),"utf-8"));
			BufferedReader compoentIn=new BufferedReader(new InputStreamReader(new FileInputStream(componentFile),"utf-8"));
			BufferedReader identifierIn=new BufferedReader(new InputStreamReader(new FileInputStream(identifierFile),"utf-8"));
			while(true){
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
				}
				else{
					break;
				}
			}
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
				}
				else{
					break;
				}
			}
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
	
	public void writeToFile(String testPath){
		
		PrintWriter pw1=null,pw2=null;
		File f2;
				
		for(int i=0;i<reportNum;i++){
			String path=testPath;
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

	private void printTest(PrintWriter pw, int i, int j){
		pw.print("1 qid:"+(i+1)+" ");
		pw.print("1:"+f.format(matrix[i][j].getCodeScore())+" ");
		pw.print("2:"+f.format(matrix[i][j].getComponentScore())+" ");
		pw.print("3:"+f.format(matrix[i][j].getIdentifierScore()));
		pw.println();	
		pw.flush();
	}
	
	public static void main(String[] args) {
		TestSetProducer svm=new TestSetProducer("./Data/CodeResult.txt", 
				"./Data/ComponentResult.txt", 
				"./Data/IdentifierResult.txt",
				1, 2638);
		svm.writeToFile("./RankingsVM/testSet.txt");
	}
}
