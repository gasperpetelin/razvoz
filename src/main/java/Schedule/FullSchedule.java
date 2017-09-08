package Schedule;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class FullSchedule
{
    List<Drive> drives;
    HashSet<Integer> usedVehicles;

    public FullSchedule(List<Drive> drives, HashSet<Integer> usedVehicles)
    {
        this.drives = drives;
        this.usedVehicles = usedVehicles;
    }

    public int getNumberOfUsedVehicles()
    {
        return this.usedVehicles.size();
    }

    public List<Drive> getUsedDrives()
    {
        List<Drive> drivesUsed = new ArrayList<>();
        for(Drive d:this.drives)
        {
            if(this.usedVehicles.contains(d.vehicleID))
                drivesUsed.add(d);
        }
        return drivesUsed;
    }
}
