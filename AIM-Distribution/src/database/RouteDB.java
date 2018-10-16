package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import model.Delivery;
import model.Route;

/**
 * The connection of the Route class with SQL database.
 * 
 * @author Ciobanu Andrei, Ioana Ganescu and Oprea Mircea.
 * @version 10.05.2016
 */
public class RouteDB {

	private Connection con;

	/**
	 * Connect with the SQL database.
	 */
	public RouteDB() {
		con = DBConnection.getConnection();
	}

	private PreparedStatement selectStatement(boolean all) throws SQLException {
		if (all)
			return con.prepareStatement("SELECT * FROM Routes");
		return con.prepareStatement("SELECT * FROM Routes WHERE route_id = ?;");
	}

	/**
	 * The select statement for the database to find a route by id.
	 * 
	 * @return An Object of the type PreparedStatement.
	 * @throws SQLException
	 *             If the connection fails of the route does not exist.
	 */
	private PreparedStatement selectByIDStatement() throws SQLException {
		return con.prepareStatement("SELECT * FROM Routes WHERE route_id = ?;");
	}

	/**
	 * The insert statement for the database to insert a route.
	 * 
	 * @return An Object of the type PreparedStatement.
	 * @throws SQLException
	 *             If the insert fails.
	 */
	private PreparedStatement insertStatement() throws SQLException {
		return con.prepareStatement(
				"INSERT INTO Routes(optimal_time, delivery_time, finished, plate_no, emp_id, starting_time) VALUES (?, ?, ?, ?, ?, ?);");
	}

	/**
	 * The delete statement for the database to delete a route by id.
	 * 
	 * @return An Object of the type PreparedStatement.
	 * @throws SQLException
	 *             If the connection fails of the route does not exist.
	 */
	private PreparedStatement deleteStatement() throws SQLException {
		return con.prepareStatement("DELETE FROM Routes WHERE route_id = ?;");
	}

	private PreparedStatement getMaxStatement() throws SQLException {
		return con.prepareStatement("SELECT MAX(route_id) AS max_route_id FROM Routes;");
	}

