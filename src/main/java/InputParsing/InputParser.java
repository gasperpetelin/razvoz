package InputParsing;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.ParseException;
import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.IntegerSolution;

import java.util.List;

public class InputParser
{
    CommandLineParser parser = new DefaultParser();
    CommandLine line;
    public InputParser(String[] args)
    {
        try
        {
            line = parser.parse(OptionsBuilder.getOptions(), args );
        }
        catch( ParseException exp )
        {
            System.err.println( "Parsing failed.  Reason: " + exp.getMessage() );
            System.exit(1);
        }
    }

    public int maximumNumberOfCycles()
    {
        return Integer.parseInt(line.getOptionValue("maxc"));
    }

    public String fileName()
    {
        return line.getOptionValue("file");
    }

    public Algorithm<List<IntegerSolution>> getAlgorithms(Problem<IntegerSolution> problem)
    {
        try
        {
            MutationOperator<IntegerSolution> mutalgo =  MutationFactory.getMutation(line.getOptionValues("mut"));
            CrossoverOperator<IntegerSolution> crossalgo = CrossoverFactory.getCrossOver(line.getOptionValues("cross"));
            Algorithm<List<IntegerSolution>> algo = AlgorithmFactory.getAlgorithm(line.getOptionValues("algo"), problem, mutalgo, crossalgo);
            return algo;
        }
        catch( ParseException exp )
        {
            System.err.println( "Parsing failed.  Reason: " + exp.getMessage() );
            System.exit(1);
        }
        return null;
    }

}
