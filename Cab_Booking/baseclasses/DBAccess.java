package baseclasses;
import java.sql.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Random;

// contains methods which perform the common queries
// on the databases

public class DBAccess {
	static Connection con;
	static Statement stmt;
	private static String query;
	static {
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cab_book?autoReconnect=true&useSSL=false", "root", "test123");
			stmt = con.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
			}
		checkRandReq();
		updateTrips();
	}
	
	
	// checks whether random re-allocation of drivers is required
	// and performs necessary action
	private static void checkRandReq() {
		query = "SELECT MAX(tripNo) FROM trips";
		try {
			ResultSet rest = stmt.executeQuery(query);
			rest.first();
			int num = rest.getInt(1);
			rest.close();
			if (num % 3 == 0)
				Driver.randAllocate();
		} catch (Exception e) {
			//e.printStackTrace();
		}
	}
	
	private static void updateTrips() {
		SimpleDateFormat sdf = new SimpleDateFormat();
		String q = "SELECT * FROM trips WHERE tripOngoing = 'Y';";
		User u;
		try {
			ResultSet rest = stmt.executeQuery(q);
			while(rest.next()) {
				u = new User(rest.getString(2));
				Trip tn = getTripDetails(u);
				Date tDate = sdf.parse(rest.getString(6) + rest.getString(7));
				Date curDate = new Date();
				int x = tDate.compareTo(curDate);
				if (x < 1) {
					tn.endTrip();
				}
			}
		} catch (Exception e) {
//			e.printStackTrace();
		}
	}
	
	protected static Trip getTripDetails(User u) {
		query = "SELECT * FROM trips WHERE User_ID = '" + u.user_id + "' ORDER BY TripTime DESC;";
		try {
			ResultSet rest = stmt.executeQuery(query);
			if (rest.first())  {
				int q = rest.getInt(3);
				String[] drdet = retrieveDriver(q);
				Driver dr = new Driver(drdet);
				String pickup = rest.getString(4);
				String drop = rest.getString(5);
				Trip t =  new Trip(u, dr, pickup, drop);
				return t;
			}
			else return null;
		} catch(Exception e) {
//			e.printStackTrace();
			return null;
		}
	}
	
	protected static String[] retrieveDriver(int id) {
		query = "SELECT * FROM drivers WHERE d_no = " + id + ";";
		ResultSet rest;
		try {
			rest = stmt.executeQuery(query);
			rest.first();
			String[] drvDet = new String[7];
			drvDet[0] = String.valueOf(rest.getInt(1)); // d_no
			drvDet[1] = rest.getString(2); // d_name
			drvDet[2] = rest.getString(8); // phNo
			drvDet[3] = rest.getString(3); // vehicle
			drvDet[4] = String.valueOf(rest.getInt(4)); // vehicle_id
			drvDet[5] = String.valueOf(rest.getDouble(5)); // rating
			drvDet[6] = String.valueOf(rest.getInt(9)); // votes
			
			return drvDet;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	// creates a new trip entry in the trip table
	protected void setTripValues(Trip tr) {
		int id;
		query = "SELECT MAX(tripNo) FROM trips";
		try {
			ResultSet res = stmt.executeQuery(query);
			if(!res.first())
				id = 1;
			else
				id = res.getInt(1) + 1;
			query = "INSERT INTO trips VALUES (" + id + ",'" + tr.user.user_id + "',";
			query += tr.drvr.id + ",'" + tr.src + "','" + tr.dest + "','" + tr.tripDate + "','";
			query += tr.tripTime + "','Y');";
			
			stmt.executeUpdate(query);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	// Marks tripOngoing column in trips table to N
	protected void endTrip(Trip tr) {
		query = "UPDATE trips SET tripOngoing = 'N' WHERE User_ID = '" + tr.user.user_id + "';";
		try {
			stmt.executeUpdate(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void ratingUpdate(Driver drvr) {
		int dID = drvr.id;
		int n = drvr.newrating;
		if (n == 0)
			return;
		double r=0; // updated rating
		double p = drvr.rating; // current rating
		int q = drvr.votes; // votes
		try {
	    	r=((p*q)+n)/(q+1);
	    	String sql1 = "UPDATE drivers SET rating = "+ r + " WHERE d_no = " + dID;
			stmt.executeUpdate(sql1);	//Updates ratings
			String sql2 = "UPDATE drivers SET votes = " + (q + 1) + " WHERE d_no = " + dID;
			stmt.executeUpdate(sql2);	//Increases no of ratings by 1
			}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	// returns an int containing the fare of trip from pickup to drop
	protected int[] getTripDet(String pickup, String drop) {
		int[] tripDet = new int[3];
		query = "SELECT distance, duration, fare_rs FROM routes WHERE src_name = '" + pickup + "' AND dest_name = '" + drop + "';";
		try {
			ResultSet rest = stmt.executeQuery(query);
			rest.first();
			tripDet[0] = rest.getInt(1); // distance
			tripDet[1] = rest.getInt(2); // duration
			tripDet[2] = rest.getInt(3); // fare
			
			return tripDet;
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		} 
	}
	
	// returns the user details in an array of strings
	protected String[] getUser(String uid) {
		try {
			ResultSet rest = stmt.executeQuery("SELECT Name, User_ID, Mob_Number, Gender, eMail, occ FROM user WHERE User_ID='" + uid + "';");
			if(rest.first()) {
				String[] userDet = new String[6];
				for (int i = 1; !rest.isAfterLast() && i < 7; i++) {
					userDet[i-1] = rest.getString(i);
				}
				return userDet;
			}
			else return null;
		} catch (Exception e) {
			return null;
		}
	}
	
	
	protected boolean userOccupied(User u) {
		query = "SELECT * FROM user WHERE User_ID = '" + u.user_id + "' AND occ = 'Y'";
		try {
			ResultSet rest = stmt.executeQuery(query);
			return rest.first();
		} catch(Exception e) {
			return false;
		}
	}
	// adds specified amount 'amt' to the wallet of user u
	public void addBalance(User u, int amt) {
		String update = "UPDATE wallet SET Amount=Amount+"+amt+" WHERE user_id='" + u.user_id + "';";
		try {
		stmt.executeUpdate(update);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// checks wallet balance of user whose user name is uid
	// returns amount, else returns -10000
	public int checkWalletBalance(String uid) {
		query = "SELECT Amount FROM wallet WHERE User_ID ='" + uid + "';";
		try {
			ResultSet res = stmt.executeQuery(query);
			res.first();
			return Integer.parseInt(res.getString(1));
		} catch (Exception e){
			e.printStackTrace();
			return -10000;
		}
	}
	
	// Returns the set of locations as an array of strings
	public String[] getLocations() {
		try {
			ResultSet res = stmt.executeQuery("SELECT COUNT(*) FROM locations");
			res.first();
			int row = Integer.parseInt(res.getString(1));
			String[] loclist = new String[row];
			query = "SELECT loc_name FROM locations"; 
			res = stmt.executeQuery(query);	
		    int i=0;
		    while(res.next()){
		    	loclist[i] = res.getString(1);
		    	i+=1;
		    }
		    return loclist;
		}catch (Exception e) {
			e.printStackTrace();
			System.out.print("Cannot Show");
			return null;
		}
	}

	// marks driver d as occupied or unoccupied based on boo value
	protected void markOccupied(User u, boolean boo) {
			query = "UPDATE user SET occ = '";
			if (boo)
				query+= "Y' ";
			else
				query+="N' ";
			query += "WHERE User_Id = '" + u.user_id + "';";
			try {
				stmt.executeUpdate(query);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	// marks driver d as occupied or unoccupied based on boo value
	protected void markOccupied(Driver d, boolean boo) {
		query = "UPDATE drivers SET occ = '";
		if (boo)
			query+= "Y' ";
		else
			query+="N' ";
		query += "WHERE d_no = " + d.id + ";";
		try {
			stmt.executeUpdate(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// d_no s of all drivers
	private int[] getDriver() {
		try {
			ResultSet res = stmt.executeQuery("SELECT COUNT(*) FROM drivers");
			res.first();
			int row = Integer.parseInt(res.getString(1));
			int[] drvlist = new int[row];
			query = "SELECT d_no FROM drivers"; 
			res = stmt.executeQuery(query);	
		    int i=0;
		    while(res.next()){
		    	drvlist[i] = res.getInt(1);
		    	i+=1;
		    }
		    return drvlist;
		}catch (Exception e) {
			e.printStackTrace();
			System.out.print("Cannot Show");
			return null;
		}
	}
	// random allotment of locations to drivers
	protected void randAlloc() {
		String[] locs = getLocations();
		int[] drvr = getDriver();
		try {
			ResultSet res = stmt.executeQuery("SELECT COUNT(*) FROM drivers");
			res.first();
			int cnt = res.getInt(1);
			Random r = new Random();
			int rndm;
			for(int i = 0; i < cnt; i++){
    			rndm = r.nextInt(locs.length);
//    			System.out.println(arr[randomNumber]);									
    			query = "UPDATE drivers SET loc = '" + locs[rndm] + "' WHERE occ = 'N' AND d_no = '" + drvr[i] + "'";	//***********//Assigning Random Locations to Drivers
    			stmt.executeUpdate(query);
    			i++;
    		}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// updates driver's location at the end of the trip
	protected void driverLocUpdate(Driver d, String loc) {
		query = "UPDATE drivers SET loc = '" + loc + "' WHERE d_no = " + d.id + ";";
		try {
			stmt.executeUpdate(query);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	// returns a String[] array containing details of the driver present at that location
	// or in the nearest location
	protected String[] allotDriver(String loc) {
		try {
			ResultSet rest = stmt.executeQuery("SELECT COUNT(*) FROM drivers WHERE loc = '" + loc + "' AND occ = 'N';");
			rest.first();
				int cnt = rest.getInt(1);
				if (cnt == 0) {
					int lNo = getLocations().length;
					String locDesc[] = new String[lNo-1];
					rest = stmt.executeQuery("SELECT dest_name FROM routes WHERE src_name = '" + loc + "' ORDER BY distance;");
					if (!rest.first())
						return null;
					rest.beforeFirst();
					int i = 0;
					while(rest.next())
						locDesc[i++] = rest.getString(1);
					i = 0;
					do {
						query = "SELECT * FROM drivers where loc = '" + locDesc[i++] + "' AND occ = 'N'";
						rest = stmt.executeQuery(query);
					} while (!rest.first());
				}
				else if (cnt >= 1) {
					query = "SELECT * FROM drivers WHERE loc='" + loc + "' and occ = 'N'";
					query += " ORDER BY rating DESC";
					rest = stmt.executeQuery(query);
					rest.first();
				}
				String[] drvDet = new String[7];
				drvDet[0] = String.valueOf(rest.getInt(1)); // d_no
				drvDet[1] = rest.getString(2); // d_name
				drvDet[2] = rest.getString(8); // phNo
				drvDet[3] = rest.getString(3); // vehicle
				drvDet[4] = String.valueOf(rest.getInt(4)); // vehicle_id
				drvDet[5] = String.valueOf(rest.getDouble(5)); // rating
				drvDet[6] = String.valueOf(rest.getInt(9)); // votes
				return drvDet;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
//	}
	// checks if email-id string for user uid is correct.
	// returns true if correct, else false
 	protected boolean checkEmail(String uid) {
		query = "SELECT eMail FROM user WHERE BINARY user_id='" + uid + "';";
		ResultSet res;
		try {
			res = stmt.executeQuery(query);
			if (res.first()) {
				return true;
			}
			else {
				System.out.println("No Email Id associated with this e-mail.");
				return false;
			}
		} catch (Exception e) {
			System.out.println("error in checkUser");
			e.printStackTrace();
			return false;
		}
		
	}
	
 	// adds new entry into wallet when new user is created.
 	protected void addWallet(String values) {
		query = "INSERT INTO wallet VALUES " + values;
		try {
			stmt.executeUpdate(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// sets the values of new user as those entered during registering
	protected void setUserValues(String values) {
		query = "INSERT INTO user VALUES " + values;
		try {
			stmt.executeUpdate(query);
		} catch (Exception e) {
			// Exception arises when duplicate user_id is passed
			e.printStackTrace();
		}
	}

	// verifies whether login credentials u_id and pass are valid
	// returns 0 if userID doesn't match
	// returns 1 if userID matches but password doesn't
	// returns 2 if both are correct
	public int verifyLoginCred(String u_id, String pass) {
		int key = 0;
		if (checkUser(u_id)) {
			key = 1;
			if (checkLoginPWD(u_id, pass)) {
				key = 2;
			}
		}
		return key;
	}
		
	// checks whether the user_id exists in the database or not
	// returns true if exists, false otherwise
	public boolean checkUser(String u_id) {
		query = "SELECT user_id FROM user WHERE BINARY user_id='" + u_id + "';";
		ResultSet res;
		try {
			res = stmt.executeQuery(query);
			if (res.first()) {
				return true;
			}
			else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	// checks whether password entered for user with id uid is correct
	// returns true if matching, false otherwise
	private boolean checkLoginPWD(String uid, String pass) {
		query = "SELECT pwd FROM user WHERE BINARY user_id='" + uid + "';";
		ResultSet res;
		try {
			res = stmt.executeQuery(query);
			res.first();
			String pwd = res.getString(1);
			if (pass.equals(pwd))
				return true;
			else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	// 
	public void changePWD(String u_id, String n_password) {
	// TODO 
		String sql = "UPDATE user SET pwd = '" + n_password + "' WHERE user_id='" + u_id +"'"; 
		try {
			stmt.executeUpdate(sql);
		}
		catch (SQLException e1) { e1.printStackTrace();}
	}
	
	
	public static void main(String[] args) {
		DBAccess dba = new DBAccess();
		int[] dArr = dba.getDriver();
		for (int i : dArr)
			 System.out.println(i);
	}
}
