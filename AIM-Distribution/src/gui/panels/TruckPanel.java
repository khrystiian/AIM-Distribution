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
import javax.swing.table.DefaultTableModel;

import controller.RouteCtrl;
import gui.windows.AddTruckWindow;
import model.Truck;

public class TruckPanel extends JPanel {
	private final JToolBar toolBar = new JToolBar();
	private final JLabel lblTrucks = new JLabel("TRUCKS");
	private final JButton addButton = new JButton("Add");
	private final JButton returnButton = new JButton("Remove");
	private final JButton findAllButton = new JButton("Find all");
	private final JButton findButton = new JButton("Find By Input");
	private final JTextField inputField = new JTextField();
	private final JButton clearButton = new JButton("Clear Table");
	private final JScrollPane scrollPane = new JScrollPane();
	private final JTable truckTable = new JTable();

	public TruckPanel() {
		setSize(new Dimension(1037, 612));
		setLayout(null);
		toolBar.setFloatable(false);
		toolBar.setBounds(10, 49, 902, 41);

		add(toolBar);
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new AddTruckWindow();
			}
		});
		addButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		addButton.setIcon(new ImageIcon("resources/Add.png"));

		toolBar.add(addButton);
		returnButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int action = JOptionPane.showConfirmDialog(null, "Are you sure you want permanently to delete this route ?", "Delete", JOptionPane.YES_NO_OPTION);
				if(action == 0){
				int plate = Integer.parseInt((String) truckTable.getValueAt(truckTable.getSelectedRow(), 0));

				new RouteCtrl().removeTruck(plate);

				((DefaultTableModel) truckTable.getModel()).removeRow(truckTable.getSelectedRow());

				}
			}
		});
		returnButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		returnButton.setIcon(new ImageIcon("resources/Remove.png"));

		toolBar.add(returnButton);
		findAllButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int size = new RouteCtrl().findAllTrucks().size();

				String[][] rows = new String[size][2];

				for (int i = 0; i < size; i++) {
					Truck truck = new RouteCtrl().findAllTrucks().get(i);

					rows[i][0] = Integer.toString(truck.plateNo);
					rows[i][1] = truck.available ? "Yes" : "No";
				}

			}
		});
		findAllButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		findAllButton.setIcon(new ImageIcon("resources/Find all.png"));

		toolBar.add(findAllButton);
		findButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
				int plate = Integer.parseInt(inputField.getText());

				Truck truck = new RouteCtrl().findTruck(plate);

				String[] row = new String[] { Integer.toString(truck.plateNo), truck.available ? "Yes" : "No" };

				((DefaultTableModel) truckTable.getModel()).addRow(row);
			}catch(NullPointerException npe){
				
			}catch(NumberFormatException fe){
				
			}
			}
		});
		findButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		findButton.setIcon(new ImageIcon("resources/Find magnifying glass.png"));

		toolBar.add(findButton);
		inputField.setFont(new Font("Tahoma", Font.PLAIN, 16));
		inputField.setColumns(10);

		toolBar.add(inputField);
		clearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				truckTable.setModel(new DefaultTableModel());
			}
		});
		clearButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		clearButton.setIcon(new ImageIcon("resources/Clear all.png"));

		toolBar.add(clearButton);
		lblTrucks.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblTrucks.setBounds(10, 11, 130, 27);

		add(lblTrucks);
		scrollPane.setBounds(10, 101, 902, 500);

		add(scrollPane);
		truckTable.setBounds(0, 0, 900, 0);
		truckTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		truckTable.setRowHeight(20);
		truckTable.setFont(new Font("Tahoma", Font.PLAIN, 14));
		final String[] headerItems = new String[] { "Plate number", "Available" };
		String[][] rows = new String[0][2];
		DefaultTableModel model = new DefaultTableModel(rows, headerItems);

		scrollPane.setViewportView(truckTable);
	}
}
