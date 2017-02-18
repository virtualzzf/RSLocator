package gridSearch;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import dataStructure.ScoreMatrix;

import utils.Evaluation;
import utils.LoadData;
import utils.PublicValue;

public class WeightGridSearch {
	/**按照比例计算最终的得分*/
	private static ScoreMatrix weightedCalculating(ScoreMatrix field1,int weight1,
			ScoreMatrix field2,int weight2,
			ScoreMatrix field3){
		return field1.mult(weight1).add(field2.mult(weight2)).add(field3).mult(10.0-weight1-weight2);
	}
	
	public static void main(String[] args) {
		int top=PublicValue.top;
		int reportNum=PublicValue.partReportNum,srcNum=PublicValue.srcNum;
		ScoreMatrix componentScore,codeScore,identifierScore,finalScore;
		int[][] topFileMatrix=new int[reportNum][top];
		List<Integer>[] answer=new ArrayList[reportNum];
		
		componentScore=new ScoreMatrix(PublicValue.partVSMcomponentResultPath,reportNum,srcNum);
		identifierScore=new ScoreMatrix(PublicValue.partVSMidentifierResultPath,reportNum,srcNum);
		codeScore=new ScoreMatrix(PublicValue.partVSMcodeResultPath,reportNum,srcNum);
		LoadData.loadAnswer(PublicValue.partAnswerPath,answer, reportNum);
		
		for(int i=1;i<10;i++){
			for(int j=0;j<11-i;j++){
				System.out.println("a="+i+",b="+j+",c="+(10-i-j));
				finalScore=weightedCalculating(codeScore,i,componentScore,j,identifierScore);
				finalScore.sort();
				topFileMatrix=finalScore.getTopResult(top);
				
				Evaluation evl=new Evaluation(topFileMatrix,answer);	
				evl.showResult();
				System.out.println();
			}
		}
	}
}
