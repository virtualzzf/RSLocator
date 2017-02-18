package dataStructure;

/**工具之目标文件类，存有文件的index、得分和排名*/
public class TargetFile {

	/**int类型fileIndex，表示文件id，按照文件内容，从0开始 */
	private int fileIndex;
	/**double类型的value得分值*/
	private double score;
	
	public TargetFile(int index,double s){
		fileIndex=index;
		score=s;
	}
	
	public int getIndex(){
		return fileIndex;
	}
	public double getScore(){
		return score;
	}	
	public void setIndex(int i){
		fileIndex=i;
	}
	public void setScore(double s){
		score=s;
	}

	public String toString(){
		return "("+this.fileIndex+","+this.score+")";
	}
}
