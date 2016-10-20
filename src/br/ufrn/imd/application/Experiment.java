package br.ufrn.imd.application;

import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.Solution;

public abstract class Experiment<S extends Solution<?>> {

	private Algorithm<S> algorithm;
	private Problem<S> problem;
	
	public Algorithm<S> getAlgorithm() {
		return algorithm;
	}
	public void setAlgorithm(Algorithm<S> algorithm) {
		this.algorithm = algorithm;
	}
	public Problem<S> getProblem() {
		return problem;
	}
	public void setProblem(Problem<S> problem) {
		this.problem = problem;
	}
	
	
	
	public void executeExperiment(){
		
	}
	
	
	
	
	
	
}
