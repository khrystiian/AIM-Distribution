package gui.panels;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import controller.OrderCtrl;
import gui.windows.AddOrderWindow;
import gui.windows.UpdateOrderWindow;
import model.Order;
import model.OrderProduct;

public class OrderPanel extends JPanel {
	private final JLabel ordersLbl = new JLabel("ORDERS");
	private final JToolBar ordersToolBar = new JToolBar();
	private final JTable ordersTable = new JTable();
	private final JScrollPane ordersTableScroll = new JScrollPane();
	private final JButton addBtn = new JButton("Add");
	private final JButton updateBtn = new JButton("Update");
	private final JButton findAllBtn = new JButton("Find all");
	private final JButton findByInputBtn = new JButton("Find By Input");
	private final JTextField inputField = new JTextField();
	private final JButton clearTableBtn = new JButton("Clear Table");
	private final JTable productsTable = new JTable();
	private final JScrollPane productsTableScrollPane = new JScrollPane();
	private final JButton btnPay = new JButton("Pay");

	/**
	 * Create the panel.
	 */
	public OrderPanel() {
		super();
		setSize(new Dimension(1037, 710));
		setLayout(null);
		ordersLbl.setFont(new Font("Tahoma", Font.PLAIN, 18));
		ordersLbl.setBounds(10, 11, 130, 27);
		
		updateBtn.setEnabled(false);
		btnPay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnPay.setEnabled(false);

		final String[] header = new String[] { "ID", "Date", "Total Amount", "Status", "Employee" };
		String[][] rows = new String[0][6];
		DefaultTableModel DTM = new DefaultTableModel(rows, header);
		ordersTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		ordersTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (ordersTable.getSelectionModel().isSelectionEmpty()) {
					updateBtn.setEnabled(false);
					btnPay.setEnabled(false);
				}
				else {
					updateBtn.setEnabled(true);
					btnPay.setEnabled(true);
				}
			}

		});
		ordersTable.setModel(DTM);
		ordersTable.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 16));
		ordersTable.setRowHeight(20);

		final String[] headers = new String[] {"Barcode", "Name", "Quantity", "Price" };
		String[][] productRows = new String[0][5];
		DefaultTableModel model = new DefaultTableModel(productRows, headers);
		productsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		productsTable.setModel(model);
		productsTable.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 16));
		productsTable.setRowHeight(20);
		
		ordersTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				int id = 0;
				if (ordersTable.getSelectionModel().isSelectionEmpty())
					return;

				id = Integer.parseInt((String) ordersTable.getModel().getValueAt(ordersTable.getSelectedRow(), 0));

				List<OrderProduct> products = new OrderCtrl().findOrder(id).products;
				int size = products.size();
				
				String[][] rows = new String[size][4];
				
				for(int i = 0; i < size; i++){
					OrderProduct op = products.get(i);
					rows[i][0] = op.product.barcode;
					rows[i][1] = op.product.name;
					rows[i][2] = Integer.toString(op.quantity);
					rows[i][3] = Double.toString(op.price);
				}
				
				DefaultTableModel model = new DefaultTableModel(rows, headers);
				productsTable.setModel(model);
			}

		});
		
		add(ordersLbl);
		ordersToolBar.setFloatable(false);
		ordersToolBar.setBounds(10, 49, 846, 45);

		add(ordersToolBar);
		ordersToolBar.setBounds(10, 49, 902, 41);
		addBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new AddOrderWindow();
			}
		});
		addBtn.setFont(new Font("Tahoma", Font.PLAIN, 16));
		addBtn.setIcon(new ImageIcon("resources/Add.png"));

		ordersToolBar.add(addBtn);
		updateBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					new UpdateOrderWindow(Integer.parseInt((String) ordersTable.getModel().getValueAt(ordersTable.getSelectedRow(), 0)));
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Please select an order from the table first!");
				}
			}
		});
		updateBtn.setFont(new Font("Tahoma", Font.PLAIN, 16));
		updateBtn.setIcon(new ImageIcon("resources/Update.png"));

		ordersToolBar.add(updateBtn);
		findAllBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String[][] rows = new String[(new OrderCtrl().findAllOrders()).size()][6];
				for (int i = 0; i < new OrderCtrl().findAllOrders().size(); i++) {
					Order ord = new OrderCtrl().findAllOrders().get(i);
					rows[i][0] = Integer.toString(ord.orderID);
					DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
					Date processDate = ord.processDate;
					String date = df.format(processDate);
					rows[i][1] = date;
					rows[i][2] = Double.toString(ord.totalAmount);
					rows[i][3] = ord.status.toString();
					rows[i][4] = ord.employee.name;
				}

				DefaultTableModel model = new DefaultTableModel(rows, header);
				ordersTable.setModel(model);
			}
		});
		btnPay.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		ordersToolBar.add(btnPay);
		findAllBtn.setFont(new Font("Tahoma", Font.PLAIN, 16));
		findAllBtn.setIcon(new ImageIcon("resources/Find all.png"));

		ordersToolBar.add(findAllBtn);
		findByInputBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String input = inputField.getText();
				String[][] rows = new String[1][5];
				Order ord = null;
				try {
					int id = Integer.parseInt(input);
					ord = new OrderCtrl().findOrder(id);

				rows[0][0] = Integer.toString(ord.orderID);
				DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
				Date processDate = ord.processDate;
				String date = df.format(processDate);
				rows[0][1] = date;
				rows[0][2] = Double.toString(ord.totalAmount);
				rows[0][3] = ord.status.toString();
				rows[0][4] = ord.employee.name;

				DefaultTableModel model = new DefaultTableModel(rows, header);
				ordersTable.setModel(model);
				
				ord = new OrderCtrl().findOrder(Integer.parseInt(input));
				
				inputField.setText("");
				
				} catch (NumberFormatException nfe) {
					if (input.equals("")) {
						JOptionPane.showMessageDialog(null, "Please input an ID!");
						return;
					}
				} catch (NullPointerException npe) {
					JOptionPane.showMessageDialog(null, "Please input an ID!");
					return;
				}
			}
		});
		findByInputBtn.setFont(new Font("Tahoma", Font.PLAIN, 16));
		findByInputBtn.setIcon(new ImageIcon("resources/Find magnifying glass.png"));

		ordersToolBar.add(findByInputBtn);
		inputField.setFont(new Font("Tahoma", Font.PLAIN, 16));
		inputField.setColumns(10);

		ordersToolBar.add(inputField);
		clearTableBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int action = JOptionPane.showConfirmDialog(null, "Do you really want to clear all ?", "Clear", JOptionPane.YES_NO_OPTION);
				if(action == 0){
				ordersTable.setModel(DTM);
				productsTable.setModel(model);
				}
			}
		});
		clearTableBtn.setFont(new Font("Tahoma", Font.PLAIN, 16));
		clearTableBtn.setIcon(new ImageIcon("resources/Clear all.png"));

		ordersToolBar.add(clearTableBtn);

		add(ordersTableScroll);
		ordersTable.setFont(new Font("Tahoma", Font.PLAIN, 16));
		ordersTableScroll.setViewportView(ordersTable);
		ordersTableScroll.setBounds(10, 101, 902, 248);
		ordersTable.setRowHeight(20);
		productsTableScrollPane.setBounds(10, 371, 900, 325);
		
		add(productsTableScrollPane);
		productsTableScrollPane.setViewportView(productsTable);

	}
}
