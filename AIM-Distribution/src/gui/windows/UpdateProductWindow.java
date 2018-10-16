package gui.windows;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.ProductCtrl;
import model.Product;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class UpdateProductWindow extends JFrame {

	private JPanel contentPane;
	private final JLabel barcodeLbl = new JLabel("Barcode");
	private final JLabel priceLbl = new JLabel("Price");
	private final JLabel quantLbl = new JLabel("Quantity");
	private final JLabel minStockLbl = new JLabel("Minimum stock");
	private final JLabel descrLbl = new JLabel("Description");
	public final JTextField barcodeField = new JTextField();
	public final JTextField priceField = new JTextField();
	public final JTextField quantField = new JTextField();
	public final JTextField minStockField = new JTextField();
	public final JTextField descrField = new JTextField();
	private final JButton btnUpdate = new JButton("Update");
	public final JTextField nameField = new JTextField();
	private final JLabel nameLbl = new JLabel("Name");
	
	private Product product = null;

	/**
	 * Create the frame.
	 */
	public UpdateProductWindow(String barcode) {
		setTitle("Update a product");
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 528, 419);
		
		product = new ProductCtrl().findProduct(barcode);
		nameField.setText(product.name);
		barcodeField.setText(product.barcode);
		priceField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if(priceField.getText().equals("") && e.getKeyChar() == '0')
					e.consume();
				if(!Character.isDigit(c) && e.getKeyChar() != '.')
					e.consume();
				if(e.getKeyChar() == '.' && priceField.getText().contains("."))
					e.consume();
				if(priceField.getText().contains(".") && ((priceField.getText().substring(priceField.getText().lastIndexOf(".")+1)).length()>1))
		           e.consume();
			}
		});
		priceField.setText(Double.toString(product.price));
		quantField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if(!Character.isDigit(c) && e.getKeyChar() != '.')
					e.consume();
			}
		});
		quantField.setText(Integer.toString(product.quantity));
		minStockField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if(!Character.isDigit(c) && e.getKeyChar() != '.')
					e.consume();
			}
		});
		minStockField.setText(Integer.toString(product.minStock));
		descrField.setText(product.description);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		barcodeLbl.setFont(new Font("Tahoma", Font.PLAIN, 18));
		barcodeLbl.setBounds(87, 85, 107, 25);
		
		contentPane.add(barcodeLbl);
		priceLbl.setFont(new Font("Tahoma", Font.PLAIN, 18));
		priceLbl.setBounds(87, 121, 107, 33);
		
		contentPane.add(priceLbl);
		quantLbl.setFont(new Font("Tahoma", Font.PLAIN, 18));
		quantLbl.setBounds(87, 165, 107, 25);
		
		contentPane.add(quantLbl);
		minStockLbl.setFont(new Font("Tahoma", Font.PLAIN, 18));
		minStockLbl.setBounds(87, 201, 131, 33);
		
		contentPane.add(minStockLbl);
		descrLbl.setFont(new Font("Tahoma", Font.PLAIN, 18));
		descrLbl.setBounds(87, 245, 107, 25);
		
		contentPane.add(descrLbl);
		barcodeField.setEditable(false);
		barcodeField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		barcodeField.setColumns(10);
		barcodeField.setBounds(241, 85, 170, 25);
		
		contentPane.add(barcodeField);
		priceField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		priceField.setColumns(10);
		priceField.setBounds(241, 128, 170, 25);
		
		contentPane.add(priceField);
		quantField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		quantField.setColumns(10);
		quantField.setBounds(241, 167, 170, 25);
		
		contentPane.add(quantField);
		minStockField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		minStockField.setColumns(10);
		minStockField.setBounds(241, 208, 170, 25);
		
		contentPane.add(minStockField);
		descrField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		descrField.setColumns(10);
		descrField.setBounds(241, 247, 170, 25);
		
		contentPane.add(descrField);
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				product.barcode = barcodeField.getText();
				product.name = nameField.getText();
				product.price = Double.parseDouble(priceField.getText());
				product.quantity = Integer.parseInt(quantField.getText());
				product.minStock = Integer.parseInt(minStockField.getText());
				product.description = descrField.getText();
				
				new ProductCtrl().updateProduct(product);
				
				JOptionPane.showMessageDialog(null, "The product has been succesfully updated!");
				
				barcodeField.setText("");
				nameField.setText("");
				priceField.setText("");
				quantField.setText("");
				minStockField.setText("");
				descrField.setText("");
			}
		});
		btnUpdate.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnUpdate.setBounds(184, 302, 117, 23);
		
		contentPane.add(btnUpdate);
		nameField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		nameField.setColumns(10);
		nameField.setBounds(241, 47, 170, 25);
		
		contentPane.add(nameField);
		nameLbl.setFont(new Font("Tahoma", Font.PLAIN, 18));
		nameLbl.setBounds(87, 47, 107, 25);
		
		contentPane.add(nameLbl);
	}

}
