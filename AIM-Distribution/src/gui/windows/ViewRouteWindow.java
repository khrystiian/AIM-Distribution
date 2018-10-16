package gui.windows;

import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Time;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import controller.RouteCtrl;
import model.Delivery;
import model.ProcessStatus;
import model.Route;
import util.GraphNode;

public class ViewRouteWindow extends JFrame implements Runnable {

	private JPanel contentPane;
	private final JScrollPane scrollPane = new JScrollPane();
	private final JTable donetable = new JTable();
	private final JLabel lblEstimatedTimeLeft = new JLabel("Time passed on this route: ");

	private int routeID;
	private boolean running = false;

	public ViewRouteWindow(int routeID) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				running = false;
			}
		});

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 776, 466);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setVisible(true);

		scrollPane.setBounds(10, 62, 740, 354);
		contentPane.add(scrollPane);
		donetable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		donetable.setRowHeight(20);
		donetable.setFont(new Font("Tahoma", Font.PLAIN, 16));
		scrollPane.setViewportView(donetable);
		final String[] headerItems = new String[] { "Customer", "Order ID", "Address", "City" };
		String[][] rows = new String[0][5];
		DefaultTableModel model = new DefaultTableModel(rows, headerItems);
		donetable.setModel(model);
		lblEstimatedTimeLeft.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblEstimatedTimeLeft.setBounds(225, 22, 451, 27);

		contentPane.add(lblEstimatedTimeLeft);
		donetable.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 16));
		fillTable(routeID);

		this.routeID = routeID;
		new Thread(this, "Counter").start();
	}

	public void fillTable(int routeID) {
		Route route = new RouteCtrl().findRoute(routeID);
		System.out.println(route.deliveries.size());
		for (Delivery delivery : route.deliveries)
			if (delivery.ord.status.equals(ProcessStatus.FINISHED)) {
				((DefaultTableModel) donetable.getModel()).addRow(new String[] { delivery.ord.point.customer.name, Integer.toString(delivery.ord.orderID), delivery.ord.point.address, delivery.ord.point.city });
				route.graph.addNode(new GraphNode<Delivery>(delivery));
			}

	}

	@Override
	public void run() {
		RouteCtrl controller = new RouteCtrl();
		Route route = controller.findRoute(routeID);
		Time startingTime = route.startingTime;
		running = true;
		long lastTime = System.currentTimeMillis();
		while (running) {
			long now = System.currentTimeMillis();
			lblEstimatedTimeLeft.setText("Time passed on this route: " + new Time(System.currentTimeMillis() - startingTime.getTime()).toString());
			if (now - lastTime >= 1000 * 60 * 5) {
				route = controller.findRoute(routeID);
				for (Delivery delivery : route.deliveries)
					if (delivery.ord.status.equals(ProcessStatus.FINISHED)) {
						((DefaultTableModel) donetable.getModel()).addRow(new String[] { delivery.ord.point.customer.name, Integer.toString(delivery.ord.orderID), delivery.ord.point.address, delivery.ord.point.city });
						route.graph.addNode(new GraphNode<Delivery>(delivery));
					}
				lastTime = now;
			}

		}

	}
}
