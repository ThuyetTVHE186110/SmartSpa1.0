/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Feedback;
import model.Person;
import model.Service;

/**
 *
 * @author admin
 */
public class FeedbackDAO extends DBContext {

    public FeedbackDAO() {
    }

    public ArrayList<Feedback> getFeedback() {

        ArrayList<Feedback> feedback = new ArrayList<>();
        try {
            String sql = "SELECT f.ID, f.Content, p.Name AS customerName, s.name AS serviceName "
                    + "FROM Feedback f "
                    + "JOIN Person p ON f.CustomerID = p.ID "
                    + "JOIN Services s ON f.ServicesID = s.ID";
            PreparedStatement statement = DBContext.getConnection().prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Feedback fb = new Feedback();
                fb.setId(rs.getInt("ID"));
                fb.setContent(rs.getString("Content"));
                
                Person customer = new Person();
                customer.setId(rs.getInt("ID"));
                customer.setName(rs.getString("customerName"));
                fb.setCustomer(customer);
                
                Service service = new Service();
                service.setId(rs.getInt("ID"));
                service.setName(rs.getString("serviceName"));
                fb.setService(service);
                
                feedback.add(fb);           
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        return feedback;
    }
    
    public int getTotalFeedbacks(){
        int  count = 0;
        try {
            String  sql = "SELECT COUNT(*) AS total FROM feedback";
            PreparedStatement statement = DBContext.getConnection().prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                count = rs.getInt("total");
            }
        } catch (SQLException e) {
             e.printStackTrace();
        }
        return count;
    } 
    
    public void deleteFeedbackByID(String id) {
        try {
            String sql = "DELETE FROM Feedback WHERE ID=?";
            PreparedStatement statement = DBContext.getConnection().prepareStatement(sql);
            statement.setString(1, id);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void updateFeedbackByID(Feedback feedback) {
        try {
            String sql = "UPDATE [dbo].[Feedback]\n"
                    + "SET \n"
                    + "    [Content] = ?,\n"
                    + "    [CustomerID] = ?,\n"
                    + "    [ServicesID] = ?\n"
                    + "WHERE \n"
                    + "    [ID] = ?;";
            PreparedStatement statement = DBContext.getConnection().prepareStatement(sql);

            statement.setString(1, feedback.getContent());
            statement.setInt(2, feedback.getCustomer().getId());
            statement.setInt(3, feedback.getService().getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void createFeedback(Feedback feedback) {
        try {
            String sql = "INSERT INTO Feedback (Content, CustomerID, ServicesID) VALUES (?, ?, ?)";
            PreparedStatement statement = DBContext.getConnection().prepareStatement(sql);
            statement.setString(1, feedback.getContent());
            statement.setInt(2, feedback.getCustomer().getId());
            statement.setInt(3, feedback.getService().getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

//    public void readFeedback(int id){
//        try {
//            String sql = "SELECT * FROM Feedback WHERE ID = ?";
//            PreparedStatement statement = DBContext.getConnection().prepareStatement(sql);
//            statement.setInt(1, id);
//            ResultSet rs = statement.executeQuery();
//            while(rs.next()){
//                
//            }
//        } catch (Exception e) {
//        }
//    }

}


