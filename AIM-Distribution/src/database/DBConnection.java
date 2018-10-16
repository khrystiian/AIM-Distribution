package database;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * The connection with SQL database.
 * 
 * @author Ciobanu Andrei, Ioana Ganescu and Oprea Mircea.
 * @version 10.05.2016
 */
public class DBConnection {

	private static Connection con = null;

	private static final String DRIVER = "jdbc:sqlserver://kraka.ucn.dk:1433";
	private static final String DATABASE_NAME = ";databaseName=dmai0915_2Sem_4";
	private static final String USER_NAME = "; user=dmai0915_2Sem_4";
	private static final String PASSWORD = ";password=IsAllowed";

	/**
	 * Create the connection with the SQL database.
	 * 
	 * @param userName
	 *            A String for the user's username.
	 * @param password
	 *            A String for the user's password.
	 */
	private static void createConnection(String userName, String password) {
		String url = DRIVER + DATABASE_NAME + "; " + userName + "; " + password;
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			con = DriverManager.getConnection(url);
			con.setAutoCommit(true);
			System.out.println("Database connection successfully !\n");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Get the connection with the database.
	 * 
	 * @return An Object of the type Connection.
	 */
	public static Connection getConnection() {
		return getConnection(USER_NAME, PASSWORD);
	}

	public static Connection getConnection(String userName, String password) {
		if (con == null) {
			createConnection(userName, password);
		}
		return con;
	}

	/**
	 * Close the connection with the database.
	 */
	public static void closeConnection() {
		try {
			con.close();
			System.out.println("Connection closed.");
		} catch (Exception e) {
			System.out.println("Error trying to close the connection " + e.getMessage());
		}
	}

}
