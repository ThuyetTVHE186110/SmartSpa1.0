/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.DashboardDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import model.Account;
import model.Payment;

/**
 *
 * @author PC
 */
@WebServlet(name = "DashboardServlet", urlPatterns = {"/dashboard"})
public class DashboardServlet extends HttpServlet {

    private DashboardDAO dashboardDAO;

    @Override
    public void init() throws ServletException {
        dashboardDAO = new DashboardDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("account") == null) {
            response.sendRedirect("adminLogin.jsp");
            return;
        }

        Account account = (Account) session.getAttribute("account");
        if (account.getRole() != 1 && account.getRole() != 2 && account.getRole() != 3) {
            request.setAttribute("errorMessage", "You do not have the required permissions.");
            request.getRequestDispatcher("error").forward(request, response);
            return;
        }

        try {
            // Get dashboard statistics
            double todayRevenue = dashboardDAO.getTotalRevenue("today");
            double monthRevenue = dashboardDAO.getTotalRevenue("month");
            int totalCustomers = dashboardDAO.getTotalCustomers();
            int todayTransactions = dashboardDAO.getTotalTransactions("today");
            
            // Get recent payments
            List<Payment> recentPayments = dashboardDAO.getRecentPayments(5);
            
            // Get revenue statistics
            Map<String, Double> revenueStats = dashboardDAO.getRevenueStats("daily", 7);
            
            // Get payment status distribution
            Map<String, Integer> statusDistribution = dashboardDAO.getPaymentStatusDistribution();
            
            // Set attributes
            request.setAttribute("todayRevenue", todayRevenue);
            request.setAttribute("monthRevenue", monthRevenue);
            request.setAttribute("totalCustomers", totalCustomers);
            request.setAttribute("todayTransactions", todayTransactions);
            request.setAttribute("recentPayments", recentPayments);
            request.setAttribute("revenueStats", revenueStats);
            request.setAttribute("statusDistribution", statusDistribution);
            
            // Forward to dashboard
            request.getRequestDispatcher("dashboard.jsp").forward(request, response);
            
        } catch (SQLException e) {
            request.setAttribute("errorMessage", "Error loading dashboard: " + e.getMessage());
            request.getRequestDispatcher("error").forward(request, response);
        }
    }
}
