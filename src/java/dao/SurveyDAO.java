package dao;

import java.sql.*;
import java.util.*;
import model.Survey;
import dal.DBContext;

public class SurveyDAO extends DBContext {
    
    public boolean saveSurvey(Survey survey) {
        String sql = "INSERT INTO UserSurvey (userId, skinType, skinConcerns, beautyGoals, budgetRange, " +
                    "preferredServices, previousTreatments) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setObject(1, survey.getUserId());
            ps.setString(2, survey.getSkinType());
            ps.setString(3, survey.getSkinConcerns());
            ps.setString(4, survey.getBeautyGoals());
            ps.setString(5, survey.getBudgetRange());
            ps.setString(6, survey.getPreferredServices());
            ps.setString(7, survey.getPreviousTreatments());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error saving survey: " + e.getMessage());
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
                    ")," +
                    "SimilarUsers AS (" +
                    "    SELECT DISTINCT s.userId " +
                    "    FROM UserSurvey s " +
                    "    JOIN UserPreferences up ON " +
                    "        s.skinType = up.skinType OR " +
                    "        s.skinConcerns LIKE '%' + up.skinConcerns + '%' OR " +
                    "        s.beautyGoals LIKE '%' + up.beautyGoals + '%' OR " +
                    "        s.budgetRange = up.budgetRange " +
                    "    WHERE s.userId != ?" +
                    ")" +
                    "SELECT TOP 5 s.serviceName, COUNT(*) as frequency " +
                    "FROM Bookings b " +
                    "JOIN Services s ON b.serviceId = s.serviceId " +
                    "WHERE b.userId IN (SELECT userId FROM SimilarUsers) " +
                    "GROUP BY s.serviceName " +
                    "ORDER BY frequency DESC";
        
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, userId);
            ps.setInt(2, userId);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    recommendations.add(rs.getString("serviceName"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error getting recommendations: " + e.getMessage());
        }
        
        return recommendations;
    }
} 