package uiFilesBackup;

import java.awt.EventQueue;

import baseclasses.User;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AddMoneyPage {

	public JFrame frame;
	private JTextField textField;
	private User user;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		User u = new User ("prajjwal");
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddMoneyPage window = new AddMoneyPage(u);
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
	public AddMoneyPage(User u) {
		user = u;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(640, 380, 450, 300);
		frame.getContentPane().setLayout(null);
		
		JLabel lblEnterMoneyBelow = new JLabel("Enter amount to add:");
		lblEnterMoneyBelow.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblEnterMoneyBelow.setBounds(124, 101, 170, 25);
		frame.getContentPane().add(lblEnterMoneyBelow);
		
		JLabel lblMoney = new JLabel("Current Balance : " + user.wallet);
		lblMoney.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblMoney.setBounds(113, 13, 278, 75);
		frame.getContentPane().add(lblMoney);
		
		JButton btnProceed = new JButton("PROCEED");
		btnProceed.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					int add = Integer.parseInt(textField.getText());
					if (add < 0) {
						throw new IllegalArgumentException();
					}
					else {
						user.addMoney(add);
						user = new User(user.user_id);
						Thread.sleep(1500);
						JOptionPane.showMessageDialog(null, "Money Added Successfully.");
						lblMoney.setText("Current amount in wallet is " + user.wallet);
						Thread.sleep(1500);
						frame.setVisible(false);
						frame.dispose();
					}
				}
				catch(Exception e){
					JOptionPane.showMessageDialog(null, "Please enter valid amount.");
				}
				
			}
	});
		btnProceed.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnProceed.setBounds(144, 180, 111, 25);
		frame.getContentPane().add(btnProceed);
		
		textField = new JTextField();
		textField.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textField.setBounds(134, 130, 136, 31);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
	}
}