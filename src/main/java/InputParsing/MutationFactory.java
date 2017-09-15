package InputParsing;

import org.apache.commons.cli.ParseException;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.impl.mutation.IntegerPolynomialMutation;
import org.uma.jmetal.operator.impl.mutation.NullMutation;
import org.uma.jmetal.solution.IntegerSolution;

public class MutationFactory
{
    public static MutationOperator<IntegerSolution> getMutation(String[] parameters) throws ParseException
    {
        if((parameters != null) && (parameters.length != 0))
        {
            String mutationType = parameters[0].toLowerCase();
            switch (mutationType)
            {
                case "null":
                    return new NullMutation<>();
                case "intpol":
                    double defprob = 0.01;
                    double defpert = 20;
                    if(parameters.length == 3)
                    {
                        if(!parameters[1].equals("/"))
                            defprob = Double.parseDouble(parameters[1]);
                        if(!parameters[2].equals("/"))
                            defpert = Double.parseDouble(parameters[2]);
                        return new IntegerPolynomialMutation(defprob, defpert);
                    }
                    if(parameters.length == 1)
                    {
                        return new IntegerPolynomialMutation(defprob, defpert);
                    }
                    break;
                default:
                    throw new ParseException("No mutation operation with name: " + mutationType);
            }
        }
        return null;
    }
}
