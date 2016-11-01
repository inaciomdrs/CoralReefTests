package br.ufrn.imd.test;

import org.uma.jmetal.solution.Solution;

import br.ufrn.imd.experiment.ExperimentInformation;

public class PopulationExceededLimitsTest<S extends Solution<?>> extends AbstractTest<S> {

	public PopulationExceededLimitsTest(ExperimentInformation<S> experimentInformation) {
		super(experimentInformation, null);
		setName("Population Exceeds Limit");
	}

	@Override
	public boolean makeTest() {
		return getExperimentInformation().isValidPopulationSize();
	}

}
