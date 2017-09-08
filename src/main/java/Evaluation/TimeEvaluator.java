package Evaluation;

import Schedule.Drive;
import Schedule.FullSchedule;
import com.sun.jna.Native;
import com.sun.jna.win32.StdCallLibrary;

import java.util.List;

/**
 * Calculates time necessary to execute given schedule by calling external dll
 */
public class TimeEvaluator implements IEvaluator
{
    EvaluationDLL lib;
    @Override
    public double evaluate(FullSchedule schedule)
    {
        //If no vehicles are used, nothing gets done.
        if(schedule.getNumberOfUsedVehicles()==0) {
            return 100_000; //Large number to symbolize inf time.
        }

        List<Drive> drives = schedule.getUsedDrives();

        int[] dllarray = new int[drives.size()*7 + 1];
        int dllpointer = 1;
        for(Drive d:drives)
        {
            int[] longForm = d.toLongForm();
            for (int i = 0; i < longForm.length; i++)
            {
                dllarray[dllpointer+i]=longForm[i];
            }
            dllpointer+=7;
        }
//
        lib.LoadSpored(dllarray, drives.size()*7);
        double eval = lib.Evaluation();
//
        if (eval<0)
            eval = 100_000_000+eval;
        //System.out.println(eval);
        return eval;

    }


    private interface EvaluationDLL extends StdCallLibrary {
        void ReadPackage(String args, int len);
        void LoadSpored(int[] array, int len);
        double Evaluation();
    }


    /**
     *
     * @param dllName location of dll file
     * @param initializationFileName location of file required to initialize variables inside dll file
     */
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
