package org.schoolFinancialSystem;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;

public class EmployeeTab {

    // Connection to the database
    private final String DBusername = "DSAstore";
    private final String DBpassword = "DSAstore123";
    private final String url = "jdbc:mysql://localhost:3306/DSAstore";

    // initialize the global variables
    private static Connection connection = null;
    private static Statement statement = null;
    private static ResultSet resultSet = null;

    // GUI components global variables
    private JTextField employeeIDField;
    private JTextField employeeNameField;
    private JTextField employeeRoleField;
    private JTextField unpaidSalaryField;
    private JTextField employeeIDField2;
    private JTextField paymentAmountField;
    private JComboBox<String> paymentModeComboBox;
    private JButton findEmployeeButton;
    private JButton payButton;
    private JButton updateButton;
    private JTable table;
    private JPanel employeePanel;

    public EmployeeTab() {
        createEmployeePanel(); // Initialize the employee panel
    }

    //A method for general label styling
    private void styleLabel(JLabel label) {
        label.setFont(new Font("Arial", Font.BOLD, 14));
        label.setForeground(Color.decode("#0f3fde"));
    }

    // Styles a JTextField with consistent properties
    private void styleTextField(JTextField textField) {
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        textField.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        textField.setPreferredSize(new Dimension(200, 30)); // Ensures consistent size
    }

