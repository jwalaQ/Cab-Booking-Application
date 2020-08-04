package uiFilesBackup;

import java.awt.*;
// import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;

public class MainPage {
	
	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main
	(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainPage window = new MainPage();
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
	public MainPage() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(238, 232, 170));
		frame.getContentPane().setForeground(new Color(0, 0, 0));
		frame.setTitle("Click a button to proceed:");
		frame.setBounds(1000, 300, 504, 349);
		frame.getContentPane().setLayout(null);
		
		JButton btnLogIn = new JButton("Log In");
		btnLogIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				LoginPage lp = new LoginPage();
				lp.frame.setVisible(true);
			}
		});
		btnLogIn.setFont(new Font("Calibri", Font.BOLD, 19));
		btnLogIn.setForeground(new Color(128, 0, 0));
		btnLogIn.setBounds(259, 94, 146, 39);
		frame.getContentPane().add(btnLogIn);
		
		JButton btnRegister = new JButton("Register Now");
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CreateUserPage cup = new CreateUserPage();
				cup.frame.setVisible(true);
			}
		});
		btnRegister.setFont(new Font("Calibri", Font.BOLD, 18));
		btnRegister.setForeground(new Color(165, 42, 42));
		btnRegister.setBounds(259, 168, 146, 38);
		frame.getContentPane().add(btnRegister);
		
		JLabel lblHaveAnAccount = new JLabel("Already Have an Account?");
		lblHaveAnAccount.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblHaveAnAccount.setBounds(12, 98, 258, 29);
		frame.getContentPane().add(lblHaveAnAccount);
		
		JLabel lblFirstTime = new JLabel("First Time?");
		lblFirstTime.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblFirstTime.setBounds(93, 171, 154, 29);
		frame.getContentPane().add(lblFirstTime);
		
		JLabel lblPath = new JLabel("P.A.T.H.");
		lblPath.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 20));
		lblPath.setBounds(164, 13, 106, 29);
		frame.getContentPane().add(lblPath);
		
		JLabel lblCabbookingPortal = new JLabel("Cab-Booking Portal");
		lblCabbookingPortal.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblCabbookingPortal.setBounds(130, 47, 185, 32);
		frame.getContentPane().add(lblCabbookingPortal);
	}
}
