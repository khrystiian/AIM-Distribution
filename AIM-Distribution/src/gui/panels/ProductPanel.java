package gui.panels;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import controller.ProductCtrl;
import gui.windows.AddProductWindow;
import gui.windows.UpdateProductWindow;
import model.Product;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ProductPanel extends JPanel {
	private final JLabel productLbl = new JLabel("PRODUCTS");
	private final JToolBar productToolBar = new JToolBar();
	private final JButton addBtn = new JButton("Add");
	private final JButton updateBtn = new JButton("Update");
	private final JButton removeBtn = new JButton("Remove");
	private final JButton findAllBtn = new JButton("Find all");
	private final JButton findByInputBtn = new JButton("Find By Input");
	private final JTextField inputField = new JTextField();
	private final JButton clearTableBtn = new JButton("Clear Table");
	private final JTable productTable = new JTable();
	private final JScrollPane productTableScroll = new JScrollPane();

	/**
	 * Create the panel.
	 */
	public ProductPanel() {
		super();
		setSize(new Dimension(1037, 612));
		setLayout(null);

		productLbl.setFont(new Font("Tahoma", Font.PLAIN, 18));
		productLbl.setBounds(10, 11, 130, 27);
		productTableScroll.setBounds(10, 101, 902, 500);
		addBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new AddProductWindow();
			}
		});
		addBtn.setFont(new Font("Tahoma", Font.PLAIN, 16));
		addBtn.setIcon(new ImageIcon("resources/Add.png"));
		
		productToolBar.add(addBtn);
		updateBtn.setEnabled(false);
		
		updateBtn.setFont(new Font("Tahoma", Font.PLAIN, 16));
		updateBtn.setIcon(new ImageIcon("resources/Update.png"));

		productToolBar.add(updateBtn);

		add(productTableScroll);
		productTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		productTable.setFont(new Font("Tahoma", Font.PLAIN, 16));
		productTableScroll.setViewportView(productTable);
		final String headerItems[] = new String[] { "Barcode", "Name", "Price", "Quantity", "Min Stock",
				"Description" };
		String Rows[][] = new String[0][6];
		DefaultTableModel model = new DefaultTableModel(Rows, headerItems);
		productTable.setModel(model);
		productTable.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 16));
		productTable.setRowHeight(20);
		productTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(productTable.getSelectionModel().isSelectionEmpty())
				updateBtn.setEnabled(false);
				else
					updateBtn.setEnabled(true);
			}
		});

		add(productLbl);
		productToolBar.setFloatable(false);
		productToolBar.setBounds(10, 49, 902, 41);

		add(productToolBar);
		updateBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
                 new UpdateProductWindow((String) productTable.getModel().getValueAt(productTable.getSelectedRow(), 0));
				}
				catch(ArrayIndexOutOfBoundsException aiofbe ){
					System.out.println("");
				}catch(NullPointerException npe){
					System.out.println("");
				}
			}
		});
		
		removeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				   try{
					   DefaultTableModel model = (DefaultTableModel) productTable.getModel();
					   productTable.setModel(model);
					   int action = JOptionPane.showConfirmDialog(null, "Are you sure you want permanently to delete this product ?", "Delete", JOptionPane.YES_NO_OPTION);
					   if(action == 0){
						   new ProductCtrl().delete(productTable.getValueAt(productTable.getSelectedRow(), 0).toString());
						   model.removeRow(productTable.getSelectedRow());
						   JOptionPane.showMessageDialog(null, "The product has been succesfully deleted! ");
					   }
				   }catch(ArrayIndexOutOfBoundsException aiobe){
					   System.out.println("");				   
				   }catch(NullPointerException npe){
					   System.out.println("");
				   }
			}
		});
		removeBtn.setFont(new Font("Tahoma", Font.PLAIN, 16));
		removeBtn.setIcon(new ImageIcon("resources/Remove.png"));

		productToolBar.add(removeBtn);
		findAllBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				String Rows[][] = new String[new ProductCtrl().getAllProducts().size()][7];
				for (int i = 0; i < new ProductCtrl().getAllProducts().size(); i++) {
					Rows[i][0] = new ProductCtrl().getAllProducts().get(i).barcode;
					Rows[i][1] = new ProductCtrl().getAllProducts().get(i).name;
					Rows[i][2] = Double.toString(new ProductCtrl().getAllProducts().get(i).price);
					Rows[i][3] = Integer.toString(new ProductCtrl().getAllProducts().get(i).quantity);
					Rows[i][4] = Integer.toString(new ProductCtrl().getAllProducts().get(i).minStock);
					Rows[i][5] = new ProductCtrl().getAllProducts().get(i).description;
				}
				DefaultTableModel model = new DefaultTableModel(Rows, headerItems);
				productTable.setModel(model);
			}
		});
		findAllBtn.setFont(new Font("Tahoma", Font.PLAIN, 16));
		findAllBtn.setIcon(new ImageIcon("resources/Find all.png"));

		productToolBar.add(findAllBtn);
		findByInputBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String input = inputField.getText();
				String rows[][] = new String[1][7];
				Product product = null;
				try {
					String barcode = input;
					product =new ProductCtrl().findProduct(barcode);
					long bc = Long.parseLong(barcode);
				} catch (NumberFormatException nfe) {
					if (input.equals("")) {
						JOptionPane.showMessageDialog(null, "Please insert a correct barcode or name !");
						return;
					}
					product = new ProductCtrl().findProductByName(input);
				} catch (NullPointerException npe) {
					JOptionPane.showMessageDialog(null, "Please insert a correct barcode or name !");
					return;
				}

				rows[0][0] = product.barcode;
				rows[0][1] = product.name;
				rows[0][2] = Double.toString(product.price);
				rows[0][3] = Integer.toString(product.quantity);
				rows[0][4] = Integer.toString(product.minStock);
				rows[0][5] = product.description;

				DefaultTableModel model = new DefaultTableModel(rows, headerItems);
				productTable.setModel(model);
				
			}

		});
		findByInputBtn.setFont(new Font("Tahoma", Font.PLAIN, 16));
		findByInputBtn.setIcon(new ImageIcon("resources/Find magnifying glass.png"));

		productToolBar.add(findByInputBtn);
		inputField.setFont(new Font("Tahoma", Font.PLAIN, 16));
		inputField.setColumns(10);

		productToolBar.add(inputField);
		clearTableBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int action = JOptionPane.showConfirmDialog(null, "Do you really want to clear all?", "Clear", JOptionPane.YES_NO_OPTION);
				if(action == 0){
					String[][] Rows = new String[0][6];
					DefaultTableModel model = new DefaultTableModel(Rows, headerItems);
					productTable.setModel(model);
				}
			}
		});
		productToolBar.add(clearTableBtn);
		clearTableBtn.setFont(new Font("Tahoma", Font.PLAIN, 16));
		clearTableBtn.setIcon(new ImageIcon("resources/Clear all.png"));

	}
}
