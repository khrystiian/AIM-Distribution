package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Customer;
import model.DeliveryPoint;

/**
 * The connection of the Customer class with SQL database.
 * 
 * @author Ciobanu Andrei, Ioana Ganescu and Oprea Mircea.
 * @version 10.05.2016
 */
public class CustomerDB {

	private Connection con;
	
	public CustomerDB() {
		con = DBConnection.getConnection();
	}

	/**
	 * Creates a statement in SQL database in the Customers table to find a
	 * customer with a given id.
	 * 
	 * @return an object of the type PreparedStatement
	 * @throws SQLException
	 *             if errors occur in the database.
	 */
	private PreparedStatement selectStatementByID() throws SQLException {
		PreparedStatement ps = null;
		String query = "select * from Customers where cust_id = ?;";
		ps = con.prepareStatement(query);
		return ps;
	}

	/**
	 * Creates a statement in SQL database in the Customers table to find a
	 * customer with a given name.
	 * 
	 * @return an object of the type PreparedStatement
	 * @throws SQLException
	 *             if errors occur in the database.
	 */
	private PreparedStatement selectStatementByName() throws SQLException {
		PreparedStatement ps = null;
		String query = "select * from Customers where name = ?;";
		ps = con.prepareStatement(query);
		return ps;
	}

	private PreparedStatement insertStatement() throws SQLException {
		return con.prepareStatement("INSERT INTO Customers (name, phone_no, mail) VALUES (?,?,?);");
	}

	/**
	 * Creates a statement in SQL database in the Customers table to select all
	 * customers.
	 * 
	 * @return an object of the type PreparedStatement
	 * @throws SQLException
	 *             if errors occur in the database.
	 */
	private PreparedStatement selectAllCustomers() throws SQLException {
		PreparedStatement ps = null;
		String query = "select * from Customers;";
		ps = con.prepareStatement(query);
		return ps;
	}

	/**
	 * Creates a statement in SQL database in the Customers table to delete a
	 * customer with a given id.
	 * 
	 * @return an object of the type PreparedStatement
	 * @throws SQLException
	 *             if errors occur in the database.
	 */
	private PreparedStatement deleteStatement() throws SQLException {
		return con.prepareStatement("delete from Customers where cust_id = ?;");
	}

	/**
	 * Creates a statement in SQL database in the Customers table to update a
	 * customer.
	 * 
	 * @return an object of the type PreparedStatement
	 * @throws SQLException
	 *             if errors occur in the database.
	 */
	private PreparedStatement updateStatement(List<String> columns) throws SQLException {
		PreparedStatement ps = null;
		String query = " update Customers set ";
		StringBuilder sb = new StringBuilder(query);
		for (int i = 0; i < columns.size(); i++) {
			if (!columns.get(i).equals(null))
				sb.append(columns.get(i));
			if (i != columns.size() - 1)
				sb.append(" = ?, ");
			else
				sb.append(" = ?");
		}
		sb.append(" where cust_id = ?;");
		query = sb.toString();
		ps = con.prepareStatement(query);
		return ps;
	}

	/**
	 * Find a customer in database.
	 * 
	 * @param id
	 *            An integer for the customer that will be found.
	 * @return an object of the type Customer for the customer found.
	 * @throws SQLException
	 *             if errors occur in SQL database.
	 */
	public Customer findCustomerById(int id) {
		try {
			PreparedStatement ps = selectStatementByID();
			ps.setInt(1, id);
			ResultSet result = ps.executeQuery();

			String name, phone, email = null;

			if (result.next()) {
				name = result.getString("name");
				phone = result.getString("phone_no");
				email = result.getString("mail");
				return new Customer(id, phone, name, email);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Find a customer in the table Customers in database.
	 * 
	 * @param name
	 *            A String for the customer's name to be found.
	 * @return an object of the type Customer that represent the customer found.
	 * @throws SQLException
	 *             if errors occur in the SQL database.
	 */
	public Customer findCustomerByName(String name) {
		try {
			PreparedStatement ps = selectStatementByName();
			ps.setString(1, name);
			ResultSet result = ps.executeQuery();

			String phone, email = null;
			int id = 0;

			if (result.next()) {
				id = result.getInt("cust_id");
				phone = result.getString("phone_no");
				email = result.getString("mail");
				return new Customer(id, phone, name, email);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Show all customers.
	 * 
	 * @return a list with all the customers in the Customers table
	 * @throws SQLException
	 *             if errors occur in SQL database.
	 */
	public List<Customer> allCustomers() {
		List<Customer> list = new ArrayList<Customer>();
		try {
			PreparedStatement ps = null;
			ps = selectAllCustomers();
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("cust_id");
				String name = rs.getString("name");
				String phone = rs.getString("phone_no");
				String email = rs.getString("mail");
				list.add(new Customer(id, name, phone, email));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * Insert a customer into the Customers table.
	 * 
	 * @param cust
	 *            An object of the type Customer for the customer that will be
	 *            insered.
	 * @throws SQLException
	 *             if errors occur in the SQL database.
	 */
	public void insertCustomer(Customer cust) {

		try {
			con.setAutoCommit(false);
			PreparedStatement insert = insertStatement();

			insert.setString(1, cust.name);
			insert.setString(2, cust.phone);
			insert.setString(3, cust.email);

			insert.executeUpdate();

			for (DeliveryPoint dp : cust.points) {
				new DeliveryPointDB().insertDeliveryPoint(dp);
			}

			con.commit();
			con.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	/**
	 * Delete a customer found by id from the Customers table.
	 * 
	 * @param id
	 *            An integer for the customer's id to be found.
	 */
	public void deleteCustomer(int id) {
		try {
			PreparedStatement ps = deleteStatement();
			ps.setInt(1, id);

			ps.executeUpdate();
		} catch (SQLException sql) {
			System.out.println("Delete customers error! " + sql.getMessage());
		}
	}

	/**
	 * Update a customer in the Customers table.
	 * 
	 * @param cust
	 *            An object of the type Customer for the customer that will be
	 *            updated.
	 * @throws SQLException
	 *             if errors occur on the SQL database.
	 */
	public void updateCustomer(Customer cust) {
		try {
			Customer customer = findCustomerById(cust.id);
			List<String> columns = new ArrayList<String>();

			if (!cust.name.equals(customer.name))
				columns.add("name");
			if (!cust.phone.equals(customer.phone))
				columns.add("phone_no");
			if (!cust.email.equals(customer.email))
				columns.add("mail");

			PreparedStatement ps = updateStatement(columns);

			int counter = 1;
			for (String s : columns) {
				if (s.equals("name")) {
					ps.setString(counter++, cust.name);
					continue;
				}
				if (s.equals("phone_no")) {
					ps.setString(counter++, cust.phone);
					continue;
				}
				if (s.equals("mail")) {
					ps.setString(counter++, cust.email);
					continue;
				}
			}
			ps.setInt(counter++, cust.id);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();

		}
	}
}