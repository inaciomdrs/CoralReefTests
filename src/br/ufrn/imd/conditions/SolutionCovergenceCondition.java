package br.ufrn.imd.conditions;

import java.util.function.Predicate;

public class SolutionCovergenceCondition implements Predicate<Double> {
	
	private double fitnessAnterior;
	
	public SolutionCovergenceCondition(double fitnessAnterior) {
		this.fitnessAnterior = fitnessAnterior;
	}

	@Override
	public boolean test(Double t) {
		return t <= this.fitnessAnterior;
	}

	
	
	

}
