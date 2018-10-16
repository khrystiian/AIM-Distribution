package driverApp;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Time;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import controller.RouteCtrl;
import database.RouteDB;
import model.Route;

public class DriverWindow extends JFrame {

	private JPanel contentPane;
	private final JScrollPane scrollPane = new JScrollPane();
	private final JTable routesTable = new JTable();
	private final JLabel lblName = new JLabel("Name");
	private final JTextField txtName = new JTextField();
	private final JButton btnFindRoutes = new JButton("Find routes");
	private final JButton btnStartRoute = new JButton("Start route");

	public static void main(String[] args) {
		new Thread() {
			public void run() {
				try {
					DriverWindow frame = new DriverWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.start();
	}

	public DriverWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 646, 502);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		scrollPane.setBounds(10, 80, 610, 163);

		contentPane.add(scrollPane);

		
		txtName.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtName.setBounds(179, 29, 193, 24);
		txtName.setColumns(10);
		
		
		routesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		routesTable.setRowHeight(20);
		final String[] header = new String[] { "ID", "Truck", "Finished" };
		String[][] rows = new String[0][3];
		DefaultTableModel DTM = new DefaultTableModel(rows, header);
		routesTable.setModel(DTM);
		routesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		routesTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(routesTable.getSelectionModel().isSelectionEmpty())
					btnStartRoute.setEnabled(false);
				else btnStartRoute.setEnabled(true);
			}
			
		});

		scrollPane.setViewportView(routesTable);
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblName.setBounds(76, 29, 66, 22);

		contentPane.add(lblName);

		contentPane.add(txtName);
		btnFindRoutes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				List<Route> routes = new RouteDB().getAllRoutes();

				for (Route route : routes) {
					if (route.emp.name.equals(txtName.getText()))
						((DefaultTableModel) routesTable.getModel()).addRow(new String[] { Integer.toString(route.routeID), Integer.toString(route.truck.plateNo), route.finished ? "Yes" : "No" });
				}

			}
		});
		btnFindRoutes.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnFindRoutes.setBounds(414, 29, 144, 22);

		contentPane.add(btnFindRoutes);
		btnStartRoute.setEnabled(false);
		btnStartRoute.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 int id = Integer.parseInt((String)routesTable.getValueAt(routesTable.getSelectedRow(), 0));
				 new RouteCtrl().changeStartingTime(id, new Time(System.currentTimeMillis()));
				 new RouteWindow(id);
				 dispose();
			}
		});
		btnStartRoute.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnStartRoute.setBounds(228, 265, 144, 22);
		
		contentPane.add(btnStartRoute);
	}
}
