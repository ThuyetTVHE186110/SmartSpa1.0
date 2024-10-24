/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.FeedbackDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import model.Account;
import model.Feedback;

/**
 *
 * @author admin
 */
public class FeedbackManagementServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("account") == null) {
            response.sendRedirect("adminLogin.jsp");
        } else {
            // Lấy đối tượng account từ session
            Account account = (Account) session.getAttribute("account");

            if (account.getRole() == 1 || account.getRole() == 2 || account.getRole() == 3) {
                request.getRequestDispatcher("dashboard.jsp").forward(request, response);
            } else {
                // Set error message and redirect to ErrorServlet
                request.setAttribute("errorMessage", "You do not have the required permissions to access the dashboard.");
                request.getRequestDispatcher("error").forward(request, response);
            }
        }
        FeedbackDAO feedbackDAO = new FeedbackDAO();
        ArrayList<Feedback> feedback = feedbackDAO.getFeedback();

        request.setAttribute("feedback", feedback);

        request.getRequestDispatcher("feedback-management.jsp").forward(request, response);
    }

}
