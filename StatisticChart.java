package org.schoolFinancialSystem;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class StatisticChart {

    public JPanel createChartPanel() {
        DefaultPieDataset dataset = fetchDataFromDatabase();

        JFreeChart chart = ChartFactory.createPieChart(
                "Statistical Chart",
                dataset,
                true, true, false);

        PiePlot plot = (PiePlot) chart.getPlot();
       plot.setSectionPaint("Total Revenue", Color.decode("#206385"));
       plot.setSectionPaint("Total Income", Color.decode("#d4046f"));
       plot.setSectionPaint("Total Spent", Color.decode("#2351e8"));
        return new ChartPanel(chart);
    }

    private DefaultPieDataset fetchDataFromDatabase() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        String url = "jdbc:mysql://localhost:3306/DSAstore";
        String DBusername = "DSAstore";
        String DBpassword = "DSAstore123";

        try (Connection connection = DriverManager.getConnection(url, DBusername, DBpassword);
             Statement statement = connection.createStatement()) {

            String query = "SELECT SUM(fees) AS totalRevenue, SUM(paid_fees) AS totalIncome FROM student_fees";
            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                int totalRevenue = resultSet.getInt("totalRevenue");
                int totalIncome = resultSet.getInt("totalIncome");
                int totalSpent = totalRevenue - totalIncome;

                dataset.setValue("Total Revenue", totalRevenue);
                dataset.setValue("Total Income", totalIncome);
                dataset.setValue("Total Spent", totalSpent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dataset;
    }
}