//package controller;
//
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.util.Properties;
//import java.util.Random;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import javax.mail.*;
//import javax.mail.internet.*;
//
//public class PasswordForgotServlet extends HttpServlet {
//
//    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        response.setContentType("text/html;charset=UTF-8");
//        try (PrintWriter out = response.getWriter()) {
//            out.println("<!DOCTYPE html>");
//            out.println("<html>");
//            out.println("<head>");
//            out.println("<title>Servlet PasswordForgotServlet</title>");
//            out.println("</head>");
//            out.println("<body>");
//            out.println("<h1>Servlet PasswordForgotServlet at " + request.getContextPath() + "</h1>");
//            out.println("</body>");
//            out.println("</html>");
//        }
//    }
//
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        processRequest(request, response);
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        String email = request.getParameter("email"); // Get email from the form
//        String otp = generateOtp(); // Generate OTP
//
//        // Send OTP to the user's email
//        if (sendOtpEmail(email, otp)) {
//            // Store the OTP and email in session or database for verification later
//            request.getSession().setAttribute("otp", otp);
//            request.getSession().setAttribute("email", email);
//            response.sendRedirect("verifyOtp.jsp"); // Redirect to OTP verification page
//        } else {
//            // Handle error if email sending fails
//            response.getWriter().println("Failed to send OTP. Please try again.");
//        }
//    }
//
//    private String generateOtp() {
//        Random random = new Random();
//        int otp = 100000 + random.nextInt(900000); // Generate a 6-digit OTP
//        return String.valueOf(otp);
//    }
//
//    private boolean sendOtpEmail(String recipient, String otp) {
//        String host = "smtp.example.com"; // Your SMTP server
//        String from = "no-reply@example.com"; // Your sender email
//        String subject = "Your OTP Code";
//        String message = "Your OTP is: " + otp;
//
//        Properties properties = new Properties();
//        properties.put("mail.smtp.host", host);
//        properties.put("mail.smtp.port", "587");
//        properties.put("mail.smtp.auth", "true");
//
//        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
//            protected PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication("your_email@example.com", "your_email_password");
//            }
//        });
//
//        try {
//            Message mimeMessage = new MimeMessage(session);
//            mimeMessage.setFrom(new InternetAddress(from));
//            mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
//            mimeMessage.setSubject(subject);
//            mimeMessage.setText(message);
//            Transport.send(mimeMessage);
//            return true; // Return true if email was sent successfully
//        } catch (MessagingException e) {
//            e.printStackTrace();
//            return false; // Return false if there was an error sending the email
//        }
//    }
//
//    @Override
//    public String getServletInfo() {
//        return "Short description";
//    }
//}
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;
import java.util.Random;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordForgotServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Directly handle GET requests if needed
        response.sendRedirect("ForgotPassword.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email"); // Get email from the form

        // Validate email input
        if (email == null || email.isEmpty() || !isValidEmail(email)) {
            request.setAttribute("errorMessage", "Please enter a valid email address.");
            request.getRequestDispatcher("ForgotPassword.jsp").forward(request, response);
            return;
        }

        String otp = generateOtp(); // Generate OTP

        // Send OTP to the user's email
        if (sendOtpEmail(email, otp)) {
            // Store the OTP and email in session for verification later
            request.getSession().setAttribute("otp", otp);
            request.getSession().setAttribute("email", email);
            response.sendRedirect("enterOtp.jsp"); // Redirect to OTP verification page
        } else {
            // Handle error if email sending fails
            request.setAttribute("errorMessage", "Failed to send OTP. Please try again.");
            request.getRequestDispatcher("ForgotPassword.jsp").forward(request, response);
        }
    }

    private String generateOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000); // Generate a 6-digit OTP
        return String.valueOf(otp);
    }

    private boolean sendOtpEmail(String recipient, String otp) {
        String host = "smtp.gmail.com"; // Your SMTP server
        String from = "dat33112@gmail.com"; // Your sender email
        String subject = "Your OTP Code";
        String messageContent = "Your OTP is: " + otp;

        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true"); // Enable TLS

        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("dat33112@gmail.com", "cdct hlco ymoj wklg"); // Use a secure method for this
            }
        });

        try {
            Message mimeMessage = new MimeMessage(session);
            mimeMessage.setFrom(new InternetAddress(from));
            mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
            mimeMessage.setSubject(subject);
            mimeMessage.setText(messageContent);
            Transport.send(mimeMessage);
            return true; // Return true if email was sent successfully
        } catch (MessagingException e) {
            System.err.println("Error sending email: " + e.getMessage());
            e.printStackTrace(); // Log the error for debugging
            return false; // Return false if there was an error sending the email
        }
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    @Override
    public String getServletInfo() {
        return "Password Forgot Servlet";
    }
}
