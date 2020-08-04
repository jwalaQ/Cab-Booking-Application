package uiFilesBackup;

import java.awt.EventQueue;
import baseclasses.*;

import javax.swing.JFrame;
import java.util.Date;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;

public class BookingDetailsPage {

	public JFrame frame;
	private Trip trip;
	private final ButtonGroup buttonGroup = new ButtonGroup();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		User usr = new User("prajjwal");
		Driver dr = Driver.getDriver("Chennai");
		Trip tr = new Trip(usr, dr,"Chennai", "Hyderabad");
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					tr.startTrip();
					BookingDetailsPage window = new BookingDetailsPage(tr);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public BookingDetailsPage(Trip trp) {
		trip = trp;
		if (trip.drvr == null) {
			JOptionPane.showMessageDialog(null, "Request Timed Out");
		}
		initialize();
	} 
	
	
	private void initialize() {
		frame = new JFrame("Booking Details");
		frame.getContentPane().setBackground(new Color(238,232,170));
		frame.getContentPane().setForeground(new Color(0, 0, 0));
		frame.setBounds(100, 100, 553, 500);
		frame.getContentPane().setLayout(null);
		
		JLabel lblDriverName = new JLabel("Driver Name : " + trip.drvr.name);
//		lblDriverName.setIcon(new ImageIcon("G:\\Academia\\Sem_3.1\\OOP_PATH_Assignment\\Icons-1\\user_24px.png"));
		lblDriverName.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblDriverName.setBounds(46, 27, 316, 34);
		frame.getContentPane().add(lblDriverName);
		
		JLabel lblVehicleNo = new JLabel("Vehicle No. : " + trip.drvr.vehicleID);
//		lblVehicleNo.setIcon(new ImageIcon("G:\\Academia\\Sem_3.1\\OOP_PATH_Assignment\\Icons\\icons8-car-32px.png"));
		lblVehicleNo.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblVehicleNo.setBounds(46, 61, 316, 34);
		frame.getContentPane().add(lblVehicleNo);
		
		JLabel lblVehicleType = new JLabel("Vehicle Type : " + trip.drvr.vehicle);
		lblVehicleType.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblVehicleType.setBounds(46, 92, 316, 34);
		frame.getContentPane().add(lblVehicleType);
		
		JLabel lblMobileNo = new JLabel("Mobile No. : " + trip.drvr.phNo);
		lblMobileNo.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblMobileNo.setBounds(46, 125, 316, 34);
		frame.getContentPane().add(lblMobileNo);
		
		JLabel lblEstimatedFare = new JLabel("Estimated Fare : " + trip.fare);
		lblEstimatedFare.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblEstimatedFare.setBounds(46, 286, 316, 34);
		frame.getContentPane().add(lblEstimatedFare);
		
		JLabel lblApproxDur = new JLabel("Approx Duration : " + trip.dur);
		lblApproxDur.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblApproxDur.setBounds(46, 321, 316, 34);
		frame.getContentPane().add(lblApproxDur);
		
		JLabel lblDriverRating = new JLabel("Driver Rating : " + trip.drvr.rating);
		lblDriverRating.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblDriverRating.setBounds(46, 157, 316, 34);
		frame.getContentPane().add(lblDriverRating);
		
		JLabel lblFrom = new JLabel("Pick-Up Location: " + trip.src);
		lblFrom.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblFrom.setBounds(46, 217, 316, 34);
		frame.getContentPane().add(lblFrom);
		
		JLabel lblDropLocation = new JLabel("Drop Location: " + trip.dest);
		lblDropLocation.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblDropLocation.setBounds(44, 249, 318, 34);
		frame.getContentPane().add(lblDropLocation);
		
		JRadioButton btnFive = new JRadioButton("5");
		buttonGroup.add(btnFive);
		btnFive.setBounds(376, 193, 50, 25);
		frame.getContentPane().add(btnFive);
		
		JRadioButton btnThree = new JRadioButton("3");
		buttonGroup.add(btnThree);
		btnThree.setBounds(376, 255, 50, 25);
		frame.getContentPane().add(btnThree);
		
		JRadioButton btnTwo = new JRadioButton("2");
		buttonGroup.add(btnTwo);
		btnTwo.setBounds(376, 286, 50, 25);
		frame.getContentPane().add(btnTwo);
		
		JRadioButton btnOne = new JRadioButton("1");
		buttonGroup.add(btnOne);
		btnOne.setBounds(376, 317, 50, 25);
		frame.getContentPane().add(btnOne);
		
		JRadioButton btnFour = new JRadioButton("4");
		buttonGroup.add(btnFour);
		btnFour.setBounds(376, 223, 50, 25);
		frame.getContentPane().add(btnFour);
		
		JLabel lblRateYourTrip = new JLabel("RATE YOUR TRIP");
		lblRateYourTrip.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblRateYourTrip.setBounds(330, 139, 151, 42);
		frame.getContentPane().add(lblRateYourTrip);
		
		JButton btnRefresh = new JButton("REFRESH");
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Date curDate = new Date();
				long curTime = curDate.getTime();
				if (curTime > trip.end) {
					btnRefresh.setText("END TRIP");
					if (btnFive.isSelected())
						trip.drvr.newrating = 5;
					else if (btnFour.isSelected())
						trip.drvr.newrating = 4;
					else if (btnThree.isSelected())
						trip.drvr.newrating = 3;
					else if (btnTwo.isSelected())
						trip.drvr.newrating = 2;
					else if (btnOne.isSelected())
						trip.drvr.newrating = 1;
					trip.endTrip();
					JOptionPane.showMessageDialog(null, "Rs. " + trip.fare + " has been deducted.");
					frame.setVisible(false);
					frame.dispose();
				}
			}
		});
		btnRefresh.setForeground(Color.BLACK);
		btnRefresh.setBackground(Color.ORANGE);
		btnRefresh.setFont(new Font("Tahoma", Font.BOLD, 17));
		btnRefresh.setBounds(189, 368, 151, 47);
		frame.getContentPane().add(btnRefresh);
		
