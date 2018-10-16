package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Truck;

/**
 * The connection of the Truck class with SQL database.
 * 
 * @author Ciobanu Andrei, Ioana Ganescu and Oprea Mircea.
 * @version 10.05.2016
 */
public class TruckDB {

	private Connection con;

	/**
	 * Connect with the SQL database.
	 */
	public TruckDB() {
		con = DBConnection.getConnection();
	}

	private PreparedStatement selectStatement(boolean available) throws SQLException {
		if (available)
			return con.prepareStatement("SELECT * FROM Trucks");
		return con.prepareStatement("SELECT * FROM Trucks WHERE plate_no = ?");
	}

	private PreparedStatement selectByAvailabilityStatement() throws SQLException {
		return con.prepareStatement("SELECT * FROM Trucks WHERE available = ?");
	}

	/**
	 * The select statement from the truck table in the database.
	 * 
	 * @return An Object of the type PreparedStatement.
	 * @throws SQLException
	 *             If the truck does not exist in the table.
	 */
	private PreparedStatement selectByPlateNo() throws SQLException {
		return con.prepareStatement("SELECT * FROM Trucks WHERE plate_no = ?");
	}

	/**
	 * The insert statement from the truck table in the database.
	 * 
	 * @return An Object of the type PreparedStatement.
	 * @throws SQLException
	 *             If an error occur.
	 */
	private PreparedStatement insertStatement() throws SQLException {
		return con.prepareStatement("INSERT INTO Trucks(plate_no, available) values (?, ?);");
	}

	private PreparedStatement deleteStatement() throws SQLException {
		return con.prepareStatement("DELETE FROM Trucks where plate_no = ?");
	}

	/**
	 * The update statement for the table Trucks in the database.
	 * 
	 * @param columns
	 *            A list of the type String for the columns that will be
	 *            updated.
	 * @return An object of the type PreparedStatement.
	 * @throws SQLException
	 *             If the row can not be updated.
	 */
	private PreparedStatement updateStatement(List<String> columns) throws SQLException {
		PreparedStatement res = null;

		String update = "UPDATE Trucks SET";

		StringBuilder sb = new StringBuilder(update);
		for (int i = 0; i < columns.size(); i++) {
			if (!columns.get(i).equals(null))
				sb.append(columns.get(i));
			if (i == columns.size() - 1)
				sb.append("= ?,");
			else
				sb.append("= ?");
		}

		sb.append(" WHERE plate_no = ?;");

		update = sb.toString();

		res = con.prepareStatement(update);
		return res;
	}

	/**
	 * Select a truck from the table in database.
	 * 
	 * @param plate_no
	 *            An Integer for the plate number of the truck.
	 * @return An Object of the type Truck found in the table.
	 */
	public Truck selectTruck(int plate_no) {
		try {
			PreparedStatement select = selectByPlateNo();
			select.setInt(1, plate_no);
			ResultSet res = select.executeQuery();
			String available = null;

			if (res.next()) {
				available = res.getString("available");

				Truck truck = new Truck(plate_no);
				boolean av = Boolean.parseBoolean(available);
				truck.available = av;

				return truck;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Select an available truck from database.
	 * 
	 * @param available
	 *            A boolean value for the availability of the truck.
	 * @return An Object of the type Truck for the Truck found available.
	 */
	public Truck selectAvailableTruck(boolean available) {
		try {
			PreparedStatement select = selectByAvailabilityStatement();
			select.setString(1, Boolean.toString(available));
			ResultSet res = select.executeQuery();

			if (res.next()) {
				int plateNo = res.getInt("plate_no");
				Truck t = new Truck(plateNo);
				t.available = available;

				return t;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Get all the trucks.
	 * 
	 * @return A list with the trucks.
	 */
	public List<Truck> getAllTrucks() {
		List<Truck> trucks = new ArrayList<Truck>();
		try {
			PreparedStatement select = selectStatement(true);
			ResultSet res = select.executeQuery();
			while (res.next()) {
				int plate_no = res.getInt("plate_no");
				String available = res.getString("available");

				Truck tr = new Truck(plate_no);
				tr.available = Boolean.parseBoolean(available);

				trucks.add(tr);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return trucks;
	}

	/**
	 * Delete a truck.
	 * 
	 * @param plate_no
	 *            An Integer for the truck that will be deleted.
	 */
	public void deleteTruck(int plate_no) {
		try {
			PreparedStatement delete = deleteStatement();
			delete.setInt(1, plate_no);

			delete.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Update a truck in the database.
	 * 
	 * @param truck
	 *            An Object of the type Truck for the truck that will be
	 *            updated.
	 */
	public void updateTruck(Truck truck) {
		try {
			Truck dbTruck = selectTruck(truck.plateNo);
			List<String> columns = new ArrayList<String>();

			if (truck.available != dbTruck.available)
				columns.add("available");

			PreparedStatement update = updateStatement(columns);

			if (truck.available == dbTruck.available)
				update.setString(1, Boolean.toString(truck.available));

			update.setInt(2, truck.plateNo);

			update.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Insert a new truck .
	 * 
	 * @param truck
	 *            An Object of the type Truck to be inserted.
	 */
	public void insertTruck(Truck truck) {
		try {
			PreparedStatement insert = insertStatement();
			boolean av = truck.available;

			String available = Boolean.toString(av);
			insert.setInt(1, truck.plateNo);
			insert.setString(2, available);

			insert.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
