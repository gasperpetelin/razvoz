package Util;

import ParameterReader.IProblemInfo;
import Schedule.Drive;
import Schedule.FullSchedule;
import org.uma.jmetal.solution.IntegerSolution;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class IntegerSolutionToFullSchedule
{
    IProblemInfo info;
    public IntegerSolutionToFullSchedule(IProblemInfo info)
    {
        this.info=info;
    }

    public FullSchedule toFullSchedule(IntegerSolution solution)
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

        return new FullSchedule(drives, usedVehicles);
    }
}
