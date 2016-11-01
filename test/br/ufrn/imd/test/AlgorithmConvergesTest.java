package br.ufrn.imd.test;

import org.uma.jmetal.solution.Solution;

import br.ufrn.imd.experiment.ExperimentInformation;

public class AlgorithmConvergesTest<S extends Solution<?>> extends AbstractTest<S> {

	public AlgorithmConvergesTest(ExperimentInformation<S> experimentInformation) {
		super(experimentInformation, null);
		setName("Algorithm Converges Test");
	}

	@Override
	public boolean makeTest() {
		return getExperimentInformation().isAlgorithmConverges();
	}

}
