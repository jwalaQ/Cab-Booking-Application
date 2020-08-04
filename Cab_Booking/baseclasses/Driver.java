package baseclasses;

public class Driver{
    public String name;
    public String vehicle;
    public String vehicleID;
    public String phNo;
    public String location;
    public int id;
    public double rating;
    public int votes;
    public int newrating;
    public boolean occ;
    private static DBAccess dba = new DBAccess();
    
    public static void randAllocate() {
    	dba.randAlloc();
    }
    
    public static Driver getDriver(int dID) {
    	String[] dDet = DBAccess.retrieveDriver(dID);
    	if (dDet == null)
    		return null;
    	else {
    		Driver drvr = new Driver(dDet);
    		return drvr;
    	}
    }
 
    public static Driver getDriver(String loc) {
    	String[] dDet = dba.allotDriver(loc);
    	if (dDet == null)
    		return null;
    	else {
    		Driver drvr = new Driver(dDet);
    		return drvr;
    	}
    }
    
    public Driver(String[] details) {
    	id = Integer.parseInt(details[0]);
    	name = details[1];
    	phNo = details[2];
    	vehicle = details[3];
    	vehicleID = details[4];
    	rating = Double.parseDouble(details[5]);
    	votes = Integer.parseInt(details[6]);
    	newrating = 0;
    }
    
    public void locUpdate(String loc) {
    	dba.driverLocUpdate(this, loc);
    }
    // marks driver as occupied if b is true
    // else marks as unoccupied
    public void occupy(boolean b) {
    	occ = b;
    	dba.markOccupied(this, b);
    }
}