
package gui.windows;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.EmployeeCtrl;
import model.Employee;
import util.EnumMaps;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class UpdateEmployeeWindow extends JFrame {

	private JPanel contentPane;
	private final JLabel nameLbl = new JLabel("Name");
	private final JLabel salaryLbl = new JLabel("Salary");
	private final JLabel deptLbl = new JLabel("Department");
	private final JLabel supervisorLbl = new JLabel("Supervisor");
	private final JTextField nameField = new JTextField();
	private final JTextField salaryField = new JTextField();
	private final JButton updateBtn = new JButton("Update");
	private final JTextField deptField = new JTextField();
	private final JTextField supervisorField = new JTextField();

	private Employee employee = null;

	/**
	 * Create the frame.
	 */
	public UpdateEmployeeWindow(int id) {
		setTitle("Update an employee");
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 515, 419);
		
		employee = new EmployeeCtrl().findEmployee(id);
		nameField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if(Character.isDigit(c))
					e.consume();
			}
		});
		nameField.setText(employee.name);
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
		salaryField.setText(Double.toString(employee.salary));
		deptField.setText(employee.department.toString());
		try {
			supervisorField.setText(new EmployeeCtrl().findEmployee(employee.supervisorID).name);			
		} catch (NullPointerException e) {
			
		}

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		nameLbl.setFont(new Font("Tahoma", Font.PLAIN, 18));
		nameLbl.setBounds(106, 95, 107, 25);

		contentPane.add(nameLbl);
		salaryLbl.setFont(new Font("Tahoma", Font.PLAIN, 18));
		salaryLbl.setBounds(106, 131, 107, 26);

		contentPane.add(salaryLbl);
		deptLbl.setFont(new Font("Tahoma", Font.PLAIN, 18));
		deptLbl.setBounds(106, 168, 107, 33);

		contentPane.add(deptLbl);
		supervisorLbl.setFont(new Font("Tahoma", Font.PLAIN, 18));
		supervisorLbl.setBounds(106, 212, 107, 25);

		contentPane.add(supervisorLbl);
		nameField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		nameField.setColumns(10);
		nameField.setBounds(242, 95, 170, 25);

		contentPane.add(nameField);
		salaryField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		salaryField.setColumns(10);
		salaryField.setBounds(242, 134, 170, 25);

		contentPane.add(salaryField);
		updateBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				employee.name = nameField.getText();
				employee.salary = Double.parseDouble(salaryField.getText());
				employee.department = EnumMaps.getDepartment(deptField.getText());
				employee.supervisorID = new EmployeeCtrl().findEmployee(supervisorField.getText()).employeeID;

				new EmployeeCtrl().updateEmployee(employee);
				
				JOptionPane.showMessageDialog(null, "The employee was successfully updated!");
				
				nameField.setText("");
				salaryField.setText("");
				deptField.setText("");
				supervisorField.setText("");
			}
		});
		updateBtn.setFont(new Font("Tahoma", Font.PLAIN, 18));
		updateBtn.setBounds(203, 309, 107, 23);

		contentPane.add(updateBtn);
		deptField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		deptField.setColumns(10);
		deptField.setBounds(242, 173, 170, 25);

		contentPane.add(deptField);
		supervisorField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		supervisorField.setColumns(10);
		supervisorField.setBounds(242, 212, 170, 25);

		contentPane.add(supervisorField);
	}
}
