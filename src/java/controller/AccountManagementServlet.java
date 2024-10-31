/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.AccountDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.Account;
import java.sql.SQLException;

/**
 *
 * @author PC
 */
public class AccountManagementServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("account") == null) {
            response.sendRedirect("adminLogin.jsp");
        } else {
            // Get account from session
            Account account = (Account) session.getAttribute("account");

            // Only role 1, 2, 3 (admin, manager, staff) can access account management
            if (account.getRole() == 1) {
                // Fetch list of staff accounts
                AccountDAO accountDAO = new AccountDAO();
                List<Account> accounts = accountDAO.getAllStaffAccounts();  // Fetch the list from the database

                // Set the list of accounts as a request attribute
                request.setAttribute("accounts", accounts);

                // Forward to the account management JSP
                request.getRequestDispatcher("accountManagement.jsp").forward(request, response);
            } else {
                // If user does not have the required role, redirect to the error page
                request.setAttribute("errorMessage", "You do not have the required permissions to access this page.");
                request.getRequestDispatcher("roleError").forward(request, response);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("account") == null) {
            response.sendRedirect("login");
            return;
        }

        String action = request.getParameter("action");
        AccountDAO accountDAO = new AccountDAO();

        try {
            if ("update".equals(action)) {
                int accountId = Integer.parseInt(request.getParameter("id"));
                String username = request.getParameter("username");
                String password = request.getParameter("password");
                String status = request.getParameter("status");
                int roleId = Integer.parseInt(request.getParameter("role"));  // Ensure role ID is parsed correctly
                String personName = request.getParameter("name");

                // Call the DAO method to update the account details
                accountDAO.updateAccountDetails(accountId, username, password, status, roleId, personName);

                // Refresh data
                List<Account> accounts = accountDAO.getAllStaffAccounts();
                request.setAttribute("accounts", accounts);

                // Set a success message and forward to account management JSP
                session.setAttribute("successMessage", "Account updated successfully.");
                request.getRequestDispatcher("accountManagement.jsp").forward(request, response);
                return;
            }
            response.sendRedirect("accountManagement.jsp");
        } catch (NumberFormatException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Invalid input format. Please check the entered data.");
            request.getRequestDispatcher("accountManagement.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "An error occurred while updating the account. Please try again.");
            request.getRequestDispatcher("accountManagement.jsp").forward(request, response);
        }
    }

}
