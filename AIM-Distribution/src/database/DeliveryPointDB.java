package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.DeliveryPoint;

/**
 * The connection of the DeliveryPoints class with SQL database.
 * 
 * @author Ciobanu Andrei, Ioana Ganescu and Oprea Mircea.
 * @version 10.05.2016
 */
public class DeliveryPointDB {

	private Connection con;

	/**
	 * The constructor of the class. Call the method that connect to the
	 * database.
	 */
	public DeliveryPointDB() {
		con = DBConnection.getConnection();
	}

	/**
	 * Create the statement in the database in the DeliveryPoints table.
	 * 
	 * @return An Object of the type PreparedStatement.
	 * @throws SQLException
	 *             If the delivery point does not exist.
	 */
	private PreparedStatement selectStatement(boolean all) throws SQLException {
		if (all)
			return con.prepareStatement("SELECT * FROM DeliveryPoints");
		return con.prepareStatement("SELECT * FROM DeliveryPoints WHERE id = ?;");
	}

	private PreparedStatement selectByID() throws SQLException {
		return con.prepareStatement("SELECT * FROM DeliveryPoints WHERE id = ?;");
	}

	/**
	 * Create the insert statement in the database.
	 * 
	 * @return An Object of the type PreparedStatement.
	 * @throws SQLException
	 */
	private PreparedStatement insertStatement() throws SQLException {
		return con.prepareStatement(
				"INSERT INTO DeliveryPoints(point_address, zip, city, region, country, cust_id) VALUES (?, ?, ?, ?, ?, ?);");
	}

	private PreparedStatement deleteStatement() throws SQLException {
		return con.prepareStatement("DELETE FROM DeliveryPoints WHERE id = ?;");
	}

	private PreparedStatement selectByCityStatement() throws SQLException {
		return con.prepareStatement("SELECT * FROM DeliveryPoints WHERE city = ?");
	}

	private PreparedStatement selectByRegionStatement() throws SQLException {
		return con.prepareStatement("SELECT * FROM DeliveryPoints WHERE region = ?");
	}

	private PreparedStatement selectByCustomerStatement() throws SQLException {
		return con.prepareStatement("SELECT * FROM DeliveryPoints WHERE cust_id = ?");
	}

	private PreparedStatement selectByAddressStatement() throws SQLException {
		return con.prepareStatement("SELECT * FROM DeliveryPoints WHERE point_address = ? AND cust_id = ?");
	}

	/**
	 * The update statement to update a delivery point in the database.
	 * 
	 * @param columns
	 *            A list of the type String for the columns that will be
	 *            updated.
	 * @return An Object of the type PreparedStatement.
	 * @throws SQLException
	 *             If the delivery point does not exist in the database.
	 */
	private PreparedStatement updateStatement(List<String> columns) throws SQLException {
		PreparedStatement result = null;

		String update = "UPDATE DeliveryPoints SET ";

		StringBuilder sb = new StringBuilder(update);
		for (int i = 0; i < columns.size(); i++) {
			if (!columns.get(i).equals(null))
				sb.append(columns.get(i));
			if (i != columns.size() - 1)
				sb.append("= ?, ");
			else
				sb.append("= ?");
		}
		sb.append(" WHERE id = ?;");

		update = sb.toString();

		result = con.prepareStatement(update);
		return result;
	}

	private PreparedStatement selectCustomerByDeliveryPointStatement() throws SQLException {
		return con.prepareStatement("SELECT cust_id FROM DeliveryPoints WHERE id = ?");
	}

