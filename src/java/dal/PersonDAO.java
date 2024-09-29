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

/**
 *
 * @author ADMIN
 */
public class PersonDAO extends DBContext {
    
    public List<Person> getAll(){
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
                person.setDateOfBirth(rs.getDate(3));
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

    public Person getPersonByEmail(String email) {
        PreparedStatement stm = null;
        ResultSet rs = null;
        
        try (Connection connection = getConnection()) { // Use getConnection from DBContext
            String query = "select * from Person where Email = ?";
            stm = connection.prepareStatement(query);
            stm.setString(1, email);
            rs = stm.executeQuery();
            
            if(rs.next()) {
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

    public Person getPersonByPhone(String phone) {
        PreparedStatement stm = null;
        ResultSet rs = null;
        
        try (Connection connection = getConnection()) { // Use getConnection from DBContext
            String query = "select * from Person where phone = ?";
            stm = connection.prepareStatement(query);
            stm.setString(1, phone);
            rs = stm.executeQuery();
            
            if(rs.next()) {
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

    public Person getPersonByName(String name) {
        PreparedStatement stm = null;
        ResultSet rs = null;
        
        try (Connection connection = getConnection()) { // Use getConnection from DBContext
            String query = "select * from Person where name = ?";
            stm = connection.prepareStatement(query);
            stm.setString(1, name);
            rs = stm.executeQuery();
            
            if(rs.next()) {
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

    public static void main(String[] args) {
        PersonDAO testDAO = new PersonDAO();
        List<Person> list = testDAO.getAll();
        for (Person person : list) {
            System.out.println(person.getId());
            System.out.println(person.getEmail());
            System.out.println(person.getPhone());
            System.out.println(person.getDateOfBirth());
        }
    }
}
