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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Account;
import model.Person;

/**
 *
 * @author PC
 */
public class AccountDAO {

    public Account getByUsernamePassword(String username, String password) {
        Account account = null;
        Person person = null;

        String sql = "SELECT a.ID, a.Username, a.Password, a.RoleID, "
                + "p.ID, p.Name, p.DateOfBirth, p.Gender, p.Phone, p.Email, p.Address "
                + "FROM Account a "
                + "JOIN Person p ON a.PersonID = p.ID "
                + "WHERE a.Username = ? AND a.Password = ?";

        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            // Set the parameters for the prepared statement
            ps.setString(1, username);
            ps.setString(2, password);

            // Execute the query
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // Create a Person object from the ResultSet
                    person = new Person();
                    person.setId(rs.getInt(5));  // Person.ID
                    person.setName(rs.getString(6));  // Person.Name
                    person.setDateOfBirth(rs.getDate(7));  // Person.DateOfBirth

                    // Check Gender safely (to avoid StringIndexOutOfBoundsException)
                    String genderStr = rs.getString(8);
                    char gender = (genderStr != null && !genderStr.isEmpty()) ? genderStr.charAt(0) : 'U';  // 'U' for unknown
                    person.setGender(gender);

                    person.setPhone(rs.getString(9));  // Person.Phone
                    person.setEmail(rs.getString(10));  // Person.Email
                    person.setAddress(rs.getString(11));  // Person.Address

                    // Create an Account object from the ResultSet
                    account = new Account(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), person);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Print stack trace for debugging
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, "Database error", e);
        }
        return account;  // Return null if no account is found
    }

    public boolean updatePassword(String email, String newPassword) throws SQLException {
        String sql = "UPDATE Account SET Password = ? WHERE Username = ?";
        try (Connection conn = DBContext.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newPassword);
            stmt.setString(2, email);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
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

    public boolean checkEmailExists(String email) {
        String query = "SELECT COUNT(*) FROM Account WHERE Username = ?";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0; // Trả về true nếu email tồn tại
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Trả về false nếu email không tồn tại hoặc có lỗi xảy ra
    }

    public void insertAccount(Connection conn, Account account) throws SQLException {
        String sql = "INSERT INTO Account (Username, Password, RoleID, PersonID) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, account.getUsername());
            stmt.setString(2, account.getPassword());
            stmt.setInt(3, account.getRole());
            stmt.setInt(4, account.getId());
            stmt.executeUpdate();
        }
    }

    public List<Account> getAllStaffAccounts() {
        List<Account> accounts = new ArrayList<>();

        // SQL truy vấn để lấy thông tin tài khoản, bao gồm tên, username, và tên vai trò
        String sql = "SELECT p.Name AS personName, p.Email, a.Username, r.Name AS roleName "
                + "FROM Account a "
                + "JOIN Person p ON a.PersonID = p.ID "
                + "JOIN Role r ON a.RoleID = r.ID "
                + "WHERE a.RoleID IN (1, 2, 3)"; // Lọc chỉ lấy roleID 1 (Admin), 2 (Manager), và 3 (Staff)

        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                // Tạo đối tượng Person
                Person person = new Person();
                person.setName(rs.getString("personName"));  // Lấy tên người từ cột personName
                person.setEmail(rs.getString("Email"));  // Lấy email của người

                // Tạo đối tượng Account
                Account account = new Account();
                account.setUsername(rs.getString("Username"));  // Lấy username
                account.setPersonInfo(person);  // Gán đối tượng Person vào Account

                // Lấy tên role và gán vào Account
                String roleName = rs.getString("roleName");  // Lấy tên vai trò từ bảng Role
                account.setRoleName(roleName);  // Gán roleName vào account

                // Thêm tài khoản vào danh sách
                accounts.add(account);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return accounts;  // Trả về danh sách các tài khoản
    }

    private Connection getConnection() throws SQLException {
        return DBContext.getConnection(); // Use your DBContext to get the connection
    }

}
