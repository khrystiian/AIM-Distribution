package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Department;
import model.Employee;
import util.EnumMaps;

/**
 * The connection of the Employee class with SQL database.
 * 
 * @author Ciobanu Andrei, Ioana Ganescu and Oprea Mircea.
 * @version 10.05.2016
 */
public class EmployeeDB {

	private Connection connection;

	public EmployeeDB() {
		connection = DBConnection.getConnection();
	}

	private PreparedStatement selectStatement(boolean all) throws SQLException {
		if (all)
			return connection.prepareStatement("SELECT * FROM Employees;");
		return connection.prepareStatement("SELECT * FROM Employees WHERE emp_id = ?;");
	}

	private PreparedStatement selectByNameStatement() throws SQLException {
		return connection.prepareStatement("SELECT * FROM Employees WHERE name = ?;");
	}

	private PreparedStatement selectByDepartmentStatement() throws SQLException {
		return connection.prepareStatement("SELECT * FROM Employees WHERE department = ?;");
	}

	private PreparedStatement selectAllBelow() throws SQLException {
		return connection.prepareStatement("SELECT * FROM Employees WHERE supervisor_id = ?;");
	}

	/**
	 * The statement for update employee used in the database.
	 * 
	 * @param columns
	 *            A list of the type String for the columns that will be
	 *            updated.
	 * @return An Object of the type PreparedStatement.
	 * @throws SQLException
	 *             If the employee does not exist.
	 */
	private PreparedStatement updateStatement(List<String> columns) throws SQLException {
		PreparedStatement result = null;

		String update = "UPDATE Employees SET ";

		StringBuilder sb = new StringBuilder(update);
		for (int i = 0; i < columns.size(); i++) {
			if (!columns.get(i).equals(null))
				sb.append(columns.get(i));
			if (i != columns.size() - 1)
				sb.append("= ?,");
			else
				sb.append("= ?");

		}

		sb.append(" WHERE emp_id = ?;");

		update = sb.toString();

		result = connection.prepareStatement(update);

		return result;
	}

	/**
	 * The delete statement to delete an employee from database.
	 * 
	 * @return An Object of the type PreparedStatement .
	 * @throws SQLException
	 *             If the employee can not be found to be deleted.
	 */
	private PreparedStatement deleteStatement() throws SQLException {
		return connection.prepareStatement("DELETE FROM Employees WHERE emp_id = ?;");
	}

	/**
	 * The insert statement to insert an employee from database.
	 * 
	 * @return An Object of the type PreparedStatement .
	 * @throws SQLException
	 *             If the employee can not be inserted.
	 */
	private PreparedStatement insertStatement() throws SQLException {
		return connection.prepareStatement(
				"INSERT INTO Employees (name, salary, emp_password, supervisor_id, department) VALUES (?,?,?,?,?);");
	}

