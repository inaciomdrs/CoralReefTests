package br.ufrn.imd.experiment;

public class ExperimentInformation<S> {

	private S bestSolution;
	private long timeElapsed;
	private int problemLength;
	private boolean validPopulationSize;
	
	public ExperimentInformation(){
		validPopulationSize = true;
	}
	
	public S getBestSolution() {
		return bestSolution;
	}
	public void setBestSolution(S bestSolution) {
		this.bestSolution = bestSolution;
	}
	public long getTimeElapsed() {
		return timeElapsed;
	}
	public void setTimeElapsed(long timeElapsed) {
		this.timeElapsed = timeElapsed;
	}
	public int getProblemLength() {
		return problemLength;
	}
	public void setProblemLength(int problemLength) {
		this.problemLength = problemLength;
	}
	public boolean isValidPopulationSize() {
		return validPopulationSize;
	}
	public void setValidPopulationSize(boolean validPopulationSize) {
		this.validPopulationSize = validPopulationSize;
	}
	
}
