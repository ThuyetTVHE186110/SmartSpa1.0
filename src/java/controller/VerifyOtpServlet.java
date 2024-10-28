//package controller;
//
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//import java.io.IOException;
//
//public class VerifyOtpServlet extends HttpServlet {
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        String inputOtp = request.getParameter("otp");
//        String otpPurpose = request.getParameter("otpPurpose");
//        HttpSession session = request.getSession();
//        String storedOtp = (String) session.getAttribute("otp");
//        String userEmail = (String) session.getAttribute("email");
//
//        // Lấy số lần nhập sai OTP từ session
//        Integer attempts = (Integer) session.getAttribute("otpAttempts");
//        if (attempts == null) {
//            attempts = 0;  // Khởi tạo nếu chưa có
//        }
//
//        if (inputOtp != null && inputOtp.equals(storedOtp)) {
//            // OTP chính xác, xóa các thuộc tính liên quan và chuyển đến trang đặt lại mật khẩu
//            session.removeAttribute("otp");
//            session.removeAttribute("otpAttempts"); // Reset số lần nhập
//            response.sendRedirect("resetPassword.jsp");
//
//        } else {
//            // OTP sai, tăng số lần nhập sai
//            attempts++;
//            session.setAttribute("otpAttempts", attempts);
//
//            if (attempts >= 3) {
//                // Nếu nhập sai OTP quá 3 lần, chuyển đến trang báo lỗi và xóa session
//                session.invalidate(); // Xóa toàn bộ session
//                request.setAttribute("errorMessage", "You have exceeded the maximum number of OTP attempts. Please try again later.");
//                request.getRequestDispatcher("error.jsp").forward(request, response);
//            } else {
//                // Nếu chưa quá 3 lần, thông báo lỗi và cho phép nhập lại OTP
//                request.setAttribute("errorMessage", "Invalid OTP. Please enter valid OTP! (" + (3 - attempts) + " attempts remaining)");
//                request.getRequestDispatcher("enterOtp.jsp").forward(request, response);
//            }
//        }
//    }
//}
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
        String otpPurpose = request.getParameter("otpPurpose"); // "login" or "profile_update"
        HttpSession session = request.getSession();
        String storedOtp = (String) session.getAttribute("otp");
        String userEmail = (String) session.getAttribute("email");

        Integer attempts = (Integer) session.getAttribute("otpAttempts");
        if (attempts == null) {
            attempts = 0;
        }

        if (inputOtp != null && inputOtp.equals(storedOtp)) {
            session.removeAttribute("otp");
            session.removeAttribute("otpAttempts");

            if ("profile_update".equals(otpPurpose)) {
                // Redirect to profile update confirmation page
                session.setAttribute("otpVerifiedForProfile", true); // Set flag to indicate OTP verified for profile
                response.sendRedirect("profileUpdateConfirmation.jsp"); // Redirect to profile update confirmation
            } else if ("login".equals(otpPurpose)) {
                response.sendRedirect("resetPassword.jsp"); // Redirect for login or password reset
            }
        } else {
            attempts++;
            session.setAttribute("otpAttempts", attempts);

            if (attempts >= 3) {
                session.invalidate();
                request.setAttribute("errorMessage", "You have exceeded the maximum number of OTP attempts. Please try again later.");
                request.getRequestDispatcher("error.jsp").forward(request, response);
            } else {
                request.setAttribute("errorMessage", "Invalid OTP. Please enter valid OTP! (" + (3 - attempts) + " attempts remaining)");
                request.getRequestDispatcher("enterOtp.jsp").forward(request, response);
            }
        }
    }
}
