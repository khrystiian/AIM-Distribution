package driverApp;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Time;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import controller.OrderCtrl;
import controller.RouteCtrl;
import model.Delivery;
import model.ProcessStatus;

public class RouteWindow extends JFrame {

	private JPanel contentPane;
	private final JScrollPane scrollPane = new JScrollPane();
	private final JTable deliveriesTable = new JTable();
	private final JLabel lblDeliveries = new JLabel("Route - Deliveries");
	private final JButton btnMarkAsFinished = new JButton("Mark as finished");
	private final JButton btnFinishRoute = new JButton("Finish route");

	public RouteWindow(int routeID) {
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 646, 502);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		scrollPane.setBounds(10, 52, 610, 163);

		List<Delivery> deliveries = new RouteCtrl().findRouteDeliveries(routeID);

		btnMarkAsFinished.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int deliveryID = Integer.parseInt((String) deliveriesTable.getValueAt(deliveriesTable.getSelectedRow(), 0));
				Delivery delivery = new RouteCtrl().findDelivery(deliveryID);
				new OrderCtrl().updateStatus(delivery.ord.orderID, ProcessStatus.FINISHED);
				deliveriesTable.setValueAt(ProcessStatus.FINISHED, deliveriesTable.getSelectedRow(), 4);


			}
		});

		btnMarkAsFinished.setEnabled(false);

		contentPane.add(scrollPane);
		deliveriesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		final String[] header = new String[] { "ID", "City", "Address", "Order ID", "Status" };
		String[][] rows = new String[0][3];
		DefaultTableModel DTM = new DefaultTableModel(rows, header);
		deliveriesTable.setModel(DTM);
		deliveriesTable.setRowHeight(20);

		scrollPane.setViewportView(deliveriesTable);
		lblDeliveries.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblDeliveries.setBounds(237, 27, 158, 14);

		contentPane.add(lblDeliveries);
		btnMarkAsFinished.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnMarkAsFinished.setBounds(225, 253, 170, 23);

		contentPane.add(btnMarkAsFinished);
		btnFinishRoute.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "The route was successfully finished!");
				RouteCtrl ctrl = new RouteCtrl();
				ctrl.changeRouteDeliveryTime(routeID, new Time(System.currentTimeMillis() - ctrl.findRoute(routeID).startingTime.getTime()));
				new DriverWindow();
				dispose();
			}
		});
		btnFinishRoute.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnFinishRoute.setBounds(225, 429, 170, 23);
		
		contentPane.add(btnFinishRoute);

		for (Delivery delivery : deliveries)
			((DefaultTableModel) deliveriesTable.getModel()).addRow(new String[] { Integer.toString(delivery.id), delivery.ord.point.city, delivery.ord.point.address, Integer.toString(delivery.ord.orderID), delivery.ord.status.toString() });

		deliveriesTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (deliveriesTable.getSelectionModel().isSelectionEmpty())
					btnMarkAsFinished.setEnabled(false);
				else
					btnMarkAsFinished.setEnabled(true);
			}

		});

	}
}
