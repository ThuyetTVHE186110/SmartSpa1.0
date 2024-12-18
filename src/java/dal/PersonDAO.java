// This is a personal academic project. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: https://pvs-studio.com
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Person;
import java.sql.Statement;

/**
 *
 * @author ADMIN
 */
public class PersonDAO extends DBContext {

    public List<Person> getAll() {
        List<Person> list = new ArrayList<>();
        PreparedStatement stm = null;
        ResultSet rs = null;

        try (Connection connection = getConnection()) { // Use getConnection from DBContext
            String strSelect = "SELECT * FROM Person";
            stm = connection.prepareStatement(strSelect);
            rs = stm.executeQuery();

            while (rs.next()) {
                Person person = new Person();
                person.setId(rs.getInt(1));
                person.setName(rs.getString(2));
                if (rs.getDate(3) != null) {
                    person.setDateOfBirth(rs.getDate(3));
                }
                // Lấy giá trị của cột gender (kiểu CHAR)
                String gender = rs.getString("gender");
                if (gender != null && gender.length() > 0) {
                    person.setGender(gender.charAt(0));  // Lấy ký tự đầu tiên
                }
                person.setPhone(rs.getString(5));
                person.setEmail(rs.getString(6));
                person.setAddress(rs.getString(7));
                list.add(person);
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

        return list;
    }

    public Person getPersonByID(int id) {
        PreparedStatement stm = null;
        ResultSet rs = null;

        try (Connection connection = getConnection()) { // Use getConnection from DBContext
            String query = "select * from Person where id = ?";
            stm = connection.prepareStatement(query);
            stm.setInt(1, id);
            rs = stm.executeQuery();

            if (rs.next()) {
                Person person = new Person();
                person.setId(rs.getInt(1));
                person.setName(rs.getString(2));
                if (rs.getDate(3) != null) {
                    person.setDateOfBirth(rs.getDate(3));
                }
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

    public List<Person> getPersonByRole(String role) {
        List<Person> list = new ArrayList<>();
        PreparedStatement stm = null;
        ResultSet rs = null;

        try (Connection connection = getConnection()) { // Use getConnection from DBContext
            String strSelect = "select * from person p "
                    + "join Account a on p.ID = a.PersonID "
                    + "join role r on r.ID = a.RoleID "
                    + "where r.Name like ?";
            stm = connection.prepareStatement(strSelect);
            stm.setString(1, role);
            rs = stm.executeQuery();

            while (rs.next()) {
                Person person = new Person();
                person.setId(rs.getInt(1));
                person.setName(rs.getString(2));
                if (rs.getDate(3) != null) {
                    person.setDateOfBirth(rs.getDate(3));
                }
                // Lấy giá trị của cột gender (kiểu CHAR)
                String gender = rs.getString("gender");
                if (gender != null && gender.length() > 0) {
                    person.setGender(gender.charAt(0));  // Lấy ký tự đầu tiên
                }
                person.setPhone(rs.getString(5));
                person.setEmail(rs.getString(6));
                person.setAddress(rs.getString(7));
                list.add(person);
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

        return list;
    }

    public Person getPersonByPhone(String phone) {
        PreparedStatement stm = null;
        ResultSet rs = null;

        try (Connection connection = getConnection()) { // Use getConnection from DBContext
            String query = "select * from Person where phone = ?";
            stm = connection.prepareStatement(query);
            stm.setString(1, phone);
            rs = stm.executeQuery();

            if (rs.next()) {
                Person person = new Person();
                person.setId(rs.getInt(1));
                person.setName(rs.getString(2));
                if (rs.getDate(3) != null) {
                    person.setDateOfBirth(rs.getDate(3));
                }
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

    public boolean deletePersonAndAccountById(int personId) {
        String deleteAccountSql = "DELETE FROM Account WHERE PersonID = ?";
        String deletePersonSql = "DELETE FROM Person WHERE ID = ?";
        
        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false); // Bắt đầu giao dịch

            try (PreparedStatement accountStmt = connection.prepareStatement(deleteAccountSql);
                 PreparedStatement personStmt = connection.prepareStatement(deletePersonSql)) {
                
                // Xóa tài khoản liên kết
                accountStmt.setInt(1, personId);
                accountStmt.executeUpdate();

                // Xóa người dùng
                personStmt.setInt(1, personId);
                int rowsAffected = personStmt.executeUpdate();
                
                connection.commit(); // Cam kết giao dịch nếu tất cả đều thành công
                return rowsAffected > 0;
            } catch (SQLException e) {
                connection.rollback(); // Quay lại nếu có lỗi xảy ra
                e.printStackTrace();
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    

    public List<Person> getPersonByName(String name) {
        List<Person> list = new ArrayList<>();
        PreparedStatement stm = null;
        ResultSet rs = null;

        try (Connection connection = getConnection()) { // Use getConnection from DBContext
            String strSelect = "SELECT * FROM Person Like ?";
            stm = connection.prepareStatement(strSelect);
            stm.setString(1, "%" + name + "%");
            rs = stm.executeQuery();

            while (rs.next()) {
                Person person = new Person();
                person.setId(rs.getInt(1));
                person.setName(rs.getString(2));
                if (rs.getDate(3) != null) {
                    person.setDateOfBirth(rs.getDate(3));
                }
                // Lấy giá trị của cột gender (kiểu CHAR)
                String gender = rs.getString("gender");
                if (gender != null && gender.length() > 0) {
                    person.setGender(gender.charAt(0));  // Lấy ký tự đầu tiên
                }
                person.setPhone(rs.getString(5));
                person.setEmail(rs.getString(6));
                person.setAddress(rs.getString(7));
                list.add(person);
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

        return list;
    }

    public Person existCheck(Person personCheck) {
        PreparedStatement stm = null;
        ResultSet rs = null;
        try (Connection connection = getConnection()) { // Use getConnection from DBContext
            String query = "select * from Person where email = ? and phone = ? and name = ?";
            stm = connection.prepareStatement(query);
            stm.setString(1, personCheck.getEmail());
            stm.setString(2, personCheck.getPhone());
            stm.setString(3, personCheck.getName());
            rs = stm.executeQuery();

            if (rs.next()) {
                Person person = new Person();
                person.setId(rs.getInt(1));
                person.setName(rs.getString(2));
                if (rs.getDate(3) != null) {
                    person.setDateOfBirth(rs.getDate(3));
                }
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

    public void addPerson(Person person) {
        PreparedStatement stm = null;

        try (Connection connection = getConnection()) { // Use getConnection from DBContext
            String query = "INSERT INTO Person (Name, DateOfBirth, Gender, Phone, Email, Address) VALUES (?, ?, ?, ?, ?, ?)";
            stm = connection.prepareStatement(query);

            // Thiết lập các giá trị cho PreparedStatement
            stm.setString(1, person.getName());
            stm.setDate(2, person.getDateOfBirth());
            stm.setString(3, String.valueOf(person.getGender()));
            stm.setString(4, person.getPhone());
            stm.setString(5, person.getEmail());
            stm.setString(6, person.getAddress());

            // Thực thi lệnh thêm
            stm.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error while adding person: " + e.getMessage());
        } finally {
            // Đóng PreparedStatement nếu không null
            try {
                if (stm != null) {
                    stm.close();
                }
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e.getMessage());
            }
        }
    }

    public int maxID() {
        PreparedStatement stm = null;
        ResultSet rs = null;
        int maxID = 1;
        try (Connection connection = getConnection()) {
            String query = "SELECT MAX(ID) AS max_id FROM Person";
            stm = connection.prepareStatement(query);
            rs = stm.executeQuery();

            if (rs.next()) {
                maxID = rs.getInt("max_id");
                return maxID;
            }
        } catch (SQLException e) {
            System.out.println("Error while getting max appointment ID: " + e.getMessage());
        } finally {
            // Đóng ResultSet và PreparedStatement nếu không null
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
        return maxID; // Trả về 1 nếu có lỗi hoặc không tìm thấy ID nào
    }

    // Chèn dữ liệu vào bảng Person, trả về ID của Person
    public int insertPerson(Person person) throws SQLException {
        String sql = "INSERT INTO Person (Name, DateOfBirth, Gender, Phone, Email, Address) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, person.getName());
            ps.setDate(2, person.getDateOfBirth());
            ps.setString(3, String.valueOf(person.getGender()));
            ps.setString(4, person.getPhone());
            ps.setString(5, person.getEmail());  // Email sẽ được dùng cho Username sau
            ps.setString(6, person.getAddress());

            int affectedRows = ps.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating person failed, no rows affected.");
            }

            // Lấy ID của Person vừa chèn
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating person failed, no ID obtained.");
                }
            }
        }
    }

    // Chèn dữ liệu vào bảng Account, dùng Email làm Username và PersonID từ bảng Person
    public void insertAccount(String email, String password, int roleID, int personID) throws SQLException {
        String sql = "INSERT INTO Account (Username, Password, RoleID, PersonID) "
                + "VALUES (?, ?, ?, ?)";

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);  // Username là Email
            ps.setString(2, password);
            ps.setInt(3, roleID);
            ps.setInt(4, personID);  // ID từ bảng Person

            ps.executeUpdate();
        }
    }

    public int insertPersonAndReturnID(Connection conn, Person person) throws SQLException {
        String sql = "INSERT INTO Person (Name, Phone, Email) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, person.getName());
            stmt.setString(2, person.getPhone());
            stmt.setString(3, person.getEmail());
            stmt.executeUpdate();

            // Lấy ID của Person vừa chèn
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            } else {
                throw new SQLException("Failed to insert person, no ID obtained.");
            }
        }
    }

    public Person getPersonById(int personId) {
        String sql = "SELECT Name, DateOfBirth, Gender, Phone, Email, Address FROM Person WHERE ID = ?";

        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, personId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Person person = new Person();
                person.setName(rs.getString("Name"));
                person.setDateOfBirth(rs.getDate("DateOfBirth"));
                person.setGender(rs.getString("Gender").charAt(0));
                person.setPhone(rs.getString("Phone"));
                person.setEmail(rs.getString("Email"));
                person.setAddress(rs.getString("Address"));

                return person;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Trả về null nếu không tìm thấy person
    }

    // Cập nhật thông tin Person trong DB
    public void updatePerson(Person person) throws SQLException {
        String sql = "UPDATE Person SET Name = ?, DateOfBirth = ?, Gender = ?, Phone = ?, Email = ?, Address = ? WHERE ID = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, person.getName());
            ps.setDate(2, person.getDateOfBirth());
            ps.setString(3, String.valueOf(person.getGender())); // 'M', 'F', or 'O'
            ps.setString(4, person.getPhone());
            ps.setString(5, person.getEmail());
            ps.setString(6, person.getAddress());
            ps.setInt(7, person.getId()); // ID của Person để cập nhật
            ps.executeUpdate();
        }
    }

    // Phương thức lấy tất cả các đối tượng Person từ cơ sở dữ liệu
    public List<Person> getAllPersons() throws SQLException {
        List<Person> persons = new ArrayList<>();
        String sql = "SELECT ID, Name, Email, Phone FROM Person";  // Giả sử bảng Person có các cột này

        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Person person = new Person();
                person.setId(rs.getInt("ID"));
                person.setName(rs.getString("Name"));
                person.setEmail(rs.getString("Email"));
                person.setPhone(rs.getString("Phone"));
                persons.add(person);
            }
        }
        return persons;
    }

    public Person getPersonByEmail(String email) throws SQLException {
        String sql = "SELECT Name, Email, Phone, Address, DateOfBirth, Gender, Image FROM Person WHERE Email = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Person person = new Person();
                person.setName(rs.getString("Name"));
                person.setEmail(rs.getString("Email"));
                person.setPhone(rs.getString("Phone"));
                person.setAddress(rs.getString("Address"));
                person.setDateOfBirth(rs.getDate("DateOfBirth"));
                person.setGender(rs.getString("Gender").charAt(0));
                person.setImage(rs.getString("Image"));  // Lấy ảnh từ cột "Image"
                return person;
            }
        }
        return null;
    }

    public void updateImage(int personId, String imageName) throws SQLException {
        String sql = "UPDATE Person SET Image = ? WHERE ID = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, imageName);
            ps.setInt(2, personId);
            ps.executeUpdate();
        }
    }

    public boolean updateEmailAndUsername(int personId, String newEmail) throws SQLException {
        Connection conn = null;
        PreparedStatement personStmt = null;
        PreparedStatement accountStmt = null;
        boolean success = false;

        String updatePersonEmail = "UPDATE Person SET Email = ? WHERE ID = ?";
        String updateAccountUsername = "UPDATE Account SET Username = ? WHERE PersonID = ?";

        try {
            conn = DBContext.getConnection();
            conn.setAutoCommit(false);  // Start transaction

            // Update email in Person table
            personStmt = conn.prepareStatement(updatePersonEmail);
            personStmt.setString(1, newEmail);
            personStmt.setInt(2, personId);
            int personRows = personStmt.executeUpdate();

            // Update username in Account table
            accountStmt = conn.prepareStatement(updateAccountUsername);
            accountStmt.setString(1, newEmail);
            accountStmt.setInt(2, personId);
            int accountRows = accountStmt.executeUpdate();

            // Commit transaction if both updates are successful
            if (personRows > 0 && accountRows > 0) {
                conn.commit();
                success = true;
            } else {
                conn.rollback();  // Rollback if either update fails
            }
        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback();  // Rollback on error
            }
            throw e;
        } finally {
            if (personStmt != null) {
                personStmt.close();
            }
            if (accountStmt != null) {
                accountStmt.close();
            }
            if (conn != null) {
                conn.setAutoCommit(true);  // Restore default behavior
            }
            if (conn != null) {
                conn.close();
            }
        }
        return success;
    }

    public Person getPersonByAccount(String username, String password) {
        String sql = "SELECT p.* FROM Person p "
                + "INNER JOIN Account a ON p.ID = a.PersonID "
                + "WHERE a.Username = ? AND a.Password = ? AND a.Status = 'Active'";

        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);

            System.out.println("Executing query for username: " + username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Person person = new Person();
                person.setId(rs.getInt("ID"));
                person.setName(rs.getString("Name"));
                person.setDateOfBirth(rs.getDate("DateOfBirth"));
                String gender = rs.getString("Gender");
                if (gender != null && gender.length() > 0) {
                    person.setGender(gender.charAt(0));
                }
                person.setPhone(rs.getString("Phone"));
                person.setEmail(rs.getString("Email"));
                person.setAddress(rs.getString("Address"));
                person.setImage(rs.getString("Image"));

                System.out.println("Found person with ID: " + person.getId());
                return person;
            }
        } catch (SQLException e) {
            System.err.println("Error getting person by account: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("No person found for username: " + username);
        return null;
    }

    public Person login(String username, String password) {
        String sql = "SELECT p.* FROM Person p "
                + "INNER JOIN Account a ON p.ID = a.PersonID "
                + "WHERE a.Username = ? AND a.Password = ?";

        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);

            System.out.println("Executing login query for username: " + username);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Person person = new Person();
                    person.setId(rs.getInt("ID"));
                    person.setName(rs.getString("Name"));
                    person.setDateOfBirth(rs.getDate("DateOfBirth"));
                    String gender = rs.getString("Gender");
                    if (gender != null && gender.length() > 0) {
                        person.setGender(gender.charAt(0));
                    }
                    person.setPhone(rs.getString("Phone"));
                    person.setEmail(rs.getString("Email"));
                    person.setAddress(rs.getString("Address"));
                    person.setImage(rs.getString("Image"));

                    System.out.println("Found person with ID: " + person.getId() + ", Name: " + person.getName());
                    return person;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error in login: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("No person found for username: " + username);
        return null;
    }

    public static void main(String args[]) {
        PersonDAO o = new PersonDAO();
        List<Person> p = o.getPersonByRole("customer");
        for (Person person : p) {
            System.out.println(person.getPhone());
        }

    }

    public void updateEmployee(Person person) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
