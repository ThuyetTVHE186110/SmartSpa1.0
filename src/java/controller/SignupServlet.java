/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.DBContext;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;
import java.util.Random;

/**
 *
 * @author PC
 */
public class SignupServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet SignupServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SignupServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("signup");

        if ("signup".equals(action)) {
            handleRegistration(request, response);
        } else if ("Reset".equals(action)) {
            handleReset(request, response);
        } else if ("verifyOtp".equals(action)) {
            handleOtpVerification(request, response);
        }
    }

    private void handleRegistration(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("txtEmail");
        String password = request.getParameter("txtPassword");
        String confirm = request.getParameter("txtConfirmPassword");
        int roleID = 4;

        // Check if passwords match
        if (password == null || confirm == null || !password.equals(confirm)) {
            request.setAttribute("error", "Password does not match the confirm password.");
            // Ensure it forwards back to signup.jsp with the error message
            request.getRequestDispatcher("signup.jsp").forward(request, response);
            return;
        }

        // Check if the email already exists only after passwords match
        String checkEmailSql = "SELECT * FROM Account WHERE Username = ?";

        try (Connection conn = DBContext.getConnection(); PreparedStatement checkStmt = conn.prepareStatement(checkEmailSql)) {

            // Check if the email already exists
            checkStmt.setString(1, username);
            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next()) {
                    request.setAttribute("error", "This email is already registered. Please log in or reset your password.");
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                    return;
                }
            }

            // If email doesn't exist, proceed with OTP generation
            String otp = generateOtp();
            if (sendOtpEmail(username, otp)) {
                // Store the OTP and other info in session
                request.getSession().setAttribute("otp", otp);
                request.getSession().setAttribute("email", username);
                request.getSession().setAttribute("password", password);
                request.getSession().setAttribute("roleID", roleID);

                // Redirect to OTP verification page
                response.sendRedirect("OtpConfirmRegistration.jsp");
            } else {
                request.setAttribute("error", "Failed to send OTP. Please try again.");
                request.getRequestDispatcher("signup.jsp").forward(request, response);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("message", "Database error: " + e.getMessage());
            request.getRequestDispatcher("signup.jsp").forward(request, response);
        }
    }

    private void handleOtpVerification(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String enteredOtp = request.getParameter("otp");
        String sessionOtp = (String) request.getSession().getAttribute("otp");

        if (enteredOtp.equals(sessionOtp)) {
            // Retrieve stored user info
            String email = (String) request.getSession().getAttribute("email");
            String password = (String) request.getSession().getAttribute("password");
            int roleID = (int) request.getSession().getAttribute("roleID");

            // Insert user into database
            String insertSql = "INSERT INTO Account (Username, Password, RoleID) VALUES (?, ?, ?)";
            try (Connection conn = DBContext.getConnection(); PreparedStatement pstmt = conn.prepareStatement(insertSql)) {
                pstmt.setString(1, email);
                pstmt.setString(2, password);
                pstmt.setInt(3, roleID);
                pstmt.executeUpdate();

                // Clear session attributes
                request.getSession().removeAttribute("otp");
                request.getSession().removeAttribute("email");
                request.getSession().removeAttribute("password");
                request.getSession().removeAttribute("roleID");

                // Redirect to login page
                request.getSession().setAttribute("message", "Registration successful! Please log in.");
                response.sendRedirect("login.jsp");

            } catch (SQLException e) {
                e.printStackTrace();
                request.setAttribute("message", "Database error: " + e.getMessage());
                request.getRequestDispatcher("OtpConfirmRegistration.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("error", "Invalid OTP. Please try again.");
            request.getRequestDispatcher("OtpConfirmRegistration.jsp").forward(request, response);
        }
    }

    private String generateOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000); // Generate a 6-digit OTP
        return String.valueOf(otp);
    }

    private boolean sendOtpEmail(String recipient, String otp) {
        String host = "smtp.gmail.com";
        String from = "no@reply.smartbeauty.com";
        String subject = "Your OTP Code";
        String messageContent = "Your OTP is: " + otp;

        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

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
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void handleReset(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("signup.jsp");
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
