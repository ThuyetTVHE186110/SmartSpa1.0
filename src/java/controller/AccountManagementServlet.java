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
            if (account.getRole() == 1 || account.getRole() == 2 || account.getRole() == 3) {
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
                request.getRequestDispatcher("error").forward(request, response);
            }
        }
    }
}
