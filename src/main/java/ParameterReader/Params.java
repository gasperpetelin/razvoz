package ParameterReader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Params implements IProblemInfo
{

    int numberOfVehicles = 0;
    int numberOfLoadingPorts = 0;
    int numberOfUnloadingPort = 0;

    public Params(String filename) throws IOException
    {

        String vozilaFile = "";
        String mestaFile = "";

        //Read names of vozila and mesta files
        int lineNumber = 0;
        try(BufferedReader br = new BufferedReader(new FileReader("Podatki\\"+filename)))
        {
            for(String line; (line = br.readLine()) != null; )
            {
                if(lineNumber==1)
                    vozilaFile = line;
                if(lineNumber==2)
                    mestaFile = line;
                lineNumber++;
            }
        }

        //Read number of vehicles
        try(BufferedReader br = new BufferedReader(new FileReader("Podatki\\"+vozilaFile)))
        {
            for(String line; (line = br.readLine()) != null; )
            {
                String[] arr = line.trim().split(" ");
                if (arr.length>0)
                    this.numberOfVehicles++;
            }
        }

        //Read number of ports
        try(BufferedReader br = new BufferedReader(new FileReader("Podatki\\"+mestaFile)))
        {
            for(String line; (line = br.readLine()) != null; )
            {
                String[] arr = line.trim().split(" ");
                if (arr.length==0)
                    break;
                if(arr[0].equals("1"))
                    this.numberOfLoadingPorts++;
                if(arr[0].equals("2"))
                    this.numberOfUnloadingPort++;

            }
        }
    }

    public int getNumberOfVehicles()
    {
        return this.numberOfVehicles;
    }

    public int getNumberOfLoadingPorts()
    {
        return this.numberOfLoadingPorts;
    }

    public int getNumberOfUnloadingPort()
    {
        return this.numberOfUnloadingPort;
    }
}
