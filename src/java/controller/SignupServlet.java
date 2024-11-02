/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.DBContext;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Redirect to the signup page or handle GET requests as needed
        request.getRequestDispatcher("signup.jsp").forward(request, response);
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
        }
    }

    private void handleRegistration(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("txtEmail");
        String password = request.getParameter("txtPassword");
        String confirm = request.getParameter("txtConfirmPassword");
        String name = request.getParameter("txtName");
        String phone = request.getParameter("txtPhone");
        int roleID = 4;

        // Check if passwords match
        if (password == null || confirm == null || !password.equals(confirm)) {
            request.setAttribute("error", "Password does not match the confirm password.");
            request.setAttribute("txtName", name);
            request.setAttribute("txtPhone", phone);
            request.setAttribute("txtEmail", email);
            request.getRequestDispatcher("signup.jsp").forward(request, response);
            return;
        }

        // SQL query to check if the phone or email already exists in Person or Account
        String checkEmailPhoneSql = "SELECT Person.Phone, Person.Email, Account.Username "
                + "FROM Person LEFT JOIN Account ON Person.ID = Account.PersonID "
                + "WHERE Person.Phone = ? OR Person.Email = ? OR Account.Username = ?";

        try (Connection conn = DBContext.getConnection(); PreparedStatement checkStmt = conn.prepareStatement(checkEmailPhoneSql)) {

            // Set parameters to check for phone and email
            checkStmt.setString(1, phone);
            checkStmt.setString(2, email);
            checkStmt.setString(3, email);

            try (ResultSet rs = checkStmt.executeQuery()) {
                boolean phoneExists = false;
                boolean emailExists = false;

                while (rs.next()) {
                    String dbPhone = rs.getString("Phone");
                    String dbEmailPerson = rs.getString("Email");
                    String dbEmailAccount = rs.getString("Username");

                    if (dbPhone != null && dbPhone.equals(phone)) {
                        phoneExists = true;
                    }

                    if ((dbEmailPerson != null && dbEmailPerson.equals(email))
                            || (dbEmailAccount != null && dbEmailAccount.equals(email))) {
                        emailExists = true;
                    }
                }

                // Nếu số điện thoại đã tồn tại
                if (phoneExists) {
                    request.setAttribute("errorMessage", "This phone number is already registered.");
                    request.setAttribute("txtName", name);
                    request.setAttribute("txtPhone", phone);
                    request.setAttribute("txtEmail", email);
                    request.getRequestDispatcher("signup.jsp").forward(request, response);
                    return;
                }

                // Nếu email đã tồn tại
                if (emailExists) {
                    request.setAttribute("errorMessage", "This email is already registered.");
                    request.setAttribute("txtName", name);
                    request.setAttribute("txtPhone", phone);
                    request.setAttribute("txtEmail", email);
                    request.getRequestDispatcher("signup.jsp").forward(request, response);
                    return;
                }
            }

            // Lưu thông tin vào session và chờ xác nhận OTP
            String otp = generateOtp(); // Hàm tạo OTP
            if (sendOtpEmail(email, otp)) {
                // Lưu OTP và thông tin người dùng vào session để xác nhận sau khi người dùng nhập đúng OTP
                HttpSession session = request.getSession();
                session.setAttribute("otp", otp);
                session.setAttribute("email", email);
                session.setAttribute("password", password);
                session.setAttribute("roleID", roleID);
                session.setAttribute("name", name);
                session.setAttribute("phone", phone);

                // Chuyển hướng đến trang OTP để xác nhận
                response.sendRedirect("OtpConfirmRegistration.jsp");
            } else {
                request.setAttribute("errorMessage", "Failed to send OTP. Please try again.");
                request.getRequestDispatcher("signup.jsp").forward(request, response);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Database error: " + e.getMessage());
            request.getRequestDispatcher("signup.jsp").forward(request, response);
        }
    }

// Helper method to retain input values in the form
    private void retainInput(HttpServletRequest request, String name, String phone, String email) {
        request.setAttribute("txtName", name);
        request.setAttribute("txtPhone", phone);
        request.setAttribute("txtEmail", email);
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

}
