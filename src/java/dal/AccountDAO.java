/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
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

        String sql = "SELECT a.ID, a.Username, a.Password, a.RoleID, a.Status, "
                + "p.ID, p.Name, p.DateOfBirth, p.Gender, p.Phone, p.Email, p.Address, p.Image "
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
                    // Check account status
                    String status = rs.getString("Status");
                    if (!"Active".equalsIgnoreCase(status)) {
                        // Return null if account status is not Active
                        return null;
                    }

                    // Create a Person object from the ResultSet
                    person = new Person();
                    person.setId(rs.getInt(6));  // Person.ID
                    person.setName(rs.getString(7));  // Person.Name
                    person.setDateOfBirth(rs.getDate(8));  // Person.DateOfBirth

                    // Check Gender safely
                    String genderStr = rs.getString(9);
                    char gender = (genderStr != null && !genderStr.isEmpty()) ? genderStr.charAt(0) : 'U';
                    person.setGender(gender);

                    person.setPhone(rs.getString(10));  // Person.Phone
                    person.setEmail(rs.getString(11));  // Person.Email
                    person.setAddress(rs.getString(12));  // Person.Address
                    person.setImage(rs.getString("Image")); // Person.Image

                    // Create an Account object from the ResultSet
                    account = new Account(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), person);
                    account.setStatus(status);  // Set status in Account object
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, "Database error", e);
        }

        return account;  // Return the Account object if found and active, otherwise null
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

        // SQL query to retrieve account information including person name, username, role name, and status
        String sql = "SELECT a.ID AS accountId, p.Name AS personName, p.Email, a.Username, r.Name AS roleName, a.Status "
                + "FROM Account a "
                + "JOIN Person p ON a.PersonID = p.ID "
                + "JOIN Role r ON a.RoleID = r.ID "
                + "WHERE a.RoleID IN (1, 2, 3)"; // Only fetch roles for staff and manager

        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                // Create the Person object
                Person person = new Person();
                person.setName(rs.getString("personName"));  // Set the name of the person
                person.setEmail(rs.getString("Email"));       // Set the email of the person

                // Create the Account object
                Account account = new Account();
                account.setId(rs.getInt("accountId"));         // Set account ID
                account.setUsername(rs.getString("Username")); // Set username
                account.setPersonInfo(person);                 // Link the Person object to the Account

                // Set role name and status
                account.setRoleName(rs.getString("roleName")); // Set role name
                String status = rs.getString("Status");
                if (status == null) {
                    status = "Unknown"; // Assign a default value if status is null
                }
                account.setStatus(status);

                // Add account to the list
                accounts.add(account);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return accounts;  // Return the list of accounts
    }

    private Connection getConnection() throws SQLException {
        return DBContext.getConnection(); // Use your DBContext to get the connection
    }

    public Account getAccountById(int accountId) throws SQLException {
        String sql = "SELECT a.ID, a.Username, a.Password, a.RoleID, p.ID AS PersonID, p.Name, p.Email, p.Phone, p.Address "
                + "FROM Account a "
                + "JOIN Person p ON a.PersonID = p.ID "
                + "WHERE a.ID = ?";  // Tìm theo Account ID

        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, accountId);  // Gán giá trị accountId vào câu SQL

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // Tạo đối tượng Person
                    Person person = new Person();
                    person.setId(rs.getInt("PersonID"));
                    person.setName(rs.getString("Name"));
                    person.setEmail(rs.getString("Email"));
                    person.setPhone(rs.getString("Phone"));
                    person.setAddress(rs.getString("Address"));

                    // Tạo đối tượng Account
                    Account account = new Account();
                    account.setId(rs.getInt("ID"));  // Lấy ID của Account từ DB
                    account.setUsername(rs.getString("Username"));
                    account.setPassword(rs.getString("Password"));
                    account.setRole(rs.getInt("RoleID"));
                    account.setPersonInfo(person);  // Liên kết với đối tượng Person

                    return account;  // Trả về đối tượng Account đã hoàn thiện
                }
            }
        }
        return null;  // Nếu không tìm thấy Account theo ID
    }

    public boolean isEmailAvailable(String email) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Account WHERE Username = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) == 0;
            }
        }
        return false;
    }

    public void updateEmail(int accountId, String newEmail) throws SQLException {
        String sql = "UPDATE Account SET Username = ? WHERE ID = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newEmail);
            stmt.setInt(2, accountId);
            stmt.executeUpdate();
        }
    }

    public void updateAccountDetails(int accountId, String username, String password, String status, int roleId, String personName) throws SQLException {
        String updateAccountSQL = (password != null)
                ? "UPDATE Account SET Username = ?, Password = ?, Status = ?, RoleID = ? WHERE ID = ?"
                : "UPDATE Account SET Username = ?, Status = ?, RoleID = ? WHERE ID = ?";

        String updatePersonSQL = "UPDATE Person SET Name = ?, Email = ? WHERE ID = (SELECT PersonID FROM Account WHERE ID = ?)";

        try (Connection conn = DBContext.getConnection()) {
            conn.setAutoCommit(false);  // Start transaction

            // Update Account table
            try (PreparedStatement accountStmt = conn.prepareStatement(updateAccountSQL)) {
                int paramIndex = 1;
                accountStmt.setString(paramIndex++, username);
                if (password != null) {
                    accountStmt.setString(paramIndex++, password);
                }
                accountStmt.setString(paramIndex++, status);
                accountStmt.setInt(paramIndex++, roleId);
                accountStmt.setInt(paramIndex, accountId);
                accountStmt.executeUpdate();
            }

            // Update Person table
            try (PreparedStatement personStmt = conn.prepareStatement(updatePersonSQL)) {
                personStmt.setString(1, personName);
                personStmt.setString(2, username); // Username should sync with Email
                personStmt.setInt(3, accountId);
                personStmt.executeUpdate();
            }

            conn.commit();  // Commit transaction
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public String getCurrentPassword(int accountId) throws SQLException {
        String currentPassword = null;
        String sql = "SELECT Password FROM Account WHERE ID = ?";

        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, accountId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    currentPassword = rs.getString("Password");
                }
            }
        }
        return currentPassword;
    }

    public void addAccount(String username, String password, int roleId, String personName) throws SQLException {
        String addPersonSQL = "INSERT INTO Person (Name, Email) VALUES (?, ?)";
        String addAccountSQL = "INSERT INTO Account (Username, Password, RoleID, PersonID) VALUES (?, ?, ?, ?)";

        try (Connection conn = getConnection()) {
            conn.setAutoCommit(false);

            int personId;
            // Insert into Person table
            try (PreparedStatement personStmt = conn.prepareStatement(addPersonSQL, Statement.RETURN_GENERATED_KEYS)) {
                personStmt.setString(1, personName);
                personStmt.setString(2, username);  // Email as username
                personStmt.executeUpdate();

                // Retrieve generated Person ID
                try (ResultSet rs = personStmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        personId = rs.getInt(1);
                    } else {
                        conn.rollback();
                        throw new SQLException("Failed to insert person record");
                    }
                }
            }

            // Insert into Account table
            try (PreparedStatement accountStmt = conn.prepareStatement(addAccountSQL)) {
                accountStmt.setString(1, username);
                accountStmt.setString(2, password);
                accountStmt.setInt(3, roleId);
                accountStmt.setInt(4, personId);
                accountStmt.executeUpdate();
            }

            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public List<Account> getAccountsByStatus(String status) throws SQLException {
        List<Account> accounts = new ArrayList<>();
        String sql;

        // Define the SQL query based on the filter
        if ("All".equalsIgnoreCase(status)) {
            sql = "SELECT a.ID, a.Username, a.Status, a.RoleID, "
                    + "p.ID AS personID, p.Name, p.Email, "
                    + "r.Name AS roleName "
                    + "FROM Account a "
                    + "JOIN Person p ON a.PersonID = p.ID "
                    + "JOIN Role r ON a.RoleID = r.ID";
        } else {
            sql = "SELECT a.ID, a.Username, a.Status, a.RoleID, "
                    + "p.ID AS personID, p.Name, p.Email, "
                    + "r.Name AS roleName "
                    + "FROM Account a "
                    + "JOIN Person p ON a.PersonID = p.ID "
                    + "JOIN Role r ON a.RoleID = r.ID "
                    + "WHERE a.Status = ?";
        }

        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            // Set the status parameter if it's not "All"
            if (!"All".equalsIgnoreCase(status)) {
                ps.setString(1, status);
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Person person = new Person();
                    person.setId(rs.getInt("personID"));
                    person.setName(rs.getString("Name"));
                    person.setEmail(rs.getString("Email"));

                    Account account = new Account();
                    account.setId(rs.getInt("ID"));
                    account.setUsername(rs.getString("Username"));
                    account.setStatus(rs.getString("Status"));
                    account.setRole(rs.getInt("RoleID"));
                    account.setRoleName(rs.getString("roleName"));
                    account.setPersonInfo(person);

                    accounts.add(account);
                }
            }
        }

        return accounts;
    }

    public int getTotalStaffAccounts() {
        String sql = "SELECT COUNT(*) FROM Account a JOIN Role r ON a.RoleID = r.ID WHERE a.RoleID IN (2, 3)";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getTotalAccountsCount() throws SQLException {
        String sql = "SELECT COUNT(*) AS total FROM Account WHERE RoleID IN (1, 2, 3)";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("total");
            }
        }
        return 0;
    }

    // Get paginated list of staff accounts
    public List<Account> getPaginatedStaffAccounts(int page, int recordsPerPage) throws SQLException {
        List<Account> accounts = new ArrayList<>();
        int offset = (page - 1) * recordsPerPage;

        String sql = """
        SELECT a.ID AS AccountID, a.Username, a.Status, r.Name AS RoleName,
               p.ID AS PersonID, p.Name AS PersonName, p.Email, p.Phone, p.Address, p.Image
        FROM Account a
        JOIN Person p ON a.PersonID = p.ID
        JOIN Role r ON a.RoleID = r.ID
        WHERE a.RoleID IN (1, 2, 3)
        ORDER BY p.Name ASC
        OFFSET ? ROWS FETCH NEXT ? ROWS ONLY
        """;

        try (Connection conn = DBContext.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, offset);
            stmt.setInt(2, recordsPerPage);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Person person = new Person();
                    person.setId(rs.getInt("PersonID"));
                    person.setName(rs.getString("PersonName"));
                    person.setEmail(rs.getString("Email"));
                    person.setPhone(rs.getString("Phone"));
                    person.setAddress(rs.getString("Address"));
                    person.setImage(rs.getString("Image"));

                    Account account = new Account();
                    account.setId(rs.getInt("AccountID"));
                    account.setUsername(rs.getString("Username"));
                    account.setStatus(rs.getString("Status"));
                    account.setRoleName(rs.getString("RoleName"));
                    account.setPersonInfo(person);

                    accounts.add(account);
                }
            }
        }
        return accounts;
    }

    public List<Account> getFilteredAccounts(String statusFilter, String roleFilter) throws SQLException {
        List<Account> accounts = new ArrayList<>();

        // Base SQL query with dynamic WHERE clause
        StringBuilder sql = new StringBuilder("""
        SELECT a.ID AS AccountID, a.Username, a.Status, r.Name AS RoleName,
               p.ID AS PersonID, p.Name AS PersonName, p.Email, p.Phone, p.Address, p.Image
        FROM Account a
        JOIN Person p ON a.PersonID = p.ID
        JOIN Role r ON a.RoleID = r.ID
        WHERE a.RoleID IN (1, 2, 3)
    """);

        boolean hasCondition = false;

        if (statusFilter != null && !statusFilter.equalsIgnoreCase("all")) {
            sql.append(" AND a.Status = ?");
            hasCondition = true;
        }

        if (roleFilter != null && !roleFilter.equalsIgnoreCase("all")) {
            sql.append(" AND r.Name = ?");
            hasCondition = true;
        }

        sql.append(" ORDER BY p.Name ASC");

        try (Connection conn = DBContext.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql.toString())) {

            int paramIndex = 1;
            if (statusFilter != null && !statusFilter.equalsIgnoreCase("all")) {
                stmt.setString(paramIndex++, statusFilter);
            }

            if (roleFilter != null && !roleFilter.equalsIgnoreCase("all")) {
                stmt.setString(paramIndex++, roleFilter);
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Person person = new Person();
                    person.setId(rs.getInt("PersonID"));
                    person.setName(rs.getString("PersonName"));
                    person.setEmail(rs.getString("Email"));
                    person.setPhone(rs.getString("Phone"));
                    person.setAddress(rs.getString("Address"));
                    person.setImage(rs.getString("Image"));

                    Account account = new Account();
                    account.setId(rs.getInt("AccountID"));
                    account.setUsername(rs.getString("Username"));
                    account.setStatus(rs.getString("Status"));
                    account.setRoleName(rs.getString("RoleName"));
                    account.setPersonInfo(person);

                    accounts.add(account);
                }
            }
        }
        return accounts;
    }

}
