package br.ufrn.imd.application;

import java.util.LinkedList;
import java.util.List;

import org.uma.jmetal.problem.DoubleProblem;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.problem.singleobjective.Griewank;
import org.uma.jmetal.problem.singleobjective.Rastrigin;
import org.uma.jmetal.problem.singleobjective.Rosenbrock;
import org.uma.jmetal.problem.singleobjective.Sphere;
import org.uma.jmetal.solution.DoubleSolution;

import br.ufrn.imd.experiment.ExperimentInformation;
import br.ufrn.imd.runner.CROExperimentRunner;
import br.ufrn.imd.test.AbstractTest;
import br.ufrn.imd.test.AlgorithmConvergesTest;
import br.ufrn.imd.test.PopulationIsBetweenBoundariesTest;
import br.ufrn.imd.test.SolutionIsUnderFitnessReferenceTest;

public class Main {

	public static void main(String[] args) {
		executeTestBattery(10, 100, 10);
	}
	
	public static void executeTestBattery(int minLength, int maxLength, int increment){
		for (int length = minLength; length < maxLength; length+=increment) {
			executeTests(length);	
		}
	}

	public static void executeTests(int problemLength) {
		List<DoubleProblem> problems = new LinkedList<DoubleProblem>();

		problems.add(new Sphere(problemLength));
		problems.add(new Rosenbrock(problemLength));
		problems.add(new Rastrigin(problemLength));
		problems.add(new Griewank(problemLength));

		ExperimentInformation<DoubleSolution> information;
		for (DoubleProblem doubleProblem : problems) {
			information = runExperimentForProblem(doubleProblem);

			System.out.print(problemLength + "\t");
			System.out.print(doubleProblem.getName() + "\t");
			System.out.print(information.getTimeElapsed() + "\t");

			runTest(new PopulationIsBetweenBoundariesTest<>(information));
			runTest(new SolutionIsUnderFitnessReferenceTest<>(information, 0.5));
			runTest(new AlgorithmConvergesTest<>(information));

			System.out.println();
		}
	}

	public static void runTest(AbstractTest<DoubleSolution> tester) {
		try {
			tester.test();
			System.out.print("Passed\t");
		} catch (AssertionError ex) {
			System.out.print("Failed\t");
		}
	}

	public static ExperimentInformation<DoubleSolution> runExperimentForProblem(Problem<DoubleSolution> problem) {
		CROExperimentRunner runner = new CROExperimentRunner(problem);
		return runner.runExperiment();
	}

}