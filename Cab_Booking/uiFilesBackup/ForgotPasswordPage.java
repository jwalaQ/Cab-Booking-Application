package uiFilesBackup;

import java.awt.EventQueue;
import baseclasses.User;
import java.awt.Font;
import java.sql.*;
import baseclasses.DBAccess;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import java.awt.Color;

public class ForgotPasswordPage {

	public JFrame frame;
	private JTextField txtUserid;
	private JTextField txtEmailid;
	private String uid=null;
	private String eid=null;
	private JTextField txt;
	private JPasswordField p2;
	private JPasswordField p1;
	private String t1;
	private String t2;
	private DBAccess dba = new DBAccess();
	
	// checks whether user id and email belong to the same user
	// returns 2 if match with database
	// returns any other number if not
	public boolean checkUserIDEmail(String uid,String email)
	{
		if (dba.checkUser(uid)) {
			User usr = new User(uid);
			return(email.equals(usr.email));
		}
		else return false;
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ForgotPasswordPage window = new ForgotPasswordPage();
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
	public ForgotPasswordPage() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(204, 204, 204));
		frame.setBounds(100, 100, 407, 281);
		frame.getContentPane().setLayout(null);
		txt = new JTextField();
		txt.setForeground(new Color(255, 255, 255));
		txt.setBackground(new Color(255, 153, 102));
		txt.setFont(new Font("Open Sans", Font.BOLD, 24));
		txt.setHorizontalAlignment(SwingConstants.CENTER);
		txt.setEditable(false);
		 
			JButton btnVfy = new JButton("Update");
			btnVfy.setForeground(new Color(0, 0, 0));
			btnVfy.setBackground(new Color(255, 153, 102));
			btnVfy.setFont(new Font("Open Sans", Font.BOLD, 13));
			btnVfy.setBounds(94, 200, 202, 25);
			frame.getContentPane().add(btnVfy);
			btnVfy.setText("Update");
		
		txt.setText("Enter Details");
		txt.setBounds(0, 0, 389, 41);
		frame.getContentPane().add(txt);
		txt.setColumns(10);
		p2 = new JPasswordField();
		p2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				t2 = new String(p2.getPassword());		
			}
		});
		
		
		JLabel lblEnterNewPassword = new JLabel("Enter New Password");
		lblEnterNewPassword.setForeground(new Color(0, 0, 0));
		lblEnterNewPassword.setBackground(new Color(204, 102, 0));
		lblEnterNewPassword.setFont(new Font("Open Sans", Font.BOLD, 13));
		lblEnterNewPassword.setHorizontalAlignment(SwingConstants.CENTER);
		lblEnterNewPassword.setBounds(42, 138, 140, 27);
		frame.getContentPane().add(lblEnterNewPassword);
		
		JLabel lblConfirmPassword = new JLabel("Confirm Password");
		lblConfirmPassword.setForeground(new Color(0, 0, 0));
		lblConfirmPassword.setBackground(new Color(0, 153, 255));
		lblConfirmPassword.setFont(new Font("Open Sans", Font.BOLD, 13));
		lblConfirmPassword.setHorizontalAlignment(SwingConstants.CENTER);
		lblConfirmPassword.setBounds(50, 171, 140, 16);
		frame.getContentPane().add(lblConfirmPassword);
		
		txtUserid = new JTextField();
		txtUserid.setFont(new Font("Segoe UI Historic", Font.PLAIN, 13));
		txtUserid.setText("User-ID");
		txtUserid.addFocusListener(new FocusAdapter() {
//			@Override
			public void focusGained(FocusEvent arg0) {
				   if(txtUserid.getText().trim().equals("User-ID"))
						txtUserid.setText("");
			        else {}
			}
//			@Override
			public void focusLost(FocusEvent e) {
				 if(txtUserid.getText().trim().equals(""))
					 txtUserid.setText("User-ID");
			        else {}
			}
		});
		
		txtUserid.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnVfy.setText("Update");
			}
		});
		
		txtUserid.setHorizontalAlignment(SwingConstants.CENTER);
		txtUserid.setBounds(42, 54, 313, 29);
		frame.getContentPane().add(txtUserid);
		txtUserid.setColumns(10);
		
		txtEmailid = new JTextField();
		txtEmailid.setFont(new Font("Segoe UI Historic", Font.PLAIN, 13));
		
		txtEmailid.addFocusListener(new FocusAdapter() {
//			@Override
			public void focusGained(FocusEvent arg0) {
				   if(txtEmailid.getText().trim().equals("Email-ID"))
					   txtEmailid.setText("");
			        else {}
			}
//			@Override
			public void focusLost(FocusEvent e) {
				 if(txtEmailid.getText().trim().equals(""))
					 txtEmailid.setText("Email-ID");
			        else {}
			}
		});
		txtEmailid.setHorizontalAlignment(SwingConstants.CENTER);
		txtEmailid.setText(" Email-ID");
		txtEmailid.setBounds(42, 96, 313, 29);
		frame.getContentPane().add(txtEmailid);
		txtEmailid.setColumns(10);
//		if(check(uid,eid)==2)
//		{
//			txt.setText("Change Password");
//		}
		p2.setBounds(206, 168, 149, 22);
		frame.getContentPane().add(p2);
		
		p1 = new JPasswordField();
		p1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnVfy.setText("Update");
			}
		});
		p1.setBounds(206, 140, 149, 22);
		frame.getContentPane().add(p1);
		
		btnVfy.addActionListener(new ActionListener() {
			// @SuppressWarnings("static-access")
			public void actionPerformed(ActionEvent arg0) {
				uid = txtUserid.getText();
				eid = txtEmailid.getText();
			    if(!checkUserIDEmail(uid,eid)){ 
			    	btnVfy.setText("Check User-ID/Email-ID");
			    }
			    t1 = new String(p1.getPassword());
				t2 = new String(p2.getPassword());
			    
			    if(!(t1.equals(t2)))
			    {
			    	btnVfy.setText("Passwords Don't Match");
			    }
				else
				{
					
					User u = new User(uid);
					u.forgotPWD(t1);
					btnVfy.setText("Password Changed");
					frame.setVisible(false);
					frame.dispose();
				}
				
				}				
									
		});
				

	}
}
