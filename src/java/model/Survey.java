package model;

public class Survey {
    private Integer surveyId;
    private Integer userId;
    private String skinType;
    private String skinConcerns;
    private String beautyGoals;
    private String budgetRange;
    private String preferredServices;
    private String previousTreatments;
    
    // Constructors
    public Survey() {}
    
    public Survey(Integer userId, String skinType, String skinConcerns, 
                 String beautyGoals, String budgetRange, 
                 String preferredServices, String previousTreatments) {
        this.userId = userId;
        this.skinType = skinType;
        this.skinConcerns = skinConcerns;
        this.beautyGoals = beautyGoals;
        this.budgetRange = budgetRange;
        this.preferredServices = preferredServices;
        this.previousTreatments = previousTreatments;
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
    
    public String getPreferredServices() { return preferredServices; }
    public void setPreferredServices(String preferredServices) { this.preferredServices = preferredServices; }
    
    public String getPreviousTreatments() { return previousTreatments; }
    public void setPreviousTreatments(String previousTreatments) { this.previousTreatments = previousTreatments; }
} 