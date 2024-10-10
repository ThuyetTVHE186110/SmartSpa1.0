/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import static dal.DBContext.getConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Account;
import model.Person;

/**
 *
 * @author PC
 */
public class AccountDAO {

//    // Existing method to get account by username and password
//    public Account getByUsernamePassword(String username, String password) {
//        try {
//            String sql = "SELECT * FROM Account WHERE Username = ? AND Password = ?";
//            PreparedStatement ps = getConnection().prepareStatement(sql);
//            ps.setString(1, username);
//            ps.setString(2, password);
//            ResultSet rs = ps.executeQuery();
//            if (rs.next()) {
//                String role = rs.getString("Role"); // Lấy giá trị role
//                System.out.println("Retrieved role: " + role); // In ra giá trị role để kiểm tra
//                return new Account(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), null);
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return null;
//    }
    // Existing method to get account by username and password
    public Account getByUsernamePassword(String username, String password) {
        try {
            String sql = "SELECT * FROM Account WHERE Username = ? AND Password = ?";
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Account(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), null);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    // Method to update the password for an account
    public boolean updatePassword(String username, String newPassword) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        boolean isUpdated = false;

        try {
            connection = getConnection(); // Use existing method to get connection
            String sql = "UPDATE Account SET Password = ? WHERE Username = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, newPassword); // Set new password
            preparedStatement.setString(2, username);    // Set the username

            int rowsAffected = preparedStatement.executeUpdate();
            isUpdated = (rowsAffected > 0); // Return true if rows were updated

        } catch (SQLException e) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            // Close resources
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, e);
            }
        }

        return isUpdated;
    }

    public Person getPersonByID(String name) {
        PreparedStatement stm = null;
        ResultSet rs = null;

        try (Connection connection = getConnection()) { // Use getConnection from DBContext
            String query = "select * from Person where ID = ?";
            stm = connection.prepareStatement(query);
            stm.setString(1, name);
            rs = stm.executeQuery();

            if (rs.next()) {
                Person person = new Person();
                person.setId(rs.getInt(1));
                person.setName(rs.getString(2));
                person.setDateOfBirth(rs.getDate(3));
                // Lấy giá trị của cột gender (kiểu CHAR)
                String gender = rs.getString("gender");
                if (gender != null && gender.length() > 0) {
                    person.setGender(gender.charAt(0));  // Lấy ký tự đầu tiên
                }
                person.setPhone(rs.getString(5));
                person.setEmail(rs.getString(6));
                person.setAddress(rs.getString(7));
                return person;
            }
        } catch (SQLException e) {
            System.out.println("Error while retrieving persons: " + e.getMessage());
        } finally {
            // Close the ResultSet and PreparedStatement if they are not null
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stm != null) {
                    stm.close();
                }
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e.getMessage());
            }
        }
        return null;
    }

    private Connection getConnection() throws SQLException {
        return DBContext.getConnection(); // Use your DBContext to get the connection
    }

}
