package uiFilesBackup;

 import java.awt.EventQueue;
import baseclasses.*;
import javax.swing.JFrame;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.JTextField;

public class BookingPage {

	public JFrame frame;
	private JButton btnSearchForCabs;
	private JButton btnLogout;
	private JButton btnRefresh;
	private JComboBox<String> pickupBox;
	private JComboBox<String> dropBox;
	public User u;
	private Location loc = new Location();
	private JTextField txtPath;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		User usr = new User("abcde");
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BookingPage window = new BookingPage(usr);
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
	public BookingPage(User u) {
		this.u = u;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(238, 232, 170));
		frame.getContentPane().setForeground(new Color(0, 0, 0));
		frame.setBounds(100, 100, 486, 393);
		frame.getContentPane().setLayout(null);
		
		JLabel lblChoosePickup = new JLabel("Please choose Pick-up and Drop locations");
		lblChoosePickup.setHorizontalAlignment(SwingConstants.CENTER);
		lblChoosePickup.setBackground(new Color(144, 238, 144));
		lblChoosePickup.setForeground(new Color(0, 0, 0));
		lblChoosePickup.setFont(new Font("Tahoma", Font.PLAIN, 19));
		lblChoosePickup.setBounds(-24, 145, 492, 35);
		frame.getContentPane().add(lblChoosePickup);
		
		
		JLabel lblCurrentWalletBalance = new JLabel("Current Wallet Balance :" + u.wallet);
		lblCurrentWalletBalance.setHorizontalAlignment(SwingConstants.LEFT);
		lblCurrentWalletBalance.setFont(new Font("Segoe UI Emoji", Font.BOLD, 17));
		lblCurrentWalletBalance.setBounds(10, 99, 293, 42);
		frame.getContentPane().add(lblCurrentWalletBalance);
		
		btnSearchForCabs = new JButton("Search for cabs");
		btnSearchForCabs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				String pickup = (String) pickupBox.getSelectedItem();
				String drop = String.valueOf(dropBox.getSelectedItem());
				if (!(u.isOccupied())) {
					if (!pickup.isEmpty()) {
						if (!drop.isEmpty()) { 
							if (!pickup.equals(drop)) {
								btnSearchForCabs.setText("Searching...");
								pickupBox.setEnabled(false);
								dropBox.setEnabled(false);
								try {
									Thread.sleep(500);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
								Driver drvr = Driver.getDriver(pickup);
								Trip trip = new Trip(u, drvr, pickup, drop);
								if (!trip.check) {
									// balance insufficient for fare
									JOptionPane.showMessageDialog(null, "Wallet balance insufficient!");
									InsufficientBalancePage wbp = new InsufficientBalancePage(u);
									wbp.frame.setVisible(true);
								}
								else {
									// if balance is sufficient for the trip fare
									JOptionPane.showMessageDialog(null, " Booking Successful! ");
									BookingDetailsPage bdp = new BookingDetailsPage(trip);
									bdp.frame.setVisible(true);
									pickupBox.setEnabled(false);
									dropBox.setEnabled(false);
									trip.startTrip();
									frame.setVisible(false);
									frame.dispose();
								}
							}
							else {
								JOptionPane.showMessageDialog(null, "Please select different locations!");
							}
						}
						else {
							JOptionPane.showMessageDialog(null, "Please select destination!");
						}
					}
					else {
						JOptionPane.showMessageDialog(null, "Please select pick-up point!");
					}
				}
				else {
					JOptionPane.showMessageDialog(null, "User is occupied!");
				}
			}
		});
//		btnSearchForCabs.setEnabled(false);
		btnSearchForCabs.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 16));
		// TODO: implement rotating or 'searching symbol' when button is pressed or else directly
		// take to Booking details page which is yet to be created.
		btnSearchForCabs.setBounds(148, 282, 188, 35);
		frame.getContentPane().add(btnSearchForCabs);
		
		pickupBox = new JComboBox<String>();
		pickupBox.addItem("");
		
		dropBox = new JComboBox<String>();
//		dropBox.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent arg0) {
//				String pick = pickupBox.getSelectedItem().toString();
//				String drop = dropBox.getSelectedItem().toString();
//				if (!pick.isEmpty())
//					if (!drop.isEmpty())
//						btnSearchForCabs.setEnabled(true);
//					else
//						btnSearchForCabs.setEnabled(false);
//				else
//					btnSearchForCabs.setEnabled(false);
//			}
//		});
		dropBox.addItem("");
		
		String[] loclist = loc.getLocations();
		
		
		for (int i = 0; i < loclist.length; i++) {
			pickupBox.addItem(loclist[i]);
			dropBox.addItem(loclist[i]);
		}
		pickupBox.setBounds(218, 200, 188, 22);
		dropBox.setBounds(218, 242, 188, 22);
		frame.getContentPane().add(pickupBox);
		frame.getContentPane().add(dropBox);
		
		JLabel lblPickup = new JLabel("Pick-up location");
		lblPickup.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPickup.setFont(new Font("Segoe UI Historic", Font.BOLD, 17));
		lblPickup.setBounds(0, 198, 206, 22);
		frame.getContentPane().add(lblPickup);
		
		JLabel lblDrop = new JLabel("Drop location");
		lblDrop.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDrop.setFont(new Font("Segoe UI Historic", Font.BOLD, 17));
		lblDrop.setBounds(10, 242, 196, 19);
		frame.getContentPane().add(lblDrop);
		
		JButton btnAddMoney = new JButton("Add Money");
		btnAddMoney.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AddMoneyPage amp = new AddMoneyPage(u);
				amp.frame.setVisible(true);
				initialize();
			}
		});
		btnAddMoney.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnAddMoney.setBounds(313, 107, 119, 25);
		frame.getContentPane().add(btnAddMoney);
		
		btnLogout = new JButton("LOGOUT");
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.setVisible(false);
				frame.dispose();
			}
		});
		btnLogout.setBounds(10, 58, 97, 25);
		frame.getContentPane().add(btnLogout);
		
		btnRefresh = new JButton("REFRESH");
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				lblCurrentWalletBalance.setText("Current Wallet Balance: " + u.wallet);
				String pick = pickupBox.getSelectedItem().toString();
				String drop = dropBox.getSelectedItem().toString();
				if (!pick.isEmpty())
					if (!drop.isEmpty())
						btnSearchForCabs.setEnabled(true);
					else
						btnSearchForCabs.setEnabled(false);
				else
					btnSearchForCabs.setEnabled(false);
			}
		});
		btnRefresh.setBounds(359, 58, 97, 25);
		frame.getContentPane().add(btnRefresh);
		
		txtPath = new JTextField();
		txtPath.setBackground(new Color(204, 255, 255));
		txtPath.setFont(new Font("Tahoma", Font.BOLD, 24));
		txtPath.setHorizontalAlignment(SwingConstants.CENTER);
		txtPath.setText("PATH");
		txtPath.setEditable(false);
		txtPath.setBounds(0, 0, 468, 55);
		frame.getContentPane().add(txtPath);
		txtPath.setColumns(10);
		
	}
}
