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

import controller.RouteCtrl;
import gui.windows.AddRouteWindow;
import gui.windows.UpdateRouteWindow;
import gui.windows.ViewRouteWindow;
import model.Delivery;
import model.Route;

public class RoutePanel extends JPanel {
	private final JToolBar routeToolBar = new JToolBar();
	private final JLabel routeLbl = new JLabel("ROUTES");
	private final JButton addBtn = new JButton("Add");
	private final JButton viewBtn = new JButton("View route");
	private final JButton findAllBtn = new JButton("Find all");
	private final JButton findByInputBtn = new JButton("Find By Input");
	private final JTextField inputField = new JTextField();
	private final JButton clearBtn = new JButton("Clear Table");
	private final JTable routesTable = new JTable();
	private final JScrollPane routesScrollPane = new JScrollPane();
	private final JTable deliveriesTable = new JTable();
	private final JScrollPane deliveriesPane = new JScrollPane();

	/**
	 * Create the panel.
	 */
	public RoutePanel() {
		super();

		setSize(new Dimension(1037, 768));
		setLayout(null);
		routeToolBar.setFloatable(false);
		routeToolBar.setBounds(10, 49, 902, 41);

		add(routeToolBar);
		addBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new AddRouteWindow();
			}
		});
		addBtn.setFont(new Font("Tahoma", Font.PLAIN, 16));
		addBtn.setIcon(new ImageIcon("resources/Add.png"));

		routesScrollPane.setBounds(10, 101, 902, 248);

		add(routesScrollPane);
		routesScrollPane.setViewportView(routesTable);
		deliveriesPane.setBounds(10, 371, 902, 325);

		add(deliveriesPane);
		deliveriesPane.setViewportView(deliveriesTable);

		final String[] header = new String[] { "ID", "Finished", "Optimal Time", "Delivery Time", "Truck", "Employee" };
		String[][] rows = new String[0][6];
		DefaultTableModel DTM = new DefaultTableModel(rows, header);
		routesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		routesTable.setModel(DTM);
		routesTable.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 16));
		routesTable.setRowHeight(20);

		final String[] headers = new String[] { "ID", "Date", "Order", "Customer", "Delivery Point" };
		String[][] deliveryRows = new String[0][5];
		DefaultTableModel model = new DefaultTableModel(deliveryRows, headers);
		deliveriesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		deliveriesTable.setModel(model);
		deliveriesTable.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 16));
		deliveriesTable.setRowHeight(20);

		routesTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				try{
				int id = 0;
				if (routesTable.getSelectionModel().isSelectionEmpty())
					return;

				id = Integer.parseInt((String) routesTable.getModel().getValueAt(routesTable.getSelectedRow(), 0));

				List<Delivery> deliveries = new RouteCtrl().findRouteDeliveries(id);

				int size = deliveries.size();

				String[][] rows = new String[size][5];

				for (int i = 0; i < size; i++) {
					Delivery del = deliveries.get(i);
					rows[i][0] = Integer.toString(del.id);
					DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
					Date deliveryDate = del.deliveryDate;
					String date = deliveryDate == null ? "NULL" : df.format(deliveryDate);
					rows[i][1] = date;
					rows[i][2] = Integer.toString(del.ord.orderID);
					rows[i][3] = del.ord.point.customer.name;
					rows[i][4] = del.ord.point.address + " " + del.ord.point.city;
				}

				DefaultTableModel model = new DefaultTableModel(rows, headers);
				deliveriesTable.setModel(model);
			}catch(ClassCastException cce){
			}
			}
			

		});

		routeToolBar.add(addBtn);
		viewBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ViewRouteWindow(Integer.parseInt((String) routesTable.getModel().getValueAt(routesTable.getSelectedRow(), 0)));
			}
		});
		viewBtn.setFont(new Font("Tahoma", Font.PLAIN, 16));
		viewBtn.setIcon(new ImageIcon("resources/View Route 2.png"));

		routeToolBar.add(viewBtn);
		findAllBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				String[][] rows = new String[new RouteCtrl().findAllRoutes().size()][6];
				for (int i = 0; i < new RouteCtrl().findAllRoutes().size(); i++) {
					Route route = new RouteCtrl().findAllRoutes().get(i);
					rows[i][0] = Integer.toString(route.routeID);
					rows[i][1] = Boolean.toString(route.finished);
					rows[i][2] = route.optimalTime == null ? "NULL" : route.optimalTime.toString();
					rows[i][3] = route.deliveryTime == null ? "NULL" : route.deliveryTime.toString();
					rows[i][4] = Integer.toString(route.truck.plateNo);
					rows[i][5] = route.emp.name;
				}
				DefaultTableModel model = new DefaultTableModel(rows, header);
				routesTable.setModel(model);

			}
		});
		findAllBtn.setFont(new Font("Tahoma", Font.PLAIN, 16));
		findAllBtn.setIcon(new ImageIcon("resources/Find all.png"));

		routeToolBar.add(findAllBtn);
		findByInputBtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				String[] header = new String[] { "ID", "Finished", "Optimal Time", "Delivery Time", "Truck", "Employee" };
				String[][] rows = new String[0][6];
				DefaultTableModel model = new DefaultTableModel(rows, header);
				routesTable.setModel(model);
				try{
					Route route = new RouteCtrl().findRoute(Integer.parseInt(inputField.getText()));
					model = (DefaultTableModel) routesTable.getModel();
					model.addRow(new Object[] {route.routeID, route.finished, route.optimalTime, route.deliveryTime, route.truck, route.emp});
					routesTable.setModel(model);
					inputField.setText("");
				}catch(NullPointerException npe){
					JOptionPane.showMessageDialog(null, "The route does not exist.");
				}catch(NumberFormatException nfe){
					
				}
				
			}
		});
		findByInputBtn.setFont(new Font("Tahoma", Font.PLAIN, 16));
		findByInputBtn.setIcon(new ImageIcon("resources/Find magnifying glass.png"));

		routeToolBar.add(findByInputBtn);
		inputField.setFont(new Font("Tahoma", Font.PLAIN, 16));
		inputField.setColumns(10);

		routeToolBar.add(inputField);
		clearBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int action = JOptionPane.showConfirmDialog(null, "Are you sure you want to clear all ?", "Clear", JOptionPane.YES_NO_OPTION);
				if(action == 0){
				routesTable.setModel(DTM);
				deliveriesTable.setModel(model);
				}
			}
		});
		clearBtn.setFont(new Font("Tahoma", Font.PLAIN, 16));
		clearBtn.setIcon(new ImageIcon("resources/Clear all.png"));

		routeToolBar.add(clearBtn);
		routeLbl.setFont(new Font("Tahoma", Font.PLAIN, 18));
		routeLbl.setBounds(10, 11, 130, 27);

		add(routeLbl);
		routeToolBar.setFloatable(false);

	}
}
