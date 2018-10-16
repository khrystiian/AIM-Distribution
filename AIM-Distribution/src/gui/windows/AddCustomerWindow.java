package gui.windows;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.CustomerCtrl;

import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

public class AddCustomerWindow extends JFrame {

	private JPanel contentPane;
	private final JTextField nameField = new JTextField();
	private final JTextField phoneField = new JTextField();
	private final JLabel nameLbl = new JLabel("Name");
	private final JLabel phoneLbl = new JLabel("Phone");
	private final JLabel mailLbl = new JLabel("E-mail");
	private final JTextField mailField = new JTextField();
	private final JButton createBtn = new JButton("Create customer");
	private final JLabel lblError = new JLabel("");
	private final JLabel addressLbl = new JLabel("Address");
	private final JTextField addressField = new JTextField();
	private final JTextField zipField = new JTextField();
	private final JLabel zipLabel = new JLabel("Zipcode");
	private final JLabel cityLbl = new JLabel("City");
	private final JTextField cityField = new JTextField();
	private final JLabel regionLbl = new JLabel("Region");
	private final JTextField regionField = new JTextField();
	private final JLabel countryLbl = new JLabel("Country");
	private final JTextField countryField = new JTextField();
	private final JButton btnAddPoint = new JButton("Add point");
	private final JButton btnFinishInsert = new JButton("Finish insert");
	private final JLabel customerLabel = new JLabel("Customer");
	private final JLabel dpLbl = new JLabel("Delivery point");
	private final JSeparator separator = new JSeparator();

