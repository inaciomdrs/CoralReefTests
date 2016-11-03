package br.ufrn.imd.test;

import org.uma.jmetal.solution.Solution;

import br.ufrn.imd.experiment.ExperimentInformation;

public class PopulationIsBetweenBoundariesTest<S extends Solution<?>> extends AbstractTest<S> {

	public PopulationIsBetweenBoundariesTest(ExperimentInformation<S> experimentInformation) {
		super(experimentInformation, null);
		setName("Population is between boundaries");
	}

	@Override
	public boolean makeTest() {
		return getExperimentInformation().isValidPopulationSize();
	}

}
