package FileLogging;

import org.uma.jmetal.solution.IntegerSolution;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

public interface IFileLogger
{
    void log(IntegerSolution solution);
    void open();
    void close();
}
