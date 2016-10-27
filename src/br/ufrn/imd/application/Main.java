package br.ufrn.imd.application;

import java.util.LinkedList;
import java.util.List;

import org.uma.jmetal.problem.DoubleProblem;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.problem.multiobjective.Schaffer;
import org.uma.jmetal.problem.singleobjective.Griewank;
import org.uma.jmetal.problem.singleobjective.Rastrigin;
import org.uma.jmetal.problem.singleobjective.Rosenbrock;
import org.uma.jmetal.problem.singleobjective.Sphere;
import org.uma.jmetal.solution.DoubleSolution;

import br.ufrn.imd.experiment.ExperimentInformation;
import br.ufrn.imd.runner.CROExperimentRunner;

public class Main {

	public static void main(String[] args) {
		List<DoubleProblem> problems = new LinkedList<DoubleProblem>();

		int problemLength = 10;

		problems.add(new Schaffer());
		problems.add(new Sphere());
		problems.add(new Rosenbrock(problemLength));
		problems.add(new Rastrigin(problemLength));
		problems.add(new Griewank(problemLength));

		System.out.println("Time Elapsed\tValid Population\tBestSolutionFitness");

		ExperimentInformation<DoubleSolution> information;
		for (DoubleProblem doubleProblem : problems) {
			information = runExperimentForProblem(doubleProblem);
			System.out.println(information.getTimeElapsed() + "\t" + information.isValidPopulationSize() + "\t"
					+ information.getBestSolution().getObjective(0));
		}

	}

	public static ExperimentInformation<DoubleSolution> runExperimentForProblem(Problem<DoubleSolution> problem) {
		CROExperimentRunner runner = new CROExperimentRunner(problem);
		return runner.runExperiment();
	}

}
