package dataStructure;

import utils.LoadData;
import utils.Print;
import utils.Sort;

/**ScoreMatrix得分矩阵类，内部包含一个记载了fileIndex（用于排序）和score的targetFile二维数组，可以实现加、数乘、排序和返回前top个值运算*/
public class ScoreMatrix{
	
	protected int reportNum;
	protected int srcNum;
	protected TargetFile[][] targetFileMatrix;
	
	/**ScoreMatrix的公有构造方法，可以从文件中载入目标文件类型的数据*/
	public ScoreMatrix(String path,int rNum, int sNum){
		//初始化一个targetFile二维数组
		this.reportNum=rNum;
		this.srcNum=sNum;
		targetFileMatrix=new TargetFile[reportNum][srcNum];
		
		for(int i=0;i<reportNum;i++){
			for(int j=0;j<srcNum;j++){
				targetFileMatrix[i][j]=new TargetFile(j+1,0);
			}
		}
		LoadData.loadResult(path,targetFileMatrix);
	}
	/**ScoreMatrix的私有构造方法，从一个targetFile二维数组载入数据*/
	private ScoreMatrix(TargetFile[][] m){
		reportNum=m.length;
		srcNum=m[0].length;
		targetFileMatrix=new TargetFile[reportNum][srcNum];
		for(int i=0;i<reportNum;i++){
			for(int j=0;j<srcNum;j++){
				targetFileMatrix[i][j]=new TargetFile(j+1,0);
			}
		}
		for(int i=0;i<reportNum;i++){
			for(int j=0;j<srcNum;j++){
				targetFileMatrix[i][j].setIndex(m[i][j].getIndex());
				targetFileMatrix[i][j].setScore(m[i][j].getScore());
			}
		}
	}
	public TargetFile[][] getTargetFile(){
		return targetFileMatrix;
	}
	
	/**ScoreMatrix的数乘操作*/
	public ScoreMatrix mult(double n){
		TargetFile[][] m=new TargetFile[reportNum][srcNum];
		for(int i=0;i<reportNum;i++){
			for(int j=0;j<srcNum;j++){
				m[i][j]=new TargetFile(j+1,0);
			}
		}
		for(int i=0;i<reportNum;i++){
			for(int j=0;j<srcNum;j++){
				m[i][j].setIndex(targetFileMatrix[i][j].getIndex());
				m[i][j].setScore(targetFileMatrix[i][j].getScore()*n);
			}
		}
		return new ScoreMatrix(m);
	}
	/**ScoreMatrix的加法操作*/
	public ScoreMatrix add(ScoreMatrix b){
		TargetFile[][] m=new TargetFile[reportNum][srcNum];
		for(int i=0;i<reportNum;i++){
			for(int j=0;j<srcNum;j++){
				m[i][j]=new TargetFile(j+1,0);
			}
		}
		
		for(int i=0;i<reportNum;i++){
			for(int j=0;j<srcNum;j++){
				m[i][j].setIndex(b.getTargetFile()[i][j].getIndex());
				m[i][j].setScore(targetFileMatrix[i][j].getScore()+b.getTargetFile()[i][j].getScore());
			}
		}
		return  new ScoreMatrix(m);
	}
	/**ScoreMatrix的排序操作*/
	public void sort(){
		for(int i=0;i<reportNum;i++){
			Sort.bubble(targetFileMatrix[i]);
		}
	}
	/**得到前top个得分结果*/
	public int[][] getTopResult(int top){
		int[][] topFileMatrix=new int[reportNum][top];
		for(int i=0;i<reportNum;i++){
			for(int j=0;j<top;j++){
				topFileMatrix[i][j]=targetFileMatrix[i][j].getIndex();
			}
		}
		return topFileMatrix;
	}
	/**得到前top个得分结果*/
	public void print(int n){
		for(int i=0;i<n;i++)
			Print.print(targetFileMatrix[i]);
	}
	/**得到所有得分结果*/
	public void print(){
		for(int i=0;i<this.reportNum;i++)
			Print.print(targetFileMatrix[i]);
	}
}
