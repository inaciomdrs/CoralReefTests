package br.ufrn.imd.experiment;

import java.util.Comparator;
import java.util.List;

import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.AlgorithmRunner;

public abstract class Experiment<S extends Solution<?>> {

	private Algorithm<S> algorithm;
	private Problem<S> problem;
	private Comparator<S> comparator;
	
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
	public Comparator<S> getComparator() {
		return comparator;
	}
	public void setComparator(Comparator<S> comparator) {
		this.comparator = comparator;
	}
	
	public abstract void configureAlgorithm();
	public abstract void configureProblem();
	
	public S executeExperiment(){
		configureAlgorithm();
		configureProblem();
		
		AlgorithmRunner algorithmRunner = new AlgorithmRunner.Executor(
				algorithm).execute();
		
		List<S> population = (List<S>) algorithm.getResult();
		
		population.sort(comparator);
		
		return population.get(0);
	}
	
	
	
	
	
	
}
