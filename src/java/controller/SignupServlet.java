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
import security.PasswordUtil;

public class SignupServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("signup.jsp").forward(request, response);
    }

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

    protected void handleRegistration(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("txtEmail");
        String password = request.getParameter("txtPassword");
        String confirm = request.getParameter("txtConfirmPassword");
        String name = request.getParameter("txtName");
        String phone = request.getParameter("txtPhone");
        int roleID = 4;

        if (password == null || confirm == null || !password.equals(confirm)) {
            retainInput(request, name, phone, email);
            request.setAttribute("error", "Password does not match the confirm password.");
            request.getRequestDispatcher("signup.jsp").forward(request, response);
            return;
        }

        String hashedPassword = PasswordUtil.hashPassword(password);
        String checkEmailPhoneSql = "SELECT Person.Phone, Person.Email, Account.Username FROM Person LEFT JOIN Account ON Person.ID = Account.PersonID WHERE Person.Phone = ? OR Person.Email = ? OR Account.Username = ?";

        try (Connection conn = DBContext.getConnection(); PreparedStatement checkStmt = conn.prepareStatement(checkEmailPhoneSql)) {
            checkStmt.setString(1, phone);
            checkStmt.setString(2, email);
            checkStmt.setString(3, email);

            try (ResultSet rs = checkStmt.executeQuery()) {
                boolean phoneExists = false, emailExists = false;
                while (rs.next()) {
                    if (phone.equals(rs.getString("Phone"))) {
                        phoneExists = true;
                    }
                    if (email.equals(rs.getString("Email")) || email.equals(rs.getString("Username"))) {
                        emailExists = true;
                    }
                }

                if (phoneExists || emailExists) {
                    retainInput(request, name, phone, email);
                    request.setAttribute("errorMessage", phoneExists ? "This phone number is already registered." : "This email is already registered.");
                    request.getRequestDispatcher("signup.jsp").forward(request, response);
                    return;
                }
            }

            String otp = generateOtp();
            if (sendOtpEmail(email, otp)) {
                HttpSession session = request.getSession();
                session.setAttribute("otp", otp);
                session.setAttribute("email", email);
                session.setAttribute("password", hashedPassword);  // Use hashed password
                session.setAttribute("roleID", roleID);
                session.setAttribute("name", name);
                session.setAttribute("phone", phone);
                response.sendRedirect("OtpConfirmRegistration.jsp");
            } else {
                retainInput(request, name, phone, email);
                request.setAttribute("errorMessage", "Failed to send OTP. Please try again.");
                request.getRequestDispatcher("signup.jsp").forward(request, response);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Database error: " + e.getMessage());
            request.getRequestDispatcher("signup.jsp").forward(request, response);
        }
    }

    private void retainInput(HttpServletRequest request, String name, String phone, String email) {
        request.setAttribute("txtName", name);
        request.setAttribute("txtPhone", phone);
        request.setAttribute("txtEmail", email);
    }

    private String generateOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
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
                return new PasswordAuthentication("dat33112@gmail.com", "cdct hlco ymoj wklg");
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
