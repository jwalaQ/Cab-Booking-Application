package baseclasses;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Trip {
	private DBAccess dba = new DBAccess();
	public String src, dest;
	public String tripDate;
	public String tripTime;
	private SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
	private SimpleDateFormat t = new SimpleDateFormat("HH:mm:ss");
	private Date tDate = new Date();
	private Date tTime = new Date();
	public User user;
	public Driver drvr;
	public int fare;
	public int dur;
	public int dist;
	public boolean check;
	public long start;
	public long end;
	
	
	public Trip(User u, Driver dr, String pick, String drop) {
		src = pick;
		dest = drop;
		tripDate = f.format(tDate);
		tripTime = t.format(tTime);
		user = u;
		drvr = dr;
		int[] det = dba.getTripDet(pick, drop);
		dist = det[0];
		dur = det[1];
		fare = det[2];
		check = checkFare();
		start = tTime.getTime();
		end = start + dur*1000;
	}
	
//	public Trip(User u, String pick, String drop) {
//		src = pick;
//		dest = drop;
//		tripDate = f.format(tDate);
//		tripTime = t.format(tTime);
//		user = u;
//		drvr = Driver.getDriver(pick);
//		int[] det = dba.getTripDet(pick, drop);
//		dist = det[0];
//		dur = det[1];
//		fare = det[2];
//		check = checkFare();
//	}
	
	public boolean isOngoing() {
		return true;
	}
	
	private boolean checkFare() {
		if (user.wallet < fare)
			return false;
		else
			return true;
	}
	
	public void startTrip() {
		tDate = new Date();
		start = tDate.getTime();
		end = start + dur*(1000);
		drvr.occupy(true);
		drvr.occ = true;
		user.occupy(true);
		user.occ = true;
		dba.setTripValues(this);
	}
	
	
	public void endTrip() {
		user.addMoney(-1*fare);
		user.occupy(false);
		drvr.occupy(false);
		drvr.locUpdate(dest);
		dba.endTrip(this);
		dba.ratingUpdate(this.drvr);
	}

	
	public static void main(String[] args) {
		User u = new User("prajjwal");
		Driver d = Driver.getDriver("Delhi");
		Trip t = new Trip(u, d, "Delhi", "Chennai");
		System.out.println(t.tripDate);
		System.out.println(t.tripTime);
	}
}


