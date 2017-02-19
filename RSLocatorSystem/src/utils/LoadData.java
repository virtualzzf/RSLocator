package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import dataStructure.TargetFile;


/**工具类之载入数据*/
public class LoadData {

	/**载入答案，即实际进行修改的文件，形成列表的数组*/
	public  static void loadAnswer(String path,List<Integer>[] answer,int reportNum){
		File file=new File(path);
		try {
			BufferedReader in=new BufferedReader(new InputStreamReader(new FileInputStream(file),"utf-8"));
			int i=0;
			for(i=0;i<reportNum;i++){
				String line=in.readLine();
				answer[i]=new ArrayList<Integer>();
	            for(String num:line.split(" ")){
	            	int n=Integer.valueOf(num);
	            	answer[i].add(new Integer(n));
	            }
			}
		} catch (UnsupportedEncodingException e) {
			System.out.println("UnsupportedEncodingException");
		} catch (FileNotFoundException e) {
			System.out.println("FileNotFoundException");
		}catch (IOException e){
			System.out.println("IOException");
		}
	}
	
	/**载入答案，形成set数组*/
	public  static void loadAnswer(String path, Set[] answer, int reportNum){
		File file=new File(path);
		try {
			BufferedReader in=new BufferedReader(new InputStreamReader(new FileInputStream(file),"utf-8"));
			int i=0;
			for(i=0;i<reportNum;i++){
				String line=in.readLine();
				answer[i]=new HashSet();
	            for(String num:line.split(" ")){
	            	int n=Integer.valueOf(num);
	            	answer[i].add(n);
	            }
			}
		} catch (UnsupportedEncodingException e) {
			System.out.println("UnsupportedEncodingException");
		} catch (FileNotFoundException e) {
			System.out.println("FileNotFoundException");
		}catch (IOException e){
			System.out.println("IOException");
		}
	}
	/**用于格式化文件模型中载入数据，形成推荐结果的二维数组*/
	public static void loadResult(String path,int[][] fileMatrix){
		File file=new File(path);
		String line;
		int i=0,j=0,fileIndex=0,reportNum=fileMatrix.length,top=fileMatrix[0].length;
		int[] read=new int[reportNum];
		for(int num=0;i<reportNum;i++)
			read[num]=0;
		try {
			BufferedReader in=new BufferedReader(new InputStreamReader(new FileInputStream(file),"utf-8"));
			/**载入数据*/
			while(true){
				line=in.readLine();
				if(line!=null){
					String[] values=line.split("\t");
					i=Integer.valueOf(values[0]);
					j=Integer.valueOf(values[1]);
					fileIndex=Integer.valueOf(values[2]);
					if(read[i]<top){
						fileMatrix[i][j]=fileIndex;
						read[i]++;
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
			System.out.println("IOException");
		}
	}
	/**用于从格式化文件中载入数据，形成得分的二维数组*/
	public static void loadResult(String path,double[][] fileMatrix){
		File file=new File(path);
		String line;
		int i=0,j=0;
		double score;
		try {
			BufferedReader in=new BufferedReader(new InputStreamReader(new FileInputStream(file),"utf-8"));
			/**载入数据*/
			while(true){
				line=in.readLine();
				if(line!=null){
					String[] values=line.split("\t");
					i=Integer.valueOf(values[0]);
					j=Integer.valueOf(values[2])-1;
					score=Double.valueOf(values[3]);
					fileMatrix[i][j]=score;
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
			System.out.println("IOException");
		}
	}
	/**用于格式化文件模型中载入数据，形成得分的targetFile类型二维数组*/
	public static void loadResult(String path,TargetFile[][] tragetFileMatrix){
		File file=new File(path);
		//读取文件中一行文本
		String line;
		//指示二维数组行列
		int i=0,j=0;
		//记录分数
		double score,max=1.0;
		try {
			BufferedReader in=new BufferedReader(new InputStreamReader(new FileInputStream(file),"utf-8"));
			/**载入数据*/
			while(true){
				line=in.readLine();
				if(line!=null){
					//文件格式是，query编号（从0开始）、排名编号（从0开始）、文件编号（从1开始）、得分
					String[] values=line.split("\t");
					i=Integer.valueOf(values[0]);
					if(Integer.valueOf(values[1])==0){
						max=Double.valueOf(values[3]);
					}
					j=Integer.valueOf(values[2]);
					score=Double.valueOf(values[3])/max;
					tragetFileMatrix[i][j-1].setIndex(j);
					tragetFileMatrix[i][j-1].setScore(score);
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
			System.out.println("IOException");
		}

	}
	/**用于格式化文件模型中载入数据，形成report对应每个file排名的二维数组*/
	public  static void loadRank(String path,Map<String,String>[] maps){
		File file=new File(path);
		String line;
		int queryIndex=0;
		String fileIndex,rank;

		for(int i=0;i<maps.length;i++){
			maps[i]=new HashMap<String,String>();
		}
		try {
			BufferedReader in=new BufferedReader(new InputStreamReader(new FileInputStream(file),"utf-8"));
			/**载入数据*/
			while(true){
				line=in.readLine();
				if(line!=null){
					String[] values=line.split("\t");
					queryIndex=Integer.valueOf(values[0]);
					fileIndex=values[2];
					rank=values[1];
					maps[queryIndex].put(fileIndex, rank);
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
			System.out.println("IOException");
		}
	}
}
