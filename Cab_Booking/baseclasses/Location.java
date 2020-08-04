package baseclasses;
import java.sql.*;
import java.util.Scanner;
import java.io.*;

public class Location {
	String name; 		// name of location
	int ID; 			// location ID
	DBAccess dba = new DBAccess();		// database access class instance to pass queries
	ResultSet rest;  	// to read result set
	String query;
//	Location(){
//		ID = getNewID()+1;
//		name = getName();
//		query = "INSERT INTO locations VALUE ('" + ID + "','" + name + "');";
//		try {
//			DBAccess.stmt.executeUpdate(query);
//		} catch (Exception e) {
//			System.out.println(e);
//		}
//	}
	public String[] getLocations() {
		String[] locs = dba.getLocations();
		return locs;
	}
	void showLocations() {
		// copy and check file reading in another class
		File f = new File("E:/Coding/eclipseWS/Cab_Booking/sqlList/showLocations.txt");
		try {
				Scanner sc = new Scanner(f);
				query = sc.nextLine();
				try {
					rest = DBAccess.stmt.executeQuery(query);
					while (rest.next())
						System.out.println(rest.getString(1) + " " + rest.getString(2));
					sc.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		// System.out.println();
//	String getName() {
//		Scanner sc = new Scanner(System.in);
//		System.out.println("Please Enter Name: ");
//		String nm = sc.next();
//		sc.close();
//		return nm;
//	}
	
//	private int getNewID() {
//		String q = "SELECT MAX(loc_id) FROM locations";
//		try {
//			ResultSet rest = DBAccess.stmt.executeQuery(q);
//			rest.first();
//			return rest.getInt(1);
//		} catch (Exception e) {
//			System.out.println("Cannot add. Current table looks like this:");
//			try {
//				DBAccess.stmt.executeQuery("SELECT * FROM locations");
//			} catch (Exception ex) {
//				ex.printStackTrace();
//			}
//			e.printStackTrace();
//			return -2;
//		}
//	}
	public static void main(String args[]) {
		Location lc = new Location();
		String[] arr = lc.getLocations();
		/*
		String str = "SELECT * FROM locations";
		System.out.println("After adding location, new table is: ");
		try {
			ResultSet res = DBAccess.stmt.executeQuery(str);
			while (res.next())
				System.out.println(res.getString(1) + " " + res.getString(2));
		} catch(Exception e) {
			e.printStackTrace();
		}
		*/
		for (String s : arr)
			System.out.println(s);
		// System.out.println("The location is " + lc.name);
	}
}
