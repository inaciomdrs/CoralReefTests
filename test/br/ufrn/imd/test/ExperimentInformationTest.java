package br.ufrn.imd.test;

import static org.junit.Assert.assertTrue;

import java.util.function.Predicate;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.uma.jmetal.solution.Solution;

import br.ufrn.imd.conditions.SolutionQualityCondition;
import br.ufrn.imd.experiment.ExperimentInformation;

public class ExperimentInformationTest<S extends Solution<?>> {

	private ExperimentInformation<S> experimentInformation;
	Predicate<S> solutionQuality;
	
	public ExperimentInformationTest(ExperimentInformation<S> experimentInformation, double fitnessReference) {
		this.experimentInformation = experimentInformation;
		this.solutionQuality = new SolutionQualityCondition<S>(fitnessReference);
	}
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {		
		assertTrue(this.experimentInformation.isValidPopulationSize());
		assertTrue(solutionQuality.test(this.experimentInformation.getBestSolution()));
	}

}
