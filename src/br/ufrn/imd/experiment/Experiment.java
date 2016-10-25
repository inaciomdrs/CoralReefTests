package br.ufrn.imd.experiment;

import org.uma.jmetal.measure.MeasureManager;
import org.uma.jmetal.solution.Solution;

public abstract class Experiment<S extends Solution<?>> {
	
	private MeasureManager measureManager;
	
	public MeasureManager getMeasureManager() {
		return measureManager;
	}
	public void setMeasureManager(MeasureManager measureManager) {
		this.measureManager = measureManager;
	}
	
	public abstract void configureAlgorithm();
	public abstract void configureMeasures();
	public abstract void runAlgorithm();
	public abstract S    getResult();
	
	public S executeExperiment(){
		configureAlgorithm();
		configureMeasures();
		runAlgorithm();
		return getResult();
	}
}
