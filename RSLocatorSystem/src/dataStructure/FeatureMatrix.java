package dataStructure;

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
