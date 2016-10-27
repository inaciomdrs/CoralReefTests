package br.ufrn.imd.runner;

import java.util.List;

import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.operator.impl.crossover.SBXCrossover;
import org.uma.jmetal.operator.impl.mutation.PolynomialMutation;
import org.uma.jmetal.operator.impl.selection.BinaryTournamentSelection;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.DoubleSolution;

import br.ufrn.imd.experiment.ExperimentInformation;
import br.ufrn.imd.experiment.cro.CROExperiment;

public class CROExperimentRunner {

	private Problem<DoubleSolution> problem;
	
	private CrossoverOperator<DoubleSolution> crossoverOperator;
	private MutationOperator<DoubleSolution> mutationOperator;
	private SelectionOperator<List<DoubleSolution>, DoubleSolution> selectionOperator;
	
	public CROExperimentRunner(Problem<DoubleSolution> problem) {
		this.problem = problem;
		
		double crossoverProbability = 0.9 ;
	    double crossoverDistributionIndex = 20.0 ;
	    crossoverOperator = new SBXCrossover(crossoverProbability, crossoverDistributionIndex) ;

	    double mutationProbability = 1.0 / problem.getNumberOfVariables() ;
	    double mutationDistributionIndex = 20.0 ;
	    mutationOperator = new PolynomialMutation(mutationProbability, mutationDistributionIndex) ;

	    selectionOperator = new BinaryTournamentSelection<DoubleSolution>() ;
	}
	
	public ExperimentInformation<DoubleSolution> runExperiment(){
		ExperimentInformation<DoubleSolution> experimentInformation = new ExperimentInformation<DoubleSolution>();
		experimentInformation.setProblemLength(problem.getNumberOfVariables());
		
		CROExperiment<DoubleSolution> experiment = new CROExperiment<>(problem, crossoverOperator, mutationOperator, selectionOperator, experimentInformation);
		experimentInformation.setBestSolution(experiment.executeExperiment());
		
		return experimentInformation;
	}
}
