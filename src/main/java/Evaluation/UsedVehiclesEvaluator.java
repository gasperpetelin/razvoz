package Evaluation;

import Schedule.FullSchedule;

public class UsedVehiclesEvaluator implements IEvaluator
{

    @Override
    public double evaluate(FullSchedule schedule) {
        return schedule.getNumberOfUsedVehicles();
    }
}
