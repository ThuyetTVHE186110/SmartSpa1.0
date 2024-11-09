/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Account;

/**
 *
 * @author ndcpr
 */
public class StaffDashboardServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false); // Lấy session nếu đã có, không tạo mới

        if (session == null || session.getAttribute("account") == null) {
            response.sendRedirect("adminLogin");
            return;
        }

        // Lấy account từ session
        Account account = (Account) session.getAttribute("account");

        if (account.getRole() == 3) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("Frontend_Staff/staffDashboard.jsp");
            dispatcher.forward(request, response);
        } else {
            request.setAttribute("errorMessage", "You do not have the required permissions to access the dashboard.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("error");
            dispatcher.forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response); 
    }
}
