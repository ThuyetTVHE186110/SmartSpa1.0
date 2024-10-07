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
import model.Person;
import model.Service;

/**
 *
 * @author admin
 */
public class FeedbackServlet extends HttpServlet {
<<<<<<< Updated upstream

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     * 
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet FeedbackServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet FeedbackServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the
    // + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     * 
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        FeedbackDAO feedbackDAO = new FeedbackDAO();
        ArrayList<Feedback> feedback = feedbackDAO.getFeedback();

        request.setAttribute("feedback", feedback);

        request.getRequestDispatcher("testimonial.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     * 
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     * 
     * @return a String containing servlet description
     */
=======

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

>>>>>>> Stashed changes
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