//		frame.setContentPane(contentPane);
		
//		JLabel label = new JLabel("");
//		label.setIcon(new ImageIcon("G:\\Academia\\Sem_3.1\\OOP_PATH_Assignment\\Icons-1\\mark-as-favorite-star_24px.png"));
//		label.setBounds(190, 247, 24, 24);
//		contentPane.add(label);
//		
//		JLabel label_1 = new JLabel("");
//		label_1.setIcon(new ImageIcon("G:\\Academia\\Sem_3.1\\OOP_PATH_Assignment\\Icons-1\\mark-as-favorite-star_24px.png"));
//		label_1.setBounds(226, 247, 24, 24);
//		contentPane.add(label_1);
//		
//		JLabel label_2 = new JLabel("");
//		label_2.setIcon(new ImageIcon("G:\\Academia\\Sem_3.1\\OOP_PATH_Assignment\\Icons-1\\mark-as-favorite-star_24px.png"));
//		label_2.setBounds(264, 247, 24, 24);
//		contentPane.add(label_2);
//		
//		JLabel label_3 = new JLabel("");
//		label_3.setIcon(new ImageIcon("G:\\Academia\\Sem_3.1\\OOP_PATH_Assignment\\Icons-1\\mark-as-favorite-star_24px.png"));
//		label_3.setBounds(300, 247, 24, 24);
//		contentPane.add(label_3);
//		
//		JLabel label_4 = new JLabel("");
//		label_4.setIcon(new ImageIcon("G:\\Academia\\Sem_3.1\\OOP_PATH_Assignment\\Icons-1\\mark-as-favorite-star_24px.png"));
//		label_4.setBounds(336, 247, 24, 24);
//		contentPane.add(label_4);
//		
//		JLabel label_5 = new JLabel("");
//		label_5.setIcon(new ImageIcon("G:\\Academia\\Sem_3.1\\OOP_PATH_Assignment\\Icons-1\\half-filled-rating-star_24px.png"));
//		label_5.setBounds(372, 247, 24, 24);
//		contentPane.add(label_5);
	}
}
