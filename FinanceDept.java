package org.schoolFinancialSystem;

// Importing required packages
import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.LinkedList;
import java.util.Queue;
import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;

public class FinanceDept extends JFrame {
    // Declare Queue
    private Queue<Transaction> transactionQueue = new LinkedList<>();

    // Textfields for financial statistics
    private final JTextField revenueField, totalSpentField, totalIncomeField;
    // Buttons for financial statistics
    private final JButton calculateButton, refreshButton;

    // Connection to the database
    private final static String DBusername = "DSAstore";
    private final static String DBpassword = "DSAstore123";
    private final static String url = "jdbc:mysql://localhost:3306/DSAstore";

    // Tab Panels function declaration
    private JPanel homePanel, studentPanel, stuffPanel, disbursementPanel;

    // HomePanel components for table and textfields
    private JTable studentFinanceTable;

    // A method for general label styling
    private void styleLabel(JLabel label) {
        label.setFont(new Font("Arial", Font.BOLD, 14));
        label.setForeground(Color.decode("#0f3fde"));
    }

    private JButton addChartButton;

    // Constructor
    public FinanceDept() {
        // Set the frame properties
        setTitle("School Finance");
        setSize(1000, 800);
        setBackground(Color.WHITE);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        // Initialize the Queue
        transactionQueue = new LinkedList<>();

        // Create the top panel (tabPanel) to hold the title and tabbedPane
        JPanel topPanel = new JPanel();
        topPanel.setBounds(0, 0, 1000, 50);
        topPanel.setBackground(Color.decode("#de0f3f"));
        topPanel.setLayout(null);

        // Create the title label
        JLabel titleLabel = new JLabel("School Finance Department");
        titleLabel.setBounds(10, 10, 300, 30);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        topPanel.add(titleLabel);

        // Create the tabbedPane
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setBounds(0, 50, 1000, 650);

        // Home Panel
        homePanel = new JPanel();
        homePanel.setLayout(null);
        homePanel.setOpaque(false);
        tabbedPane.addTab("Home", homePanel);

        // SubPanel1: Table section
        JPanel subPanel1 = new JPanel();
        subPanel1.setBounds(10, 10, 970, 300);
        subPanel1.setLayout(null);
        subPanel1.setOpaque(false);
        homePanel.add(subPanel1);

        // Create a label "Financial Status" for the table heading
        JLabel financialStatusLabel = new JLabel("Student Finance Status");
        financialStatusLabel.setBounds(10, 10, 300, 30);
        financialStatusLabel.setFont(new Font("Arial", Font.BOLD, 20));
        financialStatusLabel.setForeground(Color.decode("#de0f3f"));
        subPanel1.add(financialStatusLabel);

        // Create a scrollable table
        String[] columnNames = {"Student ID", "Student Name", "Complete", "Incomplete", "No Progress"};
        DefaultTableModel studentFinanceModel = new DefaultTableModel(columnNames, 0);
        studentFinanceTable = new JTable(studentFinanceModel);
        studentFinanceTable.setRowHeight(25);
        studentFinanceTable.setGridColor(Color.decode("#de0f3f"));
        subPanel1.add(studentFinanceTable);

        JScrollPane studentFinanceScrollPane = new JScrollPane(studentFinanceTable);
        studentFinanceScrollPane.setBounds(10, 50, 950, 200);
        subPanel1.add(studentFinanceScrollPane);

        // Add Update button
        JButton updateButton = new JButton("Update");
        styleButton(updateButton);
        updateButton.setBounds(10, 260, 150, 45);
        // Add an action listener to the update button
        updateButton.addActionListener(e -> {
            updateStudentFinanceTable();
        });
        subPanel1.add(updateButton);

        // SubPanel2: Financial statistics section
        JPanel subPanel2 = new JPanel();
        subPanel2.setBounds(10, 340, 970, 300);
        subPanel2.setLayout(null);
        subPanel2.setOpaque(true);
        subPanel2.setBackground(Color.WHITE);

        homePanel.add(subPanel2);

        // Title for Financial Statistics
        JLabel statisticsTitleLabel = new JLabel("Financial Statistics");
        statisticsTitleLabel.setBounds(500, 10, 350, 30);
        statisticsTitleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        statisticsTitleLabel.setForeground(Color.decode("#de0f3f"));
        subPanel2.add(statisticsTitleLabel);

        // Add display boxes with labels
        JLabel revenueLabel = new JLabel("Revenue:");
        styleLabel(revenueLabel);
        revenueLabel.setBounds(500, 50, 100, 50);
        subPanel2.add(revenueLabel);
        revenueField = new JTextField();
        revenueField.setBounds(680, 50, 250, 50);
        subPanel2.add(revenueField);

        JLabel totalSpentLabel = new JLabel("Total Spent:");
        styleLabel(totalSpentLabel);
        totalSpentLabel.setBounds(500, 100, 100, 50);
        subPanel2.add(totalSpentLabel);
        totalSpentField = new JTextField();
        totalSpentField.setBounds(680, 100, 250, 50);
        subPanel2.add(totalSpentField);

        JLabel totalIncomeLabel = new JLabel("Total Income:");
        styleLabel(totalIncomeLabel);
        totalIncomeLabel.setBounds(500, 150, 100, 50);
        subPanel2.add(totalIncomeLabel);
        totalIncomeField = new JTextField();
        totalIncomeField.setBounds(680, 150, 250, 50);
        subPanel2.add(totalIncomeField);

        // Add buttons

        // Add a button to add statistical chart
        addChartButton = new JButton("Add Chart");
        styleButton(addChartButton);
        addChartButton.setBounds(510, 210, 130, 40);
        subPanel2.add(addChartButton);
        // for pie chart component
        StatisticChart statisticChart = new StatisticChart();
        JPanel chartPanel = statisticChart.createChartPanel();
        chartPanel.setBounds(10, 10, 400, 290);
        // Add an action listener to the addChartButton
        addChartButton.addActionListener(e -> {
            // Add the chart panel to the subPanel2
            subPanel2.add(chartPanel);
        });

        calculateButton = new JButton("Calculate");
        styleButton(calculateButton);
        calculateButton.setBounds(660, 210, 130, 40);
        subPanel2.add(calculateButton);
        // Add an action listener to the calculate button
        calculateButton.addActionListener(e -> {
            calculateFinancialStatistics();
        });

        refreshButton = new JButton("Refresh");
        styleButton(refreshButton);
        refreshButton.setBounds(810, 210, 130, 40);
        subPanel2.add(refreshButton);
        // Add an action listener to the refresh button
        refreshButton.addActionListener(e -> {
            refreshFinancialStatistics();
        });

        // A footer panel
        JPanel footerPanel = new JPanel();
        footerPanel.setBounds(0, 710, 1000, 50);
        footerPanel.setBackground(Color.decode("#de0f3f"));

        // Create a label for the footer
        JLabel footerLabel = new JLabel("MIKS ACADEMY");
        footerLabel.setBounds(10, 10, 100, 30);
        footerLabel.setHorizontalTextPosition(SwingConstants.CENTER);
        footerLabel.setVerticalTextPosition(SwingConstants.CENTER);
        footerLabel.setForeground(Color.WHITE);
        footerPanel.add(footerLabel);

        JButton logoutButton = new JButton("Logout");
        logoutButton.setBounds(10, 210, 100, 40);
        logoutButton.setFont(new Font("Arial", Font.BOLD, 16));
        footerPanel.add(logoutButton);
        // Add an action listener to the logout button
        logoutButton.addActionListener(e -> {
            // Close the current frame
            this.dispose();
            // Open the login frame
            Login login = new Login();
            login.setVisible(true);
        });

        // Add tabs to the tabbedPane
        // Add the student tab
        StudentTab studentTab = new StudentTab();
        tabbedPane.addTab("Student", studentTab.getPanel());
        // Add the employee tab
        EmployeeTab employeeTab = new EmployeeTab();
        tabbedPane.addTab("Employee", employeeTab.getPanel());
        // Add the disbursement tab
        DisbursementTab disbursementTab = new DisbursementTab();
        tabbedPane.addTab("Disbursement", disbursementTab.getPanel());

        // Add panels to the frame
        add(footerPanel);
        add(tabbedPane);
        add(topPanel);
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

    // Method to update the studentFinanceTable
    private void updateStudentFinanceTable() {
        // Check if the table is initialized
        if (studentFinanceTable == null || studentFinanceTable.getModel() == null) {
            System.err.println("Error: Table or model is not initialized.");
            return;
        }
        // Get the table model
        DefaultTableModel model = (DefaultTableModel) studentFinanceTable.getModel();
        // Clear the table
        model.setRowCount(0);

        // Connect to the database and fetch data
        try (Connection connection = DriverManager.getConnection(url, DBusername, DBpassword);
             Statement statement = connection.createStatement()) {

            // Query the database
            String query = "SELECT student_id, name, fees, paid_fees, unpaid_fees FROM student_fees";
            ResultSet resultSet = statement.executeQuery(query);

            // Process the result set
            while (resultSet.next()) {
                String studentID = resultSet.getString("student_id");
                String studentName = resultSet.getString("name");
                int fees = resultSet.getInt("fees");
                int paidFees = resultSet.getInt("paid_fees");
                int unpaidFees = resultSet.getInt("unpaid_fees");

                // Calculate progress statuses
                String noProgress = (paidFees <= 0.05 * fees) ? "Yes" : "No";
                String complete = (paidFees >= fees) ? "Yes" : "No";
                String incomplete = (paidFees < fees && paidFees > 0.05 * fees) ? "Yes" : "No";

                // Add a row to the table
                model.addRow(new Object[]{studentID, studentName, complete, incomplete, noProgress});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to calculate the financial statistics
    private void calculateFinancialStatistics() {
        // Connect to the database and fetch data
        try (Connection connection = DriverManager.getConnection(url, DBusername, DBpassword);
             Statement statement = connection.createStatement()) {

            // Query the database
            String query = "SELECT SUM(fees) AS totalRevenue, SUM(paid_fees) AS totalIncome FROM student_fees";
            ResultSet resultSet = statement.executeQuery(query);

            // Process the result set
            if (resultSet.next()) {
                int totalRevenue = resultSet.getInt("totalRevenue");
                int totalIncome = resultSet.getInt("totalIncome");
                int totalSpent = totalRevenue - totalIncome;

                // Update the text fields
                revenueField.setText(String.valueOf(totalRevenue));
                totalIncomeField.setText(String.valueOf(totalIncome));
                totalSpentField.setText(String.valueOf(totalSpent));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to refresh the financial statistics
    private void refreshFinancialStatistics() {
        // Clear the text fields
        revenueField.setText("");
        totalIncomeField.setText("");
        totalSpentField.setText("");
    }

    private void createDisbursementPanel() {
        disbursementPanel = new JPanel();
    }

    // Method to process transactions in the queue
    private void processTransactions() {
        while (!transactionQueue.isEmpty()) {
            Transaction transaction = transactionQueue.poll();
            // Process the transaction (e.g., update the database)
            processTransaction(transaction);
        }
    }

    // Method to process a single transaction
    private void processTransaction(Transaction transaction) {
        try (Connection connection = DriverManager.getConnection(url, DBusername, DBpassword);
             PreparedStatement pst = connection.prepareStatement(
                     "UPDATE student_fees SET fees = ?, paid_fees = ? WHERE student_id = ?")) {

            pst.setInt(1, transaction.getFees());
            pst.setInt(2, transaction.getPaidFees());
            pst.setInt(3, transaction.getStudentId());
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}