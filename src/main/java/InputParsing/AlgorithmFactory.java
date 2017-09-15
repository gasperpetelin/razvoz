package InputParsing;

import org.apache.commons.cli.ParseException;
import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.algorithm.multiobjective.nsgaii.NSGAIIBuilder;
import org.uma.jmetal.algorithm.multiobjective.paes.PAESBuilder;
import org.uma.jmetal.algorithm.multiobjective.randomsearch.RandomSearchBuilder;
import org.uma.jmetal.algorithm.multiobjective.spea2.SPEA2Builder;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.IntegerSolution;

import java.util.List;

public class AlgorithmFactory
{
    public static Algorithm<List<IntegerSolution>> getAlgorithm(String[] parameters, Problem<IntegerSolution>problem,
                                                          MutationOperator<IntegerSolution> mut,
                                                          CrossoverOperator<IntegerSolution> cross) throws ParseException
    {
        if((parameters != null) && (parameters.length != 0))
        {
            Algorithm<List<IntegerSolution>> algo = null;
            String type = parameters[0].toLowerCase();
            if(type != null)
            {
                switch (type)
                {
                    case "nsgaii":
                        if(parameters.length==3)
                        {
                            NSGAIIBuilder<IntegerSolution> builder = new NSGAIIBuilder<>(problem, cross, mut);
                            if(!parameters[1].equals("/"))
                                builder.setPopulationSize(Integer.parseInt(parameters[1]));
                            if(!parameters[2].equals("/"))
                                builder.setMaxEvaluations(Integer.parseInt(parameters[2]));
                            algo = builder.build();
                        }
                        break;
                    //case "ibea":
                    //    algo = new IBEABuilder(problem)
                    //            .setArchiveSize(5)
                    //            .setCrossover(cross)
                    //            .setMutation(mut)
                    //            .setMaxEvaluations(maxeval)
                    //            .setPopulationSize(popSize)
                    //            .build();
                    //    break;
                    case "random":
                        if(parameters.length==2)
                        {
                            if(!parameters[1].equals("/"))
                                algo = new RandomSearchBuilder(problem).build();
                            else
                            {
                                RandomSearchBuilder<IntegerSolution> builder = new RandomSearchBuilder(problem);
                                builder.setMaxEvaluations(Integer.parseInt(parameters[1]));
                                algo = builder.build();
                            }
                        }
                        break;
                    //case "gde3":
                    //    algo = new GDE3Builder(problem)
                    //            .setPopulationSize(popSize)
                    //            .setMaxEvaluations(maxeval)
                    //            .build();
                    //    break;
                    case "spea2":
                        if(parameters.length==3)
                        {
                            SPEA2Builder<IntegerSolution> builder = new SPEA2Builder(problem, cross, mut);
                            if(!parameters[1].equals("/"))
                                builder.setPopulationSize(Integer.parseInt(parameters[1]));
                            if(!parameters[2].equals("/"))
                                builder.setMaxIterations(Integer.parseInt(parameters[2]));
                            algo = builder.build();
                        }
                        if(parameters.length==1)
                            algo = new SPEA2Builder(problem, cross, mut).build();
                        break;
                    case "paes":
                        if(parameters.length==4)
                        {
                            PAESBuilder<IntegerSolution> builder = new PAESBuilder<>(problem).setMutationOperator(mut);
                            if(!parameters[1].equals("/"))
                                builder.setArchiveSize(Integer.parseInt(parameters[1]));
                            if(!parameters[2].equals("/"))
                                builder.setMaxEvaluations(Integer.parseInt(parameters[2]));
                            if(!parameters[3].equals("/"))
                                builder.setBiSections(Integer.parseInt(parameters[3]));
                            algo = builder.build();
                        }
                        if(parameters.length==1)
                        {
                            algo = new PAESBuilder(problem).build();
                        }
                        break;
                    default:
                        throw new ParseException("No algorithm with name: " + type);
                }
            }
            return algo;
        }
        return null;

    }
}
