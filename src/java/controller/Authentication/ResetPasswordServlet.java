package controller.Authentication;

import dal.AccountDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import security.PasswordUtil;

public class ResetPasswordServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Chuyển hướng đến trang quên mật khẩu
        request.getRequestDispatcher("resetPassword.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String confirm = request.getParameter("txtConfirmPassword");
        String newPassword = request.getParameter("txtPassword");

        // Validate the new password
        if (newPassword == null || newPassword.isEmpty()) {
            request.setAttribute("error", "Password cannot be empty.");
            request.getRequestDispatcher("resetPassword.jsp").forward(request, response);
            return;
        }

        // Check if passwords match
        if (!newPassword.equals(confirm)) {
            request.setAttribute("error", "Passwords do not match. Re-enter password.");
            request.getRequestDispatcher("resetPassword.jsp").forward(request, response);
            return;
        }

        // Password complexity check
        if (!isPasswordValid(newPassword)) {
            request.setAttribute("error", "Password must start with an uppercase letter, contain at least one digit, one special character, and be between 8-20 characters long.");
            request.getRequestDispatcher("resetPassword.jsp").forward(request, response);
            return;
        }

        // Get the user email from the session
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");

        if (email == null || email.isEmpty()) {
            request.setAttribute("error", "Session expired. Please try again.");
            request.getRequestDispatcher("resetPassword.jsp").forward(request, response);
            return;
        }

        // Hash the new password using BCrypt
        String hashedPassword = PasswordUtil.hashPassword(newPassword);

        // Use AccountDAO to update the password
        AccountDAO accountDAO = new AccountDAO();
        boolean isPasswordUpdated;
        try {
            isPasswordUpdated = accountDAO.updatePassword(email, hashedPassword);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Database error. Please try again later.");
            request.getRequestDispatcher("resetPassword.jsp").forward(request, response);
            return;
        }

        // Handle the outcome of the password update
        if (isPasswordUpdated) {
            session.setAttribute("message", "Password changed successfully.");
            response.sendRedirect("login");
        } else {
            request.setAttribute("error", "Password update failed. Please try again.");
            request.getRequestDispatcher("resetPassword.jsp").forward(request, response);
        }
    }

    // Helper method to validate the password format
    private boolean isPasswordValid(String password) {
        String passwordCriteria = "^(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*])[A-Za-z\\d!@#$%^&*]{8,20}$";
        return password.matches(passwordCriteria);
    }
}
