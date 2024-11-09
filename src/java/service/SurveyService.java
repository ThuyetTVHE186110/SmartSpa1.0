package service;

import dal.DBContext;
import dal.SurveyDAO;
import model.*;
import java.util.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SurveyService {
    private final SurveyDAO surveyDAO;
    
    public SurveyService() {
        this.surveyDAO = new SurveyDAO();
    }
    
    public RecommendationResult processSurvey(Survey survey) {
        System.out.println("Processing survey in service layer");
        
        // Validate survey
        if (!survey.isValid()) {
            System.out.println("Survey validation failed");
            throw new ValidationException("Survey data is incomplete. Please fill in all required fields.");
        }
        
        // Test database connection
        if (!surveyDAO.testConnection()) {
            System.out.println("Database connection test failed");
            throw new ServiceException("Unable to connect to database");
        }
        
        // Save survey
        boolean saved = surveyDAO.saveSurvey(survey);
        if (!saved) {
            System.out.println("Failed to save survey");
            throw new ServiceException("Failed to save survey data");
        }
        
        // Get recommendations
        List<String> recommendations;
        if (survey.getUserId() != null) {
            recommendations = surveyDAO.getRecommendedServices(survey.getUserId());
        } else {
            // Get general recommendations if user is not logged in
            recommendations = surveyDAO.getServicesBySkinType(survey);
        }
        
        System.out.println("Got recommendations: " + recommendations.size());
        
        return new RecommendationResult(recommendations);
    }
    
    private void trackCustomerJourney(Survey survey) {
        String sql = "INSERT INTO CustomerJourney (userId, touchpoint, details) " +
                    "VALUES (?, 'SURVEY_COMPLETE', ?)";
        
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, survey.getUserId());
            ps.setString(2, "Completed beauty survey with skin type: " + survey.getSkinType());
            ps.executeUpdate();
        } catch (SQLException e) {
            // Log error but don't fail the process
            System.out.println("Error tracking journey: " + e.getMessage());
        }
    }
} 