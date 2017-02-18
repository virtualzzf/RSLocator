package utils;

import java.util.*;
/**工具类之评分*/
public class Evaluation {

	private int[][] recommendFile;
	private List<Integer>[] actualFile;
	private int[] locatedNum;
	private int[] locatedFirst;
	private double precision,recall,MRR;
	private int reportNum,topK;
	private int maxIndex=99999;
	
	/**构造方法，通过二维数组和列表数组*/
	public  Evaluation(int[][] r,List<Integer>[] a){
		recommendFile=r;
		actualFile=a;
		reportNum=r.length;
		topK=r[0].length;
		
		/**初始化locatedNum和locatedFirst数组*/
		locatedNum=new int[reportNum];
		locatedFirst=new int[reportNum];
		
		for(int i=0;i<reportNum;i++){
			int firstIndex=maxIndex;
			for(int j=0;j<actualFile[i].size();j++){
				for(int k=0;k<topK;k++){
					if(actualFile[i].get(j)==recommendFile[i][k]){
						locatedNum[i]++;
						if(k<firstIndex){
							firstIndex=k;
						}
					}
				}
			}
			if(firstIndex!=maxIndex){
				locatedFirst[i]=firstIndex+1;
			}
		}
	}

	/**肢体方法，求TopK*/
	public int getTopK(){
		int sumOfBug=0;
		for(int i=0;i<reportNum;i++){
			if(locatedNum[i]!=0)
				sumOfBug++;
		}
		return sumOfBug;
	}
	/**肢体方法，求precision*/
	public int getFindNum(){
		int findNum=sum(locatedNum);
		return findNum;
	}
	/**肢体方法，求precision*/
	public double getPrecision(){
		precision=(double)sum(locatedNum)/reportNum/topK;
		return precision;
	}
	/**肢体方法，求recall*/
	public double getRecall(){
		recall=(double)sum(locatedNum)/sum(actualFile);
		return recall;
	}
	/**肢体方法，求MRR*/
	public double getMRR(){
		double[] inverseRank=new double[reportNum];
		for(int i=0;i<reportNum;i++){
			if(locatedFirst[i]==0){
				inverseRank[i]=0;
			}else{
				inverseRank[i]=1.0/(double)locatedFirst[i];
			}
		}
		MRR=sum(inverseRank)/reportNum;
		return MRR;
	}
	/**肢体方法，求F-Measure*/
	public double getFMeasure(){
		double precision=getPrecision();
		double recall=getRecall();
		double FMeasure=2*precision*recall/(precision+recall);
		
		return FMeasure;
	}
	public void showResult(){
		System.out.println("在top"+topK+"推荐文件下，共修正了"+getTopK()+"个bug");
		System.out.println("findNum="+getFindNum());
		System.out.println("precission="+getPrecision()*100+"%");
		System.out.println("recall="+getRecall()*100+"%");
		System.out.println("MRR="+getMRR());
		System.out.println("F_Measures="+getFMeasure());
	}
	/**辅助方法，数组求和*/
	private int sum(int[] a){
		int sum=0;
		for(int i=0;i<a.length;i++){
			sum+=a[i];
		}
		return sum;
	}
	/**辅助方法，list数组元素个数求和*/
	private int sum(List<Integer>[] l){
		int sum=0;
		for(int i=0;i<l.length;i++){
				sum+=l[i].size();
		}
		return sum;
	}	
	/**辅助方法，数组求和*/
	private double sum(double[] a){
		double sum=0;
		for(int i=0;i<a.length;i++){
			sum+=a[i];
		}
		return sum;
	}
	/**测试方法，输出信息，包括locatedNum,locatedFirst数组*/
	public void showInfo(){
		System.out.println("locatedNum数组");
		for(int i=0;i<reportNum;i++){
			System.out.print(locatedNum[i]+" ");
		}
		System.out.println();
		System.out.println("locatedFirst数组");
		for(int i=0;i<reportNum;i++){
			System.out.print(locatedFirst[i]+" ");
		}
		System.out.println();
	}
	/**获取器*/
	public int[] getLocatedNum(){
		return locatedNum;
	}
	public int[] getLocatedFirst(){
		return locatedFirst;
	}
}
