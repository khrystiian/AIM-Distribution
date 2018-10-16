package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import model.Customer;
import model.DeliveryPoint;
import model.Employee;
import model.Order;
import model.OrderProduct;
import model.ProcessStatus;
import util.EnumMaps;

/**
 * The connection of the Order class with SQL database.
 * 
 * @author Ciobanu Andrei, Ioana Ganescu and Oprea Mircea.
 * @version 10.05.2016
 */
public class OrderDB {

	private Connection connection;

	/**
	 * Connect with the SQL database.
	 */
	public OrderDB() {
		connection = DBConnection.getConnection();
	}

	private PreparedStatement selectStatement(boolean all) throws SQLException {
		if (all)
			return connection.prepareStatement("SELECT * FROM Orders;");
		return connection.prepareStatement("SELECT * FROM Orders where order_id = ?;");
	}

	/**
	 * The insert statement for the query in database.
	 * 
	 * @return An Object of the type PreparedStatement.
	 * @throws SQLException
	 *             if the insert fails.
	 */
	private PreparedStatement insertStatement() throws SQLException {
		return connection.prepareStatement(
				"INSERT INTO Orders (total_amount, order_date, order_time, order_status, emp_id, point_id) VALUES (?, ?, ?, ?, ?, ?);");
	}

	/**
	 * The update status statement for the query in database.
	 * 
	 * @return An Object of the type PreparedStatement.
	 * @throws SQLException
	 *             if the update fails.
	 */
	private PreparedStatement updateStatement() throws SQLException {
		return connection.prepareStatement("UPDATE Orders SET order_status = ? WHERE order_id = ?;");
	}

	/**
	 * The delete statement for the query in database.
	 * 
	 * @return An Object of the type PreparedStatement.
	 * @throws SQLException
	 *             if the delete fails.
	 */
	private PreparedStatement deleteStatement() throws SQLException {
		return connection.prepareStatement("DELETE FROM Orders where order_id = ?;");
	}

	private PreparedStatement selectOrderByStatus() throws SQLException {
		return connection.prepareStatement("SELECT * FROM Orders WHERE order_status = ?");
	}

	private PreparedStatement getMaxStatement() throws SQLException {
		return connection.prepareStatement("SELECT MAX(order_id) AS max_ord_id FROM Orders;");
	}

	/**
	 * Select all the orders with a specific status.
	 * 
	 * @param status
	 *            A String for the status from the database.
	 * @return A list with all the orders.
	 */
	public List<Order> selectAllOrdered(String status) {
		List<Order> orders = new ArrayList<Order>();
		try {
			PreparedStatement select = selectOrderByStatus();
			select.setString(1, status);
			ResultSet result = select.executeQuery();

			while (result.next()) {
				int orderID = result.getInt("order_id");
				double totalAmount = result.getDouble("total_amount");
				Date date = result.getDate("order_date");
				Time time = result.getTime("order_time");
				int pointID = result.getInt("point_id");
				int employeeID = result.getInt("emp_id");

				DeliveryPoint dp = new DeliveryPointDB().selectPoint(pointID);
				Employee employee = new EmployeeDB().selectEmployee(employeeID);

				List<OrderProduct> products = new OrderProductDB().select(orderID);
				Order order = new Order(totalAmount, date, EnumMaps.getProcessStatus(status), employee, orderID, dp);
				order.products.addAll(products);
				orders.add(order);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return orders;
	}

	/**
	 * Select an order from database.
	 * 
	 * @param orderID
	 *            An Integer for the order's id.
	 * @return An Object of the type Order for the order found.
	 */
	public Order selectOrder(int orderID) {
		try {
			PreparedStatement select = selectStatement(false);
			select.setInt(1, orderID);

			ResultSet result = select.executeQuery();

			if (result.next()) {
				double totalAmount = result.getDouble("total_amount");
				Date date = result.getDate("order_date");
				Time time = result.getTime("order_time");
				String status = result.getString("order_status");
				int employeeID = result.getInt("emp_id");
				int pointID = result.getInt("point_id");

				Employee employee = new EmployeeDB().selectEmployee(employeeID);
				DeliveryPoint dp = new DeliveryPointDB().selectPoint(pointID);

				List<OrderProduct> products = new OrderProductDB().select(orderID);
				Order order = new Order(totalAmount, date, EnumMaps.getProcessStatus(status), employee, orderID, dp);
				order.products.addAll(products);
				return order;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Show all the orders from database.
	 * 
	 * @return A list of the type Order with all the orders.
	 */
	public List<Order> selectAllOrders() {
		List<Order> orders = new ArrayList<Order>();
		try {
			PreparedStatement select = selectStatement(true);

			ResultSet result = select.executeQuery();

			while (result.next()) {
				int orderID = result.getInt("order_id");
				double totalAmount = result.getDouble("total_amount");
				Date date = result.getDate("order_date");
				Time time = result.getTime("order_time");
				String status = result.getString("order_status");
				int pointID = result.getInt("point_id");
				int employeeID = result.getInt("emp_id");

				DeliveryPoint dp = new DeliveryPointDB().selectPoint(pointID);
				Employee employee = new EmployeeDB().selectEmployee(employeeID);

				List<OrderProduct> products = new OrderProductDB().select(orderID);
				Order order = new Order(totalAmount, date, EnumMaps.getProcessStatus(status), employee, orderID, dp);
				order.products.addAll(products);
				orders.add(order);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return orders;
	}

	private int getMaxID() {
		try {
			PreparedStatement max = getMaxStatement();

			ResultSet results = max.executeQuery();

			if (results.next())
				return results.getInt("max_ord_id");

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * Insert an order in the database.
	 * 
	 * @param order
	 *            An Object of the type Order that will be inserted.
	 */
	public void insertOrder(Order order) {
		try {
			connection.setAutoCommit(false);
			PreparedStatement insert = insertStatement();

			System.out.println(order.getFinalPrice(0));

			insert.setDouble(1, order.getFinalPrice(0));
			insert.setDate(2, order.processDate);
			insert.setTime(3, new Time(System.currentTimeMillis()));
			insert.setString(4, order.status.toString());
			insert.setInt(5, order.employee.employeeID);
			insert.setInt(6, order.point.id);
			insert.executeUpdate();

			order.orderID = getMaxID();

			for (OrderProduct product : order.products)
				new OrderProductDB().insertOrderProduct(order.orderID, product);

			connection.commit();
			connection.setAutoCommit(true);

		} catch (SQLException e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * Update an order's status in the database.
	 * 
	 * @param orderID
	 *            An Integer for the order's id found to have the status
	 *            updated.
	 * @param status
	 *            An Object of the type ProcessStatus for the order's status.
	 */
	public void updateOrder(int orderID, ProcessStatus status) {
		try {
			PreparedStatement update = updateStatement();
			update.setString(1, status.toString());
			update.setInt(2, orderID);

			update.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Delete an order.
	 * 
	 * @param orderID
	 *            An Integer for the order's id that will be deleted.
	 */
	public void deleteOrder(int orderID) {
		try {
			connection.setAutoCommit(false);
			PreparedStatement delete = deleteStatement();

			delete.setInt(1, orderID);
			delete.executeUpdate();

			new OrderProductDB().delete(orderID);

			connection.commit();
			connection.setAutoCommit(true);

		} catch (SQLException e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}

	}

}
