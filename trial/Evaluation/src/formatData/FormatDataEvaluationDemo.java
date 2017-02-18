package formatData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utils.Evaluation;
import utils.LoadData;
import utils.Print;
import utils.PublicValue;

/**用于从给定格式文件中读取结果并排序*/
public class FormatDataEvaluationDemo {

	private int reportNum=PublicValue.reportNum;
	private int top=PublicValue.top;
	private List<Integer>[] answer=new ArrayList[reportNum];
	private int[][] fileMatrix=new int[reportNum][top];
	private Map<String,String>[] rankArray=new  HashMap[reportNum];
	
	public FormatDataEvaluationDemo(){
		loadData();
		evaluate();
		check();
	}
		
	private void loadData(){
		LoadData.loadAnswer(PublicValue.answerPath,answer, reportNum);
		LoadData.loadResult(PublicValue.BM25codeResultPath,fileMatrix);
		LoadData.loadRank(PublicValue.BM25codeResultPath, rankArray);
	}
	/**输出每个file在排名中对应的名次*/
	private void check(){
		String file="";
		String rank;
		for(int i=0;i<reportNum;i++){
			for(int j=0;j<answer[i].size();j++){
				file=answer[i].get(j).toString();
				System.out.print(file+": ");
				rank=rankArray[i].get(file);
				System.out.print(rank+"\t");
			}
			System.out.println();
		}
	}
	
	/**对结果进行评估*/
	private void evaluate(){
		Evaluation evl=new Evaluation(fileMatrix,answer);
		evl.showResult();
	}
			
	public static void main(String[] args) {
		new FormatDataEvaluationDemo();
	}

}