	/**
	 * Find an employee in the database.
	 * 
	 * @param employeeName
	 *            A String for the employee's name.
	 * @return An Object of the type Employee.
	 */
	public Employee selectEmployee(String employeeName) {
		try {
			PreparedStatement select = selectByNameStatement();
			select.setString(1, employeeName);
			ResultSet result = select.executeQuery();

			if (result.next()) {
				int id = result.getInt("emp_id");
				int salary = result.getInt("salary");
				byte[] passwordBytes = result.getBytes("emp_password");
				String password = new String(passwordBytes);
				int supervisorID = result.getInt("supervisor_id");
				Department department = EnumMaps.getDepartment(result.getString("department"));

				return new Employee(employeeName, id, salary, supervisorID, password, department);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Employee selectEmployee(int employeeID) {
		try {
			PreparedStatement select = selectStatement(false);
			select.setInt(1, employeeID);
			ResultSet result = select.executeQuery();

			if (result.next()) {
				String name = result.getString("name");
				int salary = result.getInt("salary");
				String password = result.getString("emp_password");
				int supervisorID = result.getInt("supervisor_id");
				Department department = EnumMaps.getDepartment(result.getString("department"));

				return new Employee(name, employeeID, salary, supervisorID, password, department);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Show all the employees from database table for a specific department.
	 * 
	 * @param department
	 *            A String for the department name.
	 * @return A list of the employees.
	 */
	public List<Employee> selectEmployeesFrom(String department) {
		List<Employee> employees = new ArrayList<>();
		try {
			PreparedStatement select = selectByDepartmentStatement();
			select.setString(1, department);
			ResultSet result = select.executeQuery();

			while (result.next()) {
				int employeeID = result.getInt("emp_id");
				String name = result.getString("name");
				int salary = result.getInt("salary");
				String password = result.getString("emp_password");
				int supervisorID = result.getInt("supervisor_id");
				employees.add(new Employee(name, employeeID, (double) salary, supervisorID, password,
						EnumMaps.getDepartment(department)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return employees;
	}

	/**
	 * Show all the employees from a specific supervisor.
	 * 
	 * @param supervisorID
	 *            An Integer for the supervisor's id.
	 * @return A list of employees.
	 */
	public List<Employee> selectAllBelow(int supervisorID) {
		List<Employee> employees = new ArrayList<Employee>();
		try {
			PreparedStatement select = selectAllBelow();
			select.setInt(1, supervisorID);
			ResultSet result = select.executeQuery();

			while (result.next()) {
				int id = result.getInt("emp_id");
				String name = result.getString("name");
				int salary = result.getInt("salary");
				byte[] passwordBytes = result.getBytes("emp_password");
				String password = new String(passwordBytes);
				Department department = EnumMaps.getDepartment(result.getString("department"));

				employees.add(new Employee(name, id, salary, supervisorID, password, department));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return employees;
	}

	/**
	 * Get all the employees.
	 * 
	 * @return A list with all the employees from database.
	 */
	public List<Employee> getAllEmployees() {
		List<Employee> employees = new ArrayList<Employee>();
		try {
			PreparedStatement select = selectStatement(true);
			ResultSet result = select.executeQuery();

			while (result.next()) {
				int id = result.getInt("emp_id");
				String name = result.getString("name");
				int salary = result.getInt("salary");
				String password = result.getString("emp_password");
				int supervisorID = result.getInt("supervisor_id");
				Department department = EnumMaps.getDepartment(result.getString("department"));

				employees.add(new Employee(name, id, salary, supervisorID, password, department));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return employees;
	}

	public void updateEmployee(Employee employee) {
		try {
			Employee dbEmployee = selectEmployee(employee.employeeID);
			List<String> columns = new ArrayList<String>();

			if (!employee.name.equals(dbEmployee.name))
				columns.add("name");
			if (!employee.getEncryptedPassword().equals(dbEmployee.getEncryptedPassword()))
				columns.add("emp_password");
			if (employee.salary != dbEmployee.salary)
				columns.add("salary");
			if (employee.supervisorID != dbEmployee.supervisorID)
				columns.add("supervisor_id");
			if (!employee.department.equals(dbEmployee.department))
				columns.add("department");

			PreparedStatement update = updateStatement(columns);

			int counter = 1;
			for (String s : columns) {
				if (s.equals("name")) {
					update.setString(counter++, employee.name);
					continue;
				}

				if (s.equals("emp_password")) {
					update.setBytes(counter++, employee.getEncryptedPassword().getBytes());
					continue;
				}

				if (s.equals("salary")) {
					update.setInt(counter++, (int) employee.salary);
					continue;
				}

				if (s.equals("supervisor_id")) {
					update.setInt(counter++, employee.supervisorID);
					continue;
				}

				if (s.equals("department")) {
					update.setString(counter++, employee.department.toString());
					continue;
				}

			}

			update.setInt(counter++, employee.employeeID);

			update.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteEmployee(int employeeID) {
		try {
			PreparedStatement delete = deleteStatement();
			delete.setInt(1, employeeID);

			delete.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void insertEmployee(Employee e) {
		try {
			PreparedStatement insert = insertStatement();

			insert.setString(1, e.name);
			insert.setInt(2, (int) e.salary);
			insert.setString(3, e.getEncryptedPassword());
			insert.setInt(4, e.supervisorID);
			insert.setString(5, e.department.toString());

			insert.executeUpdate();

		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

}
