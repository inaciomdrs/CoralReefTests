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
		int FAIL_EXIT = 1;
		
		int problemLength;
		
		if(args.length == 1){
			try {
				problemLength = Integer.parseInt(args[0]);
				
				if(problemLength <= 0){
					System.out.println("Número inválido repassado!");
					System.exit(FAIL_EXIT);	
				}
				
				executeTests(problemLength);
			} catch(NumberFormatException ex){
				System.out.println("Número inválido repassado!");
				System.exit(FAIL_EXIT);
			}
		} else {
			System.out.println("Uso: java -jar CoralReefsTest.jar <tamanho_problema>");
			System.out.println("<tamanho_problema> deve ser um inteiro maior que 0");
			System.exit(FAIL_EXIT);
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

			System.out.println(doubleProblem.getName());
			System.out.println("\tTime Elapsed: " + information.getTimeElapsed() + "ms");

			runTest(new PopulationIsBetweenBoundariesTest<>(information));
			runTest(new SolutionIsUnderFitnessReferenceTest<>(information, 0.5));
			runTest(new AlgorithmConvergesTest<>(information));

			System.out.println();
		}
	}

	public static void runTest(AbstractTest<DoubleSolution> tester) {
		try {
			tester.test();
			System.out.print("\t" + tester.getName() + " Passed!\n");
		} catch (AssertionError ex) {
			System.out.print("\t" + tester.getName() + " Failed!\n");
		}
	}

	public static ExperimentInformation<DoubleSolution> runExperimentForProblem(Problem<DoubleSolution> problem) {
		CROExperimentRunner runner = new CROExperimentRunner(problem);
		return runner.runExperiment();
	}

}
