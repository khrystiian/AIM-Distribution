package controller;

import java.util.List;

import database.EmployeeDB;
import model.Department;
import model.Employee;
import util.Tree;
import util.TreeNode;

/**
 * The functionality of the Employee class.
 * 
 * @author Ciobanu Andrei, Ioana Ganescu and Oprea Mircea.
 * @version 10.05.2016
 */
public class EmployeeCtrl {
	/**
	 * Finds a employee in the database using as a input the id.
	 * 
	 * @param employeeID
	 *            An Integer for the employee's id.
	 * @return An Object of the type Employee for the employee found in the
	 *         database.
	 */
	public Employee findEmployee(int employeeID) {
		return new EmployeeDB().selectEmployee(employeeID);
	}

	/**
	 * Finds a employee in the database using as a input the name.
	 * 
	 * @param employeeName
	 *            A String for the employee's name.
	 * @return An Object of the type Employee for the employee found in the
	 *         database.
	 * @throws NullPointerException
	 *             If the employee does not exist in the database.
	 */
	public Employee findEmployee(String employeeName) throws NullPointerException {
		Employee result = new EmployeeDB().selectEmployee(employeeName);
		if (result == null)
			throw new NullPointerException();
		return result;
	}

	/**
	 * Finds all the employees in the database.
	 * 
	 * @return A list of the type Employee with all the employees from the
	 *         database.
	 */
	public List<Employee> findAllEmployees() {
		return new EmployeeDB().getAllEmployees();
	}

	/**
	 * Finds all the employees in the database for a specific department.
	 * 
	 * @param department
	 *            A String for the department where will be found the employees.
	 * @return A list with all the employees from the database that belong to
	 *         the user input department.
	 */
	public List<Employee> findAllEmployeesFrom(String department) {
		return new EmployeeDB().selectEmployeesFrom(department);
	}

	public Tree<Employee> getAllAsTree(int employeeID) {
		TreeNode<Employee> root = new TreeNode<Employee>(findEmployee(employeeID));
		
		List<Employee> empList = new EmployeeDB().selectAllBelow(employeeID);
		empList.forEach(n -> root.children.add(new TreeNode<Employee>(n)));
		
		Tree<Employee> employees = new Tree<Employee>(root);

		root.children.forEach(n -> employees.add(n));

		return employees;
	}

	/**
	 * Update an employee.
	 * 
	 * @param employee
	 *            An Object of the type Employee found to be updated.
	 */
	public void updateEmployee(Employee employee) {
		Employee dbEmp = findEmployee(employee.employeeID);
		int id = dbEmp.employeeID;

		if (!employee.name.equals(dbEmp.name))
			changeName(id, employee.name);
		if (employee.salary != dbEmp.salary)
			changeSalary(id, employee.salary);
		if (employee.supervisorID != dbEmp.supervisorID) {
			changeSupervisor(id, employee.supervisorID);
			if (!employee.department.equals(dbEmp.department))
				changeDepartment(id, employee.department);
		}
	}

	/**
	 * This method updates the name of the employee in the database.
	 * 
	 * @param id
	 *            An Integer for the employee's id to be found.
	 * @param name
	 *            A String for the new name given to the employee.
	 */
	public void changeName(int id, String name) {
		Employee employee = findEmployee(id);
		employee.name = name;
		new EmployeeDB().updateEmployee(employee);
	}

	/**
	 * Change the password of the user.
	 * 
	 * @param id
	 *            An Integer for the employee's id.
	 * @param password
	 *            An Array of the characters for the password that will be
	 *            changed.
	 */
	public void changePassword(int id, char[] password) {
		Employee employee = findEmployee(id);
		employee.setPassword(password);
		new EmployeeDB().updateEmployee(employee);
	}

	/**
	 * This method updates the salary of the employee in the database.
	 * 
	 * @param id
	 *            An Integer for the employee's id to be found.
	 * @param salary
	 *            A double for the new salary given to the employee.
	 */
	public void changeSalary(int id, double salary) {
		Employee employee = findEmployee(id);
		employee.salary = salary;
		new EmployeeDB().updateEmployee(employee);
	}

	/**
	 * This method updates the supervisor of the employee.
	 * 
	 * @param id
	 *            An Integer for the employee's id to be found.
	 * @param supervisorID
	 *            An Integer for the new supervisor id given to the employee.
	 */
	public void changeSupervisor(int id, int supervisorID) {
		Employee employee = findEmployee(id);
		employee.supervisorID = supervisorID;
		new EmployeeDB().updateEmployee(employee);
	}

	/**
	 * This method updates the department of the employee.
	 * 
	 * @param id
	 *            An Integer for the employee's id to be found.
	 * @param dept
	 *            An Object of the type Department for the new department given
	 *            to the employee.
	 */
	public void changeDepartment(int id, Department dept) {
		Employee employee = findEmployee(id);
		employee.department = dept;
		new EmployeeDB().updateEmployee(employee);
	}

	/**
	 * Insert a new employee in the database.
	 * 
	 * @param name
	 *            A String for the employee's name.
	 * @param salary
	 *            An Integer for the employee's salary.
	 * @param supervisorID
	 *            An Integer for the employee's supervisor.
	 * @param department
	 *            An Object of the type Department for the employee's
	 *            department.
	 */
	public void insertEmployee(String name, int salary, int supervisorID, Department department) {
		Employee emp = new Employee(name, salary, supervisorID, department);
		new EmployeeDB().insertEmployee(emp);
	}

	/**
	 * Remove an employee from database.
	 * 
	 * @param id
	 *            An Integer for the employee's id found to be deleted.
	 */
	public void deleteEmployee(int id) {
		new EmployeeDB().deleteEmployee(id);
	}

}
