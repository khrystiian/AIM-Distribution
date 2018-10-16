package gui.windows;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.CustomerCtrl;
import database.DeliveryPointDB;

import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class AddDeliveryPointWindow extends JFrame {

	private JPanel contentPane;
	private final JTextField addressField = new JTextField();
	private final JTextField zipField = new JTextField();
	private final JLabel addressLbl = new JLabel("Address");
	private final JLabel zipLbl = new JLabel("Zipcode");
	private final JLabel cityLbl = new JLabel("City");
	private final JTextField cityField = new JTextField();
	private final JLabel regionLbl = new JLabel("Region");
	private final JTextField regionField = new JTextField();
	private final JLabel countryLbl = new JLabel("Country");
	private final JTextField countryField = new JTextField();
	private final JButton addPointBtn = new JButton("Add");
	private final JLabel errorLbl = new JLabel("Address");

	public AddDeliveryPointWindow(int id) {
		
		setTitle("Add a delivery point");
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 528, 419);
		
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		addressField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		addressField.setColumns(10);
		addressField.setBounds(182, 70, 235, 34);
		
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
		zipField.setBounds(182, 115, 235, 34);
		
		contentPane.add(zipField);
		addressLbl.setFont(new Font("Tahoma", Font.PLAIN, 18));
		addressLbl.setBounds(100, 70, 66, 25);
		
		contentPane.add(addressLbl);
		zipLbl.setFont(new Font("Tahoma", Font.PLAIN, 18));
		zipLbl.setBounds(100, 114, 66, 26);
		
		contentPane.add(zipLbl);
		cityLbl.setFont(new Font("Tahoma", Font.PLAIN, 18));
		cityLbl.setBounds(100, 161, 66, 33);
		
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
		cityField.setBounds(182, 161, 235, 34);
		
		contentPane.add(cityField);
		regionLbl.setFont(new Font("Tahoma", Font.PLAIN, 18));
		regionLbl.setBounds(100, 207, 66, 33);
		
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
		regionField.setBounds(182, 207, 235, 34);
		
		contentPane.add(regionField);
		countryLbl.setFont(new Font("Tahoma", Font.PLAIN, 18));
		countryLbl.setBounds(100, 252, 66, 33);
		
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
		countryField.setBounds(182, 252, 235, 34);
		
		contentPane.add(countryField);
		addPointBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(addressField.getText().equals("") || zipField.getText().equals("") || cityField.getText().equals("")
						|| regionField.getText().equals("") || countryField.getText().equals("")){
					errorLbl.setText("Please fill in all the fields.");
					errorLbl.setVisible(true);
				}
				
				try{
					String address = addressField.getText();
					String zip = zipField.getText();
					String city = cityField.getText();
					String region = regionField.getText();
					String country = countryField.getText();
					
					new CustomerCtrl().addDeliveryPoint(id, address, zip, city, region, country);
					
					addressField.setText("");
					zipField.setText("");
					cityField.setText("");
					regionField.setText("");
					countryField.setText("");
					
				}catch(Exception ex){
					
				}
			}
		});
		addPointBtn.setFont(new Font("Tahoma", Font.PLAIN, 18));
		addPointBtn.setBounds(206, 324, 89, 23);
		
		contentPane.add(addPointBtn);
		
		errorLbl.setVisible(false);
		errorLbl.setForeground(Color.RED);
		errorLbl.setFont(new Font("Tahoma", Font.PLAIN, 18));
		errorLbl.setBounds(100, 13, 211, 25);
		
		contentPane.add(errorLbl);
	}

}
