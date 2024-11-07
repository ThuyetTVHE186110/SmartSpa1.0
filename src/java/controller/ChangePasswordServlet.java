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
import model.Account;
import org.mindrot.jbcrypt.BCrypt;

public class ChangePasswordServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get the current session and retrieve the account
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");

        // Redirect to login if not logged in
        if (account == null) {
            response.sendRedirect("login");
            return;
        }

        // Retrieve form data
        String currentPassword = request.getParameter("currentPassword");
        String newPassword = request.getParameter("newPassword");

        // Check if the current password is correct
        if (!BCrypt.checkpw(currentPassword, account.getPassword())) {
            request.setAttribute("errorMessage", "Current password is incorrect.");
            request.getRequestDispatcher("userProfile.jsp").forward(request, response);
            return;
        }

        // Encrypt the new password using bcrypt
        String hashedNewPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());

        // Update the password in the database
        AccountDAO accountDAO = new AccountDAO();
        boolean updateSuccess = false;

        try {
            updateSuccess = accountDAO.updatePassword(account.getUsername(), hashedNewPassword);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "An error occurred while updating your password. Please try again.");
            request.getRequestDispatcher("userProfile.jsp").forward(request, response);
            return;
        }

        // Check if the update was successful
        if (updateSuccess) {
            // Update the password in the session account object
            account.setPassword(hashedNewPassword);
            session.setAttribute("account", account);

            // Success message and redirect
            session.setAttribute("successMessage", "Password changed successfully!");
            response.sendRedirect("userProfile.jsp?tab=change-password");
        } else {
            request.setAttribute("errorMessage", "Failed to change password. Please try again.");
            request.getRequestDispatcher("userProfile.jsp").forward(request, response);
        }
    }
}