	/**
	 * Select a customer by delivery point.
	 * 
	 * @param pointID
	 *            An Integer for the delivery point's id.
	 * @return An Integer for the customer's id.
	 */
	public int selectCustomerByDeliveryPoint(int pointID) {
		try {
			PreparedStatement select = selectCustomerByDeliveryPointStatement();
			select.setInt(1, pointID);
			ResultSet res = select.executeQuery();

			if (res.next()) {
				int custID = res.getInt("cust_id");

				return custID;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * Select a delivery point using the customer address.
	 * 
	 * @param address
	 *            A String for the customer's address.
	 * @param custID
	 *            An Integer for the customer's id.
	 * @return An Object of the type Delivery point.
	 */
	public DeliveryPoint selectPointByAddress(String address, int custID) {
		try {
			PreparedStatement select = selectByAddressStatement();
			select.setString(1, address);
			select.setInt(2, custID);
			ResultSet res = select.executeQuery();

			if (res.next()) {
				int id = res.getInt("id");
				String zip = res.getString("zip");
				String city = res.getString("city");
				String region = res.getString("region");
				String country = res.getString("country");

				DeliveryPoint dp = new DeliveryPoint(new CustomerDB().findCustomerById(custID), address, zip, city,
						region, country);
				dp.id = id;
				return dp;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public List<DeliveryPoint> selectPointsByCustomer(int custID) {
		List<DeliveryPoint> dps = new ArrayList<DeliveryPoint>();
		try {
			PreparedStatement select = selectByCustomerStatement();
			select.setInt(1, custID);
			ResultSet res = select.executeQuery();

			while (res.next()) {
				int id = res.getInt("id");
				String address = res.getString("point_address");
				String zip = res.getString("zip");
				String region = res.getString("region");
				String country = res.getString("country");
				String city = res.getString("city");

				DeliveryPoint dp = new DeliveryPoint(new CustomerDB().findCustomerById(custID), address, zip, city,
						region, country);
				dp.id = id;

				dps.add(dp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dps;
	}

	public List<DeliveryPoint> selectPointsByCity(String city) {
		List<DeliveryPoint> dps = new ArrayList<DeliveryPoint>();
		try {
			PreparedStatement select = selectByCityStatement();
			select.setString(1, city);
			ResultSet res = select.executeQuery();

			while (res.next()) {
				int id = res.getInt("id");
				String address = res.getString("point_address");
				String zip = res.getString("zip");
				String region = res.getString("region");
				String country = res.getString("country");
				int cust_id = res.getInt("cust_id");

				DeliveryPoint dp = new DeliveryPoint(new CustomerDB().findCustomerById(cust_id), address, zip, city,
						region, country);
				dp.id = id;

				dps.add(dp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dps;
	}

	public List<DeliveryPoint> selectPointsByRegion(String region) {
		List<DeliveryPoint> dps = new ArrayList<DeliveryPoint>();
		try {
			PreparedStatement select = selectByRegionStatement();
			select.setString(1, region);
			ResultSet res = select.executeQuery();

			while (res.next()) {
				int id = res.getInt("id");
				String address = res.getString("point_address");
				String zip = res.getString("zip");
				String city = res.getString("city");
				String country = res.getString("country");
				int cust_id = res.getInt("cust_id");

				DeliveryPoint dp = new DeliveryPoint(new CustomerDB().findCustomerById(cust_id), address, zip, city,
						region, country);
				dp.id = id;

				dps.add(dp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dps;
	}

	public DeliveryPoint selectPoint(int id) {
		try {
			PreparedStatement select = selectByID();
			select.setInt(1, id);
			ResultSet res = select.executeQuery();

			if (res.next()) {
				String address = res.getString("point_address");
				String zip = res.getString("zip");
				String city = res.getString("city");
				String region = res.getString("region");
				String country = res.getString("country");
				int cust_id = res.getInt("cust_id");

				DeliveryPoint dp = new DeliveryPoint(new CustomerDB().findCustomerById(cust_id), address, zip, city,
						region, country);
				dp.id = id;
				return dp;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public List<DeliveryPoint> getAllDeliveryPoints() {
		List<DeliveryPoint> delPoints = new ArrayList<DeliveryPoint>();
		try {
			PreparedStatement select = selectStatement(true);
			ResultSet res = select.executeQuery();

			while (res.next()) {
				int id = res.getInt("id");
				String address = res.getString("point_address");
				String zip = res.getString("zip");
				String city = res.getString("city");
				String region = res.getString("region");
				String country = res.getString("country");
				int cust_id = res.getInt("cust_id");

				DeliveryPoint delPnt = new DeliveryPoint(new CustomerDB().findCustomerById(cust_id), address, zip, city,
						region, country);
				delPnt.id = id;

				delPoints.add(delPnt);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return delPoints;
	}

	/**
	 * Delete a delivery point from database.
	 * 
	 * @param id
	 *            An Integer for the delivery's id.
	 */
	public void deleteDeliveryRoute(int id) {
		try {
			PreparedStatement delete = deleteStatement();
			delete.setInt(1, id);

			delete.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Insert a delivery point in the database.
	 * 
	 * @param dP
	 *            An Object of the type delivery point that will be inserted in
	 *            the database table.
	 */
	public void insertDeliveryPoint(DeliveryPoint dP) {
		try {
			PreparedStatement insert = insertStatement();

			insert.setString(1, dP.address);
			insert.setString(2, dP.zipcode);
			insert.setString(3, dP.city);
			insert.setString(4, dP.region);
			insert.setString(5, dP.country);
			insert.setInt(6, new CustomerDB().findCustomerByName(dP.customer.name).id);

			insert.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Update a delivery point.
	 * 
	 * @param dp
	 *            An Object of the type DeliveryPoint that will be updated in
	 *            the database.
	 */
	public void updateDeliveryPoint(DeliveryPoint dp) {
		try {
			DeliveryPoint dbDP = selectPoint(dp.id);
			List<String> columns = new ArrayList<String>();

			if (!dp.address.equals(dbDP.address))
				columns.add("point_address");
			if (!dp.zipcode.equals(dbDP.zipcode))
				columns.add("zip");
			if (!dp.city.equals(dbDP.city))
				columns.add("city");
			if (!dp.region.equals(dbDP.region))
				columns.add("region");
			if (!dp.country.equals(dbDP.country))
				columns.add("country");
			if (dp.customer.id != dbDP.customer.id)
				columns.add("cust_id");

			PreparedStatement update = updateStatement(columns);

			int counter = 1;
			for (String s : columns) {
				if (s.equals("point_address")) {
					update.setString(counter++, dp.address);
					continue;
				}

				if (s.equals("zip")) {
					update.setString(counter++, dp.zipcode);
					continue;
				}

				if (s.equals("city")) {
					update.setString(counter++, dp.city);
					continue;
				}

				if (s.equals("region")) {
					update.setString(counter++, dp.region);
					continue;
				}

				if (s.equals("country")) {
					update.setString(counter++, dp.country);
					continue;
				}

				if (s.equals("cust_id")) {
					update.setInt(counter++, dp.customer.id);
					continue;
				}
			}

			update.setInt(counter++, dp.id);

			update.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
