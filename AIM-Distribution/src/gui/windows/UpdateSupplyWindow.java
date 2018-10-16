package gui.windows;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.ResupplyCtrl;
import model.Department;
import model.ProcessStatus;
import model.Resupply;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class UpdateSupplyWindow extends JFrame {

	private JPanel contentPane;
	private final JLabel statusLbl = new JLabel("Status");
	private final JLabel idLbl = new JLabel("ID");
	private final JTextField idField = new JTextField();
	private final JLabel amountLbl = new JLabel("Amount");
	private final JTextField amountField = new JTextField();
	private final JLabel dateLbl = new JLabel("Date");
	private final JTextField dateField = new JTextField();
	private final JLabel employeeLbl = new JLabel("Employee");
	private final JTextField employeeField = new JTextField();
	private final JButton updateBtn = new JButton("Update");
	private final JComboBox deptComboBox = new JComboBox();

	/**
	 * Create the frame.
	 */
	public UpdateSupplyWindow(int resupplyID) {

		setTitle("Update a supply");
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 528, 378);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		statusLbl.setFont(new Font("Tahoma", Font.PLAIN, 18));
		statusLbl.setBounds(105, 167, 107, 25);

		contentPane.add(statusLbl);
		idLbl.setFont(new Font("Tahoma", Font.PLAIN, 18));
		idLbl.setBounds(105, 56, 107, 25);

		contentPane.add(idLbl);
		idField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		idField.setEditable(false);
		idField.setColumns(10);
		idField.setBounds(239, 55, 170, 25);

		contentPane.add(idField);
		amountLbl.setFont(new Font("Tahoma", Font.PLAIN, 18));
		amountLbl.setBounds(105, 93, 107, 25);

		contentPane.add(amountLbl);
		amountField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if(amountField.getText().equals("") && e.getKeyChar() == '0')
					e.consume();
				if(!Character.isDigit(c) && e.getKeyChar() != '.')
					e.consume();
				if(e.getKeyChar() == '.' && amountField.getText().contains("."))
					e.consume();
				if(amountField.getText().contains(".") && ((amountField.getText().substring(amountField.getText().lastIndexOf(".")+1)).length()>1))
		           e.consume();
			}
		});
		amountField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		amountField.setEditable(false);
		amountField.setColumns(10);
		amountField.setBounds(239, 92, 170, 25);

		contentPane.add(amountField);
		dateLbl.setFont(new Font("Tahoma", Font.PLAIN, 18));
		dateLbl.setBounds(105, 131, 107, 25);

		contentPane.add(dateLbl);
		dateField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		dateField.setEditable(false);
		dateField.setColumns(10);
		dateField.setBounds(239, 130, 170, 25);

		contentPane.add(dateField);
		employeeLbl.setFont(new Font("Tahoma", Font.PLAIN, 18));
		employeeLbl.setBounds(105, 204, 107, 25);

		contentPane.add(employeeLbl);
		employeeField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		employeeField.setEditable(false);
		employeeField.setColumns(10);
		employeeField.setBounds(239, 203, 170, 25);

		contentPane.add(employeeField);
		updateBtn.setFont(new Font("Tahoma", Font.PLAIN, 18));
		updateBtn.setBounds(194, 275, 107, 23);

		contentPane.add(updateBtn);

		deptComboBox.setBounds(239, 167, 170, 25);
		contentPane.add(deptComboBox);

		Resupply resupply = new ResupplyCtrl().findResupply(resupplyID);
		idField.setText(Integer.toString(resupplyID));
		amountField.setText(Double.toString(resupply.getFinalPrice(0)));
		dateField.setText(resupply.processDate.toString());
		{
			deptComboBox.addItem(ProcessStatus.DELAYED);
			deptComboBox.addItem(ProcessStatus.FINISHED);
			deptComboBox.addItem(ProcessStatus.IN_PROGRESS);
			deptComboBox.addItem(ProcessStatus.ORDERED);
		}
		deptComboBox.setSelectedItem(resupply.status);
		employeeField.setText(resupply.employee.name);
	}
}
