package gui.windows;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import controller.OrderCtrl;
import model.Order;
import util.EnumMaps;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class UpdateOrderWindow extends JFrame {

	private JPanel contentPane;
	private final JLabel statusLbl = new JLabel("Status");
	private final JTextField statusField = new JTextField();
	private final JLabel idLbl = new JLabel("ID");
	private final JTextField idField = new JTextField();
	private final JLabel amountLbl = new JLabel("Amount");
	private final JTextField amountField = new JTextField();
	private final JLabel dateLbl = new JLabel("Date");
	private final JTextField dateField = new JTextField();
	private final JLabel empLbl = new JLabel("Employee");
	private final JTextField empField = new JTextField();
	private final JLabel customerLbl = new JLabel("Customer");
	private final JTextField custField = new JTextField();
	private final JButton updateBtn = new JButton("Update");
	
	private Order order = null;
	private final JTextField pointField = new JTextField();
	private final JLabel pointLbl = new JLabel("Point");

	/**
	 * Create the frame.
	 */
	public UpdateOrderWindow(int id) {
		
		setTitle("Update an order");
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 528, 419);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		statusLbl.setFont(new Font("Tahoma", Font.PLAIN, 18));
		statusLbl.setBounds(95, 143, 107, 25);
		
		contentPane.add(statusLbl);
		statusField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		statusField.setColumns(10);
		statusField.setBounds(229, 142, 170, 25);
		
		contentPane.add(statusField);
		idLbl.setFont(new Font("Tahoma", Font.PLAIN, 18));
		idLbl.setBounds(95, 32, 107, 25);
		
		contentPane.add(idLbl);
		idField.setEditable(false);
		idField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		idField.setColumns(10);
		idField.setBounds(229, 31, 170, 25);
		
		contentPane.add(idField);
		amountLbl.setFont(new Font("Tahoma", Font.PLAIN, 18));
		amountLbl.setBounds(95, 69, 107, 25);
		
		contentPane.add(amountLbl);
		amountField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if(amountField.getText().equals("") && e.getKeyChar() == '0')
					e.consume();
				if(!Character.isDigit(c) && e.getKeyChar() != '.')
					e.consume();
				if(e.getKeyChar() == '.' && amountField.getText().contains("."))
					e.consume();
				if(amountField.getText().contains(".") && ((amountField.getText().substring(amountField.getText().lastIndexOf(".")+1)).length()>1))
		           e.consume();
			}
		});
		amountField.setEditable(false);
		amountField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		amountField.setColumns(10);
		amountField.setBounds(229, 68, 170, 25);
		
		contentPane.add(amountField);
		dateLbl.setFont(new Font("Tahoma", Font.PLAIN, 18));
		dateLbl.setBounds(95, 107, 107, 25);
		
		contentPane.add(dateLbl);
		dateField.setEditable(false);
		dateField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		dateField.setColumns(10);
		dateField.setBounds(229, 106, 170, 25);
		
		contentPane.add(dateField);
		empLbl.setFont(new Font("Tahoma", Font.PLAIN, 18));
		empLbl.setBounds(95, 180, 107, 25);
		
		contentPane.add(empLbl);
		empField.setEditable(false);
		empField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		empField.setColumns(10);
		empField.setBounds(229, 179, 170, 25);
		
		contentPane.add(empField);
		customerLbl.setFont(new Font("Tahoma", Font.PLAIN, 18));
		customerLbl.setBounds(95, 217, 107, 25);
		
		contentPane.add(customerLbl);
		custField.setEditable(false);
		custField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		custField.setColumns(10);
		custField.setBounds(229, 216, 170, 25);
		
		order = new OrderCtrl().findOrder(id);
		idField.setText(Integer.toString(order.orderID));
		amountField.setText(Double.toString(order.totalAmount));
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		Date processDate = order.processDate;
		String date = df.format(processDate);
		dateField.setText(date);
		statusField.setText(order.status.toString());
		empField.setText(order.employee.name);
		pointField.setText(order.point.address + " " + order.point.city );
		custField.setText(order.point.customer.name);
		
		contentPane.add(custField);
		updateBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				order.status = EnumMaps.getProcessStatus(statusField.getText());
				
				new OrderCtrl().updateStatus(order.orderID, order.status);
				
				JOptionPane.showMessageDialog(null, "The order was successfully updated!");
				
				idField.setText("");
				amountField.setText("");
				dateField.setText("");
				statusField.setText("");
				empField.setText("");
				pointField.setText("");
				custField.setText("");
			}
		});
		updateBtn.setFont(new Font("Tahoma", Font.PLAIN, 18));
		updateBtn.setBounds(185, 321, 107, 23);
		
		contentPane.add(updateBtn);
		pointField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		pointField.setEditable(false);
		pointField.setColumns(10);
		pointField.setBounds(229, 253, 170, 25);
		
		contentPane.add(pointField);
		pointLbl.setFont(new Font("Tahoma", Font.PLAIN, 18));
		pointLbl.setBounds(95, 254, 107, 25);
		
		contentPane.add(pointLbl);
	}
}
