package service;

import model.RecommendationResult;
import model.Survey;
import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public class SurveyServiceTest {
    private SurveyService surveyService;

    @Test
    public void testSurveyProcessing() {
        Survey testSurvey = new Survey();
        testSurvey.setSkinType("normal");
        testSurvey.setSkinConcerns("acne,dryness");
        testSurvey.setBeautyGoals("natural,antiAging");
        testSurvey.setBudgetRange("moderate");
        
        RecommendationResult result = surveyService.processSurvey(testSurvey);
        assertNotNull(result.getRecommendations());
        assertFalse(result.getRecommendations().isEmpty());
    }
} 