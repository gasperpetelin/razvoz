package FileLogging;

import org.uma.jmetal.solution.IntegerSolution;

import java.io.PrintWriter;

public class IntegerSolutionLogger implements IFileLogger
{
    String name;
    PrintWriter writer;
    boolean writeHeader = true;
    public IntegerSolutionLogger(String name)
    {
        this.name = name;
    }

    @Override
    public void log(IntegerSolution solution)
    {
        if(writeHeader)
        {
            writer.println("#"+solution.getNumberOfVariables() + ","+solution.getNumberOfObjectives());
            writeHeader=false;
        }

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < solution.getNumberOfVariables(); i++)
        {
            builder.append(solution.getVariableValue(i)+",");
        }

        for (int i = 0; i < solution.getNumberOfObjectives(); i++)
        {
            builder.append(solution.getObjective(i)+",");
        }
        builder.setLength(builder.length() - 1);

        try
        {
            writer.println(builder);
        }
        catch (Exception e)
        {
            System.out.println("Exception writing to file " + this.name);
            System.exit(1);
        }
    }

    @Override
    public void open()
    {
        try
        {
            writer = new PrintWriter(this.name, "UTF-8");
        }
        catch (Exception e)
        {
            System.out.println("Exception writing to file " + this.name);
            System.exit(1);
        }
    }

    @Override
    public void close()
    {
        writer.close();
    }


}
