package gui.panels;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

import controller.ResupplyCtrl;
import gui.windows.AddSupplyWindow;
import gui.windows.UpdateSupplyWindow;
import model.OrderProduct;
import model.Resupply;

public class SupplyPanel extends JPanel {
	private final JLabel supplyLbl = new JLabel("SUPPLY");
	private final JToolBar supplyToolBar = new JToolBar();
	private final JButton addBtn = new JButton("Add");
	private final JButton updateBtn = new JButton("Update");
	private final JButton findAllBtn = new JButton("Find all");
	private final JButton findByInputBtn = new JButton("Find By Input");
	private final JTextField inputField = new JTextField();
	private final JButton clearAllBtn = new JButton("Clear Table");
	private final JTable supplyTable = new JTable();
	private final JTable productTable = new JTable();
	private final JScrollPane supplyTableScroll = new JScrollPane();
	private final JScrollPane productPane = new JScrollPane();

	/**
	 * Create the panel.
	 */
	public SupplyPanel() {
		super();
		setSize(new Dimension(1037, 746));
		setLayout(null);
		supplyLbl.setFont(new Font("Tahoma", Font.PLAIN, 18));
		supplyLbl.setBounds(10, 11, 130, 27);

		add(supplyLbl);
		supplyToolBar.setFloatable(false);
		supplyToolBar.setBounds(10, 49, 902, 41);

		supplyTableScroll.setBounds(10, 101, 902, 248);
		add(supplyTableScroll);
		supplyTable.setFont(new Font("Tahoma", Font.PLAIN, 16));
		supplyTableScroll.setViewportView(supplyTable);
		final String[] headerItems = new String[] { "ID", "Supplier", "Date", "Status", "Price", "Employee" };
		String[][] rows = new String[0][6];
		DefaultTableModel model = new DefaultTableModel(rows, headerItems);
		supplyTable.setModel(model);
		supplyTable.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 16));
		supplyTable.setRowHeight(20);
		supplyTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		productPane.setBounds(10, 371, 900, 325);
		add(productPane);
		productTable.setFont(new Font("Tahoma", Font.PLAIN, 16));
		productPane.setViewportView(productTable);
		final String[] headerItemsProd = new String[] { "Barcode", "Name", "Quantity", "Price" };
		String[][] rowsProd = new String[0][4];
		DefaultTableModel modelProd = new DefaultTableModel(rowsProd, headerItemsProd);
		productTable.setModel(modelProd);
		productTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		productTable.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 16));
		productTable.setRowHeight(20);

		supplyTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				int id = 0;
				if (supplyTable.getSelectionModel().isSelectionEmpty())
					return;

				id = Integer.parseInt((String) supplyTable.getModel().getValueAt(supplyTable.getSelectedRow(), 0));

				java.util.List<OrderProduct> products = new ResupplyCtrl().findResupply(id).products;
				int size = products.size();
				
				System.out.println(size);
				
				String[][] rows = new String[size][4];

				for (int i = 0; i < size; i++) {
					OrderProduct op = products.get(i);
					rows[i][0] = op.product.barcode;
					rows[i][1] = op.product.name;
					rows[i][2] = Integer.toString(op.quantity);
					rows[i][3] = Double.toString(op.price);
					System.out.println(rows[i][0]);
				}

				DefaultTableModel model = new DefaultTableModel(rows, headerItemsProd);
				productTable.setModel(model);

			}

		});

		add(supplyToolBar);
		addBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new AddSupplyWindow();
			}
		});
		addBtn.setFont(new Font("Tahoma", Font.PLAIN, 16));
		addBtn.setIcon(new ImageIcon("resources/Add.png"));

		supplyToolBar.add(addBtn);
		updateBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new UpdateSupplyWindow(Integer.parseInt((String)supplyTable.getValueAt(supplyTable.getSelectedRow(), 0)));
			}
		});
		updateBtn.setFont(new Font("Tahoma", Font.PLAIN, 16));
		updateBtn.setIcon(new ImageIcon("resources/Update.png"));

		supplyToolBar.add(updateBtn);
		findAllBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int size = new ResupplyCtrl().findAllResupplies().size();

				String[][] rows = new String[size][6];

				for (int i = 0; i < size; i++) {
					Resupply resupply = new ResupplyCtrl().findAllResupplies().get(i);
					rows[i][0] = Integer.toString(resupply.resupplyID);
					rows[i][1] = resupply.supplier;
					rows[i][2] = resupply.processDate.toString();
					rows[i][3] = resupply.status.toString();
					rows[i][4] = Double.toString(resupply.getFinalPrice(0));
					rows[i][5] = resupply.employee.name;
				}

				DefaultTableModel model = new DefaultTableModel(rows, headerItems);
				supplyTable.setModel(model);

			
			}});
		findAllBtn.setFont(new Font("Tahoma", Font.PLAIN, 16));
		findAllBtn.setIcon(new ImageIcon("resources/Find all.png"));

		supplyToolBar.add(findAllBtn);
		findByInputBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String input = inputField.getText();
				String[][] rows = new String[1][6];
				Resupply resupply = null;

				try {
					int id = Integer.parseInt(input);
					resupply = new ResupplyCtrl().findResupply(id);
				} catch (NumberFormatException ex) {
					if (input.equals("")) {
						JOptionPane.showMessageDialog(null, "Please input an ID or a name!");
						return;
					}
					return;
				}

				rows[0][0] = Integer.toString(resupply.resupplyID);
				rows[0][1] = resupply.supplier;
				rows[0][2] = resupply.processDate.toString();
				rows[0][3] = resupply.status.toString();
				rows[0][4] = Double.toString(resupply.getFinalPrice(0));
				rows[0][5] = resupply.employee.name;

				DefaultTableModel model = new DefaultTableModel(rows, headerItems);
				supplyTable.setModel(model);
				
				inputField.setText("");

			}
		});
		findByInputBtn.setFont(new Font("Tahoma", Font.PLAIN, 16));
		findByInputBtn.setIcon(new ImageIcon("resources/Find magnifying glass.png"));

		supplyToolBar.add(findByInputBtn);
		inputField.setFont(new Font("Tahoma", Font.PLAIN, 16));
		inputField.setColumns(10);

		supplyToolBar.add(inputField);
		clearAllBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int action = JOptionPane.showConfirmDialog(null, "Are you sure you want to clear all ?", "Clear", JOptionPane.YES_NO_OPTION);
				if(action == 0){
				supplyTable.setModel(new DefaultTableModel());
				productTable.setModel(new DefaultTableModel());
				}
			}
		});
		clearAllBtn.setFont(new Font("Tahoma", Font.PLAIN, 16));
		clearAllBtn.setIcon(new ImageIcon("resources/Clear all.png"));

		supplyToolBar.add(clearAllBtn);

	}
}
