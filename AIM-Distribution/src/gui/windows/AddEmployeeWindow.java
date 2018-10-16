package gui.windows;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import controller.EmployeeCtrl;
import model.Department;
import model.Employee;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class AddEmployeeWindow extends JFrame {

	private JPanel contentPane;
	private final JLabel nameLbl = new JLabel("Name");
	private final JLabel salaryLbl = new JLabel("Salary");
	private final JLabel departmentLbl = new JLabel("Department");
	private final JLabel supervisorLbl = new JLabel("Supervisor");
	private final JTextField nameField = new JTextField();
	private final JTextField salaryField = new JTextField();
	private final JComboBox deptComboBox = new JComboBox();
	private final JComboBox supervisorComboBox = new JComboBox();
	private final JButton addBtn = new JButton("Add");
	private final JLabel lblError = new JLabel("Error");

	/**
	 * Create the frame.
	 */
	public AddEmployeeWindow() {

		setTitle("Add an employee");
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 528, 419);
		nameField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				char c = arg0.getKeyChar();
				if(Character.isDigit(c))
					arg0.consume();
			}
		});

		nameField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		nameField.setBounds(218, 82, 170, 25);
		nameField.setColumns(10);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		nameLbl.setFont(new Font("Tahoma", Font.PLAIN, 18));
		nameLbl.setBounds(82, 82, 107, 25);

		contentPane.add(nameLbl);
		salaryLbl.setFont(new Font("Tahoma", Font.PLAIN, 18));
		salaryLbl.setBounds(82, 118, 107, 26);

		contentPane.add(salaryLbl);
		departmentLbl.setFont(new Font("Tahoma", Font.PLAIN, 18));
		departmentLbl.setBounds(82, 155, 107, 33);

		contentPane.add(departmentLbl);
		supervisorLbl.setFont(new Font("Tahoma", Font.PLAIN, 18));
		supervisorLbl.setBounds(82, 199, 107, 25);

		contentPane.add(supervisorLbl);

		contentPane.add(nameField);
		salaryField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if(salaryField.getText().equals("") && e.getKeyChar() == '0')
					e.consume();
				if(!Character.isDigit(c) && e.getKeyChar() != '.')
					e.consume();
				if(e.getKeyChar() == '.' && salaryField.getText().contains("."))
					e.consume();
				if(salaryField.getText().contains(".") && ((salaryField.getText().substring(salaryField.getText().lastIndexOf(".")+1)).length()>1))
		           e.consume();
			}
		});
		salaryField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		salaryField.setColumns(10);
		salaryField.setBounds(218, 121, 170, 25);

		contentPane.add(salaryField);

		deptComboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {

				supervisorComboBox.removeAllItems();

				supervisorComboBox.addItem("None");

				if (!deptComboBox.getSelectedItem().toString().equals("CEO"))
					supervisorComboBox.addItem(new EmployeeCtrl().findEmployee("Erik Lam").name);

				for (Employee emp : new EmployeeCtrl().findAllEmployeesFrom(deptComboBox.getSelectedItem().toString()))
					supervisorComboBox.addItem(emp.name);

			}
		});

		{
			deptComboBox.addItem(Department.CEO);
			deptComboBox.addItem(Department.MARKETING);
			deptComboBox.addItem(Department.WAREHOUSE);
			deptComboBox.addItem(Department.DISTRIBUTION);
			deptComboBox.addItem(Department.IT_SUPPORT);
			deptComboBox.addItem(Department.HUMAN_RESOURCES);
		}

		deptComboBox.setBounds(218, 164, 170, 25);

		contentPane.add(deptComboBox);
		supervisorComboBox.setBounds(218, 204, 170, 25);

		contentPane.add(supervisorComboBox);
		addBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblError.setVisible(false);
				if (nameField.getText().equals("") || salaryField.getText().equals("")) {
					lblError.setText("Please fill in all the fields.");
					lblError.setVisible(true);
					return;
				}

				try {
					String name = nameField.getText();
					double salary = Double.parseDouble(salaryField.getText());
					Department department = (Department) deptComboBox.getSelectedItem();

					Employee supervisor = null;
					if (!supervisorComboBox.getSelectedItem().equals("None")) {
						supervisor = new EmployeeCtrl().findEmployee((String) supervisorComboBox.getSelectedItem());
						new EmployeeCtrl().insertEmployee(name, (int) salary, supervisor.employeeID, department);
						nameField.setText("");
						salaryField.setText("");
						deptComboBox.setSelectedIndex(0);
						supervisorComboBox.setSelectedIndex(0);
						JOptionPane.showMessageDialog(null, "The employee was successfully added!");

					} else {
						new EmployeeCtrl().insertEmployee(name, (int) salary, 0, department);
					}

				} catch (NumberFormatException ex) {
					lblError.setText("The salary must be a number.");
					lblError.setVisible(true);
					return;
				
				}

			}
		});
		addBtn.setFont(new Font("Tahoma", Font.PLAIN, 18));
		addBtn.setBounds(194, 292, 89, 23);

		contentPane.add(addBtn);
		lblError.setForeground(Color.RED);
		lblError.setVisible(false);
		lblError.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblError.setBounds(82, 22, 306, 25);

		contentPane.add(lblError);
	}
}
