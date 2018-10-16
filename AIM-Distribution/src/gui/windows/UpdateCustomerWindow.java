package gui.windows;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import controller.CustomerCtrl;
import gui.panels.CustomerPanel;
import model.Customer;
import model.DeliveryPoint;

import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JSeparator;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class UpdateCustomerWindow extends JFrame {

	private JPanel contentPane;
	public final JTextField nameField = new JTextField();
	public final JTextField phoneField = new JTextField();
	private final JLabel nameLbl = new JLabel("Name");
	private final JLabel phoneLbl = new JLabel("Phone");
	private final JLabel mailLbl = new JLabel("E-mail");
	public final JTextField mailField = new JTextField();
	private final JButton updateBtn = new JButton("Update customer");
	public final JTextField IdField = new JTextField();
	private final JLabel idLbl = new JLabel("ID");
	private final JLabel addressLbl = new JLabel("Address");
	private final JTextField addressField = new JTextField();
	private final JTextField zipField = new JTextField();
	private final JLabel zipLbl = new JLabel("Zipcode");
	private final JLabel cityLbl = new JLabel("City");
	private final JTextField cityField = new JTextField();
	private final JLabel regionLbl = new JLabel("Region");
	private final JTextField regionField = new JTextField();
	private final JLabel countryLbl = new JLabel("Country");
	private final JTextField countryField = new JTextField();
	private final JLabel pointLbl = new JLabel("Delivery point");
	private final JLabel lblCustomer = new JLabel("Customer");
	private final JSeparator separator = new JSeparator();
	private final JButton pointBtn = new JButton("Update point");
	private final JLabel label = new JLabel("Delivery");

	private Customer cust = null;
	private DeliveryPoint delPoint = null;

	/**
	 * Create the frame.
	 */
	public UpdateCustomerWindow(int id) {
		setTitle("Update a customer");
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 723, 503);

		cust = new CustomerCtrl().findCustomerByID(id);
		IdField.setText(Integer.toString(cust.id));
		nameField.setText(cust.name);
		phoneField.setText(cust.phone);
		mailField.setText(cust.email);

		// delPoint = CustomerCtrl.findDeliveryPoint(combox);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		nameField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		nameField.setColumns(10);
		nameField.setBounds(109, 140, 212, 25);

		contentPane.add(nameField);
		phoneField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		phoneField.setColumns(10);
		phoneField.setBounds(109, 185, 212, 25);

		contentPane.add(phoneField);
		nameLbl.setFont(new Font("Tahoma", Font.PLAIN, 18));
		nameLbl.setBounds(27, 141, 72, 25);

		contentPane.add(nameLbl);
		phoneLbl.setFont(new Font("Tahoma", Font.PLAIN, 18));
		phoneLbl.setBounds(27, 185, 72, 26);

		contentPane.add(phoneLbl);
		mailLbl.setFont(new Font("Tahoma", Font.PLAIN, 18));
		mailLbl.setBounds(27, 226, 72, 33);

		final JComboBox comboBoxDelivery = new JComboBox();
		comboBoxDelivery.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {

				String tmp = comboBoxDelivery.getSelectedItem().toString();
				addressField.setText(tmp);
				DeliveryPoint dp = new CustomerCtrl().findPointByAddress(tmp, id);
				zipField.setText(dp.zipcode);
				cityField.setText(dp.city);
				regionField.setText(dp.region);
				countryField.setText(dp.country);

			}
		});
		comboBoxDelivery.setBounds(109, 276, 212, 25);

		IdField.setEditable(false);
		IdField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		IdField.setColumns(10);
		IdField.setBounds(109, 91, 212, 25);

		contentPane.add(mailLbl);
		mailField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		mailField.setColumns(10);
		mailField.setBounds(109, 229, 212, 25);

		contentPane.add(mailField);
		updateBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				cust.name = nameField.getText();
				cust.email = mailField.getText();
				cust.phone = phoneField.getText();

				new CustomerCtrl().updateCustomer(cust);

				JOptionPane.showMessageDialog(null, "The customer has been succesfully updated!");

				IdField.setText("");
				nameField.setText("");
				phoneField.setText("");
				mailField.setText("");
			}
		});
		updateBtn.setFont(new Font("Tahoma", Font.PLAIN, 18));
		updateBtn.setBounds(109, 371, 170, 23);

		contentPane.add(updateBtn);

		contentPane.add(IdField);
		idLbl.setFont(new Font("Tahoma", Font.PLAIN, 18));
		idLbl.setBounds(27, 92, 72, 25);

		contentPane.add(idLbl);
		addressLbl.setFont(new Font("Tahoma", Font.PLAIN, 18));
		addressLbl.setBounds(383, 91, 66, 25);

		contentPane.add(addressLbl);
		addressField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		addressField.setColumns(10);
		addressField.setBounds(459, 91, 212, 25);

		contentPane.add(addressField);
		zipField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if(!Character.isDigit(c) && e.getKeyChar() != '.')
					e.consume();
			}
		});
		zipField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		zipField.setColumns(10);
		zipField.setBounds(459, 140, 212, 25);

		contentPane.add(zipField);
		zipLbl.setFont(new Font("Tahoma", Font.PLAIN, 18));
		zipLbl.setBounds(383, 137, 66, 26);

		contentPane.add(zipLbl);
		cityLbl.setFont(new Font("Tahoma", Font.PLAIN, 18));
		cityLbl.setBounds(383, 183, 66, 33);

		contentPane.add(cityLbl);
		cityField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if(Character.isDigit(c))
					e.consume();
			}
		});
		cityField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		cityField.setColumns(10);
		cityField.setBounds(459, 185, 212, 25);

		contentPane.add(cityField);
		regionLbl.setFont(new Font("Tahoma", Font.PLAIN, 18));
		regionLbl.setBounds(383, 227, 66, 33);

		contentPane.add(regionLbl);
		regionField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if(Character.isDigit(c))
					e.consume();
			}
		});
		regionField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		regionField.setColumns(10);
		regionField.setBounds(459, 229, 212, 25);

		contentPane.add(regionField);
		countryLbl.setFont(new Font("Tahoma", Font.PLAIN, 18));
		countryLbl.setBounds(383, 271, 66, 33);

		contentPane.add(countryLbl);
		countryField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if(Character.isDigit(c))
					e.consume();
			}
		});
		countryField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		countryField.setColumns(10);
		countryField.setBounds(459, 273, 212, 25);

		contentPane.add(countryField);
		pointLbl.setFont(new Font("Tahoma", Font.PLAIN, 18));
		pointLbl.setBounds(494, 47, 147, 25);

		contentPane.add(pointLbl);
		lblCustomer.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblCustomer.setBounds(179, 47, 147, 25);

		contentPane.add(lblCustomer);
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setForeground(Color.LIGHT_GRAY);
		separator.setBounds(347, 24, 9, 370);

		contentPane.add(separator);
		pointBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				delPoint = new CustomerCtrl().findPointByAddress(comboBoxDelivery.getSelectedItem().toString(), id);

				delPoint.address = addressField.getText();
				delPoint.zipcode = zipField.getText();
				delPoint.city = cityField.getText();
				delPoint.region = regionField.getText();
				delPoint.country = countryField.getText();

				new CustomerCtrl().updateDeliveryPoint(delPoint);

				JOptionPane.showMessageDialog(null, "The delivery point has been succesfully updated !");

				addressField.setText("");
				zipField.setText("");
				cityField.setText("");
				regionField.setText("");
				countryField.setText("");

			}
		});
		pointBtn.setFont(new Font("Tahoma", Font.PLAIN, 18));
		pointBtn.setBounds(434, 371, 170, 23);

		contentPane.add(pointBtn);

		List<DeliveryPoint> dps = new CustomerCtrl().findAllPointsByCustomer(id);
		for (int i = 0; i < dps.size(); i++) {
			DeliveryPoint dp = dps.get(i);
			comboBoxDelivery.addItem(dp.address);
		}

		contentPane.add(comboBoxDelivery);
		label.setFont(new Font("Tahoma", Font.PLAIN, 18));
		label.setBounds(27, 272, 72, 33);

		contentPane.add(label);
	}
}
