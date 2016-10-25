package br.ufrn.imd.runner;

import java.util.List;

import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.operator.impl.crossover.IntegerSBXCrossover;
import org.uma.jmetal.operator.impl.mutation.IntegerPolynomialMutation;
import org.uma.jmetal.operator.impl.selection.BinaryTournamentSelection;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.IntegerSolution;

import br.ufrn.imd.experiment.ExperimentInformation;
import br.ufrn.imd.experiment.cro.CROExperiment;

public class CROExperimentRunner {

	private Problem<IntegerSolution> problem;
	
	private CrossoverOperator<IntegerSolution> crossoverOperator;
	private MutationOperator<IntegerSolution> mutationOperator;
	private SelectionOperator<List<IntegerSolution>, IntegerSolution> selectionOperator;
	
	public CROExperimentRunner(Problem<IntegerSolution> problem) {
		this.problem = problem;
		
		double crossoverProbability = 0.9 ;
	    double crossoverDistributionIndex = 20.0 ;
	    crossoverOperator = new IntegerSBXCrossover(crossoverProbability, crossoverDistributionIndex) ;

	    double mutationProbability = 1.0 / problem.getNumberOfVariables() ;
	    double mutationDistributionIndex = 20.0 ;
	    mutationOperator = new IntegerPolynomialMutation(mutationProbability, mutationDistributionIndex) ;

	    selectionOperator = new BinaryTournamentSelection<IntegerSolution>() ;
	}
	
	public ExperimentInformation<IntegerSolution> runExperiment(){
		ExperimentInformation<IntegerSolution> experimentInformation = new ExperimentInformation<IntegerSolution>();
		experimentInformation.setProblemLength(problem.getNumberOfVariables());
		
		CROExperiment<IntegerSolution> experiment = new CROExperiment<>(problem, crossoverOperator, mutationOperator, selectionOperator, experimentInformation);
		experimentInformation.setBestSolution(experiment.executeExperiment());
		
		return experimentInformation;
	}
}
