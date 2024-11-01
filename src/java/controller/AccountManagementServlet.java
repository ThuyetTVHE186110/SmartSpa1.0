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

        // Pagination parameters
        int page = 1; // Default page
        int recordsPerPage = 10; // Default records per page
        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }

        try {
            if ("update".equals(action)) {
                int accountId = Integer.parseInt(request.getParameter("id"));
                String username = request.getParameter("username");
                String newPassword = request.getParameter("newPassword");
                String confirmPassword = request.getParameter("confirmPassword");
                String status = request.getParameter("status");
                int roleId = Integer.parseInt(request.getParameter("role"));
                String personName = request.getParameter("name");

                // Check if newPassword and confirmPassword match (only if newPassword is provided)
                if (newPassword != null && !newPassword.isEmpty()) {
                    if (!newPassword.equals(confirmPassword)) {
                        request.setAttribute("errorMessage", "Passwords do not match.");
                        request.getRequestDispatcher("accountManagement.jsp").forward(request, response);
                        return;
                    }
                } else {
                    // If newPassword is empty, retain the existing password
                    newPassword = accountDAO.getCurrentPassword(accountId);
                }

                // Proceed with the update if passwords match
                accountDAO.updateAccountDetails(accountId, username, newPassword, status, roleId, personName);
                Account updatedAccount = accountDAO.getAccountById(accountId);

                // Replace the existing session attribute with the updated account information
                session.setAttribute("account", updatedAccount);
                session.setAttribute("person", updatedAccount.getPersonInfo());

                // Set success message
                session.setAttribute("successMessage", "Account updated successfully.");
            } else if ("add".equals(action)) {
                // Add account logic
                String username = request.getParameter("username");
                String password = request.getParameter("password");
                String confirmPassword = request.getParameter("confirmPassword");
                int roleId = Integer.parseInt(request.getParameter("role"));
                String personName = request.getParameter("name");

                // Check if passwords match
                if (!password.equals(confirmPassword)) {
                    request.setAttribute("errorMessage", "Passwords do not match.");
                    request.getRequestDispatcher("accountManagement.jsp").forward(request, response);
                    return;
                }

                // Insert the new account into the database
                accountDAO.addAccount(username, password, roleId, personName);

                // Set success message
                session.setAttribute("successMessage", "Account added successfully.");
            }

            // Retrieve paginated accounts and calculate total pages
            int totalRecords = accountDAO.getTotalStaffAccounts(); // Get the total number of accounts
            int totalPages = (int) Math.ceil((double) totalRecords / recordsPerPage);
            List<Account> accounts = accountDAO.getPaginatedStaffAccounts(page, recordsPerPage);

            // Set attributes for pagination
            request.setAttribute("accounts", accounts);
            request.setAttribute("currentPage", page);
            request.setAttribute("totalPages", totalPages);

            // Forward to account management JSP
            request.getRequestDispatcher("accountManagement.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Invalid input format. Please check the entered data.");
            request.getRequestDispatcher("accountManagement.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "An error occurred while processing the account. Please try again.");
            request.getRequestDispatcher("accountManagement.jsp").forward(request, response);
        }
    }
}
