package rankingSVM;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dataStructure.TargetFile;

import utils.Evaluation;
import utils.LoadData;
import utils.PublicValue;
import utils.Sort;

public class EvaluateResult {

	private int reportNum=PublicValue.reportNum;
	private int srcNum=PublicValue.srcNum;
	private int top=PublicValue.top;
	private List<Integer>[] answer=new ArrayList[reportNum];
	private int[][] fileMatrix=new int[reportNum][top];
	private String filePath, headPath;
	
	public EvaluateResult(String resultPath, String answerPath){
		headPath=resultPath;
		LoadData.loadAnswer(answerPath, answer, reportNum);
	}
	/**读入数据*/
	private void read(){
		String line;
		Double score;
		TargetFile a[]=new TargetFile[srcNum];
		for(int i=0;i<reportNum;i++){
			filePath=headPath+"result"+String.valueOf(i)+".txt";
			try {
				BufferedReader in=new BufferedReader(new InputStreamReader(new FileInputStream(filePath),"utf-8"));
				for(int fileIndex=0;fileIndex<srcNum;fileIndex++){
					line=in.readLine().trim();
					score=Double.parseDouble(line);
					a[fileIndex]=new TargetFile(fileIndex+1,score);
				}
				Sort.bubble(a);
				for(int j=0;j<top;j++){
					fileMatrix[i][j]=a[j].getIndex();
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();}
			  catch (FileNotFoundException e) {
				e.printStackTrace();} 
			  catch (IOException e) {
				e.printStackTrace();}
		}
	}
	
	/**对结果进行评估*/
	private void evaluate(){
		Evaluation evl=new Evaluation(fileMatrix,answer);
		evl.showResult();
	}
	
	public static void main(String[] args) {
		EvaluateResult a=new EvaluateResult(PublicValue.rankResultPath, PublicValue.answerPath);
		a.read();
		a.evaluate();
	}
}
