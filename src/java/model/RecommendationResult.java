package model;

import java.util.List;

public class RecommendationResult {
    private List<String> recommendations;
    private String personalizedMessage;
    
    public RecommendationResult(List<String> recommendations) {
        this.recommendations = recommendations;
    }
    
    // Getters and setters
    public List<String> getRecommendations() {
        return recommendations;
    }
    
    public void setRecommendations(List<String> recommendations) {
        this.recommendations = recommendations;
    }
} 