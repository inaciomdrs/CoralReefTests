package br.imd.ufrn.conditions;

import java.util.function.Predicate;

public class PopulationSizeCondition implements Predicate<Integer> {

	private int populationSizeReference;
	private int minimumPopulationSizeReference;

	public PopulationSizeCondition(int populationSizeReference, int minimumPopulationSizeReference) {
		this.populationSizeReference = populationSizeReference;
		this.minimumPopulationSizeReference = minimumPopulationSizeReference;
	}
	
	@Override
	public boolean test(Integer t) {
		return (t >= minimumPopulationSizeReference) && (t <= populationSizeReference);
	}

}
