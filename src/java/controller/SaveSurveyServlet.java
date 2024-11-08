/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import com.google.gson.Gson;
import dal.ServiceDAO;
import dal.SurveyDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import model.Account;
import model.Service;
import model.Survey;

/**
 *
 * @author Asus
 */
public class SaveSurveyServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        Map<String, Object> jsonResponse = new HashMap<>();

        try {
            // Get the user ID from session if logged in
            Integer userId = null;
            if (request.getSession().getAttribute("user") != null) {
                userId = ((Account) request.getSession().getAttribute("user")).getPersonInfo().getId();
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
        String skinType = request.getParameter("skinType");
        String budgetRange = request.getParameter("budgetRange");
        ServiceDAO serviceDAO = new ServiceDAO();

        List<Service> recommendedServices = serviceDAO.getRecommendedServices(skinType, budgetRange);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(new Gson().toJson(recommendedServices));
    }
}
