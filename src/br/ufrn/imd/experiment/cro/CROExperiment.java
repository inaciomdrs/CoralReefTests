package br.ufrn.imd.experiment.cro;

import org.uma.jmetal.algorithm.singleobjective.coralreefsoptimization.CoralReefsOptimizationWithMeasures;
import org.uma.jmetal.measure.MeasureListener;
import org.uma.jmetal.measure.impl.BasicMeasure;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.solution.Solution;

import br.ufrn.imd.experiment.Experiment;

public abstract class CROExperiment<S extends Solution<?>> extends Experiment<S> {

	private CoralReefsOptimizationWithMeasures<S> coralReefsOptimizationAlgorithm;
	private CrossoverOperator<S> crossoverOperator;
	private MutationOperator<S> mutationOperator;
	private SelectionOperator<?, ?> selectionOperator;
	
	private static class ActualPopulationListener implements MeasureListener<Integer> {

		@Override
		public void measureGenerated(Integer value) {
			
		}
		
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

	public void setSelectionOperator(SelectionOperator<?, ?> selectionOperator) {
		this.selectionOperator = selectionOperator;
	}
	
	@Override
	public void configureMeasures(){
		setMeasureManager(coralReefsOptimizationAlgorithm.getMeasureManager());

		BasicMeasure<Integer> actualPopulation = (BasicMeasure<Integer>) getMeasureManager()
				.<Integer> getPushMeasure("actualPopulationSize");
		
		actualPopulation.register(new ActualPopulationListener());
	}

	@Override
	public void runAlgorithm() {
		Thread algorithmThread = new Thread(coralReefsOptimizationAlgorithm);
	    algorithmThread.start();
	    try {
			algorithmThread.join();
		} catch (InterruptedException e) {}
	    
	}

	@Override
	public S getResult() {
		// TODO Auto-generated method stub
		return null;
	}

}
