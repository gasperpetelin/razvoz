import Evaluation.IEvaluator;
import Evaluation.TimeEvaluator;
import Evaluation.UsedVehiclesEvaluator;
import InputParsing.InputParser;
import InputParsing.OptionsBuilder;
import ParameterReader.Params;
import org.apache.commons.cli.*;
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



    public static void main(String[] args) throws IOException, ParseException {

        InputParser parser = new InputParser(args);
        String fileName = "BestPaket1.txt";
        Params p = new Params(fileName);

        List<IEvaluator> evaluators = new ArrayList<>();
        evaluators.add(new TimeEvaluator("RazvozEvalDll.dll", fileName));
        evaluators.add(new UsedVehiclesEvaluator());


        Problem<IntegerSolution> problem = new SchedulingProblem(evaluators, p, parser.maximumNumberOfCycles());


        Algorithm<List<IntegerSolution>> algorithm = parser.getAlgorithms(problem);

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
