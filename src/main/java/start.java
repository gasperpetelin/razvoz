import Evaluation.IEvaluator;
import Evaluation.TestEvaluator;
import Evaluation.TimeEvaluator;
import ParameterReader.Params;
import com.sun.jna.Native;
import com.sun.jna.win32.StdCallLibrary;
import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.algorithm.multiobjective.nsgaii.NSGAIIBuilder;
import org.uma.jmetal.operator.CrossoverOperator;
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
    public static void main(String[] args) throws IOException {
        String fileName = "BestPaket1.txt";
        Params p = new Params(fileName);
        TimeEvaluator te = new TimeEvaluator("C:\\Users\\Gasper\\Desktop\\PLES-Razvoz\\RazvozEvalDll.dll", fileName);
        int[] vehicles = {1};

        List<IEvaluator> ls = new ArrayList<>();
        ls.add(te);
        ls.add(new TestEvaluator());
        Problem<IntegerSolution> problem = new SchedulingProblem(ls, p, 50, vehicles);
        Algorithm<List<IntegerSolution>> algorithm;
        CrossoverOperator<IntegerSolution> crossover;
        MutationOperator<IntegerSolution> mutation;

        crossover = new NullCrossover<>();
        mutation = new IntegerPolynomialMutation(0.5, 10) ;


        algorithm = new NSGAIIBuilder<IntegerSolution>(problem, crossover, mutation)
                .setPopulationSize(10)
                .build() ;
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



        //try {
        //    EvaluationDLL lib = Native.loadLibrary("C:\\Users\\Gasper\\Desktop\\PLES-Razvoz\\RazvozEvalDll.dll", EvaluationDLL.class);
        //    String s = "BestPaket.txt";
//
        //    byte[] arr = new byte[s.length()];
        //    lib.ReadPackage(s, s.length());
        //    int st[] = {0, 1, 0, 1, 4, 0, 1, 0, 2, 0, 2, 1, 0, 1, 0, 3, 0, 1, 1, 0, 4, 0, 4, 0, 2, 2, 0, 1};
        //    lib.LoadSpored(st, st.length);
        //    double d = lib.Evaluation();
        //    System.out.println(d);
        //}
        //catch (Exception ex)
        //{
        //    System.out.println(ex.getStackTrace());
        //}


        //start hello = new start();
        //String s = "BestPaket.txt";
        ////hello.ReadPackage(s, s.length());
        //hello.Evaluation();


        System.out.println("sdg");

    }
}
