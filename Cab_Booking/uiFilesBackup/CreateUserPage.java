package uiFilesBackup;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.ButtonGroup;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;
import java.awt.Color;
import javax.swing.JPasswordField;
import baseclasses.User;
import javax.swing.SwingConstants;

public class CreateUserPage {

	protected JFrame frame;
	private JLabel lblEmptyField;
	private JTextField nameField;
	private JTextField mobNumberField;
	private JTextField userIDField;
	private JTextField emailField;
	private JPasswordField passwordField;
	private JPasswordField confPasswordField;
	private JRadioButton rdbtnMale;
	private JRadioButton rdbtnFemale;
	private JRadioButton rdbtnOther;
	private ButtonGroup bg1;
	
	private User u = new User();
	private JTextField txtPath;
	
	// checks whether all mandatory fields have been entered
	private boolean checkMandatory() {
		if (nameField.getText().isEmpty() || mobNumberField.getText().isEmpty())
			return false;
		if (userIDField.getText().isEmpty() || emailField.getText().isEmpty())
			return false;
		if (!(rdbtnMale.isSelected() || rdbtnFemale.isSelected() || rdbtnOther.isSelected()))
			return false;
		if (new String(passwordField.getPassword()).isEmpty() || new String(confPasswordField.getPassword()).isEmpty())
			return false;
		return true;
	}
	
	// TODO checks whether mobile number is valid
	private boolean checkMobile() {
		String mob = mobNumberField.getText();
		if (mob.length() != 10)
			return false;
		for (int i = 0; i < 10; i++)
			if (mob.charAt(i) < 48 && mob.charAt(i) > 58)
				return false;
		return true;
	}
	
	// checks special characters ! @ # $ % ^ & * ( )
	private boolean checkSpecial(char[] pwd) {
		char[] special = {'!','@','#', '$','%','^','&','*','(',')'};
		for (int i = 0; i < pwd.length; i++) {
			for (int j = 0; j < special.length; j++)
				if (pwd[i] == special[j])
					return true;
		}
		return false;
	}
	
	// checks whether at least 1 character between given ASCII limit is present
	private boolean checkASCII(int low, int high, char[] pwd) {
		boolean check = false;
		for (int i = 0; i < pwd.length; i++) {
			if(!check)
				for (int j = low; j < high; j++) {
					if (pwd[i] == j)
						check = true;
				}
			else break;
		}
		return check;
	}
	
	// checks the Password constraints
	// returns true if all password constraints are satisfied
	private boolean checkPasswordChars() {

		char[] pwd = passwordField.getPassword();
		// check if length is 8 characters
		if (pwd.length < 8) {
			lblEmptyField.setText("*Password is of insufficient length");
			lblEmptyField.setVisible(true);
			return false;
		}
		// checking special character ! @ # $ % ^ & * ( )
		if (!(checkSpecial(pwd))) {
			lblEmptyField.setText("*Include: !, @, #, $, %, ^, &, *, (, or )");
			lblEmptyField.setVisible(true);
			return false;
		}
		
		// checking numeric character
		if (!checkASCII(48, 58, pwd)) {
			lblEmptyField.setText("*Include: 0, 1, 2, 3, 4, 5, 6, 7, 8, 9");
			lblEmptyField.setVisible(true);
			return false;
		}
		
		// checking upper case alphabet
		if (!checkASCII(65, 91, pwd)) {
			lblEmptyField.setText("*Include an upper case alphabet");
			lblEmptyField.setVisible(true);
			return false;
		}
		// checking lower case alphabet
		if (!checkASCII(97, 123, pwd)){
			lblEmptyField.setText("*Include an lower case alphabet");
			lblEmptyField.setVisible(true);
			return false;
		}
		
		return true;
	}
	
	// checks whether the fields of password and confirmPassword match
	private boolean checkPasswordMatch() {
		String a = new String(passwordField.getPassword());
		String b = new String(confPasswordField.getPassword());
		return a.equals(b);
	}
	
