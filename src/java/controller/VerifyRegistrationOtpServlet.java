/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import dal.DBContext;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 *
 * @author PC
 */
public class VerifyRegistrationOtpServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String inputOtp = request.getParameter("otp"); // Get OTP input from form
        HttpSession session = request.getSession();
        String storedOtp = (String) session.getAttribute("otp"); // Get stored OTP from session
        String userEmail = (String) session.getAttribute("email"); // Get email from session
        String password = (String) session.getAttribute("password"); // Get password from session

        // Set default roleID to 4
        final int roleID = 4;

        if (inputOtp != null && inputOtp.equals(storedOtp)) {
            // OTP is correct, proceed with account registration

            // Insert the account into the database
            String insertSql = "INSERT INTO Account (Username, Password, RoleID) VALUES (?, ?, ?)";
            try (Connection conn = DBContext.getConnection(); PreparedStatement pstmt = conn.prepareStatement(insertSql)) {
                pstmt.setString(1, userEmail);
                pstmt.setString(2, password);
                pstmt.setInt(3, roleID); // Use the default roleID (4)
                pstmt.executeUpdate();

                // Clear session attributes after successful registration
                session.removeAttribute("otp");
                session.removeAttribute("email");
                session.removeAttribute("password");

                // Set success message and redirect to login page
                session.setAttribute("message", "Email verification successful! You can now log in.");
                response.sendRedirect("login.jsp"); // Redirect to login page
            } catch (SQLException e) {
                e.printStackTrace();
                request.setAttribute("error", "Database error: Unable to create account.");
                request.getRequestDispatcher("OtpConfirmRegistration.jsp").forward(request, response);
            }

        } else {
            // OTP is incorrect, show error message and allow user to retry
            request.setAttribute("error", "Invalid OTP. Please try again.");
            request.getRequestDispatcher("OtpConfirmRegistration.jsp").forward(request, response);
        }
    }
}
