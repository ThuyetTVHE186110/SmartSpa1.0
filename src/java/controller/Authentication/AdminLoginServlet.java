/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.Authentication;

import dal.AccountDAO;
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
 * @author PC
 */
public class AdminLoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Forward to the login page
        request.getRequestDispatcher("adminLogin.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("txtUsername");
        String password = request.getParameter("txtPassword");

        AccountDAO accountDAO = new AccountDAO();

        // Validate credentials
        Account account = accountDAO.getByUsernamePassword(username, password);

        if (account != null) {
            // Login successful
            HttpSession session = request.getSession();
            session.setAttribute("adminUser", account);
            session.setAttribute("message", "Login successful!");
            response.sendRedirect("dashboard"); // Redirect to the dashboard after successful login
        } else {
            // Login failed
            request.setAttribute("error", "Invalid username or password. Please try again.");
            request.getRequestDispatcher("adminLogin.jsp").forward(request, response); // Forward back to login page with error
        }
    }
}