    // Method to create the employee panel
    private void createEmployeePanel() {

        // Initialize components
        employeeIDField = new JTextField();
        employeeNameField = new JTextField();
        employeeRoleField = new JTextField();
        unpaidSalaryField = new JTextField();
        employeeIDField2 = new JTextField();
        paymentAmountField = new JTextField();
        paymentModeComboBox = new JComboBox<>(new String[]{"Cash", "Online"});
        findEmployeeButton = new JButton("Find Employee");
        payButton = new JButton("Pay Wage");
        updateButton = new JButton("Update");

        // Initialize tab panel for employee
        employeePanel = new JPanel();
        employeePanel.setLayout(null);
        employeePanel.setOpaque(true);

        //Create Panel 1: Employee Finance Records//
        JPanel panel1 = new JPanel();
        panel1.setBounds(20, 20, 950, 250);
        panel1.setLayout(new BorderLayout());
        panel1.setOpaque(true);

        //Create a Label for the table 'Student Finance Records'
        JLabel tableLabel = new JLabel("Employee Finance Records", JLabel.CENTER);
        tableLabel.setFont(new Font("Arial", Font.BOLD, 20));
        tableLabel.setForeground(Color.decode("#de0f3f"));
        panel1.add(tableLabel, BorderLayout.NORTH);

        //Create a table for the Employee finance records, displaying "Employee ID", "Name", "Role", "Annual Salary (MYR)", and "Unpaid Salary (MYR)"
        String[] columnNames = {"Employee ID", "Name", "Role", "Annual Salary (MYR)", "Unpaid Salary (MYR)"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        table.setRowHeight(25);
        table.setGridColor(Color.decode("#de0f3f"));
        JScrollPane scrollPane = new JScrollPane(table);
        panel1.add(scrollPane, BorderLayout.CENTER);
        // Fetch data from the database AND Display in the JTable
        fetchData();

        // Panel 2: Searching Employee Records
        // Create a label for the search Employee records form
        JLabel searchLabel = new JLabel("Search Employee Records");
        searchLabel.setBounds(20, 280, 300, 30);
        searchLabel.setFont(new Font("Arial", Font.BOLD, 20));
        searchLabel.setForeground(Color.decode("#de0f3f"));

        // Panel for Employee search fields; placed under the label
        JPanel panel2 = new JPanel();
        panel2.setBounds(20, 320, 450, 200);
        panel2.setLayout(new GridLayout(4, 2, 10, 10));
        panel2.setOpaque(true);

        // Employee ID
        JLabel employeeIDLabel = new JLabel("Employee ID:");
        styleLabel(employeeIDLabel);
        panel2.add(employeeIDLabel);
        employeeIDField = new JTextField();
        styleTextField(employeeIDField);
        panel2.add(employeeIDField);

        // Employee Name
        JLabel employeeNameLabel = new JLabel("Employee Name:");
        styleLabel(employeeNameLabel);
        panel2.add(employeeNameLabel);
        employeeNameField = new JTextField();
        panel2.add(employeeNameField);

        // Employee Role
        JLabel employeeRoleLabel = new JLabel("Employee Role:");
        styleLabel(employeeRoleLabel);
        panel2.add(employeeRoleLabel);
        employeeRoleField = new JTextField();
        panel2.add(employeeRoleField);

        // Unpaid Salary
        JLabel unpaidSalaryLabel = new JLabel("Unpaid Salary (MYR):");
        styleLabel(unpaidSalaryLabel);
        panel2.add(unpaidSalaryLabel);
        unpaidSalaryField = new JTextField();
        panel2.add(unpaidSalaryField);

        // Panel 3:
        // Create a label for the payment form
        JLabel paymentLabel = new JLabel("Payment Wage Form");
        paymentLabel.setBounds(510, 280, 200, 30);
        paymentLabel.setFont(new Font("Arial", Font.BOLD, 20));
        paymentLabel.setForeground(Color.decode("#de0f3f"));
        // For Paying Student Fees; placed beside the panel 3
        JPanel panel3 = new JPanel();
        panel3.setBounds(510, 320, 450, 180);
        panel3.setLayout(new GridLayout(3, 2, 5, 10));
        panel3.setOpaque(true);

        // Create a label for the Student ID
        JLabel employeeIDLabel2 = new JLabel("Employee ID:");
        styleLabel(employeeIDLabel2);
        panel3.add(employeeIDLabel2);
        employeeIDField2 = new JTextField();
        styleTextField(employeeIDField2);
        panel3.add(employeeIDField2);

        // Create a label for the payment amount
        JLabel paymentAmountLabel = new JLabel("Payment Amount (MYR):");
        styleLabel(paymentAmountLabel);
        panel3.add(paymentAmountLabel);
        paymentAmountField = new JTextField();
        styleTextField(paymentAmountField);
        panel3.add(paymentAmountField);

        // Create a dropdown for the payment mode
        JLabel paymentModeLabel = new JLabel("Payment Mode:");
        styleLabel(paymentModeLabel);
        panel3.add(paymentModeLabel);
        JComboBox<String> paymentModeComboBox = new JComboBox<>(new String[]{"Cash", "Online"});
        panel3.add(paymentModeComboBox);

        // Create a button for searching student records
        findEmployeeButton = new JButton("Find Employee");
        findEmployeeButton.setBounds(20, 540, 150, 30);
        styleButton(findEmployeeButton);
        //Add action listener to the button
        findEmployeeButton.addActionListener(e -> searchEmployee());

        // Create a button for paying student fees
        payButton = new JButton("Pay Wage");
        payButton.setBounds(500, 535, 150, 30);
        styleButton(payButton);
        //Add action listener to the button
        payButton.addActionListener(e -> employeePaymentWage());

        //Create a button 'Update' to refresh employee panel
        updateButton = new JButton("Update");
        updateButton.setBounds(670, 535, 150, 30);
        styleButton(updateButton);
        //Add action listener to the button
        updateButton.addActionListener(e -> updateEmployeePanel());

        //Add the panel to the student panel
        employeePanel.add(updateButton);
        employeePanel.add(payButton);
        employeePanel.add(findEmployeeButton);
        employeePanel.add(panel3);
        employeePanel.add(paymentLabel);
        employeePanel.add(panel2);
        employeePanel.add(searchLabel);
        employeePanel.add(panel1);
    }

    // Styles a button with consistent properties
    private void styleButton(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(Color.decode("#0f3fde"));
        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 1),
                BorderFactory.createEmptyBorder(10, 5, 10, 5)
        ));
    }

    /*
    Create a method that fetches data from the Database.
    And store the fetched data in a suitable Data structure so to use later in DSA concepts to showcase DSA concepts.
    Populate the fetched Data into JTable.

    The table info: Database 'DSAstore’, table ’staff_salary.
    The table haS following heading: staff_id (PK)(Varchar), name, staff_role, annual_salary, and unpaid_salary.
    Sample data looks like: 'TR2001', 'Alice Johnson', 'Teacher', 50000, 0.0 .
    */
    public void fetchData() {
        // Fetch data from the database
        // Store the fetched data in a suitable data structure
        // Populate the fetched data into the JTable

        try {
            // Establish connection to the database
            connection = DriverManager.getConnection(url, DBusername, DBpassword);
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM staff_salary");

            // Populate the fetched data into the JTable
            DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
            tableModel.setRowCount(0);
            while (resultSet.next()) {
                String employeeID = resultSet.getString("staff_id");
                String employeeName = resultSet.getString("name");
                String employeeRole = resultSet.getString("staff_role");
                double annualSalary = resultSet.getDouble("annual_salary");
                double unpaidSalary = resultSet.getDouble("unpaid_salary");
                tableModel.addRow(new Object[]{employeeID, employeeName, employeeRole, annualSalary, unpaidSalary});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // Create a method that search an employee by ID and display the employee info in panel 2 textfields
    //Search from the JTable using DSA concepts.
    private void searchEmployee() {
        try {
            String employeeID = employeeIDField.getText().trim();
            DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                if (tableModel.getValueAt(i, 0).equals(employeeID)) {
                    employeeNameField.setText(tableModel.getValueAt(i, 1).toString());
                    employeeRoleField.setText(tableModel.getValueAt(i, 2).toString());
                    unpaidSalaryField.setText(tableModel.getValueAt(i, 4).toString());
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Create a method for employeePaymentWage.
    //Get Employee ID, Payment Amount, and Payment Mode
    //Display the successful paid in a popup window and update the database table
    private void employeePaymentWage() {
        try {
            // Get Employee ID, Payment Amount, and Payment Mode
            String employeeID = employeeIDField2.getText().trim();
            String paymentAmountText = paymentAmountField.getText().trim();
            String paymentMode = paymentModeComboBox.getSelectedItem().toString();

            if (employeeID.isEmpty() || paymentAmountText.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Employee ID and Payment Amount cannot be empty!");
                return;
            }

            double paymentAmount = Double.parseDouble(paymentAmountText);

            // Establish a connection to the database
            connection = DriverManager.getConnection(url, DBusername, DBpassword);
            statement = connection.createStatement();

            // Fetch the employee record from the database
            String query = "SELECT * FROM staff_salary WHERE staff_id = '" + employeeID + "'";
            resultSet = statement.executeQuery(query);

            // Update the employee record in the database
            if (resultSet.next()) {
                double unpaidSalary = resultSet.getDouble("unpaid_salary");

                if (paymentAmount <= unpaidSalary) {
                    unpaidSalary -= paymentAmount;

                    // Update the employee record in the database
                    String updateQuery = "UPDATE staff_salary SET unpaid_salary = " + unpaidSalary + " WHERE staff_id = '" + employeeID + "'";
                    statement.executeUpdate(updateQuery);

                    JOptionPane.showMessageDialog(null, "Payment successful! Unpaid salary: " + unpaidSalary);
                } else {
                    JOptionPane.showMessageDialog(null, "Payment amount exceeds unpaid salary!");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Employee ID not found!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid payment amount!");
        } finally {
            // Close the database resources
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    //Create a method the form and search form
    private void updateEmployeePanel(){
        //clear TextFields
        employeeIDField.setText("");
        employeeIDField2.setText("");
        employeeNameField.setText("");
        employeeRoleField.setText("");
        unpaidSalaryField.setText("");
        paymentAmountField.setText("");
        paymentModeComboBox.setSelectedIndex(0);

        //fetch data and redisplay
        fetchData();
    }


    // Returns the employee panel
    public JPanel getPanel() {
        return employeePanel;
    }
}