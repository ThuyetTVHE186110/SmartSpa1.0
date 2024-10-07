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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author admin
 */
public class FeedbackDAO extends DBContext {

    public FeedbackDAO() {
    }

    // deleted
    public ArrayList<Feedback> getFeedback() {

        ArrayList<Feedback> feedback = new ArrayList<>();
        try {
//            String sql = "SELECT f.ID, f.Content, p.Name AS customerName, s.name AS serviceName "
//                    + "FROM Feedback f "
//                    + "JOIN Person p ON f.CustomerID = p.ID "
            String sql = "SELECT f.ID, f.Content, p.Name AS customerName, s.Name AS serviceName \n"
                    + "FROM Feedback f \n"
                    + "JOIN Person p ON f.CustomerID = p.ID \n"
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

                feedback.add(fb);
            }
        } catch (SQLException e) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, e);
        }

        return feedback;
    }

    public Feedback getFeedbackById(String id) {
        Feedback fb = new Feedback();
        PersonDAO p = new PersonDAO();
        ServiceDAO s = new ServiceDAO();
        try {
            String sql = "SELECT * FROM Feedback WHERE ID=?";
            PreparedStatement statement = DBContext.getConnection().prepareStatement(sql);
            statement.setString(1, id);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {

                fb.setId(rs.getInt("ID"));
                fb.setContent(rs.getString("Content"));
                int fbP = rs.getInt("CustomerID");
                int fbS = rs.getInt("ServicesID");
                fb.setCustomer(p.getPersonByID(fbP));
                fb.setService(s.selectService(fbS));
            }
        } catch (SQLException e) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, e);
        }
        System.out.println(fb);
        return fb;
    }

    public static void main(String[] args) {
        FeedbackDAO x = new FeedbackDAO();
        x.getFeedbackById("1");
    }

    public void deleteFeedbackByID(String id) {
        try {
            String sql = "DELETE FROM Feedback WHERE ID=?";
            PreparedStatement statement = DBContext.getConnection().prepareStatement(sql);
            statement.setString(1, id);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, e);
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
            statement.setInt(4, feedback.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void createFeedback(Feedback feedback) {
        try {
            String sql = "INSERT INTO Feedback (Content, ServicesID) VALUES (?, ?)";
            PreparedStatement statement = DBContext.getConnection().prepareStatement(sql);
            statement.setString(1, feedback.getContent());
            statement.setInt(2, feedback.getCustomer().getId());

            statement.setInt(3, feedback.getService().getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, e);
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
    public static void main(String args) {
        FeedbackDAO o = new FeedbackDAO();
        ArrayList<Feedback> f = o.getFeedback();
        for (Feedback feedback : f) {
            System.out.println(feedback.getContent());
        }

    }
}
