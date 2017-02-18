package gridSearch;

import java.util.ArrayList;
import java.util.List;

import utils.Evaluation;
import utils.LoadData;
import utils.PublicValue;

public class ParameterGridSearch {

	private int reportNum=86;
	private int top=PublicValue.top;
	private List<Integer>[] answer=new ArrayList[reportNum];
	private int[][] fileMatrix=new int[reportNum][top];
	Evaluation evl;
	
	public ParameterGridSearch(String resultPath){
		LoadData.loadAnswer("E:/研究生/毕业论文/1.6.0+1.6.1实验/part1answer.txt",answer, reportNum);
		LoadData.loadResult(resultPath,fileMatrix);
		evl=new Evaluation(fileMatrix, answer);
	}
	
	public void search(){
		evl.showResult();
	}
	
	public static void main(String[] args) {
		for(int k1_value=12;k1_value<18;k1_value++){
			double k1=k1_value/10.0;
			for(int k2=100;k2<600;k2+=100){
				for(double b_value=3;b_value<8;b_value++){
					double b=b_value/10.0;
					String path="E:/研究生/毕业论文/1.6.0+1.6.1实验/实验结果/网格搜索结果/GridSearchBM25Component/"+String.valueOf(k1)+" "+String.valueOf(k2)+" "+String.valueOf(b)+" Result.txt";
					ParameterGridSearch g=new ParameterGridSearch(path);
					System.out.println("k1="+k1+" k2="+k2+" b="+b);
					g.search();
				}
			}
		}
	}

}
