package InputParsing;

import org.apache.commons.cli.ParseException;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.impl.crossover.IntegerSBXCrossover;
import org.uma.jmetal.operator.impl.crossover.NullCrossover;
import org.uma.jmetal.operator.impl.mutation.IntegerPolynomialMutation;
import org.uma.jmetal.solution.IntegerSolution;

public class CrossoverFactory
{
    public static CrossoverOperator<IntegerSolution> getCrossOver(String[] parameters) throws ParseException
    {
        if((parameters != null) && (parameters.length != 0))
        {
            String crossoverType = parameters[0].toLowerCase();
            switch (crossoverType)
            {
                case "isbx":
                    double defDistIndex = 5;
                    double defProb = 0.01;
                    if (parameters.length == 3)
                    {
                        if(!parameters[1].equals("/"))
                            defProb = Double.parseDouble(parameters[1]);
                        if(!parameters[2].equals("/"))
                            defDistIndex = Double.parseDouble(parameters[2]);
                        return new IntegerSBXCrossover(defProb, defDistIndex);
                    }
                    if (parameters.length == 1)
                    {
                        return new IntegerSBXCrossover(defProb, defDistIndex);
                    }
                    break;

                case "null":
                    return new NullCrossover<>();
                default:
                    throw new ParseException("No crossover operation with name: " + crossoverType);
            }
        }
        return null;
    }
}
