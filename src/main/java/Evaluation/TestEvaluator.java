package Evaluation;

import Schedule.Drive;

import java.util.List;
import java.util.Random;

public class TestEvaluator implements IEvaluator
{
    Random rn = new Random();
    @Override
    public double evaluate(List<Drive> schedule)
    {
        return rn.nextDouble();
    }
}
