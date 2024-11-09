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
import model.Survey;

/**
 *
 * @author Asus
 */
public class SurveyDAO extends DBContext {
    public boolean saveSurvey(Survey survey) {
        System.out.println("Saving survey to database");
        
        String sql = "INSERT INTO UserSurvey (userId, skinType, skinConcerns, " +
                    "beautyGoals, budgetRange) " +
                    "VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            // Handle null userId for non-logged in users
            if (survey.getUserId() != null) {
                ps.setInt(1, survey.getUserId());
            } else {
                ps.setNull(1, java.sql.Types.INTEGER);
            }
            
            ps.setString(2, survey.getSkinType());
            ps.setString(3, survey.getSkinConcerns());
            ps.setString(4, survey.getBeautyGoals() != null ? survey.getBeautyGoals() : "");
            ps.setString(5, survey.getBudgetRange());
            
            int result = ps.executeUpdate();
            System.out.println("Survey saved successfully: " + (result > 0));
            return result > 0;
            
        } catch (SQLException e) {
            System.out.println("Error saving survey: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    public List<String> getRecommendedServices(int userId) {
        List<String> recommendations = new ArrayList<>();
        
        String sql = "WITH UserPreferences AS (" +
                    "    SELECT TOP 1 skinType, skinConcerns, beautyGoals, budgetRange " +
                    "    FROM UserSurvey " +
                    "    WHERE userId = ? " +
                    "    ORDER BY createdAt DESC" +
                    ")" +
                    "SELECT DISTINCT s.Name, s.Price " +
                    "FROM Services s " +
                    "JOIN ServiceRecommendations sr ON s.ID = sr.serviceId " +
                    "JOIN UserPreferences up ON " +
                    "    sr.skinType = up.skinType OR " +
                    "    sr.skinConcerns LIKE '%' + up.skinConcerns + '%' " +
                    "WHERE s.status = 'ACTIVE' " +
                    "AND s.Price <= CASE up.budgetRange " +
                    "    WHEN 'budget' THEN 100 " +
                    "    WHEN 'moderate' THEN 300 " +
                    "    ELSE 1000 END " +
                    "ORDER BY s.Price";
        
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, userId);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    recommendations.add(rs.getString("Name"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error getting recommendations: " + e.getMessage());
        }
        
        return recommendations;
    }
    
    public List<String> getServicesBySkinType(Survey survey) {
        System.out.println("Getting services for skin type: " + survey.getSkinType());
        
        List<String> services = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        sql.append("WITH RankedServices AS ( ")
           .append("    SELECT s.Name, ")
           .append("    CASE ")
           .append("        WHEN s.Name = 'Facial Therapy' THEN 1 ")
           .append("        WHEN s.Name = 'Skin Care' THEN 2 ")
           .append("        WHEN s.Name = 'Classic Lash Extensions' THEN 3 ")
           .append("        ELSE 4 ")
           .append("    END AS SortOrder, ")
           .append("    s.Price ")
           .append("    FROM Services s ")
           .append("    WHERE s.status = 'ACTIVE' ");

        // Add skin type and concerns based filtering
        if ("normal".equalsIgnoreCase(survey.getSkinType())) {
            if (survey.getSkinConcerns().contains("aging")) {
                sql.append("    AND s.Name IN ('Facial Therapy', 'Skin Care', 'Classic Lash Extensions') ");
            } else if (survey.getSkinConcerns().contains("sensitivity")) {
                sql.append("    AND s.Name IN ('Stream Bath', 'Body Massage', 'Stone Therapy') ");
            } else {
                sql.append("    AND s.Name IN ('Facial Therapy', 'Body Massage', 'Skin Care') ");
            }
        } else if ("oily".equalsIgnoreCase(survey.getSkinType())) {
            sql.append("    AND s.Name IN ('Skin Care', 'Facial Therapy', 'Stream Bath') ");
        } else if ("dry".equalsIgnoreCase(survey.getSkinType())) {
            sql.append("    AND s.Name IN ('Body Massage', 'Stream Bath', 'Stone Therapy', 'Hybrid Lash Extensions') ");
        } else if ("combination".equalsIgnoreCase(survey.getSkinType())) {
            sql.append("    AND s.Name IN ('Facial Therapy', 'Skin Care', 'Stream Bath') ");
        }

        // Add budget range filter based on your actual service prices
        if ("budget".equalsIgnoreCase(survey.getBudgetRange())) {
            sql.append("    AND s.Price <= 70 "); // Body Massage, Facial Therapy, Skin Care, Stream Bath
        } else if ("moderate".equalsIgnoreCase(survey.getBudgetRange())) {
            sql.append("    AND s.Price > 70 AND s.Price <= 150 "); // Stone Therapy, Lash Lift
        } else {
            sql.append("    AND s.Price > 150 "); // Classic/Hybrid/Volume Lash Extensions
        }

        sql.append(") ")
           .append("SELECT TOP 3 Name, SortOrder, Price ")  // Include all columns used in ORDER BY
           .append("FROM RankedServices ")
           .append("ORDER BY SortOrder, Price");
        
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            
            System.out.println("Executing SQL: " + sql.toString());
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    services.add(rs.getString("Name"));
                }
            }
            System.out.println("Found " + services.size() + " services");
            
        } catch (SQLException e) {
            System.out.println("Error getting services: " + e.getMessage());
            e.printStackTrace();
        }
        return services;
    }
    
    public List<String> getPopularServices(String season) {
        List<String> services = new ArrayList<>();
        String sql = "SELECT TOP 5 s.serviceName " +
                    "FROM Services s " +
                    "JOIN Bookings b ON s.serviceId = b.serviceId " +
                    "WHERE DATEPART(MONTH, b.bookingDate) = ? " +
                    "GROUP BY s.serviceName " +
                    "ORDER BY COUNT(*) DESC";
                    
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, season);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    services.add(rs.getString("serviceName"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error getting popular services: " + e.getMessage());
        }
        return services;
    }
    
    public boolean testConnection() {
        try (Connection conn = getConnection()) {
            return conn != null;
        } catch (SQLException e) {
            System.out.println("Database connection test failed: " + e.getMessage());
            return false;
        }
    }
}
