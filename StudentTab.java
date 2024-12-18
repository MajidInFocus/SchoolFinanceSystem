package org.schoolFinancialSystem;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;

public class StudentTab {

    // Connection to the database
    private final String DBusername = "DSAstore";
    private final String DBpassword = "DSAstore123";
    private final String url = "jdbc:mysql://localhost:3306/DSAstore";

    // initialize the global variables
    private static Connection connection = null;
    private static Statement statement = null;
    private static ResultSet resultSet = null;

    // GUI components
    private JTextField studentIDField;
    private JTextField studentNameField;
    private JTextField studentGradeField;
    private JTextField unpaidFeesField;
    private JTextField studentIDField2;
    private JTextField paymentAmountField;
    private JComboBox<String> paymentModeComboBox;
    private JButton findStudentButton;
    private JButton payButton;
    private JButton updateButton;
    private JTable table;
    private JPanel studentPanel;

    public StudentTab() {
        createStudentPanel(); // Initialize the student panel
    }

    //A method for general label styling
    private void styleLabel(JLabel label) {
        label.setFont(new Font("Arial", Font.BOLD, 16));
        label.setForeground(Color.decode("#0f3fde"));
    }

    // Styles a JTextField with consistent properties
    private void styleTextField(JTextField textField) {
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        textField.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        textField.setPreferredSize(new Dimension(200, 30)); // Ensures consistent size
    }

