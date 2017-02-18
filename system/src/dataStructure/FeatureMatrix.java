package dataStructure;

/**特征矩阵，用于存放特征（目前是代码、驼峰字、组件相似度得分）*/
public class FeatureMatrix {
	double codeScore,identifierScore,componentScore;

	public FeatureMatrix(double code,double identifier,double component){
		codeScore=code;
		identifierScore=identifier;
		componentScore=component;
	}
	
	public double getCodeScore() {
		return codeScore;
	}
	public void setCodeScore(double codeScore) {
		this.codeScore = codeScore;
	}
	public double getIdentifierScore() {
		return identifierScore;
	}
	public void setIdentifierScore(double identifierScore) {
		this.identifierScore = identifierScore;
	}
	public double getComponentScore() {
		return componentScore;
	}
	public void setComponentScore(double componentScore) {
		this.componentScore = componentScore;
	}
	
}
