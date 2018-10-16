package gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import controller.EmployeeCtrl;
import database.DBConnection;
import model.Employee;
import util.Encryption;
import util.Session;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class LoginWindow extends JFrame {

	private JPanel contentPane;
	private final JLabel lblName = new JLabel("Name");
	private final JTextField nameField = new JTextField();
	private final JLabel lblPassword = new JLabel("Password");
	private final JPasswordField passField = new JPasswordField();
	private final JButton btnLogin = new JButton("Login");
	private final JLabel lblError = new JLabel("error");
	private String capslock = "0";
	private final JLabel lblUserCaps = new JLabel("");
	private final JLabel lblPassCaps = new JLabel("");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new LoginWindow();
			}

		});
	}

	/**
	 * Create the frame.
	 */
	public LoginWindow() {
		setTitle("AIM Distribution");
		setSize(new Dimension(679, 325));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblName.setBounds(211, 92, 48, 30);
		contentPane.add(lblName);

		lblUserCaps.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblUserCaps.setBounds(394, 115, 123, 23);

		contentPane.add(lblUserCaps);
		lblPassCaps.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPassCaps.setBounds(394, 177, 123, 23);

		contentPane.add(lblPassCaps);

		nameField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyCode() == KeyEvent.VK_ESCAPE) {
					setVisible(false);
				} else if (Toolkit.getDefaultToolkit().getLockingKeyState(KeyEvent.VK_CAPS_LOCK)) {
					lblUserCaps.setText("Caps Lock is On");
					capslock = "1";
				} else {
					lblUserCaps.setText("");
					capslock = "0";
				}
			}
		});

		nameField.setFont(new Font("Tahoma", Font.PLAIN, 16));
		nameField.setBounds(301, 92, 144, 30);
		nameField.setColumns(10);
		contentPane.add(nameField);

		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblPassword.setBounds(211, 155, 72, 30);
		contentPane.add(lblPassword);

		getRootPane().setDefaultButton(btnLogin);
		passField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {

				if (Toolkit.getDefaultToolkit().getLockingKeyState(KeyEvent.VK_CAPS_LOCK)) {
					lblPassCaps.setText("Caps Lock is On");
					capslock = "1";
				} else {
					lblPassCaps.setText("");
					capslock = "0";
				}
			}
		});

		passField.setFont(new Font("Tahoma", Font.PLAIN, 16));
		passField.setColumns(10);
		passField.setBounds(301, 155, 144, 30);
		contentPane.add(passField);
		this.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
					System.out.println("Hello");
				}
			}
		});

		btnLogin.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try {
					lblError.setVisible(false);
					Employee employee = new EmployeeCtrl().findEmployee(nameField.getText());

					char[] empPass = employee.getEncryptedPassword().toCharArray();
					char[] inputPass = Encryption.encrypt(passField.getPassword());
					

					if (Encryption.areEqual(empPass, inputPass)) {
						passField.setText("");
						Session.employee = employee;
						new MainWindow();
						dispose();
					} else {
						lblError.setVisible(true);
						lblError.setText("Wrong password.");
					}
				} catch (NullPointerException e1) {
					lblError.setVisible(true);
					lblError.setText("This employee was not found.");
					e1.printStackTrace();
				}
			}

		});
		btnLogin.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnLogin.setBounds(290, 239, 89, 23);
		contentPane.add(btnLogin);
		lblError.setHorizontalAlignment(SwingConstants.CENTER);
		lblError.setForeground(Color.RED);

		lblError.setBounds(10, 34, 643, 23);
		lblError.setVisible(false);
		contentPane.add(lblError);

	}
}