	/**
	 * Create the frame.
	 */
	public AddCustomerWindow() {
		
		setTitle("Add a customer");
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 723, 503);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		nameField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				char c = arg0.getKeyChar();
				if(Character.isDigit(c))
					arg0.consume();
			}
		});
		nameField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		nameField.setColumns(10);
		nameField.setBounds(116, 72, 212, 25);
		
		contentPane.add(nameField);
		phoneField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if(!Character.isDigit(c) && e.getKeyChar() != '.')
					e.consume();
			}
		});
		phoneField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		phoneField.setColumns(10);
		phoneField.setBounds(116, 121, 212, 25);
		
		contentPane.add(phoneField);
		nameLbl.setFont(new Font("Tahoma", Font.PLAIN, 18));
		nameLbl.setBounds(40, 72, 66, 25);
		
		contentPane.add(nameLbl);
		phoneLbl.setFont(new Font("Tahoma", Font.PLAIN, 18));
		phoneLbl.setBounds(40, 118, 66, 26);
		
		contentPane.add(phoneLbl);
		mailLbl.setFont(new Font("Tahoma", Font.PLAIN, 18));
		mailLbl.setBounds(40, 164, 66, 33);
		
		contentPane.add(mailLbl);
		mailField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		mailField.setColumns(10);
		mailField.setBounds(116, 166, 212, 25);
		
		addressField.setEnabled(false);
		zipField.setEnabled(false);
		
		cityField.setEnabled(false);
		regionField.setEnabled(false);
		countryField.setEnabled(false);
		
		contentPane.add(mailField);
		createBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				lblError.setVisible(false);
								
			if(nameField.getText().equals("") || phoneField.getText().equals("") || mailField.getText().equals(""))	{
				lblError.setText("Please fill in all the fields.");
				lblError.setVisible(true);
			}
			else if(checkEmailField() == false){
				lblError.setText("Wrong e-mail format. ");
			    lblError.setVisible(true);
			}
			else{
				String name = nameField.getText();
				String phone = phoneField.getText();
				String email = mailField.getText();
				new CustomerCtrl().createCustomer(name, phone, email);
				addressField.setEnabled(true);
				zipField.setEnabled(true);
				cityField.setEnabled(true);
				regionField.setEnabled(true);
				countryField.setEnabled(true);
				
				//JOptionPane.showMessageDialog(null, "A new customer has been added. ");
			}
			}
		});
		createBtn.setFont(new Font("Tahoma", Font.PLAIN, 18));
		createBtn.setBounds(91, 318, 184, 23);
		
		contentPane.add(createBtn);
		lblError.setForeground(Color.RED);
		lblError.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblError.setBounds(214, 408, 303, 33);
		
		contentPane.add(lblError);
		addressLbl.setFont(new Font("Tahoma", Font.PLAIN, 18));
		addressLbl.setBounds(364, 72, 66, 25);
		
		contentPane.add(addressLbl);
		addressField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		addressField.setColumns(10);
		addressField.setBounds(440, 72, 212, 25);
		
		contentPane.add(addressField);
		zipField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		zipField.setColumns(10);
		zipField.setBounds(440, 121, 212, 25);
		
		contentPane.add(zipField);
		zipLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		zipLabel.setBounds(364, 118, 66, 26);
		
		contentPane.add(zipLabel);
		cityLbl.setFont(new Font("Tahoma", Font.PLAIN, 18));
		cityLbl.setBounds(364, 164, 66, 33);
		
		contentPane.add(cityLbl);
		cityField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		cityField.setColumns(10);
		cityField.setBounds(440, 166, 212, 25);
		
		contentPane.add(cityField);
		regionLbl.setFont(new Font("Tahoma", Font.PLAIN, 18));
		regionLbl.setBounds(364, 208, 66, 33);
		
		contentPane.add(regionLbl);
		regionField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		regionField.setColumns(10);
		regionField.setBounds(440, 210, 212, 25);
		
		contentPane.add(regionField);
		countryLbl.setFont(new Font("Tahoma", Font.PLAIN, 18));
		countryLbl.setBounds(364, 252, 66, 33);
		
		contentPane.add(countryLbl);
		countryField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		countryField.setColumns(10);
		countryField.setBounds(440, 254, 212, 25);
		
		contentPane.add(countryField);
		btnAddPoint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(addressField.getText().equals("") || zipField.getText().equals("") || cityField.getText().equals("")
						|| regionField.getText().equals("") || countryField.getText().equals("")){
					//errorLbl.setText("Please fill in all the fields.");
					//errorLbl.setVisible(true);
				}
				
				try{
					String address = addressField.getText();
					String zip = zipField.getText();
					String city = cityField.getText();
					String region = regionField.getText();
					String country = countryField.getText();
					
					new CustomerCtrl().addDeliveryPoint(address, zip, city, region, country);
					
					addressField.setText("");
					zipField.setText("");
					cityField.setText("");
					regionField.setText("");
					countryField.setText("");
					
				}catch(Exception ex){
					
				}
			}
		});
		btnAddPoint.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnAddPoint.setBounds(464, 318, 160, 23);
		
		contentPane.add(btnAddPoint);
		btnFinishInsert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
				new CustomerCtrl().finishCustomerInsert();
				nameField.setText("");
				phoneField.setText("");
				mailField.setText("");
				JOptionPane.showMessageDialog(null, "A new customer has been added. ");
				}catch(NullPointerException npe){
					
				}
			}
		});
		btnFinishInsert.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnFinishInsert.setBounds(279, 365, 160, 23);
		
		contentPane.add(btnFinishInsert);
		customerLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		customerLabel.setBounds(170, 28, 105, 25);
		
		contentPane.add(customerLabel);
		dpLbl.setFont(new Font("Tahoma", Font.PLAIN, 18));
		dpLbl.setBounds(475, 28, 147, 25);
		
		contentPane.add(dpLbl);
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setForeground(Color.LIGHT_GRAY);
		separator.setBounds(345, 28, 9, 313);
		
		contentPane.add(separator);
	}
	
	private boolean checkEmailField(){
		String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$";
		Pattern pattern = Pattern.compile(emailPattern);
		Matcher m = pattern.matcher(mailField.getText());
		
		return m.find();
	}
}
