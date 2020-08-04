package baseclasses;

public class User {
	private DBAccess dba = new DBAccess();
	public String name;
	public String user_id;
	public String pwd;
	public String phno;
	public String gender;
	public String email;
	public boolean occ;
	public int wallet;
	
	public User() {
		name = "";
		user_id = "";
		phno = "";
		gender = "";
		email = "";
		wallet  = 0;
		occ = false;
	}
	
	public User(String uid) {
		String[] set = dba.getUser(uid);
		name = set[0];
		user_id = set[1];
		phno = set[2];
		gender = set[3];
		email = set[4];
		if (set[5] == "N")
			occ = false;
		else {
			occ = true;
		}
		wallet = dba.checkWalletBalance(uid);
	}
	
	// marks driver as occupied if b is true
    // else marks as unoccupied
    public void occupy(boolean b) {
    	occ = b;
    	dba.markOccupied(this, b);
    }
    
    public boolean isOccupied() {
    	return dba.userOccupied(this);
    }
	public void addMoney(int amt) {
		wallet += amt;
		dba.addBalance(this, amt);
	}
	
	public Trip getTrip(User u) {
		return DBAccess.getTripDetails(u);
	}
	public void setValues() throws IllegalArgumentException {
		if (dba.getUser(user_id) == null) {
			String values = "('" + name + "','" + user_id + "','";
			values +=  phno + "','" + pwd + "','" + gender + "','" ;
			values += email;
			values += "', 'N');";
			dba.setUserValues(values);
			values = "('" + this.user_id + "'," + wallet + ");";
			dba.addWallet(values);
		}
		else
			throw new IllegalArgumentException();
	}
	
	// abstract void changePWD();
	public void forgotPWD(String np) {
		dba.changePWD(this.user_id, np);
	}
	
	public static void main(String[] args) {
		User u = new User("prajjwal");
		System.out.println(u.phno);
	}

}
