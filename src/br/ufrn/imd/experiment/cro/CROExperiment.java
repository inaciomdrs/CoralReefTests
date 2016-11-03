package br.ufrn.imd.experiment.cro;

import java.util.List;

import org.uma.jmetal.algorithm.singleobjective.coralreefsoptimization.CoralReefsOptimizationWithMeasures;
import org.uma.jmetal.measure.MeasureListener;
import org.uma.jmetal.measure.impl.BasicMeasure;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.comparator.ObjectiveComparator;

import br.ufrn.imd.conditions.PopulationSizeCondition;
import br.ufrn.imd.conditions.SolutionCovergenceCondition;
import br.ufrn.imd.experiment.Experiment;
import br.ufrn.imd.experiment.ExperimentInformation;

public class CROExperiment<S extends Solution<?>> extends Experiment<S> {

	private CoralReefsOptimizationWithMeasures<S> coralReefsOptimizationAlgorithm;
	private Problem<S> problem;
	private CrossoverOperator<S> crossoverOperator;
	private MutationOperator<S> mutationOperator;
	private SelectionOperator<List<S>, S> selectionOperator;

	private ExperimentInformation<S> experimentInformation;
	private PopulationSizeCondition populationCondition;
	private SolutionCovergenceCondition solutionConvergenceCondition;
	
	private double baseFitness;

	public CROExperiment(Problem<S> problem, CrossoverOperator<S> crossoverOperator,
			MutationOperator<S> mutationOperator, SelectionOperator<List<S>, S> selectionOperator,
			ExperimentInformation<S> experimentInformation) {
		this.problem = problem;
		this.crossoverOperator = crossoverOperator;
		this.mutationOperator = mutationOperator;
		this.selectionOperator = selectionOperator;

		this.experimentInformation = experimentInformation;
		
		this.baseFitness = Double.MAX_VALUE;
	}

	public CoralReefsOptimizationWithMeasures<S> getCoralReefsOptimizationAlgorithm() {
		return coralReefsOptimizationAlgorithm;
	}

	public void setCoralReefsOptimizationAlgorithm(
			CoralReefsOptimizationWithMeasures<S> coralReefsOptimizationAlgorithm) {
		this.coralReefsOptimizationAlgorithm = coralReefsOptimizationAlgorithm;
	}

	public CrossoverOperator<S> getCrossoverOperator() {
		return crossoverOperator;
	}

	public void setCrossoverOperator(CrossoverOperator<S> crossoverOperator) {
		this.crossoverOperator = crossoverOperator;
	}

	public MutationOperator<S> getMutationOperator() {
		return mutationOperator;
	}

	public void setMutationOperator(MutationOperator<S> mutationOperator) {
		this.mutationOperator = mutationOperator;
	}

	public SelectionOperator<?, ?> getSelectionOperator() {
		return selectionOperator;
	}

	public void setSelectionOperator(SelectionOperator<List<S>, S> selectionOperator) {
		this.selectionOperator = selectionOperator;
	}

	public ExperimentInformation<S> getExperimentInformation() {
		return experimentInformation;
	}

	public void setExperimentInformation(ExperimentInformation<S> experimentInformation) {
		this.experimentInformation = experimentInformation;
	}

	public Problem<S> getProblem() {
		return problem;
	}

	public void setProblem(Problem<S> problem) {
		this.problem = problem;
	}

	public PopulationSizeCondition getPopulationCondition() {
		return populationCondition;
	}

	public void setPopulationCondition(PopulationSizeCondition populationCondition) {
		this.populationCondition = populationCondition;
	}

	@Override
	public void configureAlgorithm() {
		int numberEvaluations = 10000;
		int n = 10;
		int m = 10;
		double rho = 0.6; 
		double fbs = 0.9;
		double fa = 0.1;
		double pd = 0.1;
		int attemptsToSettle = 3;
		
		this.coralReefsOptimizationAlgorithm = new CoralReefsOptimizationWithMeasures<S>(this.problem, numberEvaluations, 
				new ObjectiveComparator<S>(0), this.selectionOperator, this.crossoverOperator, this.mutationOperator, 
				n, m, rho, fbs, fa, pd, attemptsToSettle);
		
		int maxPopulationSize = this.coralReefsOptimizationAlgorithm.getM()
				* this.coralReefsOptimizationAlgorithm.getN();
		int minPopulationSize = (int) Math
				.round(maxPopulationSize * this.getCoralReefsOptimizationAlgorithm().getRho());
		
		this.populationCondition = new PopulationSizeCondition(maxPopulationSize, minPopulationSize);
	}

	@Override
	public void configureMeasures() {
		setMeasureManager(coralReefsOptimizationAlgorithm.getMeasureManager());

		BasicMeasure<Integer> actualPopulation = (BasicMeasure<Integer>) getMeasureManager()
				.<Integer> getPushMeasure("actualPopulationSize");
		
		BasicMeasure<Double> solutionConvergence = (BasicMeasure<Double>) getMeasureManager()
				.<Double> getPushMeasure("bestSolutionFitness");

		actualPopulation.register(new ActualPopulationListener());
		solutionConvergence.register(new SolutionConvergenceListener());
	}

	@Override
	public void runAlgorithm() {
		Thread algorithmThread = new Thread(coralReefsOptimizationAlgorithm);
		algorithmThread.start();

		long initialTime = System.currentTimeMillis();

		try {
			algorithmThread.join();
		} catch (InterruptedException e) {
		}

		this.experimentInformation.setTimeElapsed(System.currentTimeMillis() - initialTime);
	}

	@Override
	public S getResult() {
		S bestSolution = this.coralReefsOptimizationAlgorithm.getResult().get(0);
		return bestSolution;
	}

	private class ActualPopulationListener implements MeasureListener<Integer> {
		@Override
		public void measureGenerated(Integer value) {
				experimentInformation.setValidPopulationSize(populationCondition.test(value));
		}
	}
	
	private class SolutionConvergenceListener implements MeasureListener<Double> {
		@Override
		public void measureGenerated(Double value) {
			solutionConvergenceCondition = new SolutionCovergenceCondition(baseFitness);
			experimentInformation.setAlgorithmConverges(solutionConvergenceCondition.test(value));
			baseFitness = value;
		}
	}
	
}
