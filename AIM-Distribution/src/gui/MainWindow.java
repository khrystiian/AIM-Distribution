package gui;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import gui.panels.CustomerPanel;
import gui.panels.EmployeePanel;
import gui.panels.OrderPanel;
import gui.panels.ProductPanel;
import gui.panels.ProfilePanel;
import gui.panels.RoutePanel;
import gui.panels.SupplyPanel;
import gui.panels.TruckPanel;
import model.Department;
import util.Session;

public class MainWindow extends JFrame {

	private JPanel contentPane;
	private final JPanel customerPanel = new CustomerPanel();
	private final JPanel employeePanel = new EmployeePanel();
	private final JPanel orderPanel = new OrderPanel();
	private final JPanel productPanel = new ProductPanel();
	private final JPanel supplyPanel = new SupplyPanel();
	private final JPanel routePanel = new RoutePanel();
	private final JPanel truckPanel = new TruckPanel();
	private final JPanel profilePanel = new ProfilePanel();
	private final JButton empBtn = new JButton("Employees");
	private final JButton custBtn = new JButton("Customers");
	private final JButton orderBtn = new JButton("Orders");
	private final JButton productBtn = new JButton("Products");
	private final JButton routeBtn = new JButton("Routes");
	private final JButton supplyBtn = new JButton("Supply");
	private final JPanel panel = new JPanel();
	private final JButton profileBtn = new JButton("Profile");
	private final JButton truckBtn = new JButton("Trucks");

	public MainWindow() {
		setVisible(true);
		setTitle("AIM Distribution");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(new Dimension(1133, 800));
		setLocationRelativeTo(null);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		panel.setBounds(170, 0, 937, 740);
		panel.setLayout(new CardLayout());
		contentPane.add(panel);

		createCardLayout();
		((CardLayout) panel.getLayout()).show(panel, "Employees");

		empBtn.setFont(new Font("Tahoma", Font.PLAIN, 18));
		empBtn.setIcon(new ImageIcon("resources/Employee 1.png"));
		empBtn.setBounds(0, 0, 160, 100);

		custBtn.setFont(new Font("Tahoma", Font.PLAIN, 18));
		custBtn.setIcon(new ImageIcon("resources/People.png"));
		custBtn.setBounds(0, 100, 160, 100);

		orderBtn.setFont(new Font("Tahoma", Font.PLAIN, 18));
		orderBtn.setIcon(new ImageIcon("resources/Order.png"));
		orderBtn.setBounds(0, 200, 160, 100);

		productBtn.setFont(new Font("Tahoma", Font.PLAIN, 18));
		productBtn.setIcon(new ImageIcon("resources/Product 2.png"));
		productBtn.setBounds(0, 300, 160, 100);

		routeBtn.setFont(new Font("Tahoma", Font.PLAIN, 18));
		routeBtn.setIcon(new ImageIcon("resources/Route.png"));
		routeBtn.setBounds(0, 400, 160, 100);

		supplyBtn.setFont(new Font("Tahoma", Font.PLAIN, 18));
		supplyBtn.setIcon(new ImageIcon("resources/Supply 1.png"));
		supplyBtn.setBounds(0, 500, 160, 100);
		
		truckBtn.setFont(new Font("Tahoma", Font.PLAIN, 18));
		truckBtn.setIcon(new ImageIcon("resources/Truck.png"));
		truckBtn.setBounds(0, 600, 160, 100);
		
		profileBtn.setFont(new Font("Tahoma", Font.PLAIN, 18));
		profileBtn.setIcon(new ImageIcon("resources/Profile.png"));
		profileBtn.setBounds(0, 700, 160, 100);

		empBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				((CardLayout) panel.getLayout()).show(panel, "Employees");
			}
		});

		custBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				((CardLayout) panel.getLayout()).show(panel, "Customers");
			}
		});

		orderBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				((CardLayout) panel.getLayout()).show(panel, "Orders");
			}
		});
		
		productBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				((CardLayout) panel.getLayout()).show(panel, "Products");
			}
		});

		routeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				((CardLayout) panel.getLayout()).show(panel, "Routes");
			}
		});

		supplyBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				((CardLayout) panel.getLayout()).show(panel, "Supply");
			}
		});
		
		truckBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				((CardLayout) panel.getLayout()).show(panel, "Trucks");
			}
		});
		
		profileBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				((CardLayout) panel.getLayout()).show(panel, "Profile");
			}
		});

		contentPane.add(empBtn);
		profileBtn.setBounds(0, 100, 160, 100);
		contentPane.add(profileBtn);
		

		if (Session.employee.department.equals(Department.MARKETING)) {
			contentPane.add(custBtn);
			contentPane.add(orderBtn);
			contentPane.add(productBtn);
			supplyBtn.setBounds(0, 400, 160, 100);
			contentPane.add(supplyBtn);
			profileBtn.setBounds(0, 500, 160, 100);
		} else if (Session.employee.department.equals(Department.WAREHOUSE)) {
			productBtn.setBounds(0, 100, 160, 100);
			contentPane.add(productBtn);
			profileBtn.setBounds(0, 200, 160, 100);
		} else if (Session.employee.department.equals(Department.DISTRIBUTION)) {
			contentPane.add(custBtn);
			contentPane.add(orderBtn);
			routeBtn.setBounds(0, 300, 160, 100);
			contentPane.add(routeBtn);
			supplyBtn.setBounds(0, 400, 160, 100);
			contentPane.add(supplyBtn);
			truckBtn.setBounds(0, 500, 160, 100);
			contentPane.add(truckBtn);
			profileBtn.setBounds(0, 600, 160, 100);
		} else if (Session.employee.department.equals(Department.CEO)) {
			contentPane.add(custBtn);
			contentPane.add(orderBtn);
			contentPane.add(productBtn);
			contentPane.add(routeBtn);
			contentPane.add(supplyBtn);
			contentPane.add(truckBtn);
			profileBtn.setBounds(0, 700, 160, 100);
		}
	}

	private void createCardLayout() {

		panel.add(employeePanel, "Employees");
		panel.add(customerPanel, "Customers");
		panel.add(orderPanel, "Orders");
		panel.add(productPanel, "Products");
		panel.add(supplyPanel, "Supply");
		panel.add(routePanel, "Routes");
		panel.add(truckPanel, "Trucks");
		panel.add(profilePanel, "Profile");

	}
}
