package org.schoolFinancialSystem;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;

public class DisbursementTab {
    private JPanel disbursementPanel;

    // Connection to the database
    private final String DBusername = "DSAstore";
    private final String DBpassword = "DSAstore123";
    private final String url = "jdbc:mysql://localhost:3306/DSAstore";

    // initialize the global variables
    private static Connection connection = null;
    private static Statement statement = null;
    private static ResultSet resultSet = null;

    //GUI components global variables
    private JTextField payOutIDField;
    private JTextField payOutRecipientField;
    private JTextField recipientNameField2;
    private JTextField purposeField2;
    private JTextField payOutPurposeField;
    private JTextField payOutAmountField;
    private JTextField payOutDateField;
    private JTextField payOutIDField2;
    private JTextField payOutAmountField2;
    private JComboBox<String> paymentModeComboBox;
    private JTable table;
    private JButton findRecordButton;
    private JButton createPayOutButton;
    private JButton updateButton;

    public DisbursementTab() {
        createDisbursementPanel(); // Initialize the disbursement panel
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

    // Method to create the disbursement panel
    private void createDisbursementPanel() {
        // Initialize components
        payOutIDField = new JTextField();
        payOutRecipientField = new JTextField();
        payOutPurposeField = new JTextField();
        payOutAmountField = new JTextField();
        payOutDateField = new JTextField();
        payOutIDField2 = new JTextField();
        payOutAmountField2 = new JTextField();
        paymentModeComboBox = new JComboBox<>();
        findRecordButton = new JButton();
        createPayOutButton = new JButton();
        updateButton = new JButton();

        // Initialize tab panel for disbursement
        disbursementPanel = new JPanel();
        disbursementPanel.setLayout(null);
        disbursementPanel.setOpaque(true);

        // Panel 1: Table for Pay-Out Records
        JPanel panel1 = new JPanel();
        panel1.setBounds(20, 20, 960, 250); // Top panel occupies 50% in y-axis
        panel1.setLayout(new BorderLayout());
        panel1.setOpaque(false);

        JLabel tableLabel = new JLabel("Pay-Out Records", JLabel.CENTER);
        tableLabel.setFont(new Font("Arial", Font.BOLD, 20));
        tableLabel.setForeground(Color.decode("#de0f3f"));
        panel1.add(tableLabel, BorderLayout.NORTH);

        String[] columnNames = {"Disbursement ID", "Recipient", "Amount", "Payment Mode", "Remarks"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        table.setRowHeight(25);
        table.setGridColor(Color.decode("#de0f3f"));
        JScrollPane scrollPane = new JScrollPane(table);
        panel1.add(scrollPane, BorderLayout.CENTER);
        // Fetch the disbursement records from the database and display them in the table
        fetchDisbursementRecords();

        // Panel 2: Searching Pay-Out Records
        // Create a label for the search Employee records form
        JLabel searchLabel = new JLabel("Search Pay-Out Records");
        searchLabel.setBounds(20, 280, 300, 30);
        searchLabel.setFont(new Font("Arial", Font.BOLD, 20));
        searchLabel.setForeground(Color.decode("#de0f3f"));

        // Panel for Pay-Out search fields; placed under the label
        JPanel panel2 = new JPanel();
        panel2.setBounds(20, 320, 450, 220);
        panel2.setLayout(new GridLayout(5, 2, 10, 10));
        panel2.setOpaque(true);

        // Pay-Out ID; payOut
        JLabel payOutIDLabel = new JLabel("Pay-Out ID:");
        styleLabel(payOutIDLabel);
        panel2.add(payOutIDLabel);
        payOutIDField = new JTextField();
        styleTextField(payOutIDField);
        panel2.add(payOutIDField);

        // Pay-Out Recipient Name
        JLabel payOutRecipientLabel = new JLabel("Recipient Name:");
        styleLabel(payOutRecipientLabel);
        panel2.add(payOutRecipientLabel);
        payOutRecipientField = new JTextField();
        panel2.add(payOutRecipientField);

        // Pay-Out Role
        JLabel payOutPurposeLabel = new JLabel("Purpose:");
        styleLabel(payOutPurposeLabel);
        panel2.add(payOutPurposeLabel);
        payOutPurposeField = new JTextField();
        panel2.add(payOutPurposeField);

        // Pay-Out Amount
        JLabel payOutAmountLabel = new JLabel("Pay-Out Amount (MYR):");
        styleLabel(payOutAmountLabel);
        panel2.add(payOutAmountLabel);
        payOutAmountField = new JTextField();
        panel2.add(payOutAmountField);

        // Pay-Out Date (YYYY-MM-DD)
        JLabel payOutDateLabel = new JLabel("Pay-Out Date (YYYY-MM-DD):");
        styleLabel(payOutDateLabel);
        panel2.add(payOutDateLabel);
        payOutDateField = new JTextField();
        panel2.add(payOutDateField);

        // Panel 3:
        // Create a label for the Create New Pay-Out form
        JLabel newPayOutLabel = new JLabel("Create NEW Pay-Out Form");
        newPayOutLabel.setBounds(510, 280, 300, 30);
        newPayOutLabel.setFont(new Font("Arial", Font.BOLD, 20));
        newPayOutLabel.setForeground(Color.decode("#de0f3f"));
        // For payout form; placed beside the panel 3
        JPanel panel3 = new JPanel();
        panel3.setBounds(510, 320, 450, 220);
        panel3.setLayout(new GridLayout(5, 2, 10, 10));
        panel3.setOpaque(true);

        // Create a label for the Student ID
        JLabel payOutIDLabel2 = new JLabel("Pay-Out ID:");
        styleLabel(payOutIDLabel2);
        panel3.add(payOutIDLabel2);
        payOutIDField2 = new JTextField();
        styleTextField(payOutIDField2);
        panel3.add(payOutIDField2);

        // Create a label for the payment amount
        JLabel payOutAmountLabel2 = new JLabel("Pay-Out Amount (MYR):");
        styleLabel(payOutAmountLabel2);
        panel3.add(payOutAmountLabel2);
        payOutAmountField2 = new JTextField();
        styleTextField(payOutAmountField2);
        panel3.add(payOutAmountField2);

        // Create a dropdown for the payment mode
        JLabel paymentModeLabel = new JLabel("Payment Mode:");
        styleLabel(paymentModeLabel);
        panel3.add(paymentModeLabel);
        paymentModeComboBox = new JComboBox<>(new String[]{"Cash", "Online"});
        panel3.add(paymentModeComboBox);

        //Create a label 'Recipient Name' and a textfield
        JLabel recipientNameLabel2 = new JLabel("Recipient Name:");
        styleLabel(recipientNameLabel2);
        panel3.add(recipientNameLabel2);
        recipientNameField2 = new JTextField();
        styleTextField(recipientNameField2);
        panel3.add(recipientNameField2);

        //Create a label 'Purpose' and a textfield
        JLabel purposeLabel2 = new JLabel("Purpose:");
        styleLabel(purposeLabel2);
        panel3.add(purposeLabel2);
        purposeField2 = new JTextField();
        styleTextField(purposeField2);
        panel3.add(purposeField2);

        // Create a button for searching records
        findRecordButton = new JButton("Find Record");
        findRecordButton.setBounds(20, 560, 150, 30);
        styleButton(findRecordButton);
        // Add an action listener to the button
        findRecordButton.addActionListener(e -> findDisbursementRecord());

        // Create a button for Creating a new Pay-Out student fees
        createPayOutButton = new JButton("Create Pay-Out");
        createPayOutButton.setBounds(500, 560, 150, 30);
        styleButton(createPayOutButton);
        // Add an action listener to the button
        createPayOutButton.addActionListener(e -> createDisbursementRecord());

        //Create a button 'Update' to refresh disbursement panel
        updateButton = new JButton("Update");
        updateButton.setBounds(670, 560, 150, 30);
        styleButton(updateButton);
        // Add an action listener to the button
        updateButton.addActionListener(e -> updateDisbursementPanel());

        //Add the panel to the student panel
        disbursementPanel.add(updateButton);
        disbursementPanel.add(createPayOutButton);
        disbursementPanel.add(findRecordButton);
        disbursementPanel.add(panel3);
        disbursementPanel.add(newPayOutLabel);
        disbursementPanel.add(panel2);
        disbursementPanel.add(searchLabel);
        disbursementPanel.add(panel1);

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

    //Create a method to fetch the disbursement records 
    public void fetchDisbursementRecords() {
        try {
            connection = DriverManager.getConnection(url, DBusername, DBpassword);
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM school_disbursement");

            //Clear the table before adding new records
            DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
            tableModel.setRowCount(0);

            //Iterate through the result set and add the records to the table
            while (resultSet.next()) {
                String payoutID = resultSet.getString("payout_id");
                String recipientName = resultSet.getString("recipient_name");
                String amount = resultSet.getString("amount");
                String remarks = resultSet.getString("remark");
                String paymentMode = resultSet.getString("payment_mode");
                tableModel.addRow(new String[]{payoutID, recipientName, amount, paymentMode, remarks});
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //Create a method to find a disbursement record by the payout_id in the JTable
    public void findDisbursementRecord() {
        try {
            //Establish a connection to the database
            connection = DriverManager.getConnection(url, DBusername, DBpassword);
            statement = connection.createStatement();

           // Get the date disbursement record from the database according to the payout_id
            String payoutID = payOutIDField.getText();
            String query = "SELECT date FROM school_disbursement WHERE payout_id = '" + payoutID + "'";
            resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                String date = resultSet.getString("date");
                payOutDateField.setText(date);
            }

            DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                if (tableModel.getValueAt(i, 0).equals(payoutID)) {
                    payOutRecipientField.setText((String) tableModel.getValueAt(i, 1));
                    payOutAmountField.setText((String) tableModel.getValueAt(i, 2));
                    payOutPurposeField.setText((String) tableModel.getValueAt(i, 4));
                    break;
                }
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    //Create a method to create a new disbursement
    public void createDisbursementRecord() {
    try {
        connection = DriverManager.getConnection(url, DBusername, DBpassword);
        String payoutID = payOutIDField2.getText();
        String amount = payOutAmountField2.getText();
        String recipientName = recipientNameField2.getText();
        String purpose = purposeField2.getText();
        String paymentMode = (String) paymentModeComboBox.getSelectedItem();
        // Get current date from the system
        String date = new Date(System.currentTimeMillis()).toString();
        String remark = "N/A";

        String query = "INSERT INTO school_disbursement (payout_id, recipient_name, sent_for, amount, remark, payment_mode, date) " +
                       "VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, payoutID);
        preparedStatement.setString(2, recipientName);
        preparedStatement.setString(3, purpose);
        preparedStatement.setString(4, amount);
        preparedStatement.setString(5, remark);
        preparedStatement.setString(6, paymentMode);
        preparedStatement.setString(7, date);
        preparedStatement.executeUpdate();

        fetchDisbursementRecords();
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
    //Create a method to update the disbursement panel
    public void updateDisbursementPanel() {
        //Clear the text fields
        payOutIDField.setText("");
        payOutRecipientField.setText("");
        payOutPurposeField.setText("");
        payOutAmountField.setText("");
        payOutDateField.setText("");
        payOutIDField2.setText("");
        payOutAmountField2.setText("");
        paymentModeComboBox.setSelectedIndex(0);
        recipientNameField2.setText("");
        purposeField2.setText("");

        //Fetch the disbursement records
        fetchDisbursementRecords();
    }

    // Returns the disbursement panel
    public JPanel getPanel() {
        return disbursementPanel;
    }
}
