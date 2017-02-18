package linearWeighting;

import java.io.File;

import java.util.ArrayList;
import java.util.List;

import dataStructure.ScoreMatrix;

import utils.Evaluation;
import utils.LoadData;
import utils.PublicValue;

/**按照比例计算最终的得分*/
public class LinearWeightingDemo {

	private static ScoreMatrix weightedCalculating(ScoreMatrix field1,int weight1,
			ScoreMatrix field2,int weight2,
			ScoreMatrix field3){
		return field1.mult(weight1).add(field2.mult(weight2)).add(field3).mult(10.0-weight1-weight2);
	}
	
	public static void main(String[] args) {
		int top=PublicValue.top;
		int reportNum=PublicValue.reportNum,srcNum=PublicValue.srcNum;
		ScoreMatrix componentScore,codeScore,identifierScore,finalScore;
		int[][] topFileMatrix=new int[reportNum][top];
		List<Integer>[] answer=new ArrayList[reportNum];
		
		componentScore=new ScoreMatrix(PublicValue.VSMcomponentResultPath,reportNum,srcNum);
		identifierScore=new ScoreMatrix(PublicValue.VSMidentifierResultPath,reportNum,srcNum);
		codeScore=new ScoreMatrix(PublicValue.VSMcodeResultPath,reportNum,srcNum);
		LoadData.loadAnswer(PublicValue.answerPath,answer, reportNum);
		
		finalScore=weightedCalculating(codeScore,2,componentScore,2,identifierScore);
		finalScore.sort();
		topFileMatrix=finalScore.getTopResult(top);
		
		Evaluation evl=new Evaluation(topFileMatrix,answer);	
		evl.showResult();
	}

}
