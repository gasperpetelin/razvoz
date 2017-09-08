import Evaluation.IEvaluator;
import ParameterReader.IProblemInfo;
import Schedule.Drive;
import org.uma.jmetal.problem.impl.AbstractIntegerProblem;
import org.uma.jmetal.solution.IntegerSolution;

import java.util.ArrayList;
import java.util.List;

public class SchedulingProblem extends AbstractIntegerProblem
{
    List<IEvaluator> evaluators;
    int[] vehicles;
    public SchedulingProblem(List<IEvaluator> evaluators, IProblemInfo info, int maximumScheduleLength, int[] vehicles)
    {
        this.evaluators = evaluators;
        this.vehicles = vehicles;

        // vector of {cargo_type1, load_port1, unload_port1, ...  ,cargo_typeN, load_portN, unload_portN}
        this.setNumberOfVariables(3*maximumScheduleLength);
        this.setNumberOfObjectives(this.evaluators.size());

        List<Integer> lowerLimit = new ArrayList<>(getNumberOfVariables()) ;
        List<Integer> upperLimit = new ArrayList<>(getNumberOfVariables()) ;

        for (int i = 0; i < maximumScheduleLength; i++) {
            //Cargo
            lowerLimit.add(1);
            upperLimit.add(3);

            //Load. port
            lowerLimit.add(1);
            upperLimit.add(info.getNumberOfLoadingPorts());

            //Unload. port
            lowerLimit.add(1);
            upperLimit.add(info.getNumberOfUnloadingPort());
        }

        setLowerLimit(lowerLimit);
        setUpperLimit(upperLimit);

    }

    @Override
    public void evaluate(IntegerSolution solution)
    {
        List<Drive> drives = new ArrayList<>();
        for (int i = 0; i < solution.getNumberOfVariables(); i+=3)
        {
            //TODO change ID
            drives.add(new Drive(1, solution.getVariableValue(i),solution.getVariableValue(i+1),solution.getVariableValue(i+2)));
        }

        int obj = 0;
        for(IEvaluator eval:this.evaluators)
        {
            solution.setObjective(obj, eval.evaluate(drives));
            obj++;
        }

    }

}
