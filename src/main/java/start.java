import Evaluation.IEvaluator;
import Evaluation.TimeEvaluator;
import Evaluation.UsedVehiclesEvaluator;
import ParameterReader.Params;
import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.algorithm.multiobjective.nsgaii.NSGAIIBuilder;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.impl.crossover.NullCrossover;
import org.uma.jmetal.operator.impl.mutation.IntegerPolynomialMutation;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.IntegerSolution;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class start
{
    public static void main(String[] args) throws IOException
    {
        String fileName = "BestPaket1.txt";
        Params p = new Params(fileName);
        TimeEvaluator te = new TimeEvaluator("RazvozEvalDll.dll", fileName);

        List<IEvaluator> ls = new ArrayList<>();

        ls.add(te);
        ls.add(new UsedVehiclesEvaluator());

        Problem<IntegerSolution> problem = new SchedulingProblem(ls, p, 5);
        MutationOperator<IntegerSolution> mutation = new IntegerPolynomialMutation(0.5, 10) ;


        Algorithm<List<IntegerSolution>> algorithm = new NSGAIIBuilder<>(problem, new NullCrossover<IntegerSolution>(), mutation)
                .setPopulationSize(10)
                .build();

        algorithm.run();
        List<IntegerSolution> population = algorithm.getResult();

        for(IntegerSolution s:population)
        {
            for (int i = 0; i < s.getNumberOfObjectives(); i++)
            {
                System.out.print(s.getObjective(i) + " ");
            }
            System.out.println();
        }
    }
}
