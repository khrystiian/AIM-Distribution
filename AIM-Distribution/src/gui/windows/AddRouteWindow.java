package gui.windows;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import controller.EmployeeCtrl;
import controller.RouteCtrl;
import model.Employee;
import model.Route;
import util.Session;

public class AddRouteWindow extends JFrame {

	private JPanel contentPane;
	private final JButton addDeliveryButton = new JButton("Add delivery");
	private final JButton addRouteBtn = new JButton("Add route");
	private final JLabel deliveryLbl = new JLabel("Delivery");
	private final JLabel orderLbl = new JLabel("Order");
	private final JTextField orderField = new JTextField();
	private final JLabel lblEmployee = new JLabel("Employee");
	private final JTextField txtEmployee = new JTextField();
	private final JButton btnStartRoute = new JButton("Start route");

	private final RouteCtrl controller = new RouteCtrl();

	/**
	 * Create the frame.
	 */
	public AddRouteWindow() {
		setTitle("Add a route");
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 528, 419);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		addDeliveryButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.addDelivery(Integer.parseInt(orderField.getText()));
			}
		});
		addDeliveryButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		addDeliveryButton.setBounds(77, 239, 160, 23);

		contentPane.add(addDeliveryButton);
		addRouteBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.finishRoute();
			}
		});
		addRouteBtn.setFont(new Font("Tahoma", Font.PLAIN, 18));
		addRouteBtn.setBounds(272, 239, 160, 23);

		contentPane.add(addRouteBtn);
		deliveryLbl.setFont(new Font("Tahoma", Font.PLAIN, 18));
		deliveryLbl.setBounds(222, 152, 105, 25);

		contentPane.add(deliveryLbl);
		orderLbl.setFont(new Font("Tahoma", Font.PLAIN, 18));
		orderLbl.setBounds(98, 188, 66, 25);

		contentPane.add(orderLbl);
		orderField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		orderField.setColumns(10);
		orderField.setBounds(174, 188, 212, 25);

		contentPane.add(orderField);
		lblEmployee.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblEmployee.setBounds(77, 64, 87, 25);

		contentPane.add(lblEmployee);
		txtEmployee.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtEmployee.setColumns(10);
		txtEmployee.setBounds(174, 64, 212, 25);

		contentPane.add(txtEmployee);
		btnStartRoute.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Employee employee = new EmployeeCtrl().findEmployee(txtEmployee.getText());
				controller.createRoute(employee.employeeID);
				txtEmployee.setEnabled(false);
			}
		});
		btnStartRoute.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnStartRoute.setBounds(174, 107, 160, 23);

		contentPane.add(btnStartRoute);
	}
}
