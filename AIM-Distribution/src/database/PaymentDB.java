package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.Payment;

/**
 * The connection of the Payment class with SQL database.
 * 
 * @author Ciobanu Andrei, Ioana Ganescu and Oprea Mircea.
 * @version 10.05.2016
 */
public class PaymentDB {

	private Connection connection;

	/**
	 * Connect to the database.
	 */
	public PaymentDB() {
		connection = DBConnection.getConnection();
	}

	private PreparedStatement selectStatement(boolean all) throws SQLException {
		if (all)
			return connection.prepareStatement("SELECT * FROM Payments;");
		return connection.prepareStatement("SELECT * FROM Payments WHERE ID = ?;");
	}

	/**
	 * The select statement in the database Payments table.
	 * 
	 * @return An Object of the type PreparedStatement.
	 * @throws SQLException
	 *             If the payment can not be found.
	 */
	private PreparedStatement selectByOrderStatement() throws SQLException {
		return connection.prepareStatement("SELECT * FROM Payments WHERE ord_id = ?;");
	}

	private PreparedStatement insertStatement() throws SQLException {
		return connection.prepareStatement("INSERT INTO Payments (ord_id, amount, pay_date) VALUES (?,?,?);");
	}

	/**
	 * Find a payment in the database.
	 * 
	 * @param ID
	 *            An Integer for the payment's id.
	 * @return An Object of the type Payment, that will be found.
	 */
	public Payment selectPaymentByID(int ID) {
		try {
			PreparedStatement select = selectStatement(false);
			select.setInt(1, ID);

			ResultSet result = select.executeQuery();

			if (result.next()) {
				int orderID = result.getInt("ord_id");
				double amount = result.getDouble("amount");
				Date date = result.getDate("pay_date");

				return new Payment(orderID, amount, date);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Show all the payments for a from the database.
	 * 
	 * @param orderID
	 *            An Integer for the order's id.
	 * @return A list with all the payments.
	 */
	public List<Payment> selectPaymentsByOrder(int orderID) {
		List<Payment> payments = new ArrayList<Payment>();

		try {
			PreparedStatement select = selectByOrderStatement();

			ResultSet result = select.executeQuery();

			while (result.next()) {
				double amount = result.getDouble("amount");
				Date date = result.getDate("pay_date");

				payments.add(new Payment(orderID, amount, date));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return payments;
	}

	public List<Payment> selectAllPayments() {
		List<Payment> payments = new ArrayList<Payment>();

		try {
			PreparedStatement select = selectStatement(true);

			ResultSet result = select.executeQuery();

			while (result.next()) {
				int orderID = result.getInt("ord_id");
				double amount = result.getDouble("amount");
				Date date = result.getDate("pay_date");

				payments.add(new Payment(orderID, amount, date));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return payments;
	}

	/**
	 * Insert a payment in the database.
	 * 
	 * @param payment
	 *            An Object of the type Payment, that will be inserted.
	 */
	public void insertPayment(Payment payment) {

		try {
			PreparedStatement insert = insertStatement();

			insert.setInt(1, payment.orderID);
			insert.setDouble(2, payment.amount);
			insert.setDate(3, (java.sql.Date) payment.paymentDate);

			insert.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
