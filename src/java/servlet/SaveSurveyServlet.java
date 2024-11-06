package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import dal.SurveyDAO;
import model.Survey;
import model.Account;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

@WebServlet(name = "SaveSurvey", urlPatterns = {"/SaveSurvey"})
public class SaveSurveyServlet extends HttpServlet {
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        Map<String, Object> jsonResponse = new HashMap<>();
        
        try {
            // Get the user ID from session if logged in
            Integer userId = null;
            if (request.getSession().getAttribute("user") != null) {
                userId = ((Account)request.getSession().getAttribute("user")).getPersonInfo().getId();
            }
            
            // Create Survey object from request parameters
            Survey survey = new Survey();
            survey.setUserId(userId);
            survey.setSkinType(request.getParameter("skinType"));
            survey.setSkinConcerns(String.join(",", request.getParameterValues("skinConcerns")));
            survey.setBeautyGoals(String.join(",", request.getParameterValues("beautyGoals")));
            survey.setBudgetRange(request.getParameter("budgetRange"));
            
            // Save survey using DAO
            SurveyDAO surveyDAO = new SurveyDAO();
            boolean saved = surveyDAO.saveSurvey(survey);
            
            if (saved && userId != null) {
                // Get recommendations
                List<String> recommendations = surveyDAO.getRecommendedServices(userId);
                jsonResponse.put("recommendations", recommendations);
            }
            
            jsonResponse.put("success", saved);
            jsonResponse.put("message", saved ? "Survey saved successfully" : "Error saving survey");
            
        } catch (Exception e) {
            jsonResponse.put("success", false);
            jsonResponse.put("message", "Error: " + e.getMessage());
        }
        
        String json = new Gson().toJson(jsonResponse);
        response.getWriter().write(json);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
} 