	// prepares string for adding user to database
	private boolean setValues() {
		u.name = nameField.getText();
		u.user_id = userIDField.getText();
		u.phno = mobNumberField.getText();
		u.pwd = new String(passwordField.getPassword());
		u.email = emailField.getText();
		if (rdbtnMale.isSelected())
			u.gender = "M";
		else if(rdbtnFemale.isSelected())
			u.gender = "F";
		else
			u.gender = "O";
		u.wallet = 0;
		try {
			u.setValues();
			return true;
		} catch (IllegalArgumentException iae) {
			JOptionPane.showMessageDialog(null, "Please Choose a different username");
			return false;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CreateUserPage window = new CreateUserPage();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public CreateUserPage() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(238, 232, 170));
		frame.setBounds(200, 200, 435, 474);
		frame.getContentPane().setFont(new Font("Calibri", Font.PLAIN, 13));
		frame.getContentPane().setLayout(null);
		
		JLabel lblName = new JLabel("Name*");
		lblName.setHorizontalAlignment(SwingConstants.RIGHT);
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblName.setBounds(54, 75, 90, 22);
		frame.getContentPane().add(lblName);
		
		JLabel lblMobNumber = new JLabel("Mobile No.*");
		lblMobNumber.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMobNumber.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblMobNumber.setBounds(47, 150, 97, 26);
		frame.getContentPane().add(lblMobNumber);
		
		JLabel lblUserID = new JLabel("User ID*");
		lblUserID.setHorizontalAlignment(SwingConstants.RIGHT);
		lblUserID.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblUserID.setBounds(54, 228, 90, 26);
		frame.getContentPane().add(lblUserID);
		
		JLabel lblPassword = new JLabel("Password*");
		lblPassword.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblPassword.setBounds(51, 267, 90, 22);
		frame.getContentPane().add(lblPassword);
		
		JLabel lblConfPassword = new JLabel("Confirm password*");
		lblConfPassword.setHorizontalAlignment(SwingConstants.RIGHT);
		lblConfPassword.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblConfPassword.setBounds(0, 302, 144, 25);
		frame.getContentPane().add(lblConfPassword);
		
		lblEmptyField = new JLabel("SUBMISSION SUCCESSFUL");
		lblEmptyField.setHorizontalAlignment(SwingConstants.CENTER);
		lblEmptyField.setVisible(false);
		lblEmptyField.setForeground(new Color(50, 205, 50));
		lblEmptyField.setFont(new Font("Segoe UI Black", Font.BOLD, 14));
		lblEmptyField.setBounds(0, 378, 417, 25);
		frame.getContentPane().add(lblEmptyField);
		
		nameField = new JTextField();
		nameField.setHorizontalAlignment(SwingConstants.CENTER);
		nameField.setBounds(182, 74, 197, 26);
		frame.getContentPane().add(nameField);
		nameField.setColumns(10);
		
		mobNumberField = new JTextField();
		mobNumberField.setHorizontalAlignment(SwingConstants.CENTER);
		mobNumberField.setBounds(182, 152, 197, 25);
		frame.getContentPane().add(mobNumberField);
		mobNumberField.setColumns(10);
		
		userIDField = new JTextField();
		userIDField.setHorizontalAlignment(SwingConstants.CENTER);
		userIDField.setBounds(182, 230, 197, 25);
		frame.getContentPane().add(userIDField);
		userIDField.setColumns(10);
		
		rdbtnMale = new JRadioButton("Male");
		rdbtnMale.setBackground(new Color(238, 232, 170));
		rdbtnMale.setBounds(178, 113, 56, 25);
		frame.getContentPane().add(rdbtnMale);
		
		rdbtnFemale = new JRadioButton("Female");
		rdbtnFemale.setBackground(new Color(238, 232, 170));
		rdbtnFemale.setBounds(238, 113, 71, 25);
		frame.getContentPane().add(rdbtnFemale);
		
		rdbtnOther = new JRadioButton("Other");
		rdbtnOther.setBackground(new Color(238, 232, 170));
		rdbtnOther.setBounds(313, 113, 66, 25);
		frame.getContentPane().add(rdbtnOther);
	
		bg1 = new ButtonGroup(); // radio button with single selection
		bg1.add(rdbtnOther);
		bg1.add(rdbtnFemale);
		bg1.add(rdbtnMale);	
		
		JButton btnReset = new JButton("RESET");
		btnReset.setForeground(new Color(255, 0, 0));
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				nameField.setText("");
				mobNumberField.setText("");
				userIDField.setText("");
				emailField.setText("");
				passwordField.setText("");
				confPasswordField.setText("");
				bg1.clearSelection();
			}
		});
		btnReset.setFont(new Font("Calibri", Font.BOLD, 18));
		btnReset.setBounds(142, 339, 103, 26);
		frame.getContentPane().add(btnReset);
		
		JButton btnCancel = new JButton("CANCEL");
		btnCancel.setForeground(new Color(255, 51, 0));
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.setVisible(false);
				frame.dispose();
			}
		});
		btnCancel.setFont(new Font("Calibri", Font.BOLD, 18));
		btnCancel.setBounds(22, 340, 97, 25);
		frame.getContentPane().add(btnCancel);
		
		JButton btnSubmit = new JButton("SUBMIT");
		btnSubmit.setForeground(new Color(34, 139, 34));
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (checkMandatory()) {
					// if mandatory fields are all filled
					if (checkMobile()) {
						// if mobile number is valid
						if (checkPasswordChars()) {
							// if password contains all required characters
							if (checkPasswordMatch()) {
								// if password and confirm password match
								if (setValues()) {
									lblEmptyField.setForeground(Color.GREEN);
									lblEmptyField.setVisible(true);
									lblEmptyField.setText("SUCCESSFULLY REGISTERED!");
									try {
										Thread.sleep(2000);
									} catch (InterruptedException e) {}
									frame.remove(btnSubmit);
									lblEmptyField.setForeground(Color.BLUE);
									lblEmptyField.setVisible(true);
									lblEmptyField.setText("Proceed to LOGIN");
									btnCancel.setForeground(Color.GREEN);
									btnCancel.setBounds(187, 319, 97, 25);
									btnCancel.setText("LOGIN");
								}
							}
							else {
								// if password and confirm password do not match
								lblEmptyField.setText("*Passwords do not match!");
								lblEmptyField.setVisible(true);
							}
						}
						// error message taken care of inside the method
					}
					else {
						lblEmptyField.setText("*Invalid Mobile Number");
						lblEmptyField.setVisible(true);
					}
				}
				else {
					lblEmptyField.setText("*Check all mandatory fields.");
					lblEmptyField.setVisible(true);
				}
			}
		});
		btnSubmit.setFont(new Font("Calibri", Font.BOLD, 18));
		btnSubmit.setBounds(268, 340, 97, 25);
		
		JLabel lblGender = new JLabel("Gender*");
		lblGender.setHorizontalAlignment(SwingConstants.RIGHT);
		lblGender.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblGender.setBounds(54, 110, 90, 25);
		frame.getContentPane().add(lblGender);
		
		
		frame.getContentPane().add(btnSubmit);
		
		JLabel lblEmailId = new JLabel("Email ID*");
		lblEmailId.setHorizontalAlignment(SwingConstants.RIGHT);
		lblEmailId.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblEmailId.setBounds(47, 189, 97, 26);
		frame.getContentPane().add(lblEmailId);
		
		emailField = new JTextField();
		emailField.setHorizontalAlignment(SwingConstants.CENTER);
		emailField.setBounds(182, 190, 197, 26);
		frame.getContentPane().add(emailField);
		emailField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setHorizontalAlignment(SwingConstants.CENTER);
		passwordField.setBounds(182, 266, 197, 26);
		frame.getContentPane().add(passwordField);
		
		confPasswordField = new JPasswordField();
		confPasswordField.setHorizontalAlignment(SwingConstants.CENTER);
		confPasswordField.setBounds(182, 303, 197, 25);
		frame.getContentPane().add(confPasswordField);
		
		txtPath = new JTextField();
		txtPath.setBackground(new Color(153, 255, 255));
		txtPath.setFont(new Font("Tahoma", Font.BOLD, 22));
		txtPath.setHorizontalAlignment(SwingConstants.CENTER);
		txtPath.setText("P.A.T.H");
		txtPath.setBounds(0, 0, 417, 49);
		frame.getContentPane().add(txtPath);
		txtPath.setColumns(10);
	}
}
