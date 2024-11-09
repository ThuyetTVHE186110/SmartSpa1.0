/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import model.Account;
import model.RecommendationResult;
import model.Survey;
import service.SurveyService;
import service.ValidationException;
import model.RecommendationResult;

/**
 *
 * @author Asus
 */
@WebServlet(name = "SaveSurveyServlet", urlPatterns = {"/SaveSurvey"})
public class SaveSurveyServlet extends HttpServlet {
    private final SurveyService surveyService;
    
    public SaveSurveyServlet() {
        this.surveyService = new SurveyService();
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Create survey object from request
            Survey survey = createSurveyFromRequest(request);
            
            // Process survey and get recommendations
            RecommendationResult result = surveyService.processSurvey(survey);
            
            // Store recommendations in session
            request.setAttribute("recommendations", result.getRecommendations());
            
            // Forward back to survey page with recommendations
            request.getRequestDispatcher("survey.jsp").forward(request, response);
            
        } catch (ValidationException ve) {
            request.setAttribute("errorMessage", "Validation error: " + ve.getMessage());
            request.getRequestDispatcher("survey.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Server error: " + e.getMessage());
            request.getRequestDispatcher("survey.jsp").forward(request, response);
        }
    }
    
    private Survey createSurveyFromRequest(HttpServletRequest request) {
        Survey survey = new Survey();
        
        try {
            // Get the user ID from session if logged in
            if (request.getSession().getAttribute("user") != null) {
                Account user = (Account) request.getSession().getAttribute("user");
                survey.setUserId(user.getPersonInfo().getId());
            }
            
            String skinType = request.getParameter("skinType");
            String[] skinConcerns = request.getParameterValues("skinConcerns");
            String budgetRange = request.getParameter("budgetRange");
            
            // Validate required fields
            if (skinType == null || skinType.isEmpty()) {
                throw new ValidationException("Please select your skin type");
            }
            
            if (skinConcerns == null || skinConcerns.length == 0) {
                throw new ValidationException("Please select at least one skin concern");
            }
            
            if (budgetRange == null || budgetRange.isEmpty()) {
                throw new ValidationException("Please select your budget range");
            }
            
            System.out.println("Form data received:");
            System.out.println("skinType: " + skinType);
            System.out.println("skinConcerns: " + (skinConcerns != null ? String.join(",", skinConcerns) : "null"));
            System.out.println("budgetRange: " + budgetRange);
            
            survey.setSkinType(skinType);
            survey.setSkinConcerns(String.join(",", skinConcerns));
            survey.setBudgetRange(budgetRange);
            
        } catch (Exception e) {
            System.out.println("Error creating survey: " + e.getMessage());
            e.printStackTrace();
            throw new ValidationException(e.getMessage());
        }
        
        return survey;
    }
}
