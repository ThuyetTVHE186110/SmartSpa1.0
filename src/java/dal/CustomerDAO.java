/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Person;

/**
 *
 * @author admin
 */
public class CustomerDAO extends DBContext {

    public CustomerDAO() {
    }

    public ArrayList<Person> getAllCustomer() {
        ArrayList<Person> customerList = new ArrayList<>();
        try {
            String sql = "Select p.ID, p.Name, p.DateOfBirth, p.Gender, p.Phone, p.Email, p.Address, a.RoleID, a.Status  from Person p\n"
                    + "  INNER JOIN Account a ON p.ID = a.PersonID\n"
                    + "  WHERE a.RoleID = 4";
            PreparedStatement statement = DBContext.getConnection().prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                
            }
        } catch (Exception e) {
            
        }

        return null;

    }
}
