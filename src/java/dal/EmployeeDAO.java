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
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Person;

/**
 *
 * @author admin
 */
public class EmployeeDAO extends DBContext {

    public List<Person> getAllEmployee() {
        List<Person> list = new ArrayList<>();
        PreparedStatement stm = null;
        ResultSet rs = null;

        try (Connection connection = getConnection()) { // Use getConnection from DBContext
            String strSelect = "  SELECT \n"
                    + " p.Id,\n"
                    + "    p.Name, \n"
                    + "    p.DateOfBirth,\n"
                    + "	p.Gender,\n"
                    + "    p.Phone, \n"
                    + "    p.Email, \n"
                    + "    p.Address \n"
                    + "FROM Person p\n"
                    + "INNER JOIN account a \n"
                    + "    ON p.ID = a.PersonID\n"
                    + "where a.RoleID = 3;    ";
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

    public void updateEmployee(Person person) {
        PreparedStatement stm = null;
        try (Connection connection = getConnection()) {
            String strUpdate = "UPDATE Person SET Name = ?, DateOfBirth = ?, Gender = ?, Phone = ?, Email = ?, Address = ? WHERE Id = ?";
            stm = connection.prepareStatement(strUpdate);
            stm.setString(1, person.getName());
            stm.setDate(2, person.getDateOfBirth() != null ? new java.sql.Date(person.getDateOfBirth().getTime()) : null);
            stm.setString(3, String.valueOf(person.getGender()));
            stm.setString(4, person.getPhone());
            stm.setString(5, person.getEmail());
            stm.setString(6, person.getAddress());
            stm.setInt(7, person.getId());

            stm.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error while updating person: " + e.getMessage());
        } finally {
            // Close the PreparedStatement if it's not null
            try {
                if (stm != null) {
                    stm.close();
                }
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e.getMessage());
            }
        }
    }

    public void deleteEmployeeByID(String id) {
        try {
            String sql = "DELETE FROM Account\n"
                    + "WHERE RoleID = 3 AND ID = ?;";
            PreparedStatement statement = DBContext.getConnection().prepareStatement(sql);
            statement.setString(1, id);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public static void main(String[] args) {
        EmployeeDAO e = new EmployeeDAO();
        System.out.println(e.getAllEmployee());
    }
}