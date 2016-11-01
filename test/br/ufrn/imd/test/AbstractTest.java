package br.ufrn.imd.test;

import static org.junit.Assert.assertTrue;

import java.util.function.Predicate;

import org.junit.Test;
import org.uma.jmetal.solution.Solution;

import br.ufrn.imd.experiment.ExperimentInformation;

public abstract class AbstractTest<S extends Solution<?>> {

	private ExperimentInformation<S> experimentInformation;
	private Predicate<S> predicate;
	private String name;
	
	public AbstractTest(ExperimentInformation<S> experimentInformation, Predicate<S> predicate) {
		this.experimentInformation = experimentInformation;
		this.predicate = predicate;
	}

	@Test
	public void test(){
		assertTrue(makeTest());
	}
	
	public abstract boolean makeTest();
	
	public ExperimentInformation<S> getExperimentInformation() {
		return experimentInformation;
	}

	public Predicate<S> getPredicate() {
		return predicate;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
}
