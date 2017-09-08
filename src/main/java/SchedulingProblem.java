import Evaluation.IEvaluator;
import ParameterReader.IProblemInfo;
import Schedule.Drive;
import Schedule.FullSchedule;
import org.uma.jmetal.problem.impl.AbstractIntegerProblem;
import org.uma.jmetal.solution.IntegerSolution;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


/**
 * Evaluates objectives for scheduling problem
 */
public class SchedulingProblem extends AbstractIntegerProblem
{
    List<IEvaluator> evaluators;
    IProblemInfo info;

    /**
     * @param evaluators list of evaluators calculating each objective
     * @param info information about problem (number of cars, ports)
     * @param maximumScheduleLength maximum length of schedule
     */
    public SchedulingProblem(List<IEvaluator> evaluators, IProblemInfo info, int maximumScheduleLength)
    {
        this.evaluators = evaluators;
        this.info = info;

        // vector of {cargo_type1, load_port1, unload_port1, ...  ,cargo_typeN, load_portN, unload_portN , V1, V2, ..., Vh}
        //Vh - bitna spremenljivka - uporabimo vozilo h
        this.setNumberOfVariables(3*maximumScheduleLength*info.getNumberOfVehicles() + info.getNumberOfVehicles());
        this.setNumberOfObjectives(this.evaluators.size());

        //Setting limits for individual integr variable
        List<Integer> lowerLimit = new ArrayList<>(getNumberOfVariables()) ;
        List<Integer> upperLimit = new ArrayList<>(getNumberOfVariables()) ;
        for (int i = 0; i < maximumScheduleLength*info.getNumberOfVehicles(); i++)
        {
            //Cargo limits
            lowerLimit.add(1);
            upperLimit.add(3);

            //Load. port number limits
            lowerLimit.add(1);
            upperLimit.add(info.getNumberOfLoadingPorts());

            //Unload. port number limits
            lowerLimit.add(1);
            upperLimit.add(info.getNumberOfUnloadingPort());
        }

        for (int i = 0; i < info.getNumberOfVehicles(); i++)
        {
            lowerLimit.add(0);
            upperLimit.add(1);
        }

        setLowerLimit(lowerLimit);
        setUpperLimit(upperLimit);

    }

    /**
     * Calls all evaluators to calculate their objectives
     * @param solution schedule
     */
    @Override
    public void evaluate(IntegerSolution solution)
    {
        //Mapping solution to drive classes
        int driver = 0;
        List<Drive> drives = new ArrayList<>();

        for (int i = 0; i < solution.getNumberOfVariables() - this.info.getNumberOfVehicles(); i+=3)
        {
            drives.add(new Drive((driver%this.info.getNumberOfVehicles()) + 1,
                    solution.getVariableValue(i),
                    solution.getVariableValue(i+1),
                    solution.getVariableValue(i+2)));
            driver++;
        }

        //Mapping last bits to used vehicles
        HashSet<Integer> usedVehicles = new HashSet<>();
        for (int i = 0; i < info.getNumberOfVehicles(); i++)
        {
            if(solution.getVariableValue(driver*3 + i)==1)
                usedVehicles.add(i+1);
        }

        FullSchedule fullSchedule = new FullSchedule(drives, usedVehicles);

        int obj = 0;
        for(IEvaluator eval:this.evaluators)
        {
            solution.setObjective(obj, eval.evaluate(fullSchedule));
            obj++;
        }
    }
}
