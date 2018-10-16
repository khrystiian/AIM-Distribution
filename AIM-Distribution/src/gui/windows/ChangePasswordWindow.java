package gui.windows;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;

import controller.EmployeeCtrl;
import util.Encryption;
import util.Session;

public class ChangePasswordWindow extends JFrame {

	private JPanel contentPane;
	private final JLabel lblOldPassword = new JLabel("Old password");
	private final JButton btnNewButton = new JButton("Change");
	private final JPasswordField passwordField = new JPasswordField();

	/**
	 * Create the frame.
	 */
	public ChangePasswordWindow(char[] newPassword) {
		setTitle("Change password");
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 528, 419);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		lblOldPassword.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblOldPassword.setBounds(76, 147, 149, 25);

		contentPane.add(lblOldPassword);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (passwordField.getPassword().equals(null)) {
					JOptionPane.showMessageDialog(null, "Please fill in the old password");
				} else if (Encryption.areEqual(Encryption.encrypt(passwordField.getPassword()), Session.employee.getEncryptedPassword().toCharArray())) {
					new EmployeeCtrl().changePassword(Session.employee.employeeID, newPassword);
					JOptionPane.showMessageDialog(null, "Password changed.");
				}
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnNewButton.setBounds(163, 234, 139, 23);

		contentPane.add(btnNewButton);
		passwordField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		passwordField.setBounds(219, 147, 191, 25);

		contentPane.add(passwordField);
	}

}
