package org.schoolFinancialSystem;

// Importing required packages
import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.*;

// Login class
public class Login extends JFrame {

    // global components
    private final JTextField emailField;
    private final JPasswordField passwordField;
    private final JComboBox<String> roleComboBox;

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

    // Constructor
    public Login(){
    // Set the frame properties
    setTitle("User Verification");
    setSize(700, 420);

    setResizable(false);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    setLayout(null);

    //Create panel1: for title
    JPanel panel1 = new JPanel();
    panel1.setBounds(0, 0, 700, 50);
    panel1.setBackground(Color.decode("#de0f3f"));
    //Create title
    JLabel title = new JLabel("Finance Department");
    title.setHorizontalTextPosition(SwingConstants.LEFT);
    title.setFont(new Font("Arial", Font.BOLD, 30));
    title.setForeground(Color.WHITE);
    panel1.add(title);
    //Create login title label
    JLabel loginTitle = new JLabel("LOGIN");
    loginTitle.setHorizontalTextPosition(SwingConstants.RIGHT);
    loginTitle.setFont(new Font("Arial", Font.BOLD, 20));
    loginTitle.setForeground(Color.BLACK);
    panel1.add(loginTitle);

    //Create panel2: for image
    JPanel panel2 = new JPanel();
    panel2.setBounds(30, 100, 150, 150);
    panel1.setOpaque(true);
    //Get image and scale it to fit to the panel
    ImageIcon image = new ImageIcon("src/main/java/org/schoolFinancialSystem/logo2.0.png");
    Image img = image.getImage();
    Image newImg = img.getScaledInstance(panel2.getWidth(), panel2.getHeight(), Image.SCALE_SMOOTH);
    image = new ImageIcon(newImg);
    JLabel imageLabel = new JLabel("", image, JLabel.CENTER);
    panel2.add(imageLabel);

    //Create panel3: for login form
    JPanel panel3 = new JPanel();
    panel3.setBounds(300, 100, 300, 110);
    panel3.setOpaque(true);
    panel3.setLayout(new GridLayout(3, 2, 1, 10));

    //Initialize the components
    emailField = new JTextField();
    passwordField = new JPasswordField();
    roleComboBox = new JComboBox<>(new String[]{"Admin", "Staff"});

    //Creat form components
    JLabel eName = new JLabel("Email:");
    styleLabel(eName);
    panel3.add(eName);
    styleTextField(emailField);
    panel3.add(emailField);

    JLabel pName = new JLabel("Password:");
    styleLabel(pName);
    panel3.add(pName);
    styleTextField(passwordField);
    panel3.add(passwordField);

    JLabel rName = new JLabel("Role:");
    styleLabel(rName);
    panel3.add(rName);
    panel3.add(roleComboBox);

    //Create login button
    JButton loginButton = new JButton("LOGIN");
    styleButton(loginButton);
    loginButton.setBounds(300, 280, 150, 50);

    //Add a quit button to panel3
    JButton quitButton = new JButton("QUIT");
    quitButton.setBounds(470, 280, 150, 50);
    quitButton.setFont(new Font("Comic Sans", Font.BOLD, 15));
    quitButton.setForeground(Color.BLACK);
    quitButton.setBackground(Color.WHITE);

    // Add action listener to the quit button, to close the application
   quitButton.addActionListener(e -> System.exit(0));

    //Create panel4: for brand title
    JPanel panel4 = new JPanel();
    panel4.setBounds(0, 350, 700, 50);
    panel4.setBackground(Color.decode("#de0f3f"));
    //Create brand title
    JLabel brand = new JLabel("MIKS ACADEMY");
    brand.setFont(new Font("Arial", Font.BOLD, 14));
    brand.setForeground(Color.WHITE);
    brand.setVerticalTextPosition(SwingConstants.BOTTOM);
    panel4.add(brand);

    // Add action listener to the login button, to validate the user from the database
    loginButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

            // Database credentials
            String DBusername = "DSAstore";
            String DBpassword = "DSAstore123";
            String url = "jdbc:mysql://localhost:3306/DSAstore";

            // Get the user input
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());
            String role = (String) roleComboBox.getSelectedItem();

            // Establish the connection
            try {
                // Load MySQL JDBC Driver
                Class.forName("com.mysql.cj.jdbc.Driver");

                // Establish the connection
                Connection connection = DriverManager.getConnection(url, DBusername, DBpassword);
                System.out.println("Connection established successfully!");

                // SQL query to retrieve data from the 'login' table
                String query = "SELECT * FROM login WHERE email = ? AND password = ? AND role = ?";
                PreparedStatement pst = connection.prepareStatement(query);
                pst.setString(1, email);
                pst.setString(2, password);
                pst.setString(3, role);
                ResultSet rs = pst.executeQuery();

                // Check if the user exists and provide appropriate feedback
                if (rs.next()) {
                    JOptionPane.showMessageDialog(null, "Login successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    // Load the FinanceDept class and set it visible and destroy the login window
                    loginButton.setVisible(false);
                    FinanceDept financeDept = new FinanceDept();
                    financeDept.setVisible(true);
                    // Close the login window (optional)
                    SwingUtilities.getWindowAncestor(loginButton).dispose();
                } else {
                    // Check specific errors for better feedback
                    String checkEmailQuery = "SELECT * FROM login WHERE email = ?";
                    PreparedStatement emailCheckPst = connection.prepareStatement(checkEmailQuery);
                    emailCheckPst.setString(1, email);
                    ResultSet emailCheckRs = emailCheckPst.executeQuery();

                    if (!emailCheckRs.next()) {
                        JOptionPane.showMessageDialog(null, "Invalid email address!", "Login Failed", JOptionPane.ERROR_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Incorrect password or role!", "Login Failed", JOptionPane.ERROR_MESSAGE);
                    }
                    emailCheckRs.close();
                    emailCheckPst.close();
                }

                // Close resources
                rs.close();
                pst.close();
                connection.close();
                System.out.println("Connection closed.");
            } catch (ClassNotFoundException ex) {
                System.err.println("MySQL JDBC Driver not found: " + ex.getMessage());
            } catch (SQLException ex) {
                System.err.println("SQL Error: " + ex.getMessage());
            }
        }
    });

    //Add the panels
    add(panel4);
    add(quitButton);
    add(loginButton);
    add(panel3);
    add(panel2);
    add(panel1);
    }

    // Styles a button with consistent properties
    private void styleButton(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(Color.decode("#de0f3f"));
        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
    }

}

