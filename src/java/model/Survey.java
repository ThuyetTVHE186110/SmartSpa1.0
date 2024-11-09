package model;

import java.time.LocalDateTime;

public class Survey {
    private Integer surveyId;
    private Integer userId;
    private String skinType;
    private String skinConcerns;
    private String beautyGoals;
    private String budgetRange;
    private String allergies;
    private Integer age;
    private String lifestyle;
    private LocalDateTime createdAt;
    
    // Constructors
    public Survey() {}
    
    public Survey(Integer userId, String skinType, String skinConcerns, 
                 String beautyGoals, String budgetRange, 
                 String allergies, Integer age, String lifestyle) {
        this.userId = userId;
        this.skinType = skinType;
        this.skinConcerns = skinConcerns;
        this.beautyGoals = beautyGoals;
        this.budgetRange = budgetRange;
        this.allergies = allergies;
        this.age = age;
        this.lifestyle = lifestyle;
    }
    
    // Getters and Setters
    public Integer getSurveyId() { return surveyId; }
    public void setSurveyId(Integer surveyId) { this.surveyId = surveyId; }
    
    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }
    
    public String getSkinType() { return skinType; }
    public void setSkinType(String skinType) { this.skinType = skinType; }
    
    public String getSkinConcerns() { return skinConcerns; }
    public void setSkinConcerns(String skinConcerns) { this.skinConcerns = skinConcerns; }
    
    public String getBeautyGoals() { return beautyGoals; }
    public void setBeautyGoals(String beautyGoals) { this.beautyGoals = beautyGoals; }
    
    public String getBudgetRange() { return budgetRange; }
    public void setBudgetRange(String budgetRange) { this.budgetRange = budgetRange; }
    
    public String getAllergies() { return allergies; }
    public void setAllergies(String allergies) { this.allergies = allergies; }
    
    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }
    
    public String getLifestyle() { return lifestyle; }
    public void setLifestyle(String lifestyle) { this.lifestyle = lifestyle; }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    // Validation methods
    public boolean isValid() {
        System.out.println("Validating survey:");
        System.out.println("skinType: " + skinType);
        System.out.println("skinConcerns: " + skinConcerns);
        System.out.println("budgetRange: " + budgetRange);
        
        boolean isValid = skinType != null && !skinType.isEmpty() &&
                         skinConcerns != null && !skinConcerns.isEmpty() &&
                         budgetRange != null && !budgetRange.isEmpty();
                         
        System.out.println("Survey is valid: " + isValid);
        return isValid;
    }
} 