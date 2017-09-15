package InputParsing;

import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.solution.IntegerSolution;

public class AlgorithmsCollection
{
    private Algorithm<IntegerSolution> algorithm;
    private MutationOperator<IntegerSolution> mutation;
    private CrossoverOperator<IntegerSolution> crossover;

    public AlgorithmsCollection(Algorithm<IntegerSolution> algorithm, MutationOperator<IntegerSolution> mutation, CrossoverOperator<IntegerSolution> crossover) {
        this.algorithm = algorithm;
        this.mutation = mutation;
        this.crossover = crossover;
    }

    public MutationOperator<IntegerSolution> getMutation() {
        return mutation;
    }

    public Algorithm<IntegerSolution> getAlgorithm() {
        return algorithm;
    }

    public CrossoverOperator<IntegerSolution> getCrossover() {
        return crossover;
    }
}
