package gui.windows;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import controller.RouteCtrl;
import model.Delivery;
import model.Route;

public class UpdateRouteWindow extends JFrame {

	private JPanel contentPane;
	private final JLabel dateLbl = new JLabel("Date");
	private final JTextField dateField = new JTextField();
	private final JLabel timeLbl = new JLabel("Time");
	private final JTextField timeField = new JTextField();
	private final JButton updateDateBtn = new JButton("Update date");
	private final JButton updateTimeBtn = new JButton("Update time");

	private Delivery del = null;
	private Route route = null;

	/**
	 * Create the frame.
	 */
	public UpdateRouteWindow(int routeID, int delID) {

		setTitle("Update a route");
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 528, 419);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		dateLbl.setFont(new Font("Tahoma", Font.PLAIN, 18));
		dateLbl.setBounds(107, 76, 59, 25);

		contentPane.add(dateLbl);
		dateField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		dateField.setColumns(10);
		dateField.setBounds(168, 76, 212, 25);

		contentPane.add(dateField);
		timeLbl.setFont(new Font("Tahoma", Font.PLAIN, 18));
		timeLbl.setBounds(107, 206, 59, 25);

		contentPane.add(timeLbl);
		timeField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		timeField.setColumns(10);
		timeField.setBounds(168, 206, 212, 25);

		del = new RouteCtrl().findDelivery(delID);
		dateField.setText(del.deliveryDate.toString());
		route = new RouteCtrl().findRoute(routeID);
		timeField.setText(route.deliveryTime.toString());

		contentPane.add(timeField);
		updateDateBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String deliveryDate = dateField.getText();
				SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
				java.sql.Date date = null;
				try {
					date = (Date) sdf.parse(deliveryDate);
					del.deliveryDate = date;
					new RouteCtrl().changeDeliveryDate(delID, date);
				} catch (Exception ex1) {
					ex1.printStackTrace();
				}
			}
		});
		updateDateBtn.setFont(new Font("Tahoma", Font.PLAIN, 18));
		updateDateBtn.setBounds(168, 136, 166, 23);

		contentPane.add(updateDateBtn);
		updateTimeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String deliveryTime = timeField.getText();
				java.sql.Time time = null;
				time = java.sql.Time.valueOf(deliveryTime);
				route.deliveryTime = time;
				new RouteCtrl().changeRouteDeliveryTime(routeID, time);
			}
		});
		updateTimeBtn.setFont(new Font("Tahoma", Font.PLAIN, 18));
		updateTimeBtn.setBounds(168, 268, 166, 23);

		contentPane.add(updateTimeBtn);
	}

}
