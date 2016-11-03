package br.ufrn.imd.application;

import java.util.LinkedList;
import java.util.List;

import org.uma.jmetal.problem.DoubleProblem;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.problem.singleobjective.BenchmarkFunctionProblem;
import org.uma.jmetal.problem.singleobjective.Griewank;
import org.uma.jmetal.problem.singleobjective.Rastrigin;
import org.uma.jmetal.problem.singleobjective.Rosenbrock;
import org.uma.jmetal.problem.singleobjective.Sphere;
import org.uma.jmetal.problem.singleobjective.cec2005competitioncode.F12Schwefel;
import org.uma.jmetal.problem.singleobjective.cec2005competitioncode.TestFunc;
import org.uma.jmetal.solution.DoubleSolution;

import br.ufrn.imd.experiment.ExperimentInformation;
import br.ufrn.imd.runner.CROExperimentRunner;
import br.ufrn.imd.test.AbstractTest;
import br.ufrn.imd.test.AlgorithmConvergesTest;
import br.ufrn.imd.test.PopulationExceededLimitsTest;
import br.ufrn.imd.test.SolutionIsUnderFitnessReferenceTest;

public class Main {

	public static void main(String[] args) {
		int FAIL_EXIT = 1;
		
		int problemLength;
		
		if(args.length == 1){
			try {
				problemLength = Integer.parseInt(args[0]);
				executeTests(problemLength);
			} catch(NumberFormatException ex){
				System.out.println("Número inválido repassado!");
				System.exit(FAIL_EXIT);
			}
		} else {
			System.out.println("Uso: java -jar CoralReefsTest.jar <tamanho_problema>");
			System.out.println("<tamanho_problema> deve ser um inteiro");
			System.exit(FAIL_EXIT);
		}
		
		
	}

	public static void executeTests(int problemLength) {
		List<DoubleProblem> problems = new LinkedList<DoubleProblem>();

		double schwefelLowerBound = -100.0;
		double schwefelUpperBound = 100.0;
		TestFunc schwefel = new F12Schwefel(problemLength, 1, "supportData/schwefel_213_data.txt");
		BenchmarkFunctionProblem schwefelProblem = new BenchmarkFunctionProblem(problemLength, schwefel,
				"Schwefel's Problem 2.13", schwefelLowerBound, schwefelUpperBound);

		problems.add(new Sphere(problemLength));
		problems.add(new Rosenbrock(problemLength));
		problems.add(new Rastrigin(problemLength));
		problems.add(new Griewank(problemLength));
		problems.add(schwefelProblem);

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

	public static void runTest(AbstractTest<DoubleSolution> tester) {
		try {
			tester.test();
			System.out.print("\n\t" + tester.getName() + " Passed!");
		} catch (AssertionError ex) {
			System.out.print("\n\t" + tester.getName() + " Failed!");
		}
	}

	public static ExperimentInformation<DoubleSolution> runExperimentForProblem(Problem<DoubleSolution> problem) {
		CROExperimentRunner runner = new CROExperimentRunner(problem);
		return runner.runExperiment();
	}

}
