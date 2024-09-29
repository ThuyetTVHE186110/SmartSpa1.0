/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Feedback;

/**
 *
 * @author Asus
 */
public class DBContext {
    private static final String USER = "sa";
    private static final String PASS = "123456";
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=SmartBeautySpaa";
    private static final String DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";

    static {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, "JDBC Driver not found", ex);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    public static void main(String[] args) {
        try (Connection connection = DBContext.getConnection()) {
            if (connection != null) {
                System.out.println("Connected to the database!");
            }
             FeedbackDAO o= new FeedbackDAO();
        ArrayList<Feedback> f= o.getFeedback();
        for (Feedback feedback : f) {
            System.out.println(feedback.getContent());
        }
    
        } catch (SQLException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, "SQL Error", ex);
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }
}
