package br.ufrn.imd.experiment;

import org.uma.jmetal.solution.Solution;

public class ExperimentInformation<S extends Solution<?>> {

	private S bestSolution;
	private long timeElapsed;
	private int problemLength;
	private boolean validPopulationSize;
	private boolean algorithmConverges;
	
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
	public boolean isAlgorithmConverges() {
		return algorithmConverges;
	}
	public void setAlgorithmConverges(boolean algorithmConverges) {
		this.algorithmConverges = algorithmConverges;
	}
	
}
