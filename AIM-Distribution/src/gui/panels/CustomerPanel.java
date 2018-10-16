package gui.panels;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

import controller.CustomerCtrl;
import gui.windows.AddCustomerWindow;
import gui.windows.AddDeliveryPointWindow;
import gui.windows.UpdateCustomerWindow;
import jdk.nashorn.internal.scripts.JO;
import model.Customer;
import model.DeliveryPoint;
import model.Department;
import util.Session;


public class CustomerPanel extends JPanel {

	private final JLabel customersLbl = new JLabel("CUSTOMERS");
	private final JToolBar custToolBar = new JToolBar();
	private final JButton addBtn = new JButton("Add");
	private final JButton updateBtn = new JButton("Update");
	private final JButton removeBtn = new JButton("Remove");
	private final JButton findAllBtn = new JButton("Find all");
	private final JButton findByInputBtn = new JButton("Find By Input");
	private final JTextField inputField = new JTextField();
	private final JButton clearBtn = new JButton("Clear Table");
	private final JTable customerTable = new JTable();
	private final JScrollPane customerTableScroll = new JScrollPane();
	private final JTable deliveryPointsTable = new JTable();
	private final JScrollPane deliveryPointsTableScroll = new JScrollPane();
	private final JButton addPointBtn = new JButton("Add point");