    // Method to create the student panel
    // Method to create the student panel
    private void createStudentPanel() {
        // Initialize text fields components
        studentIDField = new JTextField();
        studentNameField = new JTextField();
        studentGradeField = new JTextField();
        unpaidFeesField = new JTextField();
        studentIDField2 = new JTextField();
        paymentAmountField = new JTextField();

        // Initialize tab panel for the student
        studentPanel = new JPanel();
        studentPanel.setLayout(null);
        studentPanel.setOpaque(true);

        // Create Panel 1: Student Finance Records
        JPanel panel1 = new JPanel();
        panel1.setBounds(20, 20, 950, 250);
        panel1.setLayout(new BorderLayout());
        panel1.setOpaque(true);

        // Create a Label for the table 'Student Finance Records'
        JLabel tableLabel = new JLabel("Student Finance Records", JLabel.CENTER);
        tableLabel.setFont(new Font("Arial", Font.BOLD, 20));
        tableLabel.setForeground(Color.decode("#de0f3f"));
        panel1.add(tableLabel, BorderLayout.NORTH);

        // Create a table for the student finance records, displaying Student ID, Name, Grade, Paid Fees (MYR), and Unpaid Fees (MYR)
        String[] columnNames = {"Student ID", "Name", "Grade", "Paid Fees (MYR)", "Unpaid Fees (MYR)"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        table.setRowHeight(25);
        table.setGridColor(Color.decode("#de0f3f"));
        // Add the table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(table);
        panel1.add(scrollPane, BorderLayout.CENTER);
        // Fetch and display the student records in the table
        fetchStudentRecords();

        // Panel 2: Form for Searching Student Records
        JLabel searchLabel = new JLabel("Search Student Records");
        searchLabel.setBounds(20, 270, 300, 30);
        searchLabel.setFont(new Font("Arial", Font.BOLD, 20));
        searchLabel.setForeground(Color.decode("#de0f3f"));
        JPanel panel2 = new JPanel();
        panel2.setBounds(20, 310, 450, 200);
        panel2.setLayout(new GridLayout(4, 2, 5, 10));
        panel2.setOpaque(true);

        // Create a label for the Student ID
        JLabel studentIDLabel = new JLabel("Student ID:");
        styleLabel(studentIDLabel);
        panel2.add(studentIDLabel);
        styleTextField(studentIDField);
        panel2.add(studentIDField);

        // Create a label for the Student Name
        JLabel studentNameLabel = new JLabel("Student Name:");
        styleLabel(studentNameLabel);
        panel2.add(studentNameLabel);
        panel2.add(studentNameField);

        // Create a label for the Student Grade
        JLabel studentGradeLabel = new JLabel("Student Grade:");
        styleLabel(studentGradeLabel);
        panel2.add(studentGradeLabel);
        panel2.add(studentGradeField);

        // Create a label for the unpaid fees
        JLabel unpaidFeesLabel = new JLabel("Unpaid Fees (MYR):");
        styleLabel(unpaidFeesLabel);
        panel2.add(unpaidFeesLabel);
        panel2.add(unpaidFeesField);

        // Panel 3: Form for Paying Student Fees
        JLabel paymentLabel = new JLabel("Payment Fees Form");
        paymentLabel.setBounds(490, 270, 200, 30);
        paymentLabel.setFont(new Font("Arial", Font.BOLD, 20));
        paymentLabel.setForeground(Color.decode("#de0f3f"));
        JPanel panel3 = new JPanel();
        panel3.setBounds(490, 310, 450, 180);
        panel3.setLayout(new GridLayout(3, 2, 5, 10));
        panel3.setOpaque(true);

        // Create a label for the Student ID
        JLabel studentIDLabel2 = new JLabel("Student ID:");
        styleLabel(studentIDLabel2);
        panel3.add(studentIDLabel2);
        styleTextField(studentIDField2);
        panel3.add(studentIDField2);

        // Create a label for the payment amount
        JLabel paymentAmountLabel = new JLabel("Payment Amount (MYR):");
        styleLabel(paymentAmountLabel);
        panel3.add(paymentAmountLabel);
        styleTextField(paymentAmountField);
        panel3.add(paymentAmountField);

        // Create a dropdown for the payment mode
        JLabel paymentModeLabel = new JLabel("Payment Mode:");
        styleLabel(paymentModeLabel);
        panel3.add(paymentModeLabel);
        paymentModeComboBox = new JComboBox<>(new String[]{"Cash", "Online"});
        panel3.add(paymentModeComboBox);

        // Create a button for searching student records
        findStudentButton = new JButton("Find Student");
        findStudentButton.setBounds(20, 530, 150, 30);
        styleButton(findStudentButton);
        findStudentButton.addActionListener(e -> searchStudentRecord());

        // Create a button for paying student fees
        payButton = new JButton("Pay Fees");
        payButton.setBounds(500, 530, 150, 30);
        styleButton(payButton);
        payButton.addActionListener(e -> pay_updateStudentFees());

        // Create a button 'Update' to update refresh student panel
        updateButton = new JButton("Update");
        updateButton.setBounds(670, 530, 150, 30);
        styleButton(updateButton);
        updateButton.addActionListener(e -> updateStudentPanel());

        // Add the panels to the student panel
        studentPanel.add(updateButton);
        studentPanel.add(payButton);
        studentPanel.add(findStudentButton);
        studentPanel.add(panel3);
        studentPanel.add(paymentLabel);
        studentPanel.add(panel2);
        studentPanel.add(searchLabel);
        studentPanel.add(panel1);
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

    // Fetch and display the student records in the table
    // Method to fetch student records from the database (DSAstore) table (student_fees)
    // The database table have the following columns: student_id, name, grade, paid_fees, unpaid_fees**
    // populate the data into the table.
    // Store the fetched data in an appropriate data structure; for future DSA implementation
    private void fetchStudentRecords() {
        try {
            // Establish a connection to the database
            connection = DriverManager.getConnection(url, DBusername, DBpassword);
            statement = connection.createStatement();

            // Fetch the student records from the database
            String query = "SELECT * FROM student_fees";
            resultSet = statement.executeQuery(query);

            // Clear the table before populating it with the updated data
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0);

            // Populate the table with the fetched data
            while (resultSet.next()) {
                String studentID = resultSet.getString("student_id");
                String name = resultSet.getString("name");
                String grade = resultSet.getString("grade");
                double paidFees = resultSet.getDouble("paid_fees");
                //Calculate and add to the table in the panel unpaid fees = fees - paid fees
                double unpaidFees = resultSet.getDouble("fees") - paidFees;

                model.addRow(new Object[]{studentID, name, grade, paidFees, unpaidFees});
            }
        } catch (SQLException e) {
            e.printStackTrace();
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



    // Method to search for a student record in the stored data structure and display the record in the TextFields
    // Use Search Algorithm; DSA implementation
    private void searchStudentRecord() {
        try {
            String searchID = studentIDField.getText().trim();
            DefaultTableModel model = (DefaultTableModel) table.getModel();

            for (int i = 0; i < model.getRowCount(); i++) {
                String studentID = model.getValueAt(i, 0).toString();
                if (studentID.equals(searchID)) {
                    studentNameField.setText(model.getValueAt(i, 1).toString());
                    studentGradeField.setText(model.getValueAt(i, 2).toString());
                    unpaidFeesField.setText(model.getValueAt(i, 4).toString());
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Create a method to pay student fees and update the student record in the database (DSAstore) table (student_fees)
    // The database table have the following columns: student_id, name, grade, paid_fees, unpaid_fees
    // Update the student record in the database

    private void pay_updateStudentFees() {
        try {
            // Establish a connection to the database
            connection = DriverManager.getConnection(url, DBusername, DBpassword);
            statement = connection.createStatement();

            // Fetch the student records from the database
            String studentID = studentIDField2.getText().trim();
            String paymentAmountText = paymentAmountField.getText().trim();
            if (studentID.isEmpty() || paymentAmountText.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Student ID and Payment Amount cannot be empty!");
                return;
            }

            double paymentAmount = Double.parseDouble(paymentAmountText);
            String paymentMode = paymentModeComboBox.getSelectedItem().toString();

            // Fetch the student record from the database
            String query = "SELECT * FROM student_fees WHERE student_id = '" + studentID + "'";
            resultSet = statement.executeQuery(query);

            // Update the student record in the database
            if (resultSet.next()) {
                double unpaidFees = resultSet.getDouble("unpaid_fees");
                double paidFees = resultSet.getDouble("paid_fees");

                if (paymentAmount <= unpaidFees) {
                    unpaidFees -= paymentAmount;
                    paidFees += paymentAmount;

                    // Update the student record in the database
                    String updateQuery = "UPDATE student_fees SET paid_fees = "
                            + paidFees + ", unpaid_fees = "
                            + unpaidFees
                            + " WHERE student_id = '"
                            + studentID + "'";
                    statement.executeUpdate(updateQuery);
                    JOptionPane.showMessageDialog(null, "PAID AT THE COUNTER SUCCESSFULLY! Unpaid fees: " + unpaidFees);
                } else {
                    JOptionPane.showMessageDialog(null, "Payment amount exceeds unpaid fees!");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Student ID not found!");
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



    // Method to update the student panel
    private void updateStudentPanel() {
        // Clear the text fields
        studentIDField.setText("");
        studentNameField.setText("");
        studentGradeField.setText("");
        unpaidFeesField.setText("");
        studentIDField2.setText("");
        paymentAmountField.setText("");
        paymentModeComboBox.setSelectedIndex(0);

        // Fetch and display the updated student records
        fetchStudentRecords();
    }


    // Returns the student panel
    public JPanel getPanel() {
        return studentPanel;
    }
}
