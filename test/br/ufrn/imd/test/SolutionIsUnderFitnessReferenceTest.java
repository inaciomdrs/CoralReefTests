package br.ufrn.imd.test;

import org.uma.jmetal.solution.Solution;

import br.ufrn.imd.conditions.SolutionQualityCondition;
import br.ufrn.imd.experiment.ExperimentInformation;

public class SolutionIsUnderFitnessReferenceTest<S extends Solution<?>> extends AbstractTest<S> {

	public SolutionIsUnderFitnessReferenceTest(ExperimentInformation<S> experimentInformation, double fitnessReference) {
		super(experimentInformation, new SolutionQualityCondition<>(fitnessReference));
		setName("Solution is Under Fitness Reference");
	}

	@Override
	public boolean makeTest() {
		return getPredicate().test(getExperimentInformation().getBestSolution());
	}

}
