package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import model.Delivery;

/**
 * The connection of the Delivery class with SQL database.
 * 
 * @author Ciobanu Andrei, Ioana Ganescu and Oprea Mircea.
 * @version 10.05.2016
 */
public class DeliveryDB {

	private Connection con;

	/**
	 * The constructor of the class. Call the method that connect to the
	 * database.
	 */
	public DeliveryDB() {
		con = DBConnection.getConnection();
	}

	/**
	 * Create the statement in the database in the Deliveries table.
	 * 
	 * @return An Object of the type PreparedStatement.
	 * @throws SQLException
	 *             If the delivery does not exist.
	 */
	private PreparedStatement selectStatement(boolean all) throws SQLException {
		if (all)
			return con.prepareStatement("SELECT * FROM Deliveries");
		return con.prepareStatement("SELECT * FROM Deliveries WHERE id = ?;");
	}

	private PreparedStatement selectByRouteStatement() throws SQLException {
		return con.prepareStatement("SELECT * FROM Deliveries WHERE route_id = ?;");
	}

	private PreparedStatement selectByIDStatement() throws SQLException {
		return con.prepareStatement("SELECT * FROM Deliveries WHERE id = ?;");
	}

	/**
	 * Create the insert statement in the database.
	 * 
	 * @return An Object of the type PreparedStatement.
	 * @throws SQLException
	 */
	private PreparedStatement insertStatement() throws SQLException {
		return con.prepareStatement(
				"INSERT INTO Deliveries(route_id, point_id, order_id, delivery_date) VALUES (?, ?, ?, ?);");
	}

	private PreparedStatement deleteStatement() throws SQLException {
		return con.prepareStatement("DELETE FROM Deliveries WHERE id = ?;");
	}

	private PreparedStatement updateStatement(List<String> columns) throws SQLException {
		PreparedStatement result = null;

		String update = "UPDATE Deliveries SET";

		StringBuilder sb = new StringBuilder(update);
		for (int i = 0; i < columns.size(); i++) {
			if (!columns.get(i).equals(null))
				sb.append(columns.get(i));
			if (i == columns.size() - 1)
				sb.append("= ?,");
			else
				sb.append("= ?");
		}

		sb.append(" WHERE id = ?;");

		update = sb.toString();

		result = con.prepareStatement(update);
		return result;
	}

	public Delivery selectDelivery(int id) {
		try {
			PreparedStatement select = selectByIDStatement();
			select.setInt(1, id);
			ResultSet res = select.executeQuery();

			if (res.next()) {
				int ordID = res.getInt("order_id");
				int routeID = res.getInt("route_id");
				Date deliveryDate = res.getDate("delivery_date");

				Delivery del = new Delivery(new OrderDB().selectOrder(ordID), new RouteDB().selectRoute(routeID));
				del.id = id;
				del.deliveryDate = deliveryDate;

				return del;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Delete a delivery from database.
	 * 
	 * @param id
	 *            An Integer for the delivery's id.
	 */
	public void deleteDelivery(int id) {
		try {
			PreparedStatement delete = deleteStatement();
			delete.setInt(1, id);

			delete.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Insert a delivery in the database.
	 * 
	 * @param del
	 *            An Object of the type Delivery to be inserted in the database.
	 */
	public void insertDelivery(Delivery del) {
		try {
			PreparedStatement insert = insertStatement();

			insert.setInt(1, del.route.routeID);
			insert.setInt(2, del.ord.point.id);
			insert.setInt(3, del.ord.orderID);
			insert.setDate(4, del.deliveryDate);

			insert.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Get all the deliveries from database.
	 * 
	 * @return A list with all the deliveries
	 */
	public List<Delivery> getAllDeliveries() {
		List<Delivery> deliveries = new ArrayList<Delivery>();
		try {
			PreparedStatement select = selectStatement(true);
			ResultSet res = select.executeQuery();

			while (res.next()) {
				int id = res.getInt("id");
				int routeID = res.getInt("route_id");
				int orderID = res.getInt("order_id");
				int pointID = res.getInt("point_id");
				Date deliveryDate = res.getDate("delivery_date");

				Delivery del = new Delivery(new OrderDB().selectOrder(orderID), new RouteDB().selectRoute(routeID));
				del.id = id;
				del.deliveryDate = deliveryDate;

				deliveries.add(del);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return deliveries;
	}

	public List<Delivery> getRouteDeliveries(int routeID) {
		List<Delivery> deliveries = new ArrayList<Delivery>();
		try {
			PreparedStatement select = selectByRouteStatement();
			select.setInt(1, routeID);
			ResultSet res = select.executeQuery();

			while (res.next()) {
				int id = res.getInt("id");
				int orderID = res.getInt("order_id");
				int pointID = res.getInt("point_id");
				Date deliveryDate = res.getDate("delivery_date");

				Delivery del = new Delivery(new OrderDB().selectOrder(orderID), new RouteDB().selectRoute(routeID));
				del.id = id;
				del.deliveryDate = deliveryDate;

				deliveries.add(del);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return deliveries;
	}

	/**
	 * Updates the delivery in database.
	 * 
	 * @param del
	 *            An Object of the type Delivery that will be updated.
	 */
	public void updateDelivery(Delivery del) {
		try {
			Delivery dbDel = selectDelivery(del.id);
			List<String> columns = new ArrayList<String>();

			if (del.route.routeID != dbDel.route.routeID)
				columns.add("route_id");
			if (del.ord.orderID != dbDel.ord.orderID)
				columns.add("order_id");
			if (del.deliveryDate != dbDel.deliveryDate)
				columns.add("delivery_date");

			PreparedStatement update = updateStatement(columns);

			int counter = 1;
			for (String s : columns) {
				if (s.equals("route_id")) {
					update.setInt(counter++, del.route.routeID);
					continue;
				}

				if (s.equals("order_id")) {
					update.setInt(counter++, del.ord.orderID);
					continue;
				}

				if (s.equals("delivery_date")) {
					update.setDate(counter++, del.deliveryDate);
				}
			}

			update.setInt(counter++, del.id);

			update.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
