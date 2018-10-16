package gui.panels;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import controller.EmployeeCtrl;
import gui.MainWindow;
import gui.windows.ChangePasswordWindow;
import model.Department;
import model.Employee;
import util.Session;

public class ProfilePanel extends JPanel {
	private final JLabel nameLbl = new JLabel("Name");
	private final JLabel salaryLbl = new JLabel("Salary");
	private final JLabel deptLbl = new JLabel("Department");
	private final JLabel supervisorLbl = new JLabel("Supervisor");
	private final JTextField nameField = new JTextField();
	private final JTextField salaryField = new JTextField();
	private final JTextField departmentField = new JTextField();
	private final JTextField supervisorField = new JTextField();
	private final JLabel newPassLbl = new JLabel("New password");
	private final JButton changeBtn = new JButton("Change password");
	private final JButton logoutBtn = new JButton("Logout");
	private final JPasswordField passwordField = new JPasswordField();

	/**
	 * Create the panel.
	 */
	public ProfilePanel() {
		super();
		setSize(new Dimension(1037, 612));
		setLayout(null);
		nameLbl.setFont(new Font("Tahoma", Font.PLAIN, 18));
		nameLbl.setBounds(47, 82, 107, 25);

		add(nameLbl);
		salaryLbl.setFont(new Font("Tahoma", Font.PLAIN, 18));
		salaryLbl.setBounds(47, 118, 107, 26);

		add(salaryLbl);
		deptLbl.setFont(new Font("Tahoma", Font.PLAIN, 18));
		deptLbl.setBounds(47, 155, 107, 33);

		add(deptLbl);
		supervisorLbl.setFont(new Font("Tahoma", Font.PLAIN, 18));
		supervisorLbl.setBounds(47, 199, 107, 25);

		add(supervisorLbl);
		nameField.setEditable(false);
		nameField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		nameField.setColumns(10);
		nameField.setBounds(188, 82, 170, 25);

		add(nameField);
		salaryField.setEditable(false);
		salaryField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		salaryField.setColumns(10);
		salaryField.setBounds(188, 121, 170, 25);

		add(salaryField);
		departmentField.setEditable(false);
		departmentField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		departmentField.setColumns(10);
		departmentField.setBounds(188, 160, 170, 25);

		add(departmentField);
		supervisorField.setEditable(false);
		supervisorField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		supervisorField.setColumns(10);
		supervisorField.setBounds(188, 199, 170, 25);

		add(supervisorField);
		newPassLbl.setFont(new Font("Tahoma", Font.PLAIN, 18));
		newPassLbl.setBounds(47, 235, 149, 25);

		add(newPassLbl);
		changeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ChangePasswordWindow(passwordField.getPassword());
			}
		});
		changeBtn.setFont(new Font("Tahoma", Font.PLAIN, 18));
		changeBtn.setBounds(85, 287, 203, 40);

		add(changeBtn);
		logoutBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Session.employee = null;
				System.exit(0);
			}
		});
		logoutBtn.setFont(new Font("Tahoma", Font.PLAIN, 18));
		logoutBtn.setBounds(85, 379, 203, 40);

		add(logoutBtn);
		
		passwordField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		passwordField.setBounds(188, 235, 170, 25);

		Employee emp = Session.employee;
		nameField.setText(emp.name);
		salaryField.setText(Double.toString(emp.salary));
		if (emp.department.equals(Department.CEO)) {
			supervisorField.setVisible(false);
			supervisorLbl.setVisible(false);
			newPassLbl.setBounds(47, 199, 149, 25);
			passwordField.setBounds(188, 199, 170, 25);
		}
		departmentField.setText(emp.department.toString());
		int supervisorID = emp.supervisorID;
		if (supervisorID != 0)
			supervisorField.setText(new EmployeeCtrl().findEmployee(supervisorID).name);

		add(passwordField);

	}
}