	/**
	 * Create the panel.
	 */
	public CustomerPanel() {
		super();
		setLayout(null);
		setSize(new Dimension(1037, 784));

		customersLbl.setFont(new Font("Tahoma", Font.PLAIN, 18));
		customersLbl.setBounds(10, 11, 130, 27);

		add(customersLbl);
		custToolBar.setFloatable(false);
		custToolBar.setBounds(10, 49, 902, 41);
		
		add(customerTableScroll);
		customerTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		customerTable.setFont(new Font("Tahoma", Font.PLAIN, 16));
		customerTableScroll.setViewportView(customerTable);
		final String headerItems[] = new String[] { "ID", "Name", "Phone number", "E-mail"};
		String[][] rows = new String[0][4];
		DefaultTableModel model = new DefaultTableModel(rows, headerItems);
		customerTable.setModel(model);
		customerTable.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 16));
		customerTable.setRowHeight(20);
		
		
		final String header[] = new String[] {"Address", "Zipcode", "City", "Region", "Country"};
		String row[][] = new String[0][5];
		DefaultTableModel delModel = new DefaultTableModel(row, header);
		deliveryPointsTable.setModel(delModel);
		deliveryPointsTable.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 16));
		deliveryPointsTable.setRowHeight(20);
		
		customerTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e){
				if(customerTable.getSelectionModel().isSelectionEmpty()){
					updateBtn.setEnabled(false);
				}else{
					updateBtn.setEnabled(true);
					String ID = customerTable.getModel().getValueAt(customerTable.getSelectedRow(), 0).toString();
					int id = Integer.parseInt(ID);
					List<DeliveryPoint> dps = new CustomerCtrl().findAllPointsByCustomer(id); 
					String Row[][] = new String[dps.size()][5];
					for(int i = 0; i < dps.size(); i++){
						DeliveryPoint dp = dps.get(i);
						Row[i][0] = dp.address;
						Row[i][1] = dp.zipcode;
						Row[i][2] = dp.city;
						Row[i][3] = dp.region;
						Row[i][4] = dp.country;
					}
					
					DefaultTableModel delModel = new DefaultTableModel(Row, header);
					deliveryPointsTable.setModel(delModel);
					
				}
			}
		});
		
		customerTableScroll.setBounds(10, 101, 902, 248);
		deliveryPointsTableScroll.setBounds(10, 371, 902, 325);
		
		add(deliveryPointsTableScroll);
		deliveryPointsTable.setFont(new Font("Tahoma", Font.PLAIN, 16));
		deliveryPointsTableScroll.setViewportView(deliveryPointsTable);
		deliveryPointsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		deliveryPointsTable.setFont(new Font("Tahoma", Font.PLAIN, 16));
		deliveryPointsTableScroll.setViewportView(deliveryPointsTable);

		
		
		add(custToolBar);
		addBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new AddCustomerWindow();
			}
		});
		addBtn.setIcon(new ImageIcon("resources/Add user.png"));
		addBtn.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		custToolBar.add(addBtn);
		updateBtn.setEnabled(false);
		updateBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new UpdateCustomerWindow(Integer.parseInt((String) customerTable.getModel().getValueAt(customerTable.getSelectedRow(), 0)));			
			
			}
			
		});
		updateBtn.setIcon(new ImageIcon("resources/Update.png"));
		updateBtn.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		if(Session.employee.department.equals(Department.HUMAN_RESOURCES)){
			addBtn.setEnabled(false);
		}
		addPointBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					new AddDeliveryPointWindow(Integer.parseInt((String) customerTable.getModel().getValueAt(customerTable.getSelectedRow(), 0)));
				
				}catch(ArrayIndexOutOfBoundsException aiobe){
					JOptionPane.showMessageDialog(null, "Please select a customer to add a delivery point ! ");
				}
			}
		});
		addPointBtn.setFont(new Font("Tahoma", Font.PLAIN, 16));
		addPointBtn.setIcon(new ImageIcon("resources/Point.png"));
		
		custToolBar.add(addPointBtn);
		
		custToolBar.add(updateBtn);
		removeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					DefaultTableModel model = (DefaultTableModel) customerTable.getModel();
					customerTable.setModel(model);
					int action = JOptionPane.showConfirmDialog(null, "Are you sure you want permanently to delete this customer? ", "Delete", JOptionPane.YES_NO_OPTION);
					if(action == 0){
						new CustomerCtrl().deleteCustomer(Integer.parseInt((customerTable.getValueAt(customerTable.getSelectedRow(), 0).toString())));						
						model.removeRow(customerTable.getSelectedRow());
						JOptionPane.showMessageDialog(null, "The customer has been successfully deleted!");
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

		custToolBar.add(removeBtn);
		findAllBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String[][] rows = new String[new CustomerCtrl().getAllCustomers().size()][5];
				for(int i = 0; i < new CustomerCtrl().getAllCustomers().size(); i++){
					rows[i][0] = Integer.toString(new CustomerCtrl().getAllCustomers().get(i).id);
					rows[i][1] = new CustomerCtrl().getAllCustomers().get(i).phone;
					rows[i][2] = new CustomerCtrl().getAllCustomers().get(i).name;
					rows[i][3] = new CustomerCtrl().getAllCustomers().get(i).email;
				}
				DefaultTableModel model = new DefaultTableModel(rows, headerItems);
				customerTable.setModel(model);
			}
		});
		findAllBtn.setFont(new Font("Tahoma", Font.PLAIN, 16));
		findAllBtn.setIcon(new ImageIcon("resources/Find all.png"));

		custToolBar.add(findAllBtn);
		findByInputBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String input = inputField.getText();
				String [][] rows = new String[1][4];
				Customer customer = null;
				try{
					int id = Integer.parseInt(input);
					customer = new CustomerCtrl().findCustomerByID(id);
					
					inputField.setText("");
				}catch(NumberFormatException nfe){
					customer = new CustomerCtrl().findCustomerByName(input);
				}catch(NullPointerException npe){
					return;
				}try{
				rows[0][0] = Integer.toString(customer.id);
				rows[0][1] = customer.name;
				rows[0][2] = customer.phone;
				rows[0][3] = customer.email;
				
				DefaultTableModel model = new DefaultTableModel(rows, headerItems);
				customerTable.setModel(model);
				}catch(NullPointerException npe1){
					
				}
			}
		});
		findByInputBtn.setIcon(new ImageIcon("resources/Find magnifying glass.png"));

		findByInputBtn.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		custToolBar.add(findByInputBtn);
		inputField.setFont(new Font("Tahoma", Font.PLAIN, 16));
		inputField.setColumns(10);
		
		custToolBar.add(inputField);
		clearBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int action = JOptionPane.showConfirmDialog(null, "Do you really want to clear all?", "Clear", 
						JOptionPane.YES_NO_OPTION);
				if(action == 0){
					String[][] customerRows = new String[0][4];
					String[][] pointRows = new String[0][5];
					DefaultTableModel model = new DefaultTableModel(customerRows, headerItems);
					customerTable.setModel(model);
					DefaultTableModel pointModel = new DefaultTableModel(pointRows, header);
					deliveryPointsTable.setModel(pointModel);
				}
			}
		});
		clearBtn.setFont(new Font("Tahoma", Font.PLAIN, 16));
		clearBtn.setIcon(new ImageIcon("resources/Clear all.png"));

		custToolBar.add(clearBtn);
		
		
	}
}
