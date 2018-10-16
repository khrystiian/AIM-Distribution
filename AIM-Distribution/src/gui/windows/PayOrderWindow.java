package gui.windows;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import controller.OrderCtrl;
import model.Order;
import model.Payment;
import model.ProcessStatus;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class PayOrderWindow extends JFrame {

	private JPanel contentPane;
	private final JLabel lblPaidAmount = new JLabel("Paid amount:");
	private final JLabel lblLeftToPay = new JLabel("Left to pay:");
	private final JLabel lblPay = new JLabel("Amount:");
	private final JTextField txtPay = new JTextField();
	private final JButton btnPay = new JButton("Pay");

	public PayOrderWindow(int orderID) {
		txtPay.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if(txtPay.getText().equals("") && e.getKeyChar() == '0')
					e.consume();
				if(!Character.isDigit(c) && e.getKeyChar() != '.')
					e.consume();
				if(e.getKeyChar() == '.' && txtPay.getText().contains("."))
					e.consume();
				if(txtPay.getText().contains(".") && ((txtPay.getText().substring(txtPay.getText().lastIndexOf(".")+1)).length()>1))
		           e.consume();
			}
		});
		txtPay.setBounds(202, 124, 155, 25);
		txtPay.setColumns(10);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 520, 267);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		lblPaidAmount.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblPaidAmount.setBounds(88, 32, 269, 25);

		contentPane.add(lblPaidAmount);
		lblLeftToPay.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblLeftToPay.setBounds(98, 68, 259, 25);

		contentPane.add(lblLeftToPay);
		lblPay.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblPay.setBounds(112, 124, 75, 25);

		contentPane.add(lblPay);

		contentPane.add(txtPay);
		btnPay.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				try {
					OrderCtrl controller = new OrderCtrl();
					double amount = Double.parseDouble(txtPay.getText());
					controller.payOrder(orderID, amount);
					JOptionPane.showMessageDialog(null, "The payment was accepted.");
					List<Payment> payments = controller.findPaymentsForOrder(orderID);
					Order order = controller.findOrder(orderID);

					double paid = 0;
					for (Payment p : payments)
						paid += p.amount;

					if (paid == order.getFinalPrice(0))
						controller.updateStatus(orderID, ProcessStatus.PAID);

					dispose();
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "Please input a number.");
					txtPay.setText("");
				}
			}

		});
		btnPay.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnPay.setBounds(213, 179, 89, 23);

		contentPane.add(btnPay);
	}
}
