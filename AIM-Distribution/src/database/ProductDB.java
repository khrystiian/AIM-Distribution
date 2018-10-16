package database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.Product;

/**
 * The connection of the Product class with SQL database.
 * 
 * @author Ciobanu Andrei, Ioana Ganescu and Oprea Mircea.
 * @version 10.05.2016
 */

public class ProductDB {

	// create an object of the type Connection to connect to the database.
	private Connection con;

	public ProductDB() {
		con = DBConnection.getConnection();
	}

	/**
	 * The method creates a statement in the database in the Products table to
	 * return all the products with a given barcode.
	 * 
	 * @return an object of the type PreparedStatement.
	 * @throws SQLException
	 *             if errors occur in the SQL database.
	 */
	private PreparedStatement selectStatement(boolean all) throws SQLException {
		if (all)
			return con.prepareStatement("select * from Products;");
		return con.prepareStatement("select * from Products where barcode = ?;");
	}

	private PreparedStatement selectByNameStatement() throws SQLException {
		return con.prepareStatement("select * from Products where name = ?;");
	}

	/**
	 * The method creates a statement in the database in the Products table to
	 * return all the products.
	 * 
	 * @return an object of the type PreparedStatement.
	 * @throws SQLException
	 *             if errors occur in the SQL database.
	 */
	private PreparedStatement insertStatement() throws SQLException {
		return con.prepareStatement("insert into Products (barcode, name, price, quantity, min_stock, description) values (?, ?, ?, ?, ?, ?);");
	}

	/**
	 * The method creates a statement to delete from Products table.
	 * 
	 * @return an object of the type PreparedStatement.
	 * @throws SQLException
	 *             if errors occur in the SQL database.
	 */
	private PreparedStatement deleteStatement() throws SQLException {
		return con.prepareStatement("delete from Products where barcode = ?;");
	}

	/**
	 * The method creates a statement in the database for the update product
	 * method.
	 * 
	 * @param columnNames
	 *            A String for the column names that will be updated.
	 * @return An object of the type PreparedStatement.
	 * @throws SQLException
	 *             if errors occur in the SQL database.
	 */
	private PreparedStatement updateStatement(List<String> columns) throws SQLException {
		PreparedStatement ps = null;
		String query = "UPDATE Products SET ";
		StringBuilder sb = new StringBuilder(query);
		for (int i = 0; i < columns.size(); i++) {
			if (!columns.get(i).equals(null))
				sb.append(columns.get(i));
			if (i != columns.size() - 1)
				sb.append("= ?, ");
			else
				sb.append("= ?");
		}
		sb.append(" where barcode = ?;");
		query = sb.toString();
		ps = con.prepareStatement(query);
		return ps;
	}

	/**
	 * The method creates a statement to find a product using a given barcode.
	 * 
	 * @param barcode
	 *            An String for the product's barcode to be found.
	 * @return an object of the type Product.
	 * @throws SQLException
	 *             if errors occur in the SQL database.
	 */
	public Product findProduct(String barcode) throws SQLException {

		PreparedStatement ps = selectStatement(false);
		ps.setString(1, barcode);
		ResultSet result = ps.executeQuery();

		if (result.next()) {
			String name = result.getString("name");
			String description = result.getString("description");
			int quantity = result.getInt("quantity");
			int minStock = result.getInt("min_stock");
			double price = result.getDouble("price");
			return new Product(barcode, name, price, quantity, minStock, description);
		}
		return null;
	}

	public Product findProductByName(String prodName) {
		try {
			PreparedStatement ps = selectByNameStatement();
			ps.setString(1, prodName);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				String barcode = rs.getString("barcode");
				double price = rs.getDouble("price");
				int quant = rs.getInt("quantity");
				int minStock = rs.getInt("min_stock");
				String descrip = rs.getString("description");

				return new Product(barcode, prodName, price, quant, minStock, descrip);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * The method creates a statement to return a list with all the products
	 * from the database.
	 * 
	 * @return A list with the products.
	 * @throws SQLException
	 *             if errors occur in the SQL database.
	 */
	public List<Product> allProducts() {
		List<Product> list = new ArrayList<Product>();
		try {
			PreparedStatement ps = selectStatement(true);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				String barcode = rs.getString("barcode");
				String name = rs.getString("name");
				double price = rs.getDouble("price");
				int quantity = rs.getInt("quantity");
				int minStock = rs.getInt("min_stock");
				String description = rs.getString("description");
				// add the product in the list
				list.add(new Product(barcode, name, price, quantity, minStock, description));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * The method creates a statement to insert a product in the Products table
	 * in the database.
	 * 
	 * @param prod
	 *            An object of the type Product that will be insered in the
	 *            Products table.
	 * @throws SQLException
	 *             if errors occur in the SQL database.
	 */
	public void insertProduct(Product prod) {

		try {
			PreparedStatement ps = insertStatement();

			ps.setString(1, prod.barcode);
			ps.setString(2, prod.name);
			ps.setDouble(3, prod.price);
			ps.setInt(4, prod.quantity);
			ps.setInt(5, prod.minStock);
			ps.setString(6, prod.description);

			ps.executeUpdate();
		} catch (SQLException sql) {
			System.out.println("insert product error! " + sql.getMessage());
		}
	}

	/**
	 * The method creates a statement to delete a product from database.
	 * 
	 * @param barcode
	 *            An string for the product's given barcode.
	 */
	public void deleteProduct(String barcode) {
		try {
			PreparedStatement ps = deleteStatement();
			ps.setString(1, barcode);

			ps.executeUpdate();
		} catch (SQLException sql) {
			System.out.println("delete Product error! " + sql.getMessage());
		}
	}

	/**
	 * The method will update in the database the product in the Products table.
	 * 
	 * @param prod
	 *            An object of the type product for the product that will be
	 *            updated.
	 * @param columnNames
	 *            A String for the column names that will be updated.
	 * @throws SQLException
	 *             if errors occur in the SQL database.
	 */
	public void updateProduct(Product prod) {
		try {
			Product proddb = findProduct(prod.barcode);
			List<String> columns = new ArrayList<String>();

			if (!prod.name.equals(proddb.name))
				columns.add("name");
			if (prod.price != proddb.price)
				columns.add("price");
			if (prod.quantity != proddb.quantity)
				columns.add("quantity");
			if (prod.minStock != proddb.minStock)
				columns.add("min_stock");
			if (!prod.description.equals(proddb.description))
				columns.add("description");

			PreparedStatement ps = updateStatement(columns);

			int counter = 1;
			for (String s : columns) {

				if (s.equals("name")) {
					ps.setString(counter++, prod.name);
					continue;
				}
				if (s.equals("quantity")) {
					ps.setInt(counter++, prod.quantity);
					continue;
				}
				if (s.equals("price")) {
					ps.setDouble(counter++, prod.price);
					continue;
				}
				if (s.equals("min_stock")) {
					ps.setInt(counter++, prod.minStock);
					continue;
				}
				if (s.equals("description")) {
					ps.setString(counter++, prod.description);
					continue;
				}

			}
			ps.setString(counter++, prod.barcode);
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();

		}
	}
}
