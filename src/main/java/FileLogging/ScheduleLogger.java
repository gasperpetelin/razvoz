package FileLogging;

import ParameterReader.IProblemInfo;
import Schedule.Drive;
import Schedule.FullSchedule;
import Util.IntegerSolutionToFullSchedule;
import org.uma.jmetal.solution.IntegerSolution;

import java.io.File;
import java.io.PrintWriter;

public class ScheduleLogger implements IFileLogger
{
    String foldername;
    IntegerSolutionToFullSchedule mapper;
    int solutionCounter = 0;
    public ScheduleLogger(String directoryName, IProblemInfo info)
    {
        this.mapper = new IntegerSolutionToFullSchedule(info);
        this.foldername = directoryName;
    }

    @Override
    public void log(IntegerSolution solution)
    {
        FullSchedule fullSchedule = this.mapper.toFullSchedule(solution);
        PrintWriter writer;

        String name = "";
        for (int i = 0; i < solution.getNumberOfObjectives(); i++)
        {
            name+=solution.getObjective(i)+"_";
        }

        name += this.solutionCounter + ".txt";

        try
        {
            writer = new PrintWriter(this.foldername+"/"+name, "UTF-8");
            for (Drive d:fullSchedule.getUsedDrives())
            {
                writer.println(d.toString());
            }

            writer.close();
            solutionCounter++;
        }
        catch (Exception e)
        {
            System.out.println("Exception writing to file " + name);
            System.exit(1);
        }
    }

    @Override
    public void open()
    {
        File f = new File(this.foldername);
        f.mkdirs();
    }

    @Override
    public void close()
    {

    }
}
