package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

public class VerifyOtpServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String inputOtp = request.getParameter("otp");
        HttpSession session = request.getSession();
        String storedOtp = (String) session.getAttribute("otp");
        String userEmail = (String) session.getAttribute("userEmail");

        if (inputOtp != null && inputOtp.equals(storedOtp)) {
            // OTP is correct, proceed to reset password
            response.sendRedirect("resetPassword.jsp"); // Redirect to reset password page
        } else {
            // OTP is incorrect, redirect back with error
            response.sendRedirect("resetPassword.jsp?error=invalidOtp");
        }
    }
}
