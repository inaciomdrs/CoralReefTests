package org.uma.jmetal.problem.singleobjective;

import java.util.ArrayList;
import java.util.List;

import org.uma.jmetal.problem.impl.AbstractDoubleProblem;
import org.uma.jmetal.problem.singleobjective.cec2005competitioncode.TestFunc;
import org.uma.jmetal.solution.DoubleSolution;

public class BenchmarkFunctionProblem extends AbstractDoubleProblem {

	private static final long serialVersionUID = -5325336319653749137L;
	TestFunc testFunc;

	public BenchmarkFunctionProblem(Integer numberOfVariables, TestFunc testFunc, String testFuncName, double lowerLimitValue, double upperLimitValue) {
		setNumberOfVariables(numberOfVariables);
		setNumberOfObjectives(1);
		setNumberOfConstraints(0);
		setName(testFuncName);

		this.testFunc = testFunc;
		
		List<Double> lowerLimit = new ArrayList<>(getNumberOfVariables()) ;
	    List<Double> upperLimit = new ArrayList<>(getNumberOfVariables()) ;

	    for (int i = 0; i < getNumberOfVariables(); i++) {
	      lowerLimit.add(lowerLimitValue);
	      upperLimit.add(upperLimitValue);
	    }

	    setLowerLimit(lowerLimit);
	    setUpperLimit(upperLimit);		
	}

	@Override
	public void evaluate(DoubleSolution solution) {
		int numberOfVariables = getNumberOfVariables();

		double[] x = new double[numberOfVariables];

		for (int i = 0; i < numberOfVariables; i++) {
			x[i] = solution.getVariableValue(i);
		}

		solution.setObjective(0, this.testFunc.f(x));
	}

}
