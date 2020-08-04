package uiFilesBackup;
import baseclasses.*;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Font;

public class LoginPage {

	public JFrame frame;
	private JTextField txtUserId;
	private JPasswordField passwordField;
	private JButton btnPath;
	private JButton btnSubmit;
	DBAccess dba = new DBAccess();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginPage window = new LoginPage();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	// checks credentials and then proceeds
	private void callVerify() {
		String uid = txtUserId.getText();
		String pwd = new String(passwordField.getPassword());
		int vfy = dba.verifyLoginCred(uid, pwd);
		if(vfy==2) {
			User user = new User(uid);
		if (!user.isOccupied()) {
				if (user.wallet < 300 ) {
					InsufficientBalancePage wbp = new InsufficientBalancePage(user);
					wbp.frame.setVisible(true);
				}
				else {
					btnSubmit.setForeground(Color.GREEN);
					btnSubmit.setText("Welcome");
					BookingPage bp = new BookingPage(user);
					bp.frame.setVisible(true);
					frame.setVisible(false);
					frame.dispose();
				}
			}
//			else {
//				BookingDetailsPage bdp = new BookingDetailsPage(user.getTrip(user));
//				bdp.frame.setVisible(true);
//				frame.setVisible(false);
//				frame.dispose();
//			}
		}
		else if (vfy == 1) {
			btnSubmit.setText("Wrong Password!");
			passwordField.setText("");
		}
		else {
			btnSubmit.setText("UserID incorrect!");
			txtUserId.setText("");
		}
	}
	/**
	 * Create the application.
	 */
	public LoginPage() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Welcome to PATH");
		frame.getContentPane().setBackground(new Color(238, 232, 170));
		frame.setBounds(100, 100, 450, 300);
		frame.getContentPane().setLayout(null);
		
		txtUserId = new JTextField("");
		
		// txtUserId.setText("USER ID:");
		txtUserId.setBounds(102, 90, 126, 32);
		frame.getContentPane().add(txtUserId);
		txtUserId.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(102, 135, 126, 32);
		frame.getContentPane().add(passwordField);
		
		// if '\n' is entered in pwd field,
		// the credential verification is executed
		passwordField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent ke) {
				if (ke.getKeyChar()=='\n') {
					callVerify();
				}
			}
		});
		// if the submit button is clicked,
		// credential verification is executed
		btnSubmit = new JButton("SUBMIT");
		btnSubmit.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				callVerify();
			}
		});
		btnSubmit.setBounds(250, 90, 170, 30);
		frame.getContentPane().add(btnSubmit);
		
		btnPath = new JButton("P.A.T.H.");
		btnPath.setFont(new Font("Tahoma", Font.PLAIN, 18));
		// resets to default settings (blank  text fields and SUBMIT button)
		btnPath.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtUserId.setText("");
				passwordField.setText("");
				btnSubmit.setText("Submit");
			}
		});
		btnPath.setBounds(12, 13, 106, 44);
		frame.getContentPane().add(btnPath);
		
		JLabel lblUserId = new JLabel("USER ID:");
		lblUserId.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblUserId.setBounds(12, 89, 87, 33);
		frame.getContentPane().add(lblUserId);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblPassword.setBounds(12, 130, 97, 38);
		frame.getContentPane().add(lblPassword);
		
		JButton btnForgotPassword = new JButton("Forgot Password");
		btnForgotPassword.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnForgotPassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				// open new Forgot Password Frame
				ForgotPasswordPage fpp = new ForgotPasswordPage();
				fpp.frame.setVisible(true);
//				ForgotPasswordPage.main(new String[1]);
			}
		});
		btnForgotPassword.setBounds(250, 136, 170, 32);
		frame.getContentPane().add(btnForgotPassword);
	}
}
