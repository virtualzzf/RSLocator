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

import utils.LoadData;
import utils.PublicValue;
import utils.Sort;

public class ReturnResult {

	private int srcNum=2638;
	private int top=10;
	private int[] fileMatrix=new int[top];
	private String filePath;
	
	public ReturnResult(String resultPath){
		filePath=resultPath;
	}
	/**读入数据
	 * @return */
	public int[] read(){
		String line;
		Double score;
		TargetFile a[]=new TargetFile[srcNum];

		try {
			BufferedReader in=new BufferedReader(new InputStreamReader(new FileInputStream(filePath),"utf-8"));
			for(int fileIndex=0;fileIndex<srcNum;fileIndex++){
				line=in.readLine().trim();
				score=Double.parseDouble(line);
				a[fileIndex]=new TargetFile(fileIndex+1,score);
			}
			Sort.bubble(a);
			for(int j=0;j<top;j++){
				fileMatrix[j]=a[j].getIndex();
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();}
		  catch (FileNotFoundException e) {
			e.printStackTrace();} 
		  catch (IOException e) {
			e.printStackTrace();}
			return fileMatrix;
		}
	
	public static void main(String[] args) {
		ReturnResult a=new ReturnResult("./RankingSVM/result.txt");
		a.read();
	}
}
