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
import model.Employee;
import model.Order;
import model.OrderProduct;
import model.ProcessStatus;
import model.Resupply;
import util.EnumMaps;

/**
 * The connection of the Supplies class with SQL database.
 * 
 * @author Ciobanu Andrei, Ioana Ganescu and Oprea Mircea.
 * @version 10.05.2016
 */
public class ResupplyDB {

	// Connect with the database.
	private Connection con;

	public ResupplyDB() {
		con = DBConnection.getConnection();
	}

	/**
	 * Create a statement for select a supply from the Supplies table.
	 * 
	 * @param all
	 *            A boolean that when will be true first prepared statement will
	 *            be used, otherwise the second.
	 * @return An object of the type PreparedStatement.
	 * @throws SQLException
	 *             if errors occur in SQL database.
	 */
	private PreparedStatement selectStatement(boolean all) throws SQLException {
		if (all)
			return con.prepareStatement("select * from Supplies");
		return con.prepareStatement("select * from Supplies where id = ?");
	}

	/**
	 * Create a statement for the insert a supply method.
	 * 
	 * @return An object of the type PreparedStatement.
	 * @throws SQLException
	 *             if errors occur in the SQL database.
	 */
	private PreparedStatement insertStatement() throws SQLException {
		return con.prepareStatement("insert into Supplies (total_amount, supply_date, supplier_name, supply_status, emp_id) values (?, ?, ?, ?, ?);");

	}

	private PreparedStatement updateStatement() throws SQLException {
		return con.prepareStatement("UPDATE Supplies SET supply_status = ? WHERE id = ?;");
	}

	/**
	 * Create a statement for the delete supply method.
	 * 
	 * @return An object of the type PreparedStatement.
	 * @throws SQLException
	 *             if errors occur in the SQL database.
	 */
	private PreparedStatement deleteStatement() throws SQLException {
		return con.prepareStatement("delete from Supplies where id = ?;");
	}

	private PreparedStatement getMaxStatement() throws SQLException {
		return con.prepareStatement("SELECT MAX(id) AS max_id FROM Supplies;");
	}

	private int getMaxID() {
		try {
			PreparedStatement max = getMaxStatement();

			ResultSet results = max.executeQuery();

			if (results.next())
				return results.getInt("max_id");

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * Select a supply from Supplies table using the id.
	 * 
	 * @param id
	 *            An integer used to find a supply.
	 * @return An object of the type Supply found.
	 */
	public Resupply selectResupply(int id) {
		try {
			PreparedStatement select = selectStatement(false);
			select.setInt(1, id);

			ResultSet result = select.executeQuery();

			if (result.next()) {
				double amount = result.getDouble("total_amount");
				Date date = result.getDate("supply_date");
				String supplier = result.getString("supplier_name");
				Employee employee = new EmployeeDB().selectEmployee(result.getInt("emp_id"));
				String status = result.getString("supply_status");
				Resupply resupply = new Resupply(id, supplier, amount, date, EnumMaps.getProcessStatus(status), employee);
				resupply.products.addAll(new SupplyRecordDB().select(id));
				return resupply;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Resupply> selectAllResupplies() {
		List<Resupply> resupplies = new ArrayList<Resupply>();
		try {
			PreparedStatement select = selectStatement(true);

			ResultSet result = select.executeQuery();

			while (result.next()) {
				int resupplyID = result.getInt("id");
				double totalAmount = result.getDouble("total_amount");
				Date date = result.getDate("supply_Date");
				String status = result.getString("supply_status");
				int employeeID = result.getInt("emp_id");

				String supplier = result.getString("supplier_name");
				Employee employee = new EmployeeDB().selectEmployee(employeeID);

				List<OrderProduct> products = new SupplyRecordDB().select(resupplyID);
				Resupply resupply = new Resupply(resupplyID, supplier, totalAmount, date, EnumMaps.getProcessStatus(status), employee);
				resupply.products.addAll(products);
				resupplies.add(resupply);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resupplies;
	}

	/**
	 * Insert a supply in the table Supplies in the database.
	 * 
	 * @param ord
	 * @param sup
	 */
	public void insertResupply(Resupply resupply) {
		try {
			con.setAutoCommit(false);
			PreparedStatement insert = insertStatement();

			insert.setDouble(1, resupply.getFinalPrice(0));
			insert.setDate(2, (java.sql.Date) resupply.processDate);
			insert.setString(3, resupply.supplier);
			insert.setString(4, resupply.status.toString());
			insert.setInt(5, resupply.employee.employeeID);

			insert.executeUpdate();

			resupply.resupplyID = getMaxID();

			for (OrderProduct prod : resupply.products)
				new SupplyRecordDB().insert(resupply.resupplyID, prod);

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

	public void updateResupply(int resupplyID, ProcessStatus status) {
		try {
			PreparedStatement update = updateStatement();
			update.setString(1, status.toString());
			update.setInt(2, resupplyID);

			update.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Delete a supply from Supplies table.
	 * 
	 * @param id
	 *            An integer for the supply's to be found in database.
	 */
	public void deleteResupply(int id) {
		try {
			con.setAutoCommit(false);
			PreparedStatement delete = deleteStatement();

			delete.setInt(1, id);
			delete.executeUpdate();

			new SupplyRecordDB().delete(id);

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