	/**
	 * Find the last route id.
	 * 
	 * @return the last route id if found or 0 if is not found.
	 */
	private int getMaxID() {
		try {
			PreparedStatement max = getMaxStatement();

			ResultSet results = max.executeQuery();

			if (results.next())
				return results.getInt("max_route_id");

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * The update statement for update query in the database.
	 * 
	 * @param columns
	 *            A list of the type String for the columns that will be
	 *            updated.
	 * @return An Object of the type PreparedStatement.
	 * @throws SQLException
	 *             If the update fails.
	 */
	private PreparedStatement updateStatement(List<String> columns) throws SQLException {
		PreparedStatement result = null;

		String update = "UPDATE Routes SET ";

		StringBuilder sb = new StringBuilder(update);
		for (int i = 0; i < columns.size(); i++) {
			if (!columns.get(i).equals(null))
				sb.append(columns.get(i));
			if (i != columns.size() - 1)
				sb.append("= ?,");
			else
				sb.append("= ?");
		}
		sb.append(" WHERE route_id = ?;");

		update = sb.toString();

		result = con.prepareStatement(update);
		return result;
	}

	/**
	 * The select route statement for the select query in the database.
	 * 
	 * @param routeID
	 *            An Integer for the route's id.
	 * @return An Object of the type Route for the route selected.
	 */
	public Route selectRoute(int routeID) {
		try {
			PreparedStatement select = selectByIDStatement();
			select.setInt(1, routeID);
			ResultSet res = select.executeQuery();

			if (res.next()) {
				Time optimal_time = res.getTime("optimal_time");
				Time delivery_time = res.getTime("delivery_time");
				String finished = res.getString("finished");
				int plate_no = res.getInt("plate_no");
				int emp_id = res.getInt("emp_id");
				Time starting_time = res.getTime("starting_time");

				Route rt = new Route(routeID, new TruckDB().selectTruck(plate_no),
						new EmployeeDB().selectEmployee(emp_id));
				rt.optimalTime = optimal_time;
				rt.deliveryTime = delivery_time;
				rt.startingTime = starting_time;
				rt.finished = Boolean.parseBoolean(finished);

				return rt;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Show all the routes from Routes table in the database.
	 * 
	 * @return A list with all the routes.
	 */
	public List<Route> getAllRoutes() {
		List<Route> routes = new ArrayList<Route>();
		try {
			PreparedStatement select = selectStatement(true);
			ResultSet res = select.executeQuery();
			while (res.next()) {
				int routeID = res.getInt("route_id");
				Time optimal_time = res.getTime("optimal_time");
				Time delivery_time = res.getTime("delivery_time");
				String finished = res.getString("finished");
				int plate_no = res.getInt("plate_no");
				int emp_id = res.getInt("emp_id");
				Time starting_time = res.getTime("starting_time");

				Route route = new Route(routeID, new TruckDB().selectTruck(plate_no),
						new EmployeeDB().selectEmployee(emp_id));
				route.optimalTime = optimal_time;
				route.deliveryTime = delivery_time;
				route.startingTime = starting_time;
				route.finished = Boolean.parseBoolean(finished);

				routes.add(route);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return routes;
	}

	/**
	 * Delete a route in the database.
	 * 
	 * @param routeID
	 *            An Integer for the route's id.
	 */
	public void deleteRoute(int routeID) {
		try {
			PreparedStatement delete = deleteStatement();
			delete.setInt(1, routeID);

			delete.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Update a route in database.
	 * 
	 * @param route
	 *            An Object of the type Route that will be updated.
	 */
	public void updateRoute(Route route) {
		try {
			Route dbRoute = selectRoute(route.routeID);
			List<String> columns = new ArrayList<String>();

			if (route.optimalTime != dbRoute.optimalTime)
				columns.add("optimal_time");
			if (route.deliveryTime != dbRoute.deliveryTime)
				columns.add("delivery_time");
			if (route.finished != dbRoute.finished)
				columns.add("finished");
			if (route.truck.plateNo != dbRoute.truck.plateNo)
				columns.add("plate_no");
			if (route.emp.employeeID != dbRoute.emp.employeeID)
				columns.add("emp_id");
			if (route.startingTime != dbRoute.startingTime)
				columns.add("starting_time");

			PreparedStatement update = updateStatement(columns);

			int counter = 1;
			for (String s : columns) {
				if (s.equals("optimal_time")) {
					update.setTime(counter++, route.optimalTime);
					continue;
				}

				if (s.equals("delivery_time")) {
					update.setTime(counter++, route.deliveryTime);
					continue;
				}

				if (s.equals("finished")) {
					update.setString(counter++, Boolean.toString(route.finished));
					continue;
				}

				if (s.equals("plate_no")) {
					update.setInt(counter++, route.truck.plateNo);
					continue;
				}

				if (s.equals("emp_id")) {
					update.setInt(counter++, route.emp.employeeID);
					continue;
				}
				if (s.equals("starting_time")) {
					update.setTime(counter++, route.startingTime);
					continue;
				}
			}

			update.setInt(counter++, route.routeID);

			update.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Insert a route in the database.
	 * 
	 * @param route
	 *            An Object of the type route inserted.
	 */
	public void insertRoute(Route route) {
		try {
			con.setAutoCommit(false);
			PreparedStatement insert = insertStatement();
			boolean finished = route.finished;
			String finish = Boolean.toString(finished);

			insert.setTime(1, route.optimalTime);
			insert.setTime(2, route.deliveryTime);
			insert.setString(3, finish);
			insert.setInt(4, route.truck.plateNo);
			insert.setInt(5, route.emp.employeeID);
			insert.setTime(6, new Time(0));

			insert.executeUpdate();

			route.routeID = getMaxID();

			for (Delivery d : route.deliveries) {
				new DeliveryDB().insertDelivery(d);
			}

			con.commit();
			con.setAutoCommit(true);

		} catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
}
