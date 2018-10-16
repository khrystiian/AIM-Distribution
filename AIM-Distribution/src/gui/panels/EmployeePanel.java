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
import javax.swing.JLabel;

import gui.windows.AddEmployeeWindow;
import gui.windows.UpdateEmployeeWindow;

import javax.swing.JLabel;

import controller.EmployeeCtrl;
import model.Employee;
import util.Session;
import util.TreeNode;

public class EmployeePanel extends JPanel {
	private final JToolBar empToolBar = new JToolBar();
	ImageIcon viewAll = new ImageIcon("1464300479_eye.png");
	private final JButton addBtn = new JButton("Add");
	private final JButton removeBtn = new JButton("Remove");
	private final JButton findAllBtn = new JButton("Find all");
	private final JTextField inputField = new JTextField();
	private final JButton findByInputBtn = new JButton("Find By Input");
	private final JTable empTable = new JTable();
	private final JScrollPane empTabelScroll = new JScrollPane();
	private final JButton clearTableBtn = new JButton("Clear Table");
	private final JLabel lblEmployees = new JLabel("EMPLOYEES");
	private final JButton updateBtn = new JButton("Update");

	public EmployeePanel() {
		super();

		inputField.setFont(new Font("Tahoma", Font.PLAIN, 16));
		inputField.setColumns(10);

		setSize(new Dimension(1037, 612));
		setLayout(null);
		empToolBar.setFloatable(false);

		empToolBar.setBounds(10, 49, 902, 41);

		add(empToolBar);
		addBtn.setIcon(new ImageIcon("resources/Add user.png"));
		addBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new AddEmployeeWindow();
			}
		});
		addBtn.setFont(new Font("Tahoma", Font.PLAIN, 16));

		empToolBar.add(addBtn);
		updateBtn.setIcon(new ImageIcon("resources/Update.png"));
		updateBtn.setEnabled(false);
		updateBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new UpdateEmployeeWindow(Integer.parseInt((String) empTable.getModel().getValueAt(empTable.getSelectedRow(), 0)));
			}
		});
		updateBtn.setFont(new Font("Tahoma", Font.PLAIN, 16));

		empToolBar.add(updateBtn);
		removeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int action = JOptionPane.showConfirmDialog(null, "Do you really want to permanently remove this employee ?", "Delete", JOptionPane.YES_NO_OPTION);
					if (action == 0) {
						int id = (int) empTable.getModel().getValueAt(empTable.getSelectedRow(), 0);
						new EmployeeCtrl().deleteEmployee(id);
						((DefaultTableModel) empTable.getModel()).removeRow(empTable.getSelectedRow());

						JOptionPane.showMessageDialog(null, "The employee was successfully removed!");
					}
				} catch (ArrayIndexOutOfBoundsException aeiob) {

				} catch (ClassCastException cce) {

				}
			}
		});
		removeBtn.setIcon(new ImageIcon("resources/Remove.png"));
		removeBtn.setFont(new Font("Tahoma", Font.PLAIN, 16));

		final String[] headerItems = new String[] { "ID", "Name", "Salary", "Department", "Supervisor" };
		String[][] rows = new String[0][5];
		DefaultTableModel model = new DefaultTableModel(rows, headerItems);
		empTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		empTable.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 16));
		empTable.setRowHeight(20);
		empTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (empTable.getSelectionModel().isSelectionEmpty())
					updateBtn.setEnabled(false);
				else
					updateBtn.setEnabled(true);
			}

		});
		empTable.setModel(model);
		empToolBar.add(removeBtn);

		findAllBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int size = new EmployeeCtrl().getAllAsTree(Session.employee.employeeID).getAll().size();

				String[][] rows = new String[size][5];

				for (int i = 0; i < size; i++) {
					Employee emp = new EmployeeCtrl().getAllAsTree(Session.employee.employeeID).getAll().get(i).data;
					rows[i][0] = Integer.toString(emp.employeeID);
					rows[i][1] = emp.name;
					rows[i][2] = Double.toString(emp.salary);
					rows[i][3] = emp.department.toString();
					if (emp.supervisorID != 0)
						rows[i][4] = new EmployeeCtrl().findEmployee(emp.supervisorID).name;
					else
						rows[i][4] = "";
				}

				DefaultTableModel model = new DefaultTableModel(rows, headerItems);
				empTable.setModel(model);
			}
		});
		findAllBtn.setIcon(new ImageIcon("resources/Find all.png"));
		findAllBtn.setFont(new Font("Tahoma", Font.PLAIN, 16));

		empToolBar.add(findAllBtn);
		findByInputBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EmployeeCtrl controller = new EmployeeCtrl();
				String input = inputField.getText();
				String[][] rows = new String[1][5];
				Employee employee = null;
				try {
					int id = Integer.parseInt(input);
					employee = controller.findEmployee(id);
					List<TreeNode<Employee>> employees = controller.getAllAsTree(Session.employee.employeeID).getAll();
					boolean found = false;
					for(TreeNode node : employees)
						if(node.data.equals(employee))
							found = true;
					inputField.setText("");
				} catch (NumberFormatException ex) {
					employee = controller.findEmployee(input);
					
				} catch (NullPointerException ex2) {
					return;
				}

				try {

					rows[0][0] = Integer.toString(employee.employeeID);
					rows[0][1] = employee.name;
					rows[0][2] = Double.toString(employee.salary);
					rows[0][3] = employee.department.toString();
					if (employee.supervisorID != 0)
						rows[0][4] = controller.findEmployee(employee.supervisorID).name;
					else
						rows[0][4] = "";
					DefaultTableModel model = new DefaultTableModel(rows, headerItems);
					empTable.setModel(model);

				} catch (NullPointerException ex) {
				}
}
		
			
		});
		findByInputBtn.setIcon(new ImageIcon("resources/Find magnifying glass.png"));
		findByInputBtn.setFont(new Font("Tahoma", Font.PLAIN, 16));

		empToolBar.add(findByInputBtn);

		empToolBar.add(inputField);
		clearTableBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int action = JOptionPane.showConfirmDialog(null, "Do you really want to clear all ?", "Clear", JOptionPane.YES_NO_OPTION);
				if (action == 0) {
					String[][] rows = new String[0][5];
					DefaultTableModel model = new DefaultTableModel(rows, headerItems);
					empTable.setModel(model);
				}
			}
		});
		clearTableBtn.setIcon(new ImageIcon("resources/Clear all.png"));
		clearTableBtn.setFont(new Font("Tahoma", Font.PLAIN, 16));

		empToolBar.add(clearTableBtn);
		empTabelScroll.setBounds(10, 101, 902, 500);

		add(empTabelScroll);
		empTabelScroll.setViewportView(empTable);
		empTable.setFont(new Font("Tahoma", Font.PLAIN, 14));

		lblEmployees.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblEmployees.setBounds(10, 11, 130, 27);

		add(lblEmployees);
		setVisible(true);
	}
}
