package gui.windows;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.ProductCtrl;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class AddProductWindow extends JFrame {

	private JPanel contentPane;
	private final JLabel nameLbl = new JLabel("Name");
	private final JLabel barcodeLbl = new JLabel("Barcode");
	private final JLabel priceLbl = new JLabel("Price");
	private final JLabel quantityLbl = new JLabel("Quantity");
	private final JLabel minStockLbl = new JLabel("Minimum stock");
	private final JLabel descrLbl = new JLabel("Description");
	private final JTextField txtFieldName = new JTextField();
	private final JTextField txtBarcodeField = new JTextField();
	private final JTextField txtPriceField = new JTextField();
	private final JTextField txtQuantField = new JTextField();
	private final JTextField TxtStockField = new JTextField();
	private final JTextField txtDescField = new JTextField();
	private final JButton addBtn = new JButton("Add");
	private final JLabel lblError = new JLabel("");
	
	/**
	 * Create the frame.
	 */
	public AddProductWindow() {
		setTitle("Add a product");
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 528, 419);
		
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		nameLbl.setFont(new Font("Tahoma", Font.PLAIN, 18));
		nameLbl.setBounds(90, 57, 107, 25);
		
		contentPane.add(nameLbl);
		barcodeLbl.setFont(new Font("Tahoma", Font.PLAIN, 18));
		barcodeLbl.setBounds(90, 93, 107, 26);
		
		contentPane.add(barcodeLbl);
		priceLbl.setFont(new Font("Tahoma", Font.PLAIN, 18));
		priceLbl.setBounds(90, 130, 107, 33);
		
		contentPane.add(priceLbl);
		quantityLbl.setFont(new Font("Tahoma", Font.PLAIN, 18));
		quantityLbl.setBounds(90, 174, 107, 25);
		
		contentPane.add(quantityLbl);
		minStockLbl.setFont(new Font("Tahoma", Font.PLAIN, 18));
		minStockLbl.setBounds(90, 210, 131, 33);
		
		contentPane.add(minStockLbl);
		descrLbl.setFont(new Font("Tahoma", Font.PLAIN, 18));
		descrLbl.setBounds(90, 254, 107, 25);
		
		contentPane.add(descrLbl);
		txtFieldName.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtFieldName.setColumns(10);
		txtFieldName.setBounds(231, 57, 187, 25);
		
		contentPane.add(txtFieldName);
		txtBarcodeField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtBarcodeField.setColumns(10);
		txtBarcodeField.setBounds(231, 96, 187, 25);
		
		contentPane.add(txtBarcodeField);
		txtPriceField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if(txtPriceField.getText().equals("") && e.getKeyChar() == '0')
					e.consume();
				if(!Character.isDigit(c) && e.getKeyChar() != '.')
					e.consume();
				if(e.getKeyChar() == '.' && txtPriceField.getText().contains("."))
					e.consume();
				if(txtPriceField.getText().contains(".") && ((txtPriceField.getText().substring(txtPriceField.getText().lastIndexOf(".")+1)).length()>1))
		           e.consume();
			}
		});
		txtPriceField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtPriceField.setColumns(10);
		txtPriceField.setBounds(231, 137, 187, 25);
		
		contentPane.add(txtPriceField);
		txtQuantField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if(!Character.isDigit(c) && e.getKeyChar() != '.')
					e.consume();
			}
		});
		txtQuantField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtQuantField.setColumns(10);
		txtQuantField.setBounds(231, 176, 187, 25);
		
		contentPane.add(txtQuantField);
		TxtStockField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if(!Character.isDigit(c) && e.getKeyChar() != '.')
					e.consume();
			}
		});
		TxtStockField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		TxtStockField.setColumns(10);
		TxtStockField.setBounds(231, 217, 187, 25);
		
		contentPane.add(TxtStockField);
		txtDescField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtDescField.setColumns(10);
		txtDescField.setBounds(231, 256, 187, 25);
		
		contentPane.add(txtDescField);
		addBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				lblError.setVisible(false);
				
				if(txtBarcodeField.getText().equals("") || txtDescField.getText().equals("") ||
				   txtFieldName.getText().equals("") || txtPriceField.getText().equals("") ||
				   txtQuantField.getText().equals("") || TxtStockField.getText().equals("")){
					lblError.setText("Please fill in all the fields.");
					lblError.setVisible(true);
				}
				else{
					String barcode = txtBarcodeField.getText();
					String description = txtDescField.getText();
					String name = txtFieldName.getText();
					Double price = Double.parseDouble(txtPriceField.getText());
					int quantity = Integer.parseInt(txtQuantField.getText());
					int minStock = Integer.parseInt(TxtStockField.getText());
					new ProductCtrl().insert(barcode, name, price, quantity, minStock, description);
					txtBarcodeField.setText("");
					txtDescField.setText("");
					txtFieldName.setText("");
					txtPriceField.setText("");
					txtQuantField.setText("");
					TxtStockField.setText("");
					JOptionPane.showMessageDialog(null, "A new product has been added.");
				}
			}
		});
		addBtn.setFont(new Font("Tahoma", Font.PLAIN, 18));
		addBtn.setBounds(210, 311, 89, 23);
		
		contentPane.add(addBtn);
		lblError.setForeground(Color.RED);
		lblError.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblError.setBounds(57, 13, 303, 33);
		
		contentPane.add(lblError);
	}
}
