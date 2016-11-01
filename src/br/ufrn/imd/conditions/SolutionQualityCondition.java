package br.ufrn.imd.conditions;

import java.util.function.Predicate;

import org.uma.jmetal.solution.Solution;

public class SolutionQualityCondition<S extends Solution<?>> implements Predicate<S> {

	private double fitnessReference;
	
	public SolutionQualityCondition(double fitnessReference) {
		this.fitnessReference = fitnessReference;
	}

	@Override
	public boolean test(S t) {
		return t.getObjective(0) <= fitnessReference;
	}
}
