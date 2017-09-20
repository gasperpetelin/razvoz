import Evaluation.IEvaluator;
import Evaluation.TimeEvaluator;
import Evaluation.UsedVehiclesEvaluator;
import FileLogging.IntegerSolutionLogger;
import FileLogging.IFileLogger;
import FileLogging.ScheduleLogger;
import InputParsing.InputParser;
import ParameterReader.Params;
import org.apache.commons.cli.ParseException;
import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.IntegerSolution;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class start
{
    public static void main(String[] args) throws IOException, ParseException
    {
        if(args.length!=0 && (args[0].equals("help") || args[0].equals("-help")))
        {
            System.out.println("********************************************");
            System.out.println("Required parameters:");
            System.out.println("********************************************");
            System.out.println("Name of file with problem data");
            System.out.println("-file filename.txt");
            System.out.println("Maximum number of cycles between points/docks/ports");
            System.out.println("-maxc 10");
            System.out.println("********************************************");
            System.out.println("Optional parameters:");
            System.out.println("********************************************");
            System.out.println("Mutation:");
            System.out.println("    -mut mutation_name [parameters]");
            System.out.println("    Examples:");
            System.out.println("    Null");
            System.out.println("        -mut null");
            System.out.println("    Integer Polynomial Mutation [Probability][Distribution index]");
            System.out.println("        -mut intpol 0.5 /");
            System.out.println("Crossover:");
            System.out.println("    -cross crossover_name [parameters]");
            System.out.println("    Examples:");
            System.out.println("    Null");
            System.out.println("        -cross null");
            System.out.println("    Integer SBXCrossover  [Probability][Distribution index]");
            System.out.println("        -cross isbx / 3");
            System.out.println("Algorithm:");
            System.out.println("    -algo algorithm_name [parameters]");
            System.out.println("    Examples:");
            System.out.println("    NSGAII [Population][Max evaluations]");
            System.out.println("        -algo nsgaii 100 10000");
            System.out.println("    Rand  [Max evaluations]");
            System.out.println("        -algo rand 10000");
            System.out.println("    SPEA2 [Population][Max iterations]");
            System.out.println("        -algo spea2 100 10000");
            System.out.println("    PAES  [Archive size][Max evaluations][BiSections]");
            System.out.println("        -algo paes 20 10000 15");
            System.out.println("********************************************");
            System.out.println("Run examples:");
            System.out.println("********************************************");
            System.out.println("-mut intpol 0.5 / -cross isbx / 3 -algo nsgaii 100 10000 -maxc 5 -file BestPaket1.txt");
            System.out.println("-maxc 5 -file BestPaket1.txt");
            System.exit(0);
        }

        InputParser parser = new InputParser(args);
        String fileName = parser.fileName();
        Params p = new Params(fileName);

        List<IEvaluator> evaluators = new ArrayList<>();
        evaluators.add(new TimeEvaluator("RazvozEvalDll.dll", fileName));
        evaluators.add(new UsedVehiclesEvaluator());

        String timeStamp = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(Calendar.getInstance().getTime());

        IFileLogger logger1 = new IntegerSolutionLogger("evaluatedSolutions_" + timeStamp + ".txt");
        logger1.open();
        Problem<IntegerSolution> problem = new SchedulingProblem(evaluators, p, parser.maximumNumberOfCycles(), logger1);

        Algorithm<List<IntegerSolution>> algorithm = parser.getAlgorithms(problem);

        algorithm.run();
        logger1.close();
        List<IntegerSolution> population = algorithm.getResult();

        IFileLogger logger = new IntegerSolutionLogger("finalSolution_" + timeStamp + ".txt");
        logger.open();
        for(IntegerSolution s:population)
        {
            logger.log(s);
        }
        logger.close();


        IFileLogger map = new ScheduleLogger("finalSolutionSchedules_" + timeStamp, p);
        map.open();
        for(IntegerSolution s:population)
        {
            map.log(s);
        }
        map.close();


    }
}
