package database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.Customer;
import model.OrderProduct;
import model.Product;

/**
 * The connection of the OrderProduct class with SQL database.
 * 
 * @author Ciobanu Andrei, Ioana Ganescu and Oprea Mircea.
 * @version 10.05.2016
 */
public class OrderProductDB {

	public Connection con;
	
	public OrderProductDB() {
		con = DBConnection.getConnection();
	}

	/**
	 * The method creates a statement to select the orders from OrderProducts
	 * table in the database.
	 * 
	 * @return An object of the type PreparedStatement.
	 * @throws SQLException
	 *             if errors occur when creating the statement.
	 */
	private PreparedStatement selectStatement(boolean all) throws SQLException {
		if (all)
			return con.prepareStatement("select * from OrderProducts;");
		return con.prepareStatement("select * from OrderProducts where ord_id = ?;");
	}

	/**
	 * The method creates a statement to delete an order product from the
	 * database.
	 * 
	 * @return An object of the PreparedStatement.
	 * @throws SQLException
	 *             if errors occur when creating the statement.
	 */
	private PreparedStatement deleteStatement() throws SQLException {
		return con.prepareStatement("DELETE FROM OrderProducts WHERE ord_id = ?;");
	}

	/**
	 * The method creates a statement to insert an order product in the
	 * database.
	 * 
	 * @return An object of the PreparedStatement.
	 * @throws SQLException
	 *             if errors occur when creating the statement.
	 */
	private PreparedStatement insertStatement() throws SQLException {
		return con.prepareStatement("insert into OrderProducts (ord_id, quantity, barcode, price) values (?, ?, ?, ?);");
	}

	/**
	 * The method creates a find order product statement in the database.
	 * 
	 * @param orderID
	 *            An Integer for the id to find the order.
	 * @return a list of the orders found.
	 */
	public List<OrderProduct> select(int orderID) {
		List<OrderProduct> products = new ArrayList<OrderProduct>();
		try {
			PreparedStatement select = selectStatement(false);
			select.setInt(1, orderID);

			ResultSet result = select.executeQuery();

			while (result.next()) {
				int quantity = result.getInt("quantity");
				double price = result.getDouble("price");
				String barcode = result.getString("barcode");
				Product prod = new ProductDB().findProduct(barcode);
				products.add(new OrderProduct(prod, quantity));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return products;
	}

	/**
	 * The method creates an insert statement in the database.
	 * 
	 * @param id
	 *            An Integer for the order product.
	 * @param op
	 *            An object of the type OrderProduct that will be insered.
	 */
	public void insertOrderProduct(int id, OrderProduct op) {
		try {
			PreparedStatement insert = insertStatement();
			insert.setInt(1, id);
			insert.setInt(2, op.quantity);
			insert.setString(3, op.product.barcode);
			insert.setDouble(4, op.price);

			insert.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create a delete statement in the database.
	 * 
	 * @param id
	 *            An Integer for the order product to be found.
	 */
	public void delete(int id) {
		try {
			PreparedStatement ps = deleteStatement();
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
