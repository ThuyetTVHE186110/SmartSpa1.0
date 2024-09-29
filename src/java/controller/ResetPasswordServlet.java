//package controller;
//
//import dal.AccountDAO;  // Import your AccountDAO class
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//import java.io.IOException;
//
//public class ResetPasswordServlet extends HttpServlet {
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        String confirm = request.getParameter("confirm");
//        // Get the new password from the request
//        String newPassword = request.getParameter("newPassword");
//
//        // Validate the new password (you can add more advanced validation if needed)
//        if (newPassword == null || newPassword.isEmpty()) {
//            response.sendRedirect("resetPassword.jsp?error=empty"); // Redirect back with an error message
//            return;
//        }
//        // Check if passwords match
//        if (!newPassword.equals(confirm)) {
//            request.setAttribute("error", "Password does not match the confirm password.");
//            request.getRequestDispatcher("signup.jsp").forward(request, response);
//            return;
//        }
//
//        // Get the user email (or username) from the session
//        HttpSession session = request.getSession();
//        String email = (String) session.getAttribute("email");
//
//        // Check if the session contains the user email
//        if (email == null || email.isEmpty()) {
//            response.sendRedirect("resetPassword.jsp?error=sessionExpired"); // Redirect if session is invalid
//            return;
//        }
//
//        // Use AccountDAO to update the password
//        AccountDAO accountDAO = new AccountDAO();
//        boolean isPasswordUpdated = accountDAO.updatePassword(email, newPassword);
//
//        // Handle the outcome of the password update
//        if (isPasswordUpdated) {
//            // Redirect to the login page with a success message
//            response.sendRedirect("login.jsp?success=passwordChanged"); // Include success message in the URL
//        } else {
//            response.sendRedirect("resetPassword.jsp?error=updateFailed"); // Redirect back to reset form on failure
//        }
//    }
//
//}
package controller;

import dal.AccountDAO;  // Import your AccountDAO class
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

public class ResetPasswordServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String confirm = request.getParameter("confirm");
        String newPassword = request.getParameter("newPassword");

        // Validate the new password
        if (newPassword == null || newPassword.isEmpty()) {
            request.setAttribute("error", "Password cannot be empty."); // Set error message
            request.getRequestDispatcher("resetPassword.jsp").forward(request, response);
            return;
        }

        // Check if passwords match
        if (!newPassword.equals(confirm)) {
            request.setAttribute("error", "Passwords do not match."); // Set error message
            request.getRequestDispatcher("resetPassword.jsp").forward(request, response);
            return;
        }

        // Get the user email from the session
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");

        if (email == null || email.isEmpty()) {
            request.setAttribute("error", "Session expired. Please try again."); // Set error message
            request.getRequestDispatcher("resetPassword.jsp").forward(request, response);
            return;
        }

        // Use AccountDAO to update the password
        AccountDAO accountDAO = new AccountDAO();
        boolean isPasswordUpdated = accountDAO.updatePassword(email, newPassword);

        // Handle the outcome of the password update
        if (isPasswordUpdated) {
            request.getSession().setAttribute("message", "Password changed successfully."); // Set success message
            response.sendRedirect("login.jsp"); // Redirect to login page
        } else {
            request.setAttribute("error", "Password update failed. Please try again."); // Set error message
            request.getRequestDispatcher("resetPassword.jsp").forward(request, response);
        }
    }
}
