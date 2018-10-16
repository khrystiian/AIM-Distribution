package gui.windows;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import controller.ProductCtrl;
import controller.ResupplyCtrl;
import model.Product;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class AddSupplyWindow extends JFrame {

	private JPanel contentPane;
	private final JLabel label = new JLabel("Products");
	private final JLabel label_2 = new JLabel("Barcode");
	private final JTextField txtBarcode = new JTextField();
	private final JButton addProdButton = new JButton("Add product");
	private final JButton addOrderButton = new JButton("Add resupply");
	private final JLabel label_3 = new JLabel("Error");
	private final JLabel lblSuppliersName = new JLabel("Supplier's name");
	private final JTextField txtSupplier = new JTextField();
	private final JScrollPane scrollPane = new JScrollPane();
	private final JButton btnCreateResupply = new JButton("Create resupply");
	private final JLabel label_5 = new JLabel("Quantity");
	private final JTextField txtQuantity = new JTextField();
	private final JTable productsTable = new JTable();
	private final JButton btnNewProduct = new JButton("New product");

	/**
	 * Create the frame.
	 */
	public AddSupplyWindow() {

		setTitle("Add a supply");
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 723, 503);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		label.setFont(new Font("Tahoma", Font.PLAIN, 18));
		label.setBounds(440, 41, 152, 27);

		contentPane.add(label);
		label_2.setFont(new Font("Tahoma", Font.PLAIN, 18));
		label_2.setBounds(99, 261, 92, 27);

		contentPane.add(label_2);
		txtBarcode.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtBarcode.setColumns(10);
		txtBarcode.setBounds(197, 260, 174, 34);

		final String header[] = new String[] { "Name", "Barcode", "Quantity", "Price" };
		String[][] rows = new String[0][4];
		DefaultTableModel model = new DefaultTableModel(rows, header);
		scrollPane.setBounds(433, 92, 234, 236);

		contentPane.add(scrollPane);
		scrollPane.setViewportView(productsTable);
		productsTable.setFont(new Font("Tahoma", Font.PLAIN, 14));
		productsTable.setModel(model);
		
		
		contentPane.add(txtBarcode);
		addProdButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String barcode = txtBarcode.getText();
				int quantity = 0;
				try {
					quantity = Integer.parseInt(txtQuantity.getText());
				} catch (NumberFormatException ex) {
					JOptionPane.showConfirmDialog(null, "The quantity must be a number.");
					return;
				}

				new ResupplyCtrl().addProduct(barcode, quantity);
				Product prod = new ProductCtrl().findProduct(barcode);
				
				String[] row = new String[] {prod.name, barcode, Integer.toString(quantity), Double.toString(prod.price)};
				((DefaultTableModel)productsTable.getModel()).addRow(row);
				
				txtBarcode.setText("");
				txtQuantity.setText("");
				
			}
		});
		addProdButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		addProdButton.setBounds(109, 360, 190, 27);

		contentPane.add(addProdButton);
		addOrderButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ResupplyCtrl().finishResupply();
				JOptionPane.showConfirmDialog(null, "The resupply was created!");
			}
		});
		addOrderButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		addOrderButton.setBounds(440, 384, 190, 27);

		contentPane.add(addOrderButton);
		label_3.setForeground(Color.RED);
		label_3.setFont(new Font("Tahoma", Font.PLAIN, 18));
		label_3.setBounds(148, 41, 104, 27);

		contentPane.add(label_3);
		lblSuppliersName.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblSuppliersName.setBounds(39, 130, 152, 27);

		contentPane.add(lblSuppliersName);
		txtSupplier.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if(Character.isDigit(c))
					e.consume();
			}
		});
		txtSupplier.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtSupplier.setColumns(10);
		txtSupplier.setBounds(197, 126, 174, 34);

		contentPane.add(txtSupplier);
		scrollPane.setBounds(421, 97, 234, 236);

		btnCreateResupply.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (txtSupplier.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Fill in the required fields.");
					return;
				}
				new ResupplyCtrl().startResupply(txtSupplier.getText());
			}
		});
		btnCreateResupply.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnCreateResupply.setBounds(109, 204, 190, 27);

		contentPane.add(btnCreateResupply);
		label_5.setFont(new Font("Tahoma", Font.PLAIN, 18));
		label_5.setBounds(99, 306, 92, 27);

		contentPane.add(label_5);
		txtQuantity.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if(!Character.isDigit(c) && e.getKeyChar() != '.')
					e.consume();
			}
		});
		txtQuantity.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtQuantity.setColumns(10);
		txtQuantity.setBounds(197, 305, 174, 34);

		contentPane.add(txtQuantity);
		btnNewProduct.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new AddProductWindow();
			}
		});
		btnNewProduct.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnNewProduct.setBounds(109, 398, 190, 27);

		contentPane.add(btnNewProduct);
	}
}
