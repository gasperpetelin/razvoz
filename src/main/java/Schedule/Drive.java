package Schedule;

public class Drive
{
    int vehicleID;
    int cargoType;
    int loadPort;
    int unloadPort;

    public Drive(int vehicleID, int cargoType, int loadPort, int unloadPort)
    {
        this.vehicleID = vehicleID;
        this.cargoType = cargoType;
        this.loadPort = loadPort;
        this.unloadPort = unloadPort;
    }

    public int[] toLongForm()
    {
        int[] arr = new int[7];
        arr[0]=this.vehicleID;
        arr[1]=0;//cikel
        arr[2]=cargoType;
        arr[3]= loadPort;
        arr[4]=0;//load prioriteta
        arr[5]= unloadPort;
        arr[6]=0;//unload prioritet
        return arr;
    }
}
