/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.FeedbackDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import model.Feedback;

/**
 *
 * @author admin
 */
public class FeedbackServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        FeedbackDAO feedbackDAO = new FeedbackDAO();
        if (action == null || action.equals("viewAll")) {

            ArrayList<Feedback> feedbackList = feedbackDAO.getFeedback();
            request.setAttribute("feedback", feedbackList);
            request.getRequestDispatcher("testimonial.jsp").forward(request, response);
        } else if (action.equals("view")) {
            // View specific feedback
            String id = request.getParameter("id");
            Feedback feedback = feedbackDAO.getFeedbackById(id); // Assuming the method returns a list
            request.setAttribute("feedback", feedback);
            request.getRequestDispatcher("viewFeedback.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        FeedbackDAO feedbackDAO = new FeedbackDAO();
        if (action.equals("edit")) {
            // Edit feedbac
            String id = request.getParameter("id");
            String content = request.getParameter("content");

            Feedback feedback = feedbackDAO.getFeedbackById(id);
            System.out.println(feedback);// Fetch existing feedback
            feedback.setContent(content);
            feedbackDAO.updateFeedbackByID(feedback);
            response.sendRedirect("feedback?action=viewAll"); // Redirect to view all feedback
        } else if (action.equals("delete")) {
            // Delete feedback
            String id = request.getParameter("id");
            feedbackDAO.deleteFeedbackByID(id);
            response.sendRedirect("feedback?action=viewAll"); // Redirect to view all feedback
//        } else if ("add".equals(action)) {
//            String serviceIdParam = request.getParameter("serviceId");
//            String content = request.getParameter("content");
//
//            // Convert parameters to appropriate types
//            int serviceId = Integer.parseInt(serviceIdParam);
//
//            // Create a new Feedback object
//            Person service = new Person();
//            Feedback feedback = new Feedback();
//            feedback.setService(service.getId());
//            feedback.setContent(content);
//
//            // Insert feedback into the database
//            feedbackDAO.createFeedback(feedback);
//            // Redirect or return response as needed
//            response.sendRedirect("feedback"); // Redirect to feedback page
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
