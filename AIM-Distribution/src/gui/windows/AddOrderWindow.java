package gui.windows;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import controller.CustomerCtrl;
import controller.OrderCtrl;
import controller.OrderCtrl.NullOrderException;
import controller.ProductCtrl;
import model.DeliveryPoint;
import model.Product;

public class AddOrderWindow extends JFrame {

	private JPanel contentPane;
	private final JLabel productsLabel = new JLabel("Products");
	private final JLabel customerPointsLbl = new JLabel("Customer's points");
	private final JComboBox pointsComboBox = new JComboBox();
	private final JLabel barcodeLbl = new JLabel("Barcode");
	private final JTextField barcodeField = new JTextField();
	private final JButton addProductButton = new JButton("Add product");
	private final JButton addOrderBtn = new JButton("Add order");
	private final JLabel errorLbl = new JLabel("Error");
	private final JLabel custNameLbl = new JLabel("Customer's name");
	private final JTextField custNameField = new JTextField();
	private final JTable productsTable = new JTable();
	private final JScrollPane scrollPane = new JScrollPane();
	private final JButton btnCreateOrder = new JButton("Create order");
	private final JLabel quantLbl = new JLabel("Quantity");
	private final JTextField quantField = new JTextField();

	/**
	 * Create the frame.
	 */
	public AddOrderWindow() {
		setFont(new Font("Tahoma", Font.PLAIN, 18));
		barcodeField.setBounds(209, 255, 174, 34);
		barcodeField.setColumns(10);
		setTitle("Add an order");
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 723, 503);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		productsLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		productsLabel.setBounds(452, 36, 152, 27);

		contentPane.add(productsLabel);
		customerPointsLbl.setFont(new Font("Tahoma", Font.PLAIN, 18));
		customerPointsLbl.setBounds(51, 146, 152, 27);

		contentPane.add(customerPointsLbl);

		pointsComboBox.setFont(new Font("Tahoma", Font.PLAIN, 18));
		pointsComboBox.setBounds(209, 146, 174, 34);

		contentPane.add(pointsComboBox);
		barcodeLbl.setFont(new Font("Tahoma", Font.PLAIN, 18));
		barcodeLbl.setBounds(111, 256, 92, 27);

		contentPane.add(barcodeLbl);

		contentPane.add(barcodeField);

		addProductButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
				String[] rows = new String[5];
				String barcode = barcodeField.getText();

				int quantity = Integer.parseInt(quantField.getText()); 
				Product prod = new ProductCtrl().findProduct(barcode);
				rows[0] = prod.name;
				rows[1] = barcode;
				rows[2] = Double.toString(quantity);
				rows[3] = Double.toString(prod.price);
				new OrderCtrl().addProduct(barcode, quantity);
				DefaultTableModel dtm = (DefaultTableModel) productsTable.getModel();
				dtm.addRow(rows);
				}catch(NullPointerException npe ){
					JOptionPane.showMessageDialog(null, "Please fill up the fields with a valid input !");
				}catch(NumberFormatException nfe){
					
				}
			}
		});
		addProductButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		addProductButton.setBounds(121, 379, 190, 27);

		contentPane.add(addProductButton);
		addOrderBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
				new OrderCtrl().finishOrder();
				JOptionPane.showMessageDialog(null, "A new order has been added.");
				}catch(NullPointerException npe){
					JOptionPane.showMessageDialog(null, "Please fill up the fields with a valid input");
				}
			}
		});
		addOrderBtn.setFont(new Font("Tahoma", Font.PLAIN, 18));
		addOrderBtn.setBounds(452, 379, 190, 27);

		contentPane.add(addOrderBtn);
		errorLbl.setForeground(Color.RED);
		errorLbl.setFont(new Font("Tahoma", Font.PLAIN, 18));
		errorLbl.setBounds(160, 36, 104, 27);

		contentPane.add(errorLbl);
		custNameLbl.setFont(new Font("Tahoma", Font.PLAIN, 18));
		custNameLbl.setBounds(51, 96, 152, 27);

		contentPane.add(custNameLbl);
		custNameField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		custNameField.setColumns(10);
		custNameField.setBounds(209, 92, 174, 34);
		custNameField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
					System.out.println("Hello");
					int id = new CustomerCtrl().findCustomerByName(custNameField.getText()).id;
					for (DeliveryPoint dp : new CustomerCtrl().findAllPointsByCustomer(id)) {
						pointsComboBox.addItem(dp.address);
					}
				}
			}
		});

		contentPane.add(custNameField);
		final String header[] = new String[] { "Name", "Barcode", "Quantity", "Price" };
		String[][] rows = new String[0][4];
		DefaultTableModel model = new DefaultTableModel(rows, header);
		scrollPane.setBounds(433, 92, 234, 236);

		contentPane.add(scrollPane);
		scrollPane.setViewportView(productsTable);
		productsTable.setFont(new Font("Tahoma", Font.PLAIN, 14));
		productsTable.setModel(model);
		btnCreateOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
				String address = (String) pointsComboBox.getSelectedItem();
				int id = new CustomerCtrl().findCustomerByName(custNameField.getText()).id;
				DeliveryPoint dp = new CustomerCtrl().findPointByAddress(address, id);
				System.out.println(dp.address);
				new OrderCtrl().startOrder(dp.id);
				
			}catch(NullPointerException npe){
				JOptionPane.showMessageDialog(null, "Please fill up the fields with a valid input !");
			}
				
			}
		});
		btnCreateOrder.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnCreateOrder.setBounds(121, 211, 190, 27);

		contentPane.add(btnCreateOrder);
		quantLbl.setFont(new Font("Tahoma", Font.PLAIN, 18));
		quantLbl.setBounds(111, 301, 92, 27);

		contentPane.add(quantLbl);
		quantField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if(!Character.isDigit(c) && e.getKeyChar() != '.')
					e.consume();
			}
		});
		quantField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		quantField.setColumns(10);
		quantField.setBounds(209, 300, 174, 34);

		contentPane.add(quantField);
		productsTable.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 14));

	}
}
