package Evaluation;

import Schedule.FullSchedule;

public class UsedVehiclesEvaluator implements IEvaluator
{

    @Override
    public double evaluate(FullSchedule schedule)
    {
        if(schedule.getNumberOfUsedVehicles()==0)
            return 100_000_000;
        return schedule.getNumberOfUsedVehicles();
    }
}
