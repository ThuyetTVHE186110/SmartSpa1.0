package util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import model.Survey;
import dal.DBContext;

public class Analytics {
    public static void trackSurveyCompletion(Survey survey) {
        String sql = "INSERT INTO CustomerJourney (userId, touchpoint, details) VALUES (?, 'SURVEY_COMPLETE', ?)";
        
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, survey.getUserId());
            ps.setString(2, String.format("Completed survey with skin type: %s", survey.getSkinType()));
            ps.executeUpdate();
            
        } catch (Exception e) {
            System.out.println("Error tracking survey completion: " + e.getMessage());
        }
    }
} 