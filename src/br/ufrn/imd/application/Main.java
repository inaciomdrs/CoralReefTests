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
import br.ufrn.imd.test.PopulationExceededLimitsTest;
import br.ufrn.imd.test.SolutionIsUnderFitnessReferenceTest;

public class Main {
	
	public static void main(String[] args) {
		List<DoubleProblem> problems = new LinkedList<DoubleProblem>();

		int problemLength = 50;
		
		problems.add(new Sphere(problemLength));
		problems.add(new Rosenbrock(problemLength));
		problems.add(new Rastrigin(problemLength));
		problems.add(new Griewank(problemLength));

		ExperimentInformation<DoubleSolution> information;
		for (DoubleProblem doubleProblem : problems) {
			information = runExperimentForProblem(doubleProblem);
			
			System.out.print(information.getTimeElapsed() + "ms - " + doubleProblem.getName());
			
			runTest(new PopulationExceededLimitsTest<>(information));
			runTest(new SolutionIsUnderFitnessReferenceTest<>(information, 0.5));
			runTest(new AlgorithmConvergesTest<>(information));
			
			System.out.println();
		}

	}
	
	public static void runTest(AbstractTest<DoubleSolution> tester){
		try {
			tester.test();	
			System.out.print("\n\t" + tester.getName() + " Passed!");
		} catch(AssertionError ex){
			System.out.print("\n\t" + tester.getName() + " Failed!");
		}
	}

	public static ExperimentInformation<DoubleSolution> runExperimentForProblem(Problem<DoubleSolution> problem) {
		CROExperimentRunner runner = new CROExperimentRunner(problem);
		return runner.runExperiment();
	}

}
