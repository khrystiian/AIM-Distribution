package gui.windows;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.RouteCtrl;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class AddTruckWindow extends JFrame {

	private JPanel contentPane;
	private final JLabel lblPlateNumber = new JLabel("Plate number");
	private final JTextField txtPlatenumber = new JTextField();
	private final JButton btnNewButton = new JButton("Add");

	
	public AddTruckWindow() {
		txtPlatenumber.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if(!Character.isDigit(c) && e.getKeyChar() != '.')
					e.consume();
			}
		});
		txtPlatenumber.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtPlatenumber.setHorizontalAlignment(SwingConstants.CENTER);
		txtPlatenumber.setBounds(182, 56, 110, 27);
		txtPlatenumber.setColumns(10);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 474, 188);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		lblPlateNumber.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblPlateNumber.setBounds(35, 56, 110, 29);
		
		contentPane.add(lblPlateNumber);
		
		contentPane.add(txtPlatenumber);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					new RouteCtrl().addTruck(Integer.parseInt(txtPlatenumber.getText()));
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "Input a valid plate number.");
				}
				
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnNewButton.setBounds(171, 115, 89, 23);
		
		contentPane.add(btnNewButton);
	}

}
