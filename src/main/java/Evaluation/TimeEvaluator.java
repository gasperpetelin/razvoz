package Evaluation;

import Schedule.Drive;
import com.sun.jna.Native;
import com.sun.jna.win32.StdCallLibrary;

import java.util.List;

public class TimeEvaluator implements IEvaluator
{
    @Override
    public double evaluate(List<Drive> schedule)
    {
        int[] dllarray = new int[schedule.size()*7 + 1];
        int dllpointer = 1;
        for(Drive d:schedule)
        {
            int[] longForm = d.toLongForm();
            for (int i = 0; i < longForm.length; i++)
            {
                dllarray[dllpointer+i]=longForm[i];
            }
            dllpointer+=7;
        }

        lib.LoadSpored(dllarray, schedule.size()*7);
        double eval = lib.Evaluation();

        if (eval<0)
            eval = 100_000_000+eval;
        return eval;
    }

    public interface EvaluationDLL extends StdCallLibrary {
        void ReadPackage(String args, int len);
        void LoadSpored(int[] array, int len);
        double Evaluation();
    }

    EvaluationDLL lib;
    public TimeEvaluator(String dllName, String initializationFileName)
    {
        try
        {
            lib = Native.loadLibrary(dllName, EvaluationDLL.class);
            lib.ReadPackage(initializationFileName, initializationFileName.length()+1);
        }
        catch (Exception ex)
        {
            System.out.println(ex.getStackTrace());
            System.exit(0);
        }
    }

}
