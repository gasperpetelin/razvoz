package Evaluation;

import Schedule.Drive;
import java.util.List;

public interface IEvaluator
{
    double evaluate(List<Drive> schedule);
}
