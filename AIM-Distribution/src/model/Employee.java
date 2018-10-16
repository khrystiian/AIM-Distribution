package model;

import util.Encryption;

/**
 * The Employee ModelLayer interface.
 * 
 * @author Ciobanu Andrei, Ioana Ganescu and Oprea Mircea.
 * @version 08.05.2016
 */
public class Employee {

	public int employeeID;
	public String name;
	private String password;
	public double salary;
	public int supervisorID;
	public Department department;

	/**
	 * Use this constructor when registering a new employee.
	 * 
	 * @param name
	 *            A String for the employee's name.
	 * @param salary
	 *            A double for the employee's salary.
	 * @param supervisorID
	 *            An Integer for the employee's supervisor id.
	 */
	public Employee(String name, double salary, int supervisorID) {
		this.name = name;
		this.salary = salary;
		this.supervisorID = supervisorID;
		setPassword("password".toCharArray());
	}

	/**
	 * Use this constructor when create a new employee with the department.
	 * 
	 * @param name
	 *            A String for the employee's name.
	 * @param salary
	 *            A double for the employee's salary.
	 * @param supervisorID
	 *            An Integer for the employee's supervisor id.
	 * @param department
	 *            An object of the type Department for the employee's
	 *            department.
	 */
	public Employee(String name, double salary, int supervisorID, Department department) {
		this.name = name;
		this.salary = salary;
		this.supervisorID = supervisorID;
		this.department = department;
		setPassword("password".toCharArray());
	}

	/**
	 * Use this constructor when create a new employee in the database.
	 * 
	 * @param name
	 *            A String for the employee's name.
	 * @param salary
	 *            A double for the employee's salary.
	 * @param supervisorID
	 *            An Integer for the employee's supervisor id.
	 * @param encryptedPassword
	 *            A String for the encrypted password.
	 * @param employeeID
	 *            An Integer for the employee's id.
	 */
	public Employee(String name, int employeeID, double salary, int supervisorID, String encryptedPassword,
			Department department) {
		this.name = name;
		this.employeeID = employeeID;
		this.salary = salary;
		this.supervisorID = supervisorID;
		this.department = department;
		this.password = encryptedPassword;
	}

	/**
	 * This method encrypts the password for security.
	 * 
	 * @param password
	 *            A character that will be the password.
	 */
	public void setPassword(char[] password) {
		this.password = new String(Encryption.encrypt(password));
		for (int i = 0; i < password.length; i++)
			password[i] = '0';
	}

	/**
	 * This method return the password encrypted.
	 * 
	 * @return The password as a String.
	 */
	public String getEncryptedPassword() {
		return password;
	}

}
